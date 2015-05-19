package de.ecraft.blocks;

import de.ecraft.ECraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ECraftBlocks {

	public static Block solid_fuel_burner;
	public static Block boiler;
	public static Block boilermb;
	
	public static Block steam_pipe;
	
	public static Block stirling_engine;
	
	public static void init() {
		solid_fuel_burner = new SolidFuelBurner();
		boiler = new Boiler();
		boilermb = new BoilerMB();
		steam_pipe = new SteamPipe();
		stirling_engine = new StirlingEngine();
	}
	
	public static void register() {
		GameRegistry.registerBlock(solid_fuel_burner, solid_fuel_burner.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(boiler, boiler.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(boilermb, boilermb.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(steam_pipe, steam_pipe.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(stirling_engine, stirling_engine.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders() {
		registerRenderer(solid_fuel_burner, 0);
		registerRenderer(boiler, 0);
		registerRenderer(boilermb, 0);
		registerRenderer(steam_pipe, 0);
		registerRenderer(stirling_engine, 0);
	}
	
	public static void registerRenderer(Block block, int meta) {
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(ECraft.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
