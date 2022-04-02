package com.swdteam.example.common.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ExampleTabs {
	public static ItemGroup EXAMPLE_TAB = new ItemGroup("javajson") {
		@Override
		public ItemStack makeIcon() {
			if(ExampleBlocks.STATUE.isPresent()) return new ItemStack(ExampleBlocks.STATUE.get());
			return ItemStack.EMPTY;
		}
	};
}
