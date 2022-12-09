package net.trashaim.client.mods.impl.render;

import net.minecraft.entity.Entity;
import net.trashaim.client.mods.Category;
import net.trashaim.client.mods.Module;

public class ShaderESP extends Module {

	public ShaderESP() {
		this.setName("ShaderESP");
		this.category = Category.Render;
	}
	
	@Override
	public void tick() {
		for(Entity e : mc.world.getEntities()) {
			
			e.setGlowing(true);
		}
	}

	
	@Override
	public void onDisable() {
		for(Entity e : mc.world.getEntities()) {
			e.setGlowing(false);
		}
	}
	
}
