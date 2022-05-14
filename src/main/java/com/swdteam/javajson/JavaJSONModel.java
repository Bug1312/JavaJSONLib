package com.swdteam.javajson;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.javajson.JavaJSONFile.FontData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.math.vector.Vector3f;

public class JavaJSONModel extends Model {

	public JavaJSONParsed model;
	public List<JavaJSONRenderer> renderList = new ArrayList<>();
	public Map<String, JavaJSONRenderer> partsList = new HashMap<>();
	public float modelScale;
	public List<FontData> fontData;
	
	public JavaJSONModel(int texWidth, int texHeight, float scale, List<FontData> fontData) {
		super(RenderType::entityTranslucent);
		this.texHeight = texHeight;
		this.texWidth = texWidth;
		this.modelScale = scale;
		this.fontData = fontData;
	}
	
	public JavaJSONModel(int texWidth, int texHeight, float scale) {
		this(texWidth, texHeight, scale, null);
	}
		
	public JavaJSONRenderer getPart(String groupName) {
		return (partsList.containsKey(groupName)) ? partsList.get(groupName) : JavaJSONParser.NULL_PART;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		IRenderTypeBuffer bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
		RenderType tempRenderType;
		
		if(model != null) {
			// Alpha Overlay & Map
			if(alpha < 1) {
				boolean alphaMapExists = model.getModelInfo().getAlphaMap() != null;
				tempRenderType = RenderType.entityTranslucent(JavaJSONRenderer.generateAlphaOverlay(alphaMapExists ? model.getModelInfo().getAlphaMap() : model.getModelInfo().getTexture()));

				renderLayer(matrixStack, bufferSource.getBuffer(tempRenderType), packedLight, packedOverlay, red, green, blue, 1);
				
				if(alphaMapExists) {
					tempRenderType = RenderType.entityTranslucent(model.getModelInfo().getAlphaMap());
					
					renderLayer(matrixStack, bufferSource.getBuffer(tempRenderType), packedLight, packedOverlay, red, green, blue, alpha);
				}
			}
			
			// Normal Model
			renderLayer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			
			// Light Map
			if(model.getModelInfo().getLightMap() != null) {
				tempRenderType = JavaJSONRenderer.lightMapRenderType(model.getModelInfo().getLightMap());
				
				renderLayer(matrixStack, bufferSource.getBuffer(tempRenderType), packedLight, packedOverlay, red, green, blue, alpha);
			}
			
			// Font Data
			if(model.getModelInfo().getModel().fontData != null) {
				for(FontData fontData : model.getModelInfo().getModel().fontData) {
					renderFont(matrixStack, fontData, fontData.getColor().getRed() / 255 * red, fontData.getColor().getGreen() / 255 * green, fontData.getColor().getBlue() / 255 * blue, alpha);
				}
			}		
		}
	}
	
	public void renderLayer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		matrixStack.pushPose();
		matrixStack.translate(0.5, 0.0, 0.5);
		matrixStack.scale(modelScale, modelScale, modelScale);
		matrixStack.translate(0.0, -1.5, 0.0);
			
		for(JavaJSONRenderer renderer : renderList) renderer.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		
		matrixStack.popPose();
	}
	
	public void renderFont(MatrixStack matrixStack, FontData fontData, float red, float green, float blue, float alpha) {
		Minecraft mc = Minecraft.getInstance();
		FontRenderer font = mc.font;

		matrixStack.pushPose();
		
		matrixStack.translate(0.5, 0, 0.5);
		matrixStack.translate(fontData.origin[0] / 16D, fontData.origin[1] / 16D, fontData.origin[2] / 16D);	
		
		matrixStack.mulPose(Vector3f.XN.rotationDegrees(fontData.rotation[0]));
		matrixStack.mulPose(Vector3f.YN.rotationDegrees(fontData.rotation[1]));
		matrixStack.mulPose(Vector3f.ZN.rotationDegrees(fontData.rotation[2] + 180));					
				
		float scale = fontData.scale * modelScale / 100F;
		matrixStack.scale(scale, scale, scale);
		
		float adjustmentX = fontData.centered[0] ? -font.width(fontData.value) / 2 : 0;
		float adjustmentY = fontData.centered[1] ? (1 / 32F) * (fontData.scale * modelScale) : 0;

		font.draw(matrixStack, fontData.value, adjustmentX, adjustmentY, new Color(red, green, blue, alpha).hashCode());
		
		matrixStack.popPose();
		
	}
	
}
