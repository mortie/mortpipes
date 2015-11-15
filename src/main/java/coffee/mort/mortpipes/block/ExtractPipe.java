package coffee.mort.mortpipes.block;

import coffee.mort.mortpipes.block.LogicPipe;
import coffee.mort.mortpipes.tileentity.ExtractPipeTileEntity;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class ExtractPipe extends LogicPipe {
	public ExtractPipe() {
		super("pipe_extract");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new ExtractPipeTileEntity();
	}
}

