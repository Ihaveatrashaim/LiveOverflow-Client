package net.trashaim.client.gui.comp;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import net.trashaim.client.Client;
import net.trashaim.client.gui.ModuleButton;

public class Comp {

	public int x,y;
	
	public float width = 102, height = 20;
	
	public void render(MatrixStack matrices, TextRenderer textRenderer, int mouseX, int mouseY, float delta) {}
	
	public void mouseClicked(double mouseX, double mouseY, int button) {}

    public void mouseReleased(double mouseX, double mouseY, int button) {}
	
	public void drawRect(MatrixStack matrices, float x2, float y2, float g, float h, int color) {
    	Matrix4f matrix = matrices.peek().getPositionMatrix();
		Tessellator tessellator = RenderSystem.renderThreadTesselator();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionShader);
		float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
		RenderSystem.setShaderColor(f, f1, f2,f3);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
		bufferBuilder.vertex(matrix, x2, y2, 0).next();
		bufferBuilder.vertex(matrix, x2, h, 0).next();
		bufferBuilder.vertex(matrix, g, h, 0).next();
		bufferBuilder.vertex(matrix, g, y2, 0).next();
		tessellator.draw();
    }
	
	public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
    }
	
	protected boolean isHover(double mouseX, double mouseY) {
        if(mouseX > x && mouseX < x + 102 && mouseY > y && mouseY < y + 16) {
            return true;
        }
        return false;
    }
}
