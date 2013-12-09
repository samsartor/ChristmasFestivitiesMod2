package eekysam.festivities.kringle.biome;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

//TODO Make system for multiple biomes
public class WorldChunkManagerKringle extends WorldChunkManager
{
	protected BiomePerlin plantNoise;
	protected BiomePerlin candyNoise;

	private float kringleTemp = 0.0F;
	private float kringleHumid = 0.5F;

	public WorldChunkManagerKringle()
	{

	}

	@Override
	public BiomeGenBase getBiomeGenAt(int x, int y)
	{
		float plant = this.plantNoise.getValue(x, y);
		float candy = this.candyNoise.getValue(x, y);
		return BiomeGenKringle.getBiome(plant, candy);
	}

	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
	{
		if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
		{
			par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
		}

		float[] plant = this.plantNoise.getGrid(null, par2, par3, par4, par5);
		float[] candy = this.candyNoise.getGrid(null, par2, par3, par4, par5);

		int l = par4 * par5;

		for (int i = 0; i < l; i++)
		{
			par1ArrayOfBiomeGenBase[i] = BiomeGenKringle.getBiome(plant[i], candy[i]);
		}

		return par1ArrayOfBiomeGenBase;
	}

	@Override
	public float[] getTemperatures(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
	{
		if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
		{
			par1ArrayOfFloat = new float[par4 * par5];
		}

		Arrays.fill(par1ArrayOfFloat, 0, par4 * par5, this.kringleTemp);
		return par1ArrayOfFloat;
	}

	@Override
	public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
	{
		if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
		{
			par1ArrayOfFloat = new float[par4 * par5];
		}

		Arrays.fill(par1ArrayOfFloat, 0, par4 * par5, kringleHumid);
		return par1ArrayOfFloat;
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
	{
		if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
		{
			par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
		}

		float[] plant = this.plantNoise.getGrid(null, par2, par3, par4, par5);
		float[] candy = this.candyNoise.getGrid(null, par2, par3, par4, par5);

		int l = par4 * par5;

		for (int i = 0; i < par4; i++)
		{
			for (int j = 0; j < par5; j++)
			{
				par1ArrayOfBiomeGenBase[i * par5 + j] = BiomeGenKringle.getBiome(plant[i + j * par4], candy[i + j * par4]);
			}
		}

		return par1ArrayOfBiomeGenBase;
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6)
	{
		return this.loadBlockGeneratorData(par1ArrayOfBiomeGenBase, par2, par3, par4, par5);
	}

	@Override
	public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random)
	{
		return null;
	}

	@Override
	public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
	{
		return true;
	}

	public void makeNoise(long seed)
	{
		this.candyNoise = new BiomePerlin(seed, 1, 8, 0.5F);
		this.candyNoise.makeWorld();
		this.plantNoise = new BiomePerlin(seed, 2, 8, 0.5F);
		this.plantNoise.makeWorld();
	}
}
