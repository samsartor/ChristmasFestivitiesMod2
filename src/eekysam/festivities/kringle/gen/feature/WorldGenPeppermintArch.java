package eekysam.festivities.kringle.gen.feature;

import java.util.Random;

import eekysam.festivities.Festivities;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenPeppermintArch extends WorldGenerator implements WorldGenFestive
{
	public int minHeight;
	public int minWidth;
	public int wvar;
	public int hvar;
	public int runs;

	public WorldGenPeppermintArch(int minHeight, int heightvariance, int minWidth, int widthvariance, int runs)
	{
		this.minHeight = minHeight;
		this.hvar = heightvariance;
		this.minWidth = minWidth;
		this.wvar = widthvariance;
		this.runs = runs;
	}

	public boolean generate(World world, Random random, int x, int y, int z)
	{
		for (int t = 0; t < this.runs; t++)
		{
			int X1 = x + random.nextInt(8) - random.nextInt(8);
			int Y = y + random.nextInt(8) - random.nextInt(8);
			int Z1 = z + random.nextInt(8) - random.nextInt(8);

			if (this.canGrow(world, X1, Y, Z1))
			{
				int X2 = X1 + random.nextInt(minWidth * 2) - minWidth + random.nextInt(wvar * 2) - wvar;
				int Z2 = Z1 + random.nextInt(minWidth * 2) - minWidth + random.nextInt(wvar * 2) - wvar;

				int h = minHeight + random.nextInt(hvar);

				for (int i = 0; i < h; i++)
				{
					world.setBlock(X1, Y + i, Z1, Festivities.candyLog.blockID, 0, 2);
				}

				int dy = Y + h;
				while (true)
				{
					int id = world.getBlockId(X2, dy, Z2);
					if (id != 0 && !Block.blocksList[id].isBlockReplaceable(world, X2, dy--, Z2))
					{
						break;
					}
					else
					{
						world.setBlock(X2, dy, Z2, Festivities.candyLog.blockID, 0, 2);
					}
					dy--;
				}

				int i = 0;
				int j = 0;
				int toi = X2 - X1;
				int toj = Z2 - Z1;

				while (true)
				{
					if (Math.abs(i) >= Math.abs(toi) && Math.abs(j) >= Math.abs(toj))
					{
						break;
					}

					if (Math.abs(i / (float) toi) > Math.abs(j / (float) toj))
					{
						world.setBlock(X1 + i, Y + h, Z1 + j, Festivities.candyLog.blockID, 8, 2);
						if (toj < 0)
						{
							j--;
						}
						else
						{

							j++;
						}
					}
					else
					{
						world.setBlock(X1 + i, Y + h, Z1 + j, Festivities.candyLog.blockID, 4, 2);
						if (toi < 0)
						{
							i--;
						}
						else
						{

							i++;
						}
					}
				}

				return true;
			}
		}
		return false;
	}

	public boolean canGrow(World world, int x, int y, int z)
	{
		boolean flag = true;
		for (int i = 0; i < 2; i++)
		{
			int id = world.getBlockId(x, y + i, z);
			flag &= (id == 0 || Block.blocksList[id].isBlockReplaceable(world, x, y + i, z));
		}
		flag &= world.doesBlockHaveSolidTopSurface(x, y - 1, z);
		return flag;
	}
}
