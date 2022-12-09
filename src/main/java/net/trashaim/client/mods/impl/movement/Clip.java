package net.trashaim.client.mods.impl.movement;

import net.trashaim.client.mods.Category;
import net.trashaim.client.mods.Module;

public class Clip extends Module {

	public Clip() {
		this.setName("Clip");
		this.category = Category.Movement;
	}
	
	@Override
	public void tick() {
		mc.player.setPosition(mc.player.getX(), mc.player.getY() + 3, mc.player.getZ());
		this.setToggle(false);
	}
	
}
