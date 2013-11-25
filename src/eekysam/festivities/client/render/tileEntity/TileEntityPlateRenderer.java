package eekysam.festivities.client.render.tileEntity;

import org.lwjgl.opengl.GL11;

import eekysam.festivities.Festivities;
import eekysam.festivities.tile.TileEntityPlate;
import eekysam.festivities.tile.TileEntityPlate.PlateFoods;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import eekysam.utils.draw.BoxDrawBasic;
import eekysam.utils.draw.BoxDrawFakeShade;
import eekysam.utils.draw.IRenderer;

public class TileEntityPlateRenderer extends TileEntitySpecialRenderer implements IRenderer
{
	public static final short[] cookieXPos = new short[] {6,7,2,4,10,5,8,2,3,5,3,10,9,5,10,10,7,7,1,3};
	public static final short[] cookieYPos = new short[] {0,0,0,1,0,0,1,0,1,2,0,0,1,1,2,0,2,3,1,2};
	public static final short[] cookieZPos = new short[] {4,8,7,6,3,1,3,3,3,5,11,7,7,10,5,10,8,6,9,8};
	
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.render((TileEntityPlate) tileentity);
		GL11.glPopMatrix();
	}
	
	public void render(TileEntityPlate tile)
	{
		Tessellator t = Tessellator.instance;
		int cookieAt = 0;
		int figgyAt = 0;
		int pieAt = 0;
		for (PlateFoods item : tile.onPlate)
		{
			if (item.isCookie)
			{
				this.renderCookie(1 + this.cookieXPos[cookieAt], 1 + this.cookieYPos[cookieAt], 1 + this.cookieZPos[cookieAt], item, 0);
				cookieAt++;
			}
			if (item == PlateFoods.Figgy)
			{
				if (figgyAt == 0)
				{
					this.renderFiggy(1, 1, 1);
				}
				if (figgyAt == 1)
				{
					this.renderFiggy(8, 1, 8);
				}
				figgyAt++;
			}
			if (item == PlateFoods.BluPie)
			{
				this.renderPie(4, 1, 4, "blu");
				pieAt++;
			}
			if (item == PlateFoods.PmkPie)
			{
				this.renderPie(4, 1, 4, "ppk");
				pieAt++;
			}
		}
		
		BoxDrawBasic draw = new BoxDrawFakeShade(this);
		draw.setTexture(Festivities.ID, "textures/tile/dish.png", 64, 32);
		t.startDrawingQuads();
		
		draw.cube(0, 0, 0, 16, 3, 16);
		draw.selectUV(0, 0);
		draw.YUp();
		draw.selectUV(16, 0);
		draw.YDown();
		draw.selectUV(32, 0);
		draw.XUp();
		draw.selectUV(32, 3);
		draw.ZUp();
		draw.selectUV(32, 6);
		draw.XDown();
		draw.selectUV(32, 9);
		draw.ZDown();
		
		draw.cube(1, 1, 1, 14, 2, 14);
		draw.faceIn();
		draw.selectUV(0, 16);
		draw.YDown();
		draw.selectUV(14, 16);
		draw.XUp();
		draw.selectUV(14, 18);
		draw.ZUp();
		draw.selectUV(14, 20);
		draw.XDown();
		draw.selectUV(14, 22);
		draw.ZDown();
		
		t.draw();
	}
	
	public void renderFiggy(int x, int y, int z)
	{
		BoxDrawBasic draw = new BoxDrawFakeShade(this);
		Tessellator t = Tessellator.instance;
		draw.setTexture(Festivities.ID, "textures/tile/plate_figgy.png", 24, 11);
		t.startDrawingQuads();
		
		draw.setPos(x, y, z);
		draw.cube(6, 5, 6);
		draw.selectUV(0, 0);
		draw.drawAllLeftJustTextureShape(true);
		
		draw.setPos(x + 3, y + 6, z + 3);
		draw.cube(1, 1, 1);
		draw.selectUV(12, 4);
		draw.drawAllNormalTextureShape();
		
		draw.setPos(x + 3, y + 5, z + 2);
		draw.drawAllNormalTextureShape();
		
		draw.setPos(x + 2, y + 5, z + 3);
		draw.drawAllNormalTextureShape();
		
		float u = (16) / 24.0F;
		float v = (3) / 11.0F;
		float U = (3) / 24.0F;
		float V = (3) / 11.0F;
		float leafx = (3 + x) / 16.0F;
		float leafy = (5.5F + y) / 16.0F;
		float leafz = (3 + z) / 16.0F;
		float X = (3) / 16.0F;
		float Z = (3) / 16.0F;
		
		t.addVertexWithUV(leafx, leafy, leafz, u, v + V);
		t.addVertexWithUV(leafx + X, leafy, leafz, u, v);
		t.addVertexWithUV(leafx + X, leafy, leafz + Z, u + U, v);
		t.addVertexWithUV(leafx, leafy, leafz + Z, u + U, v + V);
		
		t.draw();
	}
	
	public void renderPie(int x, int y, int z, String texture)
	{
		BoxDrawBasic draw = new BoxDrawFakeShade(this);
		Tessellator t = Tessellator.instance;
		draw.setTexture(Festivities.ID, "textures/tile/plate_pie_" + texture + ".png", 32, 16);
		t.startDrawingQuads();
		
		draw.setPos(x, y, z);
		draw.cube(8, 4, 8);
		draw.selectUV(0, 4);
		draw.drawAllLeftJustTextureShape(false);
		
		draw.setPos(x + 1, y + 3, z + 1);
		draw.cube(6, 1, 6);
		draw.faceIn();
		draw.selectUV(0, 3);
		draw.drawSidesGroupedTexture();
		draw.selectUV(16, 6);
		draw.YDown();

		t.draw();
	}
	
	public void renderCookie(int x, int y, int z, PlateFoods type, int texture)
	{
		int t = -1;
	    switch (type)
		{
		    case ChipCookie:
		    	t = 0;
		    	break;
		    case SugarCookie:
		    	t = 1;
		    	break;
		    case ChocCookie:
		    	t = 2;
		    	break;
		    case SprinkCookie:
		    	t = 3;
		    	break;
		    case CandyCookie:
		    	t = 4;
		    	break;
		    default:
		    	break;
		}
		this.renderCookie(x, y, z, t, texture);
	}
		
	public void renderCookie(int x, int y, int z, int type, int texture)
	{
		BoxDrawBasic draw = new BoxDrawFakeShade(this);
		Tessellator t = Tessellator.instance;
		draw.setTexture(Festivities.ID, "textures/tile/plate_cookie.png", 16, 16);
		t.startDrawingQuads();
		draw.setPos(x, y, z);
		draw.selectUV(type * 3, texture * 3);
		draw.cube(3, 1, 3);
		draw.YUp();
		draw.selectV(13);
		draw.YDown();
		draw.selectV(12);
		draw.drawSidesSameTexture();
		t.draw();
	}

	public void rendererBindTexture(ResourceLocation loc)
	{
		this.bindTexture(loc);
	}
}
