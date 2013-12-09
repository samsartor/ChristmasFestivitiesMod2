package eekysam.festivities.tile;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import eekysam.festivities.Festivities;
import eekysam.festivities.client.player.PlayerClientData;
import eekysam.festivities.kringle.KringleTeleporter;
import eekysam.festivities.player.PlayerData;

public class TileEntitySnowglobe extends TileEntityFestive
{
	public int type = -1;

	private long ticks;

	protected boolean isPortal = true;

	public static final int portalTime = 120;
	public static final int lookTick = 10;

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
	public void updateEntity()
	{
		this.ticks++;

		if (this.isPortal)
		{
			if (this.ticks % this.lookTick == 0)
			{
				boolean flag = false;

				AxisAlignedBB box = AxisAlignedBB.getBoundingBox(this.xCoord - 16, this.yCoord - 16, this.zCoord - 16, this.xCoord + 16, this.yCoord + 16, this.zCoord + 16);
				List entities = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, box);

				for (int i = 0; i < entities.size(); ++i)
				{
					EntityPlayer player = (EntityPlayer) entities.get(i);

					if (this.isLooking(player) && this.canSee(player))
					{
						long worldtime = this.worldObj.getWorldTime();
						if (this.worldObj.isRemote)
						{
							PlayerClientData data = (PlayerClientData) player.getExtendedProperties(Festivities.PLAYERDATA);
							data.testTimeOut(worldtime);

							int portaltime = data.incrementSnowglobe(worldtime);

							if (portaltime > this.portalTime)
							{
								data.resetSnowglobePortal();
							}
						}
						else
						{
							PlayerData data = (PlayerData) player.getExtendedProperties(Festivities.PLAYERDATA);
							data.testTimeOut(worldtime);

							int portaltime = data.incrementSnowglobe(worldtime);

							if (portaltime > this.portalTime)
							{
								data.resetSnowglobePortal();
								MinecraftServer mServer = MinecraftServer.getServer();
								EntityPlayerMP playermp = (EntityPlayerMP) player;
								if (playermp.dimension == Festivities.kringleId)
								{
									playermp.mcServer.getConfigurationManager().transferPlayerToDimension(playermp, 0, new KringleTeleporter(mServer.worldServerForDimension(0)));
								}
								else
								{
									playermp.mcServer.getConfigurationManager().transferPlayerToDimension(playermp, Festivities.kringleId, new KringleTeleporter(mServer.worldServerForDimension(Festivities.kringleId)));
								}
							}
						}
					}
				}
			}
		}
	}

	protected boolean isLooking(EntityPlayer player)
	{
		Vec3 look = player.getLook(1.0F).normalize();
		Vec3 pos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.xCoord - player.posX + 0.5D, this.yCoord - (player.posY + (double) player.getEyeHeight()) + 0.5D, this.zCoord - player.posZ + 0.5D);
		double dist = pos.lengthVector();
		pos = pos.normalize();
		double stare = look.dotProduct(pos);

		return stare > 1.0D - 0.025D / dist;
	}

	protected boolean canSee(EntityPlayer player)
	{
		Vec3 look = player.getLook(1.0F).normalize();
		return this.worldObj.clip(this.worldObj.getWorldVec3Pool().getVecFromPool(this.xCoord + 0.5D + look.xCoord * -2, this.yCoord + 0.5D + look.yCoord * -2, this.zCoord + 0.5D + look.zCoord * -2), this.worldObj.getWorldVec3Pool().getVecFromPool(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ)) == null;
	}

	@Deprecated
	public boolean rayTraceTo(float dist, EntityLivingBase entity)
	{
		return false;// entity.rayTrace(dist, 1.0F);
	}
}
