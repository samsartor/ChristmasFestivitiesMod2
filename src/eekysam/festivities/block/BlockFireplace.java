package eekysam.festivities.block;

import eekysam.festivities.Festivities;
import eekysam.festivities.tile.TileEntityFireplace;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFireplace extends BlockContainer
{
	public BlockFireplace(int par1, Material par2Material)
	{
		super(par1, par2Material);
		this.setBlockBounds(1 / 16.0F, 0 / 16.0F, 1 / 16.0F, 15 / 16.0F, 5 / 16.0F, 15 / 16.0F);
	}
	
    public int getRenderType()
    {
        return Festivities.blockItemRenderId;
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
    

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityFireplace();
	}
}
