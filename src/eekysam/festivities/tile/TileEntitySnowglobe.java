package eekysam.festivities.tile;

import cpw.mods.fml.common.network.PacketDispatcher;
import eekysam.festivities.network.packet.FestPacket;
import eekysam.festivities.network.packet.PacketUpdateTile;
import net.minecraft.block.BlockContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntitySnowglobe extends TileEntity
{
	public int type;
	
	public void onChange()
	{
		System.out.println("onChange");
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		PacketUpdateTile pk = new PacketUpdateTile(this.xCoord, this.yCoord, this.zCoord, compound);
		PacketDispatcher.sendPacketToServer(FestPacket.buildPacket(pk));
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		System.out.println("writeToNBT");
		super.writeToNBT(tag);
		tag.setShort("scene", (short) this.type);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		System.out.println("readFromNBT");
		super.readFromNBT(tag);
		this.type = tag.getShort("scene");
	}

	@Override
	public Packet getDescriptionPacket()
	{
		System.out.println("getDescriptionPacket");
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, compound);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		System.out.println("onDataPacket");
		super.onDataPacket(net, pkt);
		this.readFromNBT(pkt.data);
	}
}
