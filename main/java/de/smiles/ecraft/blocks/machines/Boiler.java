package de.smiles.ecraft.blocks.machines;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import de.smiles.ecraft.blocks.EC_Block;
import de.smiles.ecraft.blocks.machines.tileentitys.TileEntityBoiler;
import de.smiles.ecraft.blocks.machines.tileentitys.TileEntityBoilerMB;

public class Boiler extends EC_Block implements ITileEntityProvider {

	public static final IProperty STATE = PropertyInteger.create("state", 0, 15);
	
	public Boiler() {
		super(Material.iron, "boiler");
		setCreativeTab(CreativeTabs.tabBlock);
		setDefaultState(this.blockState.getBaseState().withProperty(STATE, 0));
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Integer) state.getValue(STATE);
	}
	
	@Override
	public IBlockState getStateFromMeta(int m) {
		return getDefaultState().withProperty(STATE, m);
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { STATE });
	}

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		return new TileEntityBoilerMB();
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos bp, IBlockState arg2, Block arg3) {
		if(world.getTileEntity(bp) != null)
			((TileEntityBoiler)world.getTileEntity(bp)).neightbourChange(world, bp);
	}
}
