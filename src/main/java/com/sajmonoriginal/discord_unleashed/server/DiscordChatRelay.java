package com.sajmonoriginal.discord_unleashed.server;

import club.minnced.discord.webhook.external.JDAWebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.sajmonoriginal.discord_unleashed.config.DiscordUnleashedConfig;
import com.sajmonoriginal.discord_unleashed.DiscordUnleashedMod;
import com.sajmonoriginal.discord_unleashed.util.ChatFormatting;
import net.minecraft.server.MinecraftServer;

import java.time.Instant;

public class DiscordChatRelay {
    private static JDAWebhookClient webhookClient;

    private static JDAWebhookClient getWebhookClient() {
        if (webhookClient == null) {
            try {
                webhookClient = DiscordClient.getWebhook();
            } catch (Exception e) {
                DiscordUnleashedMod.LOGGER.info("Failed to initialize Discord webhook client: " + e.getMessage());
            }
        }
        return webhookClient;
    }

    public static void sendToMinecraft(String author, String message) {

        String formattedMessage = "[" + ChatFormatting.DARK_PURPLE + "DISCORD" + ChatFormatting.RESET + "] <" + author + "> " + message;


        DiscordUnleashedMod.LOGGER.info(formattedMessage);


        try {
            com.sajmonoriginal.discord_unleashed.util.ServerHelper.broadcastMessage(formattedMessage);
        } catch (Exception e) {
            DiscordUnleashedMod.LOGGER.info("Failed to broadcast Discord message: " + e.toString());
        }
    }

    public static void sendToDiscord(String author, String message) {
        DiscordUnleashedMod.LOGGER.info("sendToDiscord() called with author=" + author + ", message=" + message);

        JDAWebhookClient client = getWebhookClient();
        if (client == null) {
            DiscordUnleashedMod.LOGGER.warn("Webhook client is null. Not sending to Discord.");
            return;
        }

        try {
            DiscordUnleashedMod.LOGGER.info("Preparing webhook message...");
            WebhookMessageBuilder builder = new WebhookMessageBuilder();
            builder.setUsername(author);
            builder.setAvatarUrl("https://www.mc-heads.net/head/" + author + "/150");
            builder.setContent(message);

            DiscordUnleashedMod.LOGGER.info("Sending webhook message...");
            client.send(builder.build());

            DiscordUnleashedMod.LOGGER.info("Message sent successfully to Discord!");
        } catch (Exception e) {
            DiscordUnleashedMod.LOGGER.error("Failed to send message to Discord: ", e);
        }
    }


    public static void sendJoinLeaveMessage(String username, boolean joined) {
        JDAWebhookClient client = getWebhookClient();
        if (client == null) {
            DiscordUnleashedMod.LOGGER.info("Webhook client is null — not sending join/leave.");
            return;
        }

        try {
            String avatarUrl = "https://www.mc-heads.net/head/" + username;
            String joinLeaveText = username + (joined ? " joined" : " left") + " the server";

            WebhookEmbed embed = new WebhookEmbedBuilder()
                    .setColor(joined ? 0x00FF00 : 0xFF0000)
                    .setAuthor(new WebhookEmbed.EmbedAuthor(joinLeaveText, avatarUrl, null))
                    .build();

            sendMessage(null, embed);
        } catch (Exception e) {
            DiscordUnleashedMod.LOGGER.info("Failed to send join/leave message: " + e.getMessage());
        }
    }


    public static void sendServerStartMessage() {
        JDAWebhookClient client = getWebhookClient();
        if (client == null) return;

        try {
            WebhookEmbed embed = new WebhookEmbedBuilder()
                    .setColor(0x4ae485)
                    .setAuthor(new WebhookEmbed.EmbedAuthor("✅ Server started!", null, null))
                    .setTimestamp(Instant.now())
                    .build();
            sendMessage(null, embed);
        } catch (Exception e) {
            DiscordUnleashedMod.LOGGER.info("Failed to send server start message: " + e.getMessage());
        }
    }

    public static void sendServerStoppedMessage() {
        JDAWebhookClient client = getWebhookClient();
        if (client == null) return;

        try {
            WebhookEmbed embed = new WebhookEmbedBuilder()
                    .setColor(0xf92f60)
                    .setAuthor(new WebhookEmbed.EmbedAuthor("❌ Server stopped!", null, null))
                    .setTimestamp(Instant.now())
                    .build();
            sendMessage(null, embed);
        } catch (Exception e) {
            DiscordUnleashedMod.LOGGER.info("Failed to send server stop message: " + e.getMessage());
        }
    }

    private static void sendMessage(String content, WebhookEmbed embed) {
        JDAWebhookClient client = getWebhookClient();
        if (client == null) return;

        try {
            WebhookMessageBuilder builder = new WebhookMessageBuilder();
            String serverName = DiscordUnleashedConfig.discord_servername;
            if (serverName.toLowerCase().contains("discord")) {
                serverName = "MC Server";
            }
            builder.setUsername(serverName);
            builder.setAvatarUrl(DiscordUnleashedConfig.discord_serverpfp_url);
            if (content != null && !content.isEmpty()) {
                builder.setContent(content);
            }
            if (embed != null) {
                builder.addEmbeds(embed);
            }
            client.send(builder.build());
        } catch (Exception e) {
            DiscordUnleashedMod.LOGGER.info("Failed to send Discord message: " + e.toString());
        }
    }
}
