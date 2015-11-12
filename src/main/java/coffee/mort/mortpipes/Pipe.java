package coffee.mort.mortpipes;

import coffee.mort.mortpipes.MortBlockContainer;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;

class Pipe extends MortBlockContainer {
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");

	public Pipe(String name) {
		super(Material.iron, name);

		this.setDefaultState(this.blockState.getBaseState()
			.withProperty(NORTH, false)
			.withProperty(EAST, false)
			.withProperty(SOUTH, false)
			.withProperty(WEST, false)
			.withProperty(UP, false)
			.withProperty(DOWN, false));

		setHitbox(this.blockState.getBaseState());
	}

	private void setHitbox(IBlockState state) {
		double min = 0.2;
		double max = 0.8;

		this.minX = min;
		this.maxX = max;
		this.minY = min;
		this.maxY = max;
		this.minZ = min;
		this.maxZ = max;
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		state = state.withProperty(NORTH, this.canPipeConnect(worldIn, pos, EnumFacing.NORTH));
		state = state.withProperty(EAST, this.canPipeConnect(worldIn, pos, EnumFacing.EAST));
		state = state.withProperty(SOUTH, this.canPipeConnect(worldIn, pos, EnumFacing.SOUTH));
		state = state.withProperty(WEST, this.canPipeConnect(worldIn, pos, EnumFacing.WEST));
		state = state.withProperty(UP, this.canPipeConnect(worldIn, pos, EnumFacing.UP));
		state = state.withProperty(DOWN, this.canPipeConnect(worldIn, pos, EnumFacing.DOWN));

		return state;
	}

	private boolean canPipeConnect(IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
		BlockPos offPos = pos.offset(direction);
		Block block = worldIn.getBlockState(offPos).getBlock();

		return (block instanceof Pipe);
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
		return new BlockState(this, new IProperty[] {NORTH, EAST, SOUTH, WEST, UP, DOWN});
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
