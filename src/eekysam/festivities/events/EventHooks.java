package eekysam.festivities.events;

import eekysam.festivities.Festivities;
import eekysam.festivities.player.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
}
