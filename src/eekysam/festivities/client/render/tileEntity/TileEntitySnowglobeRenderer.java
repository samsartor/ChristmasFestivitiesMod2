package eekysam.festivities.client.render.tileEntity;

import org.lwjgl.opengl.GL11;

import eekysam.festivities.Festivities;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntitySnowglobeRenderer extends TileEntitySpecialRenderer
{
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		Tessellator t = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		this.bindTexture(new ResourceLocation("festivities","textures/snowglobe/scene_testWorld.png"));
		GL11.glDisable(GL11.GL_CULL_FACE);
		t.startDrawingQuads();
		t.setColorOpaque_F(0.8F, 0.8F, 0.8F);
		
		float X = (1) / 16.0F;
		float Z = (1) / 16.0F;
		float W = (14) / 16.0F;
		float H = (14) / 16.0F;
		float v = (26) / 40.0F;
		float U = (14) / 98.0F;
		float V = (14) / 40.0F;
		
		for (int i = 0; i < 7; i++)
		{
			float Y = (i * 2 + 2) / 16.0F;
			float u = (14 * i) / 98.0F;
			
			t.addVertexWithUV(X, Y, Z, u, v);
			t.addVertexWithUV(X + W, Y, Z, u + U, v);
			t.addVertexWithUV(X + W, Y, Z + H, u + U, v + V);
			t.addVertexWithUV(X, Y, Z + H, u, v + V);
		}
		
		t.draw();
		t.startDrawingQuads();
		t.setColorOpaque_F(0.4F, 0.4F, 0.4F);
		
		float Y = (2) / 16.0F;
		X = (1) / 16.0F;
		W = (14) / 16.0F;
		H = (13) / 16.0F;
		v = (0) / 40.0F;
		U = (14) / 98.0F;
		V = (13) / 40.0F;
		
		for (int i = 0; i < 7; i++)
		{
			Z = (i * 2 + 2) / 16.0F;
			float u = (14 * i) / 98.0F;
			
			t.addVertexWithUV(X, Y + H, Z, u, v);
			t.addVertexWithUV(X + W, Y + H, Z, u + U, v);
			t.addVertexWithUV(X + W, Y, Z, u + U, v + V);
			t.addVertexWithUV(X, Y, Z, u, v + V);
		}
		
		t.draw();
		t.startDrawingQuads();
		t.setColorOpaque_F(0.6F, 0.6F, 0.6F);
		
		Y = (2) / 16.0F;
		Z = (1) / 16.0F;
		W = (14) / 16.0F;
		H = (13) / 16.0F;
		v = (13) / 40.0F;
		U = (14) / 98.0F;
		V = (13) / 40.0F;
		
		for (int i = 0; i < 7; i++)
		{
			X = (i * 2 + 2) / 16.0F;
			float u = (14 * i) / 98.0F;
			
			t.addVertexWithUV(X, Y + H, Z + W, u, v);
			t.addVertexWithUV(X, Y + H, Z, u + U, v);
			t.addVertexWithUV(X, Y, Z, u + U, v + V);
			t.addVertexWithUV(X, Y, Z + W, u, v + V);
		}
		
		t.draw();
		GL11.glPopMatrix();
	}
}
