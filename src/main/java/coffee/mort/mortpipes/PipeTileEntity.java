package coffee.mort.mortpipes;

import coffee.mort.mortpipes.Pipe;
import coffee.mort.mortpipes.Pipe.AttachType;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.util.EnumFacing;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

class PipeTileEntity extends TileEntity implements IUpdatePlayerListBox {
	private static int updateTimeout = 10;
	private int updateCounter = 0;
	private int speed = 500;

	public AttachType north = AttachType.NONE;
	public AttachType east = AttachType.NONE;
	public AttachType south = AttachType.NONE;
	public AttachType west = AttachType.NONE;
	public AttachType up = AttachType.NONE;
	public AttachType down = AttachType.NONE;

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
		System.out.println("hi");
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

		for (MovingItemStack movingStack: movingItems) {
			if (movingStack.insertedTime < time - speed) {
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
		System.out.println("Outputting "+movingStack.stack.getItem().getUnlocalizedName());
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
