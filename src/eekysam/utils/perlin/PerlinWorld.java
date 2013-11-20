package eekysam.utils.perlin;

import java.util.ArrayList;
import java.util.List;

public class PerlinWorld implements IPerlinLayer
{
	protected Perlin perlin;
	protected int size;
	protected int layersize;
	
	protected List<PerlinLayer> layers = new ArrayList<PerlinLayer>();
	protected List<Integer> xpos = new ArrayList<Integer>();
	protected List<Integer> ypos = new ArrayList<Integer>();
	
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
	
	public float[] getChunk(int chunkx, int chunky)
	{
		int xind = chunkx / layersize;
		int yind = chunky / layersize;
		float u = chunkx / (float) layersize - xind;
		float v = chunky / (float) layersize - yind;
		PerlinLayer l = this.getLayer(xind, yind);
		if (l == null)
		{
			l = new PerlinLayer(this, this.perlin, xind, yind, this.perlin.getSeed());
		}
		return l.getChunk(u, v);
	}
	
	protected PerlinLayer getLayer(int chunkx, int chunky)
	{
		for (int i = 0; i < this.layers.size(); i++)
		{
			int x = this.xpos.get(i);
			int y = this.ypos.get(i);
			if (x == chunkx && y == chunky)
			{
				return this.layers.get(i);
			}
		}
		return null;
	}
}
