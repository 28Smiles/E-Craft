package de.smiles.ecraft.api.register.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE}) 
public @interface CustomRenderer {
	
	Class<? extends TileEntity> tileentity();
	Class<? extends TileEntitySpecialRenderer> renderer();
}
