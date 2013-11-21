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
	
	public int worldxpos = -1;
	public int worldypos = -1;
	
	protected float[] ownGrid = new float[256];
	protected float[] grid = new float[256];
	
	private boolean builtThisGrid = false;
	private boolean builtGrid = false;
	
	public PerlinLayer(IPerlinLayer parent, Perlin perlin, int layer, byte corner, long seed) throws Exception
	{
		this(parent, perlin, layer, corner, seed, true);
	}
	
	public PerlinLayer(IPerlinLayer parent, Perlin perlin, int layer, byte corner, long seed, boolean build) throws Exception
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
		this.buildGrid();
	}
	
	public PerlinLayer(PerlinWorld parent, Perlin perlin, int xpos, int ypos, long seed)
	{
		this(parent, perlin, xpos, ypos, seed, true);
	}
	
	public PerlinLayer(PerlinWorld parent, Perlin perlin, int xpos, int ypos, long seed, boolean build)
	{
		this.worldxpos = xpos;
		this.worldypos = ypos;
		this.parent = parent;
		this.perlin = perlin;
		this.layer = 1;
		this.corner = -1;
		this.isLast = this.layer == perlin.numLayers();
		this.newSeed(seed, xpos, ypos);
		this.buildGrid();
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
			return this.getAnyValue(x, y);
		}
		if (corner == 1)
		{
			return (this.getAnyValue(x, y) + this.getAnyValue((x + 1), y)) / 2;
		}
		if (corner == 2)
		{
			return (this.getAnyValue(x, y) + this.getAnyValue(x, (y + 1))) / 2;
		}
		if (corner == 3)
		{
			return (this.getAnyValue(x, y) + this.getAnyValue(x, (y + 1)) + this.getAnyValue((x + 1), y) + this.getAnyValue((x + 1), (y + 1))) / 4;
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
			this.list[corner] = new PerlinLayer(this, this.perlin, this.layer + 1, corner, this.seed, true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public float[] getChunk(float u, float v)
	{
		if (!this.builtGrid)
		{
			this.buildGrid();
		}
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
    
    public float getAnyValue(int x, int y)
    {
    	if (x >= 0 && y >= 0 && x < 16 && y < 16)
    	{
    		return this.grid[x + y * 16];
    	}
    	else
    	{
    		return this.getAnyValueLayer(x, y, this.layer);
    	}
    }
    
    public float getAnyValueLayer(float x, float y, int layer)
    {
    	if (x >= 0 && y >= 0 && x < 16 && y < 16)
    	{
    		if (this.layer == layer)
    		{
    			return this.grid[(int) x + (int) y * 16];
    		}
    		else
    		{
    			return this.getValueUnderLayer(x / 16.0F, y / 16.0F, layer);
    		}
    	}
    	else
    	{
    		if (this.parent instanceof PerlinLayer)
    		{
    			PerlinLayer p = (PerlinLayer) this.parent;
    			switch (this.corner)
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
    			x /= 2;
    			y /= 2;
    			return p.getAnyValueLayer(x, y, layer);
    		}
    		else
    		{
    			PerlinWorld w = (PerlinWorld) this.parent;
    			int wx = this.worldxpos;
    			int wy = this.worldypos;
    			if (x < 0)
    			{
    				wx -= 1;
    				x += 16;
    			}
    			else if (x >= 16)
    			{
    				wx += 1;
    				x -= 16;
    			}
    			else if (y < 0)
    			{
    				wy -= 1;
    				y += 16;
    			}
    			else if (y >= 16)
    			{
    				wy += 1;
    				y -= 16;
    			}
    			PerlinLayer s = w.makeLayer(wx, wy, true);
        		if (this.layer == layer)
        		{
        			return s.getAnyValueLayer(x, y, layer);
        		}
        		else
        		{
	    			return s.getValueUnderLayer(x / 16.0F, y / 16.0F, layer);
        		}
    		}
    	}
    }
    
    public float getAnyValueLayerF(float u, float v, int layer)
    {
    	return this.getAnyValueLayer(u * 16, v * 16, layer);
    }
    
    public float getValueUnderLayer(float u, float v, int layer)
    {
		if (u < 0.5F && v < 0.5F)
		{
			if (this.list[0] == null)
			{
				this.makeGrid((byte) 0);
			}
			return this.list[0].getAnyValueLayerF(u * 2, v * 2, layer);
		}
		if (u >= 0.5F && v < 0.5F)
		{
			if (this.list[1] == null)
			{
				this.makeGrid((byte) 1);
			}
			return this.list[1].getAnyValueLayerF((u - 0.5F) * 2, v * 2, layer);
		}
		if (u < 0.5F && v >= 0.5F)
		{
			if (this.list[2] == null)
			{
				this.makeGrid((byte) 2);
			}
			return this.list[2].getAnyValueLayerF(u * 2, (v - 0.5F) * 2, layer);
		}
		if (u >= 0.5F && v >= 0.5F)
		{
			if (this.list[3] == null)
			{
				this.makeGrid((byte) 3);
			}
			return this.list[3].getAnyValueLayerF((u - 0.5F) * 2, (v - 0.5F) * 2, layer);
		}
		return 0;
    }
}
