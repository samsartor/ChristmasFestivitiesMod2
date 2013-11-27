package eekysam.festivities.tile;

import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import eekysam.festivities.client.particle.EntitySnowFX;
import eekysam.festivities.network.packet.FestPacket;
import eekysam.festivities.network.packet.PacketUpdateTile;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntitySnowMachine extends TileEntity {

	private int iceCount;
	private long tickCount;
	private float snowDensity;
	private static final int iceConsumption = 1000;
	private static final int snowDistance = 16;
	private static final float jetspeed = 0.01F;
	private static final float jetbend = 0.95F;
	private static final float jetangle = 0.2F;
	private static final float jetvel = 0.1F;
	
	private float jetx;
	private float jetz;
	
	public TileEntitySnowMachine() {
		// TODO Auto-generated constructor stub
	}

	public void readFromNBT(NBTTagCompound myTag)
	{
		super.readFromNBT(myTag);
		iceCount = myTag.getInteger("iceCount");
	}
	
	public void writeToNBT(NBTTagCompound myTag)
    {
		super.writeToNBT(myTag);
		myTag.setInteger("iceCount", iceCount);
    }
	
	public void onClick(EntityPlayer player, World world)
	{
		if (!world.isRemote)
		{
			ItemStack myStack = player.inventory.getCurrentItem();
			if(myStack != null && myStack.itemID == Block.ice.blockID)
			{
				tickCount = 0;
				iceCount += myStack.stackSize;
				if (!player.capabilities.isCreativeMode)
	            {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
	            }
			}
			this.onChange();
		}
	}
	
	public void updateEntity() 
	{
		World myWorld = this.worldObj;
		
		this.jetx *= jetbend;
		this.jetz *= jetbend;
		
		if (this.jetx < 1.0F)
		{
			this.jetx += myWorld.rand.nextFloat() * jetspeed;
		}
		if (this.jetx > 1.0F)
		{
			this.jetx -= myWorld.rand.nextFloat() * jetspeed;
		}
		if (this.jetz < 1.0F)
		{
			this.jetz += myWorld.rand.nextFloat() * jetspeed;
		}
		if (this.jetz > 1.0F)
		{
			this.jetz -= myWorld.rand.nextFloat() * jetspeed;
		}
		
		snowDensity = 0;
		if (myWorld.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
        {
			if (iceCount > 0 && this.worldObj.isAirBlock(xCoord, yCoord + 1, zCoord))
			{
				snowDensity = (float)++tickCount / (4 * iceConsumption);
				if (snowDensity > 1)
					snowDensity = 1;
				if (myWorld.rand.nextFloat() < 0.05)
				{
					letItSnow();
				}
				if(tickCount % iceConsumption == 0)
				{
					iceCount--;
				}
			}
        }
	}
	
	public float getSnowDensity()
	{
		return snowDensity;
	}
	
	private void letItSnow()
	{
		int x;
		int y;
		int z;
		Random randy = this.worldObj.rand;
		for (int i = 0; i < 20; i++)
		{
			x = (int)((randy.nextFloat() + randy.nextFloat() - 1) * snowDistance) + this.xCoord;
			z = (int)((randy.nextFloat() + randy.nextFloat() - 1) * snowDistance) + this.zCoord;
			y = getFirstUncoveredBlockHeight(x, z);
			int id = this.worldObj.getBlockId(x, y, z);
			if (id == Block.snow.blockID)
			{
				int meta = this.worldObj.getBlockMetadata(x, y, z);
				if (meta < 7)
				{
					this.worldObj.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
					break;
				}
			}
			else
			{
				y++;
				if (Block.snow.canPlaceBlockAt(this.worldObj, x, y, z))
				{
					this.worldObj.setBlock(x, y, z, Block.snow.blockID);
					break;
				}
			}
		}
	}
	
	public void onChange()
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		PacketUpdateTile pk = new PacketUpdateTile(this.xCoord, this.yCoord, this.zCoord, compound);
		PacketDispatcher.sendPacketToServer(FestPacket.buildPacket(pk));
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
	
	public int getFirstUncoveredBlockHeight(int x, int z)
	{
		int y;
		for (y = 128; this.worldObj.isAirBlock(x, y + 1, z); --y);
		return y + 1;
	}
	
	public void spawnFX(Random rand)
	{
		int runs = (int) (8 * this.getSnowDensity() + 0.99F);
		for (int i = 0; i < runs; i++)
		{
    		double X = this.xCoord + rand.nextFloat() * (8 / 16.0F) + (4 / 16.0F);
    		double Z = this.zCoord + rand.nextFloat() * (8 / 16.0F) + (4 / 16.0F);
    		double Y = this.yCoord + 0.6F + rand.nextFloat() * 0.4F;
    		float xvel = this.jetx * jetangle;
    		float zvel = this.jetz * jetangle;
    		float yvel = jetvel * (rand.nextFloat() * 0.2F + 0.9F);
    		EntitySnowFX.spawn(new EntitySnowFX(this.worldObj, X, Y, Z, xvel, yvel, zvel).setSize(0.01F).setMult(0.995F));
		}
	}
}
