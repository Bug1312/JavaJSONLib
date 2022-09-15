package com.bug1312.javajson.example.client.render.entity.model;

import com.bug1312.javajson.example.common.entity.MovingMannequin;
import com.bug1312.javajson.example.main.ExampleMod;
import com.bug1312.javajson.javajson.IUseJavaJSON;
import com.bug1312.javajson.javajson.JavaJSONParsed;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class ModelMovingMannequin extends BipedModel<MovingMannequin> 
implements IUseJavaJSON { // JavaJSON Extra
	
	private static ResourceLocation AUTON_MODEL_LOCATION = new ResourceLocation(ExampleMod.MODID, "models/entity/auton.json");
	
	public ModelMovingMannequin(float modelSize) {
		super(modelSize);
		registerJavaJSON(AUTON_MODEL_LOCATION); // JavaJSON Extra
	}
		
	@Override // JavaJSON Extra
	public void reload() {
		this.hat.visible = false;

		JavaJSONParsed model = getJavaJSON();
		if(model != null) {
			this.head = model.getPart("Head");
			this.body = model.getPart("Body");
			this.leftArm = model.getPart("LeftArm");
			this.rightArm = model.getPart("RightArm");
			this.leftLeg = model.getPart("LeftLeg");
			this.rightLeg = model.getPart("RightLeg");
		}
	}
}
