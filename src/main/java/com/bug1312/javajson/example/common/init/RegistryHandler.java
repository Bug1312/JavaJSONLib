package com.bug1312.javajson.example.common.init;

import com.bug1312.javajson.example.main.ExampleMod;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ExampleMod.MODID);
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ExampleMod.MODID);

	public static void init(IEventBus eventBus) {
		ExampleItems.init();
		ExampleBlocks.init();
		ExampleEntities.init();
		ExampleTiles.init();
		
		ITEMS.register(eventBus);
		BLOCKS.register(eventBus);
		ENTITY_TYPES.register(eventBus);
		TILE_ENTITY_TYPES.register(eventBus);
	}
}
