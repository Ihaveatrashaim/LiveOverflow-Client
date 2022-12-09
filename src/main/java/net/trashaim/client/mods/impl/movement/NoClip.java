package net.trashaim.client.mods.impl.movement;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.trashaim.client.mods.Category;
import net.trashaim.client.mods.Module;

public class NoClip extends Module {

	public NoClip() {
		this.setName("NoClip");
		this.category = Category.Movement;
	}

	
	
	@Override
	public void tick() {
		mc.player.setVelocity(mc.player.getVelocity().getX(), 0, mc.player.getVelocity().getZ());
		
		if(!mc.player.isOnGround()) {
			mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() - 1, mc.player.getZ(), false));
			mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + 1, mc.player.getZ(), false));
		}
	}
	
	@Override
	public void onDisable() {
	}
	
}
