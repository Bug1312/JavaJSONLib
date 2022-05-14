package com.swdteam.example.client.render.tile;

import com.swdteam.example.main.ExampleMod;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

public class RenderBlackboard extends RenderTileEntityBase {

	private static ResourceLocation STATUE_MODEL = new ResourceLocation(ExampleMod.MODID, "/models/tileentity/blackboard.json");
	
	public RenderBlackboard(TileEntityRendererDispatcher rendererDispatcher) {
		super(rendererDispatcher, STATUE_MODEL);
	}

}
