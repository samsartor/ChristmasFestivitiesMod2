package eekysam.utils.perlin;

public abstract class Perlin
{
	protected PerlinWorld world;
	protected float[] chunk;
	protected int loadedx;
	protected int loadedy;
	
	public abstract long getSeed();
	
	public abstract float getMult(int layer);
	
	public float rand(float original, int layer, long seed)
	{
		return original;
	}
		
	public abstract int numLayers();
	
	public void makeWorld()
	{
		this.world = new PerlinWorld(this);
	}
	
	public float[] getChunk(int chunkx, int chunky)
	{
		this.loadChunk(chunkx, chunky);
		return this.chunk;
	}
	
	private void loadChunk(int chunkx, int chunky)
	{
		if (chunkx != this.loadedx || chunky != this.loadedy || this.chunk == null)
		{
			this.loadedx = chunkx;
			this.loadedy = chunky;
			this.chunk = this.world.getChunk(chunkx, chunky);
		}
	}
	
	public float getValue(int x, int y)
	{
		int chunkx = x / 16;
		int chunky = y / 16;
		this.loadChunk(chunkx, chunky);
		return this.chunk[(x % 16) + (y % 16) * 16];
	}
	
	public float[] getGrid(float[] array, int x, int y, int width, int height)
	{
		if (array == null || array.length < width * height)
		{
			array = new float[width * height];
		}
		int chkx = x / 16;
		int chky = y / 16;
		int numx = ((x + width) / 16) - chkx + 1;
		int numy = ((y + height) / 16) - chky + 1;
		for (int i = 0; i < numx; i++)
		{
			int chkxind = (chkx + i) * 16;
			for (int j = 0; j < numy; j++)
			{
				float[] chunk = this.world.getChunk(chkx + i, chky + j);
				int chkyind = (chky + j) * 16;
				for (int k = 0; k < 256; k++)
				{
					int xind = chkxind + k % 16 - x;
					int yind = chkyind + k / 16 - y;
					if (xind >= 0 && xind < width && yind >= 0 && yind < height)
					{
						array[xind + yind * height] = chunk[k];
					}
				}
			}
		}
		return array;
	}
}
