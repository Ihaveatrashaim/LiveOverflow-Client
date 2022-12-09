package net.trashaim.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.darkmagician6.eventapi.EventManager;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.trashaim.client.event.EventPacket;

@Mixin(ClientConnection.class)
public abstract class MixinNetworkHandler {

	@Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/Packet;)V", cancellable = true)
    private void onSendPacketHead(Packet<?> packet, CallbackInfo info) {

		EventPacket e = new EventPacket(packet);
		
		EventManager.call(e);
		
		if(e.isCancelled()) {
			info.cancel();
			return;
		}
		
		packet = e.getPacket();
		
    }
}
