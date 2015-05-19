package de.ecraft.blocks;

import java.util.ArrayList;
import java.util.List;

import de.ecraft.api.conduit.steam.IBlockSteam;
import de.ecraft.api.conduit.steam.ISteamNetworkPart;
import de.ecraft.api.conduit.steam.ISteamPipe;
import de.ecraft.api.conduit.steam.ISteamSink;
import de.ecraft.api.conduit.steam.ISteamSource;
import de.ecraft.tileentitys.TileEntitySteamPipe;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SteamPipe extends Block implements ITileEntityProvider, IBlockSteam {
	
	public static final IProperty NORTH = PropertyBool.create("north");
	public static final IProperty SOUTH = PropertyBool.create("south");
	public static final IProperty WEST = PropertyBool.create("west");
	public static final IProperty EAST = PropertyBool.create("east");
	public static final IProperty DOWN = PropertyBool.create("down");
	public static final IProperty UP = PropertyBool.create("up");
	
	public SteamPipe() {
		super(Material.iron);
		setUnlocalizedName("steam_pipe");
		setDefaultState(this.blockState.getBaseState().withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(WEST, false)
				.withProperty(EAST, false).withProperty(DOWN, false).withProperty(UP, false));
	}
	
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing arg2, float arg3, float arg4, float arg5, int arg6, EntityLivingBase arg7) {
		return getActualState(getDefaultState(), world, pos);
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
    public IBlockState getActualState(final IBlockState bs, final IBlockAccess world, final BlockPos coord) {
        return bs.withProperty(WEST, this.canConnectTo(world,coord,EnumFacing.WEST, coord.west()))
        		 .withProperty(DOWN, this.canConnectTo(world,coord,EnumFacing.DOWN, coord.down()))
        		 .withProperty(SOUTH, this.canConnectTo(world,coord,EnumFacing.SOUTH, coord.south()))
        		 .withProperty(EAST, this.canConnectTo(world,coord,EnumFacing.EAST, coord.east()))
        		 .withProperty(UP, this.canConnectTo(world,coord,EnumFacing.UP, coord.up()))
        		 .withProperty(NORTH, this.canConnectTo(world,coord,EnumFacing.NORTH, coord.north()));
    }
	
	public boolean canConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing, BlockPos other) {
		Block block = world.getBlockState(other).getBlock();
		return ((block instanceof IBlockSteam) || (world.getTileEntity(other) instanceof ISteamSource) || (world.getTileEntity(other) instanceof ISteamSink));
	}

	@Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { WEST, DOWN, SOUTH, EAST, UP, NORTH });
    }
	
	@Override
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final BlockPos coord) {
        final boolean connectNorth = this.canConnectTo(world,coord,EnumFacing.NORTH, coord.north());
        final boolean connectSouth = this.canConnectTo(world,coord,EnumFacing.SOUTH, coord.south());
        final boolean connectWest = this.canConnectTo(world,coord,EnumFacing.WEST, coord.west());
        final boolean connectEast = this.canConnectTo(world,coord,EnumFacing.EAST, coord.east());
        final boolean connectUp = this.canConnectTo(world,coord,EnumFacing.UP, coord.up());
        final boolean connectDown = this.canConnectTo(world,coord,EnumFacing.DOWN, coord.down());
        
        float rminus = 0.5f - 0.25f;
        float rplus = 0.5f + 0.25f;
        
        float x1 = rminus;
        float x2 = rplus;
        float y1 = rminus;
        float y2 = rplus;
        float z1 = rminus;
        float z2 = rplus;
        if (connectNorth) {
            z1 = 0.0f;
        }
        if (connectSouth) {
            z2 = 1.0f;
        }
        if (connectWest) {
            x1 = 0.0f;
        }
        if (connectEast) {
            x2 = 1.0f;
        }
        if(connectDown){
        	y1 = 0.0f;
        }
        if(connectUp){
        	y2 = 1.0f;
        }
        this.setBlockBounds(x1, y1, z1, x2, y2, z2);
    }

    /**
     * Calculates the collision boxes for this block.
     */
	@Override
    public void addCollisionBoxesToList(final World world, final BlockPos coord, final IBlockState bs, final AxisAlignedBB box, final List collisionBoxList, final Entity entity) {
        final boolean connectNorth = this.canConnectTo(world,coord,EnumFacing.NORTH, coord.north());
        final boolean connectSouth = this.canConnectTo(world,coord,EnumFacing.SOUTH, coord.south());
        final boolean connectWest = this.canConnectTo(world,coord,EnumFacing.WEST, coord.west());
        final boolean connectEast = this.canConnectTo(world,coord,EnumFacing.EAST, coord.east());
        final boolean connectUp = this.canConnectTo(world,coord,EnumFacing.UP, coord.up());
        final boolean connectDown = this.canConnectTo(world,coord,EnumFacing.DOWN, coord.down());
        
        float rminus = 0.5f - 0.25f;
        float rplus = 0.5f + 0.25f;
        
        this.setBlockBounds(rminus, rminus, rminus, rplus, rplus, rplus);
        super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);

        if(connectUp){
            this.setBlockBounds(rminus, rminus, rminus, rplus, 1f, rplus);
            super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);
        }
        if(connectDown){
            this.setBlockBounds(rminus, 0f, rminus, rplus, rplus, rplus);
            super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);
        }
        if(connectEast){
            this.setBlockBounds(rminus, rminus, rminus, 1f, rplus, rplus);
            super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);
        }
        if(connectWest){
            this.setBlockBounds(0f, rminus, rminus, rplus, rplus, rplus);
            super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);
        }
        if(connectSouth){
            this.setBlockBounds(rminus, rminus, rminus, rplus, rplus, 1f);
            super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);
        }
        if(connectNorth){
            this.setBlockBounds(rminus, rminus, 0f, rplus, rplus, rplus);
            super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);
        }
    }
	
	@Override
    public int getMetaFromState(final IBlockState bs) {
        return 0;
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess world, final BlockPos coord, final EnumFacing face) {
        return true;
    }
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntitySteamPipe();
	}
}
