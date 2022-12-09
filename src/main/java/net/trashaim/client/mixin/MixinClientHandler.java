package net.trashaim.client.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderInitializeS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientHandler {

    @Inject(at = @At("HEAD"), method = "onGameStateChange", cancellable = true)
    public void onGameStateChange(GameStateChangeS2CPacket packet, CallbackInfo ci) {
        GameStateChangeS2CPacket.Reason reason = packet.getReason();
        if(reason == GameStateChangeS2CPacket.GAME_WON) ci.cancel();
        if(reason == GameStateChangeS2CPacket.DEMO_MESSAGE_SHOWN) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "onWorldBorderInitialize", cancellable = true)
    public void onWorldBorderInitialize(WorldBorderInitializeS2CPacket packet, CallbackInfo ci) {
        ci.cancel();
    }
}
