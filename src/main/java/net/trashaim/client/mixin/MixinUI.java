package net.trashaim.client.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.trashaim.client.Client;
import net.trashaim.client.gui.HUD;
import net.trashaim.client.mods.Module;

@Mixin(InGameHud.class)
public class MixinUI {

	@Shadow @Final private MinecraftClient client;
	
	@SuppressWarnings({ "resource", "static-access" })
	@Inject(
			at = @At(value = "INVOKE",
				target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V",
				remap = false,
				ordinal = 4),
			method = {"render(Lnet/minecraft/client/util/math/MatrixStack;F)V"})
    private void onRender(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
		int width = MinecraftClient.getInstance().getWindow().getWidth();
		if(MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().world != null) {
			int y = 2;
            for(Module m : Client.instance.moduleManager.modules) {
                if(m.isToggle()) {
                	MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, m.getName(), width - MinecraftClient.getInstance().textRenderer.getWidth(m.getName()) , y, Client.instance.main_color.getRGB(), true);
                    y += MinecraftClient.getInstance().textRenderer.fontHeight;
                }
                
            }
        }
	}
	
}
