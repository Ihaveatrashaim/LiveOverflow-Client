package net.trashaim.client.gui;

import java.awt.Color;
import java.util.ArrayList;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import net.trashaim.client.Client;
import net.trashaim.client.mods.Category;

public class Panel {

    public Category category;

    public int x;
    public int y;
    public double dragX;
    public double dragY;
    public boolean expanded, drag;
    public TextRenderer textRenderer;
    public Screen screen;
    
    public Color color = new Color(28, 40, 51).darker();
    
    public ArrayList<ModuleButton> mods;
    
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
    	if(expanded) {
    		for(ModuleButton m : mods) {
    			m.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    		}
    	}
    }
    
    public Panel(Category category, TextRenderer textRenderer, Screen screen ,int x, int y) {
        this.category = category;
        this.textRenderer = textRenderer;
        this.screen = screen;
        this.x = x;
        this.y = y;
        
        mods = new ArrayList<>();
        
        int yL = y + 16;
        for(net.trashaim.client.mods.Module m : Client.instance.moduleManager.getModsByCategory(category)) {
        	mods.add(new ModuleButton(m, x, yL));
        	yL += 16;
        }
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
    
    public void render(MatrixStack matrices, TextRenderer textRenderer, int mouseX, int mouseY, float delta) {
    	
    	
    	if(expanded) {
    		int yL = y + 16;
    		for(ModuleButton m : mods) {
    			m.render(matrices, textRenderer, mouseX, mouseY, delta);
    			m.y = yL;
    			m.x = x;
    			
    			if(m.expanded) {
    				yL += (16 + (m.module.getValues().size() * 20));
    			}else {
    				yL += 16;
    			}
    		}
    	}
    	
        if(drag) {
            x = (int) (mouseX - dragX);
            y = (int) (mouseY - dragY);
        }

    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
    	if(expanded) {
    		for(ModuleButton m : mods) {
    			m.mouseClicked(mouseX, mouseY, button);
    		}
    	}
    	
        if(isHover(mouseX, mouseY) && button == 0) {
            drag = true;
            dragX = mouseX - x;
            dragY = mouseY - y;
    		int yL = y + 16;
            for(ModuleButton m : mods) {
    			m.y = yL;
    			m.x = x;
    			
    			if(m.expanded) {
					yL += 16 + (m.module.getValues().size() * 20);
				}else {
					yL += 16;
				}
    		}
        }else if(isHover(mouseX, mouseY) && button == 1) {
        	if(Client.instance.moduleManager.getModsByCategory(category).isEmpty()) return;
        	
            expanded = !expanded;
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        drag = false;
    }

    private boolean isHover(double mouseX, double mouseY) {
        if(mouseX > x && mouseX < x + 102 && mouseY > y && mouseY < y + 16) {
            return true;
        }
        return false;
    }



}
