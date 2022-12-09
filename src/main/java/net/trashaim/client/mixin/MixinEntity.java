package net.trashaim.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public abstract class MixinEntity {

	@Shadow
	public abstract boolean isGlowingLocal();
	
	@Overwrite
	public boolean isGlowing() {
		return isGlowingLocal();
	}
}
