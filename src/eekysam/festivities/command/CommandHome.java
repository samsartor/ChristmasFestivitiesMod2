package eekysam.festivities.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Teleporter;
import eekysam.festivities.Festivities;
import eekysam.festivities.kringle.KringleTeleporter;

public class CommandHome
{
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "/" + this.getCommandName();
	}

	public String getCommandName()
	{
		return "gohome";
	}

	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		if (astring.length > 0)
		{
			try
			{
				String username = astring[0];
				if (!this.runCommand(icommandsender, username))
				{
					throw new Exception("Could not find player with name: " + username);
				}
			}
			catch (final Exception var6)
			{
				throw new CommandException(var6.getMessage(), new Object[0]);
			}
		}
		else
		{
			try
			{
				String username = icommandsender.getCommandSenderName();
				if (!this.runCommand(icommandsender, username))
				{
					throw new Exception("Could not find player with name: " + username);
				}
			}
			catch (final Exception var6)
			{
				throw new CommandException(var6.getMessage(), new Object[0]);
			}
		}
	}

	/*
	 * HashMap<String, Integer> map =
	 * WorldUtil.getArrayOfPossibleDimensions(WorldUtil
	 * .getPossibleDimensionsForSpaceshipTier(Integer.MAX_VALUE), playerBase);
	 * 
	 * String temp = ""; int count = 0;
	 * 
	 * for (Entry<String, Integer> entry : map.entrySet()) { temp =
	 * temp.concat(entry.getKey() + (count < map.entrySet().size() - 1 ? "." :
	 * "")); count++; }
	 * 
	 * playerBase.playerNetServerHandler.sendPacketToPlayer(PacketUtil.createPacket
	 * (GalacticraftCore.CHANNEL, EnumPacketClient.UPDATE_DIMENSION_LIST, new
	 * Object[] { playerBase.username, temp }));
	 * playerBase.setSpaceshipTier(Integer.MAX_VALUE);
	 * playerBase.setUsingPlanetGui(); playerBase.mountEntity(null);
	 * 
	 * CommandBase.notifyAdmins(icommandsender, "commands.dimensionteleport",
	 * new Object[] { String.valueOf(EnumColor.GREY + "[" +
	 * playerBase.getEntityName()), "]" });
	 */

	protected boolean runCommand(ICommandSender icommandsender, String username)
	{
		EntityPlayerMP player = (EntityPlayerMP) icommandsender.getEntityWorld().getPlayerEntityByName(username);
		if (player == null)
		{
			return false;
		}
		MinecraftServer mServer = MinecraftServer.getServer();
		player.mcServer.getConfigurationManager().transferPlayerToDimension(player, 0, new Teleporter(mServer.worldServerForDimension(0)));
		return true;
	}
}
