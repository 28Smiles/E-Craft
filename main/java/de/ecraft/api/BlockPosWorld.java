package de.ecraft.api;

import net.minecraft.world.World;

public class BlockPosWorld {
	
	private int x, y, z;
	private World world;
	
	public BlockPosWorld(World world, int x, int y, int z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "BlockPosWorld{world=" + world + ", x=" + x + ", y=" + y + ", z=" + z + "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BlockPosWorld) {
			BlockPosWorld comp = (BlockPosWorld)obj;
			if(comp.world == this.world && comp.x == this.x && comp.y == this.y && comp.z == this.z)
				return true;
		}
		return false;
	}
}
