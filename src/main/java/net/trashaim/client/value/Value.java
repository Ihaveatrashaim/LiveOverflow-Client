package net.trashaim.client.value;

public class Value {
	
	protected Object value;
	protected String name;
	
	public String getName() {
		return name;
	}

	public Object getObject() {
		return value;
	}

	public void setObject(Object value) {
		this.value = value;
	}
	
}
