 package com.swdteam.example.common.init;

import com.swdteam.example.common.entity.MovingMannequin;
import com.swdteam.example.main.ExampleMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ExampleEntities <E extends Entity> {

	public static RegistryObject<EntityType<MovingMannequin>> MOVING_MANNEQUIN = register("moving_mannequin", EntityType.Builder.of(MovingMannequin::new, EntityClassification.MONSTER).sized(1.0F, 2.0F));
	
	/* Register Method */
	private static <E extends Entity> RegistryObject<EntityType<E>> register(String id, EntityType.Builder<E> entity) {
		RegistryObject<EntityType<E>> registry = RegistryHandler.ENTITY_TYPES.register(id, () -> entity.build(new ResourceLocation(ExampleMod.MODID, id).toString()));	
		return registry;
	}
	
	public static void init() {};	
    
}
