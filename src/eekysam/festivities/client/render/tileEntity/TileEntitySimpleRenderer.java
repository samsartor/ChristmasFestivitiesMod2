package eekysam.festivities.client.render.tileEntity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import eekysam.festivities.client.render.block.FestivitiesBlockRenderer;
import eekysam.festivities.tile.ISimpleTile;
import eekysam.festivities.tile.TileEntityPlate;
import eekysam.utils.draw.IRenderer;

public class TileEntitySimpleRenderer extends TileEntitySpecialRenderer implements IRenderer
{
	private int id;
	
	protected int anim = 0;
	
	public TileEntitySimpleRenderer(int id)
	{
		this.id = id;
	}
	
	public void rendererBindTexture(ResourceLocation loc)
	{
		this.bindTexture(loc);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f)
	{
		if (tile instanceof ISimpleTile)
		{
			this.anim = ((ISimpleTile) tile).getAnimNum();
		}
		
		Tessellator tess = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		FestivitiesBlockRenderer.render(this, this.id, tile.worldObj.getBlockId(tile.xCoord, tile.yCoord, tile.zCoord), tile.worldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord), tile.xCoord, tile.yCoord, tile.zCoord);
		
		GL11.glPopMatrix();
	}
	
	@Override
	public int getAnimNum()
	{
		return this.anim;
	}
}
