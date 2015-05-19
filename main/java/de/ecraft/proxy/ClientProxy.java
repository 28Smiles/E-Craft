package de.ecraft.proxy;

import de.ecraft.blocks.ECraftBlocks;
import de.ecraft.items.ECraftItems;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers() {
		ECraftItems.registerRenders();
		ECraftBlocks.registerRenders();
	}
}
