package net.trashaim.client.gui.comp;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.trashaim.client.Client;
import net.trashaim.client.value.BooleanValue;


public class BooleanComp extends Comp {

	public BooleanValue value;
	
	public Color color = new Color(26, 25, 28);
	
	public BooleanComp(BooleanValue value, int x, int y) {
		this.value = value;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void render(MatrixStack matrices, TextRenderer textRenderer, int mouseX, int mouseY, float delta) {
		int buttonX = x + 5;
		int buttonY = y + 4;
		
		boolean toggled = (boolean) value.getObject();
		
		drawRect(matrices, buttonX, buttonY, buttonX + 12, buttonY + 12, toggled ? Client.main_color.getRGB() : color.darker().darker().getRGB());
		
		textRenderer.drawWithShadow(matrices, value.getName(), x + 22, y + (height / 2) - (textRenderer.fontHeight / 2) + 1, -1, true);
	}

	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		int buttonX = x + 4;
		int buttonY = y + 2;
		
		if(isHover(mouseX, mouseY)) {
			if(button == 0) {
				boolean toggled = (boolean) value.getObject();
				
				value.setObject(!toggled);
			}
			
		}
		
	}

}
