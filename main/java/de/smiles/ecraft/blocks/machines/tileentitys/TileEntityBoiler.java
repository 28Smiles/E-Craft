package de.smiles.ecraft.blocks.machines.tileentitys;

import java.util.ArrayList;
import java.util.List;

import de.smiles.ecraft.blocks.machines.Boiler;
import de.smiles.ecraft.blocks.machines.BoilerPart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class TileEntityBoiler extends TileEntity implements IUpdatePlayerListBox {
	
	protected boolean placed = true;
	protected BlockPos master;
	
	@Override
	public void update() {
		if(placed) {
			neightbourChange(worldObj, pos);
			master = searchMaster(new ArrayList<BlockPos>()).get(0);
			placed = false;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("placed", placed);
		NBTTagCompound posnbt = new NBTTagCompound();
		posnbt.setInteger("x", master.getX());
		posnbt.setInteger("y", master.getX());
		posnbt.setInteger("z", master.getX());
		nbt.setTag("master", posnbt);
		super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		placed = nbt.getBoolean("placed");
		NBTTagCompound posnbt = (NBTTagCompound) nbt.getTag("master");
		master = new BlockPos(posnbt.getInteger("x"), posnbt.getInteger("y"), posnbt.getInteger("z"));
		super.readFromNBT(nbt);
	}
	
	public List<BlockPos> searchMaster(List<BlockPos> searchList) {
		if(!searchList.contains(pos)) {
			searchList.add(pos);
			if(this instanceof TileEntityBoilerMB)
				searchList.add(0, pos);
			else 
			{
				if(worldObj.getTileEntity(pos.north()) instanceof TileEntityBoiler)
					searchList = searchMaster(searchList);
				if(worldObj.getTileEntity(pos.south()) instanceof TileEntityBoiler)
					searchList = searchMaster(searchList);
				if(worldObj.getTileEntity(pos.east()) instanceof TileEntityBoiler)
					searchList = searchMaster(searchList);
				if(worldObj.getTileEntity(pos.west()) instanceof TileEntityBoiler)
					searchList = searchMaster(searchList);
				if(worldObj.getTileEntity(pos.up()) instanceof TileEntityBoiler)
					searchList = searchMaster(searchList);
				if(worldObj.getTileEntity(pos.down()) instanceof TileEntityBoiler)
					searchList = searchMaster(searchList);
			}
		}
		return searchList;
	}
	
	public void neightbourChange(World world, BlockPos bp) {
		int i = 0;
		if(world.getBlockState(pos.north()).getBlock() instanceof Boiler)
			i += 1;
		else if(world.getBlockState(pos.north()).getBlock() instanceof BoilerPart)
			i += 1;
		if(world.getBlockState(pos.south()).getBlock() instanceof Boiler)
			i += 2;
		else if(world.getBlockState(pos.south()).getBlock() instanceof BoilerPart)
			i += 2;
		if(world.getBlockState(pos.east()).getBlock() instanceof Boiler)
			i += 4;
		else if(world.getBlockState(pos.east()).getBlock() instanceof BoilerPart)
			i += 4;
		if(world.getBlockState(pos.west()).getBlock() instanceof Boiler)
			i += 8;
		else if(world.getBlockState(pos.west()).getBlock() instanceof BoilerPart)
			i += 8;
		if(world.getBlockState(pos).getBlock() instanceof BoilerPart) {
			if(((Integer)world.getBlockState(pos).getValue(BoilerPart.STATE)) != i) {
				TileEntityBoiler tb = (TileEntityBoiler) world.getTileEntity(pos);
				IBlockState blockstate = world.getBlockState(pos);
				blockstate = blockstate.withProperty(BoilerPart.STATE, i);
				world.setBlockState(pos, blockstate, i);
				world.setTileEntity(pos, tb);
				world.markBlockForUpdate(pos);
			}
		} else if(world.getBlockState(pos).getBlock() instanceof Boiler) {
			if(((Integer)world.getBlockState(pos).getValue(Boiler.STATE)) != i) {
				TileEntityBoiler tb = (TileEntityBoiler) world.getTileEntity(pos);
				IBlockState blockstate = world.getBlockState(pos);
				blockstate = blockstate.withProperty(Boiler.STATE, i);
				world.setBlockState(pos, blockstate, i);
				world.setTileEntity(pos, tb);
				world.markBlockForUpdate(pos);
			}
		}
	}
	
	public List<BlockPos> searchBigBoiler(List<BlockPos> searchList) {
		if(!searchList.contains(pos)) {
			searchList.add(pos);
			if(worldObj.getTileEntity(pos.north()) instanceof TileEntityBoiler)
				searchList = searchBigBoiler(searchList);
			if(worldObj.getTileEntity(pos.south()) instanceof TileEntityBoiler)
				searchList = searchBigBoiler(searchList);
			if(worldObj.getTileEntity(pos.east()) instanceof TileEntityBoiler)
				searchList = searchBigBoiler(searchList);
			if(worldObj.getTileEntity(pos.west()) instanceof TileEntityBoiler)
				searchList = searchBigBoiler(searchList);
			if(worldObj.getTileEntity(pos.up()) instanceof TileEntityBoiler)
				searchList = searchBigBoiler(searchList);
			if(worldObj.getTileEntity(pos.down()) instanceof TileEntityBoiler)
				searchList = searchBigBoiler(searchList);
		}
		return searchList;
	}

	public boolean onBlockActivated(World world, BlockPos bp, IBlockState bs, EntityPlayer player, EnumFacing face, float f1, float f2, float f3) {
		return false;
	}

	public void onBlockPlaced(World world, BlockPos bp, EntityLivingBase entity) {}

	public void onBlockHarvested(World world, BlockPos bp, EntityPlayer entity) {
		
	}
}
