package coffee.mort.mortpipes;

import coffee.mort.mortpipes.MortBlockContainer;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

class Pipe extends MortBlockContainer {
	public Pipe(String name) {
		super(Material.iron, name);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new PipeTileEntity();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
