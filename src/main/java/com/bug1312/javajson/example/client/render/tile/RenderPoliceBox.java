package com.bug1312.javajson.example.client.render.tile;

import com.bug1312.javajson.example.main.ExampleMod;
import com.bug1312.javajson.javajson.IUseJavaJSON;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class RenderPoliceBox extends TileEntityRenderer<TileEntity> 
implements IUseJavaJSON { // JavaJSON Extra
	
	private static ResourceLocation POLICE_BOX_MODEL = new ResourceLocation(ExampleMod.MODID, "/models/tileentity/police_box.json");
	
	public RenderPoliceBox(TileEntityRendererDispatcher rendererDispatcher) {
		super(rendererDispatcher);
		registerJavaJSON(POLICE_BOX_MODEL); // JavaJSON Extra
	}
	
	@Override
	public void render(TileEntity tile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {	
		float rotation = 0;
		if(tile.getBlockState().getValues().containsKey(HorizontalBlock.FACING))
			rotation = tile.getBlockState().getValue(HorizontalBlock.FACING).toYRot();		
		
		matrixStack.pushPose();
		
		matrixStack.translate(0.5D, 0.5D, 0.5D);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(-rotation));
		matrixStack.translate(-0.5D, -0.5D, -0.5D);
		
		Minecraft mc = Minecraft.getInstance();
		double tick = mc.player.tickCount;
		float calc = (float) ((Math.sin(tick / 10)) * 1.2F + 1) / 2;
		if (calc < 0) calc = 0;
		if (calc > 1) calc = 1;
		
		getModel().renderToBuffer(matrixStack, buffer.getBuffer(getRenderType()), combinedLight, combinedOverlay, 1, 1, 1, calc); // JavaJSON Extra
		
		matrixStack.popPose();
	}
}
