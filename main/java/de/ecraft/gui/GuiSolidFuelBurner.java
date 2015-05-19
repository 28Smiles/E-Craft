package de.ecraft.gui;

import org.lwjgl.opengl.GL11;

import de.ecraft.container.ContainerSolidFuelBurner;
import de.ecraft.tileentitys.TileEntitySolidFuelBurner;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiSolidFuelBurner extends GuiContainer {

	private TileEntitySolidFuelBurner tile;
	private static final ResourceLocation gui = new ResourceLocation("ecraft:textures/gui/solid_fuel_burner.png");
	private InventoryPlayer playerInventory;
	
	public GuiSolidFuelBurner(InventoryPlayer playerInventory, TileEntitySolidFuelBurner tile) {
		super(new ContainerSolidFuelBurner(tile, playerInventory));
		this.tile = tile;
		this.playerInventory = playerInventory;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int arg0, int arg1) {
		this.fontRendererObj.drawString(this.tile.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, (ySize - 96) + 2, 4210752);
		int posT = 106;
		if(Math.round(this.tile.getTemperature()) >= 1000)
			posT = 96;
		this.fontRendererObj.drawString(((float)Math.round(this.tile.getTemperature() * 100) / 100) + "", posT, 70, 4210752);
		this.fontRendererObj.drawString("°K", 138, 70, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float arg0, int arg1, int arg2) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
		int var7;

		if (this.tile.isBurning())
		{
			var7 = this.tile.getBurnProcessScaled();
			drawTexturedModalRect(l + 81, (i1 + 39) - var7, 176, 12 - var7, 14, var7 + 2);
		}
		int temp = this.tile.getTemperatureScaled();
		drawTexturedModalRect(l + 151, (i1 + 78) - temp, 176, 84 - temp, 17, temp + 2);
	}
}
