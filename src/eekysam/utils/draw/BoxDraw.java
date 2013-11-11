abstract class Sketcher
{
	private int domainW = 16;
	private int domainH = 16;
	private int domainL = 16;
	
	private float xpos;
	private float ypos;
	private float zpos;
	
	private float width;
	private float height;
	private float length;
	
	private int textureWidth;
	private int textureHeight;
	
	private float textureU;
	private float textureV;
	
	public void setPos(int x, int y, int z)
	{
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
		this.bindTexture(new ResourceLocation(id, texture));
	}
	
	public abstract void bindTexture(ResourceLocation loc);
	
	public void selectUV(int u, int v)
	{
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
		this.XUp();
		this.textureU += this.width / (float) textureWidth;
		this.ZUp();
		this.textureU += this.length / (float) textureWidth;
		this.XDown();
		this.textureU += this.width / (float) textureWidth;
		this.ZDown();
	}
	
	public void drawAllNormalTextureShape()
	{
		this.textureU += this.width / (float) textureWidth;
		this.YUp();
		this.textureU += this.width / (float) textureWidth;
		this.YDown();
		this.textureU -= 2 * (this.width / (float) textureWidth);
		this.textureV += this.height / (float) textureHeight;
		this.XUp();
		this.textureU += this.width / (float) textureWidth;
		this.ZUp();
		this.textureU += this.length / (float) textureWidth;
		this.XDown();
		this.textureU += this.width / (float) textureWidth;
		this.ZDown();
	}
}
