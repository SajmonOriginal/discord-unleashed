package com.sajmonoriginal.discord_unleashed.mixin;

import com.sajmonoriginal.discord_unleashed.DiscordUnleashedMod;
import com.sajmonoriginal.discord_unleashed.config.DiscordUnleashedConfig;
import com.sajmonoriginal.discord_unleashed.server.DiscordChatRelay;
import net.minecraft.class_340;
import net.minecraft.class_69;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(value = ServerPlayNetworkHandler.class, remap = false)
public class Mixin_ServerPlayNetworkHandler {

    private class_69 getPlayer(ServerPlayNetworkHandler instance) {
        try {
            for (Field field : ServerPlayNetworkHandler.class.getDeclaredFields()) {
                if (field.getType().getName().equals("net.minecraft.class_69")) {
                    field.setAccessible(true);
                    return (class_69) field.get(instance);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to find class_69 field: " + e.getMessage());
        }
        return null;
    }

    @Inject(method = "method_833", at = @At("HEAD"))
    private void onDisconnect(String reason, CallbackInfo ci) {
        DiscordUnleashedMod.LOGGER.info("Mixin hook: method_833 called. Reason: " + reason);

        if (!DiscordUnleashedConfig.discord_enable) return;

        class_69 player = getPlayer((ServerPlayNetworkHandler)(Object)this);
        if (player != null) {
            DiscordUnleashedMod.LOGGER.info("Sending disconnect embed to Discord for: " + player.name);
            DiscordChatRelay.sendJoinLeaveMessage(player.name, false);
        }
    }


    @Inject(method = "method_1431", at = @At("HEAD"))
    private void onChat(class_340 packet, CallbackInfo ci) {
        if (!DiscordUnleashedConfig.discord_enable) return;

        String message = packet.field_1270;
        if (!message.startsWith("/")) {
            class_69 player = getPlayer((ServerPlayNetworkHandler) (Object) this);
            if (player != null) {
                DiscordChatRelay.sendToDiscord(player.name, message);
            }
        }
    }
}
