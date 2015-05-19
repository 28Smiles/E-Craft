package de.ecraft.items;

import de.ecraft.ECraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

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
