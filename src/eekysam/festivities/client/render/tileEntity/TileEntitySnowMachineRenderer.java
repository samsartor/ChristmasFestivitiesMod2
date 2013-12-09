package eekysam.festivities.client.render.tileEntity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import eekysam.festivities.Festivities;
import eekysam.festivities.tile.TileEntitySnowMachine;
import eekysam.utils.draw.BoxDrawBasic;
import eekysam.utils.draw.IRenderer;

public class TileEntitySnowMachineRenderer extends TileEntitySpecialRenderer implements IRenderer
{
	@Override
	public void rendererBindTexture(ResourceLocation loc)
	{
		this.bindTexture(loc);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f)
	{
		Tessellator tess = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslated(d0, d1, d2);

		GL11.glDisable(GL11.GL_CULL_FACE);

		this.renderSnowMachine((TileEntitySnowMachine) tileentity);

		GL11.glPopMatrix();
	}

	public void renderSnowMachine(TileEntitySnowMachine tile)
	{
		Tessellator tess = Tessellator.instance;
		BoxDrawBasic draw = new BoxDrawBasic(this);
		draw.setTexture(Festivities.ID, "textures/tile/snowMachine.png", 36, 36);
		tess.startDrawingQuads();

		draw.cube(0, 11, 0, 16, 5, 16);
		draw.selectUV(16, 0);
		draw.drawSidesSameTexture();
		draw.selectUV(0, 16);
		draw.YDown();
		draw.selectUV(0, 0);
		draw.YUp();

		draw.cube(5, 3, 5, 6, 8, 6);
		draw.selectUV(30, 5);
		draw.drawSidesSameTexture();

		draw.cube(1, 0, 1, 14, 3, 14);
		draw.selectUV(16, 5);
		draw.YUp();
		draw.selectUV(16, 19);
		draw.YDown();

		if (tile.isPowered())
		{
			draw.selectUV(16, 33);
		}
		else
		{
			draw.selectUV(2, 33);
		}

		draw.drawSidesSameTexture();

		tess.draw();
	}

	@Override
	public int getAnimNum()
	{
		return 0;
	}
}
