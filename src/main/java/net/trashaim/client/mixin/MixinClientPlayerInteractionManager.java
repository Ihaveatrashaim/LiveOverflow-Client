package net.trashaim.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.trashaim.client.mixinI.IClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class MixinClientPlayerInteractionManager implements IClientPlayerInteractionManager{
	
	@Shadow
	private MinecraftClient client;
	@Shadow
	private float currentBreakingProgress;
	@Shadow
	private boolean breakingBlock;
	@Shadow
	private int blockBreakingCooldown;

	@Inject(at = {@At("HEAD")},
		method = {"getReachDistance()F"},
		cancellable = true)
	private void onGetReachDistance(CallbackInfoReturnable<Float> ci)
	{
		
	}
	
	@Inject(at = {@At("HEAD")},
		method = {"hasExtendedReach()Z"},
		cancellable = true)
	private void hasExtendedReach(CallbackInfoReturnable<Boolean> cir)
	{
		
	}
	
	
	@Override
	public float getCurrentBreakingProgress()
	{
		return currentBreakingProgress;
	}
	
	@Override
	public void setBreakingBlock(boolean breakingBlock)
	{
		this.breakingBlock = breakingBlock;
	}
	
	@Override
	public void windowClick_PICKUP(int slot)
	{
		clickSlot(0, slot, 0, SlotActionType.PICKUP, client.player);
	}
	
	@Override
	public void windowClick_QUICK_MOVE(int slot)
	{
		clickSlot(0, slot, 0, SlotActionType.QUICK_MOVE, client.player);
	}
	
	@Override
	public void windowClick_THROW(int slot)
	{
		clickSlot(0, slot, 1, SlotActionType.THROW, client.player);
	}
	
	@Override
	public void windowClick_SWAP(int from, int to)
	{
		clickSlot(0, from, to, SlotActionType.SWAP, client.player);
	}
	
	@Override
	public void rightClickItem()
	{
		interactItem(client.player, Hand.MAIN_HAND);
	}
	
	@Override
	public void rightClickBlock(BlockPos pos, Direction side, Vec3d hitVec)
	{
		interactBlock(client.player, Hand.MAIN_HAND,
			new BlockHitResult(hitVec, side, pos, false));
		interactItem(client.player, Hand.MAIN_HAND);
	}
	
	@Override
	public void sendPlayerActionC2SPacket(Action action, BlockPos blockPos,
		Direction direction)
	{
		sendSequencedPacket(client.world,
			i -> new PlayerActionC2SPacket(action, blockPos, direction, i));
	}
	
	@Override
	public void sendPlayerInteractBlockPacket(Hand hand,
		BlockHitResult blockHitResult)
	{
		sendSequencedPacket(client.world,
			i -> new PlayerInteractBlockC2SPacket(hand, blockHitResult, i));
	}
	
	@Override
	public void setBlockHitDelay(int delay)
	{
		blockBreakingCooldown = delay;
	}
	
	@Shadow
	private void sendSequencedPacket(ClientWorld world,
		SequencedPacketCreator packetCreator)
	{
		
	}
	
	@Shadow
	public abstract ActionResult interactBlock(
		ClientPlayerEntity clientPlayerEntity_1, Hand hand_1,
		BlockHitResult blockHitResult_1);
	
	@Shadow
	public abstract ActionResult interactItem(PlayerEntity playerEntity_1,
		Hand hand_1);
	
	@Shadow
	public abstract void clickSlot(int syncId, int slotId, int clickData,
		SlotActionType actionType, PlayerEntity playerEntity);

}
