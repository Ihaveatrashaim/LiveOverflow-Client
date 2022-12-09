package net.trashaim.client.mods.impl.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.trashaim.client.mods.Category;
import net.trashaim.client.mods.Module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Xray extends Module {

    public List<Block> blocks = Arrays.asList(Blocks.LAVA, Blocks.WATER, Blocks.COAL_ORE,
            Blocks.IRON_ORE, Blocks.CHEST, Blocks.ANCIENT_DEBRIS, Blocks.NETHERITE_BLOCK, Blocks.DIAMOND_BLOCK,
            Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.IRON_ORE,
            Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.GOLD_ORE, Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE);

    private static final ThreadLocal<BlockPos.Mutable> EXPOSED_POS = ThreadLocal.withInitial(BlockPos.Mutable::new);
    
    public Xray() {
        this.setName("Xray");
        this.category = Category.Render;
    }

    @Override
    public void tick() {

    }


    @Override
    public void onDisable() {
    	mc.worldRenderer.reload();
    }

    @Override
    public void onEnable() {
    	mc.worldRenderer.reload();
    }
}
