package de.ecraft.api.conduit.steam;

import java.util.List;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface ISteamSink extends ISteamNetworkPart {

	@Override
	public default List<BlockPos> updateNetwork(World world, BlockPos pos, List<BlockPos> list) {
		if(!list.contains(pos))
			list.add(pos);
		return list;
	}
	
	public abstract float fillSteam(float steam, boolean doFill);
	
	public abstract float getSteam();
	
	public abstract float getPressure();
}
