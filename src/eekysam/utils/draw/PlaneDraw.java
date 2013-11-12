package eekysam.utils.draw;

import org.lwjgl.util.vector.Vector3f;

import eekysam.utils.EnumDirection;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public abstract class PlaneDraw
{	
	protected int domainW = 16;
	protected int domainH = 16;
	protected int domainL = 16;
	
	public Vector3f v;
	
	protected float xpos;
	protected float ypos;
	protected float zpos;
	
	protected Vector3f[] vects = new Vector3f[2];
	protected Vector3f[] ivects = new Vector3f[2];
	
	protected IRenderer parent;
	public Tessellator tess;
	
	protected int textureWidth;
	protected int textureHeight;
	
	protected float textureU;
	protected float textureV;
	protected int itextureU;
	protected int itextureV;
	
	protected float width = 0;
	protected float height = 0;
	
	protected boolean doubleSided = false;
	
	public PlaneDraw(IRenderer parent)
	{
		this.parent = parent;
		this.tess = Tessellator.instance;
		this.vects[0] = new Vector3f();
		this.vects[1] = new Vector3f();
		this.ivects[0] = new Vector3f();
		this.ivects[1] = new Vector3f();
	}
	
	public void setDoubleSided(boolean doubleSided)
	{
		this.doubleSided = doubleSided;
	}
	
	public void setDoubleSided()
	{
		this.setDoubleSided(true);
	}
	
	public void setDomain(int width, int height, int length)
	{
		this.domainW = width;
		this.domainH = height;
		this.domainL = length;
		this.scaleInDomain();
	}
	
	public void setPos(float x, float y, float z)
	{
		this.xpos = x / (float) this.domainW;
		this.ypos = y / (float) this.domainH;
		this.zpos = z / (float) this.domainL;
	}
	
	protected void scaleInDomain()
	{
		this.vects[0].x = this.ivects[0].x / (float) this.domainW;
		this.vects[0].y = this.ivects[0].y / (float) this.domainH;
		this.vects[0].z = this.ivects[0].z / (float) this.domainL;
		this.vects[1].x = this.ivects[1].x / (float) this.domainW;
		this.vects[1].y = this.ivects[1].y / (float) this.domainH;
		this.vects[1].z = this.ivects[1].z / (float) this.domainL;
	}
	
	protected void fixLengths()
	{
		this.width = this.ivects[0].length();
		this.height = this.ivects[1].length();
	}
	
	public void plane(EnumDirection dir, int width, int height, int x, int y, int z)
	{
		this.setPos(x, y, z);
		this.ivects[0] = dir.getBadPlaneVector(width, 0.0F);
		this.ivects[1] = dir.getBadPlaneVector(0.0F, height);
		this.scaleInDomain();
		this.width = width;
		this.height = height;
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
}
