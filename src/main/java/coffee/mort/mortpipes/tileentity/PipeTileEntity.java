package coffee.mort.mortpipes.tileentity;

import coffee.mort.mortpipes.MortPipes;
import coffee.mort.mortpipes.block.Pipe;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.world.World;

public class PipeTileEntity extends TileEntity implements IUpdatePlayerListBox {
	private static int updateTimeout = 10;
	private int updateCounter = 0;
	private int speed = 500;

	public List<MovingItemStack> movingItems = new ArrayList<MovingItemStack>();

	public PipeTileEntity() {}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
	}

	@Override
	public void update() {
		if (!this.getWorld().isRemote) {
			updateCounter -= 1;
			if (updateCounter <= 0) {
				updateCounter = updateTimeout;
				onServerUpdate();
			}
		} else {
			onClientUpdate();
		}
	}

	public void onClientUpdate() {}

	public void onServerUpdate() {
		long time = System.currentTimeMillis();

		for (int i = 0; i < movingItems.size(); ++i) {
			MovingItemStack movingStack = movingItems.get(i);
			if (movingStack != null && movingStack.insertedTime < time - speed) {
				movingItems.remove(movingStack);
				outputItem(movingStack);
			}
		}
	}

	public void insertItem(ItemStack stack, EnumFacing fromFace) {
		MovingItemStack movingStack = new MovingItemStack(stack, fromFace);
		movingItems.add(movingStack);
	}

	public void outputItem(MovingItemStack movingStack) {
		MortPipes.spawnItemStack(this.getWorld(), this.pos, movingStack.stack);
	}

	public class MovingItemStack {
		public ItemStack stack;
		public EnumFacing fromFace;
		public long insertedTime;

		MovingItemStack(ItemStack stack, EnumFacing fromFace) {
			this.stack = stack;
			this.fromFace = fromFace;
			this.insertedTime = System.currentTimeMillis();
			}
		}
}
