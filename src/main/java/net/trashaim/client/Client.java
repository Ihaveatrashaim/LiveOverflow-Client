package net.trashaim.client;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.trashaim.client.gui.ClickGUI;
import net.trashaim.client.mods.FileManager;
import net.trashaim.client.mods.ModuleManager;
import java.awt.Color;
import java.io.IOException;

import org.lwjgl.glfw.GLFW;

public class Client implements ModInitializer {

	public static Client instance;
	
	public ModuleManager moduleManager;

	public static KeyBinding keyClickGui;

	public static Color main_color = new Color(128, 69, 248);
	
	@SuppressWarnings("static-access")
	public Client() {
		this.instance = this;
	}

	public ClickGUI clickGUI;

	@Override
	public void onInitialize() {
		moduleManager = new ModuleManager();

		clickGUI = new ClickGUI(Text.literal("ClickGUI"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (keyClickGui.wasPressed()) {
				client.setScreen(clickGUI);
			}
			
		});

		keyClickGui = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"ClickGui", 
				InputUtil.Type.KEYSYM, 
				GLFW.GLFW_KEY_RIGHT_SHIFT, 
				"Client"
		));
		
		try {
			new FileManager().loadModules();
		} catch (IOException e) {
		};

	}
	
	
}
