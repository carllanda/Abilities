package me.galodystic.abilities;

import java.util.ArrayList;
import java.util.logging.Logger;

import me.ftbastler.BukkitGames.BGChat;
import me.ftbastler.BukkitGames.BGKit;

import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pgDev.bukkit.DisguiseCraft.Disguise.MobType;

public class ABListener extends JavaPlugin implements Listener{
	
	Logger log = Logger.getLogger("Minecraft");
	
	private Main plugin;
	
	public ArrayList<Player> viperList = new ArrayList<Player>();
	public ArrayList<Player> monkList = new ArrayList<Player>();
	public ArrayList<Player> thiefList = new ArrayList<Player>();
	public ArrayList<Player> ghostList = new ArrayList<Player>();
	public ArrayList<Player> thorList = new ArrayList<Player>();
	
	public ABListener(Main mainclass) {
		
		plugin = mainclass;
		
		mainclass.getServer().getPluginManager().registerEvents(this, mainclass);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlockPlaced();
		Player player = event.getPlayer();
		if (block.getType() == Material.CROPS) {
			if (BGKit.hasAbility(player, 20)) {
				block.setData(CropState.RIPE.getData());
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		if (BGKit.hasAbility(player, 30) && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem().getType() == Material.STONE_AXE) {
			
			if (!thorList.contains(player)) {
				thorList.add(player);
				plugin.cool.thorCooldown(player);
				Block block = event.getClickedBlock();
				Location loc = block.getLocation();
				World world = plugin.getServer().getWorld("world");
				world.strikeLightning(loc);
			}else {
				BGChat.printPlayerChat(player, plugin.config.readString("Abilities.30.Expired"));
			}
		}
		
		if (BGKit.hasAbility(player, 80) && event.getItem().getType() == Material.APPLE && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) ) {
			
			if (!ghostList.contains(player)) {
				if (player.getItemInHand().getAmount() == 1) {
					player.getInventory().clear(player.getInventory().getHeldItemSlot());
				}else {
					player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
				}
				ghostList.add(player);
				plugin.cool.ghostCooldown(player);
				plugin.cool.showPlayerCooldown(player, plugin.getServer().getOnlinePlayers());
				for(Player p : plugin.getServer().getOnlinePlayers()) {
					if (p.getName().equals(player.getName())) {
						continue;
					}
					p.hidePlayer(player);
				}
				BGChat.printPlayerChat(player, plugin.config.readString("Abilities.80.invisble"));
			}else {
				
				BGChat.printPlayerChat(player, plugin.config.readString("Abilities.80.Expired"));
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		
		Entity damager = event.getDamager();
		Entity defender = event.getEntity();
		if (damager.getType() == EntityType.PLAYER && defender.getType() == EntityType.PLAYER) {
			
			Player dam = (Player)damager;
			Player def = (Player)defender;
			if (BGKit.hasAbility(dam, 40) && event.getCause() == DamageCause.ENTITY_ATTACK) {
				
				dam.setHealth(dam.getHealth()+1);
			}
			
			if (BGKit.hasAbility(dam, 50) && dam.getItemInHand().getType() == Material.BLAZE_ROD && event.getCause() == DamageCause.ENTITY_ATTACK) {
			
				if (!(def.getItemInHand() == null)) {
					if (!monkList.contains(dam)) {	
						int random = (int) (Math.random()* (plugin.config.readInt("Abilities.50.Chance")-1)+1);
						if (random == 1) {
							monkList.add(dam);
							plugin.cool.monkCooldown(dam);
							def.getInventory().clear(def.getInventory().getHeldItemSlot());
							BGChat.printPlayerChat(dam, plugin.config.readString("Abilities.50.Success"));
							BGChat.printPlayerChat(def, plugin.config.readString("Abilities.50.Success"));
						}
					}else {
						BGChat.printPlayerChat(dam, plugin.config.readString("Abilities.50.Expired"));
					}
				}
			}
			
			if (BGKit.hasAbility(dam, 70) && dam.getItemInHand().getType() == Material.STICK && event.getCause() == DamageCause.ENTITY_ATTACK) {
				
				if (!(def.getItemInHand() == null)) {	
					if (!thiefList.contains(dam)) {
						int random = (int) (Math.random()* (plugin.config.readInt("Abilities.70.Chance")-1)+1);
						if (random == 1) {	
							thiefList.add(dam);
							plugin.cool.thiefCooldown(dam);
							dam.getInventory().clear(dam.getInventory().getHeldItemSlot());
							dam.getInventory().addItem(def.getItemInHand());
							def.getInventory().clear(def.getInventory().getHeldItemSlot());
							BGChat.printPlayerChat(dam, plugin.config.readString("Abilities.70.Success"));
							BGChat.printPlayerChat(def, plugin.config.readString("Abilities.70.Success"));
						}
					}else {
						BGChat.printPlayerChat(dam, plugin.config.readString("Abilities.70.Expired"));
					}
				}
			}
			
			if (BGKit.hasAbility(dam, 110)) {
				
				int random = (int) (Math.random()* (plugin.config.readInt("Abilities.110.Chance")-1)+1) ;
				if (random == 1 && !viperList.contains(def)) {
					
					def.addPotionEffect(new PotionEffect(PotionEffectType.POISON, plugin.config.readInt("Abilities.110.Duration")*20, 1));
					viperList.add(def);
					BGChat.printPlayerChat(dam, plugin.config.readString("Abilities.110.Damager"));
					BGChat.printPlayerChat(def, plugin.config.readString("Abilities.110.Defender"));
					plugin.cool.viperCooldown(def);
				}
			}
		}
		if(damager.getType() == EntityType.PLAYER) {
			
			Player player = (Player) damager;
			EntityType mob = defender.getType();
			
			if (BGKit.hasAbility(player, 90) && event.getCause() == DamageCause.ENTITY_ATTACK) {
				if (ABDisguise.getMobType(mob) != null) {
				
					MobType mt = ABDisguise.getMobType(mob);
					plugin.abdis.disguise(player, mt);
				}
			}
			
			if (BGKit.hasAbility(player, 100) && player.getItemInHand().getType() == Material.AIR) {
				
				event.setDamage(event.getDamage()+ 4);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		
		Player dp = event.getEntity();
		if (dp.getKiller() != null) {
			
			Player killer = dp.getKiller();
			if (BGKit.hasAbility(killer, 60)) {
				
				killer.setFoodLevel(killer.getFoodLevel() + 6);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		
		Entity en = event.getEntity();
		if (en.getType() == EntityType.PLAYER) {
			Player player = (Player) en;
			if (BGKit.hasAbility(player, 90)) {
				plugin.abdis.unDisguise(player);
			}
			if (BGKit.hasAbility(player, 100) && event.getDamage() > 1) {
				event.setDamage(event.getDamage() - 1);
			}
		}
	}
	
	@EventHandler
	public void onEntityTarget(EntityTargetEvent event) {
		
		Entity entity = event.getTarget();
		if (entity != null) {	
			if (entity.getType() == EntityType.PLAYER) {
			
				Player player = (Player) entity;
				if (BGKit.hasAbility(player, 120) && event.getReason() == TargetReason.CLOSEST_PLAYER) {
					event.setCancelled(true);
				}
			}
		}
	}
}
