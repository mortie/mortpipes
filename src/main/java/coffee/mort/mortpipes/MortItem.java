package coffee.mort.mortpipes;

import coffee.mort.mortpipes.MortPipes;

import net.minecraft.item.Item;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;

abstract class MortItem extends Item {
	private final String name;

	public MortItem(String name) {
		this.name = name;

		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName(MortPipes.MODID+"_"+name);
		GameRegistry.registerItem(this, name);
	}

	public String getName() {
		return name;
	}
}
