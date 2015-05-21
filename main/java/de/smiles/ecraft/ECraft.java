package de.smiles.ecraft;

import de.smiles.ecraft.blocks.ECraftBlocks;
import de.smiles.ecraft.handlers.ECraftTileEntitys;
import de.smiles.ecraft.handlers.ConfigHandler;
import de.smiles.ecraft.handlers.ECraftCrafting;
import de.smiles.ecraft.handlers.GuiHandler;
import de.smiles.ecraft.handlers.InputHandler;
import de.smiles.ecraft.items.ECraftItems;
import de.smiles.ecraft.network.PacketHandler;
import de.smiles.ecraft.proxy.CommonProxy;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = ECraft.MODID, version = ECraft.VERSION)
public class ECraft {
	
	public static final String MODID = "ecraft";
    public static final String VERSION = "1.0.0";
    
    @Instance(ECraft.MODID)
    public static ECraft INSTANCE;
    
    public static final String CLIENT_PROXY = "de.smiles.ecraft.proxy.ClientProxy";
    public static final String SERVER_PROXY = "de.smiles.ecraft.proxy.CommonProxy";
    
    @SidedProxy(clientSide = ECraft.CLIENT_PROXY, serverSide = ECraft.SERVER_PROXY)
    public static CommonProxy proxy;
    
    public static GuiHandler gui_handler;
    public static InputHandler key_handler;
    public static ConfigHandler config_handler;
    
    @EventHandler
    public void init(FMLPreInitializationEvent event)
    {
    	/** Config */
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config_handler = new ConfigHandler(config);
		config_handler.init();
		
		/** Block and Item Registering */
		ECraftBlocks.init();
		ECraftItems.init();
		ECraftBlocks.register();
		ECraftItems.register();
		
		/** Packet Handler Init */
		PacketHandler.init();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	/** Render Registering */
    	proxy.registerRenderers();
    	
    	/** GUI Handler Registering */
    	gui_handler = new GuiHandler();
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, gui_handler);
    	
    	/** Crafting Recipes */
		ECraftCrafting.register();
    	
    	/** TileEntity Registering */
    	ECraftTileEntitys.register();
    	
    	if(event.getSide() == Side.CLIENT) {
    		key_handler = new InputHandler();
    		FMLCommonHandler.instance().bus().register(key_handler);
    	}
    }
    
    @EventHandler
    public void init(FMLPostInitializationEvent event)
    {
    	
    }
}
