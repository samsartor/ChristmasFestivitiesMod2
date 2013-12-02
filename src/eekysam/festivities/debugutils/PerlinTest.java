package eekysam.festivities.debugutils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import eekysam.utils.Timer;
import eekysam.utils.perlin.Perlin;

public class PerlinTest extends Perlin
{
	public long seed;
	public int num;
	public float mult;
	
	public Timer timer;
	public int chunks = 0;
	
	public PerlinTest(long seed, int num, float mult)
	{
		this.seed = seed;
		this.num = num;
		this.mult = mult;
		
		this.timer = new Timer();
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
	
	public void saveImg(String file, int w, int h)
	{
	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    float[] grid = this.getGrid(null, 100, 100, w, h);
	    long nano = this.timer.end();
	    System.out.printf("Made %d chunks in %.2fms%n", this.chunks, nano * 0.000001D);
	    for (int i = 0; i < w; i++)
	    {
		    for (int j = 0; j < h; j++)
		    {
		    	float v = grid[i + j * w];
		    	v /= 2;
		    	if (v > 1.0F)
		    	{
		    		v = 1.0F;
		    	}
		    	if (v < 0.0F)
		    	{
		    		v = 0.0F;
		    	}
		    	Color c = new Color(v, v, v);
		    	bi.setRGB(i, j, c.getRGB());
		    }
	    }
		try 
		{
		    File outputfile = new File(file);
		    ImageIO.write(bi, "png", outputfile);
		} 
		catch (IOException e) {}
	}
	
	public float[] getGrid(float[] array, int x, int y, int width, int height)
	{
		this.chunks = 0;
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
				this.timer.start();
				float[] chunk = this.world.getChunk(chkx + i, chky + j);
				this.timer.pause();
				this.chunks++;
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
