package com.swdteam.example.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.javajson.IUseJavaJSON;

import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;

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
			switch (tile.getBlockState().getValue(HorizontalBlock.FACING)) {
				case NORTH: default: 		break;
				case EAST: 	rotation = 270; break;
				case SOUTH: rotation = 180; break;
				case WEST: 	rotation =  90; break;
			}	
				
		matrixStack.pushPose();
		
		matrixStack.translate(0.5, 0.0, 0.5);
		matrixStack.mulPose(new Quaternion(0, rotation, 0, true));
		matrixStack.translate(-0.5, 0.0, -0.5);

		double tick = Minecraft.getInstance().player.tickCount;
		float calc = (float) ((Math.sin(tick/10))*1.1F+1)/2;
		if(calc < 0) calc = 0;
		if(calc > 1) calc = 1;
		
		getModel().renderToBuffer(matrixStack, buffer.getBuffer(getRenderType()), combinedLight, combinedOverlay, 1,1,1, calc); // JavaJSON Extra
		
		matrixStack.popPose();
	}
}
