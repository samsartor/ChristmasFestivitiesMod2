package eekysam.festivities.tile;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.network.PacketDispatcher;
import eekysam.festivities.Festivities;
import eekysam.festivities.kringle.KringleTeleporter;
import eekysam.festivities.network.packet.FestPacket;
import eekysam.festivities.network.packet.PacketUpdateTile;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntitySnowglobe extends TileEntity
{
	public int type = -1;
	
	private long ticks;
	
	protected int lookTicks = 0;
	protected String looker = "";
	protected boolean hasLooker = false;
	
	public static final int portalTime = 120;
	
	//protected List<EntityPlayer> watchers = new ArrayList<EntityPlayer>();
	
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
    	
    	if (this.hasLooker)
    	{
    		this.lookTicks++;
			if (this.worldObj.isRemote)
			{
				Festivities.instance.playerFovAnimation.put(this.looker, this.lookTicks);
			}
    	}
    	else
    	{
			if (this.worldObj.isRemote && !this.looker.isEmpty())
			{
				Festivities.instance.playerFovAnimation.remove(this.looker);
				this.looker = "";
			}
    	}
    	
    	if (ticks % 20 == 0)
    	{
    		boolean flag = false;
    		
    		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(this.xCoord - 16, this.yCoord - 16, this.zCoord - 16, this.xCoord + 16, this.yCoord + 16, this.zCoord + 16);
    		List entities = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, box);
    		
            for (int i = 0; i < entities.size(); ++i)
            {
            	EntityPlayer player = (EntityPlayer) entities.get(i);
            	
            	EntityPlayerMP playermp = null;
            	EntityPlayerSP playersp = null;
            	
            	if (this.worldObj.isRemote)
            	{
            		playersp = (EntityPlayerSP) player;
            	}
            	else
            	{
            		playermp = (EntityPlayerMP) player;
            	}
        	
        		
            	if (this.isLooking(player) && this.canSee(player))
            	{
            		if (this.hasLooker)
            		{
            			if (this.looker.equals(player.getEntityName()))
            			{
            				flag = true;
            				if (playermp  != null)
            				{
	            				if (this.lookTicks > this.portalTime)
	            				{
	            					MinecraftServer mServer = MinecraftServer.getServer();
	            					playermp.mcServer.getConfigurationManager().transferPlayerToDimension(playermp, Festivities.kringleId, new KringleTeleporter(mServer.worldServerForDimension(Festivities.kringleId)));
	            					
	            					flag = false;
	            				}
            				}

            				break;
            			}
            		}
            		else
            		{
            			this.looker = player.getEntityName();
            			this.lookTicks = 0;
            			this.hasLooker = true;
            			flag = true;
            			break;
            		}
            	}
            }
            
            if (!flag)
            {
            	this.hasLooker = false;
            	this.lookTicks = 0;
            }
    	}
    }
    
    protected boolean isLooking(EntityPlayer player)
    {
        Vec3 look = player.getLook(1.0F).normalize();
        Vec3 pos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.xCoord - player.posX + 0.D, this.yCoord - (player.posY + (double)player.getEyeHeight()) + 0.5D, this.zCoord - player.posZ + 0.5D);
        double dist = pos.lengthVector();
        pos = pos.normalize();
        double stare = look.dotProduct(pos);
        
    	return stare > 1.0D - 0.025D / dist;
    }
    
    protected boolean canSee(EntityPlayer player)
    {
    	Vec3 look = player.getLook(1.0F).normalize();
    	return this.worldObj.clip(this.worldObj.getWorldVec3Pool().getVecFromPool(this.xCoord + 0.5F + look.xCoord * -2, this.yCoord + 0.5F + look.yCoord * -2, this.zCoord + 0.5D + look.zCoord * -2), this.worldObj.getWorldVec3Pool().getVecFromPool(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ)) == null;
    }
    
    @Deprecated
    public boolean rayTraceTo(float dist, EntityLivingBase entity)
    {
    	return false;//entity.rayTrace(dist, 1.0F);
    }
}
