package coffee.mort.mortpipes.tileentity;

import coffee.mort.mortpipes.MortPipes;
import coffee.mort.mortpipes.block.Pipe;
import coffee.mort.mortpipes.block.Pipe.AttachType;
import coffee.mort.mortpipes.util.RandUtils;
import coffee.mort.mortpipes.util.ItemUtils;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.world.World;

public class PipeTileEntity extends TileEntity implements IUpdatePlayerListBox {
	private static int updateTimeout = 10;
	private int updateCounter = 0;
	private int speed = 500;

	public AttachType north;
	public AttachType east;
	public AttachType south;
	public AttachType west;
	public AttachType up;
	public AttachType down;

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
		this.updateAttachTypes();

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
		EnumFacing face = getOutputFace(movingStack);

		if (face == null) {
			ItemUtils.spawnItemStack(this.getWorld(), this.pos, movingStack.stack);
		} else {
			TileEntity te = this.getWorld().getTileEntity(pos.offset(face));

			if (te instanceof PipeTileEntity) {
				((PipeTileEntity)te).insertItem(movingStack.stack, face.getOpposite());
			} else if (te instanceof IInventory) {
				ItemStack leftovers = ItemUtils.putStackInInventory((IInventory)te, movingStack.stack, face.getOpposite());
				ItemUtils.spawnItemStack(this.getWorld(), this.pos, leftovers);
			} else {
				ItemUtils.spawnItemStack(this.getWorld(), this.pos, movingStack.stack);
			}
		}
	}

	private EnumFacing getOutputFace(MovingItemStack movingStack) {
		int i = 0;
		EnumFacing faces[] = new EnumFacing[6];

		if (north != AttachType.NONE && movingStack.fromFace != EnumFacing.NORTH)
			faces[i++] = EnumFacing.NORTH;
		if (east != AttachType.NONE && movingStack.fromFace != EnumFacing.EAST)
			faces[i++] = EnumFacing.EAST;
		if (south != AttachType.NONE && movingStack.fromFace != EnumFacing.SOUTH)
			faces[i++] = EnumFacing.SOUTH;
		if (west != AttachType.NONE && movingStack.fromFace != EnumFacing.WEST)
			faces[i++] = EnumFacing.WEST;
		if (up != AttachType.NONE && movingStack.fromFace != EnumFacing.UP)
			faces[i++] = EnumFacing.UP;
		if (down != AttachType.NONE && movingStack.fromFace != EnumFacing.DOWN)
			faces[i++] = EnumFacing.DOWN;

		if (i == 0)
			return null;
		else
			return faces[RandUtils.randInt(0, i - 1)];
	}

	private void updateAttachTypes() {
		World world = this.getWorld();
		north = Pipe.canPipeConnect(world, pos, EnumFacing.NORTH);
		east = Pipe.canPipeConnect(world, pos, EnumFacing.EAST);
		south = Pipe.canPipeConnect(world, pos, EnumFacing.SOUTH);
		west = Pipe.canPipeConnect(world, pos, EnumFacing.WEST);
		up = Pipe.canPipeConnect(world, pos, EnumFacing.UP);
		down = Pipe.canPipeConnect(world, pos, EnumFacing.DOWN);
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
