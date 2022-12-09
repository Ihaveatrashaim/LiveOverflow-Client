package net.trashaim.client.mods;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.darkmagician6.eventapi.EventManager;

import net.minecraft.client.MinecraftClient;
import net.trashaim.client.mixinI.IMinecraft;
import net.trashaim.client.value.Value;

public class Module {

	protected MinecraftClient mc = MinecraftClient.getInstance();
	protected IMinecraft imc = (IMinecraft)MinecraftClient.getInstance();
	
	public String name;

	public Category category;

	public boolean toggle;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isToggle() {
		return toggle;
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
		
		if(mc.player == null || mc.world == null) return;
		if(!toggle) {
			onDisable();
			EventManager.unregister(this);
		}
		else {
			onEnable(); 
			EventManager.register(this);
		}
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}

	public void tick() {
		
	}
	
	public Value getValue(final String valueName) {
        for(final Field field : getClass().getDeclaredFields()) {
            try{
                field.setAccessible(true);

                final Object o = field.get(this);

                if(o instanceof Value) {
                    final Value value = (Value) o;

                    if(value.getName().equalsIgnoreCase(valueName))
                        return value;
                }
            }catch(IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public List<Value> getValues() {
        final List<Value> values = new ArrayList<>();

        for(final Field field : getClass().getDeclaredFields()) {
            try{
                field.setAccessible(true);

                final Object o = field.get(this);

                if(o instanceof Value)
                    values.add((Value) o);
            }catch(IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return values;
    }

}
