package de.ecraft.gui;

import org.lwjgl.opengl.GL11;

import de.ecraft.container.ContainerBoiler;
import de.ecraft.tileentitys.TileEntityBoiler;
import de.ecraft.tileentitys.TileEntityBoilerMB;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class GuiBoiler extends GuiContainer {

	private static final ResourceLocation gui = new ResourceLocation("ecraft:textures/gui/boiler.png");
	private static final ResourceLocation water = new ResourceLocation("ecraft:textures/gui/fluids/water.png");
	private TileEntityBoiler tile;
	private InventoryPlayer inventory_player;
	
	public GuiBoiler(InventoryPlayer playerinv, TileEntityBoiler tileentity) {
		super(new ContainerBoiler(tileentity, playerinv));
		tile = tileentity;
		inventory_player = playerinv;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mx, int my) {
		this.fontRendererObj.drawString("Boiler", 8, 6, 4210752);
		this.fontRendererObj.drawString(inventory_player.getDisplayName().getUnformattedText(), 8, (ySize - 96) + 2, 4210752);
		String temp = ((float)Math.round(this.tile.getTemperature() * 100) / 100) + "°K";
		this.fontRendererObj.drawString(temp, 75, 60, 4210752);
		this.fontRendererObj.drawString(this.tile.getWater() + "mB", 75, 50, 4210752);
		this.fontRendererObj.drawString(Math.round(this.tile.getMaster().getPressure()) + "hPa", 75, 40, 4210752);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		drawTexturedModalRect(128, 10, 187, 0, 16, 60);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float arg0, int mx, int my) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
		int temp = this.tile.getTemperatureScaled(60);
		// x, y, sx, sy, tx, ty
		drawTexturedModalRect(l + 156, (i1 + 69) - temp, 176, 59 - temp, 11, temp);
		int pressure = this.tile.getPressureScaled(60);
		drawTexturedModalRect(l + 149, (i1 + 69) - pressure, 203, 59 - pressure, 2, pressure);
		
		//TODO: Proper FluidRendering
		int water = this.tile.getWaterScaled(60);
		drawTexturedModalRect(l + 128, (i1 + 69) - water, 205, 59 - water, 16, water);
	}
}
