package com.bug1312.javajson.javajson;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class JavaJSON {

	public static void initialize() {
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> JavaJSONCache::init);
	}
	
	public static JavaJSONParsed getParsedJavaJSON(IUseJavaJSON part) {
		return JavaJSONParser.loadModel(JavaJSONCache.reloadableModels.get(part));
	}
	
	public static Model getModel(IUseJavaJSON part) {
		if(getParsedJavaJSON(part) == null) return null;
		return getParsedJavaJSON(part).getModelInfo().getModel();
	}
	
	public static ResourceLocation getTexture(IUseJavaJSON part) {
		if(getParsedJavaJSON(part) == null) return null;
		return getParsedJavaJSON(part).getModelInfo().getTexture();
	}
	
}
