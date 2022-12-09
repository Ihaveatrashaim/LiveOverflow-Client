package net.trashaim.client.value;

import java.util.ArrayList;

public class ModeValue extends Value {

	public ArrayList<String> modes;
	
	public ModeValue(String name, String... modes) {
		this.name = name;
		
		this.modes = new ArrayList<String>();
		
		for(String s : modes) {
			this.modes.add(s);
		}
				
		if(!this.modes.isEmpty()) this.value = this.modes.get(0);
	}
	
	public String getMode() {
		return (String) this.value;
	}
	
	public void setMode(String value) {
		this.value = value;
	}
}
