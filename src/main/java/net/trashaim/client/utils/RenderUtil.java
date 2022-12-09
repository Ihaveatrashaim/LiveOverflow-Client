package net.trashaim.client.utils;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;
import net.trashaim.client.gui.OldGuiScreen;

public class RenderUtil {

	private static MinecraftClient mc = MinecraftClient.getInstance();
	
	public static void setGlColor(final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GlStateManager._clearColor(red, green, blue, alpha);
    }
	
	public static void drawDot(final float x, final float y, final float size, final int color) {
        GL11.glEnable(2848);
        GL11.glEnable(2832);
        setGlColor(color);
        GL11.glPointSize(size);
        GlStateManager._enableBlend();
        GlStateManager._disableTexture();
        GlStateManager._blendFunc(770, 771);
        GL11.glBegin(0);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GlStateManager._enableTexture();
        GlStateManager._disableBlend();
    }
    
	public static void circle(final float x, final float y, final float radius, final int fill) {
        arc(x, y, 0.0f, 360.0f, radius, fill);
    }

    public static void circle(float x, float y, float radius, Color fill) {
        arc(x, y, 0.0f, 360.0f, radius, fill);
    }
	
    public static void arc(final float x, final float y, final float start, final float end, final float radius,
            final int color) {
    	arcEllipse(x, y, start, end, radius, radius, color);
    }

    public static void arc(final float x, final float y, final float start, final float end, final float radius,
            final Color color) {
    	arcEllipse(x, y, start, end, radius, radius, color.getRGB());
    }

    public static void startSmooth() {
        GL11.glEnable((int)2848);
        GL11.glEnable((int)2881);
        GL11.glEnable((int)2832);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
        GL11.glHint((int)3153, (int)4354);
    }

    public static void endSmooth() {
        GL11.glDisable((int)2848);
        GL11.glDisable((int)2881);
        GL11.glEnable((int)2832);
    }
    
    public static void drawRoundedRect(MatrixStack matrices, float x, float y, float x2, float y2, final float round, final int color) {
        x += (float) (round / 2.0f + 0.5);
        y += (float) (round / 2.0f + 0.5);
        x2 -= (float) (round / 2.0f + 0.5);
        y2 -= (float) (round / 2.0f + 0.5);
        OldGuiScreen.drawRect(matrices, x, y, x2, y2, color);
        circle(x2 - round / 2.0f, y + round / 2.0f, round, color);
        circle(x + round / 2.0f, y2 - round / 2.0f, round, color);
        circle(x + round / 2.0f, y + round / 2.0f, round, color);
        circle(x2 - round / 2.0f, y2 - round / 2.0f, round, color);
        OldGuiScreen.drawRect(matrices, (x - round / 2.0f - 0.5f), (y + round / 2.0f), x2, (y2 - round / 2.0f),
                color);
        OldGuiScreen.drawRect(matrices, x, (y + round / 2.0f), (x2 + round / 2.0f + 0.5f), (y2 - round / 2.0f),
                color);
        OldGuiScreen.drawRect(matrices, (x + round / 2.0f), (y - round / 2.0f - 0.5f), (x2 - round / 2.0f),
                (y2 - round / 2.0f), color);
        OldGuiScreen.drawRect(matrices, (x + round / 2.0f), y, (x2 - round / 2.0f), (y2 + round / 2.0f + 0.5f),
                color);
    }
    
    public static void arcEllipse(final float x, final float y, float start, float end, final float w, final float h,final int color) {
    	GlStateManager._clearColor(0.0f, 0.0f, 0.0f, 0.0f);
    	float temp = 0.0f;
    	if (start > end) {
    		temp = end;
    		end = start;
    		start = temp;
    	}
    	final float var11 = (color >> 24 & 0xFF) / 255.0f;
    	final float var12 = (color >> 16 & 0xFF) / 255.0f;
    	final float var13 = (color >> 8 & 0xFF) / 255.0f;
    	final float var14 = (color & 0xFF) / 255.0f;
    	final Tessellator var15 = Tessellator.getInstance();
    	var15.getBuffer();
    	GlStateManager._enableBlend();
    	GlStateManager._disableTexture();
    	GlStateManager._blendFuncSeparate(770, 771, 1, 0);
    	GlStateManager._clearColor(var12, var13, var14, var11);
    	if (var11 > 0.5f) {
    		GL11.glEnable(2848);
    		GL11.glLineWidth(2.0f);
    		GL11.glBegin(3);
    		for (float i = end; i >= start; i -= 4.0f) {
    			final float ldx = (float) Math.cos(i * Math.PI / 180.0) * w * 1.001f;
    			final float ldy = (float) Math.sin(i * Math.PI / 180.0) * h * 1.001f;
    			GL11.glVertex2f(x + ldx, y + ldy);
    		}
    		GL11.glEnd();
    		GL11.glDisable(2848);
    	}
    	GL11.glBegin(6);
    	for (float i = end; i >= start; i -= 4.0f) {
    		final float ldx = (float) Math.cos(i * Math.PI / 180.0) * w;
    		final float ldy = (float) Math.sin(i * Math.PI / 180.0) * h;
    		GL11.glVertex2f(x + ldx, y + ldy);
    	}
    	GL11.glEnd();
    	GlStateManager._enableTexture();
    	GlStateManager._disableBlend();
    	}
	}
