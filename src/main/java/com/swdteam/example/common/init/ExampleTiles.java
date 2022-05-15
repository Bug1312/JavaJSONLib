package com.swdteam.example.common.init;

import java.util.function.Supplier;

import com.swdteam.example.common.tile.BlackboardTile;
import com.swdteam.example.common.tile.PoliceBoxTile;
import com.swdteam.example.common.tile.StatueTile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class ExampleTiles {
	
	public static RegistryObject<TileEntityType<StatueTile>> STATUE = register("statue", () -> TileEntityType.Builder.of(StatueTile::new, ExampleBlocks.STATUE.get()).build(null));
	public static RegistryObject<TileEntityType<BlackboardTile>> BLACKBOARD = register("blackboard", () -> TileEntityType.Builder.of(BlackboardTile::new, ExampleBlocks.BLACKBOARD.get()).build(null));
	public static RegistryObject<TileEntityType<PoliceBoxTile>> POLICE_BOX = register("police_box", () -> TileEntityType.Builder.of(PoliceBoxTile::new, ExampleBlocks.POLICE_BOX.get()).build(null));

	/* Register Method */
	private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String id, Supplier<TileEntityType<T>> supplier) {
		RegistryObject<TileEntityType<T>> registryItem = RegistryHandler.TILE_ENTITY_TYPES.register(id, supplier);
		return registryItem;
	}
	
	public static void init() {};	

}
