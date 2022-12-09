package net.trashaim.client.value;

public class NumberValue extends Value{

	public final float max,min;
	
	public NumberValue(String name, float value, float min, float max) {
		this.name = name;
		this.max = max;
		this.min = min;
		this.value = value;
	}
	
	public float getValue() {
		return (float) value;
	}
	
	public void setValue(float value) {
		this.value = value;
	}
}
