package de.ecraft.tileentitys;

import java.util.ArrayList;
import java.util.List;

import de.ecraft.ECraft;
import de.ecraft.api.conduit.steam.ISteamPipe;
import de.ecraft.blocks.Boiler;
import de.ecraft.blocks.ECraftBlocks;
import de.ecraft.data.DataValues;
import de.ecraft.data.IHeatSource;
import de.ecraft.data.NbtHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityBoilerMB extends TileEntityBoiler {
	
	public BlockPos[] bigboiler = new BlockPos[0];
	
	/** Steam in L */
	public float steam = 0;
	
	/** Boiler in mB */
	public static final float boiler_size = 10000;
	
	private List<BlockPos> sinks = new ArrayList<BlockPos>();
	
	@Override
	public void update() {
		if(worldObj.isRemote) {
			if(bigboiler.length == 0)
				createMaster();
			else if(bigboiler[0] == null)
				createMaster();
			if(getTemperature() > DataValues.normalTemp) {
				for(BlockPos bp : bigboiler)
					if(worldObj.getTileEntity(bp) != null)
						if(worldObj.getTileEntity(bp) instanceof TileEntityBoiler)
							((TileEntityBoiler)worldObj.getTileEntity(bp)).heat -= 0.05F;
			} else
				for(BlockPos bp : bigboiler)
					if(worldObj.getTileEntity(bp) != null)
						if(worldObj.getTileEntity(bp) instanceof TileEntityBoiler)
							((TileEntityBoiler)worldObj.getTileEntity(bp)).heat = (getMJpK() * DataValues.normalTemp) / (float)bigboiler.length;
			if(getTemperature() > (DataValues.normalTemp + 100)) {
				steam += waterToSteam((int)((getTemperature() - DataValues.normalTemp) * 0.01F)) * DataValues.LmB * DataValues.VsteamMkg;
			}
			if(steam > 0)
				steam -= handleSteamOutput(worldObj, sinks, steam * DataValues.LmB, steam * DataValues.LmB, boiler_size * bigboiler.length * DataValues.LmB, true);
			}
		super.update();
	}
	
	@Override
	public List<BlockPos> updateNetwork(World world, BlockPos pos, List<BlockPos> list) {
		List<BlockPos> nlist = new ArrayList<BlockPos>();
		nlist.add(list.get(0));
		for(BlockPos bp : bigboiler)
			if(worldObj.getTileEntity(bp) != null)
				if(worldObj.getTileEntity(bp) instanceof TileEntityBoiler)
					nlist = ((TileEntityBoiler)worldObj.getTileEntity(bp)).getPipes(world, bp, nlist);
		
		sinks = new ArrayList<BlockPos>();
		for(int i = 0; i < nlist.size(); i++) {
			BlockPos bp = nlist.get(i);
			List<BlockPos>[] slist = new ArrayList[3];
			slist[0] = new ArrayList<BlockPos>();
			slist[1] = new ArrayList<BlockPos>();
			slist[2] = new ArrayList<BlockPos>();
			for(BlockPos sp : ((ISteamPipe)world.getTileEntity(bp)).searchAll(world, bp, slist)[1])
				if(!sinks.contains(sp))
					sinks.add(sp);
		}
		return list;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NbtHelper.setBlockPosArray(nbt, bigboiler, "bigboiler");
		nbt.setFloat("steam", steam);
		super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		bigboiler = NbtHelper.getBlockPosArray(nbt, "bigboiler");
		steam = nbt.getFloat("steam");
		super.readFromNBT(nbt);
	}
	
	public void createMaster() {
		ArrayList<BlockPos> list = new ArrayList<BlockPos>();
		list = searchBoilers(list, worldObj);
		bigboiler = new BlockPos[list.size()];
		master_boiler = pos;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) != pos)
			if(worldObj.getBlockState(list.get(i)).getBlock() instanceof Boiler) {
				worldObj.setBlockState(list.get(i), ECraftBlocks.boilermb.getDefaultState());
			}
			worldObj.getBlockState(list.get(i)).getBlock().onNeighborBlockChange(worldObj, list.get(i), worldObj.getBlockState(list.get(i)), worldObj.getBlockState(list.get(i)).getBlock());
			bigboiler[i] = list.get(i);
		}
	}
	
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos bp, EntityLivingBase entity) {
		createMaster();
		if(!world.isRemote) {
			updateNetwork(world, bp, new ArrayList<BlockPos>());
		}
		return super.onBlockPlaced(world, bp, entity);
	}

	public void openInventory(World world, BlockPos bp, IBlockState bs, EntityPlayer player, EnumFacing face, float f1, float f2, float f3) {
		if (!world.isRemote)
		{
			TileEntity tile_entity = world.getTileEntity(pos);

			if (tile_entity instanceof TileEntityBoiler)
			{
				player.openGui(ECraft.INSTANCE, 1, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
	}
	
	@Override
	public float heatUp(IHeatSource source, float heat, float temp) {
		if(temp > getTemperature()) {
			float dif = temp - getTemperature();
			float mj = (source.getMJpK() * dif) / 2;
			if(mj > 32)
				mj = 32;
			float tmj = mj / (float)bigboiler.length;
			for(BlockPos bp : bigboiler)
				if(bp != null)
				if(worldObj.getTileEntity(bp) != null) {
					((TileEntityBoiler)worldObj.getTileEntity(bp)).heat += tmj;
				} else {
					mj = 0;
					tmj = 0;
				}
			heat -= mj;
		}
		return heat;
	}
	
	@Override
	public float getHeat() {
		float e = 0F;
		for(BlockPos bp : bigboiler)
			if(bp != null)
			if(worldObj.getTileEntity(bp) != null)
				e += ((TileEntityBoiler)worldObj.getTileEntity(bp)).heat;
		return e;
	}
	
	/**
	 * Pressure in hPa
	 */
	public float getPressure() {
		return 1013F * ((steam + (DataValues.LmB * boiler_size * bigboiler.length)) / (DataValues.LmB * boiler_size * bigboiler.length));
	}
	
	@Override
	public int getWater() {
		int a = 0;
		for(int i = 0; i < bigboiler.length; i++)
			if(bigboiler[i] != null)
				if(worldObj.getTileEntity(bigboiler[i]) != null)
					a += ((TileEntityBoiler)worldObj.getTileEntity(bigboiler[i])).waterTank.getFluidAmount();
		return a;
	}
	
	@Override
	public float getTemperature() {
		return getHeat() / getMJpK();
	}
	
	public boolean canDrainAll(EnumFacing arg0, Fluid arg1) {
		return false;
	}

	public boolean canFillAll(EnumFacing arg0, Fluid arg1) {
		int c = 0;
		int a = 0;
		for(int i = 0; i < bigboiler.length; i++)
			if(bigboiler[i] != null)
				if(worldObj.getTileEntity(bigboiler[i]) != null) {
					c = ((TileEntityBoiler)worldObj.getTileEntity(bigboiler[i])).waterTank.getCapacity();
					a = ((TileEntityBoiler)worldObj.getTileEntity(bigboiler[i])).waterTank.getFluidAmount();
				}
		return c > a;
	}
	
	public FluidStack drainAll(EnumFacing face, FluidStack fluidstack, boolean doDrain) {
		return null;
	}

	public FluidStack drainAll(EnumFacing face, int amount, boolean doDrain) {
		return null;
	}
	
	public int waterToSteam(int amount) {
		int a = 0;
		for(int i = 0; i < bigboiler.length; i++)
			if(bigboiler[i] != null)
				if(amount > 0)
				if(worldObj.getTileEntity(bigboiler[i]) != null) {
					FluidStack t = ((TileEntityBoiler)worldObj.getTileEntity(bigboiler[i])).waterTank.drain(amount, true);
					if(t != null) {
						a += t.amount;
						amount -= t.amount;
					}
				}
		float w_heat = (((float)a) * DataValues.VsteamMkg * DataValues.LmB * DataValues.kJsteamTkelvin * 10F) / bigboiler.length;
		for(int i = 0; i < bigboiler.length; i++)
			if(bigboiler[i] != null)
				if(worldObj.getTileEntity(bigboiler[i]) != null) {
					((TileEntityBoiler)worldObj.getTileEntity(bigboiler[i])).heat -= w_heat;
				}
		return a;
	}

	public int fillAll(EnumFacing face, FluidStack fluidstack, boolean doFill) {
		int a = 0;
		for(int i = 0; i < bigboiler.length; i++)
			if(bigboiler[i] != null)
				if(worldObj.getTileEntity(bigboiler[i]) != null) {
					int t = ((TileEntityBoiler)worldObj.getTileEntity(bigboiler[i])).waterTank.fill(fluidstack, doFill);
					a += t;
					fluidstack.amount -= t;
					if(fluidstack.amount == 0)
						return a;
				}
		float w_heat = (((float)a) * DataValues.LmB * DataValues.normalTemp * DataValues.kJLwaterTkelvin) / bigboiler.length;
		for(int i = 0; i < bigboiler.length; i++)
			if(bigboiler[i] != null)
				if(worldObj.getTileEntity(bigboiler[i]) != null) {
					((TileEntityBoiler)worldObj.getTileEntity(bigboiler[i])).heat += w_heat;
				}
		return a;
	}

	public FluidTankInfo[] getAllTankInfo(EnumFacing arg0) {
		FluidTankInfo[] info = new FluidTankInfo[bigboiler.length];
		for(int i = 0; i < bigboiler.length; i++)
			if(bigboiler[i] != null)
				if(worldObj.getTileEntity(bigboiler[i]) != null)
					info[i] = ((TileEntityBoiler)worldObj.getTileEntity(bigboiler[i])).waterTank.getInfo();
		return info;
	}
	
	@Override
	public float drainSteam(EnumFacing face, float amount, boolean doDrain) {
		if(amount <= steam) {
			steam -= amount;
			float heatdown = (DataValues.kJLwaterTkelvin * amount * getTemperature()) / bigboiler.length;
			for(BlockPos bp : bigboiler)
				if(worldObj.getTileEntity(bp) != null)
					if(worldObj.getTileEntity(bp) instanceof TileEntityBoiler)
						((TileEntityBoiler)worldObj.getTileEntity(bp)).heat -= heatdown;
			return amount;
		}
		steam = 0;
		return steam;
	}
	
	@Override
	public boolean canDrainSteam(EnumFacing face) {
		return steam > 0;
	}
}
