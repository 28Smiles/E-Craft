package de.ecraft.blocks;

import de.ecraft.ECraft;
import de.ecraft.tileentitys.TileEntitySolidFuelBurner;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class SolidFuelBurner extends Block implements ITileEntityProvider {

	protected SolidFuelBurner() {
		super(Material.rock);
		setUnlocalizedName("solid_fuel_burner");
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof IInventory)
		{
			IInventory inv = (IInventory) tileEntity;
			InventoryHelper.dropInventoryItems(world, pos, inv);
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			TileEntity tile_entity = world.getTileEntity(pos);

			if (tile_entity instanceof TileEntitySolidFuelBurner)
			{
				player.openGui(ECraft.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}
	
	public void onBlockPlacedBy(World p_onBlockPlacedBy_1_, BlockPos p_onBlockPlacedBy_2_, IBlockState p_onBlockPlacedBy_3_, EntityLivingBase p_onBlockPlacedBy_4_, ItemStack p_onBlockPlacedBy_5_)
	  {
	    if (p_onBlockPlacedBy_5_.hasDisplayName())
	    {
	      TileEntity localTileEntity = p_onBlockPlacedBy_1_.getTileEntity(p_onBlockPlacedBy_2_);
	      if ((localTileEntity instanceof TileEntitySolidFuelBurner)) {
	        ((TileEntitySolidFuelBurner)localTileEntity).setCustomInventoryName(p_onBlockPlacedBy_5_.getDisplayName());
	      }
	    }
	  }

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		return new TileEntitySolidFuelBurner();
	}
}
