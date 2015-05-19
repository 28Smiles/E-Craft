package de.ecraft.gui;

import org.lwjgl.opengl.GL11;

import de.ecraft.container.ContainerStirlingEngine;
import de.ecraft.tileentitys.TileEntityStirlingEngine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiStirlingEngine extends GuiContainer {

	private TileEntityStirlingEngine tile;
	private static final ResourceLocation gui = new ResourceLocation("ecraft:textures/gui/stirling_engine.png");
	private InventoryPlayer playerInventory;
	
	public GuiStirlingEngine(InventoryPlayer playerInventory, TileEntityStirlingEngine tile) {
		super(new ContainerStirlingEngine(tile, playerInventory));
		this.tile = tile;
		this.playerInventory = playerInventory;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int arg0, int arg1) {
		this.fontRendererObj.drawString(this.tile.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, (ySize - 96) + 2, 4210752);
		this.fontRendererObj.drawString(Math.round(this.tile.getPressure()) + "hPa", 75, 20, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float arg0, int arg1, int arg2) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
		int var7;
		int pressure = this.tile.getPressureScaled(60);
		drawTexturedModalRect(l + 176, (i1 + 69) - pressure, 203, 59 - pressure, 2, pressure);
	}
}
