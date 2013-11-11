package eekysam.festivities.client.render.tileEntity;

import org.lwjgl.opengl.GL11;

import eekysam.festivities.Festivities;
import eekysam.festivities.tile.SnowglobeScene;
import eekysam.festivities.tile.TileEntitySnowglobe;
import eekysam.utils.draw.BoxDrawBasic;
import eekysam.utils.draw.IRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntitySnowglobeRenderer extends TileEntitySpecialRenderer implements IRenderer
{
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float k)
	{
		Tessellator tess = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		TileEntitySnowglobe globe = (TileEntitySnowglobe) tile;

		float f = globe.blockType.getBlockBrightness(globe.worldObj, globe.xCoord, globe.yCoord, globe.zCoord);
		int l = globe.worldObj.getLightBrightnessForSkyBlocks(globe.xCoord, globe.yCoord, globe.zCoord, 0);
		int l1 = l % 65536;
		int l2 = l / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) l1, (float) l2);

		float X;
		float Y;
		float Z;
		float W;
		float L;
		float H;
		float u;
		float v;
		float U;
		float V;
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		if (globe.type >= 0 && globe.type < SnowglobeScene.list.size())
		{
			this.bindTexture(SnowglobeScene.list.get(globe.type).getResource());
			GL11.glDisable(GL11.GL_LIGHTING);

			X = (1) / 16.0F;
			Z = (1) / 16.0F;
			W = (14) / 16.0F;
			H = (14) / 16.0F;
			v = (26) / 40.0F;
			U = (14) / 98.0F;
			V = (14) / 40.0F;
			
			tess.startDrawingQuads();
			// t.setColorOpaque_F(1.0F * f, 1.0F * f, 1.0F * f);

			for (int i = 0; i < 7; i++)
			{
				Y = (i * 2 + 2) / 16.0F;
				u = (14 * i) / 98.0F;

				tess.addVertexWithUV(X, Y, Z, u, v);
				tess.addVertexWithUV(X + W, Y, Z, u + U, v);
				tess.addVertexWithUV(X + W, Y, Z + H, u + U, v + V);
				tess.addVertexWithUV(X, Y, Z + H, u, v + V);
			}

			tess.draw();
			tess.startDrawingQuads();
			// t.setColorOpaque_F(1.0F * f, 1.0F * f, 1.0F * f);

			Y = (2) / 16.0F;
			X = (1) / 16.0F;
			W = (14) / 16.0F;
			H = (13) / 16.0F;
			v = (0) / 40.0F;
			U = (14) / 98.0F;
			V = (13) / 40.0F;

			for (int i = 0; i < 7; i++)
			{
				Z = (i * 2 + 2) / 16.0F;
				u = (14 * i) / 98.0F;

				tess.addVertexWithUV(X, Y + H, Z, u, v);
				tess.addVertexWithUV(X + W, Y + H, Z, u + U, v);
				tess.addVertexWithUV(X + W, Y, Z, u + U, v + V);
				tess.addVertexWithUV(X, Y, Z, u, v + V);
			}

			tess.draw();
			tess.startDrawingQuads();
			// t.setColorOpaque_F(1.0F * f, 1.0F * f, 1.0F * f);

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
				u = (14 * i) / 98.0F;

				tess.addVertexWithUV(X, Y + H, Z + W, u, v);
				tess.addVertexWithUV(X, Y + H, Z, u + U, v);
				tess.addVertexWithUV(X, Y, Z, u + U, v + V);
				tess.addVertexWithUV(X, Y, Z + W, u, v + V);
			}

			tess.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
		}
		else
		{
			this.bindTexture(new ResourceLocation(Festivities.ID, "textures/snowglobe/base.png"));
			tess.startDrawingQuads();
			
			X = (1) / 16.0F;
			Y = (2) / 16.0F;
			Z = (1) / 16.0F;
			W = (14) / 16.0F;
			H = (14) / 16.0F;
			v = (0) / 40.0F;
			u = (0) / 40.0F;
			U = (14) / 14.0F;
			V = (14) / 14.0F;
			
			tess.addVertexWithUV(X + H, Y, Z + W, u, v);
			tess.addVertexWithUV(X + H, Y, Z, u + U, v);
			tess.addVertexWithUV(X, Y, Z, u + U, v + V);
			tess.addVertexWithUV(X, Y, Z + W, u, v + V);
			
			tess.draw();
		}
		
		BoxDrawBasic draw = new BoxDrawBasic(this);
		draw.setTexture(Festivities.ID, "textures/snowglobe/globe.png", 64, 32);
		tess.startDrawingQuads();
		// t.setColorOpaque_F(1.0F * f, 1.0F * f, 1.0F * f);

		draw.cube(0, 0, 0, 16, 3, 16);
		draw.selectUV(16, 0);
		draw.YDown();
		draw.selectUV(0, 0);
		draw.YUp();

		int met = tile.worldObj.getBlockMetadata(globe.xCoord, globe.yCoord, globe.zCoord);

		draw.selectUV((met == 2 ? 0 : 16), 16);
		draw.ZDown();
		draw.selectUV((met == 3 ? 0 : 16), 16);
		draw.ZUp();
		draw.selectUV((met == 4 ? 0 : 16), 16);
		draw.XDown();
		draw.selectUV((met == 5 ? 0 : 16), 16);
		draw.XUp();

		draw.cube(1, 2, 1, 14, 13, 14);
		draw.selectUV(46, 0);
		draw.YUp();
		draw.selectUV(32, 0);
		draw.drawSidesSameTexture();

		tess.draw();
		GL11.glPopMatrix();
	}
	
	public void rendererBindTexture(ResourceLocation loc)
	{
		this.bindTexture(loc);
	}
}
