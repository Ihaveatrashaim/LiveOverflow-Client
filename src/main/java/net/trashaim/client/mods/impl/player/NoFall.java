package net.trashaim.client.mods.impl.player;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.trashaim.client.mods.Category;
import net.trashaim.client.mods.Module;

public class NoFall extends Module {

	public NoFall() {
		this.setName("NoFall");
		this.category = Category.Player;
	}

	@Override
	public void tick() {
		mc.player.fallDistance = 0;
		mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), true));
	}
	
	@Override
	public void onDisable() {
		
	}
	
}
