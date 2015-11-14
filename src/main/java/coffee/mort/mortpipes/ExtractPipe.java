package coffee.mort.mortpipes;

import coffee.mort.mortpipes.LogicPipe;
import coffee.mort.mortpipes.ExtractPipeTileEntity;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

class ExtractPipe extends LogicPipe {
	public ExtractPipe() {
		super("pipe_extract");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new ExtractPipeTileEntity();
	}
}

