package eekysam.festivities.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.Festivities;
import eekysam.festivities.client.particle.EntitySnowFX;
import eekysam.festivities.tile.SnowglobeScene;
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

public class BlockSnowGlobe extends BlockContainer
{
	public BlockSnowGlobe(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntitySnowglobe();
	}
	
    public int getRenderType()
    {
        return Festivities.blockItemRenderId;
    }

	public boolean onBlockActivated(World world, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (world.isRemote)
		{
			TileEntitySnowglobe t = (TileEntitySnowglobe) world.getBlockTileEntity(par2, par3, par4);
			t.type = world.rand.nextInt(SnowglobeScene.list.size());
			t.onChange();
		}
		return true;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
    public boolean renderAsNormalBlock()
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
	
    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
    	for (int i = 0; i < 4; i++)
    	{
    		double X = x + random.nextFloat() * (12 / 16.0F) + (2 / 16.0F);
    		double Z = z + random.nextFloat() * (12 / 16.0F) + (2 / 16.0F);
    		double Y = y + (15 / 16.0F);
    		EntitySnowFX.spawn(new EntitySnowFX(world, X, Y, Z).setSize(0.004F));
    	}
    }
}
