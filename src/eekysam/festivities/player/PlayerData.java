package eekysam.festivities.player;

import eekysam.festivities.tile.TileEntitySnowglobe;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerData implements IExtendedEntityProperties
{
	public int globex;
	public int globey;
	public int globez;
	
	public long santaCooldown = 0;
	
	protected int snowgobePortalTime = 0;
	protected long lastWorldTime = -1;
	protected boolean snowglobe = false;
	
	public static final int snowglobeTimeOut = TileEntitySnowglobe.lookTick + 1;
	
	public int incrementSnowglobe(long worldTime)
	{
		this.snowglobe = true;
		if (lastWorldTime >= 0)
		{
			this.snowgobePortalTime += worldTime - this.lastWorldTime;
		}
		this.lastWorldTime = worldTime;
		return this.getSnowgobePortalTime();
	}
	
	public void testTimeOut(long worldTime)
	{
		if (this.lastWorldTime >= 0)
		{
			if (worldTime - this.lastWorldTime > this.snowglobeTimeOut)
			{
				this.resetSnowglobePortal();
			}
		}
		else
		{
			this.lastWorldTime = worldTime;
		}
	}
	
	public int getSnowgobePortalTime()
	{
		return this.snowgobePortalTime;
	}
	
	public float getSnowgobePortal()
	{
		return this.getSnowgobePortalTime() / (float) TileEntitySnowglobe.portalTime;
	}
	
	public void resetSnowglobePortal()
	{
		this.lastWorldTime = -1;
		this.snowgobePortalTime = 0;
		this.snowglobe = false;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		compound.setInteger("globex", this.globex);
		compound.setInteger("globey", this.globey);
		compound.setInteger("globez", this.globez);
		
		compound.setInteger("snowglobePortal", this.snowgobePortalTime);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		this.globex = compound.getInteger("globex");
		this.globey = compound.getInteger("globey");
		this.globez = compound.getInteger("globez");
	}

	@Override
	public void init(Entity entity, World world)
	{
		
	}
}
