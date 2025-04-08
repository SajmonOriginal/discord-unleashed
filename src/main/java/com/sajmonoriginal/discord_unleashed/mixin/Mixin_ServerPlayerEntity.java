package com.sajmonoriginal.discord_unleashed.mixin;

import com.sajmonoriginal.discord_unleashed.config.DiscordUnleashedConfig;
import com.sajmonoriginal.discord_unleashed.server.DiscordChatRelay;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(value = net.minecraft.class_69.class, remap = false)
public class Mixin_ServerPlayerEntity {

    @Inject(method = "method_308", at = @At("HEAD"))
    private void onPlayerLeave(CallbackInfo ci) {
        if (DiscordUnleashedConfig.discord_enable) {
            String name = getName((PlayerEntity)(Object)this);
            DiscordChatRelay.sendJoinLeaveMessage(name, false);
        }
    }

    private String getName(PlayerEntity player) {
        try {
            for (Field field : PlayerEntity.class.getDeclaredFields()) {
                if (field.getType() == String.class) {
                    field.setAccessible(true);
                    return (String) field.get(player);
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting name reflectively: " + e.getMessage());
        }
        return "unknown";
    }
}
