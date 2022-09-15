package com.bug1312.javajson.example.client.init;

import com.bug1312.javajson.example.client.render.entity.RenderMovingMannequin;
import com.bug1312.javajson.example.common.init.ExampleEntities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ExampleEntityRenders {
	
	public static void init() {
		register(ExampleEntities.MOVING_MANNEQUIN.get(), RenderMovingMannequin::new);
	}

	/* Register Method */
	public static <T extends Entity> void register(EntityType<T> entity, IRenderFactory<? super T> renderer) {
		RenderingRegistry.registerEntityRenderingHandler(entity, renderer);
	}
}
