package net.trashaim.client.mods.impl.movement;

import java.util.ArrayList;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.trashaim.client.mods.Category;
import net.trashaim.client.mods.Module;
public class MoveClip extends Module {
	
	public MoveClip() {
		this.setName("MoveClip");
		this.category = Category.Movement;
	}
	
	public static boolean inSameBlock(Vec3d vector, Vec3d other) {
        return other.x >= Math.floor(vector.x) && other.x <= Math.ceil(vector.x) &&
                other.y >= Math.floor(vector.y) && other.y <= Math.ceil(vector.y) &&
                other.z >= Math.floor(vector.z) && other.z <= Math.ceil(vector.z);
    }
	
	public void handleMove(Vec3d vec) {
		mc.player.setPosition(vec);
		mc.player.setPosition(mc.player.getX() + 2000, mc.player.getY() + 2000, mc.player.getZ() + 2000);

	}
	
    public static int flyingTimer = 0;
	
    @Override
    public void onEnable() {
    	mc.player.jump();
    }
    
	public Vec3d getMovementVec() {
        if (mc.player == null) return null;

        Vec3d vec = new Vec3d(0, 0, 0);

        // Key presses changing position
        if (mc.player.input.jumping) {  // Move up
            vec = vec.add(new Vec3d(0, 0.01, 0));
        } else if (mc.player.input.sneaking) {  // Move down
            vec = vec.add(new Vec3d(0, -0.01, 0));
        } else {
            // Horizontal movement (not at the same time as vertical)
            if (mc.player.input.pressingForward) {
                vec = vec.add(new Vec3d(0, 0, 0.06));
            }
            if (mc.player.input.pressingRight) {
                vec = vec.add(new Vec3d(0.06, 0, 0));
            }
            if (mc.player.input.pressingBack) {
                vec = vec.add(new Vec3d(0, 0, -0.06));
            }
            if (mc.player.input.pressingLeft) {
                vec = vec.add(new Vec3d(-0.06, 0, 0));
            }
        }

        return vec.normalize();
    }
	
	public static double MAX_DELTA = 0.006;
	
	public boolean collide(Vec3d pos) {
        if (mc.player == null || mc.world == null) return false;

        return !mc.world.isSpaceEmpty(mc.player, mc.player.getBoundingBox().offset(pos.subtract(mc.player.getPos())));
    }
	
	@Override
	public void tick() {

	}
	
	@Override
	public void onDisable() {
		
	}
	
}
