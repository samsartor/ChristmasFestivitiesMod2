package eekysam.festivities.kringle;

import eekysam.festivities.Festivities;
import eekysam.festivities.player.PlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class KringleTeleporter extends Teleporter
{
	public KringleTeleporter(WorldServer par1WorldServer)
	{
		super(par1WorldServer);
	}
	
    public void placeInPortal(Entity entity, double x, double y, double z, float par8)
    {
    	PlayerData dat = Festivities.getPlayerData((EntityPlayerMP) entity);
    	if (entity.dimension == Festivities.kringleId)
    	{
    		dat.globex = (int) x;
    		dat.globey = (int) y;
    		dat.globez = (int) z;
	    	entity.fallDistance = -1000.0F;
	    	entity.setPosition(5000, 200, 5000);
    	}
    	else
    	{
    		entity.setPosition(dat.globex, dat.globey, dat.globez);
    	}
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
