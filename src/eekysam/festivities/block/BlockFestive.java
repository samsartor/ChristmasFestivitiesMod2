package eekysam.festivities.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import eekysam.festivities.ITipItem;

public class BlockFestive extends Block implements ITipItem
{
	private String[] tip = null;
	private String[] shifttip = null;

	public BlockFestive(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}

	public BlockFestive setTip(String... tip)
	{
		this.tip = tip;
		return this;
	}

	public BlockFestive setShiftTip(String... tip)
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
}
