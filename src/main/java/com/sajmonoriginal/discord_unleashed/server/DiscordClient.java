package com.sajmonoriginal.discord_unleashed.server;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.external.JDAWebhookClient;
import com.sajmonoriginal.discord_unleashed.DiscordUnleashedMod;
import com.sajmonoriginal.discord_unleashed.config.DiscordUnleashedConfig;
import com.sajmonoriginal.discord_unleashed.util.ChatFormatting;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class DiscordClient {
    private static JDA jda;
    private static TextChannel channel;

    public static boolean init() {
        try {
            if (!DiscordUnleashedConfig.discord_enable) {
                DiscordUnleashedMod.LOGGER.info("Discord integration disabled in config");
                return false;
            }

            String token = DiscordUnleashedConfig.discord_token;
            if (token == null || token.isEmpty() || token.equals("paste your token here")) {
                DiscordUnleashedMod.LOGGER.info("Discord token not set in config");
                return false;
            }

            jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .build();

            jda.addEventListener(new Listener());

            jda.awaitReady();

            String channelId = DiscordUnleashedConfig.discord_channel;
            if (channelId == null || channelId.isEmpty() || channelId.equals("paste your channel id here")) {
                DiscordUnleashedMod.LOGGER.info("Discord channel ID not set in config");
                return false;
            }

            try {
                channel = jda.getTextChannelById(channelId);

                if (channel == null) {
                    DiscordUnleashedMod.LOGGER.info("Could not find Discord channel with ID: " + channelId);
                    return false;
                }

                DiscordUnleashedMod.LOGGER.info("Successfully connected to Discord channel: " + channel.getName());
            } catch (Exception e) {
                DiscordUnleashedMod.LOGGER.info("Error getting Discord channel: " + e.getMessage());
                return false;
            }

            return true;
        } catch (Exception e) {
            DiscordUnleashedMod.LOGGER.info("Discord initialization error: " + e.getMessage());
            return false;
        }
    }

    public static JDAWebhookClient getWebhook() {
        if (!DiscordUnleashedConfig.discord_enable) {
            return null;
        }

        String webhookUrl = DiscordUnleashedConfig.discord_webhook_url;
        if (webhookUrl == null || webhookUrl.isEmpty() || webhookUrl.equals("paste your webhook url here")) {
            DiscordUnleashedMod.LOGGER.info("Discord webhook URL not set in config");
            return null;
        }


        String serverName = DiscordUnleashedConfig.discord_servername;
        if (serverName.toLowerCase().contains("discord")) {
            serverName = "Minecraft Server"; // Safe fallback name
        }

        try {
            return new WebhookClientBuilder(webhookUrl)
                    .setThreadFactory((job) -> {
                        Thread thread = new Thread(job, "Discord-Webhook-Thread");
                        thread.setDaemon(true);
                        return thread;
                    })
                    .setWait(true)
                    .buildJDA();
        } catch (Exception e) {
            DiscordUnleashedMod.LOGGER.info("Error creating webhook client: " + e.getMessage());
            return null;
        }
    }

    private static class Listener extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (event.getAuthor().isBot()) return;
            if (channel == null) return;
            if (event.getChannel().getId().equals(channel.getId())) {
                Message message = event.getMessage();


                String content = message.getContentDisplay();


                content = ChatFormatting.stripDiscordFormatting(content);


                DiscordChatRelay.sendToMinecraft(
                        message.getAuthor().getName(),
                        content
                );
            }
        }
    }
}