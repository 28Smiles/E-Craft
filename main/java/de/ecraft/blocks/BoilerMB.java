package de.ecraft.blocks;

import de.ecraft.tileentitys.TileEntityBoiler;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BoilerMB extends Block implements ITileEntityProvider {

	public static final IProperty STATE = PropertyInteger.create("state", 0, 15);
	
	protected BoilerMB() {
		super(Material.iron);
		setUnlocalizedName("boiler_mb");
		setDefaultState(this.blockState.getBaseState().withProperty(STATE, 0));
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos bp, IBlockState arg2, Block arg3) {
		if(world.getTileEntity(bp) != null)
			((TileEntityBoiler)world.getTileEntity(bp)).neightbourChange(world, bp);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos bp, IBlockState bs, EntityPlayer entity) {
		if(world.getTileEntity(bp) != null)
			((TileEntityBoiler)world.getTileEntity(bp)).onBlockHarvested(world, bp, entity);
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
	public boolean isFullCube() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public IBlockState getStateFromMeta(int m) {
		return getDefaultState().withProperty(STATE, m);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Integer) state.getValue(STATE);
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

}
