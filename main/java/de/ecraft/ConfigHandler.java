package de.ecraft;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	
	private static Configuration config;
	private static final String category_energy_general = "General Energy Settings";
	
	
	public static boolean debug;
	
	public static boolean advanced_steam_processing;
	public static boolean advanced_fluid_processing;
	public static boolean advanced_electricity_processing;
	public static boolean advanced_rotary_processing;
	
	public ConfigHandler(Configuration cfg) {
		config = cfg;
		config.load();
	}
	
	public void init() {
		debug = config.getBoolean("Debug", config.CATEGORY_GENERAL, false, "Enable debug mode(Just for Modders)");
		
		advanced_steam_processing       = config.getBoolean("advanced_steam_processing"      , category_energy_general, true, "Enable avanced steam processing mode(More realistic)");
		advanced_fluid_processing       = config.getBoolean("advanced_fluid_processing"      , category_energy_general, true, "Enable avanced fluid processing mode(More realistic)");
		advanced_electricity_processing = config.getBoolean("advanced_electricity_processing", category_energy_general, true, "Enable avanced electricity processing mode(More realistic)");
		advanced_rotary_processing      = config.getBoolean("advanced_rotary_processing"     , category_energy_general, true, "Enable avanced rotary processing mode(More realistic)");
		
		config.save();
	}
}
