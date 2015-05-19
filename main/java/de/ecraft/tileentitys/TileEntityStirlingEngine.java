package de.ecraft.tileentitys;

import de.ecraft.api.conduit.steam.ISteamSink;
import de.ecraft.data.DataValues;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;

public class TileEntityStirlingEngine extends TileEntity implements ISteamSink, ISidedInventory, IUpdatePlayerListBox {
	
	/** In L */
	public final float steam_storage = DataValues.LmB * 16;
	/** In L */
	public float steam = 0;
	public int rpmCfg = 0;
	public int nmCfg = 0;
	
	public int rpm = 0;
	public int nm = 0;
	private String customName = "";
	private boolean isInventoryOpen = false;
	
	@Override
	public float fillSteam(float amount, float steam, float pressure, float tanksize, boolean doFill) {
		float sPressure = getPressure();
		System.out.println("Handling sink current pressure=" + sPressure);
		if(sPressure < pressure) {
			float x = ((steam * this.steam_storage) - (this.steam * tanksize)) / (tanksize + this.steam_storage);
			System.out.println(x + "= ((" + steam + " * " + this.steam_storage + ") - (" + this.steam + " * " + tanksize + ")) / (" + tanksize + " + " + this.steam_storage + ")");
			if(amount < x) {
				if(doFill) {
					this.steam += amount;
				}
				return amount;
			}
			if(doFill)
				this.steam += x;
			return x;
		}
		return 0;
	}

	@Override
	public float getSteam() {
		return steam;
	}

	@Override
	public float getPressure() {
		return DataValues.normalPressure * ((steam + steam_storage) / steam_storage);
	}

	@Override
	public void update() {
		
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.worldObj.getTileEntity(this.pos) != this ? false : entityplayer.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setFloat("steam", steam);
		nbt.setString("customName", customName);
		super.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		steam = nbt.getFloat("steam");
		customName = nbt.getString("customName");
		super.readFromNBT(nbt);
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
	public void clear() {}

	@Override
	public void openInventory(EntityPlayer arg0) {
		isInventoryOpen  = true;
	}
	
	@Override
	public void closeInventory(EntityPlayer arg0) {
		isInventoryOpen = false;
	}
	@Override
	public ItemStack decrStackSize(int arg0, int arg1) {
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
		return 0;
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int arg0) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int arg0) {
		return null;
	}

	@Override
	public boolean isItemValidForSlot(int arg0, ItemStack arg1) {
		return false;
	}
	
	@Override
	public void setField(int arg0, int arg1) {}

	@Override
	public void setInventorySlotContents(int arg0, ItemStack arg1) {}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentText(this.hasCustomName() ? this.customName : I18n.format("container.stirling_engine", null));
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : I18n.format("container.stirling_engine", null);
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && this.customName.length() > 0;
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
	
	public void setCustomInventoryName(String displayName) {
		customName = displayName;
	}

	public int getPressureScaled(int size) {
		return (int) ((getPressure() / (1013 * 100)) * size);
	}
}
