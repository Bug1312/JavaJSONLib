package com.bug1312.javajson.javajson;

import java.io.IOException;
import java.io.InputStream;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class JavaJSONRenderer extends ModelRenderer {

	public JavaJSONModel model;
	
	public JavaJSONRenderer(JavaJSONModel model) { super(model); this.model = model; }
	public JavaJSONRenderer() { super(new BlankModel()); }
	
	private static class BlankModel extends Model {
		public BlankModel() { super(JavaJSONRenderer::transparentRenderType); }
		
		@Override public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {}
	}
	
	protected static RenderType transparentRenderType(ResourceLocation tex) {
		RenderType.State state = RenderType.State.builder()
				.setTextureState(new RenderState.TextureState(tex, false, false))
				.setTransparencyState(new RenderState.TransparencyState("translucent_transparency", () -> { RenderSystem.enableBlend(); RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA); }, () -> { RenderSystem.disableBlend(); RenderSystem.defaultBlendFunc(); }))
				.setDiffuseLightingState(new RenderState.DiffuseLightingState(true))
				.setAlphaState(new RenderState.AlphaState(0.003921569F))
				.setCullState(new RenderState.CullState(true))
				.setLightmapState(new RenderState.LightmapState(true))
				.setOverlayState(new RenderState.OverlayState(true))
				.createCompositeState(true);
		
		RenderType.State.builder()
				.setTextureState(new RenderState.TextureState(tex, false, false))
				.setWriteMaskState(new RenderState.WriteMaskState(true, false))
				.setFogState(new RenderState.FogState("no_fog", () -> {}, () -> {}))
				.createCompositeState(false);
		
		return RenderType.create("java_json_default", DefaultVertexFormats.NEW_ENTITY, 7, 256, true, false, state);
	}
	
	protected static RenderType lightMapRenderType(ResourceLocation tex) {
		RenderType.State state = RenderType.State.builder()
			.setTextureState(new RenderState.TextureState(tex, false, false))
			.setTransparencyState(new RenderState.TransparencyState("additive_transparency", () -> { RenderSystem.enableBlend(); RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA); }, () -> {}))
			.setAlphaState(new RenderState.AlphaState(0.003921569F))
			.setOverlayState(new RenderState.OverlayState(true))
			.setCullState(new RenderState.CullState(false))
			.createCompositeState(true);
		
		return RenderType.create("java_json_light_map", DefaultVertexFormats.NEW_ENTITY, 7, 256, true, false, state);
	}
	
	protected static ResourceLocation generateAlphaOverlay(ResourceLocation texture) {
		try {
			Minecraft mc = Minecraft.getInstance();
			ResourceLocation newLocation = new ResourceLocation("javajson:textures/generated/alpha_overlay/" + texture.getPath());
			
			InputStream stream = mc.getResourceManager().getResource(texture).getInputStream();
			NativeImage originalImage = NativeImage.read(stream);
			
			NativeImage newImage = new NativeImage(originalImage.getWidth(), originalImage.getHeight(), false);
			newImage.copyFrom(originalImage);
			
			for (int y = 0; y < originalImage.getHeight(); y++) {
				for (int x = 0; x < originalImage.getWidth(); x++) {
					int pixel = originalImage.getPixelRGBA(x, y);
					
					int alpha = NativeImage.getA(pixel);
					int red   = NativeImage.getR(pixel);
					int blue  = NativeImage.getG(pixel);
					int green = NativeImage.getB(pixel);
					
					if (alpha > 0) newImage.setPixelRGBA(x, y, NativeImage.combine(0x02, green, blue, red));
				}
			}
			
			return mc.getTextureManager().register(newLocation.getPath(), new DynamicTexture(newImage));
		
		} catch (IOException e) { e.printStackTrace(); }
		
		return texture;
	}
}
