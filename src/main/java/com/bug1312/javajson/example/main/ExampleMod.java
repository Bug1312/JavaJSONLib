package com.bug1312.javajson.example.main;

import com.bug1312.javajson.example.client.init.ExampleEntityRenders;
import com.bug1312.javajson.example.client.init.ExampleTileRenders;
import com.bug1312.javajson.example.common.entity.MovingMannequin;
import com.bug1312.javajson.example.common.init.ExampleEntities;
import com.bug1312.javajson.example.common.init.RegistryHandler;
import com.bug1312.javajson.javajson.JavaJSON;

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
