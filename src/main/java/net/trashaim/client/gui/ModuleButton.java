package net.trashaim.client.gui;

import java.awt.Color;
import java.util.ArrayList;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import net.trashaim.client.mods.Module;
import net.trashaim.client.value.BooleanValue;
import net.trashaim.client.value.ModeValue;
import net.trashaim.client.value.NumberValue;
import net.trashaim.client.value.Value;
import net.trashaim.client.Client;
import net.trashaim.client.gui.comp.*;

public class ModuleButton {

    public Module module;
    public boolean expanded;

    public int x;
    public int y;
    
    public Color color = new Color(28, 40, 51).darker().darker();
    
    public ArrayList<Comp> comps;
    
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
    	if(expanded) {
    		for(Comp c : comps) {
    			c.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    		}
    	}
    }
    
    public ModuleButton(Module module, int x, int y) {
        this.module = module;
        this.x = x;
        this.y = y;
        
        comps = new ArrayList<Comp>();
		
		int yL = (int) (this.y + 16);
		for(Value value : module.getValues()) {
			if(value instanceof ModeValue) {
				comps.add(new ModeComp((ModeValue) value, this.x, yL));
			}
			if(value instanceof BooleanValue) {
				comps.add(new BooleanComp((BooleanValue) value, this.x, yL));
			}
			if(value instanceof NumberValue) {
				comps.add(new SliderComp((NumberValue) value, this.x + 5, yL + 5));
			}
			
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
    	this.drawRect(matrices, x, y, x + 102, y + 16, module.isToggle() ? Client.main_color.getRGB() : new Color(26, 25, 28).getRGB());
    	textRenderer.drawWithShadow(matrices, module.getName(), x + 5, y + (16 / 2) - (textRenderer.fontHeight / 2), -1, true);
    	
    	if(expanded) {
    		drawRect(matrices, x, y + 16, x + 102, y + 16 + (module.getValues().size() * 20), color.darker().getRGB());
    		
    		int yL = (int) (this.y + 16);

			for(Comp c : comps) {
				c.render(matrices, textRenderer, mouseX, mouseY, delta);
				
				if(c instanceof SliderComp) {
					c.x = x + 5;
					c.y = yL + 2 ;
				}else {
					c.x = x;
					c.y = yL;
				}
				
				
				yL += 20;
			}
    	}
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
    	if(expanded) {
    		for(Comp c : comps) {
    			c.mouseClicked(mouseX, mouseY, button);
    		}
    	}
    	
        if(isHover(mouseX, mouseY) && button == 0) {
            this.module.setToggle(!module.toggle);
        }
        
        if(isHover(mouseX, mouseY) && button == 1) {
            if(module.getValues().isEmpty()) return;
            
            expanded = !expanded;
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
    	if(expanded) {
    		for(Comp c : comps) {
    			c.mouseReleased(mouseX, mouseY, button);
    		}
    	}
    }

    private boolean isHover(double mouseX, double mouseY) {
        if(mouseX > x && mouseX < x + 102 && mouseY > y && mouseY < y + 16) {
            return true;
        }
        return false;
    }



}
