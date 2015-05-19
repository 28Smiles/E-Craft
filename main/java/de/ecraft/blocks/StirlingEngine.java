package de.ecraft.blocks;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;

import de.ecraft.api.conduit.steam.ISteamNetworkPart;
import de.ecraft.api.conduit.steam.ISteamPipe;
import de.ecraft.tileentitys.TileEntityBoiler;
import de.ecraft.tileentitys.TileEntityStirlingEngine;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class StirlingEngine extends Block implements ITileEntityProvider {
	
	public static final IProperty FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
	
	public StirlingEngine() {
		super(Material.iron);
		setUnlocalizedName("stirling_engine");
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	public IBlockState onBlockPlaced(final World w, final BlockPos coord, final EnumFacing face, final float partialX, final float partialY, final float partialZ, final int i, final EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState bs) {
		List<BlockPos> list = new ArrayList<BlockPos>();
		list.add(pos);
		if((world.getTileEntity((pos.north())) instanceof ISteamNetworkPart))
			((ISteamPipe)world.getTileEntity(pos.north())).updateNetwork(world, pos.north(), list);
		if((world.getTileEntity((pos.south())) instanceof ISteamNetworkPart))
			((ISteamPipe)world.getTileEntity(pos.south())).updateNetwork(world, pos.south(), list);
		if((world.getTileEntity((pos.west())) instanceof ISteamNetworkPart))
			((ISteamPipe)world.getTileEntity(pos.west())).updateNetwork(world, pos.west(), list);
		if((world.getTileEntity((pos.east())) instanceof ISteamNetworkPart))
			((ISteamPipe)world.getTileEntity(pos.east())).updateNetwork(world, pos.east(), list);
		if((world.getTileEntity((pos.up())) instanceof ISteamNetworkPart))
			((ISteamPipe)world.getTileEntity(pos.up())).updateNetwork(world, pos.up(), list);
		if((world.getTileEntity((pos.down())) instanceof ISteamNetworkPart))
			((ISteamPipe)world.getTileEntity(pos.down())).updateNetwork(world, pos.down(), list);
		super.onBlockAdded(world, pos, bs);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState arg2, EntityPlayer arg3) {
		List<BlockPos> list = new ArrayList<BlockPos>();
		list.add(pos);
		if((world.getTileEntity((pos.north())) instanceof ISteamNetworkPart))
			((ISteamPipe)world.getTileEntity(pos.north())).updateNetwork(world, pos.north(), list);
		if((world.getTileEntity((pos.south())) instanceof ISteamNetworkPart))
			((ISteamPipe)world.getTileEntity(pos.south())).updateNetwork(world, pos.south(), list);
		if((world.getTileEntity((pos.west())) instanceof ISteamNetworkPart))
			((ISteamPipe)world.getTileEntity(pos.west())).updateNetwork(world, pos.west(), list);
		if((world.getTileEntity((pos.east())) instanceof ISteamNetworkPart))
			((ISteamPipe)world.getTileEntity(pos.east())).updateNetwork(world, pos.east(), list);
		if((world.getTileEntity((pos.up())) instanceof ISteamNetworkPart))
			((ISteamPipe)world.getTileEntity(pos.up())).updateNetwork(world, pos.up(), list);
		if((world.getTileEntity((pos.down())) instanceof ISteamNetworkPart))
			((ISteamPipe)world.getTileEntity(pos.down())).updateNetwork(world, pos.down(), list);
		super.onBlockHarvested(world, pos, arg2, arg3);
	}
	
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}

    @Override
    public int getMetaFromState(final IBlockState bs) {
        int i = ((EnumFacing)bs.getValue(FACING)).getIndex();
        return i;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { FACING });
    }
    
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityStirlingEngine();
	}
}
