package eekysam.festivities.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import eekysam.festivities.network.packet.FestPacket;
import eekysam.festivities.network.packet.PacketUpdateTile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPlate extends TileEntity
{
	public int[] contents = new int[PlateFoods.values().length];
	public List<PlateDrawFoods> onPlate = new ArrayList<PlateDrawFoods>();
	public static int maxCookie = 20;
	
	public static enum PlateFoods
	{
		Figgy(false),
		BluPie(false),
		PmkPie(false),
		Cookie(true),
		ChipCookie(true),
		SugarCookie(true),
		ChocCookie(true),
		SprinkCookie(true),
		CandyCookie(true);
		
		public boolean isCookie;
		
		PlateFoods(boolean cookie)
		{
			this.isCookie = cookie;
		}
	}
	
	public static enum PlateDrawFoods
	{
		Figgy(false),
		BluPie(false),
		PmkPie(false),
		ChipCookie(true),
		SugarCookie(true),
		ChocCookie(true),
		SprinkCookie(true),
		CandyCookie(true);
		
		public boolean isCookie;
		
		PlateDrawFoods(boolean cookie)
		{
			this.isCookie = cookie;
		}
	}
	
	public boolean addItem(PlateFoods food, Random rand)
	{
		if (this.canAdd(food))
		{
			this.contents[food.ordinal()]++;
			if (food.equals(PlateFoods.Cookie))
			{
				int r = rand.nextInt(5);
				int j = 0;
				for (int i = 0; i < PlateDrawFoods.values().length; i++)
				{
					PlateDrawFoods f = PlateDrawFoods.values()[i];
					if (f.isCookie)
					{
						if (j == r)
						{
							this.onPlate.add(f);
							break;
						}
						j++;
					}
				}
			}
			else
			{
				this.onPlate.add(PlateDrawFoods.valueOf(food.name()));
			}
			return true;
		}
		return false;
	}
	
	public boolean canAdd(PlateFoods food)
	{
		boolean flag = true;
		if (this.contents[PlateFoods.Figgy.ordinal()] >= 2)
		{
			return false;
		}
		if (this.contents[PlateFoods.BluPie.ordinal()] >= 1)
		{
			return false;
		}
		if (this.contents[PlateFoods.PmkPie.ordinal()] >= 1)
		{
			return false;
		}
		if (this.getTotalCookies() >= this.maxCookie)
		{
			return false;
		}
		if (food.isCookie)
		{
			if (this.getTotalNotCookies() > 0)
			{
				return false;
			}
		}
		return true;
	}
	
	public void clear()
	{
		this.contents = new int[PlateFoods.values().length];
		this.onPlate.clear();
	}
	
	public int getTotalCookies()
	{
		int sum = 0;
		for (int i = 0; i < this.contents.length; i++)
		{
			int n = this.contents[i];
			PlateFoods f = PlateFoods.values()[i];
			if (f.isCookie)
			{
				sum += n;
			}
		}
		return sum;
	}
	
	public int getTotal()
	{
		int sum = 0;
		for (int i = 0; i < this.contents.length; i++)
		{
			int n = this.contents[i];
			sum += n;
		}
		return sum;
	}
	
	public int getTotalNotCookies()
	{
		int sum = 0;
		for (int i = 0; i < this.contents.length; i++)
		{
			int n = this.contents[i];
			PlateFoods f = PlateFoods.values()[i];
			if (!f.isCookie)
			{
				sum += n;
			}
		}
		return sum;
	}
	
	public NBTTagCompound writeContents()
	{
		NBTTagCompound tag = new NBTTagCompound();
		for (int i = 0; i < this.contents.length; i++)
		{
			tag.setShort(PlateFoods.values()[i].name(), (short)this.contents[i]);
		}
		return tag;
	}
	
	public void readContents(NBTTagCompound tag)
	{
		Random rand = new Random();
		for (int i = 0; i < this.contents.length; i++)
		{
			PlateFoods f = PlateFoods.values()[i];
			int num = tag.getShort(f.name());
			for (int j = 0; j < num; j++)
			{
				if (!this.addItem(f, rand))
				{
					this.contents[i]++;
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
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		NBTTagCompound cont = this.writeContents();
		tag.setCompoundTag("contents", cont);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTTagCompound cont = tag.getCompoundTag("contents");
		this.readContents(cont);
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
