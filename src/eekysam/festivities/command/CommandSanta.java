package eekysam.festivities.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import eekysam.festivities.Festivities;
import eekysam.festivities.player.PlayerData;
import eekysam.festivities.santaclient.SantaClient;
import eekysam.festivities.users.UserType;

public class CommandSanta extends CommandBase
{
	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "/" + this.getCommandName();
	}

	@Override
	public String getCommandName()
	{
		return "santa";
	}

	@Override
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
		PlayerData data = (PlayerData) player.getExtendedProperties(Festivities.PLAYERDATA);
		if (data.santaCooldown > 0 && !UserType.getUserType(player.username).isCreator())
		{
			icommandsender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("chat.type.announcement", new Object[] { Festivities.CHATNAME, "Please wait before sending another Item" }));
			return false;
		}
		ItemStack stack = player.inventory.getCurrentItem();
		if (stack != null)
		{
			player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
			ItemStack rec = SantaClient.getClient().sendAndReceiveItemFrom(stack, Festivities.getSantaUrl(), username);
			if (rec != null)
			{
				player.dropPlayerItem(rec);
				data.santaCooldown = Festivities.santacooldowntime;
			}
			else
			{
				player.dropPlayerItem(stack);
			}
		}
		return true;
	}
}
