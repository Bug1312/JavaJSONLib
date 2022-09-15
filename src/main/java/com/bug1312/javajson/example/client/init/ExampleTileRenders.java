package com.bug1312.javajson.example.client.init;

import java.util.function.Function;

import com.bug1312.javajson.example.client.render.tile.RenderBlackboard;
import com.bug1312.javajson.example.client.render.tile.RenderPoliceBox;
import com.bug1312.javajson.example.client.render.tile.RenderStatue;
import com.bug1312.javajson.example.common.init.ExampleTiles;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ExampleTileRenders {
	
	public static void init() {
		register(ExampleTiles.STATUE.get(), RenderStatue::new);
		register(ExampleTiles.BLACKBOARD.get(), RenderBlackboard::new);
		register(ExampleTiles.POLICE_BOX.get(), RenderPoliceBox::new);
	}

	/* Register Method */
	public static <T extends TileEntity> void register(TileEntityType<T> tile, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> renderer) {
		ClientRegistry.bindTileEntityRenderer(tile, renderer);
	}
}
