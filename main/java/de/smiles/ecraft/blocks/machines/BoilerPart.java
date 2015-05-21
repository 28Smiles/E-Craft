package de.smiles.ecraft.blocks.machines;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import de.smiles.ecraft.blocks.EC_Block;
import de.smiles.ecraft.blocks.ECraftBlocks;
import de.smiles.ecraft.blocks.machines.tileentitys.TileEntityBoiler;
import de.smiles.ecraft.blocks.machines.tileentitys.TileEntityBoilerMB;

public class BoilerPart extends EC_Block implements ITileEntityProvider {

	public static final IProperty STATE = PropertyInteger.create("state", 0, 15);
	
	public BoilerPart() {
		super(Material.iron, "boiler_mb");
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
		return new TileEntityBoiler();
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos bp, IBlockState arg2, Block arg3) {
		if(world.getTileEntity(bp) != null)
			((TileEntityBoiler)world.getTileEntity(bp)).neightbourChange(world, bp);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos bp, IBlockState bs, EntityPlayer player, EnumFacing face, float f1, float f2, float f3) {
		if(world.getTileEntity(bp) != null)
			return ((TileEntityBoiler)world.getTileEntity(bp)).onBlockActivated(world, bp, bs, player, face, f1, f2, f3);
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos bp, IBlockState bs, EntityLivingBase entity, ItemStack itemstack) {
		if(world.getTileEntity(bp) != null)
			((TileEntityBoiler)world.getTileEntity(bp)).onBlockPlaced(world, bp, entity);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos bp, IBlockState bs, EntityPlayer entity) {
		if(world.getTileEntity(bp) != null)
			((TileEntityBoiler)world.getTileEntity(bp)).onBlockHarvested(world, bp, entity);
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess p_getDrops_1_, BlockPos p_getDrops_2_, IBlockState p_getDrops_3_, int p_getDrops_4_) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(ECraftBlocks.blocks.get("boiler")));
		return list;
	}
}