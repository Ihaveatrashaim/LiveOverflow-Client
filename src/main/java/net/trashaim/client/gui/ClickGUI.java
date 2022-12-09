package net.trashaim.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import net.trashaim.client.mods.Category;

import java.awt.Color;
import java.util.ArrayList;

import com.mojang.blaze3d.systems.RenderSystem;

public class ClickGUI extends Screen {

    public ArrayList<Panel> panels;

    @SuppressWarnings("resource")
	public ClickGUI(Text title) {
        super(title);

        panels = new ArrayList<>();

        int x = 10;

        for(Category category : Category.values()) {
            panels.add(new Panel(category, MinecraftClient.getInstance().textRenderer, this, x, 20));

            x += 120;
        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
    	for(Panel panel : panels) {
            panel.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    	return false;
    }



    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for(Panel panel : panels) {
            panel.mouseClicked(mouseX, mouseY, button);
        }
        
        

        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for(Panel panel : panels) {
            panel.mouseReleased(mouseX, mouseY, button);
        }

        return false;
    }

    public void drawRect(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
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
		bufferBuilder.vertex(matrix, x1, y1, 0).next();
		bufferBuilder.vertex(matrix, x1, y2, 0).next();
		bufferBuilder.vertex(matrix, x2, y2, 0).next();
		bufferBuilder.vertex(matrix, x2, y1, 0).next();
		tessellator.draw();
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        for(Panel panel : panels) {
        	panel.render(matrices, textRenderer, mouseX, mouseY, delta);
        	this.drawRect(matrices, panel.x, panel.y, panel.x + 102, panel.y + 16, panel.color.getRGB());
        	this.textRenderer.drawWithShadow(matrices, panel.category.name(), panel.x + 5, panel.y + (16 / 2) - (this.textRenderer.fontHeight / 2), -1, true);
        }
    }
}
