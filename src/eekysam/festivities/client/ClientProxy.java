package eekysam.festivities.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import eekysam.festivities.CommonProxy;
import eekysam.festivities.client.render.tileEntity.TileEntitySnowglobeRenderer;
import eekysam.festivities.tile.TileEntitySnowglobe;

public class ClientProxy extends CommonProxy
{
    public void registerRenderers() 
    {
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySnowglobe.class, new TileEntitySnowglobeRenderer());
    }
}
