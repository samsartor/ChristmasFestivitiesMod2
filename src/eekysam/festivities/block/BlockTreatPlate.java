package eekysam.festivities.block;

import eekysam.festivities.tile.SnowglobeScene;
import eekysam.festivities.tile.TileEntityPlate;
import eekysam.festivities.tile.TileEntityPlate.PlateFoods;
import eekysam.festivities.tile.TileEntitySnowglobe;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTreatPlate extends BlockContainer
{
	public BlockTreatPlate(int par1, Material par2Material)
	{
		super(par1, par2Material);
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
				if (t.addItem(food, par5EntityPlayer.getRNG()))
				{
					t.onChange();
	                if (!par5EntityPlayer.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
	                {
	                    par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, (ItemStack)null);
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
}
