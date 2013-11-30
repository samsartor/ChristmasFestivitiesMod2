package eekysam.festivities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ITipItem
{
	public String[] getTip(EntityPlayer player, ItemStack stack);
	
	public String[] getShiftTip(EntityPlayer player, ItemStack stack);
}
