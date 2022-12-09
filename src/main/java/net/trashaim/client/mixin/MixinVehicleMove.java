package net.trashaim.client.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;

@Mixin(VehicleMoveC2SPacket.class)
public class MixinVehicleMove {

	@Redirect(method = "<init>(Lnet/minecraft/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getX()D"))
    public double getX(Entity instance)
    {
		double i1 = Math.round(((double)instance.getX()) * 100) / 100d;    	
    	return Math.nextAfter(i1, i1 + Math.signum(i1));
    }
	
    @Redirect(method = "<init>(Lnet/minecraft/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getZ()D"))
    public double getZ(Entity instance) {
    	double i1 = Math.round(((double)instance.getZ()) * 100) / 100d;    	
    	return Math.nextAfter(i1, i1 + Math.signum(i1));
    }
}
