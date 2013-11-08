package eekysam.festivities.network;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import eekysam.festivities.Festivities;
import eekysam.festivities.network.packet.FestPacket;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class PacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		if (packet.channel == Festivities.CHANNEL)
		{
			FestPacket fest = FestPacket.buildFest(packet);
			fest.run(player);
		}
	}
}
