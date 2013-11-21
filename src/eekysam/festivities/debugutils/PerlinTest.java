package eekysam.festivities.debugutils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import eekysam.utils.perlin.Perlin;

public class PerlinTest extends Perlin
{
	public long seed;
	public int num;
	public float mult;
	
	public PerlinTest(long seed, int num, float mult)
	{
		this.seed = seed;
		this.num = num;
		this.mult = mult;
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

	@Override
	public void onBuild()
	{
		
	}
}
