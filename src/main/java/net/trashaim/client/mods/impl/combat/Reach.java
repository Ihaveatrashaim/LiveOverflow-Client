package net.trashaim.client.mods.impl.combat;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.trashaim.client.mods.Category;
import net.trashaim.client.mods.Module;
import net.trashaim.client.value.NumberValue;

public class Reach extends Module {

	public NumberValue reach = new NumberValue("Reach", 10, 1, 5000);
	
	public Reach() {
		this.setName("Reach");
		this.category = Category.Combat;
	}
	
	@SuppressWarnings("resource")
	public Entity getEntityInSight(double reach) {
		float tickDelta = 1f;
		Entity target = null;
        Entity entity = MinecraftClient.getInstance().getCameraEntity();
        if (entity != null) {
            if (MinecraftClient.getInstance().world != null) {
                double d = reach;
                MinecraftClient.getInstance().crosshairTarget = entity.raycast(d, tickDelta, false);
                Vec3d vec3d = entity.getCameraPosVec(tickDelta);
                double e = d;

                e *= e;
                if (MinecraftClient.getInstance().crosshairTarget != null) {
                    e = MinecraftClient.getInstance().crosshairTarget.getPos().squaredDistanceTo(vec3d);
                }

                Vec3d vec3d2 = entity.getRotationVec(1.0F);
                Vec3d vec3d3 = vec3d.add(vec3d2.x * d, vec3d2.y * d, vec3d2.z * d);
                Box box = entity.getBoundingBox().stretch(vec3d2.multiply(d)).expand(1.0, 1.0, 1.0);
                EntityHitResult entityHitResult = ProjectileUtil.raycast(entity, vec3d, vec3d3, box, (entityx) -> {
                    return !entityx.isSpectator() && entityx.canHit();
                }, e);
                if (entityHitResult != null) {
                    Entity entity2 = entityHitResult.getEntity();
                    if (entity2 instanceof LivingEntity || entity2 instanceof ItemFrameEntity) {
                    	target = entity2;
                    }
                }
            }
        }
        
        return target;
    }
	
	@Override
	public void tick() {
		if(getEntityInSight(reach.getValue()) == null) return;
		
		LivingEntity target = (LivingEntity) getEntityInSight(reach.getValue());
		
		if(mc.options.attackKey.wasPressed()) {
			Vec3d playerPos = mc.player.getPos();
			Vec3d targetPos = target.getPos();
			
			teleportFromTo(playerPos, targetPos);
			
			mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(target, false));
			mc.player.swingHand(Hand.MAIN_HAND);
			
			teleportFromTo(targetPos, playerPos);
			mc.player.setPosition(playerPos);
		}
	}
	
	public void teleportFromTo(Vec3d from, Vec3d to) {
		double d = 6.5;
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
