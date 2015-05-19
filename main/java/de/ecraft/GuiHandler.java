package de.ecraft;

import de.ecraft.container.ContainerBoiler;
import de.ecraft.container.ContainerSolidFuelBurner;
import de.ecraft.gui.GuiBoiler;
import de.ecraft.gui.GuiSolidFuelBurner;
import de.ecraft.tileentitys.TileEntityBoiler;
import de.ecraft.tileentitys.TileEntitySolidFuelBurner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		switch(id) {
			case 0:return new GuiSolidFuelBurner(player.inventory, (TileEntitySolidFuelBurner)world.getTileEntity(new BlockPos(x, y, z)));
			case 1:return new GuiBoiler(player.inventory, (TileEntityBoiler)world.getTileEntity(new BlockPos(x, y, z)));
			default: return null;
		}
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		switch(id) {
			case 0: return new ContainerSolidFuelBurner((TileEntitySolidFuelBurner)world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
			case 1: return new ContainerBoiler((TileEntityBoiler)world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
		default: return null;
		}
	}

}
