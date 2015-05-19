package de.ecraft.api.conduit.steam;

import java.util.List;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface ISteamSource extends ISteamNetworkPart {
	
	@Override
	public default List<BlockPos> updateNetwork(World world, BlockPos pos, List<BlockPos> list) {
		if(!list.contains(pos))
			list.add(pos);
		return list;
	}
	
	public abstract float getPressure();
	
	public abstract float drainSteam(EnumFacing face, float amount, boolean doDrain);
	
	public abstract boolean canDrainSteam(EnumFacing face);
}
