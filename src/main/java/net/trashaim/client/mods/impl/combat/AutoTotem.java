package net.trashaim.client.mods.impl.combat;

import java.util.Comparator;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.trashaim.client.mods.Category;
import net.trashaim.client.mods.Module;

public class AutoTotem extends Module {

	private int nextTickSlot;
	private int totems;
	private int timer;
	private boolean wasTotemInOffhand;
	
	public AutoTotem() {
		this.setName("AutoTotem");
		this.category = Category.Combat;
	}
	
	@Override
	public void tick() {	
		finishMovingTotem();
		
		PlayerInventory inventory = mc.player.getInventory();
		int nextTotemSlot = searchForTotems(inventory);
		
		ItemStack offhandStack = inventory.getStack(40);
		if(isTotem(offhandStack))
		{
			totems++;
			wasTotemInOffhand = true;
			return;
		}
		
		if(wasTotemInOffhand)
		{
			timer = 10;
			wasTotemInOffhand = false;
		}

		if(mc.currentScreen instanceof HandledScreen
			&& !(mc.currentScreen instanceof AbstractInventoryScreen))
			return;
		
		if(nextTotemSlot == -1)
			return;
		
		if(timer > 0)
		{
			timer--;
			return;
		}
		
		moveTotem(nextTotemSlot, offhandStack);
	}
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		nextTickSlot = -1;
		totems = 0;
		timer = 0;
		wasTotemInOffhand = false;
	}
	
	private void moveTotem(int nextTotemSlot, ItemStack offhandStack)
	{
		boolean offhandEmpty = offhandStack.isEmpty();
		
		imc.getInteractionManagerI().windowClick_PICKUP(nextTotemSlot);
		imc.getInteractionManagerI().windowClick_PICKUP(45);
		
		if(!offhandEmpty)
			nextTickSlot = nextTotemSlot;
	}
	
	private void finishMovingTotem()
	{
		if(nextTickSlot == -1)
			return;
		
		imc.getInteractionManagerI().windowClick_PICKUP(nextTickSlot);
		nextTickSlot = -1;
	}
	
	private int searchForTotems(PlayerInventory inventory)
	{
		totems = 0;
		int nextTotemSlot = -1;
		
		for(int slot = 0; slot <= 36; slot++)
		{
			if(!isTotem(inventory.getStack(slot)))
				continue;
			
			totems++;
			
			if(nextTotemSlot == -1)
				nextTotemSlot = slot < 9 ? slot + 36 : slot;
		}
		
		return nextTotemSlot;
	}
	
	private boolean isTotem(ItemStack stack)
	{
		return stack.getItem() == Items.TOTEM_OF_UNDYING;
	}
	
}
