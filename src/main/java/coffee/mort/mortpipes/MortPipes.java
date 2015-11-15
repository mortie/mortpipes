package coffee.mort.mortpipes;

import coffee.mort.mortpipes.block.MortBlockContainer;
import coffee.mort.mortpipes.block.DumbPipe;
import coffee.mort.mortpipes.block.ExtractPipe;
import coffee.mort.mortpipes.item.MortItem;
import coffee.mort.mortpipes.tileentity.PipeTileEntity;
import coffee.mort.mortpipes.tileentity.LogicPipeTileEntity;
import coffee.mort.mortpipes.tileentity.ExtractPipeTileEntity;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = MortPipes.MODID, version = MortPipes.VERSION)
public class MortPipes {
	public static final String MODID = "mortpipes";
	public static final String VERSION = "0.0.0";

	public static MortBlockContainer blockDumbPipe;
	public static MortBlockContainer blockExtractPipe;

	private static void addRenderer(RenderItem ri, MortBlockContainer block) {
		ri.getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(MortPipes.MODID + ":" + block.getName(), "inventory"));
	}

	private static void addRenderer(RenderItem ri, MortItem item) {
		ri.getItemModelMesher().register(item, 0, new ModelResourceLocation(MortPipes.MODID + ":" + item.getName(), "inventory"));
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		blockDumbPipe = new DumbPipe();
		blockExtractPipe = new ExtractPipe();

		GameRegistry.registerTileEntity(PipeTileEntity.class, "pipe");
		GameRegistry.registerTileEntity(LogicPipeTileEntity.class, "pipe_logic");
		GameRegistry.registerTileEntity(ExtractPipeTileEntity.class, "pipe_extract");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		//Register renders
		if (event.getSide() == Side.CLIENT) {
			RenderItem ri = Minecraft.getMinecraft().getRenderItem();

			//Blocks
			addRenderer(ri, blockDumbPipe);
			addRenderer(ri, blockExtractPipe);
		}
	}
}
