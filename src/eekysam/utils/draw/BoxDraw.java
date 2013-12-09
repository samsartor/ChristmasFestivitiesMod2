package eekysam.utils.draw;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public abstract class BoxDraw
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

	protected float width;
	protected float height;
	protected float length;
	protected int iwidth;
	protected int iheight;
	protected int ilength;

	protected int textureWidth;
	protected int textureHeight;

	protected float textureU;
	protected float textureV;
	protected int itextureU;
	protected int itextureV;

	protected boolean rotUVWorldMapping = false;

	protected IRenderer parent;

	protected boolean inside = false;

	public Tessellator tess;

	public BoxDraw(IRenderer parent)
	{
		this.parent = parent;
		this.tess = Tessellator.instance;
	}

	public void setRotUVWorldMapping(boolean rot)
	{
		this.rotUVWorldMapping = rot;
	}

	public void faceOut()
	{
		this.inside = false;
	}

	public void faceIn()
	{
		this.inside = true;
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

	public void cube(int x, int y, int z, int width, int height, int length)
	{
		this.setPos(x, y, z);
		this.cube(width, height, length);
	}

	public void cube(int width, int height, int length)
	{
		this.iwidth = width;
		this.iheight = height;
		this.ilength = length;
		this.width = width / (float) this.domainW;
		this.height = height / (float) this.domainH;
		this.length = length / (float) this.domainL;
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

	public abstract void XUp();

	public abstract void XDown();

	public abstract void YUp();

	public abstract void YDown();

	public abstract void ZUp();

	public abstract void ZDown();

	public void drawAll()
	{
		this.XUp();
		this.XDown();
		this.YUp();
		this.YDown();
		this.ZUp();
		this.ZDown();
	}

	public void drawSidesSameTexture()
	{
		this.XUp();
		this.XDown();
		this.ZUp();
		this.ZDown();
	}

	public void drawSidesGroupedTexture()
	{
		float u = this.textureU;
		float v = this.textureV;
		this.XUp();
		this.textureU += this.iwidth / (float) textureWidth;
		this.ZUp();
		this.textureU += this.ilength / (float) textureWidth;
		this.XDown();
		this.textureU += this.iwidth / (float) textureWidth;
		this.ZDown();
		this.textureU = u;
		this.textureV = v;
	}

	public void drawAllNormalTextureShape()
	{
		float u = this.textureU;
		float v = this.textureV;
		this.textureU += this.iwidth / (float) textureWidth;
		this.YUp();
		this.textureU += this.iwidth / (float) textureWidth;
		this.YDown();
		this.textureU = u;
		this.textureV = v;
		this.textureV += this.ilength / (float) textureHeight;
		this.drawSidesGroupedTexture();
		this.textureU = u;
		this.textureV = v;
	}

	public void drawAllLeftJustTextureShape(boolean topfirst)
	{
		float u = this.textureU;
		float v = this.textureV;
		if (topfirst)
		{
			this.YUp();
		}
		else
		{
			this.YDown();
		}
		this.textureU += this.iwidth / (float) textureWidth;
		if (!topfirst)
		{
			this.YUp();
		}
		else
		{
			this.YDown();
		}
		this.textureU = u;
		this.textureV = v;
		this.textureV += this.ilength / (float) textureHeight;
		this.drawSidesGroupedTexture();
		this.textureU = u;
		this.textureV = v;
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
