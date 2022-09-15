package com.bug1312.javajson.example.common.init;

import java.util.function.Supplier;

import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;

public class ExampleItems {

	public static final RegistryObject<Item>
	MOVING_MANNEQUIN_SPAWNER = register("moving_mannequin_spawner", () -> new ForgeSpawnEggItem(() -> ExampleEntities.MOVING_MANNEQUIN.get(), 9986629, 12157536, new Item.Properties().tab(ExampleTabs.EXAMPLE_TAB)));
	
	/* Register Methods */
	private static RegistryObject<Item> register(String name, Supplier<Item> item) {
		return RegistryHandler.ITEMS.register(name, item);
	}	

	public static void init() {};	

}
