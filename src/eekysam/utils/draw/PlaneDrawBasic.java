package eekysam.utils.draw;

import org.lwjgl.util.vector.Vector3f;

public class PlaneDrawBasic extends PlaneDraw
{
	public PlaneDrawBasic(IRenderer parent)
	{
		super(parent);
	}

	@Override
	public void draw()
	{
		float u = this.width / (float) this.textureWidth;
		float v = this.height / (float) this.textureHeight;
		this.tess.addVertexWithUV(this.xpos, this.ypos, this.zpos, this.textureU, this.textureV + v);
		Vector3f vect = this.vects[0];
		this.tess.addVertexWithUV(this.xpos + vect.x, this.ypos + vect.y, this.zpos + vect.z, this.textureU + u, this.textureV + v);
		vect = new Vector3f();
		vect = Vector3f.add(this.vects[0], this.vects[1], vect);
		this.tess.addVertexWithUV(this.xpos + vect.x, this.ypos + vect.y, this.zpos + vect.z, this.textureU + u, this.textureV);
		vect = this.vects[1];
		this.tess.addVertexWithUV(this.xpos + vect.x, this.ypos + vect.y, this.zpos + vect.z, this.textureU, this.textureV);
	}

}
