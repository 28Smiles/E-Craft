package de.smiles.ecraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class EC_Block extends Block {

	protected EC_Block(Material material, String name) {
		super(material);
		setUnlocalizedName(name);
		init(name);
	}
	
	private void init(String name) {
		ECraftBlocks.blocks.put(name, this);
	}
}
