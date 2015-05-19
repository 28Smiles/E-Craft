package de.ecraft.container;

import de.ecraft.tileentitys.TileEntityStirlingEngine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerStirlingEngine extends Container {

	private TileEntityStirlingEngine tile;
	
	public ContainerStirlingEngine(TileEntityStirlingEngine tileentity, IInventory playerInventory) {
		tile = tileentity;
		
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 84));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer arg0) {
		return tile.isUseableByPlayer(arg0);
	}
	
}
