package eekysam.festivities.kringle;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class KringleTeleporter extends Teleporter
{
	public KringleTeleporter(WorldServer par1WorldServer)
	{
		super(par1WorldServer);
	}
	
    public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8)
    {
    	par1Entity.fallDistance = -1000.0F;
    	par1Entity.setPosition(5000, 200, 5000);
    }
    
    public boolean placeInExistingPortal(Entity par1Entity, double par2, double par4, double par6, float par8)
    {
    	return true;
    }
    
    public boolean makePortal(Entity par1Entity)
    {
    	return true;
    }
}
