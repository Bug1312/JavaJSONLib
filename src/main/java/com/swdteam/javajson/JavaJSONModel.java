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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.math.vector.Vector3f;

public class JavaJSONModel extends Model {

	public JavaJSONParsed model;
	public List<JavaJSONRenderer> renderList = new ArrayList<>();
	public Map<String, JavaJSONRenderer> partsList = new HashMap<>();
	public Map<String, List<FontData>> fontData = new HashMap<>();
	public float modelScale;
	public List<FontData> rootfontData = new ArrayList<>();
	
	public JavaJSONModel(int texWidth, int texHeight, float scale, List<FontData> fontData) {
		super(JavaJSONRenderer::transparentRenderType);
		this.texHeight = texHeight;
		this.texWidth = texWidth;
		this.modelScale = scale;
		this.rootfontData = fontData;
	}
	
	public JavaJSONModel(int texWidth, int texHeight, float scale) { this(texWidth, texHeight, scale, null); }
	public JavaJSONModel() { this(0, 0, 1, null); }
		
	public JavaJSONRenderer getPart(String groupName) {
		return (partsList.containsKey(groupName)) ? partsList.get(groupName) : JavaJSONParser.NULL_PART;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		IRenderTypeBuffer bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
		RenderType renderType;
		
		if (model != null && alpha > 0 ) {
			// Alpha Overlay & Map
			if (alpha < 1) {
				boolean alphaMapExists = model.getModelInfo().getAlphaMap() != null;
				
				renderType = JavaJSONRenderer.transparentRenderType(JavaJSONRenderer.generateAlphaOverlay(alphaMapExists ? model.getModelInfo().getAlphaMap() : model.getModelInfo().getTexture()));
				renderLayer(matrixStack, bufferSource.getBuffer(renderType), packedLight, packedOverlay, red, green, blue, 1);
				
				if (alphaMapExists) {
					renderType = JavaJSONRenderer.transparentRenderType(model.getModelInfo().getAlphaMap());
					renderLayer(matrixStack, bufferSource.getBuffer(renderType), packedLight, packedOverlay, red, green, blue, alpha);
				}
			}
			
			// Normal Textures
			renderType = JavaJSONRenderer.transparentRenderType(model.getModelInfo().getTexture());
			renderLayer(matrixStack, bufferSource.getBuffer(renderType), packedLight, packedOverlay, red, green, blue, alpha);
			
			// Light Map
			if(model.getModelInfo().getLightMap() != null) {
				renderType = JavaJSONRenderer.lightMapRenderType(model.getModelInfo().getLightMap());
				renderLayer(matrixStack, bufferSource.getBuffer(renderType), packedLight, packedOverlay, red, green, blue, alpha);
			}
						
			// Font Data
			if (rootfontData.size() > 0) {
				for (FontData fontData : rootfontData) {
					renderFont(matrixStack, null, fontData, fontData.getColor().getRed() / 255 * red, fontData.getColor().getGreen() / 255 * green, fontData.getColor().getBlue() / 255 * blue, alpha, packedLight);
				}
			}	
			
			if (fontData.size() > 0) {
				for (Map.Entry<String, List<FontData>> entry : fontData.entrySet()) {
					for (FontData fontData : entry.getValue()) {
						renderFont(matrixStack, entry.getKey(), fontData, fontData.getColor().getRed() / 255 * red, fontData.getColor().getGreen() / 255 * green, fontData.getColor().getBlue() / 255 * blue, alpha, packedLight);
					}
				}
			}
		}
	}
	
	public void renderLayer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		matrixStack.pushPose();
		matrixStack.translate(0.5, 0.0, 0.5);
		matrixStack.scale(modelScale, modelScale, modelScale);
		matrixStack.translate(0.0, -1.5, 0.0);
			
		for (JavaJSONRenderer renderer : renderList) renderer.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		
		matrixStack.popPose();
	}
	
	public void renderFont(MatrixStack matrixStack, String _parent, FontData fontData, float red, float green, float blue, float alpha, int packedLight) {
		Minecraft mc = Minecraft.getInstance();
		FontRenderer font = mc.font;
		
		JavaJSONRenderer parent = model.getPart(_parent);
		
		matrixStack.pushPose();
				
		matrixStack.translate((parent.x + 8) / 16D, (parent.y - 24) / 16D, (parent.z + 8) / 16D);
		matrixStack.mulPose(Vector3f.ZP.rotation(parent.zRot));
		matrixStack.mulPose(Vector3f.YP.rotation(parent.yRot + (float) Math.toRadians(180)));
		matrixStack.mulPose(Vector3f.XN.rotation(parent.xRot + (float) Math.toRadians(180)));
		matrixStack.translate((parent.x + 8) / -16D, (parent.y - 24) / -16D, (parent.z + 8) / -16D);

		matrixStack.translate(0.5, 0, 0.5);
		matrixStack.translate(fontData.origin[0] / 16D, fontData.origin[1] / 16D, fontData.origin[2] / 16D);			
		
		matrixStack.mulPose(Vector3f.ZP.rotationDegrees(fontData.rotation[2] + 180));					
		matrixStack.mulPose(Vector3f.YN.rotationDegrees(fontData.rotation[1]));
		matrixStack.mulPose(Vector3f.XN.rotationDegrees(fontData.rotation[0]));
				
		float scale = fontData.scale * modelScale / 100F;
		matrixStack.scale(scale, scale, scale);
		
		float adjustmentX = fontData.centered[0] ? -font.width(fontData.value) / 2 : 0;
		float adjustmentY = fontData.centered[1] ? (1 / 32F) * (fontData.scale * modelScale) : 0;

		if (fontData.glow) {
			font.draw(matrixStack, fontData.value, adjustmentX, adjustmentY, new Color(red, green, blue, alpha).hashCode());	
		} else {
			int color = new Color(red, green, blue, alpha).hashCode();
			int realRed = (int) (NativeImage.getR(color) * 0.7D);
			int realGreen = (int) (NativeImage.getG(color) * 0.7D);
			int realBlue = (int) (NativeImage.getB(color) * 0.7D);
			int realColor = NativeImage.combine(0, realBlue, realGreen, realRed);

			font.drawInBatch(fontData.value, adjustmentX, adjustmentY, realColor, false, matrixStack.last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), false, 0, packedLight);	
		}
				
		matrixStack.popPose();
		
	}
	
}
