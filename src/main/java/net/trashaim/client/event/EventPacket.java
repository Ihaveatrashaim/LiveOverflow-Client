package net.trashaim.client.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

import net.minecraft.network.Packet;

public class EventPacket extends EventCancellable {

	@SuppressWarnings("rawtypes")
	private Packet packet;
	
	@SuppressWarnings("rawtypes")
	public EventPacket(Packet packet) {
		this.packet = packet;
	}
	
	@SuppressWarnings("rawtypes")
	public Packet getPacket() {
		return packet;
	}

	@SuppressWarnings("rawtypes")
	public void setPacket(Packet packet) {
		this.packet = packet;
	}
}
