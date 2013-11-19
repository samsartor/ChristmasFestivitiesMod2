package eekysam.festivities.events;

import eekysam.festivities.Festivities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EventHooks
{
	@ForgeSubscribe
	public void onEntityJoin(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			if (event.world.isRemote)
			{
		    	if (Festivities.TESTVERSION)
		    	{
		    		Festivities.SendChat(player, Festivities.TESTMSG);
		    	}
		    	else
		    	{
		    		Festivities.SendChat(player, Festivities.MSG);
		    	}
			}
		}
	}
}
