package eekysam.festivities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public final class FestivitiesTab extends CreativeTabs
{
	int iconId;
	String name;
	
	public FestivitiesTab(int par1, String name)
	{
		super(par1, name);
		this.name = name;
	}
	
	public void setIcon(Item icon)
	{
		this.iconId = icon.itemID;
	}
	
	public void setIcon(Block icon)
	{
		this.iconId = icon.blockID;
	}

	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex()
	{
		return this.iconId;
	}

	public String getTranslatedTabLabel()
	{
		return this.name;
	}
}