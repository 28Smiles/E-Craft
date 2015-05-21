package de.smiles.ecraft.proxy;

import de.smiles.ecraft.blocks.ECraftBlocks;
import de.smiles.ecraft.items.ECraftItems;


public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		ECraftItems.registerRenders();
		ECraftBlocks.registerRenders();
		super.registerRenderers();
	}
}
