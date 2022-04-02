package com.swdteam.example.common.init;


import com.swdteam.example.common.block.StatueBlock;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ExampleBlocks {

	public static RegistryObject<Block> STATUE = register("statue", new StatueBlock(Properties.of(Material.STONE).noOcclusion()), true);

	/* Register Methods */
	private static RegistryObject<Block> register(String name, Block block, Item.Properties properties, boolean hasItem) {
		if (hasItem) {
			Item item = new BlockItem(block, properties);
			RegistryHandler.ITEMS.register(name, () -> item);
		}
		return RegistryHandler.BLOCKS.register(name, () -> block);
	}	
	
	private static RegistryObject<Block> register(String name, Block block, boolean hasItem) {
		return register(name, block, new Item.Properties().tab(ExampleTabs.EXAMPLE_TAB), hasItem);
	}	
	
	public static void init() {};	
	
}
