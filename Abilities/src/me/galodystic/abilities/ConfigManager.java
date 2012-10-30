package me.galodystic.abilities;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager extends JavaPlugin {

	private Main plugin;
	
	private File configFile;
	private FileConfiguration config;
			
	public ConfigManager(Main plugin) {
		
		this.plugin = plugin;
		manageConfig();
	}
	
	private void manageConfig() {
		
		configFile = new File(plugin.getDataFolder() + "/config.yml");
		config = YamlConfiguration.loadConfiguration(configFile);
		LinkedHashMap<String , String> configMap = new LinkedHashMap<String, String>();
		
		config.options().header("ConfigFile for the Abilities for The BukkitGames by Galodystic!");
		
		configMap.put("Abilities.20.Desc", "Crops are grown immediately");
		
		configMap.put("Abilities.30.Desc", "Right click with the stone ax and lightning strikes on the clicked block");
		configMap.put("Abilities.30.Cooldown", "15");
		configMap.put("Abilities.30.Expired", "Cooldown has not expired!");
		
		configMap.put("Abilities.40.Desc", "When you inflict damage to another player you will be healed");
		
		configMap.put("Abilities.50.Desc", "With a Blaze Rod you can make items from other players broke");
		configMap.put("Abilities.50.Chance", "5");
		configMap.put("Abilities.50.Cooldown", "45");
		configMap.put("Abilities.50.Expired", "Cooldown has not expired!");
		configMap.put("Abilities.50.Success", "Item is broken!");
		
		configMap.put("Abilities.60.Desc", "If you kill a player your food level will be healed");
		
		configMap.put("Abilities.70.Desc", "With a stick you can steal other players items");
		configMap.put("Abilities.70.Chance", "5");
		configMap.put("Abilities.70.Cooldown", "30");
		configMap.put("Abilities.70.Expired", "Cooldown has not expired!");
		configMap.put("Abilities.70.Success", "Item was stolen");
		
		configMap.put("Abilities.80.Desc", "If you eat an apple you are invisible for 6 seconds");
		configMap.put("Abilities.80.Cooldown", "60");
		configMap.put("Abilities.80.Expired", "Cooldown has not expired!");
		configMap.put("Abilities.80.Duration", "6");
		configMap.put("Abilities.80.invisble", "You are now invisible!");
		configMap.put("Abilities.80.visible", "You are no longer visible!");
		
		configMap.put("Abilities.90.Desc", "When you hit a mob, you turn in this mob until you take damage");
		configMap.put("Abilities.90.disguise", "You are now disguised!");
		configMap.put("Abilities.90.undisguise", "You are no longer disguised!");
		
		configMap.put("Abilities.100.Desc", "With the fist you make damage like a stonesword, incoming damage is lower!");
		
		configMap.put("Abilities.110.Desc", "With every hit there is a chance to poison the other player");
		configMap.put("Abilities.110.Chance", "12");
		configMap.put("Abilities.110.Duration", "20");
		configMap.put("Abilities.110.Damager", "Player was poisoned!");
		configMap.put("Abilities.110.Defender", "You have been poisoned!");
		
		configMap.put("Abilities.120.Desc", "Mobs don't attack until you attack them first");
		
		for (Entry<String, String> entry : configMap.entrySet()) {
			String path = entry.getKey();
			Object value = entry.getValue();
			
			if (!config.contains(path)) {
				config.set(path, value);
			}
		}
		
		try{
			config.save(configFile);
		}catch (Exception e) {
			e.printStackTrace();
			plugin.log.info("[Abilities] Unable to save config.yml!");
		}
	}
	
	public String readString(String path) {
		
		return config.getString(path);
	}
	
	public int readInt(String path) {
		
		String string = config.getString(path);
		int zahl;
		try{
			zahl = Integer.parseInt(string);
			return zahl;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
