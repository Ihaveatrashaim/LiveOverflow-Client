package net.trashaim.client.mods.impl.movement;

import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.trashaim.client.event.EventPacket;
import net.trashaim.client.mods.Category;
import net.trashaim.client.mods.Module;

public class Blink extends Module {

	public Blink() {
		this.setName("Blink");
		this.category = Category.Movement;
	}

	@EventTarget
	public void onPacket(EventPacket event) {
		if(event.getPacket() instanceof PlayerMoveC2SPacket) {
			mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));
			event.setCancelled(true);
			
		}
	}
	
}
