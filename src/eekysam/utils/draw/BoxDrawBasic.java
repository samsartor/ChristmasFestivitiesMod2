package eekysam.utils.draw;

public class BoxDrawBasic extends BoxDraw
{
	public BoxDrawBasic(IRenderer parent)
	{
		super(parent);
	}

	public void XUp()
	{
		float u = this.ilength / (float) this.textureWidth;
		float v = this.iheight / (float) this.textureHeight;
		this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos, this.textureU, this.textureV + v);
		this.tess.addVertexWithUV(this.xpos + this.width, this.ypos + this.height, this.zpos, this.textureU, this.textureV);
		this.tess.addVertexWithUV(this.xpos + this.width, this.ypos + this.height, this.zpos + this.length, this.textureU + u, this.textureV);
		this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos + this.length, this.textureU + u, this.textureV + v);
	}

	public void XDown()
	{
		float u = this.ilength / (float) this.textureWidth;
		float v = this.iheight / (float) this.textureHeight;
		this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU, this.textureV + v);
		this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos, this.textureU, this.textureV);
		this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos + this.length, this.textureU + u, this.textureV);
		this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos + this.length, this.textureU + u, this.textureV + v);
	}

	public void YUp()
	{
		float u = this.iwidth / (float) this.textureWidth;
		float v = this.ilength / (float) this.textureHeight;
		this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos, this.textureU, this.textureV);
		this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos + this.length, this.textureU, this.textureV + v);
		this.tess.addVertexWithUV(this.xpos + this.width, this.ypos + this.height, this.zpos + this.length, this.textureU + u, this.textureV + v);
		this.tess.addVertexWithUV(this.xpos + this.width, this.ypos + this.height, this.zpos, this.textureU + u, this.textureV);
	}

	public void YDown()
	{
		float u = this.iwidth / (float) this.textureWidth;
		float v = this.ilength / (float) this.textureHeight;
		this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU, this.textureV);
		this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos + this.length, this.textureU, this.textureV + v);
		this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos + this.length, this.textureU + u, this.textureV + v);
		this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos, this.textureU + u, this.textureV);
	}

	public void ZUp()
	{
		float u = this.iwidth / (float) this.textureWidth;
		float v = this.iheight / (float) this.textureHeight;
		this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos + this.length, this.textureU, this.textureV + v);
		this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos + this.length, this.textureU, this.textureV);
		this.tess.addVertexWithUV(this.xpos + this.width, this.ypos + this.height, this.zpos + this.length, this.textureU + u, this.textureV);
		this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos + this.length, this.textureU + u, this.textureV + v);
	}

	public void ZDown()
	{
		float u = this.iwidth / (float) this.textureWidth;
		float v = this.iheight / (float) this.textureHeight;
		this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU, this.textureV + v);
		this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos, this.textureU, this.textureV);
		this.tess.addVertexWithUV(this.xpos + this.width, this.ypos + this.height, this.zpos, this.textureU + u, this.textureV);
		this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos, this.textureU + u, this.textureV + v);
	}
}
