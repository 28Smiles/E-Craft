package de.ecraft.container;

import de.ecraft.tileentitys.TileEntitySolidFuelBurner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSolidFuelBurner extends Container {
	
	private TileEntitySolidFuelBurner tile;
	
	public ContainerSolidFuelBurner(TileEntitySolidFuelBurner tileentity, IInventory playerInventory) {
		tile = tileentity;
		this.addSlotToContainer(new Slot(tile, 0, 80, 43));
		
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
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		Slot slot = getSlot(i);

		  if(slot != null && slot.getHasStack()) {
		    ItemStack itemstack = slot.getStack();
		    ItemStack result = itemstack.copy();

		    if(i >= 36) {
		      if(!mergeItemStack(itemstack, 0, 36, false)) {
		        return null;
		      }
		    } else if(!mergeItemStack(itemstack, 36, 36 + this.tile.getSizeInventory(), false)) {
		      return null;
		    }

		    if(itemstack.stackSize == 0) {
		      slot.putStack(null);
		    } else {
		      slot.onSlotChanged();
		    }
		    slot.onPickupFromSlot(player, itemstack); 
		    return result;
		  }
		  return null;
	}
}
