package de.smiles.ecraft.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import de.smiles.ecraft.ECraft;
import de.smiles.ecraft.api.register.annotations.CustomRenderer;
import de.smiles.ecraft.blocks.machines.Boiler;

public class ECraftBlocks {
	
	public static Map<String, Block> blocks = new HashMap<String, Block>();
	
	public static void init() {
		new Boiler();
	}
	
	public static void register() {
		for(Block block : blocks.values()) {
			GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
		}
	}
	
	public static void registerRenders() {
		for(Block block : blocks.values()) {
			CustomRenderer crenderer = block.getClass().getAnnotation(CustomRenderer.class);
			
			if(crenderer != null) {
				try {
					ClientRegistry.bindTileEntitySpecialRenderer(crenderer.tileentity(), crenderer.renderer().newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					ECraft.logger.log(Level.WARNING, "Failed to register special-renderer for " + block.getUnlocalizedName().substring(5) + ": \n", e);
				}
				registerRenderer(block, 0);
			} else {
				registerRenderer(block, 0);
			}
		}
	}
	
	public static void registerRenderer(Block block, int meta) {
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(ECraft.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
	
	public static void registerRenderer(Block block, String name, int meta) {
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(ECraft.MODID + ":" + name, "inventory"));
	}
}
