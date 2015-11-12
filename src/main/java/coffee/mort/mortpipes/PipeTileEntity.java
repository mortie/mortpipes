package coffee.mort.mortpipes;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

class PipeTileEntity extends TileEntity {
	public List<ItemStack> items = new ArrayList<ItemStack>();

	public PipeTileEntity() {}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
	}
}
