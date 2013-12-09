package eekysam.festivities.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.Festivities;
import eekysam.festivities.ITipItem;

public class ItemFoodFestive extends ItemFood implements ITipItem
{
	public ItemFoodFestive(int par1, int par2, boolean par3)
	{
		super(par1, par2, par3);
	}

	public ItemFoodFestive(int par1, int par2, float par3, boolean par4)
	{
		super(par1, par2, par3, par4);
	}

	private String[] tip = null;
	private String[] shifttip = null;

	public ItemFoodFestive setTip(String... tip)
	{
		this.tip = tip;
		return this;
	}

	public ItemFoodFestive setShiftTip(String... tip)
	{
		this.shifttip = tip;
		return this;
	}

	@Override
	public String[] getTip(EntityPlayer player, ItemStack stack)
	{
		return this.tip;
	}

	@Override
	public String[] getShiftTip(EntityPlayer player, ItemStack stack)
	{
		return this.shifttip;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced)
	{
		Festivities.addInformation(this, stack, player, info, advanced);
	}
}
