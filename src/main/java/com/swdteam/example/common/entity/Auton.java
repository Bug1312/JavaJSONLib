package com.swdteam.example.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class Auton extends MonsterEntity {
	public static final DataParameter<Integer> AUTON_TYPE = EntityDataManager.defineId(Auton.class, DataSerializers.INT);

	public Auton(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public int getAutonType() {
		return this.entityData.get(AUTON_TYPE).intValue() % 7; 
	}
	
	public void setAutonType(int i) {
		if (this.entityData != null) this.entityData.set(AUTON_TYPE, i);
	}
	
	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.23D);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(5, new LookRandomlyGoal(this));

		super.registerGoals();
	}
	
	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		if (this.entityData != null) compound.putInt("variant", this.entityData.get(AUTON_TYPE));
		
		super.addAdditionalSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		if (compound.contains("variant")) this.setAutonType(compound.getInt("variant"));
		else this.setAutonType(this.random.nextInt(7));
		
		super.readAdditionalSaveData(compound);
	}

	@Override
	protected void defineSynchedData() {
		this.getEntityData().define(AUTON_TYPE, random.nextInt(7));
		
		super.defineSynchedData();
	}
}
