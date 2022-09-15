package com.bug1312.javajson.example.common.init;


import java.util.function.Supplier;

import com.bug1312.javajson.example.common.block.BlackboardBlock;
import com.bug1312.javajson.example.common.block.PoliceBoxBlock;
import com.bug1312.javajson.example.common.block.StatueBlock;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ExampleBlocks {

	public static RegistryObject<Block> 
	STATUE 		= register("statue", 	 () -> new StatueBlock(Properties.of(Material.STONE).noOcclusion())),
	BLACKBOARD 	= register("blackboard", () -> new BlackboardBlock(Properties.of(Material.WOOD).noOcclusion())),
	POLICE_BOX 	= register("police_box", () -> new PoliceBoxBlock(Properties.of(Material.WOOD).noOcclusion()));

	/* Register Methods */
	private static <B extends Block> RegistryObject<Block> register(String name, Supplier<B> block) {
		RegistryObject<Block> regObj = RegistryHandler.BLOCKS.register(name, block);
		RegistryHandler.ITEMS.register(name, () -> new BlockItem(regObj.get(), new Item.Properties().tab(ExampleTabs.EXAMPLE_TAB)));
		return regObj;
	}	
	
	public static void init() {};	
	
}
