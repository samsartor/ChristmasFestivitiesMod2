package eekysam.festivities.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.ITipItem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemFestiveBlock extends ItemBlock
{
	public ItemFestiveBlock(int par1)
	{
		super(par1);
	}

	@SideOnly(Side.CLIENT)
	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced)
	{
		Block b = Block.blocksList[this.getBlockID()];
		if (b instanceof ITipItem)
		{
			ITipItem tipItem = (ITipItem) b;
			info.add(tipItem.getTip(player, stack));
		}
	}
}
