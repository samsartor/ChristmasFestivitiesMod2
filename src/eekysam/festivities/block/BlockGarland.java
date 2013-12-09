package eekysam.festivities.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.Festivities;
import eekysam.festivities.ITipItem;
import eekysam.festivities.tile.TileEntityGarland;

public class BlockGarland extends BlockContainer implements ITipItem
{
	public BlockGarland(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y,
	 * z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		this.updateGarlandBounds(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}

	/**
	 * Update the ladder block bounds based on the given metadata value.
	 */
	public void updateGarlandBounds(int meta)
	{
		meta %= 8;

		int xpos = 8;
		int ypos = 8;
		int zpos = 8;

		int dir;

		if (meta < 4)
		{
			int side = meta % 4;
			dir = side / 2;
			switch (side)
			{
				case 0:
					xpos = 2;
					break;
				case 1:
					xpos = 14;
					break;
				case 2:
					zpos = 2;
					break;
				case 3:
					zpos = 14;
					break;
			}
		}
		else
		{
			meta = meta % 4;
			dir = meta % 2;
			if (meta < 2)
			{
				ypos = 2;
			}
			else
			{
				ypos = 14;
			}
		}

		if (dir == 0)
		{
			this.setBlockBounds((xpos - 2) / 16.0F, (ypos - 2) / 16.0F, 0.0F, (xpos + 2) / 16.0F, (ypos + 2) / 16.0F, 1.0F);
		}
		else
		{
			this.setBlockBounds(0.0F, (ypos - 2) / 16.0F, (zpos - 2) / 16.0F, 1.0F, (ypos + 2) / 16.0F, (zpos + 2) / 16.0F);
		}
	}

	@Override
	public String[] getTip(EntityPlayer player, ItemStack stack)
	{
		return new String[] { "Oops...", "Use the garland item", "This is a technical block" };
	}

	@Override
	public String[] getShiftTip(EntityPlayer player, ItemStack stack)
	{
		return null;
	}

	@Override
	public int getRenderType()
	{
		return Festivities.blockItemRenderId;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityGarland();
	}
}
