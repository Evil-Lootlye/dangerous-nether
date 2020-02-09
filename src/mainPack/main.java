package mainPack;

import java.awt.Event;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Illager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import mainPack.NetherGenerator;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.TileEntitySkull;

public class main extends JavaPlugin implements Listener, CommandExecutor{

	BukkitScheduler scheduler = null;
	FileConfiguration config = getConfig();
	public ConsoleCommandSender console = getServer().getConsoleSender();
	Random randor = new Random();
	boolean hasWorlds = false;
	public static List<String> worlds = new ArrayList<String>();
	public boolean canSave = false;
	public List<Entity> effectEnts = new ArrayList<Entity>();
	boolean ambients = false;
	boolean caveents = false;
	public static List<String> mobNames = new ArrayList<String>();
	int damage = 0;
	static boolean nethstruct = true;
	static int structchance = 0;
	static int webchance = 0;
	static int mushchance = 0;
	static int nspikechance = 0;
	static int coralchance = 0;
	static int rosechance = 0;
	static int mushroomchance = 0;
	static int replacerackchance = 0;
	static int nheight = 120;
	
	boolean newFire = false;
	
	@Override
	public void onEnable() {
		createConfigFol();
		this.getServer().getPluginManager().registerEvents(this, this);
		ambients = config.getBoolean("Enable Ambient Sounds ");
		caveents = config.getBoolean("Enable Custom Mobs ");
		webchance = config.getInt("Web Vine Chance ");
		mushchance = config.getInt("Mushroom Chance ");
		nethstruct = config.getBoolean("Enable Nether Structures ");
		structchance = config.getInt("All Structures Chance ");
		nspikechance = config.getInt("Nether Spike Chance ");
		coralchance = config.getInt("Coral Chance ");
		rosechance = config.getInt("Rose Chance ");
		newFire = config.getBoolean("Enable Better Fire ");
		mushroomchance = config.getInt("Mushrooms Chance ");
		replacerackchance = config.getInt("Replace Netherrack Chance ");
		nheight = config.getInt("Nether World Height ");
		mobNames.add(config.getString("Molten = "));
		mobNames.add(config.getString("Fireball = "));
		mobNames.add(config.getString("Inferno = "));
		mobNames.add(config.getString("Old Shadow = "));
		mobNames.add(config.getString("Sherogath = "));
		mobNames.add(config.getString("Sadness = "));
		mobNames.add(config.getString("Necromancer = "));
		mobNames.add(config.getString("Alpha Pigman = "));
		worlds = (List<String>) config.getList("Enabled Worlds - If Left Blank Will Just Use default world ");
		int d = config.getInt("Hungering Darkness Damage ");
		if(d > 200) {
			damage = 200;
		}
		else if(d < 0) {
			damage = 0;
		}
		else {
			damage = d;
		}
		if(worlds.size()==0) {
			boolean hasWorldR = false;
			for(World w : Bukkit.getWorlds()) {
				if(w.getName().toLowerCase().contains("nether")) {
					hasWorldR = true;
					worlds.add(w.getName());
				}
			}
			if(hasWorldR == true) {
			}
			else {
			worlds.add(Bukkit.getWorlds().get(0).getName());
			}
		}
		for(String namew : worlds) {
			World wor = Bukkit.getWorld(namew);
			if(wor!=null) {
			List<BlockPopulator> bp = new ArrayList<BlockPopulator>(wor.getPopulators());
			for(BlockPopulator bps : bp) {
				if(bps.toString().length()>=24) {
					if(bps.toString().substring(0, 24).equals("mainPack.NetherGenerator")) {
						wor.getPopulators().remove(bps);
					}
					if(bps.toString().substring(0, 25).equals("mainPack.NetherGenerator2")) {
						wor.getPopulators().remove(bps);
					}
				}
			}
			wor.getPopulators().add(new NetherGenerator());
			wor.getPopulators().add(new NetherGenerator2());
			}
		}
		scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				if(caveents==true||config.getBoolean("Enable Lava Burns ")==true) {
				betterEffectLooper();
				}
			}
		}, 0L, /* 600 */((long) 3));
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				fireLoop();
			}
		}, 0L, /* 600 */((long) 1));
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				try {
				if(ambients == true) {
				for(String namew : worlds) {
				World wor = Bukkit.getWorld(namew);
				if (wor != null) {
						for (Player p : wor.getPlayers()) {
						doSound(p);
					}
				}
				}
				}
				if(randor.nextBoolean()) {
					testPlayerHandItem();
				}
				}
				catch(Exception e) {
				}
			}
		}, 0L, /* 600 */10L);
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				try {
				if(config.getBoolean("Enable Lava Spouts ")) {
				for(String namew : worlds) {
				World wor = Bukkit.getWorld(namew);
				if (wor != null) {
						for (Player p : wor.getPlayers()) {
						if(randor.nextInt(3)==1&&(config.getBoolean("Enable Lava Spouts "))) {
							doLavaSpout(p);
						}
					}
				}
				}
				}
				}
				catch(Exception e) {
				}
			}
		}, 0L, /* 600 */350L);
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				try {
				if(ambients == true) {
				for(String namew : worlds) {
				World wor = Bukkit.getWorld(namew);
				if (wor != null) {
						for (Player p : wor.getPlayers()) {
							if(ambients==true) {
								doWind(p);
							}
						}
					}
				}
				}
				}
				catch(Exception e) {
				}
			}
		}, 0L, /* 600 */390L);
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				try {
				if(config.getBoolean("Nether Lightning ")) {
				for(String namew : worlds) {
				World wor = Bukkit.getWorld(namew);
				if (wor != null) {
						for (Player p : wor.getPlayers()) {
						doLightning(p);
						}
				}
				}
				}
				}
				catch(Exception e) {
				}
			}
		}, 0L, /* 600 */500L);
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				try {
				if(config.getBoolean("Enable Falling Nether ")) {
				for(String namew : worlds) {
				World wor = Bukkit.getWorld(namew);
				if (wor != null) {
						for (Player p : wor.getPlayers()) {
						collapseNether(p);
						}
				}
				}
				}
				}
				catch(Exception e) {
				}
			}
		}, 0L, /* 600 */5000L);

	}
	
	private static boolean getLookingAt2(LivingEntity player, LivingEntity player1) {
		Location eye = player.getEyeLocation();
		Vector toEntity = ((LivingEntity) player1).getEyeLocation().toVector().subtract(eye.toVector());
		double dot = toEntity.normalize().dot(eye.getDirection());

		return dot > 0.70D;
	}
	
	@Override
	public void onDisable() {
		worlds.clear();
	}
	
	public void createConfigFol() {
		config.addDefault("Enable Ambient Sounds ", true);
		config.addDefault("Enable Better Fire ", true);
		config.addDefault("Nether Lightning ", true);
		config.addDefault("Soulsand Slowness ", true);
		config.addDefault("Netherrack Fires ", true);
		config.addDefault("Enable Custom Mobs ", true);
		config.addDefault("Enable Mean Ghasts ", true);
		config.addDefault("Enable Lava Burns ", true);
		config.addDefault("Enable Lava Placement ", true);
		config.addDefault("Enable Lava Spouts ", true);
		config.addDefault("Enable Falling Nether ", true);
		config.addDefault("Enable Nether Structures ", true);
		config.addDefault("Enable Alpha Pigman Sword Drop ", true);
		config.addDefault("Wither Potion Drops ", true);
		config.addDefault("Haste Potion Drops ", true);
		config.addDefault("Extended Wither Boss ", false);
		config.addDefault("Enderman Growl replace Enderdragon Growl ", false);
		config.addDefault("::::Higher equals lower chance!::::", "");
		config.addDefault("Lightning Chance ", 0);
		config.addDefault("Netherack Melting Chance ", 18);
		config.addDefault("Netherrack Fire Chance ", 10);
		config.addDefault("Mob Spawn Chance ", 5);
		config.addDefault("Falling Nether Chance ", 0);
		config.addDefault("Alpha Pigman Sword Drop Chance ", 1);
		config.addDefault("Wind Volume - lower equals louder - ", 800);
		config.addDefault("Wind Pitch - must be between 0 and 2 - ", 0);
		config.addDefault("Low Y Value Wind ", 70);
		config.addDefault("High Y Value Wind ", 200);
		config.addDefault("Netherrack Step Sound ", "ENTITY_SLIME_SQUISH");
		config.addDefault("Wither Potion Drop Chance ", 2);
		config.addDefault("Haste Potion Drop Chance ", 2);
		config.addDefault("::::::::::::::::::::::::::::::::::::", "");
		config.addDefault("All Structures Chance ", 0);
		config.addDefault("Web Vine Chance ", 0);
		config.addDefault("Mushroom Chance ", 0);
		config.addDefault("Nether Spike Chance ", 0);
		config.addDefault("Nether World Height ", 120);
		config.addDefault("Coral Chance  ", 0);
		config.addDefault("Rose Chance ", 0);
		config.addDefault("Mushrooms Chance ", 0);
		config.addDefault("Replace Netherrack Chance ", 0);
		config.addDefault(":::::::::::::::::::::::::::::::::::::", "");
		config.addDefault("Spawn Molten ", true);
		config.addDefault("Spawn Fireball ", true);
		config.addDefault("Spawn Inferno ", true);
		config.addDefault("Spawn Old Shadow ", true);
		config.addDefault("Spawn Sherogath ", true);
		config.addDefault("Spawn Sadness ", true);
		config.addDefault("Spawn Necromancer ", true);
		config.addDefault("Spawn Alpha Pigman ", true);
		config.addDefault(":::::Monster Names - no Blanks:::::", "");
		config.addDefault("Molten = ", "Molten");
		config.addDefault("Fireball = ", "Fireball");
		config.addDefault("Inferno = ", "Inferno");
		config.addDefault("Old Shadow = ", "Old Shadow");
		config.addDefault("Sherogath = ", "Sherogath");
		config.addDefault("Sadness = ", "Sadness");
		config.addDefault("Necromancer = ", "Necromancer");
		config.addDefault("Alpha Pigman = ", "Alpha Pigman");
		config.addDefault("::::::::::::::::::::::::::::::::::::::", "");
		List<String> listitem2 = new ArrayList<String>();
		boolean hasWorldR = false;
		for(World w : Bukkit.getWorlds()) {
			if(w.getName().toLowerCase().contains("nether")) {
				hasWorldR = true;
				listitem2.add("" + w.getName());
			}
		}
		config.addDefault("Enabled Worlds - If Left Blank Will Just Use default world ", listitem2);
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	@EventHandler
	public void setWorld(PlayerJoinEvent e) {
		if(hasWorlds==false) {
			ambients = config.getBoolean("Enable Ambient Sounds ");
		}
		//}
	}
	
	public int coinFlip() {
		if(randor.nextBoolean()) {
			return -1;
		}
		return 1;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("getworldnamed")) {
			if (sender instanceof Player) {
				((Player) sender).getWorld().generateTree(((Player) sender).getLocation(), TreeType.RED_MUSHROOM);
				console.sendMessage(ChatColor.GREEN + "World Name = " + ChatColor.RESET + " " + ((Player) sender).getWorld().getName());
				for(Entity e : ((Player) sender).getNearbyEntities(10, 10, 10)) {
					if(e instanceof ArmorStand) {
						e.remove();
					}
				}
					return true;
			}
		}
	    return true;
	}

	//Lava Stream
	
	public void doLavaSpout(Player p) {
		for(int direction = 0; direction < 4; direction++) {
			Location l = p.getLocation();
			int ammount = randor.nextInt(3)+1;
			Location l3 = l.clone();
			boolean banana = false;
			Location l2 = null;
			for(int i2 = 10; i2 > 0; i2--) {
				if(l3.getBlock().getType()==Material.LAVA) {
					banana = true;
					l2 = l3.clone();
				}
				if(direction == 0) {
					l3.add(ammount, 0, 0);
				}
				else if(direction == 1) {
					l3.subtract(ammount, 0, 0);
				}
				else if(direction == 2) {
					l3.add(0, 0, ammount);
				}
				else if(direction == 3) {
					l3.subtract(0, 0, ammount);
				}
				if(i2%10==0) {
					l3.subtract(0, 1, 0);
				}
			}
			if(l2!=null) {
			int height = randor.nextInt(20)+5;
			for(int count = 0; count < height; count++) {
				l2.getWorld().spawnParticle(Particle.LAVA, l2, 2);
				if(count>height/2) {
					l2.getWorld().spawnParticle(Particle.LAVA, l2, 2);
				}
				l2.add(0, 1, 0);
			}
			}
			if(banana == true) {
				break;
			}
		}
	}
	
	//
	
	//Mob Stuff

	public boolean hasName(String n, Entity e) {
		if(exists(e)) {
		if(e.hasMetadata(n)) {
			return true;
		}
		if(e.getCustomName() != null) {
			String name = ChatColor.stripColor(e.getCustomName());
			if((!name.equals("")) && (!name.equals(" "))) {
				if(name.equals(n)) {
					return true;
				}
			}
		}
		}
		return false;
	}
	
	public boolean exists(Entity e) {
		if(e == null) {
			return false;
		}
		if(e.isDead()) {
			return false;
		}
		return true;
	}
	
	@EventHandler
	public void onTarget(EntityTargetEvent event) {
		if(event.isCancelled() || (!exists(event.getTarget()))) {
			return;
		}
		if(caveents==true) {
		if(event.getEntity() instanceof Monster) {
			Monster e = (Monster) event.getEntity();
				if(e.getCustomName()!=null || hasName("nethermob", e)) {
					if(hasName(config.getString("Inferno = "), e)) {
							effectEnts.add(event.getEntity());
					}
					else if(hasName(config.getString("Old Shadow = "), e)) {
						effectEnts.add(event.getEntity());
					}
					else if(hasName(config.getString("Fireball = "), e)) {
						effectEnts.add(event.getEntity());
					}
					else if(hasName(config.getString("Molten = "), e)) {
						effectEnts.add(event.getEntity());
					}
					else if(hasName(config.getString("Sadness = "), e)) {
						effectEnts.add(event.getEntity());
					}
					else if(hasName(config.getString("Sherogath = "), e)) {
						effectEnts.add(event.getEntity());
					}
				}
			}
		}
		if(config.getBoolean("Enable Mean Ghasts ")==true) {
			if(event.getEntity() instanceof Ghast) {
				effectEnts.add(event.getEntity());
			}
		}
	}
	
	private static boolean getLookingAt(LivingEntity player, LivingEntity player1) {
		Location eye = player.getEyeLocation();
		Vector toEntity = ((LivingEntity) player1).getEyeLocation().toVector().subtract(eye.toVector());
		double dot = toEntity.normalize().dot(eye.getDirection());

		return dot > 0.99D;
	}
	
	public void betterEffectLooper() {
		if(!effectEnts.isEmpty()) {
		List<Entity> tempEntss = new ArrayList<Entity>(effectEnts);
		for(Entity e : tempEntss) {
			LivingEntity e2 = (LivingEntity) e;
			if(!worlds.contains(e2.getWorld().getName())) {
				effectEnts.remove(e);
			}
			else {
			if(exists(e)) {
				if(e instanceof Monster) {
					if(exists(((Monster) e).getTarget())) {
						String name = e2.getCustomName();
						if(name!=null || hasName("nethermob", e)) {
								if(hasName(config.getString("Inferno = "), e)) {
									e2.getWorld().spawnParticle(Particle.FLAME, e2.getEyeLocation(), 1, 0, 0, 0, 0.001);
									e2.getWorld().playSound(e2.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 1, 2);
									if(getLookingAt((LivingEntity) e2, (LivingEntity) ((Monster) e).getTarget())) {
										int radius = 1;
										if(((Player) ((Monster) e).getTarget()).isSneaking()){
											
										}
										else {
										Location loc = ((Monster) e).getTarget().getLocation();
						    			if(loc.getBlock().getType()==Material.AIR) {
						    				loc.getBlock().setType(Material.FIRE, false);
						    			}
										}
									}
								}
								else if(hasName(config.getString("Molten = "), e)) {
									if(randor.nextInt(14)==1) {
										e.getLocation().getBlock().setType(Material.FIRE);
									}
									if(randor.nextInt(28)==1) {
										e.getLocation().subtract(0, 1, 0).getBlock().setType(Material.MAGMA_BLOCK);
									}
									e2.getWorld().spawnParticle(Particle.LAVA, e2.getLocation().add(0, 1, 0), 2, 0.2, 0.5, 0.2, 0.005);
									if(Bukkit.getServer().getVersion().contains("1.14")) {
									e2.getWorld().spawnParticle(Particle.valueOf("FALLING_LAVA"), e2.getLocation().add(0, 1, 0), 6, 0.2, 0.5, 0.2, 0.005);
									}
								}
								else if(hasName(config.getString("Old Shadow = "), e)) {
									e2.getWorld().playSound(e2.getLocation(), Sound.ENTITY_HORSE_HURT, 1, 2);
									if(e2.getLocation().getBlock().getLightLevel()>=12) {
										int radius = 4;
										Location loc = e2.getLocation();
						    			int cx = loc.getBlockX();
						    			int cy = loc.getBlockY();
						    			int cz = loc.getBlockZ();
						    			for (int x = cx - radius; x <= cx + radius; x++) {
						    				for (int z = cz - radius; z <= cz + radius; z++) {
						    					for (int y = (cy - radius); y < (cy + radius); y++) {
						    						double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));
						    							org.bukkit.block.Block b = new Location(loc.getWorld(), x, y, z).getBlock();
						    							if(b.getType()==Material.TORCH||b.getType()==Material.FIRE||b.getType()==Material.GLOWSTONE||b.getType()==Material.JACK_O_LANTERN||b.getType()==Material.REDSTONE_LAMP||b.getType()==Material.SEA_LANTERN) {
						    								b.setType(Material.AIR);
						    							}
						    					}
						    				}
						    			}
									}
									else {
										List<Entity> ents = e2.getNearbyEntities(3, 3, 3);
										for(Entity ez : ents) {
											if(ez instanceof LivingEntity) {
												((LivingEntity) ez).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,120,1));
												((LivingEntity) ez).addPotionEffect(new PotionEffect(PotionEffectType.WITHER,120,0));
											}
										}
										e2.getWorld().spawnParticle(Particle.SUSPENDED, e2.getLocation().add(0, 1, 0), 230, 1,1,1,0.001);
										e2.getWorld().spawnParticle(Particle.SMOKE_NORMAL, e2.getLocation().add(0, 1, 0), 10, 1,1,1,0.001);
									}
								}
								else if(hasName(config.getString("Sadness = "), e)) {
									if(e2.hasPotionEffect(PotionEffectType.SLOW)) {
										Entity e3 = ((Monster) e2).getTarget();
										if(e3 != null) {
											double blocksAway = Math.sqrt((Math.pow(Math.abs(e3.getLocation().getX()-e2.getLocation().getX()), 2) + (Math.pow(Math.abs(e3.getLocation().getZ()-e2.getLocation().getZ()), 2))));
											if(blocksAway <= 6) {
												if(randor.nextInt(8)==1) {
													e2.getWorld().playSound(e2.getLocation(), Sound.ENTITY_WOLF_GROWL, 1, 0);
												}
											}
											else {
												if(randor.nextInt(14)==1) {
													e2.getWorld().playSound(e2.getLocation(), Sound.ENTITY_WOLF_HURT, 1, 0);
												}
											}
										}
									}
								}
								else if(hasName(config.getString("Fireball = "), e)) {
										e2.getWorld().spawnParticle(Particle.FLAME, e2.getLocation().add(0, e2.getEyeHeight(), 0), 5, 0.1, 0.1, 0.1, 0.005);
										e2.getWorld().spawnParticle(Particle.LAVA, e2.getLocation().add(0, e2.getEyeHeight(), 0), 1, 0.1, 0.1, 0.1, 0.005);
								}
								else if(hasName(config.getString("Sherogath = "), e)) {
									e2.getWorld().spawnParticle(Particle.BARRIER, e2.getLocation() ,1);
									if(randor.nextInt(14)==1) {
										((Player) ((Monster) e2).getTarget()).playSound(((Player) ((Monster) e2).getTarget()).getLocation(), Sound.ENCHANT_THORNS_HIT, (float) .04, (float) .2);
									}
								}
							}
						}
						else {
							effectEnts.remove(e);	
						}
						}
					else if(e instanceof Player) {
						Player p = (Player) e;
						boolean isCarry = false;
						try {
						if(p.getInventory().getItemInMainHand()!=null) {
						if(p.getInventory().getItemInMainHand().getType()==Material.LAVA_BUCKET) {
							if(randor.nextInt(10)==0) {
								p.damage(1);
								p.setFireTicks(20);
							}
							isCarry = true;
						}
						}
						else if(p.getInventory().getItemInOffHand()!=null) {
							if(p.getInventory().getItemInOffHand().getType()==Material.LAVA_BUCKET) {
								if(randor.nextInt(10)==0) {
									p.damage(1);
									p.setFireTicks(20);
								}
								isCarry = true;
							}
						}
						if(isCarry == false) {
							effectEnts.remove(e);	
						}
						}
						catch(Exception except) {
							
						}
					}
					else {
						effectEnts.remove(e);	
					}
		}
			else {
				effectEnts.remove(e);	
			}
		}}}}
	
	public static Location getBlockInFrontOfPlayer(LivingEntity entsa) {
		Vector inverseDirectionVec = entsa.getLocation().getDirection().normalize().multiply(1);
		return entsa.getLocation().add(inverseDirectionVec);
	}
	
	@EventHandler
	public void onMobName(PlayerInteractEntityEvent event) {
		if(!worlds.contains(event.getRightClicked().getWorld().getName())) {
			return;
		}
		try {
			World wor = event.getRightClicked().getWorld();
		if(!(event.getRightClicked() instanceof Player)) {
			ItemStack i = event.getPlayer().getInventory().getItemInMainHand();
			if(i.getType()==Material.NAME_TAG) {
				if(i.hasItemMeta()) {
					if(i.getItemMeta().hasDisplayName()) {
						String s = i.getItemMeta().getDisplayName();
						if(mobNames.contains(s)) {
							removeItemNaturally(event.getPlayer());
							if(event.getRightClicked() instanceof LivingEntity && (!(event.getRightClicked() instanceof Player))) {
								((LivingEntity) event.getRightClicked()).setCustomName("");
								event.setCancelled(true);
							}
							else {
								event.getRightClicked().remove();
							}
						}
					}
				}
			}
		}
		}
		catch(Exception e) {
			
		}
	}
	
	public static void removeItemNaturally(Player p) {
		try {
		if (p.getInventory().getItemInMainHand().getAmount() <= 1) {
			//p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
			p.getInventory().getItemInMainHand().setAmount(0);
		} else {
			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
		}
		}
		catch(Exception error) {
			
		}
	}
	
	public static void removeItemNaturally(Player p, boolean side) {
		try {
			if(side == true) {
				if (p.getInventory().getItemInMainHand().getAmount() <= 1) {
					p.getInventory().getItemInMainHand().setAmount(0);
				} 
				else {
					p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
				}
			}
			else if(side == true) {
				if (p.getInventory().getItemInOffHand().getAmount() <= 1) {
					p.getInventory().getItemInOffHand().setAmount(0);
				} 
				else {
					p.getInventory().getItemInOffHand().setAmount(p.getInventory().getItemInOffHand().getAmount() - 1);
				}
			}
		}
		catch(Exception error) {
			
		}
	}
	
	@EventHandler
	public void deleteLightLevel(CreatureSpawnEvent event) {
		if(event.isCancelled()) {
			return;
		}
		Entity e = event.getEntity();
		if (exists(e)) {
			if(!worlds.contains(e.getWorld().getName())) {
				return;
			}
			World wor = event.getEntity().getWorld();
			if(caveents==true) {
				Block b = e.getLocation().subtract(0, 1, 0).getBlock();
				if(b==null) {
					return;
				}
				if(caveents==true) {
					if (e instanceof PigZombie) {
						if (event.getSpawnReason() == SpawnReason.NATURAL && (b.getType()==Material.NETHERRACK || b.getType()==Material.SOUL_SAND)) {
							if(randor.nextInt(config.getInt("Mob Spawn Chance ")+1)==0) {
								doMobSpawns(e);
							}
						}
					}
				}
				else {
					return;
				}
			}
		}
	}
	
	//
	
	//Nether Lightning
	
	public void doLightning(Player p) {
		try {
		if(inHell(p.getLocation().getBlock())) {
			if(randor.nextInt(config.getInt("Lightning Chance ")+1)==0) {
				Location l = p.getLocation();
				l.getWorld().strikeLightning(l.add((getRandValue() * randor.nextInt(160)), 1, (getRandValue() * randor.nextInt(160))));
			}
		}
		}
		catch(Exception e) {
			
		}
	}
	
	public int getRandValue() {
		if(randor.nextBoolean()==true) {
			return 1;
		}
		else {
			return -1;
		}
	}
	//
	
	//Ambient Sounds
	
	@EventHandler
	public void onCoralFade(BlockFadeEvent e) {
		if (inHell(e.getBlock())) {
			if(e.getBlock().getType().name().toLowerCase().contains("coral")) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onWalke(PlayerMoveEvent event) {
		if (config.getBoolean("Enable Ambient Sounds ")==true) {
			Player p = event.getPlayer();
			if(worlds.contains(event.getPlayer().getWorld().getName())) {
			if (!(((int) event.getFrom().getX() == (int) event.getTo().getX())
					&& ((int) event.getFrom().getY() == (int) event.getTo().getY())
					&& ((int) event.getFrom().getZ() == (int) event.getTo().getZ()))) {
				doFootSteps(event.getPlayer());
			}
			}
		}
	}
	
	public void doFootSteps(Player p) {
		try {
		int var = 2;
		if ((!p.isSneaking())) {
			org.bukkit.block.Block b = p.getLocation().subtract(0, 1, 0).getBlock();
			Material type = b.getType();
			if (type == Material.SOUL_SAND && randor.nextInt(3)==1) {
				if(p.isSprinting()) {
					if(randor.nextInt(2)==1) {
						if (randor.nextBoolean() == true) {
							p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SCREAM, (float) 1, 0);
						} else {
							p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SCREAM, (float) 1, 2);
						}
					}
				}
				else if(randor.nextInt(3)==1) {
				if (randor.nextBoolean() == true) {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_AMBIENT, (float) 0.07, 0);
				} else {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_AMBIENT, (float) 0.07, 2);
				}
				}
				return;
			}
			else if ((type == Material.NETHERRACK || type == Material.MOSSY_COBBLESTONE) && randor.nextInt(2) == 1) {
				if(!config.getString("Netherrack Step Sound ").equals("NONE")) {
				p.getWorld().playSound(p.getLocation(), Sound.valueOf(config.getString("Netherrack Step Sound ")), (float) 0.08, (float) 1.3);
				return;
				}
			}
		}
		}
		catch(Exception ee) {
			
		}
	}

	public void doSound(Player p) {
		if (config.getBoolean("Enable Ambient Sounds ")==false) {
			return;
		}
		if (inHell(p.getLocation().getBlock())) {
			Location l = p.getLocation();
			for(int i = 0; i < 10; i++) {
				Material mm = l.add(0, 1, 0).getBlock().getType();
				boolean air = l.getBlock().isPassable();
				if(air == false){
					if(mm==Material.NETHERRACK||mm==Material.MAGMA_BLOCK||mm==Material.SOUL_SAND) {
					}
					else {
						return;
					}
				}
			}
			if (randor.nextInt(155) == 1) {
				int random = randor.nextInt(2);
				boolean enderman = config.getBoolean("Enderman Growl replace Enderdragon Growl ");
				if(enderman == false) {
				try {
				if (random == 1) {
					p.playSound(p.getLocation().add(8 + (randor.nextInt(2) + 1), 6, 8 + (randor.nextInt(2) + 1)),
							Sound.valueOf("ENTITY_ENDERDRAGON_GROWL"), (float) getRandLowVolume(), randor.nextInt(2));
				} else {
					p.playSound(p.getLocation().subtract(8 + (randor.nextInt(2) + 1), 6, 8 + (randor.nextInt(2) + 1)),
							Sound.valueOf("ENTITY_ENDERDRAGON_GROWL"), (float) getRandLowVolume(), randor.nextInt(2));
				}
				}
				catch(Exception e) {
					try {
						if (random == 1) {
							p.playSound(p.getLocation().add(8 + (randor.nextInt(2) + 1), 6, 8 + (randor.nextInt(2) + 1)),
									Sound.valueOf("ENTITY_ENDER_DRAGON_GROWL"), (float) getRandLowVolume(), randor.nextInt(2));
						} else {
							p.playSound(p.getLocation().subtract(8 + (randor.nextInt(2) + 1), 6, 8 + (randor.nextInt(2) + 1)),
									Sound.valueOf("ENTITY_ENDER_DRAGON_GROWL"), (float) getRandLowVolume(), randor.nextInt(2));
						}
					}
					catch(Exception e2) {
						
					}
				}
				}
				else {
					try {
						if (random == 1) {
							p.playSound(p.getLocation().add(8 + (randor.nextInt(2) + 1), 6, 8 + (randor.nextInt(2) + 1)),
									Sound.valueOf("ENTITY_ENDERMAN_STARE"), (float) getRandLowVolume(), randor.nextInt(2));
						} else {
							p.playSound(p.getLocation().subtract(8 + (randor.nextInt(2) + 1), 6, 8 + (randor.nextInt(2) + 1)),
									Sound.valueOf("ENTITY_ENDERMAN_STARE"), (float) getRandLowVolume(), randor.nextInt(2));
						}
					}
					catch(Exception e) {
							
					}	
				}
			}
			if(p.getLocation().getY()<70) {
			if (randor.nextInt(10) == 1) {
				if (randor.nextInt(2) == 1) {
					p.playSound(p.getLocation().subtract(8 + (randor.nextInt(2) + 1), 6, 8 + (randor.nextInt(2) + 1)),
							Sound.BLOCK_FIRE_AMBIENT, (float) (getRandLowVolume() + .6), randor.nextInt(2));
				} else {
					p.playSound(p.getLocation().add(8 + (randor.nextInt(2) + 1), 4, 8 + (randor.nextInt(2) + 1)),
							Sound.BLOCK_FIRE_AMBIENT, (float) (getRandLowVolume() + .6), randor.nextInt(2));
				}
			}
			if (randor.nextInt(18) == 1) {
				if (randor.nextInt(2) == 1) {
					p.playSound(p.getLocation().subtract(8 + (randor.nextInt(2) + 1), 6, 8 + (randor.nextInt(2) + 1)),
							Sound.BLOCK_LAVA_AMBIENT, (float) (getRandLowVolume() + .4), randor.nextInt(2));
				} else {
					p.playSound(p.getLocation().add(8 + (randor.nextInt(2) + 1), 4, 8 + (randor.nextInt(2) + 1)),
							Sound.BLOCK_LAVA_AMBIENT, (float) (getRandLowVolume() + .4), randor.nextInt(2));
				}
			}
			if (randor.nextInt(24) == 1) {
				int time1 = randor.nextInt(20) + 30;
				int time2 = randor.nextInt(20) + 30 + time1;
				int time3 = randor.nextInt(20) + 30 + time2;
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(
								p.getLocation().add(5 + (randor.nextInt(2) + 1), 1, 5 + (randor.nextInt(2) + 1)),
								Sound.BLOCK_FURNACE_FIRE_CRACKLE, (float) (getRandLowVolume() + .8), randor.nextInt(2)),
						time1);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(
								p.getLocation().add(5 + (randor.nextInt(2) + 1), 1, 5 + (randor.nextInt(2) + 1)),
								Sound.BLOCK_FURNACE_FIRE_CRACKLE, (float) (getRandLowVolume() + .8), randor.nextInt(2)),
						time2);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(
								p.getLocation().add(5 + (randor.nextInt(2) + 1), 1, 5 + (randor.nextInt(2) + 1)),
								Sound.BLOCK_FURNACE_FIRE_CRACKLE, (float) (getRandLowVolume() + .8), randor.nextInt(2)),
						time3);

			}
			if (randor.nextInt(15) == 1) {
				//Sound.valueOf("ENTITY_IRON_GOLEM_HURT")
				try {
					Sound s = Sound.valueOf("ENTITY_IRONGOLEM_HURT");
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(p.getLocation(), Sound.valueOf("ENTITY_IRONGOLEM_HURT"), (float) 0.01, 0), 3);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(p.getLocation(), Sound.valueOf("ENTITY_IRONGOLEM_HURT"), (float) 0.01, 0), 9);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(p.getLocation(), Sound.valueOf("ENTITY_IRONGOLEM_HURT"), (float) 0.01, 0), 24);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(p.getLocation(), Sound.valueOf("ENTITY_IRONGOLEM_HURT"), (float) 0.001, 0), 38);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(p.getLocation(), Sound.valueOf("ENTITY_IRONGOLEM_HURT"), (float) 0.01, 0), 50);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(p.getLocation(), Sound.valueOf("ENTITY_IRONGOLEM_HURT"), (float) 0.01, 0), 67);
				}
				catch(Exception e) {
					Bukkit.getScheduler().runTaskLater(this,
							() -> p.playSound(p.getLocation(), Sound.valueOf("ENTITY_IRON_GOLEM_HURT"), (float) 0.01, 0), 3);
					Bukkit.getScheduler().runTaskLater(this,
							() -> p.playSound(p.getLocation(), Sound.valueOf("ENTITY_IRON_GOLEM_HURT"), (float) 0.01, 0), 9);
					Bukkit.getScheduler().runTaskLater(this,
							() -> p.playSound(p.getLocation(), Sound.valueOf("ENTITY_IRON_GOLEM_HURT"), (float) 0.01, 0), 24);
					Bukkit.getScheduler().runTaskLater(this,
							() -> p.playSound(p.getLocation(), Sound.valueOf("ENTITY_IRON_GOLEM_HURT"), (float) 0.001, 0), 38);
					Bukkit.getScheduler().runTaskLater(this,
							() -> p.playSound(p.getLocation(), Sound.valueOf("ENTITY_IRON_GOLEM_HURT"), (float) 0.01, 0), 50);
					Bukkit.getScheduler().runTaskLater(this,
							() -> p.playSound(p.getLocation(), Sound.valueOf("ENTITY_IRON_GOLEM_HURT"), (float) 0.01, 0), 67);
				}
			}
			if (randor.nextInt(18) == 1) {
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, (float) 0.01, 0), 3);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, (float) 0.01, 0), 15);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, (float) 0.01, 0), 25);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, (float) 0.01, 0),
						35);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, (float) 0.01, 0), 45);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, (float) 0.01, 0), 55);
			}
			if (randor.nextInt(34) == 1) {
				int time1 = randor.nextInt(20) + 30;
				int time2 = randor.nextInt(20) + 30 + time1;
				int time3 = randor.nextInt(20) + 30 + time2;
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(
								p.getLocation().add(7 + (randor.nextInt(2) + 1), 1, 7 + (randor.nextInt(2) + 1)),
								Sound.ENTITY_GUARDIAN_FLOP, (float) (getRandLowVolume()), 0),
						time1);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(
								p.getLocation().add(7 + (randor.nextInt(2) + 1), 1, 7 + (randor.nextInt(2) + 1)),
								Sound.ENTITY_GUARDIAN_FLOP, (float) (getRandLowVolume()), 0),
						time2);
				Bukkit.getScheduler().runTaskLater(this,
						() -> p.playSound(
								p.getLocation().add(7 + (randor.nextInt(2) + 1), 1, 7 + (randor.nextInt(2) + 1)),
								Sound.ENTITY_GUARDIAN_FLOP, (float) (getRandLowVolume()), 0),
						time3);
			}
			if (randor.nextInt(54) == 1) {
				p.playSound(p.getLocation().add(1, 1, 1), Sound.ENTITY_HORSE_BREATHE, (float) (.1), 0);
			}
		}
		}
	}
	
	public void doWind(Player p) {
		try {
		if ((config.getBoolean("Enable Ambient Sounds ")==false)||(!(p.getLocation().getY()>=config.getInt("Low Y Value Wind ")&&p.getLocation().getY()<=config.getInt("High Y Value Wind ")))) {
			return;
		}
		if (inHell(p.getLocation().getBlock())) {
			Location l = p.getLocation();
			for(int i = 0; i < 10; i++) {
				Material mm = l.add(0, 1, 0).getBlock().getType();
				if(mm!=Material.AIR){
					if(mm==Material.NETHERRACK||mm==Material.MAGMA_BLOCK||mm==Material.SOUL_SAND) {
					}
					else {
						return;
					}
				}
			}
				if (randor.nextInt(2) == 1) {
					p.playSound(p.getLocation().add(0, config.getDouble("Wind Volume - lower equals louder - "), 0),
							Sound.ITEM_ELYTRA_FLYING, SoundCategory.RECORDS, (float) 100, (float) config.getDouble("Wind Pitch - must be between 0 and 2 - "));//.25, 0);
				} else {
					p.playSound(p.getLocation().add(0, config.getDouble("Wind Volume - lower equals louder - "), 0),
							Sound.ITEM_ELYTRA_FLYING, SoundCategory.RECORDS, (float) 100, (float) config.getDouble("Wind Pitch - must be between 0 and 2 - "));//.25, 0);
				}

		}
		}
		catch(Exception ee) {
			
		}
	}
	
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		try {
		if(worlds.contains(e.getPlayer().getWorld().getName())) {
			Player p = e.getPlayer();
			List<BlockPopulator> bp = new ArrayList<BlockPopulator>(p.getWorld().getPopulators());
			boolean contains = false;
			for(BlockPopulator bps : bp) {
				if(bps.toString().length()>=24) {
					if(bps.toString().substring(0, 24).equals("mainPack.NetherGenerator")) {
						contains = true;
						break;
					}
				}
			}
			if(contains == false) {
				p.getWorld().getPopulators().add(new NetherGenerator());
		   }
		}
		if (config.getBoolean("Enable Ambient Sounds ")==false) {
			return;
		}
		if(worlds.contains(e.getPlayer().getWorld().getName())) {
			Player p = e.getPlayer();
			if(p.getLocation().getY()>=70) {
			if (randor.nextInt(2) == 1) {
				p.playSound(p.getLocation().add(0, 800, 0),
						Sound.ITEM_ELYTRA_FLYING, (float) 100, 0);//.25, 0);
			} else {
				p.playSound(p.getLocation().add(0, 800, 0),
						Sound.ITEM_ELYTRA_FLYING, (float) 100, 0);//.25, 0);
			}
			}
		}
		}
		catch(Exception ee) {
			
		}
	}

	public double getRandLowVolume() {
		int chose = randor.nextInt(4);
		if (chose == 0) {
			return .1;
		} else if (chose == 1) {
			return .2;
		} else if (chose == 2) {
			return .3;
		} else if (chose == 3) {
			return .4;
		} else {
			return .1;
		}
	}

	//
	
	//SoulSand Slowness + Magma Lava
	
	@EventHandler
	public void onWalk(PlayerMoveEvent event) {
	if(config.getBoolean("Soulsand Slowness ") == true) {
		try {
	Player p = event.getPlayer();
	if(!worlds.contains(p.getWorld().getName())) {
		return;
	}
	World wor = p.getWorld();
	if(randor.nextInt(2)==0) {
		if(p.isSprinting()) {
			if(p.getLocation().subtract(0, 1, 0).getBlock().getType()==Material.SOUL_SAND) {
				if(inHell(p.getLocation().getBlock())) {
					if (randor.nextBoolean() == true) {
						p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SCREAM, (float) 1, 0);
					} else {
						p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SCREAM, (float) 1, 2);
					}
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, randor.nextInt(200)+40, 3, false, false));
				}
			}
		}
	}
	if(randor.nextInt(config.getInt("Netherack Melting Chance ")+1)==0) {
		if(p.getLocation().subtract(0, 1, 0).getBlock().getType()==Material.NETHERRACK){
			if(inHell(p.getLocation().getBlock())) {
				Location newL = p.getLocation().subtract(0, 1, 0);
				Material xp = newL.clone().add(1, 0, 0).getBlock().getType();
				Material xn = newL.clone().subtract(1, 0, 0).getBlock().getType();
				Material zp = newL.clone().add(0, 0, 1).getBlock().getType();
				Material zn = newL.clone().subtract(0, 0, 1).getBlock().getType();
				Location newL2 = p.getLocation();
				Material xp2 = newL2.clone().add(1, 0, 0).getBlock().getType();
				Material xn2 = newL2.clone().subtract(1, 0, 0).getBlock().getType();
				Material zp2 = newL2.clone().add(0, 0, 1).getBlock().getType();
				Material zn2 = newL2.clone().subtract(0, 0, 1).getBlock().getType();
				if((xp == Material.LAVA)||(xn == Material.LAVA)||(zn == Material.LAVA)||(zp == Material.LAVA)) {
					p.getLocation().subtract(0,1,0).getBlock().setType(Material.MAGMA_BLOCK);
				}
				else if((xp2 == Material.LAVA)||(xn2 == Material.LAVA)||(zn2 == Material.LAVA)||(zp2 == Material.LAVA)) {
					p.getLocation().subtract(0,1,0).getBlock().setType(Material.MAGMA_BLOCK);
				}
			}
		}
	}
		}
		catch(Exception error) {
			console.sendMessage(ChatColor.RED + "Uh oh error inside moving.");
		}
	}
	}
	
	//
	
	//Netherack Fire
	
	@EventHandler
	public void onBreakB(BlockBreakEvent dr) {
		Player p = dr.getPlayer();
		if(!worlds.contains(p.getWorld().getName())) {
			return;
		}
		World wor = p.getWorld();
		if(config.getBoolean("Netherrack Fires ")==true) {
			try {
				if(randor.nextInt(config.getInt("Netherrack Fire Chance ")+1)==0) {
					if(dr.getBlock().getType()==Material.NETHERRACK) {
						Bukkit.getScheduler().runTaskLater(this, () -> dr.getBlock().setType(Material.FIRE), randor.nextInt(5)+5);
					}
				}
			}
			catch(Exception error) {
				console.sendMessage(ChatColor.RED + "Uh oh error inside cave-in.");
			}
		}
	}
	
	//
	
	//Nether Mobs
	
	public String mobTypes() {
		int choice = randor.nextInt(8);
		try {
		if(choice == 0) {
			if(config.getBoolean("Spawn Molten ")==true) {
			return config.getString("Molten = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 1) {
			if(config.getBoolean("Spawn Fireball ")==true) {
			return config.getString("Fireball = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 2) {
			if(config.getBoolean("Spawn Inferno ")==true) {
			return config.getString("Inferno = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 3) {
			if(config.getBoolean("Spawn Old Shadow ")==true) {
			return config.getString("Old Shadow = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 4) {
			if(config.getBoolean("Spawn Sadness ")==true) {
			return config.getString("Sadness = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 5) {
			if(config.getBoolean("Spawn Sherogath ")==true) {
			return config.getString("Sherogath = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 6) {
			if(config.getBoolean("Spawn Necromancer ")==true) {
			return config.getString("Necromancer = ");
			}
			else {
				return "";
			}
		}
		else if(choice == 7) {
			if(config.getBoolean("Spawn Alpha Pigman ")==true) {
			return config.getString("Alpha Pigman = ");
			}
			else {
				return "";
			}
		}
		return "";
		}
		catch(Exception error) {
			console.sendMessage(ChatColor.RED + "Uh oh error inside mob names.");
			return "";
		}
	}
	
	public void doMobSpawns(Entity entitye) {
			LivingEntity e = (LivingEntity) entitye;
			String name = mobTypes();
			if (e != null && !e.isDead()) {
				try {
				if(name.equals(config.getString("Fireball = "))) {
					Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.SKELETON);
					e.remove();
					e = (LivingEntity) e2;
					e.setSilent(true);
					EntityEquipment ee = ((LivingEntity) e).getEquipment();
					/*ItemStack myAwesomeSkull = new ItemStack(Material.PLAYER_HEAD, 1);
					SkullMeta myAwesomeSkullMeta = (SkullMeta) myAwesomeSkull.getItemMeta();
					myAwesomeSkullMeta.setOwner("Spe");
					myAwesomeSkull.setItemMeta(myAwesomeSkullMeta);*/
					//ee.setHelmet(myAwesomeSkull);
					ee.setHelmet(getSkull("http://textures.minecraft.net/texture/b6965e6a58684c277d18717cec959f2833a72dfa95661019dbcdf3dbf66b048"));
					ee.setHelmetDropChance(0);
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
					e.setMetadata("nethermob", new FixedMetadataValue(this, 0));
				}
				else if(name.equals(config.getString("Necromancer = "))) {
					if(e.getType()!=EntityType.WITHER_SKELETON) {
					Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.WITHER_SKELETON);
					e.remove();
					e = (LivingEntity) e2;
					}
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
					e.setMetadata("nethermob", new FixedMetadataValue(this, 0));
					EntityEquipment ee = (e).getEquipment();
					ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
					LeatherArmorMeta lch6 = (LeatherArmorMeta) lchest.getItemMeta();
					lch6.setColor(Color.fromRGB(38, 33, 36));
					lchest.setItemMeta(lch6);
					ItemStack lchest3 = new ItemStack(Material.LEATHER_BOOTS, 1);
					LeatherArmorMeta lch62 = (LeatherArmorMeta) lchest3.getItemMeta();
					lch62.setColor(Color.fromRGB(38, 33, 36));
					lchest3.setItemMeta(lch62);
					/*ItemStack myAwesomeSkull = new ItemStack(Material.PLAYER_HEAD, 1);
					SkullMeta myAwesomeSkullMeta = (SkullMeta) myAwesomeSkull.getItemMeta();
					myAwesomeSkullMeta.setOwner("MHF_WSkeleton");
					myAwesomeSkull.setItemMeta(myAwesomeSkullMeta);
					ee.setHelmet(myAwesomeSkull);*/
					ee.setHelmet(getSkull("http://textures.minecraft.net/texture/ee280cefe946911ea90e87ded1b3e18330c63a23af5129dfcfe9a8e166588041"));
					ee.setChestplate(lchest);
					ee.setBoots(lchest3);
					ee.setItemInMainHand(new ItemStack(Material.BOW));
				}
				else if(name.equals(config.getString("Alpha Pigman = "))) {
					if(e.getType()!=EntityType.PIG_ZOMBIE) {
					Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.PIG_ZOMBIE);
					e.remove();
					e = (LivingEntity) e2;
					}
					EntityEquipment ee = (e).getEquipment();
					ee.setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
					e.setMetadata("nethermob", new FixedMetadataValue(this, 0));
				}
				else if(name.equals(config.getString("Sherogath = "))) {
					if(e.getType()!=EntityType.ZOMBIE_VILLAGER) {
					//Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
					//((Zombie) e2).setBaby(true);
					Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE_VILLAGER);
					((ZombieVillager) e2).setBaby(true);
					e.remove();
					e = (LivingEntity) e2;
					}
					((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
					((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 1));
					e.setSilent(true);
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
					e.setMetadata("nethermob", new FixedMetadataValue(this, 0));
				}
				else if(name.equals(config.getString("Molten = "))) {
					if(e.getType()!=EntityType.ZOMBIE) {
						Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
						e.remove();
						e = (LivingEntity) e2;
					}
					EntityEquipment ee = (e).getEquipment();
					e.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 100, false, false));
					e.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 100, false, false));
					ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
					LeatherArmorMeta lch6 = (LeatherArmorMeta) lchest.getItemMeta();
					lch6.setColor(Color.fromRGB(252, 115, 69));
					lchest.setItemMeta(lch6);
					ItemStack lchest2 = new ItemStack(Material.LEATHER_BOOTS, 1);
					LeatherArmorMeta lch61 = (LeatherArmorMeta) lchest2.getItemMeta();
					lch61.setColor(Color.fromRGB(252, 115, 69));
					lchest2.setItemMeta(lch61);
					ItemStack lchest3 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
					LeatherArmorMeta lch62 = (LeatherArmorMeta) lchest3.getItemMeta();
					lch62.setColor(Color.fromRGB(252, 115, 69));
					lchest3.setItemMeta(lch62);
					ee.setItemInMainHand(new ItemStack(Material.FIRE, 1));
					ee.setItemInOffHand(new ItemStack(Material.FIRE, 1));
					ee.setChestplate(lchest);
					ee.setLeggings(lchest3);
					ee.setBoots(lchest2);
					e.setFireTicks(999999);
					e.setCustomName(name);
					e.setSilent(true);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
					e.setMetadata("nethermob", new FixedMetadataValue(this, 0));
				}
				else if(name.equals(config.getString("Sadness = "))) {
					if(e.getType()!=EntityType.ZOMBIE) {
						Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
						e.remove();
						e = (LivingEntity) e2;
					}
					((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
					((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 200));
					((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 2));
					e.setSilent(true);
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
					e.setMetadata("nethermob", new FixedMetadataValue(this, 0));
				}
				else if(name.equals(config.getString("Old Shadow = "))) {
					if(e.getType()!=EntityType.ZOMBIE) {
						Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
						e.remove();
						e = (LivingEntity) e2;
					}
					((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
					e.setSilent(true);
					e.setCustomName(name);
					e.setMetadata(name, new FixedMetadataValue(this, 0));
					e.setMetadata("R", new FixedMetadataValue(this, 0));
					e.setMetadata("nethermob", new FixedMetadataValue(this, 0));
				}
				else if(name.equals(config.getString("Inferno = "))) {
					Entity scream = e.getWorld().spawnEntity(e.getLocation().add(0, 1, 0), EntityType.ZOMBIE_VILLAGER);
					((ZombieVillager) scream).setBaby(true);
					EntityEquipment ee = ((Monster) scream).getEquipment();
					/*ItemStack myAwesomeSkull = new ItemStack(Material.PLAYER_HEAD, 1);
					SkullMeta myAwesomeSkullMeta = (SkullMeta) myAwesomeSkull.getItemMeta();
					myAwesomeSkullMeta.setOwner("crolin");
					myAwesomeSkull.setItemMeta(myAwesomeSkullMeta);
					ee.setHelmet(myAwesomeSkull);*/
					ee.setHelmet(getSkull("http://textures.minecraft.net/texture/f142a35ac0b055ed50a5cbf870b6ef1cc1f94e2642b9ba650c9e0385e6cbe36"));
					Entity scream2 = e.getWorld().spawnEntity(e.getLocation().add(0, 1, 0), EntityType.BAT);
					((LivingEntity) scream).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
					((LivingEntity) scream).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 200));
					((LivingEntity) scream2).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
					scream2.setSilent(true);
					scream2.setPassenger(scream);
					((LivingEntity) scream).setHealth(3);
					e.remove();
					scream.setCustomName(name);
					scream.setSilent(true);
					scream.setMetadata(name, new FixedMetadataValue(this, 0));
					scream.setMetadata("R", new FixedMetadataValue(this, 0));
					e.setMetadata("nethermob", new FixedMetadataValue(this, 0));
				}
				}
				catch(Exception error) {
					console.sendMessage(ChatColor.RED + "Uh oh error inside dressing mob.");
				}
			}
				
			return;
	}
	
	@EventHandler
	public void entityHit(EntityDamageByEntityEvent event) {
		if(event.isCancelled()) {
			return;
		}
		if(!worlds.contains(event.getEntity().getWorld().getName())) {
			return;
		}
		World wor = event.getEntity().getWorld();
		if (event.getDamager() instanceof Monster && event.getEntity() instanceof Player) {
			if(hasName(config.getString("Molten = "), event.getDamager())) {
				if (randor.nextInt(2) == 1) {
					((LivingEntity) event.getEntity()).setFireTicks(60);
				}
				return;
			}
			else if(hasName(config.getString("Alpha Pigman = "), event.getDamager())) {
				((LivingEntity) event.getEntity())
				.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 75, 1));
				return;
			}
			else if(hasName(config.getString("Sherogath = "), event.getDamager())) {
				lagPlayer((LivingEntity) event.getEntity(), 0, randor.nextInt(10)+5);
				return;
			}
		}
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Monster) {
			if(randor.nextInt(6)==1) {
				if(hasName(config.getString("Sadness = "), event.getEntity())) {
					if(randor.nextInt(3)==1) {
						event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.ENTITY_WOLF_DEATH, 1, 0);
					}
					if(((LivingEntity) event.getEntity()).hasPotionEffect(PotionEffectType.SLOW)) {
						((LivingEntity) event.getEntity()).removePotionEffect(PotionEffectType.SLOW);
					}
					return; 
				}
			}
		}
	}
	
	@EventHandler
	public void testShoot(EntityShootBowEvent event) {
		if(!worlds.contains(event.getEntity().getWorld().getName())) {
			return;
		}
		World wor = event.getEntity().getWorld();
		if(!event.isCancelled()) {
				if(hasName(config.getString("Necromancer = "), event.getEntity())) {
					Vector v2 = event.getProjectile().getVelocity();
					event.getProjectile().remove();
					event.getEntity().launchProjectile(WitherSkull.class, v2);
					return;
				}
				else if(hasName(config.getString("Fireball = "), event.getEntity())) {
					Vector v2 = event.getProjectile().getVelocity();
					event.getProjectile().remove();
					event.getEntity().launchProjectile(SmallFireball.class, v2);
					return;
				}
		}
	}

	public ItemStack getSkull(String url) {
	        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
	        if(url.isEmpty())return head;
	       
	       
	        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
	        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
	        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
	        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
	        Field profileField = null;
	        try {
	            profileField = headMeta.getClass().getDeclaredField("profile");
	            profileField.setAccessible(true);
	            profileField.set(headMeta, profile);
	        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
	            e1.printStackTrace();
	        }
	        head.setItemMeta(headMeta);
	        return head;
	    }
	
	public void lagPlayer(LivingEntity p, int time, int maxTime) {
		if((!exists(p)) || (time >= maxTime)) {
			return;
		}
		else {
			p.teleport(p.getLocation().add(coinFlip()*(randor.nextInt(5)/30.0), 0, coinFlip()*(randor.nextInt(5)/30.0)));
			Bukkit.getScheduler().runTaskLater(this, () -> lagPlayer(p, time + 1, maxTime), randor.nextInt(6)+2);
		}
	}
	
	//
	
	//Lava Burns + Better Placement
	
	@EventHandler
	public void onPlace(PlayerBucketEmptyEvent e) {
		if(!e.isCancelled()) {
		if(config.getBoolean("Enable Lava Placement ")==true) {
			Player p = e.getPlayer();
			try {
			if(worlds.contains(p.getWorld().getName())) {
				if(e.getBucket()!=null) {
					if(e.getBucket()==Material.LAVA_BUCKET) {
						Location loc = e.getBlockClicked().getRelative(e.getBlockFace()).getLocation();
						int radius = 2;
						int cx = loc.getBlockX();
						int cy = loc.getBlockY();
						int cz = loc.getBlockZ();
						for (int x = cx - radius; x <= cx + radius; x++) {
							for (int z = cz - radius; z <= cz + radius; z++) {
								for (int y = (cy - radius); y < (cy + radius); y++) {
									double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

									if (dist < radius * radius) {
											Location l2 = new Location(loc.getWorld(), x, y, z);
											if(l2.getBlock().getType()==Material.NETHERRACK) {
												l2.getBlock().setType(Material.LAVA);
											}
									}
								}
							}
						}
					}
				}
			}
			}
			catch(Exception ee) {
				
			}
		}
		}
	}
	
	@EventHandler
	public void onFill(PlayerBucketFillEvent e) {
		if(!e.isCancelled()) {
		if(config.getBoolean("Enable Lava Placement ")==true) {
			Player p = e.getPlayer();
			try {
			if(worlds.contains(p.getWorld().getName())) {
				if(e.getBlockClicked()!=null) {
					if(e.getBlockClicked().getType()==Material.LAVA) {
						Location loc = e.getBlockClicked().getLocation();
						int radius = 2;
						int cx = loc.getBlockX();
						int cy = loc.getBlockY();
						int cz = loc.getBlockZ();
						for (int x = cx - radius; x <= cx + radius; x++) {
							for (int z = cz - radius; z <= cz + radius; z++) {
								for (int y = (cy - radius); y < (cy + radius); y++) {
									double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

									if (dist < radius * radius) {
											Location l2 = new Location(loc.getWorld(), x, y, z);
											if(l2.getBlock().getType()==Material.LAVA) {
												l2.getBlock().setType(Material.AIR);
											}
									}
								}
							}
						}
					}
				}
			}
			}
			catch(Exception ee) {
				
			}
		}
		}
	}
	
	@EventHandler
	public void onLavaHeld(PlayerItemHeldEvent e) {
		try {
		if(config.getBoolean("Enable Lava Burns ")==true) {
		Player p = e.getPlayer();
		if(worlds.contains(p.getWorld().getName())) {
			if(p.getInventory().getItem(e.getNewSlot())!=null) {
		Material m = p.getInventory().getItem(e.getNewSlot()).getType();
			if(m == Material.LAVA_BUCKET) {
				if(inHell(p.getLocation().getBlock())) {
					if(!effectEnts.contains(p)) {
					effectEnts.add(p);
					}
				}
			}
			}
		}
		}
		}
		catch(Exception ee) {
			
		}
	}
	
	//
	
	//Mean Ghasts
	
	@EventHandler
	public void onGhastLaunch(ProjectileLaunchEvent event) {
		try {
		if(config.getBoolean("Enable Mean Ghasts ")==true) {
		Entity e = event.getEntity();
		if(worlds.contains(e.getWorld().getName())) {
		if(e instanceof Fireball) {
			if(((Fireball) e).getShooter() instanceof Ghast) {
				Location newL = ((Fireball) e).getLocation().clone();
				((Fireball) e).setYield(((Fireball) e).getYield()*3);
				e.setVelocity(e.getVelocity().add(new Vector(e.getVelocity().getX()*2, 0, e.getVelocity().getZ()*2)));
				if(randor.nextBoolean()==true) {
					((Ghast) ((Fireball) e).getShooter()).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 100, false, false));
					(((Fireball) e)).getWorld().spawnParticle(Particle.CLOUD,((Ghast) ((Fireball) e).getShooter()).getLocation(), 100);
				}
			}
		}
		}
		}
		}
		catch(Exception ee) {
			
		}
	}
	
	//
	
	//Collapsing Nether
	
	public Block getNetherBlock(Player p) {
		try {
		Block b = null;
			Location l = p.getLocation().clone();
			int ammount = randor.nextInt(3)+1;
			int direction = randor.nextInt(4);
			for(int i = 80; i > 0; i--) {
				if(l.getBlock().getType()==Material.NETHERRACK||l.getBlock().getType()==Material.SOUL_SAND||l.getBlock().getType()==Material.GLOWSTONE) {
					Location l2 = l.clone();
					if(l2.subtract(0, 1, 0).getBlock().getType()==Material.AIR) {
					b = l.getBlock();
					return b;
					}
				}
				if(direction == 0) {
					l.add(ammount, 0, 0);
				}
				else if(direction == 1) {
					l.subtract(ammount, 0, 0);
				}
				else if(direction == 2) {
					l.add(0, 0, ammount);
				}
				else if(direction == 3) {
					l.subtract(0, 0, ammount);
				}
				l.add(0, 1, 0);
			}
		return b;
		}
		catch(Exception ee) {
		return null;
		}
	}
	
	public void collapseNether(Player p) {
		try {
		if(!worlds.contains(p.getWorld().getName())) {
			return;
		}
		World wor = p.getWorld();
		if(config.getBoolean("Enable Falling Nether ")==true) {
			try {
		if(randor.nextInt(config.getInt("Falling Nether Chance")+1)==0) {
			Block b1 = getNetherBlock(p);
			if(b1 == null) {
				return;
			}
			if(b1.getLocation().distanceSquared(p.getLocation())<70) {
				return;
			}
			Location loc = b1.getLocation();
			int radius = 5;
			int cx = loc.getBlockX();
			int cy = loc.getBlockY();
			int cz = loc.getBlockZ();
			for (int x = cx - radius; x <= cx + radius; x++) {
				for (int z = cz - radius; z <= cz + radius; z++) {
					for (int y = (cy - radius); y < (cy + radius); y++) {
						double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

						if (dist < radius * radius) {
							Location l = new Location(loc.getWorld(), x, y + 2, z);
							Block b = l.getBlock();
							if(b.getType()==Material.AIR||b.getType()==Material.BEDROCK) {
							}
							else {
								b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
								l.getBlock().setType(Material.AIR);
							}
						}
					}
				}
			}
			p.getWorld().playSound(b1.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
			p.getWorld().createExplosion(b1.getLocation(), 5);
		}
			}
			catch(Exception error) {
				console.sendMessage(ChatColor.RED + "Uh oh error inside cave-in.");
			}
		}
		}
		catch(Exception ee) {
			
		}
	}
	
	//
	
	//Artifacts
	
	@EventHandler
	public void doSpook(EntityDeathEvent event) {
		if(event.getEntity() instanceof PigZombie) {
			try {
			if(config.getBoolean("Enable Alpha Pigman Sword Drop ")==true&&worlds.contains(event.getEntity().getWorld().getName())) {
		boolean isMagma = false;
		if((event.getEntity()).hasMetadata(config.getString("Alpha Pigman = "))) {
			isMagma = true;
		}
		else if(event.getEntity().getCustomName() != null) {
			if (event.getEntity().getCustomName().equals(config.getString("Alpha Pigman = "))){
				isMagma = true;
			}
		}
		if(isMagma == true) {
			if (randor.nextInt(config.getInt("Alpha Pigman Sword Drop Chance ")+1) == 0) {
				ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
				ItemMeta itemmeta = item.getItemMeta();
				if(randor.nextBoolean()==true) {
				itemmeta.setLore(Arrays.asList(ChatColor.GRAY + "Blinding I"));
				}
				else {
					itemmeta.setLore(Arrays.asList(ChatColor.GRAY + "Blinding II"));
				}
				item.setItemMeta(itemmeta);
				((LivingEntity) event.getEntity()).getWorld().dropItemNaturally(event.getEntity().getLocation(),
						item);
			}
			return;
		}
			}
			}
			catch(Exception ee) {
				
			}
		}
	}

	@EventHandler
	public void onHitByPlayer(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			try {
		if(event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
			ItemStack it = ((Player) event.getDamager()).getInventory().getItemInMainHand();
			if(it != null) {
				if(it.hasItemMeta()) {
					if(it.getItemMeta().hasLore()) {
						LivingEntity e = ((LivingEntity) event.getEntity());
						List<String> lore = it.getItemMeta().getLore();
						if(lore.contains(ChatColor.GRAY + "Blinding I")) {
							e.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 200));
							return;
						}
						else if(lore.contains(ChatColor.GRAY + "Blinding II")) {
							e.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 200));
							return;
						}
					}
				}
			}
		}
			}
			catch(Exception ee) {
				
			}
		}
	}
	
	@EventHandler
	public void onPotionDrops(EntityDeathEvent event) {
		World wor = event.getEntity().getWorld();
		try {
		if(worlds.contains(wor.getName())) {
		if (event.getEntity() instanceof PigZombie) {
			if(config.getBoolean("Haste Potion Drops ")) {
			if (randor.nextInt(config.getInt("Haste Potion Drop Chance ")+1) == 0) {
				int choice = randor.nextInt(3);
				if (choice == 0) {
					int choice2 = randor.nextInt(3);
					if(choice2 == 0) {
					ItemStack item = new ItemStack(Material.POTION);
					PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
					potionMeta.setColor(Color.YELLOW);
					potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2400, 0), true);
					potionMeta.setDisplayName("§fPotion of Haste");
					item.setItemMeta(potionMeta);
					if (wor != null) {
						wor.dropItemNaturally(event.getEntity().getLocation(), item);
					}
					}
					else if(choice2 == 1) {
						ItemStack item = new ItemStack(Material.POTION);
						PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
						potionMeta.setColor(Color.YELLOW);
						potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2400, 1), true);
						potionMeta.setDisplayName("§fPotion of Haste");
						item.setItemMeta(potionMeta);
						if (wor != null) {
							wor.dropItemNaturally(event.getEntity().getLocation(), item);
						}
					}
					else if(choice2 == 2) {
						if(randor.nextInt(2)==1) {
							ItemStack item = new ItemStack(Material.POTION);
							PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
							potionMeta.setColor(Color.YELLOW);
							potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 4800, 3), true);
							potionMeta.setDisplayName("§fPotion of Haste");
							item.setItemMeta(potionMeta);
							if (wor != null) {
								wor.dropItemNaturally(event.getEntity().getLocation(), item);
							}
						}
					}
				} else if (choice == 1) {
					ItemStack item = new ItemStack(Material.LINGERING_POTION);
					PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
					potionMeta.setColor(Color.YELLOW);
					potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2400, 0), true);
					potionMeta.setDisplayName("§fLingering Potion of Haste");
					item.setItemMeta(potionMeta);
					if (wor != null) {
						wor.dropItemNaturally(event.getEntity().getLocation(), item);
					}
				} else if (choice == 2) {
					ItemStack item = new ItemStack(Material.SPLASH_POTION);
					PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
					potionMeta.setColor(Color.YELLOW);
					potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2400, 0), true);
					potionMeta.setDisplayName("§fSplash Potion of Haste");
					item.setItemMeta(potionMeta);
					if (wor != null) {
						wor.dropItemNaturally(event.getEntity().getLocation(), item);
					}
				}
			}
		}
		}else if (event.getEntity() instanceof WitherSkeleton) {
			if(config.getBoolean("Wither Potion Drops ")) {
			if (randor.nextInt(config.getInt("Wither Potion Drop Chance ")+1) == 0) {
				int choice = randor.nextInt(4);
				if (choice == 0) {
					ItemStack item = new ItemStack(Material.LINGERING_POTION);
					PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
					potionMeta.setColor(Color.BLACK);
					potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 1800, 0), true);
					potionMeta.setDisplayName("§fLingering Potion of Wither");
					item.setItemMeta(potionMeta);
					if (wor != null) {
						wor.dropItemNaturally(event.getEntity().getLocation(), item);
					}
				} else if (choice == 1) {
					ItemStack item = new ItemStack(Material.SPLASH_POTION);
					PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
					potionMeta.setColor(Color.BLACK);
					potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 1800, 0), true);
					potionMeta.setDisplayName("§fSplash Potion of Wither");
					item.setItemMeta(potionMeta);
					if (wor != null) {
						wor.dropItemNaturally(event.getEntity().getLocation(), item);
					}
				}
				else if (choice == 2) {
					ItemStack item = new ItemStack(Material.SPLASH_POTION);
					PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
					potionMeta.setColor(Color.BLACK);
					potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 1800, 1), true);
					potionMeta.setDisplayName("§fSplash Potion of Wither");
					item.setItemMeta(potionMeta);
					if (wor != null) {
						wor.dropItemNaturally(event.getEntity().getLocation(), item);
					}
				}
				else if (choice == 3) {
					ItemStack item = new ItemStack(Material.LINGERING_POTION);
					PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
					potionMeta.setColor(Color.BLACK);
					potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 1800, 1), true);
					potionMeta.setDisplayName("§fLingering Potion of Wither");
					item.setItemMeta(potionMeta);
					if (wor != null) {
						wor.dropItemNaturally(event.getEntity().getLocation(), item);
					}
				}
			}
			}
		}
		}
		}
		catch(Exception ee) {
			
		}
	}
	
	//
	
	public boolean inHell(Block b) {
		try {
		if(b.getBiome()==Biome.valueOf("HELL")) {
			return true;
		}
		else {
			return false;
		}
		}
		catch(Exception e) {
			try{
			if(b.getBiome()==Biome.valueOf("NETHER")) {
				return true;
			}
			else {
				return false;
			}
			}
			catch(Exception e2) {
				return false;
			}
		}
	}
	
	@EventHandler
	public void entityOnFireNether(EntityCombustEvent e) {
		if(e.getEntity() instanceof LivingEntity) {
			if(worlds.contains(e.getEntity().getWorld().getName())) {
				if(e.getEntity().getLocation().getBlock().getBiome() == Biome.NETHER) {
					e.setDuration(e.getDuration() * 2);
				}
			}
		}
	}

	public void testPlayerHandItem() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(worlds.contains(p.getWorld().getName())) {
				ItemStack i = p.getInventory().getItemInMainHand();
				ItemStack i2 = p.getInventory().getItemInOffHand();
				if(i != null) {
					if(i.getType() == Material.WATER_BUCKET || i.getType().name().toLowerCase().contains("snow") || i.getType().name().toLowerCase().contains("ice")) {
						if(p.getFireTicks() > 0) {
							if(p.getLocation().getBlock().getType() != Material.LAVA || p.getLocation().getBlock().getType() != Material.FIRE) {
								p.setFireTicks(0);
								p.getWorld().playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
							}
						}
						if(randor.nextInt(3) == 0) {
							if(i.getType() == Material.WATER_BUCKET) {
								if(randor.nextInt(3) == 0) {
									p.getInventory().getItemInMainHand().setType(Material.BUCKET);
									p.getWorld().playSound(p.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 1);
								}
							}
							else {
								removeItemNaturally(p, true);
								p.getWorld().playSound(p.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, (float) 0.1, 1);
							}
						}
					}
				}
				if(i2 != null) {
					if(i2.getType() == Material.WATER_BUCKET || i2.getType().name().toLowerCase().contains("snow") || i2.getType().name().toLowerCase().contains("ice")) {
						if(p.getFireTicks() > 0) {
							if(p.getLocation().getBlock().getType() != Material.LAVA || p.getLocation().getBlock().getType() != Material.FIRE) {
								p.setFireTicks(0);
								p.getWorld().playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
							}
						}
						if(randor.nextInt(3) == 0) {
							if(i2.getType() == Material.WATER_BUCKET) {
								if(randor.nextInt(3) == 0) {
									p.getInventory().getItemInMainHand().setType(Material.BUCKET);
									p.getWorld().playSound(p.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 1);
								}
							}
							else {
								removeItemNaturally(p, false);
								p.getWorld().playSound(p.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, (float) 0.1, 1);
							}
						}
					}
				}
			}
		}
	}
	
	//Fire
	
	List<LivingEntity> onFire = new ArrayList<LivingEntity>();

	@EventHandler
	public void onSetOnFire(EntityCombustEvent e) {
		if(worlds.contains(e.getEntity().getWorld().getName()) && newFire) {
			if(e.getEntity().getLocation().getBlock().getBiome() == Biome.NETHER) {
				if(e.getEntity() instanceof LivingEntity && (!(e.getEntity().hasMetadata("player")))) {
					if((e instanceof Blaze || e instanceof Ghast || e instanceof WitherSkeleton || e instanceof PigZombie) || hasName(config.getString("Molten = "), e.getEntity())) {
						return;
					}
					onFire.add((LivingEntity) e.getEntity());
				}
			}
		}
	}
	
	public boolean isValid(LivingEntity e) {
		if(e == null) {
			return false;
		}
		if(e.isDead()) {
			return false;
		}
		return true;
	}
	
	public void fireLoop() {
		List<LivingEntity> onFireCopy = new ArrayList<LivingEntity>(onFire);
		for(LivingEntity e : onFireCopy) {
			if(isValid(e)) {
				if(e.getFireTicks() > 0) {
					if(e instanceof Blaze || e instanceof Ghast || e instanceof WitherSkeleton || e instanceof PigZombie) {
						onFire.remove(e);
					}
					else if(e instanceof Enderman || e instanceof Player || e instanceof Zombie || e instanceof Skeleton || e instanceof Illager || e instanceof Creeper || e instanceof Witch) {
						if(randor.nextInt(2)==0) {
						e.getWorld().spawnParticle(Particle.FLAME, e.getLocation().add(0, 1, 0), (randor.nextInt(3)+1), 0.2, 0.5, 0.2, 0.01);
						}
						if(randor.nextInt(3)==0) {
						e.getWorld().spawnParticle(Particle.SMOKE_NORMAL, e.getLocation().add(0, 1, 0), (randor.nextInt(3)+1), 0.2, 0.5, 0.2, 0.01);
						}
						if(randor.nextInt(5)==0) {
							e.getWorld().spawnParticle(Particle.LAVA, e.getLocation(), (randor.nextInt(2)+1), 0.1, 0, 0.1, 0.01);
						}
						if(randor.nextInt(10)==0) {
							e.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, e.getLocation().add(0, 1, 0), (randor.nextInt(2)+1), 0.2, 0.5, 0.2, 0.01);
						}
					}
					else {
						BoundingBox bb = e.getBoundingBox();
						double heightAdd = bb.getHeight() / 2.0;
						double width = (bb.getWidthX() + bb.getWidthZ()) / 2.0;
						if(randor.nextInt(2)==0) {
						e.getWorld().spawnParticle(Particle.FLAME, e.getLocation().add(0, heightAdd, 0), (randor.nextInt(3)+1), width/3.0, heightAdd/2.0, width/3.0, 0.015);
						}
						if(randor.nextInt(3)==0) {
						e.getWorld().spawnParticle(Particle.SMOKE_NORMAL, e.getLocation().add(0, heightAdd, 0), (randor.nextInt(3)+1), width/3.0, heightAdd/2.0, width/3.0, 0.015);
						}
						if(randor.nextInt(5)==0) {
							e.getWorld().spawnParticle(Particle.LAVA, e.getLocation(), (randor.nextInt(2)+1), width/4.0, 0, width/4.0, 0.015);
						}
						if(randor.nextInt(10)==0) {
							e.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, e.getLocation().add(0, heightAdd, 0), (randor.nextInt(2)+1), width/3.0, heightAdd/2.0, width/3.0, 0.015);
						}
					}
				}
				else {
					onFire.remove(e);
				}
			}
			else {
				onFire.remove(e);
			}
		}
	}
	
	//
	
	public void getBlockSkull(String url, Location l) {
		Block b = l.getBlock();
        b.setType(Material.PLAYER_HEAD, false);
        
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
       
        TileEntitySkull skullTile = (TileEntitySkull)((CraftWorld)b.getWorld()).getHandle().getTileEntity(new BlockPosition(b.getX(), b.getY(), b.getZ()));
        skullTile.setGameProfile(profile);
        b.getState().update(true);
	}
	
	public boolean canPlaceMushroom(Location l, Chunk org) {
		Chunk c = org;
		if(nextToEdge(l.clone().add(1, 0, 0), c, false)) {
			return false;
		}
		if(nextToEdge(l.clone().subtract(1, 0, 0), c, false)) {
			return false;
		}
		if(nextToEdge(l.clone().subtract(0, 0, 1), c, false)) {
			return false;
		}
		if(nextToEdge(l.clone().add(0, 0, 1), c, false)) {
			return false;
		}
		return true;
	}
	
	public boolean nextToEdge(Location l, Chunk org, boolean skipMinus) {
		int minus = 0;
		if(!skipMinus) {
			if(l.getX() <= 0 || l.getZ() <=0) {
				minus = 1;
			}
		}
		Chunk c = org;
		int cX = c.getX() * 16;
		int cZ = c.getZ() * 16;
		if((cX + minus) >= l.getBlockX()) {
			return true;
		}
		else if(((cX + 15) - minus) <= l.getBlockX()) {
			return true;
		}
		if((cZ + minus) >= l.getBlockZ()) {
			return true;
		}
		else if(((cZ + 15) - minus) <= l.getBlockZ()) {
			return true;
		}
		return false;
	}
	
}
