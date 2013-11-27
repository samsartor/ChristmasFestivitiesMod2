package eekysam.festivities.tile;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.network.PacketDispatcher;
import eekysam.festivities.network.packet.FestPacket;
import eekysam.festivities.network.packet.PacketUpdateTile;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntitySnowglobe extends TileEntity
{
	public int type = -1;
	
	private long ticks;
	
	protected List<EntityPlayer> watchers = new ArrayList<EntityPlayer>();
	
	public void onChange()
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		PacketUpdateTile pk = new PacketUpdateTile(this.xCoord, this.yCoord, this.zCoord, compound);
		PacketDispatcher.sendPacketToServer(FestPacket.buildPacket(pk));
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setShort("scene", (short) this.type);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.type = tag.getShort("scene");
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
	
    public void updateEntity() 
    {
    	this.ticks++;
    	
    	if (ticks % 20 == 0)
    	{
    		this.watchers.clear();
    		
    		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(this.xCoord - 16, this.yCoord - 16, this.zCoord - 16, this.xCoord + 16, this.yCoord + 16, this.zCoord + 16);
    		List entities = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, box);
    		
            for (int i = 0; i < entities.size(); ++i)
            {
            	EntityPlayer ent = (EntityPlayer) entities.get(i);
            	
            	if (this.isLooking(ent))
            	{
            		this.watchers.add(ent);
            	}
            }
    	}
    }
    
    protected boolean isLooking(EntityPlayer player)
    {
    	return true;
    }
}
