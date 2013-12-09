package eekysam.utils.draw;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import eekysam.utils.EnumDirection;

public abstract class SideDraw
{
	protected int domainW = 16;
	protected int domainH = 16;
	protected int domainL = 16;

	protected float xpos;
	protected float ypos;
	protected float zpos;
	protected int ixpos;
	protected int iypos;
	protected int izpos;

	protected int textureWidth;
	protected int textureHeight;

	protected float textureU;
	protected float textureV;
	protected int itextureU;
	protected int itextureV;

	protected IRenderer parent;
	public Tessellator tess;

	protected int iwidth = 0;
	protected int iheight = 0;
	protected float width = 0;
	protected float height = 0;

	protected boolean rotUVWorldMapping = false;

	protected boolean doubleSided = false;

	protected EnumDirection dir;

	protected boolean flipU = false;
	protected boolean flipV = false;

	public SideDraw(IRenderer parent)
	{
		this.parent = parent;
		this.tess = Tessellator.instance;
	}

	public void setFlip(boolean u, boolean v)
	{
		this.flipU = u;
		this.flipV = v;
	}

	public void setDoubleSided(boolean doubleSided)
	{
		this.doubleSided = doubleSided;
	}

	public void setRotUVWorldMapping(boolean rot)
	{
		this.rotUVWorldMapping = rot;
	}

	public void setDoubleSided()
	{
		this.setDoubleSided(true);
	}

	public void setPos(int x, int y, int z)
	{
		this.ixpos = x;
		this.iypos = y;
		this.izpos = z;
		this.xpos = x / (float) this.domainW;
		this.ypos = y / (float) this.domainH;
		this.zpos = z / (float) this.domainL;
	}

	public void setDomain(int width, int height, int length)
	{
		this.domainW = width;
		this.domainH = height;
		this.domainL = length;
	}

	public void setTexture(String id, String texture, int width, int height)
	{
		this.textureWidth = width;
		this.textureHeight = height;
		this.parent.rendererBindTexture(new ResourceLocation(id, texture));
	}

	public void selectV(int v)
	{
		this.itextureV = v;
		this.textureV = v / (float) textureHeight;
	}

	public void selectU(int u)
	{
		this.itextureU = u;
		this.textureU = u / (float) textureWidth;
	}

	public void selectUV(int u, int v)
	{
		this.itextureU = u;
		this.itextureV = v;
		this.textureU = u / (float) textureWidth;
		this.textureV = v / (float) textureHeight;
	}

	public abstract void draw();

	public void side(EnumDirection dir, int width, int height, int x, int y, int z)
	{
		this.setPos(x, y, z);
		this.side(dir, width, height);
	}

	public void side(EnumDirection dir, int width, int height)
	{
		this.iwidth = width;
		this.iheight = height;
		this.dir = dir;
		EnumDirection dup = dir.getUp();
		if (dup == EnumDirection.XUp)
		{
			if (this.rotUVWorldMapping)
			{
				this.width = width / (float) this.domainH;
				this.height = height / (float) this.domainL;
			}
			else
			{
				this.width = width / (float) this.domainL;
				this.height = height / (float) this.domainH;
			}
		}
		else if (dup == EnumDirection.YUp)
		{
			if (this.rotUVWorldMapping)
			{
				this.width = width / (float) this.domainL;
				this.height = height / (float) this.domainW;
			}
			else
			{
				this.width = width / (float) this.domainW;
				this.height = height / (float) this.domainL;
			}
		}
		else if (dup == EnumDirection.ZUp)
		{
			if (this.rotUVWorldMapping)
			{
				this.width = width / (float) this.domainH;
				this.height = height / (float) this.domainW;
			}
			else
			{
				this.width = width / (float) this.domainW;
				this.height = height / (float) this.domainH;
			}
		}
	}

	public void reTess()
	{
		if (this.tess.isDrawing)
		{
			this.tess.draw();
			this.tess.startDrawingQuads();
		}
		else
		{
			this.tess.startDrawingQuads();
		}
	}

	public void startTess()
	{
		if (!this.tess.isDrawing)
		{
			this.tess.startDrawingQuads();
		}
	}

	public void setTess(boolean draw)
	{
		if (this.tess.isDrawing != draw)
		{
			if (draw)
			{
				this.tess.startDrawingQuads();
			}
			else
			{
				this.tess.draw();
			}
		}
	}
}
