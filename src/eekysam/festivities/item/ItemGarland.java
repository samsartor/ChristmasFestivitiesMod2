package eekysam.festivities.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.Festivities;

public class ItemGarland extends ItemFestive
{
	private int spawnID;

	private Icon[] icons = new Icon[2];

	public ItemGarland(int par1, Block block)
	{
		super(par1);
		this.spawnID = block.blockID;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		return "Garland";
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Gets an icon index based on an item's damage value
	 */
	public Icon getIconFromDamage(int par1)
	{
		int j = MathHelper.clamp_int(par1, 0, 15);
		return this.icons[j];
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int j = 0; j < 2; ++j)
		{
			par3List.add(new ItemStack(par1, 1, j));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.icons[0] = par1IconRegister.registerIcon(Festivities.ID + ":garland" + "_" + "spruce");
		this.icons[1] = par1IconRegister.registerIcon(Festivities.ID + ":garland" + "_" + "bell");
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
			if (world.canPlaceEntityOnSide(this.spawnID, x, y, z, false, side, (Entity) null, stack))
			{
				Block block = Block.blocksList[this.spawnID];
				int meta = block.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, stack.getItemDamage());
				meta = stack.getItemDamage() * 8;

				if (side == 0)
				{
					meta += 6;
				}
				if (side == 1)
				{
					meta += 4;
				}
				if (side == 0 || side == 1)
				{
					meta += (int) ((player.rotationYaw + 45) / 90) % 2;
				}
				if (side == 2)
				{
					meta += 3;
				}
				if (side == 3)
				{
					meta += 2;
				}
				if (side == 4)
				{
					meta += 1;
				}
				if (side == 5)
				{
					meta += 0;
				}

				if (world.setBlock(x, y, z, this.spawnID, meta, 3))
				{
					if (world.getBlockId(x, y, z) == this.spawnID)
					{
						Block.blocksList[this.spawnID].onBlockPlacedBy(world, x, y, z, player, stack);
						Block.blocksList[this.spawnID].onPostBlockPlaced(world, x, y, z, meta);
					}

					--stack.stackSize;
				}
			}

			return true;
		}
	}
}
