package eekysam.festivities.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockOrnament extends Block
{
	public BlockOrnament(int par1)
	{
        super(par1, Material.circuits);
    }
	
    public boolean isOpaqueCube()
    {
        return false;
    }
}
