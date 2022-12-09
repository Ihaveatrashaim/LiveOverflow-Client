package net.trashaim.client.mods.impl.movement;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.trashaim.client.mods.Category;
import net.trashaim.client.mods.Module;
import net.trashaim.client.value.BooleanValue;
import net.trashaim.client.value.ModeValue;
import net.trashaim.client.value.NumberValue;

public class Flight extends Module {

	public ModeValue modes = new ModeValue("Mode", "Creative", "Motion", "NoStayY");
	public NumberValue hSpeed = new NumberValue("H Speed", 1, 1, 8);
	public NumberValue vSpeed = new NumberValue("V Speed", 1, 1, 8);
	public BooleanValue flyBypass = new BooleanValue("Fly Bypass", true);
	
	public Flight() {
		this.setName("Flight");
		this.category = Category.Movement;
	}

	public static int flyingTimer = 0;

	public static double MAX_DELTA = 0.05;
	
	@Override
	public void tick() {
		
		
		
		if(modes.getMode().equalsIgnoreCase("Motion")) {
			if(!mc.player.isOnGround()) {
				mc.player.setVelocity(0,0,0);
			}
			
			if((boolean) flyBypass.getObject()) {
				if (++flyingTimer > 20) { 
		            Vec3d pos = mc.player.getPos();
		            pos = pos.add(0, -MAX_DELTA, 0); 

		            mc.player.setPosition(pos.x, pos.y, pos.z);
		            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(pos.x, pos.y, pos.z, true));
		            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(pos.x, pos.y + 1337.0, 
		                    pos.z + 1337.0, mc.player.getYaw(), mc.player.getPitch(), true));

		            flyingTimer = 0; 
		        }
			}
			
			mc.player.airStrafingSpeed = hSpeed.getValue();
			
			Vec3d velocity = mc.player.getVelocity();
			
			if(mc.options.jumpKey.isPressed())
				mc.player.setVelocity(velocity.x, vSpeed.getValue(),
					velocity.z);
			
			if(mc.options.sneakKey.isPressed())
			{
				mc.player.airStrafingSpeed =
					Math.min(2, 0.85F);
				mc.player.setVelocity(velocity.x, -vSpeed.getValue(),
					velocity.z);
			}
		}else if(modes.getMode().equalsIgnoreCase("Creative")) {
			mc.player.getAbilities().allowFlying = true;
			if(mc.player.getAbilities().flying) {
				Vec3d velocity = mc.player.getVelocity();
				if(mc.options.jumpKey.isPressed())
					mc.player.setVelocity(velocity.x, vSpeed.getValue(),
						velocity.z);
				
				if(mc.options.sneakKey.isPressed())
				{
					mc.player.airStrafingSpeed =
						Math.min(2, 0.85F);
					mc.player.setVelocity(velocity.x, -vSpeed.getValue(),
						velocity.z);
				}
			}
		}else if(modes.getMode().equalsIgnoreCase("NoStayY")) {
			mc.player.airStrafingSpeed = hSpeed.getValue();
			Vec3d velocity = mc.player.getVelocity();
			
			if(mc.options.jumpKey.isPressed())
				mc.player.setVelocity(velocity.x, vSpeed.getValue(),
					velocity.z);
			
			if(mc.options.sneakKey.isPressed())
			{
				mc.player.airStrafingSpeed =
					Math.min(2, 0.85F);
				mc.player.setVelocity(velocity.x, -vSpeed.getValue(),
					velocity.z);
			}
		}
	}
	
	@Override
	public void onDisable() {
		mc.player.getAbilities().allowFlying = false;
		mc.player.getAbilities().flying = false;
	}
	
}
