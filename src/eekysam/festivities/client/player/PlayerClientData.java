package eekysam.festivities.client.player;

import eekysam.festivities.player.PlayerData;
import eekysam.festivities.tile.TileEntitySnowglobe;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerClientData extends PlayerData
{	
	public float getSnowgobePortal(long worldTime)
	{
		if (this.snowglobe)
		{
			int t = this.snowgobePortalTime + (int) (worldTime - this.lastWorldTime);
			return t / (float) TileEntitySnowglobe.portalTime;
		}
		else
		{
			return 0.0F;
		}
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		//super.saveNBTData(compound);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		super.loadNBTData(compound);
		
		this.snowgobePortalTime = compound.getInteger("snowglobePortal");
	}
}
