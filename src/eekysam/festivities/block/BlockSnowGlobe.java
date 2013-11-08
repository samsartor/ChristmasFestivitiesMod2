package eekysam.festivities.block;

import eekysam.festivities.tile.TileEntitySnowglobe;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSnowglobe extends BlockContainer
{
	public BlockSnowglobe(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntitySnowglobe();
	}

	public boolean onBlockActivated(World world, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (world.isRemote)
		{
			TileEntitySnowglobe t = (TileEntitySnowglobe) world.getBlockTileEntity(par2, par3, par4);
			t.type = 1;
			t.onChange();
		}
		return true;
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

	public static int determineOrientation(World world, int x, int y, int z, EntityLivingBase entity)
	{
		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item)
	{
		int l = determineOrientation(world, x, y, z, entity);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}
}
