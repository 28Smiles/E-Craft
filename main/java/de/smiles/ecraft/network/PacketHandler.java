package de.smiles.ecraft.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import de.ecraft.ECraft;

public class PacketHandler {

	public static final SimpleNetworkWrapper SNW = NetworkRegistry.INSTANCE.newSimpleChannel(ECraft.MODID);
	
	public static void init() {
		
	}
}
