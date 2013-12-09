package eekysam.festivities.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.Festivities;
import eekysam.festivities.ITipItem;

public class ItemFestive extends Item implements ITipItem
{
	private String[] tip = null;
	private String[] shifttip = null;

	public ItemFestive(int par1)
	{
		super(par1);
	}

	public ItemFestive setTip(String... tip)
	{
		this.tip = tip;
		return this;
	}

	public ItemFestive setShiftTip(String... tip)
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
