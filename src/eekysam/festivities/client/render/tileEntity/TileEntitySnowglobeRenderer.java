package eekysam.festivities.client.render.tileEntity;

import org.lwjgl.opengl.GL11;

import eekysam.festivities.Festivities;
import eekysam.festivities.tile.TileEntitySnowglobe;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntitySnowglobeRenderer extends TileEntitySpecialRenderer
{
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float k)
	{
		Tessellator t = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		TileEntitySnowglobe globe = (TileEntitySnowglobe) tile;
		
        float f = globe.blockType.getBlockBrightness(globe.worldObj, globe.xCoord, globe.yCoord, globe.zCoord);
        int l = globe.worldObj.getLightBrightnessForSkyBlocks(globe.xCoord, globe.yCoord, globe.zCoord, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)l1, (float)l2); 
        
        if (globe.type > 8)
        {
        	this.bindTexture(new ResourceLocation("festivities","textures/snowglobe/scene_candyWorld.png"));
        }
        else
        {
        	this.bindTexture(new ResourceLocation("festivities","textures/snowglobe/scene_snowWorld.png"));
        }
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		t.startDrawingQuads();
		//t.setColorOpaque_F(1.0F * f, 1.0F * f, 1.0F * f);
		
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
		//t.setColorOpaque_F(1.0F * f, 1.0F * f, 1.0F * f);
		
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
		//t.setColorOpaque_F(1.0F * f, 1.0F * f, 1.0F * f);
		
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
		GL11.glEnable(GL11.GL_LIGHTING);
		this.bindTexture(new ResourceLocation("festivities","textures/snowglobe/globe.png"));
		t.startDrawingQuads();
		//t.setColorOpaque_F(1.0F * f, 1.0F * f, 1.0F * f);
		
		X = (0) / 16.0F;
		Y = (0) / 16.0F;
		Z = (0) / 16.0F;
		W = (16) / 16.0F;
		float L = (16) / 16.0F;
		H = (3) / 16.0F;
		
		float u = (16) / 64.0F;
		v = (0) / 32.0F;
		U = (16) / 64.0F;
		V = (16) / 32.0F;
		
		t.addVertexWithUV(X + L, Y, Z + W, u, v);
		t.addVertexWithUV(X + L, Y, Z, u + U, v);
		t.addVertexWithUV(X, Y, Z, u + U, v + V);
		t.addVertexWithUV(X, Y, Z + W, u, v + V);
		
		u = (0) / 64.0F;
		v = (0) / 32.0F;
		U = (16) / 64.0F;
		V = (16) / 32.0F;
		
		t.addVertexWithUV(X + L, Y + H, Z + W, u, v);
		t.addVertexWithUV(X + L, Y + H, Z, u + U, v);
		t.addVertexWithUV(X, Y + H, Z, u + U, v + V);
		t.addVertexWithUV(X, Y + H, Z + W, u, v + V);
		
		int met = tile.worldObj.getBlockMetadata(globe.xCoord, globe.yCoord, globe.zCoord);
		
		float u0 = (0) / 64.0F;
		float u1 = (16) / 64.0F;
		
		v = (16) / 32.0F;
		U = (16) / 64.0F;
		V = (3) / 32.0F;
		
		u = (met == 2 ? u0 : u1);
		t.addVertexWithUV(X, Y, Z, u, v);
		t.addVertexWithUV(X + L, Y, Z, u + U, v);
		t.addVertexWithUV(X + L, Y + H, Z, u + U, v + V);
		t.addVertexWithUV(X, Y + H, Z, u, v + V);
		
		u = (met == 3 ? u0 : u1);
		t.addVertexWithUV(X, Y, Z + W, u, v);
		t.addVertexWithUV(X + L, Y, Z + W, u + U, v);
		t.addVertexWithUV(X + L, Y + H, Z + W, u + U, v + V);
		t.addVertexWithUV(X, Y + H, Z + W, u, v + V);
		
		u = (met == 4 ? u0 : u1);
		t.addVertexWithUV(X, Y, Z, u, v);
		t.addVertexWithUV(X, Y, Z + L, u + U, v);
		t.addVertexWithUV(X, Y + H, Z + L, u + U, v + V);
		t.addVertexWithUV(X, Y + H, Z, u, v + V);
		
		u = (met == 5 ? u0 : u1);
		t.addVertexWithUV(X + W, Y, Z, u, v);
		t.addVertexWithUV(X + W, Y, Z + L, u + U, v);
		t.addVertexWithUV(X + W, Y + H, Z + L, u + U, v + V);
		t.addVertexWithUV(X + W, Y + H, Z, u, v + V);
		
		X = (1) / 16.0F;
		Y = (2) / 16.0F;
		Z = (1) / 16.0F;
		W = (14) / 16.0F;
		L = (14) / 16.0F;
		H = (13) / 16.0F;
		
		u = (46) / 64.0F;
		v = (0) / 32.0F;
		U = (14) / 64.0F;
		V = (14) / 32.0F;
		
		t.addVertexWithUV(X + L, Y + H, Z + W, u, v);
		t.addVertexWithUV(X + L, Y + H, Z, u + U, v);
		t.addVertexWithUV(X, Y + H, Z, u + U, v + V);
		t.addVertexWithUV(X, Y + H, Z + W, u, v + V);
		
		u = (32) / 64.0F;
		v = (0) / 32.0F;
		U = (14) / 64.0F;
		V = (13) / 32.0F;
		
		t.addVertexWithUV(X, Y, Z, u, v);
		t.addVertexWithUV(X + L, Y, Z, u + U, v);
		t.addVertexWithUV(X + L, Y + H, Z, u + U, v + V);
		t.addVertexWithUV(X, Y + H, Z, u, v + V);
		
		t.addVertexWithUV(X, Y, Z, u, v);
		t.addVertexWithUV(X, Y, Z + L, u + U, v);
		t.addVertexWithUV(X, Y + H, Z + L, u + U, v + V);
		t.addVertexWithUV(X, Y + H, Z, u, v + V);
		
		t.addVertexWithUV(X + W, Y, Z, u, v);
		t.addVertexWithUV(X + W, Y, Z + L, u + U, v);
		t.addVertexWithUV(X + W, Y + H, Z + L, u + U, v + V);
		t.addVertexWithUV(X + W, Y + H, Z, u, v + V);
		
		t.addVertexWithUV(X, Y, Z + W, u, v);
		t.addVertexWithUV(X + L, Y, Z + W, u + U, v);
		t.addVertexWithUV(X + L, Y + H, Z + W, u + U, v + V);
		t.addVertexWithUV(X, Y + H, Z + W, u, v + V);
		
		t.draw();
		GL11.glPopMatrix();
	}
}
