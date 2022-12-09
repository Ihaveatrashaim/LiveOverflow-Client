package net.trashaim.client.gui.comp;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.trashaim.client.value.ModeValue;

public class ModeComp extends Comp {

	public ModeValue value;
	
	public Color color = new Color(26, 25, 28);
	
	public ModeComp(ModeValue value, int x, int y) {
		this.value = value;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void render(MatrixStack matrices, TextRenderer textRenderer, int mouseX, int mouseY, float delta) {
		if(!value.modes.contains(value.getMode())) {
			value.setMode(value.modes.get(0));
		}
		
		textRenderer.drawWithShadow(matrices, value.getName() + " : " + value.getMode(), x + 3, y + (height / 2) - (textRenderer.fontHeight / 2) + 1, -1, true);

	}
	
	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if(isHover(mouseX, mouseY)) {
			
			if(button == 0) {
				for(int i = 0; i < value.modes.size(); i++) {
					if(value.modes.get(i).equalsIgnoreCase(value.getMode())) {
						try {
							int newValue = i + 1;
							value.setMode(value.modes.get(newValue));
						}catch(Exception e) {
							value.setMode(value.modes.get(0));
						}
						break;
					}
				}
			}
			
			if(button == 1) {
				for(int i = 0; i < value.modes.size(); i++) {
					if(value.modes.get(i).equalsIgnoreCase(value.getMode())) {
						try {
							int newValue = i - 1;
							value.setMode(value.modes.get(newValue));
						}catch(Exception e) {
							value.setMode(value.modes.get(value.modes.size() - 1));
						}
						break;
					}
				}
			}
			
		}
		
	}


}
