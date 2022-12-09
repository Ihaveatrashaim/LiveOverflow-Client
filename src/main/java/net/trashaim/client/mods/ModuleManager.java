package net.trashaim.client.mods;

import java.util.ArrayList;

import net.trashaim.client.mods.impl.combat.*;
import net.trashaim.client.mods.impl.exploit.*;
import net.trashaim.client.mods.impl.movement.*;
import net.trashaim.client.mods.impl.player.*;
import net.trashaim.client.mods.impl.render.ShaderESP;
import net.trashaim.client.mods.impl.render.TrueSight;
import net.trashaim.client.mods.impl.render.Xray;

public class ModuleManager {

	public Module[] modules = {
		new AntiFakeCreative(),
		new AntiHungry(),
		new TrueSight(),
		new ShaderESP(),
		new AutoTotem(),
		new KillAura(),
		new MoveClip(),
		new LOFixer(),
		new NoClip(),
		new Flight(),
		new NoFall(),
		new TPAura(),
		new Blink(),
		new Reach(),
		new Speed(),
		new Xray(),
		new Clip(),
	};
	
	public Module getModule(Class<?> klass) {
		Module mod = null;
		
		for(Module m : modules) {
			if(m.getClass() == klass) {
				mod = m;
				break;
			}
		}
		
		return mod;
	}
	
	public ArrayList<Module> getModsByCategory(Category category) {
		ArrayList<Module> mods = new ArrayList<Module>();
		
		for(Module m : modules) {
			if(m.category == category) {
				mods.add(m);
			}
		}
		
		return mods;
	}
}
