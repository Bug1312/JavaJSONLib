package com.swdteam.example.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class TileEntityBaseBlock extends Block {
	public Supplier<TileEntity> tileEntitySupplier;

	public TileEntityBaseBlock(Supplier<TileEntity> tileEntitySupplier, Properties properties) {
		super(properties);
		this.tileEntitySupplier = tileEntitySupplier;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return tileEntitySupplier.get();
	}
	
	@SuppressWarnings("deprecation")
	public static class WaterLoggable extends TileEntityBaseBlock implements IWaterLoggable {

		public WaterLoggable(Supplier<TileEntity> tileEntitySupplier, Properties properties) {
			super(tileEntitySupplier, properties);
			this.registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
		}
		
		@Override
		public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
			return VoxelShapes.box(0, 0, 0, 0.9999, 1, 0.9999);
		}
		
		public BlockState getStateForPlacement(BlockItemUseContext context) {
			BlockPos blockpos = context.getClickedPos();
			FluidState fluidstate = context.getLevel().getFluidState(blockpos);
			return super.getStateForPlacement(context).setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
		}
		
		public BlockState updateShape(BlockState state, Direction direction, BlockState state2, IWorld world, BlockPos pos, BlockPos pos2) {
			if (state.getValue(BlockStateProperties.WATERLOGGED)) world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		
			return super.updateShape(state, direction, state2, world, pos, pos2);
		}

		public FluidState getFluidState(BlockState state) {
			return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
		}

		protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state) {
			super.createBlockStateDefinition(state);
			state.add(BlockStateProperties.WATERLOGGED);
		}
		
	}
}
