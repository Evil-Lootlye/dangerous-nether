package mainPack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.generator.BlockPopulator;

public class NetherGenerator2  extends BlockPopulator {

	Random randor = new Random();
	BlockFace[] bfs = {BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.SELF};
	
	public void populate(World wor, Random rand, Chunk chnk) {
		if(rand.nextInt(main.structchance+1)==0&&main.nethstruct==true) {
		int typeC = rand.nextInt(1);
		int cX = chnk.getX() * 16;
		int cZ = chnk.getZ() * 16;
		int cXOff = cX;
		int cZOff = cZ;
		if(randor.nextBoolean() && ((rand.nextInt(main.rosechance+1)==0))) {
			if(!Bukkit.getServer().getBukkitVersion().contains("1.13")) {
				//createWitherRoseGrass(cXOff+ rand.nextInt(3) + 6, cZOff+ rand.nextInt(3) + 6, wor);
				createWitherRoseGrass2(cXOff+ rand.nextInt(3) + 6, cZOff+ rand.nextInt(3) + 6, wor);
			}
		}
		else if(rand.nextInt(main.mushroomchance+1)==0){
			//createMushroomGrass(cXOff+ rand.nextInt(3) + 6, cZOff+ rand.nextInt(3) + 6, wor);
			createMushroomGrass2(cXOff+ rand.nextInt(3) + 6, cZOff+ rand.nextInt(3) + 6, wor);
		}
    	}
	}
	
	public List<Integer> getClosestAirB(int cXOff, int cZOff, World w) {
		List<Integer> yvals = new ArrayList<Integer>();
		try {
		Location loc = new Location(w, cXOff, 20, cZOff);
		while(loc.getY()<main.nheight) {
			loc.add(0, 1, 0);
			if(loc.getBlock()!=null) {
			if(loc.getBlock().getType()==Material.AIR) {
				Location loc2 = new Location(w, loc.getX(), loc.getY() - 1, loc.getZ());
				if(loc2.getBlock().getType()==Material.NETHERRACK) {
					yvals.add((int) loc.clone().getY());
				}
			}
			}
		}
		return yvals;
		}
		catch(Exception error) {
			return null;
		}
	}
		
	public void createWitherRoseGrass(int cXOff, int cZOff, World w) {
		try {
			List<Integer> yvals = getClosestAirB(cXOff, cZOff, w);
			if(yvals!=null) {
				for(int y : yvals) {
						for(int count = 0; count < 8; count++) {
							Location org = new Location(w, cXOff, y, cZOff);
						Location l = getRandLoc(new Location(w, cXOff, y, cZOff), 5);
						if(l.getBlock().getType() == Material.AIR && (!l.clone().subtract(0, 1, 0).getBlock().isPassable())) {
							l.getBlock().setType(Material.WITHER_ROSE, false);
						}
						if(randor.nextInt(main.replacerackchance+1)==0) {
							GenerateTile(l, org.getChunk());
						}
						}
				}
			}
		}
		catch(Exception error) {
			
		}
	}
	
	public void createMushroomGrass(int cXOff, int cZOff, World w) {
		try {
			List<Integer> yvals = getClosestAirB(cXOff, cZOff, w);
			if(yvals!=null) {
				for(int y : yvals) {
						for(int count = 0; count < 12; count++) {
							Location org = new Location(w, cXOff, y, cZOff);
						Location l = getRandLoc(new Location(w, cXOff, y, cZOff), 5);
						if(l.getBlock().getType() == Material.AIR && (!l.clone().subtract(0, 1, 0).getBlock().isPassable())) {
							if(randor.nextBoolean()) {
								l.getBlock().setType(Material.BROWN_MUSHROOM, false);
							}
							else {
								l.getBlock().setType(Material.RED_MUSHROOM, false);
							}
							if(randor.nextInt(main.replacerackchance+1)==0) {
							GenerateTile(l, org.getChunk());
							}
						}
						}
				}
			}
		}
		catch(Exception error) {
			
		}
	}
	
	public void createWitherRoseGrass2(int cXOff, int cZOff, World w) {
		try {
			List<Integer> yvals = getClosestAirB(cXOff, cZOff, w);
			if(yvals!=null) {
				for(int y : yvals) {
						for(int count = 0; count < 36; count++) {
						Location org = new Location(w, cXOff, y, cZOff);
						Location l = getRandLoc(new Location(w, cXOff, y, cZOff), 9);
						if(canPlaceBlock(l, org.getChunk())) {
							Block b = l.clone().subtract(0, 1, 0).getBlock();
							if(l.getBlock().getType() == Material.AIR && (b.getType() == Material.NETHERRACK || b.getType() == Material.SOUL_SAND)) {
								l.getBlock().setType(Material.WITHER_ROSE, false);
							}
							if(randor.nextInt(main.replacerackchance+1)==0) {
								if(b.getType() == Material.NETHERRACK) {
								GenerateTile(l, org.getChunk());
								}
							}
						}
					}
				}
			}
		}
		catch(Exception error) {
			
		}
	}
	
	public void createMushroomGrass2(int cXOff, int cZOff, World w) {
		try {
			List<Integer> yvals = getClosestAirB(cXOff, cZOff, w);
			if(yvals!=null) {
				for(int y : yvals) {
						for(int count = 0; count < 36; count++) {
						Location org = new Location(w, cXOff, y, cZOff);
						Location l = getRandLoc(new Location(w, cXOff, y, cZOff), 9);
						Block b = l.clone().subtract(0, 1, 0).getBlock();
						if(canPlaceBlock(l, org.getChunk())) {
							if(l.getBlock().getType() == Material.AIR && (b.getType() == Material.NETHERRACK || b.getType() == Material.SOUL_SAND)) {
							if(randor.nextBoolean()) {
								l.getBlock().setType(Material.BROWN_MUSHROOM, false);
							}
							else {
								l.getBlock().setType(Material.RED_MUSHROOM, false);
							}
							if(randor.nextInt(main.replacerackchance+1)==0) {
								if(b.getType() == Material.NETHERRACK) {
								GenerateTile(l, org.getChunk());
								}
								}
						}
							}
						}
				}
			}
		}
		catch(Exception error) {
			
		}
	}
	
	public void GenerateTile(Location l, Chunk org) {
		Location newL = l.clone().subtract(0, 1, 0);
		for(int count = 0; count < bfs.length; count++) {
			BlockFace bf = bfs[count];
			if(!outsideChunkRelative(bf, newL, org)) {
			Block b = newL.getBlock().getRelative(bf);
			if(b.getType() == Material.NETHERRACK) {
			if(randor.nextBoolean()) {
				
			}
			else if(randor.nextBoolean()) {
				b.setType(Material.FIRE_CORAL_BLOCK, false);
			}
			else if(randor.nextBoolean()) {
				b.setType(Material.NETHER_WART_BLOCK, false);
			}
			}
			}
		}
	}
	
	public boolean outsideChunkRelative(BlockFace bf, Location l, Chunk org) {
		Chunk c = l.getChunk();
		if(nextToEdge(l, org, false)) {
			if(bf == BlockFace.EAST) {
				if(outsideChunk(l.clone().add(1, 0, 0), c)) {
					return true;
				}
			}
			if(bf == BlockFace.WEST) {
				if(outsideChunk(l.clone().subtract(1, 0, 0), c)) {
					return true;
				}
			}
			if(bf == BlockFace.NORTH) {
				if(outsideChunk(l.clone().subtract(0, 0, 1), c)) {
					return true;
				}
			}
			if(bf == BlockFace.SOUTH) {
				if(outsideChunk(l.clone().add(0, 0, 1), c)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Location getRandLoc(Location l, int radi) {
		if(l!=null) {
		double radius = radi;
		double x0 = l.getX();
		double y0 = l.getY();
		double z0 = l.getZ();
			   double u = Math.random();
			   double v = Math.random();
			   double theta = 2 * Math.PI * u;
			   double phi = Math.acos(2 * v - 1);
			   double x = x0 + (radius * Math.sin(phi) * Math.cos(theta));
			   double y = y0;
			   double z = z0 + (radius * Math.cos(phi));
			   return new Location(l.getWorld(), x, y, z, randor.nextInt(360), randor.nextInt(360));
		}
		return null;
	}
	
	public boolean canPlaceBlock(Location l, Chunk org) {
		if(outsideChunk(l, org)) {
			return false;
		}
		if(l.getX() <= 0 || l.getZ() <= 0) {
			if(nextToEdge(l, org, true)) {
				return false;
			}
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
	
	public boolean outsideChunk(Location l, Chunk org) {
		Chunk c = org;
		int cX = c.getX() * 16;
		int cZ = c.getZ() * 16;
		int x = ((int) l.getX());
		int z = ((int) l.getZ());
		if((cX) > x) {
			return true;
		}
		else if((cX+15) < x) {
			return true;
		}
		if((cZ) > z) {
			return true;
		}
		else if((cZ+15) < z) {
			return true;
		}
		return false;
	}
	
	public boolean isAir(Material m) {
		if(m == Material.AIR || m == Material.CAVE_AIR || m == Material.VOID_AIR) {
			return true;
		}
		return false;
	}
	
}
