package coffee.mort.mortpipes.tileentity;

import coffee.mort.mortpipes.block.Pipe;
import coffee.mort.mortpipes.tileentity.LogicPipeTileEntity;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.block.state.IBlockState;

public class ExtractPipeTileEntity extends PipeTileEntity {
	public ExtractPipeTileEntity() {}

	@Override
	public void onServerUpdate() {
		super.onServerUpdate();

		tryInsertItemFrom(EnumFacing.NORTH);
		tryInsertItemFrom(EnumFacing.EAST);
		tryInsertItemFrom(EnumFacing.SOUTH);
		tryInsertItemFrom(EnumFacing.WEST);
		tryInsertItemFrom(EnumFacing.UP);
		tryInsertItemFrom(EnumFacing.DOWN);
	}

	private void tryInsertItemFrom(EnumFacing fromFace) {
		BlockPos offPos = pos.offset(fromFace);
		TileEntity te = getWorld().getTileEntity(offPos);

		if (te instanceof IInventory) {
			ItemStack stack = getNextStack((IInventory)te);
			if (stack != null)
				insertItem(stack, fromFace);
		}
	}

	private ItemStack getNextStack(IInventory inv) {
		int l = inv.getSizeInventory();

		for (int i = 0; i < l; ++i) {
			ItemStack stack = inv.decrStackSize(i, 1);
			if (stack != null) {
				return stack;
			}
		}

		return null;
	}
}
