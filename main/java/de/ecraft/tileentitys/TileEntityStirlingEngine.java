package de.ecraft.tileentitys;

import de.ecraft.api.conduit.steam.ISteamSink;
import de.ecraft.data.DataValues;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

public class TileEntityStirlingEngine extends TileEntity implements ISteamSink, IUpdatePlayerListBox {
	
	public final float steam_storage = DataValues.LmB * 16;
	public float steam = 0F;
	public int rpmCfg = 0;
	public int nmCfg = 0;
	
	public int rpm = 0;
	public int nm = 0;
	
	@Override
	public float fillSteam(float steam, boolean doFill) {
		if(doFill)
			this.steam += steam;
		return steam;
	}

	@Override
	public float getSteam() {
		return steam;
	}

	@Override
	public float getPressure() {
		return DataValues.normalPressure * (steam / steam_storage);
	}

	@Override
	public void update() {
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setFloat("steam", steam);
		super.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		steam = nbt.getFloat("steam");
		super.readFromNBT(nbt);
	}
}
