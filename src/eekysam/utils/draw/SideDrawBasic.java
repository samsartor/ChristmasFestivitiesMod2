package eekysam.utils.draw;

public class SideDrawBasic extends SideDraw
{
	public SideDrawBasic(IRenderer parent)
	{
		super(parent);
	}

	@Override
	public void draw()
	{
		switch (this.dir)
		{
			case XUp:
				this.XUp();
				return;
			case XDown:
				this.XDown();
				return;
			case YUp:
				this.YUp();
				return;
			case YDown:
				this.YDown();
				return;
			case ZUp:
				this.ZUp();
				return;
			case ZDown:
				this.ZDown();
				return;
		}
	}

	protected void XUp()
	{
		float u2;
		float v2;
		float u1 = 0;
		float v1 = 0;
		if (this.rotUVWorldMapping)
		{
			u2 = this.iheight / (float) this.textureWidth;
			v2 = this.iwidth / (float) this.textureHeight;
			if (this.flipU)
			{
				u1 = u2;
				u2 = 0;
			}
			if (this.flipV)
			{
				v1 = v2;
				v2 = 0;
			}
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU + u2, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos, this.textureU + u1, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos + this.width, this.textureU + u1, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos + this.width, this.textureU + u2, this.textureV + v2);
		}
		else
		{
			u2 = this.iwidth / (float) this.textureWidth;
			v2 = this.iheight / (float) this.textureHeight;
			if (this.flipU)
			{
				u1 = u2;
				u2 = 0;
			}
			if (this.flipV)
			{
				v1 = v2;
				v2 = 0;
			}
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU + u1, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos, this.textureU + u1, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos + this.width, this.textureU + u2, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos + this.width, this.textureU + u2, this.textureV + v2);
		}
	}

	protected void XDown()
	{
		float u2;
		float v2;
		float u1 = 0;
		float v1 = 0;
		if (this.rotUVWorldMapping)
		{
			u2 = this.iheight / (float) this.textureWidth;
			v2 = this.iwidth / (float) this.textureHeight;
			if (this.flipU)
			{
				u1 = u2;
				u2 = 0;
			}
			if (this.flipV)
			{
				v1 = v2;
				v2 = 0;
			}
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU + u2, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos, this.textureU + u1, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos + this.width, this.textureU + u1, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos + this.width, this.textureU + u2, this.textureV + v2);
		}
		else
		{
			u2 = this.iwidth / (float) this.textureWidth;
			v2 = this.iheight / (float) this.textureHeight;
			if (this.flipU)
			{
				u1 = u2;
				u2 = 0;
			}
			if (this.flipV)
			{
				v1 = v2;
				v2 = 0;
			}
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU + u1, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos, this.textureU + u1, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos + this.width, this.textureU + u2, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos + this.width, this.textureU + u2, this.textureV + v2);
		}
	}

	protected void YUp()
	{
		float u2;
		float v2;
		float u1 = 0;
		float v1 = 0;
		if (this.rotUVWorldMapping)
		{
			u2 = this.iheight / (float) this.textureWidth;
			v2 = this.iwidth / (float) this.textureHeight;
			if (this.flipU)
			{
				u1 = u2;
				u2 = 0;
			}
			if (this.flipV)
			{
				v1 = v2;
				v2 = 0;
			}
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU + u1, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos + this.height, this.textureU + u2, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos + this.height, this.textureU + u2, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos, this.textureU + u1, this.textureV + v2);
		}
		else
		{
			u2 = this.iwidth / (float) this.textureWidth;
			v2 = this.iheight / (float) this.textureHeight;
			if (this.flipU)
			{
				u1 = u2;
				u2 = 0;
			}
			if (this.flipV)
			{
				v1 = v2;
				v2 = 0;
			}
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU + u1, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos + this.height, this.textureU + u1, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos + this.height, this.textureU + u2, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos, this.textureU + u2, this.textureV + v1);
		}
	}

	protected void YDown()
	{
		float u2;
		float v2;
		float u1 = 0;
		float v1 = 0;
		if (this.rotUVWorldMapping)
		{
			u2 = this.iheight / (float) this.textureWidth;
			v2 = this.iwidth / (float) this.textureHeight;
			if (this.flipU)
			{
				u1 = u2;
				u2 = 0;
			}
			if (this.flipV)
			{
				v1 = v2;
				v2 = 0;
			}
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU + u1, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos + this.height, this.textureU + u2, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos + this.height, this.textureU + u2, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos, this.textureU + u1, this.textureV + v2);
		}
		else
		{
			u2 = this.iwidth / (float) this.textureWidth;
			v2 = this.iheight / (float) this.textureHeight;
			if (this.flipU)
			{
				u1 = u2;
				u2 = 0;
			}
			if (this.flipV)
			{
				v1 = v2;
				v2 = 0;
			}
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU + u1, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos + this.height, this.textureU + u1, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos + this.height, this.textureU + u2, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos, this.textureU + u2, this.textureV + v1);
		}
	}

	protected void ZUp()
	{
		float u2;
		float v2;
		float u1 = 0;
		float v1 = 0;
		if (this.rotUVWorldMapping)
		{
			u2 = this.iheight / (float) this.textureWidth;
			v2 = this.iwidth / (float) this.textureHeight;
			if (this.flipU)
			{
				u1 = u2;
				u2 = 0;
			}
			if (this.flipV)
			{
				v1 = v2;
				v2 = 0;
			}
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU + u2, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos, this.textureU + u1, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos + this.height, this.zpos, this.textureU + u1, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos, this.textureU + u2, this.textureV + v2);
		}
		else
		{
			u2 = this.iwidth / (float) this.textureWidth;
			v2 = this.iheight / (float) this.textureHeight;
			if (this.flipU)
			{
				u1 = u2;
				u2 = 0;
			}
			if (this.flipV)
			{
				v1 = v2;
				v2 = 0;
			}
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU + u1, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos, this.textureU + u1, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos + this.height, this.zpos, this.textureU + u2, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos, this.textureU + u2, this.textureV + v2);
		}
	}

	protected void ZDown()
	{
		float u2;
		float v2;
		float u1 = 0;
		float v1 = 0;
		if (this.rotUVWorldMapping)
		{
			u2 = this.iheight / (float) this.textureWidth;
			v2 = this.iwidth / (float) this.textureHeight;
			if (this.flipU)
			{
				u1 = u2;
				u2 = 0;
			}
			if (this.flipV)
			{
				v1 = v2;
				v2 = 0;
			}
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU + u2, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos, this.textureU + u1, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos + this.height, this.zpos, this.textureU + u1, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos, this.textureU + u2, this.textureV + v2);
		}
		else
		{
			u2 = this.iwidth / (float) this.textureWidth;
			v2 = this.iheight / (float) this.textureHeight;
			if (this.flipU)
			{
				u1 = u2;
				u2 = 0;
			}
			if (this.flipV)
			{
				v1 = v2;
				v2 = 0;
			}
			this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU + u1, this.textureV + v2);
			this.tess.addVertexWithUV(this.xpos, this.ypos + this.height, this.zpos, this.textureU + u1, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos + this.height, this.zpos, this.textureU + u2, this.textureV + v1);
			this.tess.addVertexWithUV(this.xpos + this.width, this.ypos, this.zpos, this.textureU + u2, this.textureV + v2);
		}
	}
}
