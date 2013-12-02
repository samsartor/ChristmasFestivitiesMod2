package eekysam.utils.perlin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import eekysam.utils.LocationRandom;

class PerlinLayer implements IPerlinLayer
{
	protected IPerlinLayer parent;
	protected LocationRandom rand;
	protected long seed;

	public PerlinLayer[] list = new PerlinLayer[4];
	protected boolean[] removed = new boolean[4];
	
	public Perlin perlin;
	public boolean isLast;
	public int layer;
	public byte corner;

	public int xpos;
	public int ypos;

	protected float[] grid;

	public PerlinLayer(PerlinWorld parent, Perlin perlin, long seed, int xpos, int ypos)
	{
		this.parent = parent;
		this.perlin = perlin;
		this.layer = 1;
		this.corner = -1;
		this.xpos = xpos;
		this.ypos = ypos;
		this.isLast = this.layer == perlin.numLayers();
		this.seed = seed;
		this.rand = new LocationRandom(seed);
		this.buildGrid();
	}

	public PerlinLayer(PerlinLayer parent, Perlin perlin, int layer, byte corner, long seed, int xpos, int ypos) throws Exception
	{
		this.parent = parent;
		this.perlin = perlin;
		this.layer = layer;
		this.corner = corner;
		if (layer == 0)
		{
			throw new Exception("Perlin layer number 0 is reserved for World");
		}
		if (layer < 0)
		{
			throw new Exception("Perlin layer number can not be negitive");
		}
		if (layer > perlin.numLayers())
		{
			throw new Exception("Perlin layer number to large");
		}
		this.xpos = xpos;
		this.ypos = ypos;
		this.isLast = this.layer == perlin.numLayers();
		this.seed = seed;
		this.rand = new LocationRandom(seed);
		this.buildGrid();
	}

	public float getValue(int x, int y, byte corner)
	{
		switch (corner)
		{
			case 0:
				break;
			case 1:
				x += 16;
				break;
			case 2:
				y += 16;
				break;
			case 3:
				x += 16;
				y += 16;
				break;
		}
		byte c = 0;
		if (x % 2 == 1)
		{
			c += 1;
		}
		if (y % 2 == 1)
		{
			c += 2;
		}
		return this.blur((x / 2), (y / 2), c);
	}

	public float blur(int x, int y, byte corner)
	{
		if (corner == 0)
		{
			return this.getOwnValue(x, y);
		}
		if (corner == 1)
		{
			return (this.getOwnValue(x, y) + this.getOwnValue((x + 1), y)) / 2;
		}
		if (corner == 2)
		{
			return (this.getOwnValue(x, y) + this.getOwnValue(x, (y + 1))) / 2;
		}
		if (corner == 3)
		{
			return (this.getOwnValue(x, y) + this.getOwnValue(x, (y + 1)) + this.getOwnValue((x + 1), y) + this.getOwnValue((x + 1), (y + 1))) / 4;
		}
		return 0;
	}

	private float getOwnValue(int x, int y)
	{
		return this.grid[(x + 1) + (y + 1) * 18];
	}

	public void buildGrid()
	{
		this.grid = new float[324];

		float m = this.perlin.getMult(this.layer);

		for (int i = -1; i < 17; i++)
		{
			for (int j = -1; j < 17; j++)
			{
				int x = i + this.xpos * 16;
				int y = j + this.ypos * 16;
				int ind = (i + 1) + (j + 1) * 18;
				this.grid[ind] = this.perlin.rand(this.rand.nextFloat(x, y), this.layer, this.rand.getLocSeed(x, y)) * m;
			}
		}

		if (this.parent instanceof PerlinLayer)
		{
			PerlinLayer l = (PerlinLayer) this.parent;
			for (int i = -1; i < 17; i++)
			{
				for (int j = -1; j < 17; j++)
				{
					int x = i + this.xpos * 16;
					int y = j + this.ypos * 16;
					int ind = (i + 1) + (j + 1) * 18;
					this.grid[ind] += l.getValue(i, j, this.corner);
				}
			}
		}
	}

	public void makeGrid(byte corner)
	{
		if (this.isLast || this.list[corner] != null)
		{
			return;
		}
		try
		{
			int x = this.xpos * 2;
			int y = this.ypos * 2;
			switch (corner)
			{
				case 0:
					break;
				case 1:
					x += 1;
					break;
				case 2:
					y += 1;
					break;
				case 3:
					x += 1;
					y += 1;
					break;
			}
			this.list[corner] = new PerlinLayer(this, this.perlin, this.layer + 1, corner, this.downSeed(), x, y);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public long downSeed()
	{
		return (this.seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
	}

	public float[] getChunk(float u, float v)
	{
		if (this.isLast)
		{
			float[] g = new float[256];
			for (int i = 0; i < 256; i++)
			{
				int X = i % 16;
				int Y = i / 16;
				g[i] = this.getOwnValue(X, Y);
			}
			return g;
		}
		float[] dat = null;
		if (u < 0.5F && v < 0.5F)
		{
			if (this.list[0] == null)
			{
				this.makeGrid((byte) 0);
			}
			dat = this.list[0].getChunk(u * 2, v * 2);
		}
		else if (u >= 0.5F && v < 0.5F)
		{
			if (this.list[1] == null)
			{
				this.makeGrid((byte) 1);
			}
			dat = this.list[1].getChunk((u - 0.5F) * 2, v * 2);
		}
		else if (u < 0.5F && v >= 0.5F)
		{
			if (this.list[2] == null)
			{
				this.makeGrid((byte) 2);
			}
			dat = this.list[2].getChunk(u * 2, (v - 0.5F) * 2);
		}
		if (u >= 0.5F && v >= 0.5F)
		{
			if (this.list[3] == null)
			{
				this.makeGrid((byte) 3);
			}
			dat = this.list[3].getChunk((u - 0.5F) * 2, (v - 0.5F) * 2);
		}
		if (this.isDone())
		{
			if (this.parent instanceof PerlinLayer)
			{
				PerlinLayer l = (PerlinLayer) this.parent;
				l.removeme(corner);
			}
			else
			{
				PerlinWorld w = (PerlinWorld) this.parent;
				w.removeme(this.xpos, this.ypos);
			}
		}
		return dat;
	}
	
	public boolean isDone()
	{
		boolean flag = true;
		for (int i = 0; i < 4; i++)
		{
			flag &= this.list[i] != null || this.removed[i];
		}
		return flag;
	}
	
	public void removeme(byte corner)
	{
		this.removed[corner] = true;
		this.list[corner] = null;
	}
}
