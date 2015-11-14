package coffee.mort.mortpipes;

import coffee.mort.mortpipes.Pipe;
import coffee.mort.mortpipes.Pipe.AttachType;
import coffee.mort.mortpipes.LogicPipeTileEntity;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
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

	private void tryInsertItemFrom(AttachType type, EnumFacing fromFace) {
		if (type != AttachType.INVENTORY)
			return;

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
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null) {
				System.out.println("slot "+i+" wasn't null");
				return stack;
			}
		}

		return null;
	}
}
