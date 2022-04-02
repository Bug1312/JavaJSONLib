package com.swdteam.example.client.init;

import java.util.function.Function;

import com.swdteam.example.client.render.tile.RenderStatue;
import com.swdteam.example.common.init.ExampleTiles;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ExampleTileRenders {
	
	public static void init() {
		register(ExampleTiles.STATUE.get(), RenderStatue::new);
	}

	/* Register Method */
	public static <T extends TileEntity> void register(TileEntityType<T> tile, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> renderer) {
		ClientRegistry.bindTileEntityRenderer(tile, renderer);
	}
}
