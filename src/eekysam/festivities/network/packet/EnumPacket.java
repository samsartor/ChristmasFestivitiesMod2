package eekysam.festivities.network.packet;

public enum EnumPacket
{
	UPDATETILE(PacketUpdateTile.class);

	public Class packet;

	EnumPacket(Class pak)
	{
		this.packet = pak;
	}
}
