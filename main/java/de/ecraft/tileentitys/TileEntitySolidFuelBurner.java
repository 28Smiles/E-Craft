package de.ecraft.tileentitys;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import scala.util.Random;
import de.ecraft.data.IHeatSink;
import de.ecraft.data.IHeatSource;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class TileEntitySolidFuelBurner extends TileEntity implements ISidedInventory, IUpdatePlayerListBox, IHeatSource {

	ItemStack burn_slot;
	private int burnTime = 0;
	private int currentItemBurnTime = 0;
	private String customName = "";
	private boolean isInventoryOpen = false;
	
	/** heat in MJ */
	private float heat = 59600;
	
	public TileEntitySolidFuelBurner() {}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttc) {
		burn_slot = ItemStack.loadItemStackFromNBT(nbttc.getCompoundTag("burn_slot"));
		burnTime = nbttc.getInteger("bt");
		currentItemBurnTime = nbttc.getInteger("cibt");
		isInventoryOpen = nbttc.getBoolean("invopen");
		heat = nbttc.getFloat("heat");
		customName = nbttc.getString("customName");
		super.readFromNBT(nbttc);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttc) {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		if(burn_slot != null)
		nbttc.setTag("burn_slot", burn_slot.writeToNBT(nbttagcompound));
		nbttc.setInteger("bt", burnTime);
		nbttc.setInteger("cibt", currentItemBurnTime);
		nbttc.setBoolean("invopen", isInventoryOpen);
		nbttc.setFloat("heat", heat);
		nbttc.setString("customName", customName);
		super.writeToNBT(nbttc);
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

	@Override
	public void clear() {
		
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (burn_slot != null)
		{
			if (burn_slot.stackSize <= j)
			{
				ItemStack itemstack = burn_slot;
				burn_slot = null;
				return itemstack;
			}
			ItemStack itemstack1 = burn_slot.splitStack(j);
			if (burn_slot.stackSize == 0)
			{
				burn_slot = null;
			}
			return itemstack1;
		}
		return null;
	}

	@Override
	public int getField(int arg0) {
		return 0;
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int arg0) {
		return burn_slot;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(burn_slot != null) {
			ItemStack itemstack = burn_slot;
			burn_slot = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack arg1) {
		return par1 == 2 ? false : (par1 == 1 ? false : true);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.worldObj.getTileEntity(this.pos) != this ? false : entityplayer.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer arg0) {
		isInventoryOpen = true;
	}
	
	@Override
	public void closeInventory(EntityPlayer arg0) {
		isInventoryOpen = false;
	}

	@Override
	public void setField(int arg0, int arg1) {}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.burn_slot = itemstack;
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentText(this.hasCustomName() ? this.customName : I18n.format("container.solid_fuel_burner", null));
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : I18n.format("container.solid_fuel_burner", null);
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	int i = 0;
	public static Random rand = new Random();
	/**
	 * Update
	 */
	@Override
	public void update() {
		i++;
		if(i == 5) {
			i = 0;
			if(burnTime > 0)
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + (0.5D + ((rand.nextDouble() / 4D) - 0.25D)), pos.getY(), pos.getZ() + (0.5D + ((rand.nextDouble() / 4D) - 0.25D)), 0, 0, 0, new int[0]);
		}
		if(burnTime > 0) {
			burnTime--;
			heat += 4;
		} else {
			if(getTemperature() > 298)
				heat -= 0.5F;
			else
				heat = 59600;
		}
		if(burnTime == 0 && canBurn()) {
			burnTime = TileEntityFurnace.getItemBurnTime(burn_slot);
			currentItemBurnTime = burnTime;
			if(burn_slot.stackSize - 1 > 0)
				burn_slot.stackSize--;
			else if(burn_slot.stackSize - 1 == 0)
				burn_slot = null;
		}
		if(worldObj.getTileEntity(pos.up()) instanceof IHeatSink) {
			heat = ((IHeatSink)worldObj.getTileEntity(pos.up())).heatUp(this, heat, getTemperature());
		}
	}
	
	public boolean isBurning() {
		return burnTime > 0;
	}
	
	public int getTemperatureScaled() {
		return (int) ((((float)getTemperature()) / (float)2000) * 69);
	}
	
	public int getBurnProcessScaled() {
		return (int) ((((float)burnTime) / (float)currentItemBurnTime) * 12);
	}
	
	private boolean canBurn() {
		if(burn_slot != null)
		if(burn_slot.stackSize > 0)
			return TileEntityFurnace.getItemBurnTime(burn_slot) > 0;
		return false;
	}

	@Override
	public boolean canExtractItem(int arg0, ItemStack arg1, EnumFacing arg2) {
		return false;
	}

	@Override
	public boolean canInsertItem(int arg0, ItemStack arg1, EnumFacing arg2) {
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing arg0) {
		return null;
	}

	@Override
	public float getTemperature() {
		return heat / getMJpK();
	}

	@Override
	public float getHeat() {
		return heat;
	}

	@Override
	public float getMJpK() {
		return 200;
	}

	public void setCustomInventoryName(String displayName) {
		customName = displayName;
	}
}
