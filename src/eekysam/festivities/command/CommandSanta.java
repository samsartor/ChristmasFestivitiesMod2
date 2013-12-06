package eekysam.festivities.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import eekysam.festivities.Festivities;
import eekysam.festivities.kringle.KringleTeleporter;
import eekysam.festivities.santaclient.SantaClient;

public class CommandSanta extends CommandBase
{
	public final static String url = "http://localhost:8888/festivesanta";

	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "/" + this.getCommandName();
	}

	public String getCommandName()
	{
		return "santa";
	}

	public void processCommand(ICommandSender icommandsender, String[] astring)
	{

		String username = icommandsender.getCommandSenderName();
		this.runCommand(icommandsender, username);
	}

	protected boolean runCommand(ICommandSender icommandsender, String username)
	{
		EntityPlayer player = icommandsender.getEntityWorld().getPlayerEntityByName(username);
		if (player == null)
		{
			return false;
		}
		ItemStack stack = player.inventory.getCurrentItem();
		if (stack != null)
		{
			player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
			ItemStack rec = SantaClient.getClient().sendAndReceiveItem(stack, this.url);
			if (rec != null)
			{
				player.dropPlayerItem(rec);
			}
		}
		return true;
	}
}
