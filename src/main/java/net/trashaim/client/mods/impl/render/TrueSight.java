package net.trashaim.client.mods.impl.render;

import net.minecraft.entity.Entity;
import net.trashaim.client.mods.Category;
import net.trashaim.client.mods.Module;

public class TrueSight extends Module {

	public TrueSight() {
		this.setName("TrueSight");
		this.category = Category.Render;
	}
	
	@Override
	public void tick() {
		for(Entity e : mc.world.getEntities()) {
			e.setInvisible(false);
		}
	}

	
	@Override
	public void onDisable() {
	}
	
}
