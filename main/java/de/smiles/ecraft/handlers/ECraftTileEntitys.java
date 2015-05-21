package de.smiles.ecraft.handlers;

import de.smiles.ecraft.blocks.machines.tileentitys.TileEntityBoilerMB;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ECraftTileEntitys {

	public static void register() {
		GameRegistry.registerTileEntity(TileEntityBoilerMB.class, "tile_master_boiler");
	}
}