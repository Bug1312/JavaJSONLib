package com.swdteam.example.main;

import com.swdteam.example.client.init.ExampleEntityRenders;
import com.swdteam.example.client.init.ExampleTileRenders;
import com.swdteam.example.common.entity.MovingMannequin;
import com.swdteam.example.common.init.ExampleEntities;
import com.swdteam.example.common.init.RegistryHandler;
import com.swdteam.javajson.JavaJSON;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExampleMod.MODID)
public class ExampleMod {
	public static final String MODID = "javajson";

	public ExampleMod() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
				
		RegistryHandler.init(modEventBus);
		
		modEventBus.addListener(this::setupEntityAttributes);
		modEventBus.addListener(this::clientSetup);
				
		JavaJSON.initialize(); // JavaJSON Extra
		
	}
	
	private void setupEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(ExampleEntities.MOVING_MANNEQUIN.get(), MovingMannequin.createAttributes().build());
	}
	
	private void clientSetup(FMLClientSetupEvent event) {
		ExampleTileRenders.init();
		ExampleEntityRenders.init();
	}
}
