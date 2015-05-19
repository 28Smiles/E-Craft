package de.ecraft.api.conduit.steam;

import java.util.List;

import de.ecraft.ConfigHandler;
import de.ecraft.data.DataValues;
import net.minecraft.tileentity.TileEntity;
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
	
	/**
	 * 
	 * @param world
	 * @param sinks
	 * @param amount in L!
	 * @param steam in L!
	 * @param tanksize in L!
	 * @param doDrain
	 * @return used Amount
	 */
	public default float handleSteamOutput(World world, List<BlockPos> sinks, float amount, float steam, float tanksize, boolean doDrain) {
		System.out.println("Steamoutput(amount: " + amount + "): ");
		float usedsteam = 0;
		
		for(BlockPos sinkPos : sinks) {
			TileEntity tile = world.getTileEntity(sinkPos);
			if(tile instanceof ISteamSink) {
				ISteamSink sink = (ISteamSink)tile;
				System.out.println("Sink: " + sinkPos);
				usedsteam += sink.fillSteam(amount / sinks.size(), steam / sinks.size(), DataValues.normalPressure * ((steam + tanksize) / tanksize), tanksize / sinks.size(), true);
			}
		}
		System.out.println(usedsteam);
		float us1 = usedsteam;
		
		if(usedsteam < amount) {
			for(BlockPos sinkPos : sinks) {
				TileEntity tile = world.getTileEntity(sinkPos);
				if(tile instanceof ISteamSink) {
					ISteamSink sink = (ISteamSink)tile;
					usedsteam += sink.fillSteam(amount - usedsteam, steam - usedsteam, DataValues.normalPressure * (((steam - usedsteam) + tanksize) / tanksize), tanksize - usedsteam, true);
				}
			}
		}
		System.out.println(us1);
		float us2 = usedsteam;
		for(int i = 0; i < ConfigHandler.advanced_steam_processing; i++) {
			if(us1 < us2) {
				float us3 = us2;
				for(BlockPos sinkPos : sinks) {
					TileEntity tile = world.getTileEntity(sinkPos);
					if(tile instanceof ISteamSink) {
						ISteamSink sink = (ISteamSink)tile;
						us3 += sink.fillSteam(amount - us3, steam - us3, DataValues.normalPressure * (((steam - us3) + tanksize) / tanksize), tanksize - us3, true);
					}
				}
				System.out.println(us3);
				us1 = us2;
				us2 = us3;
			}
		}
		return usedsteam;
	}
}
