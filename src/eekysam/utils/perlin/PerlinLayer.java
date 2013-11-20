package eekysam.utils.perlin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerlinLayer implements IPerlinLayer
{
	protected IPerlinLayer parent;
	protected long seed;
	public PerlinLayer[] list = new PerlinLayer[4];
	public Perlin perlin;
	public boolean isLast;
	public int layer;
	public byte corner;
	
	protected float[] ownGrid = new float[256];
	protected float[] grid = new float[256];
	
	private boolean builtThisGrid = false;
	private boolean builtGrid = false;
	
	public PerlinLayer(IPerlinLayer parent, Perlin perlin, int layer, byte corner, long seed) throws Exception
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
		this.isLast = this.layer == perlin.numLayers();
		this.newSeed(seed, corner);
	}
	
	public PerlinLayer(PerlinWorld parent, Perlin perlin, int xpos, int ypos, long seed)
	{
		this.parent = parent;
		this.perlin = perlin;
		this.layer = 1;
		this.corner = -1;
		this.isLast = this.layer == perlin.numLayers();
		this.newSeed(seed, xpos, ypos);
	}
	
	public void buildOwnGrid()
	{
		Random r = new Random(this.seed);
		for (int i = 0; i < 256; i++)
		{
			this.ownGrid[i] = this.perlin.rand(r.nextFloat(), i, this.layer, this.seed);
		}
		this.builtThisGrid = true;
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
			return this.grid[x + y * 16];
		}
		if (corner == 1)
		{
			if (x < 15)
			{
				return (this.grid[x + y * 16] + this.grid[(x + 1) + y * 16]) / 2;
			}
			else
			{
				return this.blur(x, y, (byte) 0);
			}
		}
		if (corner == 2)
		{
			if (y < 15)
			{
				return (this.grid[x + y * 16] + this.grid[x + (y + 1) * 16]) / 2;
			}
			else
			{
				return this.blur(x, y, (byte) 0);
			}
		}
		if (corner == 3)
		{
			if (y < 15 && x < 15)
			{
				return (this.grid[x + y * 16] + this.grid[x + (y + 1) * 16] + this.grid[(x + 1) + y * 16] + this.grid[(x + 1) + (y + 1) * 16]) / 4;
			}
			else if (x < 15)
			{
				return this.blur(x, y, (byte) 1);
			}
			else if (y < 15)
			{
				return this.blur(x, y, (byte) 2);
			}
			else
			{
				return this.blur(x, y, (byte) 0);
			}
		}
		return 0;
	}
	
	public void buildGrid()
	{
		if (!this.builtThisGrid)
		{
			this.buildOwnGrid();
		}
		this.builtGrid = true;
		float m = this.perlin.getMult(this.layer);
		if (this.parent instanceof PerlinLayer)
		{
			PerlinLayer l = (PerlinLayer) this.parent;
			for (int i = 0; i < 256; i++)
			{
				int X = i % 16;
				int Y = i / 16;
				this.grid[i] = l.getValue(X, Y, this.corner);
				this.grid[i] += this.ownGrid[i] * m;
			}
		}
		else
		{
			for (int i = 0; i < 256; i++)
			{
				this.grid[i] = this.ownGrid[i] * m;
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
			this.list[corner] = new PerlinLayer(this, this.perlin, this.layer + 1, corner, this.seed);
		}
		catch (Exception e) {}
	}
	
	public float[] getChunk(float u, float v)
	{
		if (this.isLast)
		{
			return this.grid;
		}
		if (u < 0.5F && v < 0.5F)
		{
			if (this.list[0] == null)
			{
				this.makeGrid((byte) 0);
			}
			return this.list[0].getChunk(u * 2, v * 2);
		}
		if (u >= 0.5F && v < 0.5F)
		{
			if (this.list[1] == null)
			{
				this.makeGrid((byte) 1);
			}
			return this.list[1].getChunk((u - 0.5F) * 2, v * 2);
		}
		if (u < 0.5F && v >= 0.5F)
		{
			if (this.list[2] == null)
			{
				this.makeGrid((byte) 2);
			}
			return this.list[2].getChunk(u * 2, (v - 0.5F) * 2);
		}
		if (u >= 0.5F && v >= 0.5F)
		{
			if (this.list[3] == null)
			{
				this.makeGrid((byte) 3);
			}
			return this.list[3].getChunk((u - 0.5F) * 2, (v - 0.5F) * 2);
		}
		return null;
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
    
	
    public void newSeed(long seed, long par1, long par2)
    {
        this.seed = seed;
        this.seed *= this.seed * 6364136223846793005L + 1442695040888963407L;
        this.seed += par1;
        this.seed *= this.seed* 6364136223846793005L + 1442695040888963407L;
        this.seed += par2;
        this.seed *= this.seed * 6364136223846793005L + 1442695040888963407L;
        this.seed += par1;
        this.seed *= this.seed * 6364136223846793005L + 1442695040888963407L;
        this.seed += par2;
    }
    
    public void newSeed(long seed, long... pars)
    {
        this.seed = seed;
        for (int i = 0; i < pars.length; i++)
        {
            this.seed *= this.seed * 6364136223846793005L + 1442695040888963407L;
            this.seed += pars[i];
        }
        for (int i = 0; i < pars.length; i++)
        {
            this.seed *= this.seed * 6364136223846793005L + 1442695040888963407L;
            this.seed += pars[i];
        }
    }
}
