package eekysam.festivities.tile;

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

	public TileEntitySnowglobe()
	{
		
	}
	
	public TileEntitySnowglobe(World world, BlockContainer block)
	{
		this.blockType = block;
		this.worldObj = world;
		this.type = this.worldObj.rand.nextInt(SnowglobeScene.list.size());
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		System.out.println("Saved");
		super.writeToNBT(tag);
		tag.setShort("scene", (short) this.type);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		System.out.println("Read");
		super.readFromNBT(tag);
		this.type = tag.getShort("scene");
	}

	public Packet getDescriptionPacket()
	{
		System.out.println("Desc");
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, compound);
	}
	
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
    	super.onDataPacket(net, pkt);
    	this.readFromNBT(pkt.data);
    }
}
