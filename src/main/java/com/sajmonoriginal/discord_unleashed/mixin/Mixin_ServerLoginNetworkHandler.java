package com.sajmonoriginal.discord_unleashed.mixin;

import com.sajmonoriginal.discord_unleashed.server.DiscordChatRelay;
import net.minecraft.network.packet.login.LoginHelloPacket;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerLoginNetworkHandler.class, remap = false)
public class Mixin_ServerLoginNetworkHandler {

    @Inject(
            method = "method_419",
            at = @At("HEAD")
    )
    private void onLoginPacketReceived(LoginHelloPacket packet, CallbackInfo ci) {

        if (packet != null && packet.username != null) {
            String username = packet.username;

            new Thread(() -> {
                try {

                    Thread.sleep(500);
                    DiscordChatRelay.sendJoinLeaveMessage(username, true);
                } catch (Exception e) {

                }
            }).start();
        }
    }
}