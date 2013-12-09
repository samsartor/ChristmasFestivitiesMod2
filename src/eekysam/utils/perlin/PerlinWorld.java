package eekysam.utils.perlin;

import java.util.ArrayList;
import java.util.List;

public class PerlinWorld implements IPerlinGroup
{
	protected Perlin perlin;
	protected int size;
	protected int layersize;

	protected boolean saveOld;
	protected int maxSaveCount = -1;
	protected int maxLayerCount = -1;

	protected List<IPerlinLayer> layers = new ArrayList<IPerlinLayer>();
	protected List<Integer> xpos = new ArrayList<Integer>();
	protected List<Integer> ypos = new ArrayList<Integer>();

	protected List<ChunkFloats> chunks = new ArrayList<ChunkFloats>();

	public PerlinWorld(Perlin perlin)
	{
		this.perlin = perlin;
		this.size = 16;
		this.layersize = 1;
		int n = perlin.numLayers();
		for (int i = 1; i < n; i++)
		{
			this.size *= 2;
			this.layersize *= 2;
		}
	}

	public void setMaxChunkBuffer(int size)
	{
		if (size <= 0)
		{
			this.saveOld = false;
			size = -1;
		}
		this.maxSaveCount = size;
	}

	public void setMaxLayerCount(int size)
	{
		if (size <= 0)
		{
			throw new IllegalArgumentException("layer buffer must exist");
		}
		this.maxLayerCount = size;
	}

	public void clear()
	{
		layers.clear();
		chunks.clear();
		xpos.clear();
		ypos.clear();
	}

	public float[] getChunk(int chunkx, int chunky)
	{
		if (this.saveOld)
		{
			for (int i = 0; i < this.chunks.size(); i++)
			{
				ChunkFloats fs = this.chunks.get(i);
				if (fs.x == chunkx && fs.y == chunky)
				{
					return fs.floats;
				}
			}
		}
		int xind = chunkx / layersize;
		int yind = chunky / layersize;
		float u = chunkx / (float) layersize - xind;
		float v = chunky / (float) layersize - yind;
		IPerlinLayer l = this.makeLayer(xind, yind);
		float[] fs = l.getChunk(u, v);
		if (this.saveOld)
		{
			ChunkFloats chunk = new ChunkFloats();
			chunk.x = chunkx;
			chunk.y = chunky;
			chunk.floats = fs;
			this.chunks.add(chunk);
			if (this.maxSaveCount > 0 && this.chunks.size() > this.maxSaveCount)
			{
				this.chunks.remove(0);
			}
		}
		return fs;
	}

	protected IPerlinLayer getLayer(int xind, int yind)
	{
		for (int i = 0; i < this.layers.size(); i++)
		{
			int x = this.xpos.get(i);
			int y = this.ypos.get(i);
			if (x == xind && y == yind)
			{
				return this.layers.get(i);
			}
		}
		return null;
	}

	protected IPerlinLayer makeLayer(int xind, int yind)
	{
		IPerlinLayer l = this.getLayer(xind, yind);
		if (l == null)
		{
			l = new PerlinLayerLinear(this, this.perlin, this.perlin.getSeed(), xind, yind);
		}
		return l;
	}

	public void removeme(int x, int y)
	{
		for (int i = 0; i < this.layers.size(); i++)
		{
			int lx = this.xpos.get(i);
			int ly = this.ypos.get(i);
			if (lx == x && ly == y)
			{
				this.layers.remove(i);
				return;
			}
		}
	}

	private class ChunkFloats
	{
		public int x;
		public int y;
		public float[] floats;
	}
}
