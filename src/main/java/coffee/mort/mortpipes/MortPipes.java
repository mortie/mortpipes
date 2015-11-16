package coffee.mort.mortpipes;

import coffee.mort.mortpipes.block.MortBlockContainer;
import coffee.mort.mortpipes.block.DumbPipe;
import coffee.mort.mortpipes.block.ExtractPipe;
import coffee.mort.mortpipes.item.MortItem;
import coffee.mort.mortpipes.tileentity.PipeTileEntity;
import coffee.mort.mortpipes.tileentity.LogicPipeTileEntity;
import coffee.mort.mortpipes.tileentity.ExtractPipeTileEntity;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import net.minecraft.util.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = MortPipes.MODID, version = MortPipes.VERSION)
public class MortPipes {
	private static final Random rand = new Random();

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
		GameRegistry.registerTileEntity(PipeTileEntity.class, "pipe_te");
		GameRegistry.registerTileEntity(LogicPipeTileEntity.class, "pipe_logic_te");
		GameRegistry.registerTileEntity(ExtractPipeTileEntity.class, "pipe_extract_te");

		blockDumbPipe = new DumbPipe();
		blockExtractPipe = new ExtractPipe();
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

	public static void spawnItemStack(World worldIn, BlockPos pos, ItemStack stack) {
		float offX = rand.nextFloat() * 0.8F + 0.1F;
		float offY = rand.nextFloat() * 0.8F + 0.1F;
		float offZ = rand.nextFloat() * 0.8F + 0.1F;

		EntityItem entity = new EntityItem(
			worldIn,
			pos.getX() + offX,
			pos.getY() + offY,
			pos.getZ() + offZ,
			new ItemStack(stack.getItem(), stack.stackSize, stack.getMetadata())
		);

		entity.motionX = rand.nextGaussian() * 0.05;
		entity.motionY = rand.nextGaussian() * 0.05;
		entity.motionZ = rand.nextGaussian() * 0.05;

		worldIn.spawnEntityInWorld(entity);
	}

	public static int randInt(int min, int max) {
		return rand.nextInt((max - min) + 1) + min;
	}
}
