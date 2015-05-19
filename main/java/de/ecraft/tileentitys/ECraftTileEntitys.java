package de.ecraft.tileentitys;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ECraftTileEntitys {

	public static void register() {
		GameRegistry.registerTileEntity(TileEntitySolidFuelBurner.class, "ecraftsfb");
		GameRegistry.registerTileEntity(TileEntityBoiler.class, "ecraftboiler");
		GameRegistry.registerTileEntity(TileEntityBoilerMB.class, "ecraftboilermb");
		GameRegistry.registerTileEntity(TileEntitySteamPipe.class, "ecraftsteampipe");
	}
}
