package eekysam.festivities.events;

import eekysam.festivities.Festivities;
import eekysam.festivities.player.PlayerData;
import eekysam.festivities.tile.TileEntitySnowglobe;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EventHooks
{
	@ForgeSubscribe
	public void onEntityJoin(EntityJoinWorldEvent event)
	{

	}
	
	@ForgeSubscribe
	public void onEntityConstructing(EntityConstructing event)
	{
		if (event.entity instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) event.entity;
			player.registerExtendedProperties(Festivities.PLAYERDATA, new PlayerData());
		}
	}
	
	@ForgeSubscribe
	public float getOffsetFOV(FOVUpdateEvent event)
	{
		Integer integ = Festivities.instance.playerFovAnimation.get(event.entity.getEntityName());
		float fov = event.fov;
		if (integ != null)
		{
			int i = (int) integ;
			
			float portal = integ / (float) TileEntitySnowglobe.portalTime;
			
			fov -= portal * 0.6F;
		}
		return fov;
	}
}
