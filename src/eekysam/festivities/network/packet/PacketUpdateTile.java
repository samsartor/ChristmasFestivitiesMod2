package eekysam.festivities.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.Player;

public class PacketUpdateTile extends FestPacket
{
	public int x;
	public int y;
	public int z;
	public NBTTagCompound tag;

	public PacketUpdateTile(EnumPacket type)
	{
		super(type);
	}

	public PacketUpdateTile(int x, int y, int z, NBTTagCompound tag)
	{
		super(EnumPacket.UPDATETILE);
		this.x = x;
		this.y = y;
		this.z = z;
		this.tag = tag;
	}

	@Override
	public void write(DataOutputStream outputStream) throws IOException
	{
		outputStream.writeInt(this.x);
		outputStream.writeInt(this.y);
		outputStream.writeInt(this.z);
		NBTTagCompound.writeNamedTag(this.tag, outputStream);
	}

	@Override
	public void read(DataInputStream inputStream) throws IOException
	{
		this.x = inputStream.readInt();
		this.y = inputStream.readInt();
		this.z = inputStream.readInt();
		this.tag = (NBTTagCompound) NBTTagCompound.readNamedTag(inputStream);
	}

	@Override
	public void run()
	{

	}

	@Override
	public void run(Player player)
	{
		EntityPlayerMP playerMP = (EntityPlayerMP) player;

		TileEntity te = playerMP.worldObj.getBlockTileEntity(this.x, this.y, this.z);
		if (te != null)
		{
			te.readFromNBT(this.tag);
			playerMP.worldObj.markBlockForUpdate(this.x, this.y, this.z);
		}
		else
		{
			
		}
	}
}
