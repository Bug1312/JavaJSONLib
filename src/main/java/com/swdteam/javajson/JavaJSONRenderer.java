package com.swdteam.javajson;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class JavaJSONRenderer extends ModelRenderer {

	public JavaJSONRenderer(Model model) { super(model); }
	public JavaJSONRenderer() { super(new BlankModel()); }
	
	private static class BlankModel extends Model {
		public BlankModel() { super(RenderType::entityCutoutNoCull); }
		
		@Override public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {}
	}
	
	protected static RenderType lightMapRenderType(ResourceLocation tex) {
		RenderType.State state = RenderType.State.builder()
			.setTextureState(new RenderState.TextureState(tex, false, false))
			.setTransparencyState(new RenderState.TransparencyState("additive_transparency", () -> { RenderSystem.enableBlend(); RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA); }, () -> {}))
			.setAlphaState(new RenderState.AlphaState(0.003921569F))
			.setOverlayState(new RenderState.OverlayState(true))
			.setCullState(new RenderState.CullState(false))
			.createCompositeState(true);
		
		RenderType.State.builder()
				.setTextureState(new RenderState.TextureState(tex, false, false))
				.setWriteMaskState(new RenderState.WriteMaskState(true, false))
				.setFogState(new RenderState.FogState("no_fog", () -> {}, () -> {}))
				.createCompositeState(false);
		
		return RenderType.create("java_json_light_map", DefaultVertexFormats.NEW_ENTITY, 7, 256, true, false, state);
	}
}
