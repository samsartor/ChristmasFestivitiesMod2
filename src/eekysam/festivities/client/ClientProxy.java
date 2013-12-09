package eekysam.festivities.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import eekysam.festivities.CommonProxy;
import eekysam.festivities.Festivities;
import eekysam.festivities.client.render.block.BlockItemRenderer;
import eekysam.festivities.client.render.entity.RenderCandyCreeper;
import eekysam.festivities.client.render.tileEntity.TileEntityPlateRenderer;
import eekysam.festivities.client.render.tileEntity.TileEntitySimpleRenderer;
import eekysam.festivities.client.render.tileEntity.TileEntitySnowMachineRenderer;
import eekysam.festivities.client.render.tileEntity.TileEntitySnowglobeRenderer;
import eekysam.festivities.entity.EntityCandyCreeper;
import eekysam.festivities.tile.TileEntityFireplace;
import eekysam.festivities.tile.TileEntityGarland;
import eekysam.festivities.tile.TileEntityOrnament;
import eekysam.festivities.tile.TileEntityPlate;
import eekysam.festivities.tile.TileEntitySnowMachine;
import eekysam.festivities.tile.TileEntitySnowglobe;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySnowglobe.class, new TileEntitySnowglobeRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlate.class, new TileEntityPlateRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOrnament.class, new TileEntitySimpleRenderer(1));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFireplace.class, new TileEntitySimpleRenderer(2));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySnowMachine.class, new TileEntitySnowMachineRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGarland.class, new TileEntitySimpleRenderer(3));

		Festivities.blockItemRenderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(Festivities.blockItemRenderId, new BlockItemRenderer());

		RenderingRegistry.registerEntityRenderingHandler(EntityCandyCreeper.class, new RenderCandyCreeper());
	}
}
