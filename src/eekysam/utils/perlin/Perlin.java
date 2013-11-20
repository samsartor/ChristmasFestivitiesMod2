package eekysam.utils.perlin;

public abstract class Perlin
{
	protected PerlinWorld world;
	protected float[] chunk;
	protected int loadedx;
	protected int loadedy;
	
	public abstract long getSeed();
	
	public abstract float getMult(int layer);
	
	public float rand(float original, int i, int layer, long seed)
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
}
