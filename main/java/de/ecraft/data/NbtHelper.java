package de.ecraft.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;

public class NbtHelper {

	public static void setBlockPosArray(NBTTagCompound nbttc, BlockPos[] array, String name) {
		NBTTagCompound nnbttc = new NBTTagCompound();
		nnbttc.setInteger(name, array.length);
		if(array.length > 0)
		for(int i = 0; i < array.length; i++) {
			if(array[i] != null) {
				nnbttc.setInteger(i + "_X", array[i].getX());
				nnbttc.setInteger(i + "_Y", array[i].getY());
				nnbttc.setInteger(i + "_Z", array[i].getZ());
			}
			nnbttc.setBoolean(i + "", array[i] != null);
		}
		nbttc.setTag(name, nnbttc);
	}
	
	public static BlockPos[] getBlockPosArray(NBTTagCompound nbttc, String name) {
		NBTTagCompound nnbttc = nbttc.getCompoundTag(name);
		int i = nnbttc.getInteger(name);
		BlockPos[] array = new BlockPos[i];
		if(i > 0)
		for(int j = 0; j < i; j++) {
			if(nnbttc.getBoolean("j")) {
				int x = nnbttc.getInteger(j + "X");
				int y = nnbttc.getInteger(j + "Y");
				int z = nnbttc.getInteger(j + "Z");
				array[j] = new BlockPos(x, y, z);
			}
		}
		return array;
	}
	
	public static void setBlockPos(NBTTagCompound nbttc, BlockPos blockpos, String name) {
		NBTTagCompound nnbttc = new NBTTagCompound();
		if(blockpos != null) {
			nnbttc.setInteger("X", blockpos.getX());
			nnbttc.setInteger("Y", blockpos.getY());
			nnbttc.setInteger("Z", blockpos.getZ());
		}
		nnbttc.setBoolean("there", blockpos != null);
		nbttc.setTag(name, nnbttc);
	}
	
	public static BlockPos getBlockPos(NBTTagCompound nbttc, String name) {
		NBTTagCompound nnbttc = nbttc.getCompoundTag(name);
		if(nnbttc.getBoolean("there")) {
			int x = nnbttc.getInteger("X");
			int y = nnbttc.getInteger("Y");
			int z = nnbttc.getInteger("Z");
			return new BlockPos(x, y, z);
		}
		return null;
	}
}
