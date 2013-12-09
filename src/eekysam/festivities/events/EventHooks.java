package eekysam.festivities.events;

import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.client.GuiIdMismatchScreen;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.ItemData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.Festivities;
import eekysam.festivities.client.gui.GuiMismatch;
import eekysam.festivities.client.player.PlayerClientData;
import eekysam.festivities.player.PlayerData;

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
		else if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			player.registerExtendedProperties(Festivities.PLAYERDATA, new PlayerClientData());
		}
	}

	@SideOnly(Side.CLIENT)
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

			if (data.santaCooldown > 0)
			{
				data.santaCooldown -= 5;
			}

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

	@SideOnly(Side.CLIENT)
	@ForgeSubscribe
	public void onGUIOpen(GuiOpenEvent event)
	{
		GuiScreen gui = event.gui;

		if (gui instanceof GuiIdMismatchScreen && !(Minecraft.getMinecraft().currentScreen instanceof GuiMismatch))
		{
			boolean isme = false;
			Map<Integer, ItemData> diffs = GameData.gateWorldLoadingForValidation().entriesOnlyOnLeft();
			Iterator<Integer> keys = diffs.keySet().iterator();
			while (keys.hasNext())
			{
				ItemData item = diffs.get(keys.next());
				isme |= item.getModId().equals(Festivities.ID);
			}

			if (isme)
			{
				GuiScreen sc = new GuiMismatch(this.validationMessage(), gui);
				event.gui = sc;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private String validationMessage()
	{
		return "The Christmas Festivities Mod is being yelled at by Forge :(\nForge is upset because your world has blocks or items that\n   are different from what the mod has told Forge\n\nIt could be that:\n\nA. You just updated the mod\n   During Version 2.3.x.x I was forced to change all the item Ids\n\nB. You just changed back to an old version\n\nC. You have changed the Config file\n\nThis will result in some blocks/items that you placed or have stored\n   changing, disappearing, or becoming bugged.\nSorry D:";
	}
}
