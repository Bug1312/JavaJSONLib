package com.swdteam.javajson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;

public class JavaJSONModel extends Model {

	public JavaJSONParsed model;
	public List<JavaJSONRenderer> renderList = new ArrayList<>();
	public Map<String, JavaJSONRenderer> partsList = new HashMap<>();
	public float modelScale;
	
	public JavaJSONModel(int texWidth, int texHeight, float scale) {
		super(RenderType::entityCutoutNoCull);
		this.texHeight = texHeight;
		this.texWidth = texWidth;
		this.modelScale = scale;
	}
		
	public JavaJSONRenderer getPart(String groupName) {
		return (partsList.containsKey(groupName)) ? partsList.get(groupName) : JavaJSONParser.NULL_PART;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		renderLayer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		if(model != null && model.getModelInfo().getLightMap() != null) {
			RenderType lightMapRenderType = JavaJSONRenderer.lightMapRenderType(model.getModelInfo().getLightMap());
			
			buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(lightMapRenderType);
			renderLayer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}
	
	private void renderLayer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		matrixStack.pushPose();
		matrixStack.translate(0.5, 0.0, 0.5);
		matrixStack.scale(modelScale, modelScale, modelScale);
		matrixStack.translate(0.0, -1.5, 0.0);
			
		for(JavaJSONRenderer renderer : renderList) renderer.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		
		matrixStack.popPose();
	}
	
}
