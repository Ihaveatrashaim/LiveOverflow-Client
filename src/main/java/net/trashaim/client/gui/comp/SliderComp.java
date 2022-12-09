package net.trashaim.client.gui.comp;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.client.Mouse;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.trashaim.client.Client;
import net.trashaim.client.value.NumberValue;

public class SliderComp extends Comp {

	public NumberValue value;
	
	public Color color = new Color(26, 25, 28);
	
	private boolean changing = false;
	private boolean hovered;
	
	public SliderComp(NumberValue value, int x, int y) {
		this.value = value;
		this.x = x;
		this.y = y;
	}
	
	private void updateHovered(int x, int y, boolean offscreen) {
        hovered = !offscreen && x >= this.x && y >= this.y && x <= this.x + getWidth() && y <= this.y + getHeight();
    }
	
	private float getHeight() {
		return (this.height - 4);
	}

	private void updateValue(int x, int y) {
		float oldValue = value.getValue();
        float maxValue = value.max;
        float minValue = value.min;
        
        float newValue = (float) Math.max(Math.min((x - this.x) / (double) getWidth() * (maxValue - minValue) + minValue, maxValue), minValue);

        boolean change = true;

        if (change) {
            value.setValue(newValue);
        }

    }
	
	private double getWidth() {
		return this.width - 10;
	}

	@Override
	public void render(MatrixStack matrices, TextRenderer textRenderer, int mouseX, int mouseY, float delta) {
		updateHovered(mouseX, mouseY, false);
		
		float maxValue = value.max;
        float minValue = value.min;
		double sliderPos = (value.getValue() - minValue) / (maxValue - minValue) * (getWidth() - 4);
		
		drawRect(matrices, (float)x, (float)y, (float)x + (float) getWidth(), y + getHeight(), color.darker().darker().getRGB());
		drawRect(matrices, (float) (x + sliderPos), y, (float) (x + sliderPos + (float) 4), y + getHeight(), 
				Client.main_color.getRGB());
		
		textRenderer.drawWithShadow(matrices, value.getName() + " : " +  String.format("%.2f", value.getValue()), x + 18, y + (height / 2) - (textRenderer.fontHeight / 2) - 2, -1, true);
	}
	
	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if(button == 0 && hovered) {
			updateValue((int)mouseX, (int)mouseY);
		}
	}
	
	@Override
	public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if(button == 0 && hovered) {
			updateValue((int)mouseX, (int)mouseY);
		}
	}
	
	

}
