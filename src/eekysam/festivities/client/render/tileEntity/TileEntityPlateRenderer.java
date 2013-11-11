package eekysam.festivities.client.render.tileEntity;

import org.lwjgl.opengl.GL11;

import eekysam.festivities.Festivities;
import eekysam.festivities.tile.TileEntityPlate;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import eekysam.utils.draw.BoxDrawBasic;
import eekysam.utils.draw.IRenderer;

public class TileEntityPlateRenderer extends TileEntitySpecialRenderer implements IRenderer
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
	
	public void renderFiggy(int x, int y, int z)
	{
		BoxDrawBasic draw = new BoxDrawBasic(this);
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
		
		draw.setPos(x + 4, y + 5, z + 3);
		draw.drawAllNormalTextureShape();
		
		draw.setPos(x + 3, y + 5, z + 4);
		draw.drawAllNormalTextureShape();
		
		float u = (16) / 24.0F;
		float v = (3) / 11.0F;
		float U = (3) / 24.0F;
		float V = (3) / 11.0F;
		float leafx = (3 + x) / 16.0F;
		float leafy = (6 + y) / 16.0F;
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
		BoxDrawBasic draw = new BoxDrawBasic(this);
		Tessellator t = Tessellator.instance;
		draw.setTexture(Festivities.ID, "textures/tile/plate_" + texture + ".png", 32, 16);
		t.startDrawingQuads();
		
		draw.setPos(x, y, z);
		draw.cube(8, 4, 8);
		draw.selectUV(0, 4);
		draw.drawAllLeftJustTextureShape(false);
		
		draw.setPos(x + 1, y + 4, z + 1);
		draw.cube(6, 1, 6);
		draw.faceIn();
		draw.selectUV(0, 3);
		draw.drawSidesGroupedTexture();
		draw.selectUV(16, 6);
		draw.YDown();

		t.draw();
	}
	
	public void renderCookie(int x, int y, int z, int type, int texture)
	{
		BoxDrawBasic draw = new BoxDrawBasic(this);
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
