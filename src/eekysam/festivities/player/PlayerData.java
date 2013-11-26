package eekysam.festivities.player;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerData implements IExtendedEntityProperties
{
	public int globex;
	public int globey;
	public int globez;
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		compound.setInteger("globex", this.globex);
		compound.setInteger("globey", this.globey);
		compound.setInteger("globez", this.globez);
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
