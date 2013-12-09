package eekysam.festivities.kringle;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import eekysam.festivities.Festivities;
import eekysam.festivities.player.PlayerData;

public class KringleTeleporter extends Teleporter
{
	public KringleTeleporter(WorldServer par1WorldServer)
	{
		super(par1WorldServer);
	}

	@Override
	public void placeInPortal(Entity entity, double x, double y, double z, float par8)
	{
		EntityPlayerMP player = (EntityPlayerMP) entity;
		PlayerData dat = Festivities.getPlayerData(player);
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
			if (dat.globex == 0 && dat.globey == 0 && dat.globez == 0)
			{
				ChunkCoordinates spawn = player.worldObj.getSpawnPoint();
				entity.setPosition(spawn.posX, spawn.posY, spawn.posZ);
			}
			else
			{
				entity.setPosition(dat.globex, dat.globey, dat.globez);
			}
		}
	}

	@Override
	public boolean placeInExistingPortal(Entity par1Entity, double par2, double par4, double par6, float par8)
	{
		return true;
	}

	@Override
	public boolean makePortal(Entity par1Entity)
	{
		return true;
	}
}
