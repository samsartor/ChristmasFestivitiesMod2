package eekysam.festivities.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.Festivities;
import eekysam.festivities.tile.TileEntityOrnament;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockOrnament extends BlockContainer
{
	private boolean clear;
	public static boolean canSit = false;
	public static boolean hangAny = true;
	public static boolean doHang = true;
	
	public BlockOrnament(int par1, boolean clear)
	{
        super(par1, Material.circuits);
        this.clear = clear;
    }
	
    public boolean isClear()
	{
		return clear;
	}
	
    public boolean isOpaqueCube()
    {
        return false;
    }
    
	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
	{
		return false;
	}
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World world, int x, int y, int z)
    {
    	if (this.clear)
    	{
    		return Festivities.clearOrnament.itemID;
    	}
    	else
    	{
    		return Festivities.coloredOrnament.itemID;
    	}
    }
    
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
    	return super.canPlaceBlockAt(world, x, y, z) && this.canHangAt(world, x, y, z);
    }
    
    public boolean canHangAt(World world, int x, int y, int z)
    {
    	if (this.doHang)
    	{
	    	if (this.canHangFrom(world, x, y + 1, z, 0))
	    	{
	    		return true;
	    	}
	    	if (this.canHangFrom(world, x + 1, y, z, 1))
	    	{
	    		return true;
	    	}
	    	if (this.canHangFrom(world, x - 1, y, z, 1))
	    	{
	    		return true;
	    	}
	    	if (this.canHangFrom(world, x, y, z + 1, 1))
	    	{
	    		return true;
	    	}
	    	if (this.canHangFrom(world, x, y, z - 1, 1))
	    	{
	    		return true;
	    	}
	    	if (this.canHangFrom(world, x, y - 1, z, 2))
	    	{
	    		return true;
	    	}
	    	return false;
    	}
    	return true;
    }
    
    public boolean canHangFrom(World world, int x, int y, int z, int dir)
    {
    	int id = world.getBlockId(x, y, z);
    	Block b;
    	if (id == 0)
    	{
    		return false;
    	}
    	else
    	{
    		if (dir == 2 && this.canSit && world.doesBlockHaveSolidTopSurface(x, y, z))
    		{
    			return true;
    		}
    		b = Block.blocksList[id];
    		if (this.hangAny)
    		{
    			return ! (b instanceof BlockOrnament);
    		}
    	}
    	if (b.isLeaves(world, x, y, z))
    	{
    		return true;
    	}
    	Material mat = b.blockMaterial;
    	if (mat == Material.circuits || mat == Material.fire || mat == Material.vine || mat.isLiquid() || mat == Material.cactus || mat == Material.anvil || mat == Material.cake || mat == Material.dragonEgg || mat == Material.materialCarpet)
    	{
    		return false;
    	}
    	if (dir == 0)
    	{
    		if (b.isBlockSolidOnSide(world, x, y, z, ForgeDirection.DOWN))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, int changeId)
    {
        if (!this.canHangAt(world, x, y, z))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }


    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDamageValue(World world, int x, int y, int z)
    {
    	if (this.clear)
    	{
    		return 0;
    	}
    	else
    	{
    		return world.getBlockMetadata(x, y, z);
    	}
    }
    
    public int idDropped(int meta, Random rand, int fort)
    {
    	if (this.clear)
    	{
    		return Festivities.clearOrnament.itemID;
    	}
    	return 0;
    }
    
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
        
        if (!this.clear)
        {
            ItemStack itemstack = new ItemStack(Festivities.coloredOrnament, 1, meta);
            this.dropBlockAsItem_do(world, x, y, z, itemstack);
        }
    }

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityOrnament();
	}
}
