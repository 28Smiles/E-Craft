package de.ecraft.tileentitys;

import java.util.ArrayList;
import java.util.List;

import de.ecraft.api.conduit.steam.ISteamNetworkPart;
import de.ecraft.api.conduit.steam.ISteamPipe;
import de.ecraft.api.conduit.steam.ISteamSource;
import de.ecraft.blocks.Boiler;
import de.ecraft.blocks.BoilerMB;
import de.ecraft.blocks.ECraftBlocks;
import de.ecraft.data.DataValues;
import de.ecraft.data.IHeatSink;
import de.ecraft.data.IHeatSource;
import de.ecraft.data.NbtHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityBoiler extends TileEntity implements IUpdatePlayerListBox, IHeatSink, IFluidHandler, ISteamSource {
	
	public BlockPos master_boiler;
	/** Heat in MJ */
	public float heat = getMJpK() * DataValues.normalTemp;
	/** Watertank */
	public FluidTank waterTank = new FluidTank(FluidRegistry.WATER, 0, 10000);
	
	@Override
	public void update() {
		if(master_boiler == null) {
			ArrayList<BlockPos> bigboiler = new ArrayList<BlockPos>();
			bigboiler = searchBoilers(bigboiler, worldObj);
			for(BlockPos boiler_pos : bigboiler)
				if(worldObj.getTileEntity(boiler_pos) instanceof TileEntityBoilerMB)
					master_boiler = boiler_pos;
		} else if(!(worldObj.getTileEntity(master_boiler) instanceof TileEntityBoilerMB)) {
			master_boiler = null;
		}
	}
	
	@Override
	public List<BlockPos> updateNetwork(World world, BlockPos pos, List<BlockPos> list) {
		if(!this.pos.equals(master_boiler))
			getMaster().updateNetwork(world, pos, list);
		return ISteamSource.super.updateNetwork(world, pos, list);
	}
	
	public List<BlockPos> getPipes(World world, BlockPos pos, List<BlockPos> list) {
		if((world.getTileEntity((pos.north())) instanceof ISteamPipe))
			if(!list.contains(pos.north()))
				list.add(pos.north());
		if((world.getTileEntity((pos.south())) instanceof ISteamPipe))
			if(!list.contains(pos.south()))
				list.add(pos.south());
		if((world.getTileEntity((pos.west())) instanceof ISteamPipe))
			if(!list.contains(pos.west()))
				list.add(pos.west());
		if((world.getTileEntity((pos.east())) instanceof ISteamPipe))
			if(!list.contains(pos.east()))
				list.add(pos.east());
		if((world.getTileEntity((pos.up())) instanceof ISteamPipe))
			if(!list.contains(pos.up()))
				list.add(pos.up());
		if((world.getTileEntity((pos.down())) instanceof ISteamPipe))
			if(!list.contains(pos.down()))
				list.add(pos.down());
		return list;
	}
	
	public TileEntityBoilerMB getMaster() {
		if(master_boiler != null)
		if(worldObj.getTileEntity(master_boiler) instanceof TileEntityBoilerMB)
			return (TileEntityBoilerMB) worldObj.getTileEntity(master_boiler);
		return null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttc) {
		nbttc.setFloat("heat", heat);
		waterTank.writeToNBT(nbttc);
		NbtHelper.setBlockPos(nbttc, master_boiler, "master");
		super.writeToNBT(nbttc);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttc) {
		heat = nbttc.getFloat("heat");
		waterTank = waterTank.readFromNBT(nbttc);
		master_boiler = NbtHelper.getBlockPos(nbttc, "master");
		super.readFromNBT(nbttc);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		NBTTagCompound tagCom = pkt.getNbtCompound();
		this.readFromNBT(tagCom);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tagCom = new NBTTagCompound();
		this.writeToNBT(tagCom);
		return new S35PacketUpdateTileEntity(pos, getBlockMetadata(), tagCom);
	}
	
	public void neightbourChange(World world, BlockPos bp) {
		int i = 0;
		if(world.getBlockState(pos.north()).getBlock() instanceof Boiler)
			i += 1;
		else if(world.getBlockState(pos.north()).getBlock() instanceof BoilerMB)
			i += 1;
		if(world.getBlockState(pos.south()).getBlock() instanceof Boiler)
			i += 2;
		else if(world.getBlockState(pos.south()).getBlock() instanceof BoilerMB)
			i += 2;
		if(world.getBlockState(pos.east()).getBlock() instanceof Boiler)
			i += 4;
		else if(world.getBlockState(pos.east()).getBlock() instanceof BoilerMB)
			i += 4;
		if(world.getBlockState(pos.west()).getBlock() instanceof Boiler)
			i += 8;
		else if(world.getBlockState(pos.west()).getBlock() instanceof BoilerMB)
			i += 8;
		if(world.getBlockState(pos).getBlock() instanceof BoilerMB) {
			if(((Integer)world.getBlockState(pos).getValue(BoilerMB.STATE)) != i) {
				TileEntityBoiler tb = (TileEntityBoiler) world.getTileEntity(pos);
				IBlockState blockstate = world.getBlockState(pos);
				blockstate = blockstate.withProperty(BoilerMB.STATE, i);
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
	
	public ArrayList<BlockPos> searchBoilers(ArrayList<BlockPos> list, World world) {
		if(!list.contains(pos))
			list.add(pos);
		if((world.getBlockState(pos.north()).getBlock() instanceof Boiler) || (world.getBlockState(pos.north()).getBlock() instanceof BoilerMB))
			if(!list.contains(pos.north()))
				list = ((TileEntityBoiler)world.getTileEntity(pos.north())).searchBoilers(list, world);
		if((world.getBlockState(pos.south()).getBlock() instanceof Boiler) || (world.getBlockState(pos.south()).getBlock() instanceof BoilerMB))
			if(!list.contains(pos.south()))
				list = ((TileEntityBoiler)world.getTileEntity(pos.south())).searchBoilers(list, world);
		if((world.getBlockState(pos.west()).getBlock() instanceof Boiler) || (world.getBlockState(pos.west()).getBlock() instanceof BoilerMB))
			if(!list.contains(pos.west()))
				list = ((TileEntityBoiler)world.getTileEntity(pos.west())).searchBoilers(list, world);
		if((world.getBlockState(pos.east()).getBlock() instanceof Boiler) || (world.getBlockState(pos.east()).getBlock() instanceof BoilerMB))
			if(!list.contains(pos.east()))
				list = ((TileEntityBoiler)world.getTileEntity(pos.east())).searchBoilers(list, world);
		if((world.getBlockState(pos.up()).getBlock() instanceof Boiler) || (world.getBlockState(pos.up()).getBlock() instanceof BoilerMB))
			if(!list.contains(pos.up()))
				list = ((TileEntityBoiler)world.getTileEntity(pos.up())).searchBoilers(list, world);
		if((world.getBlockState(pos.down()).getBlock() instanceof Boiler) || (world.getBlockState(pos.down()).getBlock() instanceof BoilerMB))
			if(!list.contains(pos.down()))
				list = ((TileEntityBoiler)world.getTileEntity(pos.down())).searchBoilers(list, world);
		return list;
	}

	public boolean onBlockActivated(World world, BlockPos bp, IBlockState bs, EntityPlayer player, EnumFacing face, float f1, float f2, float f3) {
		if(player.inventory.getCurrentItem() != null)
			if(player.inventory.getCurrentItem().getItem().equals(Items.water_bucket)) {
				if(getMaster() != null) {
					fill(face, new FluidStack(FluidRegistry.WATER, 1000), true);
					return true;
				}
			}
		if(getMaster() != null)
			getMaster().openInventory(world, bp, bs, player, face, f1, f2, f3);
		return true;
	}
	
	

	public IBlockState onBlockPlaced(World world, BlockPos bp, EntityLivingBase entity) {
		return world.getBlockState(bp);
	}

	public void onBlockHarvested(World world, BlockPos bp, EntityPlayer entity) {
		ArrayList<BlockPos> bigboiler = new ArrayList<BlockPos>();
		bigboiler = searchBoilers(bigboiler, world);
		bigboiler.remove(pos);
		for(BlockPos bpos : bigboiler) 
			if(world.getBlockState(bpos).getBlock() instanceof BoilerMB) {
				world.setBlockState(bpos, ECraftBlocks.boiler.getDefaultState());
				world.getBlockState(bpos).getBlock().onNeighborBlockChange(world, bpos, world.getBlockState(bpos), world.getBlockState(bpos).getBlock());
				break;
			}
	}
	
	public int getTemperatureScaled(int size) {
		return (int) ((getTemperature() / 2000F) * size);
	}
	
	public int getPressureScaled(int size) {
		return (int) ((getPressure() / (1013 * 100)) * size);
	}
	
	public int getWaterScaled(int size) {
		if(getMaster() == null)
			return 0;
		return (int) ((getWater() / (10000F * (float)getMaster().bigboiler.length)) * size);
	}
	
	public float getPressure() {
		if(getMaster() == null)
			return 0;
		return getMaster().getPressure();
	}

	@Override
	public float getTemperature() {
		if(getMaster() == null)
			return 0;
		return getMaster().getTemperature();
	}

	@Override
	public float getHeat() {
		if(getMaster() == null)
			return 0;
		return getMaster().getHeat();
	}

	@Override
	public float getMJpK() {
		if(getMaster() == null)
			return 1;
		return (0.001F * DataValues.kJLwaterTkelvin * DataValues.LmB * getMaster().getWater()) + (100 * getMaster().bigboiler.length);
	}

	@Override
	public float heatUp(IHeatSource source, float heat, float temp) {
		if(getMaster() == null)
			return heat;
		return getMaster().heatUp(source, heat, temp);
	}
	
	public int getWater() {
		if(getMaster() == null)
			return 0;
		return getMaster().getWater();
	}

	@Override
	public boolean canDrain(EnumFacing arg0, Fluid arg1) {
		if(getMaster() == null)
			return false;
		return getMaster().canDrainAll(arg0, arg1);
	}

	@Override
	public boolean canFill(EnumFacing arg0, Fluid arg1) {
		if(getMaster() == null)
			return false;
		return getMaster().canFillAll(arg0, arg1);
	}

	@Override
	public FluidStack drain(EnumFacing face, FluidStack fluidstack, boolean doDrain) {
		if(getMaster() == null)
			return null;
		return getMaster().drainAll(face, fluidstack, doDrain);
	}

	@Override
	public FluidStack drain(EnumFacing face, int amount, boolean doDrain) {
		if(getMaster() == null)
			return null;
		return getMaster().drainAll(face, amount, doDrain);
	}

	@Override
	public int fill(EnumFacing face, FluidStack fluidstack, boolean doFill) {
		if(getMaster() == null)
			return 0;
		return getMaster().fillAll(face, fluidstack, doFill);
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing face) {
		if(getMaster() == null)
			return null;
		return getMaster().getAllTankInfo(face);
	}

	@Override
	public float drainSteam(EnumFacing face, float amount, boolean doDrain) {
		if(getMaster() == null)
			return 0;
		return getMaster().drainSteam(face, amount, doDrain);
	}

	@Override
	public boolean canDrainSteam(EnumFacing face) {
		if(getMaster() == null)
			return false;
		return getMaster().canDrainSteam(face);
	}
}
