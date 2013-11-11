package eekysam.festivities.client.render.tileEntity;

import org.lwjgl.opengl.GL11;

import eekysam.festivities.Festivities;
import eekysam.festivities.tile.TileEntityPlate;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityPlateRenderer extends TileEntitySpecialRenderer
{
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glDisable(GL11.GL_CULL_FACE);
		this.render((TileEntityPlate) tileentity);
		GL11.glPopMatrix();
	}
	
	public void render(TileEntityPlate tile)
	{
		Tessellator t = Tessellator.instance;
	}
	
	public void renderFiggy(float x, float y, float z)
	{
		Tessellator t = Tessellator.instance;
		this.bindTexture(new ResourceLocation(Festivities.ID, "textures/tile/plate_figgy.png"));
		t.startDrawingQuads();
		
		t.draw();
	}
	
	public void renderPie(float x, float y, float z, String texture)
	{
		Tessellator t = Tessellator.instance;
		this.bindTexture(new ResourceLocation(Festivities.ID, "textures/tile/plate_" + texture + ".png"));
		t.startDrawingQuads();
		
		t.draw();
	}
	
	public void renderCookie(float x, float y, float z, int type, int texture)
	{
		Tessellator t = Tessellator.instance;
		this.bindTexture(new ResourceLocation(Festivities.ID, "textures/tile/plate_cookie.png"));
		t.startDrawingQuads();
		
		float X = 3 / 16.0F;
		float Y = 1 / 16.0F;
		float Z = 3 / 16.0F;
		float u = (type * 3) / 16.0F;
		float v = (texture * 3) / 16.0F;
		float U = 3 / 16.0F;
		float V = 3 / 16.0F;
		
		t.addVertexWithUV(x + X, y + Y, z + Z, u, v);
		t.addVertexWithUV(x + X, y + Y, z, u + U, v);
		t.addVertexWithUV(x, y + Y, z, u + U, v + V);
		t.addVertexWithUV(x, y + Y, z + Z, u, v + V);
		
		v = 13 / 16.0F;
		
		t.addVertexWithUV(x + X, y, z + Z, u, v);
		t.addVertexWithUV(x + X, y, z, u + U, v);
		t.addVertexWithUV(x, y, z, u + U, v + V);
		t.addVertexWithUV(x, y, z + Z, u, v + V);
		
		v = 12 / 16.0F;
		V = 1 / 16.0F;
		
		t.addVertexWithUV(x, y, z, u, v);
		t.addVertexWithUV(x + X, y, z, u + U, v);
		t.addVertexWithUV(x + X, y + Y, z, u + U, v + V);
		t.addVertexWithUV(x, y + Y, z, u, v + V);

		t.addVertexWithUV(x, y, z + Z, u, v);
		t.addVertexWithUV(x + X, y, z + Z, u + U, v);
		t.addVertexWithUV(x + X, y + Y, z + Z, u + U, v + V);
		t.addVertexWithUV(x, y + Y, z + Z, u, v + V);

		t.addVertexWithUV(x, y, z, u, v);
		t.addVertexWithUV(x, y, z + Z, u + U, v);
		t.addVertexWithUV(x, y + Y, z + Z, u + U, v + V);
		t.addVertexWithUV(x, y + Y, z, u, v + V);

		t.addVertexWithUV(x + X, y, z, u, v);
		t.addVertexWithUV(x + X, y, z + Z, u + U, v);
		t.addVertexWithUV(x + X, y + Y, z + Z, u + U, v + V);
		t.addVertexWithUV(x + X, y + Y, z, u, v + V);
		
		t.draw();
	}
}
