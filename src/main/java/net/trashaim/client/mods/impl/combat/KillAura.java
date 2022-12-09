package net.trashaim.client.mods.impl.combat;

import java.util.Comparator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.trashaim.client.mods.Category;
import net.trashaim.client.mods.Module;
import net.trashaim.client.value.NumberValue;

public class KillAura extends Module {

	public NumberValue reach = new NumberValue("Reach", 4.3f, 1, 6f);
	
	public KillAura() {
		this.setName("KillAura");
		this.category = Category.Combat;
	}
	
	@Override
	public void tick() {	
		float reach = this.reach.getValue();
		Object[] objects = mc.world.getPlayers().stream().filter(entity -> entity instanceof LivingEntity && entity != mc.player && entity.getHealth() > 0F && entity.distanceTo(mc.player) <= reach).sorted(Comparator.comparingDouble(entity -> entity.distanceTo(mc.player))).toArray();

		if(objects.length <= 0) return;
		
		LivingEntity target = (LivingEntity) objects[0];
		if(mc.player.getAttackCooldownProgress(0) == 1) {
			mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(target, false));
			mc.player.swingHand(Hand.MAIN_HAND);
		}
	}
	

	@Override
	public void onDisable() {
		mc.player.getAbilities().allowFlying = false;
		mc.player.getAbilities().flying = false;
	}
	
}
