package me.galodystic.abilities;

import java.util.logging.Logger;

import me.ftbastler.BukkitGames.BGChat;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	Logger log = Logger.getLogger("Minecraft");
	
	public ABDisguise abdis;
	public Cooldown cool;
	public ABListener list;
	public ConfigManager config;
	
	public void onEnable() {
			
			config = new ConfigManager(this);
			abdesc();
			abdis = new ABDisguise(this);
			cool = new Cooldown(this);
			list = new ABListener(this);
			log.info("[Abilities] by Galodystic");
			log.info("[Abilities] Plugin enabled!");
	}
	
	public void onDisable() {
		
		log.info("[Abilities] Plugin disabled");
	}
	
	public void abdesc() {
		
		BGChat.setAbilityDesc(20, config.readString("Abilities.20.Desc"));
		BGChat.setAbilityDesc(30, config.readString("Abilities.30.Desc"));
		BGChat.setAbilityDesc(40, config.readString("Abilities.40.Desc"));
		BGChat.setAbilityDesc(50, config.readString("Abilities.50.Desc"));
		BGChat.setAbilityDesc(60, config.readString("Abilities.60.Desc"));
		BGChat.setAbilityDesc(70, config.readString("Abilities.70.Desc"));
		BGChat.setAbilityDesc(80, config.readString("Abilities.80.Desc"));
		BGChat.setAbilityDesc(90, config.readString("Abilities.90.Desc"));
		BGChat.setAbilityDesc(100, config.readString("Abilities.100.Desc"));
		BGChat.setAbilityDesc(110, config.readString("Abilities.110.Desc"));
		BGChat.setAbilityDesc(120, config.readString("Abilities.120.Desc"));
	}
}
