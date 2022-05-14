package com.swdteam.javajson;

import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.util.ResourceLocation;

public class JavaJSONParsed {

	private ResourceLocation location;
	private ModelInformation modelInfo = new ModelInformation();

	public JavaJSONParsed(ResourceLocation path) {
		this.location = path;
	}

	public ModelInformation getModelInfo() { return modelInfo; }

	public JavaJSONParsed load() {
		modelInfo = JavaJSONParser.getModelInfo(location);
		
		if (modelInfo.getModel() != null) modelInfo.getModel().model = this;
		
		return this;
	}
	
	public JavaJSONRenderer getPart(String groupName) {
		return getModelInfo().getModel().getPart(groupName);
	} 

	public static class ModelInformation {
		private JavaJSONModel model = new JavaJSONModel(0, 0, 1);
		private ResourceLocation texture = MissingTextureSprite.getLocation();
		private ResourceLocation lightMap;
		private ResourceLocation alphaMap;

		public ModelInformation(JavaJSONModel model, ResourceLocation tex, ResourceLocation lightMap, ResourceLocation alphaMap) {
			if(model 	!= null) this.model    = model;
			if(tex   	!= null) this.texture  = tex;
			if(lightMap != null) this.lightMap = lightMap;
			if(alphaMap != null) this.alphaMap = lightMap;
		}
		
		public ModelInformation() { this(null,  null, null, null); }
				
		public JavaJSONModel getModel() 	  { return model; }
		public ResourceLocation getTexture()  { return texture; }
		public ResourceLocation getLightMap() { return lightMap; }
		public ResourceLocation getAlphaMap() { return alphaMap; }
	}

}
