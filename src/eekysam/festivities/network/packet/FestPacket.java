package eekysam.festivities.network.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.network.Player;
import eekysam.festivities.Festivities;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

public abstract class FestPacket
{
	public EnumPacket type;
	
	public FestPacket(EnumPacket type)
	{
		this.type = type;
	}

	public static Packet250CustomPayload buildPacket(FestPacket fest)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(fest.type.ordinal());
			fest.write(outputStream);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Festivities.CHANNEL;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		return packet;
	}
	
	public static FestPacket buildFest(Packet250CustomPayload packet)
	{
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		FestPacket fest = null;
		
		try
		{
			EnumPacket type = EnumPacket.values()[inputStream.readInt()];
			fest = (FestPacket)type.packet.getConstructor(new Class[] {EnumPacket.class}).newInstance(new Object[] {type});
			fest.read(inputStream);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return fest;
	}
	
	public abstract void write(DataOutputStream outputStream) throws IOException;
	
	public abstract void read(DataInputStream inputStream) throws IOException;
	
	public abstract void run();
	
	public abstract void run(Player player);
}
