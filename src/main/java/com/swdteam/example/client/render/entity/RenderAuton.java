package com.swdteam.example.client.render.entity;

import com.swdteam.example.client.render.entity.model.ModelAuton;
import com.swdteam.example.common.entity.Auton;
import com.swdteam.example.main.ExampleMod;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderAuton extends LivingRenderer<Auton, ModelAuton> {
	private static final ResourceLocation BLUE 	= new ResourceLocation(ExampleMod.MODID, "textures/entity/auton_blue.png");
	private static final ResourceLocation RED 	= new ResourceLocation(ExampleMod.MODID, "textures/entity/auton_red.png");
	private static final ResourceLocation PINK 	= new ResourceLocation(ExampleMod.MODID, "textures/entity/auton_pink.png");
	private static final ResourceLocation BLACK = new ResourceLocation(ExampleMod.MODID, "textures/entity/auton_black.png");
	private static final ResourceLocation NOTCH = new ResourceLocation(ExampleMod.MODID, "textures/entity/auton_notch.png");
	private static final ResourceLocation TARRI = new ResourceLocation(ExampleMod.MODID, "textures/entity/auton_khotarri.png");
	private static final ResourceLocation JOHN 	= new ResourceLocation(ExampleMod.MODID, "textures/entity/auton_1wtc.png");		
	
	public RenderAuton(EntityRendererManager renderManager) {
		super(renderManager, new ModelAuton(1.0f), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(Auton entity) {
		switch(entity.getAutonType()) {
			case 0: default: return BLUE;
			case 1: 		 return RED;
			case 2: 		 return PINK;
			case 3: 		 return BLACK;
			case 4: 		 return NOTCH;
			case 5: 		 return TARRI;
			case 6: 		 return JOHN;
		}
	}
	
	@Override
	protected boolean shouldShowName(Auton entity) {
		return entity.hasCustomName();
	}
	
}
