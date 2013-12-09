package eekysam.festivities.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.Festivities;

public class ItemMoreCookies extends ItemFoodFestive
{
	public static final String[] names = new String[] { "sugar", "choc", "sprink", "candy" };

	@SideOnly(Side.CLIENT)
	private Icon[] cookieIcons;

	public ItemMoreCookies(int par1, int par2, float par3)
	{
		super(par1, par2, par3, false);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Gets an icon index based on an item's damage value
	 */
	public Icon getIconFromDamage(int par1)
	{
		int j = MathHelper.clamp_int(par1, 0, 15);
		return this.cookieIcons[j];
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have different names based on their
	 * damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
		return super.getUnlocalizedName() + "." + names[i];
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int j = 0; j < 4; ++j)
		{
			par3List.add(new ItemStack(par1, 1, j));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.cookieIcons = new Icon[names.length];

		for (int i = 0; i < names.length; ++i)
		{
			this.cookieIcons[i] = par1IconRegister.registerIcon(Festivities.ID + ":cookie" + "_" + names[i]);
		}
	}
}
