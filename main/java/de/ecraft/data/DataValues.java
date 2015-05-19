package de.ecraft.data;

public class DataValues {
	
	/**
	 * V = mB * LmB
	 * 15L = 1000 mB * 0.015 L/mB
	 */
	public static final float LmB = 0.015F;
	
	/**
	 * E = JLwaterTkelvin * V * T
	 * 18.69354 MJ = 4.182 * 15L * 298K
	 */
	public static final float kJLwaterTkelvin = 4.182F;
	
	/**
	 * E = (kJsteamTkelvin * T) / m
	 * 18.69354 MJ = 4.182 * 15L * 298K
	 */
	public static final float kJsteamTkelvin = 2.080F;
	
	/**
	 * V = m * VsteamMkg
	 */
	public static final float VsteamMkg = 1673F;
	
	/**
	 * Normal Temp in K
	 */
	public static final float normalTemp = 298;
	
	/**
	 * Normal pressure in hPa
	 */
	public static final float normalPressure = 1013F;
}
