package net.trashaim.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

@Mixin(PlayerMoveC2SPacket.PositionAndOnGround.class)

public abstract class PositionPacketMixin {
    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket;<init>(DDDFFZZZ)V"))
    private static void init(Args args) {
    	double i1 = Math.round(((double)args.get(0)) * 100) / 100d;
    	double i2 = Math.round(((double)args.get(2)) * 100) / 100d;
        args.set(0, Math.nextAfter(i1, i1 + Math.signum(i1)));
        args.set(2, Math.nextAfter(i2, i2 + Math.signum(i2)));
    }

}