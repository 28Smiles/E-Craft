package de.smiles.ecraft.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import de.ecraft.ECraft;

public class ECraftItems {
	
public static void init() {
		
	}
	
	public static void register() {
		
	}
	
	public static void registerRenders() {
		
	}
	
	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(ECraft.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
