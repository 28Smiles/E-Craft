package de.smiles.ecraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import de.ecraft.ECraft;
import de.ecraft.blocks.Boiler;
import de.ecraft.blocks.BoilerMB;
import de.ecraft.blocks.SolidFuelBurner;
import de.ecraft.blocks.SteamPipe;
import de.ecraft.blocks.StirlingEngine;

public class ECraftBlocks {
	
	public static void init() {
		
	}
	
	public static void register() {
		
	}
	
	public static void registerRenders() {
		
	}
	
	public static void registerRenderer(Block block, int meta) {
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(ECraft.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
