package eekysam.festivities.kringle.biome;

import eekysam.utils.perlin.Perlin;

public class BiomePerlin extends Perlin
{
	public int counter = 0;
	public long seed;
	public int num;
	public float mult;
	
	public BiomePerlin(long seed, int par, int num, float mult)
	{
		this.newSeed(seed, par);
		this.num = num;
		this.mult = mult;
	}
	
    public void newSeed(long seed, long par)
    {
        this.seed = seed;
        this.seed *= this.seed * 6364136223846793005L + 1442695040888963407L;
        this.seed += par;
        this.seed *= this.seed* 6364136223846793005L + 1442695040888963407L;
        this.seed += par;
        this.seed *= this.seed * 6364136223846793005L + 1442695040888963407L;
        this.seed += par;
    }
	
	@Override
	public long getSeed()
	{
		return this.seed;
	}

	@Override
	public float getMult(int layer)
	{
		float m = 1;
		for (int i = 1; i < layer; i++)
		{
			m *= this.mult;
		}
		return m;
	}

	@Override
	public int numLayers()
	{
		return this.num;
	}

	@Override
	public void onBuild()
	{
		this.counter++;
		if (this.counter > 32)
		{
			this.counter = 0;
			this.world.clear();
		}
	}
}
