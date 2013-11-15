package eekysam.festivities.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import eekysam.festivities.Festivities;
import eekysam.festivities.network.packet.FestPacket;
import eekysam.festivities.network.packet.PacketUpdateTile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

	public PlateFoods getFood(ItemStack item)
	{
		int d = item.getItemDamage();
		if (item.itemID == Item.cookie.itemID)
		{
			return PlateFoods.ChipCookie;
		}
		if (item.itemID == Festivities.moreCookies.itemID)
		{
			switch (d)
			{
				case 0:
					return PlateFoods.SugarCookie;
				case 1:
					return PlateFoods.ChocCookie;
				case 2:
					return PlateFoods.SprinkCookie;
				case 3:
					return PlateFoods.CandyCookie;
			}
		}
		if (item.itemID == Item.pumpkinPie.itemID)
		{
			return PlateFoods.PmkPie;
		}
		if (item.itemID == Festivities.bluePie.itemID)
		{
			return PlateFoods.BluPie;
		}
		if (item.itemID == Festivities.figgy.itemID)
		{
			return PlateFoods.Figgy;
		}
		return null;
	}
	
	public ItemStack dropOneItem()
	{
		return null;
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
			flag &= false;
		}
		if (this.contents[PlateFoods.BluPie.ordinal()] >= 1)
		{
			flag &= false;
		}
		if (this.contents[PlateFoods.PmkPie.ordinal()] >= 1)
		{
			flag &= false;
		}
		if (this.getTotalCookies() >= this.maxCookie)
		{
			flag &= false;
		}
		if (food.isCookie)
		{
			if (this.getTotalNotCookies() > 0)
			{
				flag &= false;
			}
		}
		else
		{
			if (this.getTotalCookies() > 0)
			{
				flag &= false;
			}
		}
		return flag;
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
			tag.setShort(PlateFoods.values()[i].name(), (short) this.contents[i]);
		}
		return tag;
	}

	public void readContents(NBTTagCompound tag)
	{
		Random rand = new Random();
		this.clear();
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
