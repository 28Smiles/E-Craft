package de.ecraft.api.conduit.steam;

import java.util.List;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface ISteamNetworkPart {
	
	default public List<BlockPos> updateNetwork(World world, BlockPos pos, List<BlockPos> list) {
		if(!list.contains(pos))
			if(world.getTileEntity((pos)) instanceof ISteamNetworkPart) {
				list.add(pos);
				if((world.getTileEntity((pos.north())) instanceof ISteamNetworkPart))
					if(!list.contains(pos.north()))
						list = ((ISteamNetworkPart)world.getTileEntity(pos.north())).updateNetwork(world, pos.north(), list);
				if((world.getTileEntity((pos.south())) instanceof ISteamNetworkPart))
					if(!list.contains(pos.south()))
						list = ((ISteamNetworkPart)world.getTileEntity(pos.south())).updateNetwork(world, pos.south(), list);
				if((world.getTileEntity((pos.west())) instanceof ISteamNetworkPart))
					if(!list.contains(pos.west()))
						list = ((ISteamNetworkPart)world.getTileEntity(pos.west())).updateNetwork(world, pos.west(), list);
				if((world.getTileEntity((pos.east())) instanceof ISteamNetworkPart))
					if(!list.contains(pos.east()))
						list = ((ISteamNetworkPart)world.getTileEntity(pos.east())).updateNetwork(world, pos.east(), list);
				if((world.getTileEntity((pos.up())) instanceof ISteamNetworkPart))
					if(!list.contains(pos.up()))
						list = ((ISteamNetworkPart)world.getTileEntity(pos.up())).updateNetwork(world, pos.up(), list);
				if((world.getTileEntity((pos.down())) instanceof ISteamNetworkPart))
					if(!list.contains(pos.down()))
						list = ((ISteamNetworkPart)world.getTileEntity(pos.down())).updateNetwork(world, pos.down(), list);
			}
		return list;
	}
	
	default public List<BlockPos>[] searchAll(World world, BlockPos pos, List<BlockPos>[] list) {
		if(!list[0].contains(pos))
			if(world.getTileEntity((pos)) instanceof ISteamPipe) {
				list[0].add(pos);
				if((world.getTileEntity((pos.north())) instanceof ISteamPipe))
					if(!list[0].contains(pos.north()))
						list = ((ISteamPipe)world.getTileEntity(pos.north())).searchAll(world, pos.north(), list);
				if((world.getTileEntity((pos.south())) instanceof ISteamPipe))
					if(!list[0].contains(pos.south()))
						list = ((ISteamPipe)world.getTileEntity(pos.south())).searchAll(world, pos.south(), list);
				if((world.getTileEntity((pos.west())) instanceof ISteamPipe))
					if(!list[0].contains(pos.west()))
						list = ((ISteamPipe)world.getTileEntity(pos.west())).searchAll(world, pos.west(), list);
				if((world.getTileEntity((pos.east())) instanceof ISteamPipe))
					if(!list[0].contains(pos.east()))
						list = ((ISteamPipe)world.getTileEntity(pos.east())).searchAll(world, pos.east(), list);
				if((world.getTileEntity((pos.up())) instanceof ISteamPipe))
					if(!list[0].contains(pos.up()))
						list = ((ISteamPipe)world.getTileEntity(pos.up())).searchAll(world, pos.up(), list);
				if((world.getTileEntity((pos.down())) instanceof ISteamPipe))
					if(!list[0].contains(pos.down()))
						list = ((ISteamPipe)world.getTileEntity(pos.down())).searchAll(world, pos.down(), list);
				
				if((world.getTileEntity((pos.north())) instanceof ISteamSink))
					if(!list[1].contains(pos.north()))
						list[1].add(pos.north());
				if((world.getTileEntity((pos.south())) instanceof ISteamSink))
					if(!list[1].contains(pos.south()))
						list[1].add(pos.south());
				if((world.getTileEntity((pos.west())) instanceof ISteamSink))
					if(!list[1].contains(pos.west()))
						list[1].add(pos.west());
				if((world.getTileEntity((pos.east())) instanceof ISteamSink))
					if(!list[1].contains(pos.east()))
						list[1].add(pos.east());
				if((world.getTileEntity((pos.up())) instanceof ISteamSink))
					if(!list[1].contains(pos.up()))
						list[1].add(pos.up());
				if((world.getTileEntity((pos.down())) instanceof ISteamSink))
					if(!list[1].contains(pos.down()))
						list[1].add(pos.down());
				
				if((world.getTileEntity((pos.north())) instanceof ISteamSource))
					if(!list[2].contains(pos.north()))
						list[2].add(pos.north());
				if((world.getTileEntity((pos.south())) instanceof ISteamSource))
					if(!list[2].contains(pos.south()))
						list[2].add(pos.south());
				if((world.getTileEntity((pos.west())) instanceof ISteamSource))
					if(!list[2].contains(pos.west()))
						list[2].add(pos.west());
				if((world.getTileEntity((pos.east())) instanceof ISteamSource))
					if(!list[2].contains(pos.east()))
						list[2].add(pos.east());
				if((world.getTileEntity((pos.up())) instanceof ISteamSource))
					if(!list[2].contains(pos.up()))
						list[2].add(pos.up());
				if((world.getTileEntity((pos.down())) instanceof ISteamSource))
					if(!list[2].contains(pos.down()))
						list[2].add(pos.down());
			}
		return list;
	}
}
