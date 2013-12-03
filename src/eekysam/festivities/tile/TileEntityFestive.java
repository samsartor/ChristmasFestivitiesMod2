package eekysam.festivities.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.PacketDispatcher;
import eekysam.festivities.network.packet.FestPacket;
import eekysam.festivities.network.packet.PacketUpdateTile;

public class TileEntityFestive extends TileEntity
{
	public void onChange()
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		PacketUpdateTile pk = new PacketUpdateTile(this.xCoord, this.yCoord, this.zCoord, compound);
		if (this.worldObj.isRemote)
		{
			PacketDispatcher.sendPacketToServer(FestPacket.buildPacket(pk));
		}
		else
		{
			//PacketDispatcher.sendPacketToAllPlayers(FestPacket.buildPacket(pk));
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, compound);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		super.onDataPacket(net, pkt);
		this.readFromNBT(pkt.data);
	}
}
