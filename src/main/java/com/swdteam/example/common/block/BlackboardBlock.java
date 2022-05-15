package com.swdteam.example.common.block;

import com.swdteam.example.common.tile.BlackboardTile;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class BlackboardBlock extends RotatableTileEntityBase.WaterLoggable {

	public BlackboardBlock(Properties properties) {
		super(BlackboardTile::new, properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch(state.getValue(FACING)) {
			default:
			case NORTH: return VoxelShapes.or(VoxelShapes.box(0, 0, 15/16D, 1, 1, 1));
			case EAST: 	return VoxelShapes.or(VoxelShapes.box(0, 0, 0, 1/16D, 1, 1)); 
			case SOUTH: return VoxelShapes.or(VoxelShapes.box(0, 0, 0, 1, 1, 1/16D)); 
			case WEST: 	return VoxelShapes.or(VoxelShapes.box(15/16D, 0, 0, 1, 1, 1));
		}
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

}
