package eekysam.festivities.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.Festivities;
import eekysam.festivities.ITipItem;
import eekysam.festivities.tile.TileEntityFireplace;

public class BlockFireplace extends BlockContainer implements ITipItem
{
	public BlockFireplace(int par1, Material par2Material)
	{
		super(par1, par2Material);
		this.setBlockBounds(1 / 16.0F, 0 / 16.0F, 1 / 16.0F, 15 / 16.0F, 5 / 16.0F, 15 / 16.0F);
	}

	@Override
	public int getRenderType()
	{
		return Festivities.blockItemRenderId;
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
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityFireplace();
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (par5Random.nextInt(24) == 0)
		{
			par1World.playSound((double) ((float) par2 + 0.5F), (double) ((float) par3 + 0.5F), (double) ((float) par4 + 0.5F), "fire.fire", 1.0F + par5Random.nextFloat(), par5Random.nextFloat() * 0.7F + 0.3F, false);
		}

		for (int i = 0; i < 3; ++i)
		{
			float fx = (float) par2 + par5Random.nextFloat() * 0.6F + 0.2F;
			float fy = (float) par3 + par5Random.nextFloat() * 0.5F + 0.3F;
			float fz = (float) par4 + par5Random.nextFloat() * 0.6F + 0.2F;
			par1World.spawnParticle("smoke", fx, fy, fz, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public String[] getTip(EntityPlayer player, ItemStack stack)
	{
		return new String[] { "Nice, warm, and safe" };
	}

	@Override
	public String[] getShiftTip(EntityPlayer player, ItemStack stack)
	{
		return null;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return super.canPlaceBlockAt(world, x, y, z) && this.canSitAt(world, x, y, z);
	}

	public boolean canSitAt(World world, int x, int y, int z)
	{
		int id = world.getBlockId(x, y - 1, z);
		if (id == 0)
		{
			return false;
		}
		if (id == this.blockID)
		{
			return false;
		}
		return world.doesBlockHaveSolidTopSurface(x, y - 1, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int changeId)
	{
		if (!this.canSitAt(world, x, y, z))
		{
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}
}
