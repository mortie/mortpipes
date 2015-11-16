package coffee.mort.mortpipes.util;

import coffee.mort.mortpipes.util.RandUtils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

//Utility class, which contains a bunch of method which for some reason
//don't seem to exist publicly in Minecraft for some reason,
//and is rather duplicated everywhere ever.
public class ItemUtils {
	public static void spawnItemStack(World worldIn, BlockPos pos, ItemStack stack) {
		float offX = RandUtils.rand.nextFloat() * 0.8F + 0.1F;
		float offY = RandUtils.rand.nextFloat() * 0.8F + 0.1F;
		float offZ = RandUtils.rand.nextFloat() * 0.8F + 0.1F;

		EntityItem entity = new EntityItem(
			worldIn,
			pos.getX() + offX,
			pos.getY() + offY,
			pos.getZ() + offZ,
			new ItemStack(stack.getItem(), stack.stackSize, stack.getMetadata())
		);

		entity.motionX = RandUtils.rand.nextGaussian() * 0.05;
		entity.motionY = RandUtils.rand.nextGaussian() * 0.05;
		entity.motionZ = RandUtils.rand.nextGaussian() * 0.05;

		worldIn.spawnEntityInWorld(entity);
	}

	//Plate the stack into inventory, and return the leftovers
	public static ItemStack putStackInInventory(IInventory inv, ItemStack stack, EnumFacing side) {
		if (inv instanceof ISidedInventory && side != null) {
			ISidedInventory sidedInv = (ISidedInventory)inv;
			int[] slots = sidedInv.getSlotsForFace(side);

			for (int i = 0; i < slots.length && stack != null && stack.stackSize > 0; ++i) {
				stack = insertStack(inv, stack, slots[i], side);
			}
		} else {
			int l = inv.getSizeInventory();
			for (int i = 0; i < l && stack != null && stack.stackSize > 0; ++i) {
				stack = insertStack(inv, stack, i, side);
			}
		}

		if (stack != null && stack.stackSize == 0)
			stack = null;

		return stack;
	}

	//Insert into inventory and return leftovers
	public static ItemStack insertStack(IInventory inv, ItemStack stack, int index, EnumFacing side) {
		ItemStack slotStack = inv.getStackInSlot(index);

		if (canInsertItemInSlot(inv, stack, index, side)) {
			boolean shouldMarkDirty = false;

			if (slotStack == null) {
				int max = Math.min(stack.getMaxStackSize(), inv.getInventoryStackLimit());
				if (max >= stack.stackSize) {
					inv.setInventorySlotContents(index, stack);
					stack = null;
				} else {
					inv.setInventorySlotContents(index, stack.splitStack(max));
				}

				shouldMarkDirty = true;
			} else if (canCombine(slotStack, stack)) {
				int max = Math.min(stack.getMaxStackSize(), inv.getInventoryStackLimit());
				if (max > slotStack.stackSize) {
					int size = Math.min(stack.stackSize, max - slotStack.stackSize);
					stack.stackSize -= size;
					slotStack.stackSize += size;
					shouldMarkDirty = size > 0;
				}
			}

			if (shouldMarkDirty) {
				inv.markDirty();
			}
		}

		return stack;
	}

	public static boolean canInsertItemInSlot(IInventory inv, ItemStack stack, int index, EnumFacing side) {
		if (!inv.isItemValidForSlot(index, stack))
			return false;

		return !(inv instanceof ISidedInventory) || ((ISidedInventory)inv).canInsertItem(index, stack, side);
	}

	//I'm not even going to touch this
	public static boolean canCombine(ItemStack stack1, ItemStack stack2) {
		return stack1.getItem() != stack2.getItem() ? false : (stack1.getMetadata() != stack2.getMetadata() ? false : (stack1.stackSize > stack1.getMaxStackSize() ? false : ItemStack.areItemStackTagsEqual(stack1, stack2)));
	}
}
