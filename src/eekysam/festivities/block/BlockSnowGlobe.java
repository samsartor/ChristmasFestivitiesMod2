package eekysam.festivities.block;

import eekysam.festivities.tile.TileEntitySnowGlobe;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
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
		return new TileEntitySnowGlobe();
	}
	
    public boolean isOpaqueCube()
    {
        return false;
    }
}
