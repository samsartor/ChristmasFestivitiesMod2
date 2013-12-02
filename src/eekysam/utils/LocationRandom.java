package eekysam.utils;

import java.util.Random;

public class LocationRandom
{
	private long seed;
	private long lastseed;

	public LocationRandom(long seed)
	{
		this.seed = seed;
	}

	private long posSeed(long x, long y)
	{
		long seed = this.seed;
		seed *= seed * 6364136223846793005L + 1442695040888963407L;
		seed += x;
		seed *= seed * 6364136223846793005L + 1442695040888963407L;
		seed += y;
		seed *= seed * 6364136223846793005L + 1442695040888963407L;
		seed += x;
		seed *= seed * 6364136223846793005L + 1442695040888963407L;
		seed += y;
		return seed;
	}

	private int next(int bits, int x, int y)
	{
		long seed = this.posSeed(x, y);
		seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
		this.lastseed = seed;
		return (int) (seed >>> (48 - bits));
	}

	private int next(int bits)
	{
		long seed = this.lastseed;
		seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
		this.lastseed = seed;
		return (int) (seed >>> (48 - bits));
	}

	public int nextInt(int x, int y)
	{
		return this.next(32, x, y);
	}

	public int nextInt(int n, int x, int y)
	{
		if (n <= 0)
		{
			throw new IllegalArgumentException("n must be positive");
		}

		if ((n & -n) == n) // i.e., n is a power of 2
		{
			return (int) ((n * (long) next(31, x, y)) >> 31);
		}

		this.lastseed = this.posSeed(x, y);
		
		int bits, val;
		do
		{
			bits = next(31);
			val = bits % n;
		}
		while (bits - val + (n - 1) < 0);
		return val;
	}

	public float nextFloat(int x, int y)
	{
		return next(24, x, y) / ((float) (1 << 24));
	}
	
	public long getLocSeed(int x, int y)
	{
		return this.posSeed(x, y);
	}
}
