package com.swdteam.example.client.render.entity;

import com.swdteam.example.client.render.entity.model.ModelMovingMannequin;
import com.swdteam.example.common.entity.MovingMannequin;
import com.swdteam.example.main.ExampleMod;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderMovingMannequin extends LivingRenderer<MovingMannequin, ModelMovingMannequin> {
	private static final ResourceLocation BLUE 	= new ResourceLocation(ExampleMod.MODID, "textures/entity/auton_blue.png");
	private static final ResourceLocation RED 	= new ResourceLocation(ExampleMod.MODID, "textures/entity/auton_red.png");
	private static final ResourceLocation PINK 	= new ResourceLocation(ExampleMod.MODID, "textures/entity/auton_pink.png");
	private static final ResourceLocation BLACK = new ResourceLocation(ExampleMod.MODID, "textures/entity/auton_black.png");

	public RenderMovingMannequin(EntityRendererManager renderManager) {
		super(renderManager, new ModelMovingMannequin(1.0f), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(MovingMannequin entity) {
		switch(entity.getAutonType()) {
			case 0: default: return BLUE;
			case 1: 		 return RED;
			case 2: 		 return PINK;
			case 3: 		 return BLACK;
		}
	}
	
	@Override
	protected boolean shouldShowName(MovingMannequin entity) {
		return entity.hasCustomName();
	}
	
}
