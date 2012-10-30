package me.galodystic.abilities;

import java.util.Timer;
import java.util.TimerTask;

import me.ftbastler.BukkitGames.BGChat;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Cooldown extends JavaPlugin {
	
	private Main plugin;

	public Cooldown(Main plugin) {
		
		this.plugin = plugin;
	}
	
	public void monkCooldown(final Player player) {
		
		TimerTask action = new TimerTask() {
			
			public void run() {
				
				plugin.list.monkList.remove(player);
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(action, plugin.config.readInt("Abilities.50.Cooldown") * 1000);
	}
	
	public void thiefCooldown(final Player player) {
	
		TimerTask action = new TimerTask() {
			
			public void run() {
				
				plugin.list.thiefList.remove(player);
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(action, ((Integer) plugin.config.readInt("Abilities.70.Cooldown")) * 1000);
	}
	
	public void ghostCooldown(final Player player) {
		
		TimerTask action = new TimerTask() {
			
			public void run() {
				
				plugin.list.ghostList.remove(player);
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(action, ((Integer) plugin.config.readInt("Abilities.80.Cooldown")) * 1000);
	}
	
	public void showPlayerCooldown(final Player player, final Player[] players) {
		
		TimerTask action = new TimerTask() {
			
			public void run() {
				
				for (Player p : players) {
					if (p.getName().equals(player)) {
						continue;
					}
					p.showPlayer(player);
				}
				BGChat.printPlayerChat(player, plugin.config.readString("Abilities.80.visible"));
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(action, plugin.config.readInt("Abilities.80.Duration") * 1000);
	}
	
	public void viperCooldown(final Player player) {
		
		TimerTask action = new TimerTask() {
			
			public void run() {
				
				plugin.list.viperList.remove(player);
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(action, plugin.config.readInt("Abilities.110.Duration") * 1000);
	}
	
	public void thorCooldown(final Player player) {
		
		TimerTask action = new TimerTask() {
			
			public void run(){
				
				plugin.list.thorList.remove(player);
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(action, plugin.config.readInt("Abilities.30.Cooldown") * 1000);
	}
}
