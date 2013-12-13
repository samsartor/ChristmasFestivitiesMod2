package eekysam.festivities.item;

import eekysam.festivities.Festivities;
import eekysam.festivities.block.BlockMintPlant;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMintPlant extends ItemFestive
{
	public ItemMintPlant(int par1)
	{
		super(par1);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		int atId = world.getBlockId(x, y, z);

		if (atId == Block.snow.blockID && (world.getBlockMetadata(x, y, z) & 7) < 1)
		{
			side = 1;
		}
		else if (atId != Block.vine.blockID && atId != Block.tallGrass.blockID && atId != Block.deadBush.blockID)
		{
			if (side == 0)
			{
				--y;
			}

			if (side == 1)
			{
				++y;
			}

			if (side == 2)
			{
				--z;
			}

			if (side == 3)
			{
				++z;
			}

			if (side == 4)
			{
				--x;
			}

			if (side == 5)
			{
				++x;
			}
		}

		if (!player.canPlayerEdit(x, y, z, side, stack))
		{
			return false;
		}
		else if (stack.stackSize == 0)
		{
			return false;
		}
		else
		{
			BlockMintPlant block = (BlockMintPlant) Festivities.mintPlant;
			if (block.canPlaceBlockAt(world, x, y, z))
			{
				world.setBlock(x, y, z, block.blockID, 0, 3);
				world.setBlock(x, y + 1, z, block.blockID, 1, 3);
				
				--stack.stackSize;
				
				return true;
			}
		}
		return false;
	}
}
