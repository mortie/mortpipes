package coffee.mort.mortpipes.block;

import coffee.mort.mortpipes.block.MortBlockContainer;
import coffee.mort.mortpipes.tileentity.PipeTileEntity;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;

public class Pipe extends MortBlockContainer {
	public static final PropertyEnum NORTH = PropertyEnum.create("north", Pipe.AttachType.class);
	public static final PropertyEnum EAST = PropertyEnum.create("east", Pipe.AttachType.class);
	public static final PropertyEnum SOUTH = PropertyEnum.create("south", Pipe.AttachType.class);
	public static final PropertyEnum WEST = PropertyEnum.create("west", Pipe.AttachType.class);
	public static final PropertyEnum UP = PropertyEnum.create("up", Pipe.AttachType.class);
	public static final PropertyEnum DOWN = PropertyEnum.create("down", Pipe.AttachType.class);
	public static final PropertyBool NOBLOCK = PropertyBool.create("noblock");

	public Pipe(String name) {
		super(Material.iron, name);

		this.setDefaultState(this.blockState.getBaseState()
			.withProperty(NORTH, AttachType.NONE)
			.withProperty(EAST, AttachType.NONE)
			.withProperty(SOUTH, AttachType.NONE)
			.withProperty(WEST, AttachType.NONE)
			.withProperty(UP, AttachType.NONE)
			.withProperty(DOWN, AttachType.NONE)
			.withProperty(NOBLOCK, false));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);

		float min = 0.3F;
		float max = 0.7F;
		float bigmin = 0F;
		float bigmax = 1F;

		float minX = min;
		float maxX = max;
		float minY = min;
		float maxY = max;
		float minZ = min;
		float maxZ = max;

		this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		AttachType north = canPipeConnect(worldIn, pos, EnumFacing.NORTH);
		AttachType east = canPipeConnect(worldIn, pos, EnumFacing.EAST);
		AttachType south = canPipeConnect(worldIn, pos, EnumFacing.SOUTH);
		AttachType west = canPipeConnect(worldIn, pos, EnumFacing.WEST);
		AttachType up = canPipeConnect(worldIn, pos, EnumFacing.UP);
		AttachType down = canPipeConnect(worldIn, pos, EnumFacing.DOWN);

		state = state.withProperty(NORTH, north);
		state = state.withProperty(EAST, east);
		state = state.withProperty(SOUTH, south);
		state = state.withProperty(WEST, west);
		state = state.withProperty(UP, up);
		state = state.withProperty(DOWN, down);

		if (
			(
				(north != AttachType.NONE && south != AttachType.NONE) &&
				(east == AttachType.NONE && west == AttachType.NONE) &&
				(up == AttachType.NONE && down == AttachType.NONE)
			) ||
			(
				(north == AttachType.NONE && south == AttachType.NONE) &&
				(east != AttachType.NONE && west != AttachType.NONE) &&
				(up == AttachType.NONE && down == AttachType.NONE)
			) ||
			(
				(north == AttachType.NONE && south == AttachType.NONE) &&
				(east == AttachType.NONE && west == AttachType.NONE) &&
				(up != AttachType.NONE && down != AttachType.NONE)
			)
		) {
			state = state.withProperty(NOBLOCK, true);
		} else {
			state = state.withProperty(NOBLOCK, false);
		}

		return state;
	}

	private AttachType canPipeConnect(IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
		BlockPos offPos = pos.offset(direction);
		Block block = worldIn.getBlockState(offPos).getBlock();

		if (block instanceof Pipe)
			return AttachType.PIPE;

		TileEntity ent = worldIn.getTileEntity(offPos);
		if (ent != null && ent instanceof IInventory)
			return AttachType.INVENTORY;

		return AttachType.NONE;
	}

	public static enum AttachType implements IStringSerializable {
		PIPE("pipe"),
		INVENTORY("inventory"),
		NONE("none");
		private final String name;

		private AttachType(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

		public String getName() {
			return name;
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {NORTH, EAST, SOUTH, WEST, UP, DOWN, NOBLOCK});
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new PipeTileEntity();
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
