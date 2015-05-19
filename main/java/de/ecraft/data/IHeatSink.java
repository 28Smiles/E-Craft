package de.ecraft.data;

public interface IHeatSink {
	
	public abstract float getTemperature();
	
	public abstract float getHeat();
	
	public abstract float getMJpK();
	
	public abstract float heatUp(IHeatSource source, float heat, float temp);
}
