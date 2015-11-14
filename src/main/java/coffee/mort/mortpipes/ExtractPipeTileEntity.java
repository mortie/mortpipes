package coffee.mort.mortpipes;

import coffee.mort.mortpipes.Pipe;
import coffee.mort.mortpipes.Pipe.AttachType;
import coffee.mort.mortpipes.LogicPipeTileEntity;

import net.minecraft.util.EnumFacing;
import net.minecraft.block.state.IBlockState;

class ExtractPipeTileEntity extends PipeTileEntity {
	public ExtractPipeTileEntity() {}

	@Override
	public void onServerUpdate() {
		super.onServerUpdate();

		tryInsertItemFrom(north, EnumFacing.NORTH);
		tryInsertItemFrom(east, EnumFacing.EAST);
		tryInsertItemFrom(south, EnumFacing.SOUTH);
		tryInsertItemFrom(west, EnumFacing.WEST);
		tryInsertItemFrom(up, EnumFacing.UP);
		tryInsertItemFrom(down, EnumFacing.DOWN);
	}

	private void tryInsertItemFrom(AttachType type, EnumFacing facing) {
		if (type == AttachType.INVENTORY) {

		}
	}
}
