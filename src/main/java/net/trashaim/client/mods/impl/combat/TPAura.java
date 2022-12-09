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

public class TPAura extends Module {

	public NumberValue reach = new NumberValue("Reach", 1000, 1, 5000);
	
	public TPAura() {
		this.setName("TPAura");
		this.category = Category.Combat;
	}
	
	@Override
	public void tick() {	
		float reach = this.reach.getValue();
		Object[] objects = mc.world.getPlayers().stream().filter(entity -> entity instanceof LivingEntity && entity != mc.player && entity.getHealth() > 0F && entity.distanceTo(mc.player) <= reach).sorted(Comparator.comparingDouble(entity -> entity.distanceTo(mc.player))).toArray();

		if(objects.length <= 0) return;
		
		LivingEntity target = (LivingEntity) objects[0];
		
		Vec3d playerPos = mc.player.getPos();
		Vec3d targetPos = target.getPos();
		
		if(mc.player.getAttackCooldownProgress(0) == 1) {
			teleportFromTo(playerPos, targetPos);
			
			mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(target, false));
			mc.player.swingHand(Hand.MAIN_HAND);
			
			teleportFromTo(targetPos, playerPos);
			mc.player.setPosition(playerPos);
		}
	}
	
	public void teleportFromTo(Vec3d from, Vec3d to) {
		double d = 5.5;
		double td = Math.ceil(from.distanceTo(to) / d);
		
		for(int i = 1; i <= td; i++) {
			Vec3d tempPos = from.lerp(to, i / td);
			
			mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(tempPos.getX(), tempPos.getY(), tempPos.getZ(), mc.player.isOnGround()));
			
			if(i % 4 == 0) {
				try {
					Thread.sleep((long)((1 / 20) * 100));
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void onDisable() {
		mc.player.getAbilities().allowFlying = false;
		mc.player.getAbilities().flying = false;
	}
	
}
