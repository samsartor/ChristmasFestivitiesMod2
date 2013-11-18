package eekysam.festivities.kringle.gen.feature;

import java.util.Random;

import eekysam.festivities.Festivities;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenPeppermintPole extends WorldGenerator implements WorldGenFestive
{
	public int minHeight;
	public int variance;
	public int runs;
	
	public WorldGenPeppermintPole(int minHeight, int variance, int runs)
	{
		this.minHeight = minHeight;
		this.variance = variance;
		this.runs = runs;
	}
	
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		for (int t = 0; t < this.runs; t++)
		{
			int X = x + random.nextInt(8) - random.nextInt(8);
			int Y = y + random.nextInt(8) - random.nextInt(8);
			int Z = z + random.nextInt(8) - random.nextInt(8);
			
			if (this.canGrow(world, X, Y, Z))
			{
				int h = this.minHeight + random.nextInt(this.variance);
				
				for (int i = 0; i < h; i++)
				{
					int id = world.getBlockId(x, y + i, z);
					if (id == 0 || Block.blocksList[id].isBlockReplaceable(world, x, y, z))
					{
						world.setBlock(x, y + i, z, Festivities.candyLog.blockID, 0, 2);
					}
					else
					{
						break;
					}
				}
			}
		}
		return true;
	}
	
	public boolean canGrow(World world, int x, int y, int z)
	{
		boolean flag = true;
		for (int i = 0; i < 2; i++)
		{
			int id = world.getBlockId(x, y + i, z);
			flag &= (id == 0 || Block.blocksList[id].isBlockReplaceable(world, x, y, z));
		}
		flag &= world.doesBlockHaveSolidTopSurface(x, y - 1, z);
		return flag;
	}
}
