package net.trashaim.client.mods.impl.movement;

import net.minecraft.util.math.Vec3d;
import net.trashaim.client.mods.Category;
import net.trashaim.client.mods.Module;
import net.trashaim.client.value.ModeValue;

public class Speed extends Module {

	public ModeValue modes = new ModeValue("Mode", "OnGround", "Wurst");
	
	public Speed() {
		this.setName("Speed");
		this.category = Category.Movement;
	}

	
	
	@Override
	public void tick() {
		if(mc.player.input.movementForward == 0 && mc.player.input.movementSideways == 0) return;
		
		if(modes.getMode().equalsIgnoreCase("OnGround")) {
			mc.player.jump();
			mc.player.addVelocity(0, -1, 0);
		}else {
			if(mc.player.isSneaking()
					|| mc.player.forwardSpeed == 0 && mc.player.sidewaysSpeed == 0)
					return;
				
				// activate sprint if walking forward
				if(mc.player.forwardSpeed > 0 && !mc.player.horizontalCollision)
					mc.player.setSprinting(true);
				
				// activate mini jump if on ground
				if(!mc.player.isOnGround())
					return;
				
				Vec3d v = mc.player.getVelocity();
				mc.player.setVelocity(v.x * 1.8, v.y + 0.1, v.z * 1.8);
				
				v = mc.player.getVelocity();
				double currentSpeed = Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.z, 2));
				
				// limit speed to highest value that works on NoCheat+ version
				// 3.13.0-BETA-sMD5NET-b878
				// UPDATE: Patched in NoCheat+ version 3.13.2-SNAPSHOT-sMD5NET-b888
				double maxSpeed = 0.66F;
				
				if(currentSpeed > maxSpeed)
					mc.player.setVelocity(v.x / currentSpeed * maxSpeed, v.y,
						v.z / currentSpeed * maxSpeed);
		}
	}
	
	@Override
	public void onDisable() {
	}
	
}
