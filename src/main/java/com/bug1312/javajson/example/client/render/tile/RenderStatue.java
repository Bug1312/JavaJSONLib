package com.bug1312.javajson.example.client.render.tile;

import com.bug1312.javajson.example.main.ExampleMod;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

public class RenderStatue extends RenderTileEntityBase {

	private static ResourceLocation STATUE_MODEL = new ResourceLocation(ExampleMod.MODID, "/models/tileentity/statue.json");
	
	public RenderStatue(TileEntityRendererDispatcher rendererDispatcher) {
		super(rendererDispatcher, STATUE_MODEL);
	}

}
