package com.swdteam.javajson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.javajson.JavaJSONFile.FontData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
		super(RenderType::entityCutoutNoCull);
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
		renderLayer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		if(model != null && model.getModelInfo().getLightMap() != null) {
			RenderType lightMapRenderType = JavaJSONRenderer.lightMapRenderType(model.getModelInfo().getLightMap());
			
			buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(lightMapRenderType);
			renderLayer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}
		
		if(model != null && model.getModelInfo().getModel().fontData != null) {
			Minecraft mc = Minecraft.getInstance();
			FontRenderer font = mc.font;

			for(FontData fontData : model.getModelInfo().getModel().fontData) {
				matrixStack.pushPose();
				
				fontData.setupDeprecation();
				

							
				if(fontData.deprecatedPos) {

					matrixStack.translate(fontData.origin[0], fontData.origin[1], fontData.origin[2]);				

					matrixStack.translate(-0.5, 0.0, -0.5);
					matrixStack.translate(0.0, -1.5, 0.0);

					matrixStack.mulPose(Vector3f.XN.rotationDegrees(fontData.rotation[0] + 180));
					matrixStack.mulPose(Vector3f.YN.rotationDegrees(-fontData.rotation[1]));
					matrixStack.mulPose(Vector3f.ZN.rotationDegrees(fontData.rotation[2]));
					
					float scale = fontData.scale * modelScale / 100F;
					matrixStack.scale(scale, scale, scale);
				} else {
					matrixStack.mulPose(Vector3f.XN.rotationDegrees(fontData.rotation[0]));
					matrixStack.mulPose(Vector3f.YN.rotationDegrees(fontData.rotation[1]));
					matrixStack.mulPose(Vector3f.ZN.rotationDegrees(fontData.rotation[2] + 180));
					
					matrixStack.translate(fontData.origin[0] - 0.5, -fontData.origin[1] - 1.5, fontData.origin[2] - 0.5);				
					float scale = fontData.scale * modelScale / 100F;
					matrixStack.scale(scale, scale, scale);
					


				}
								
				float adjustmentX = fontData.centered ? -font.width(fontData.value) / 2 : 0;
				font.draw(matrixStack, fontData.value, adjustmentX, 0, fontData.getColor());

				matrixStack.popPose();
			}
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
