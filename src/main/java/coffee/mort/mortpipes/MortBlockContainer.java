package coffee.mort.mortpipes;

import coffee.mort.mortpipes.MortPipes;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;

abstract class MortBlockContainer extends BlockContainer {
	private final String name;

	public MortBlockContainer(Material m, String name) {
		super(m);
		this.name = name;

		setCreativeTab(CreativeTabs.tabBlock);
		setUnlocalizedName(MortPipes.MODID+"_"+name);
		GameRegistry.registerBlock(this, name);
	}

	public String getName() {
		return name;
	}

	@Override
	public int getRenderType() {
		return 3;
	}
}
