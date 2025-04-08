package com.sajmonoriginal.discord_unleashed.mixin;

import com.sajmonoriginal.discord_unleashed.util.ServerHelper;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MinecraftServer.class, remap = false)
public class MixinMinecraftServer {

    @Inject(method = "method_2166", at = @At("HEAD"))
    private void onServerInit(CallbackInfoReturnable<Boolean> cir) {
        ServerHelper.setServer((MinecraftServer)(Object)this);
    }
}
