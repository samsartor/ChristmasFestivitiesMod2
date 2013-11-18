package eekysam.festivities.kringle.gen.feature;

import java.util.Random;

import eekysam.festivities.kringle.Kringle;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenLiquids;

public class WorldGenIce extends WorldGenLiquids implements WorldGenFestive
{
	protected int iceId;
	protected int stone;
	
	public WorldGenIce(int iceId)
	{
		super(iceId);
		this.iceId = iceId;
		this.stone = Kringle.getStone();
	}
	
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        if (par1World.getBlockId(par3, par4 + 1, par5) != this.stone)
        {
            return false;
        }
        else if (par1World.getBlockId(par3, par4 - 1, par5) != this.stone)
        {
            return false;
        }
        else if (par1World.getBlockId(par3, par4, par5) != 0 && par1World.getBlockId(par3, par4, par5) != this.stone)
        {
            return false;
        }
        else
        {
            int l = 0;

            if (par1World.getBlockId(par3 - 1, par4, par5) == this.stone)
            {
                ++l;
            }

            if (par1World.getBlockId(par3 + 1, par4, par5) == this.stone)
            {
                ++l;
            }

            if (par1World.getBlockId(par3, par4, par5 - 1) == this.stone)
            {
                ++l;
            }

            if (par1World.getBlockId(par3, par4, par5 + 1) == this.stone)
            {
                ++l;
            }

            int i1 = 0;

            if (par1World.isAirBlock(par3 - 1, par4, par5))
            {
                ++i1;
            }

            if (par1World.isAirBlock(par3 + 1, par4, par5))
            {
                ++i1;
            }

            if (par1World.isAirBlock(par3, par4, par5 - 1))
            {
                ++i1;
            }

            if (par1World.isAirBlock(par3, par4, par5 + 1))
            {
                ++i1;
            }

            if (l == 3 && i1 == 1)
            {
                par1World.setBlock(par3, par4, par5, this.iceId, 0, 2);
                par1World.scheduledUpdatesAreImmediate = true;
                Block.blocksList[this.iceId].updateTick(par1World, par3, par4, par5, par2Random);
                par1World.scheduledUpdatesAreImmediate = false;
            }

            return true;
        }
    }
}
