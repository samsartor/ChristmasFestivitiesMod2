package eekysam.festivities.events;

import eekysam.festivities.Festivities;
import eekysam.festivities.client.player.PlayerClientData;
import eekysam.festivities.player.PlayerData;
import eekysam.festivities.tile.TileEntitySnowglobe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

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
		if (event.entity instanceof EntityPlayerSP)
		{
			EntityPlayerSP player = (EntityPlayerSP) event.entity;
			player.registerExtendedProperties(Festivities.PLAYERDATA, new PlayerClientData());
		}
	}

	@ForgeSubscribe
	public void getOffsetFOV(FOVUpdateEvent event)
	{
		float fov = event.fov;
		EntityPlayerSP player = event.entity;
		PlayerClientData data = (PlayerClientData) player.getExtendedProperties(Festivities.PLAYERDATA);
		if (player.dimension == Festivities.kringleId)
		{
			fov += data.getSnowgobePortal(player.worldObj.getWorldTime()) * 0.6F;
		}
		else
		{
			fov -= data.getSnowgobePortal(player.worldObj.getWorldTime()) * 0.6F;
		}
		event.newfov = fov;
	}

	@ForgeSubscribe
	public void entityUpdateEvent(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.entityLiving;

		if (entity.ticksExisted % 5 == 0 && entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			PlayerData data = (PlayerData) player.getExtendedProperties(Festivities.PLAYERDATA);

			data.testTimeOut(player.worldObj.getWorldTime());
		}
	}

	@ForgeSubscribe
	public void onBlockHarvest(HarvestDropsEvent event)
	{
		if (event.block.blockID == Block.grass.blockID)
		{
			if (event.world.rand.nextFloat() <= 0.08F)
			{
				event.drops.add(new ItemStack(Festivities.berries, 1));
			}
			if (event.world.rand.nextFloat() <= 0.1F)
			{
				event.drops.add(new ItemStack(Festivities.holly, 1));
			}
		}
		if (event.block instanceof BlockFlower)
		{
			if (event.world.rand.nextFloat() <= 0.15F)
			{
				event.drops.add(new ItemStack(Festivities.berries, 2));
			}
			if (event.world.rand.nextFloat() <= 0.24F)
			{
				event.drops.add(new ItemStack(Festivities.holly, 1));
			}
		}
	}
}
