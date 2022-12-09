package net.trashaim.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.util.Session;
import net.trashaim.client.Client;
import net.trashaim.client.mixinI.IClientPlayerInteractionManager;
import net.trashaim.client.mixinI.IMinecraft;
import net.trashaim.client.mods.FileManager;
import net.trashaim.client.mods.Module;
import java.io.IOException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraft implements IMinecraft {

	@Shadow
	private ClientPlayerInteractionManager interactionManager;
	
	@Shadow
    public Session session;
		
    @SuppressWarnings("resource")
	@Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo info) {    	
        if(MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().world != null) {
            for(Module m : Client.instance.moduleManager.modules) {
                if(m.isToggle()) {
                    m.tick();
                }
            }
        }
    }
    
    @Inject(at = @At("HEAD"), method = "stop")
    public void shutdown(CallbackInfo info) {
        try {
			new FileManager().saveModules();
		} catch (IOException e) {
		}
    }
    
    @Override
	public IClientPlayerInteractionManager getInteractionManagerI()
	{
		return (IClientPlayerInteractionManager)interactionManager;
	}

}
