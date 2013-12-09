package eekysam.festivities.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import eekysam.festivities.Festivities;

public class TileEntityPlate extends TileEntityFestive
{
	public List<PlateFoods> onPlate = new ArrayList<PlateFoods>();
	public static int maxCookie = 20;

	public static enum PlateFoods
	{
		Figgy(Festivities.figgy, 0, false),
		BluPie(Festivities.bluePie, 0, false),
		PmkPie(Item.pumpkinPie, 0, false),
		ChipCookie(Item.cookie, 0, true),
		SugarCookie(Festivities.moreCookies, 0, true),
		ChocCookie(Festivities.moreCookies, 1, true),
		SprinkCookie(Festivities.moreCookies, 2, true),
		CandyCookie(Festivities.moreCookies, 3, true);

		public Item item;
		public int meta;
		public boolean isCookie;

		PlateFoods(Item item, int meta, boolean isCookie)
		{
			this.isCookie = isCookie;
			this.item = item;
			this.meta = meta;
		}

		public ItemStack getItem()
		{
			return new ItemStack(this.item, 1, this.meta);
		}

		public static PlateFoods getFood(ItemStack item)
		{
			return getFood(item.itemID, item.getItemDamage());
		}

		public static PlateFoods getFood(int id, int meta)
		{
			for (int i = 0; i < PlateFoods.values().length; i++)
			{
				PlateFoods f = PlateFoods.values()[i];
				if (f.item.itemID == id && f.meta == meta)
				{
					return f;
				}
			}
			return null;
		}
	}

	public PlateFoods getFood(ItemStack item)
	{
		return PlateFoods.getFood(item);
	}

	public ItemStack doDropOneItem()
	{
		int j = this.onPlate.size() - 1;
		if (j >= 0)
		{
			PlateFoods food = this.onPlate.get(j);
			this.onPlate.remove(j);
			return food.getItem();
		}
		return null;
	}

	public ItemStack dropOneItem()
	{
		ItemStack i = this.doDropOneItem();
		this.onChange();
		return i;
	}

	public ItemStack[] onClear()
	{
		ItemStack[] drop = new ItemStack[this.onPlate.size()];
		int i = 0;
		while (!this.onPlate.isEmpty())
		{
			i++;
			ItemStack item = this.doDropOneItem();
			if (i < drop.length)
			{
				drop[i] = item;
			}
		}
		this.onChange();
		return drop;
	}

	public boolean addItem(PlateFoods food)
	{
		if (this.canAdd(food))
		{
			this.onPlate.add(food);
			return true;
		}
		return false;
	}

	public boolean canAdd(PlateFoods food)
	{
		if (this.getTotalofType(PlateFoods.Figgy) >= 2)
		{
			return false;
		}
		if (this.getTotalofType(PlateFoods.BluPie) >= 1)
		{
			return false;
		}
		if (this.getTotalofType(PlateFoods.PmkPie) >= 1)
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
		else
		{
			if (this.getTotal() - this.getTotalofType(food) > 0)
			{
				return false;
			}
		}
		return true;
	}

	public void clear()
	{
		this.onPlate.clear();
	}

	public int getTotalofType(PlateFoods food)
	{
		int sum = 0;
		for (int i = 0; i < this.onPlate.size(); i++)
		{
			PlateFoods item = this.onPlate.get(i);
			if (item.equals(food))
			{
				sum++;
			}
		}
		return sum;
	}

	public int getTotalofTypes(PlateFoods... foods)
	{
		int sum = 0;
		for (int i = 0; i < this.onPlate.size(); i++)
		{
			PlateFoods item = this.onPlate.get(i);
			for (int j = 0; j < foods.length; j++)
			{
				if (item.equals(foods[j]))
				{
					sum++;
					break;
				}
			}
		}
		return sum;
	}

	public int getTotalCookies()
	{
		int sum = 0;
		for (int i = 0; i < this.onPlate.size(); i++)
		{
			PlateFoods item = this.onPlate.get(i);
			if (item.isCookie)
			{
				sum++;
			}
		}
		return sum;
	}

	public int getTotal()
	{
		return this.onPlate.size();
	}

	public int getTotalNotCookies()
	{
		int sum = 0;
		for (int i = 0; i < this.onPlate.size(); i++)
		{
			PlateFoods item = this.onPlate.get(i);
			if (!item.isCookie)
			{
				sum++;
			}
		}
		return sum;
	}

	public int[] writeContents()
	{
		int[] tag = new int[this.onPlate.size()];
		for (int i = 0; i < this.onPlate.size(); i++)
		{
			tag[i] = this.onPlate.get(i).ordinal();
		}
		return tag;
	}

	public void readContents(int[] tag)
	{
		this.clear();
		for (int i = 0; i < tag.length; i++)
		{
			this.addItem(PlateFoods.values()[tag[i]]);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		int[] cont = this.writeContents();
		tag.setIntArray("contents", cont);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		int[] cont = tag.getIntArray("contents");
		this.readContents(cont);
	}
}
