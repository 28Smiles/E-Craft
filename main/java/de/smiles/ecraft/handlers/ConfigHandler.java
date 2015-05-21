package de.smiles.ecraft.handlers;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	private static Configuration config;
	
	public static boolean debug;
	
	public ConfigHandler(Configuration cfg) {
		config = cfg;
		config.load();
	}
	
	public void init() {
		debug = config.getBoolean("Debug", config.CATEGORY_GENERAL, false, "Enable debug mode(Just for Modders)");
		
		config.save();
	}
}
