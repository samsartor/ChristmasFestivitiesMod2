package eekysam.festivities.block;

import eekysam.festivities.Festivities;
import eekysam.festivities.ITipItem;
import eekysam.festivities.tile.SnowglobeScene;
import eekysam.festivities.tile.TileEntityPlate;
import eekysam.festivities.tile.TileEntityPlate.PlateFoods;
import eekysam.festivities.tile.TileEntitySnowglobe;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTreatPlate extends BlockContainer implements ITipItem
{
	public static boolean givetip = true;

	public BlockTreatPlate(int par1, Material par2Material)
	{
		super(par1, par2Material);
		this.setBlockBounds(0 / 16.0F, 0 / 16.0F, 0 / 16.0F, 16 / 16.0F, 4 / 16.0F, 16 / 16.0F);
	}

	public int getRenderType()
	{
		return Festivities.blockItemRenderId;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	public void onBlockClicked(World world, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
		TileEntityPlate t = (TileEntityPlate) world.getBlockTileEntity(par2, par3, par4);
		if (t != null)
		{
			ItemStack[] items = t.onClear();
			for (int i = 0; i < items.length; i++)
			{
				if (items[i] != null)
				{
					par5EntityPlayer.dropPlayerItem(items[i]);
				}
			}
		}
	}

	public void breakBlock(World world, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityPlate t = (TileEntityPlate) world.getBlockTileEntity(par2, par3, par4);
		if (t != null)
		{
			ItemStack[] items = t.onClear();
			for (int i = 0; i < items.length; i++)
			{
				if (items[i] != null)
				{
					float f = 0.7F;
					double dx = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
					double dy = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.2D + 0.6D;
					double dz = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
					EntityItem entityitem = new EntityItem(world, (double) par2 + dx, (double) par3 + dy, (double) par4 + dz, items[i]);
					entityitem.delayBeforeCanPickup = 10;
					world.spawnEntityInWorld(entityitem);
				}
			}
		}
		super.breakBlock(world, par2, par3, par4, par5, par6);
	}

	public boolean onBlockActivated(World world, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		ItemStack itemstack = par5EntityPlayer.inventory.getCurrentItem();

		if (world.isRemote)
		{
			return true;
		}
		if (itemstack == null)
		{
			TileEntityPlate t = (TileEntityPlate) world.getBlockTileEntity(par2, par3, par4);
			ItemStack i = t.dropOneItem();
			if (i == null)
			{
				return false;
			}
			else
			{
				par5EntityPlayer.dropPlayerItem(i);
				return true;
			}
		}
		else
		{
			TileEntityPlate t = (TileEntityPlate) world.getBlockTileEntity(par2, par3, par4);
			PlateFoods food = t.getFood(itemstack);
			if (food != null)
			{
				if (t.addItem(food))
				{
					t.onChange();
					if (!par5EntityPlayer.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
					{
						par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, (ItemStack) null);
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityPlate();
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
	public String[] getTip(EntityPlayer player, ItemStack stack)
	{
		return new String[] { "A dish to hold all your tasty treats!" };
	}

	public String[] getShiftTip(EntityPlayer player, ItemStack stack)
	{
		return new String[] { "Right-Click to add the treat you are holding to the plate", "If the treat isn't added, then it might not fit on the plate", "Remove treats from the plate by Right-Clicking while holding nothing" };
	}
}
