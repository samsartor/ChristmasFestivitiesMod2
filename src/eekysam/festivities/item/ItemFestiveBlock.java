package eekysam.festivities.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.Festivities;
import eekysam.festivities.ITipItem;

public class ItemFestiveBlock extends ItemBlock
{
	public ItemFestiveBlock(int par1)
	{
		super(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced)
	{
		Block block = Block.blocksList[this.getBlockID()];
		if (block instanceof ITipItem)
		{
			Festivities.addInformation((ITipItem) block, stack, player, info, advanced);
		}
	}
}
