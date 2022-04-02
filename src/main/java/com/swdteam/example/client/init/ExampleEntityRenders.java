package com.swdteam.example.client.init;

import com.swdteam.example.client.render.entity.RenderAuton;
import com.swdteam.example.common.init.ExampleEntities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ExampleEntityRenders {
	
	public static void init() {
		register(ExampleEntities.AUTON.get(), RenderAuton::new);
	}

	/* Register Method */
	public static <T extends Entity> void register(EntityType<T> entity, IRenderFactory<? super T> renderer) {
		RenderingRegistry.registerEntityRenderingHandler(entity, renderer);
	}
}
