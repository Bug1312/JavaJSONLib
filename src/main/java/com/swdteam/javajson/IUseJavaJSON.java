package com.swdteam.javajson;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.ResourceLocation;

public interface IUseJavaJSON {
	default public void registerJavaJSON(ResourceLocation modelPath) { JavaJSONCache.register(this, modelPath); };
	
	default public RenderType getRenderType() { return this.getModel().renderType(this.getTexture()); };

	default public JavaJSONParsed getJavaJSON() { return JavaJSON.getParsedJavaJSON(this); };
	default public Model getModel() { return JavaJSON.getModel(this); };
	default public ResourceLocation getTexture() { return JavaJSON.getTexture(this); };

	default public void reload() {};
}
