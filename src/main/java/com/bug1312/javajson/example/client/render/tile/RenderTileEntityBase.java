package com.bug1312.javajson.example.client.render.tile;

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

public class RenderTileEntityBase extends TileEntityRenderer<TileEntity> 
implements IUseJavaJSON { // JavaJSON Extra
	
	public RenderTileEntityBase(TileEntityRendererDispatcher rendererDispatcher, ResourceLocation modelPath) {
		super(rendererDispatcher);
		registerJavaJSON(modelPath); // JavaJSON Extra
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
		double calc = ((Math.sin(tick / 70)) * Math.PI + Math.PI);
		
		getJavaJSON().getPart("bb2").visible = false;
		getJavaJSON().getPart("bb").xRot = (float) calc;
		getJavaJSON().getPart("bb").yRot = (float) calc;
		getJavaJSON().getPart("bb").zRot = (float) calc;
		
		getJavaJSON().getPart("bb").x = (float) Math.sin(tick / 10) * 5;
		getJavaJSON().getPart("bb").y = 24 + (float) Math.sin(tick / 10) * 5;
		getJavaJSON().getPart("bb").z = (float) Math.sin(tick / 10) * 5;
		
		
		getModel().renderToBuffer(matrixStack, buffer.getBuffer(getRenderType()), combinedLight, combinedOverlay, 1, 1, 1, 1); // JavaJSON Extra
	
		matrixStack.popPose();
	}
}
