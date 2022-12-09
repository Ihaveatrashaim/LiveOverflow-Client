package net.trashaim.client.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.Packet;

public class EventRender3D extends EventCancellable {

	public MatrixStack matrixStack;
	
	public EventRender3D(MatrixStack matrixStack) {
		this.matrixStack = matrixStack;
	}
}
