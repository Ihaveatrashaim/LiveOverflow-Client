package net.trashaim.client.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.darkmagician6.eventapi.EventManager;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.trashaim.client.event.EventRender3D;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

	@Inject(
			at = {@At(value = "FIELD",
				target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
				opcode = Opcodes.GETFIELD,
				ordinal = 0)},
			method = {
				"renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V"})
	public void renderWorld(float partialTicks, long finishTimeNano, MatrixStack matrixStack, CallbackInfo ci) {
		EventManager.call(new EventRender3D(matrixStack));
	}
}
