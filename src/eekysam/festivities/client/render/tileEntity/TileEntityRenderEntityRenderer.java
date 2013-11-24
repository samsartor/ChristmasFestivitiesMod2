package eekysam.festivities.client.render.tileEntity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import eekysam.festivities.client.render.block.FestivitiesBlockRenderer;
import eekysam.festivities.tileEntity.TileEntityRenderEntity;
import eekysam.utils.draw.IRenderer;

public class TileEntityRenderEntityRenderer extends TileEntitySpecialRenderer implements IRenderer
{
	@Override
	public void rendererBindTexture(ResourceLocation loc)
	{
		this.bindTexture(loc);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		TileEntityRenderEntity r = (TileEntityRenderEntity) tileentity;
		int id = r.worldObj.getBlockId(r.xCoord, r.yCoord, r.zCoord);
		int meta = r.worldObj.getBlockMetadata(r.xCoord, r.yCoord, r.zCoord);
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		//float f = tileentity.blockType.getBlockBrightness(tileentity.worldObj, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
		int l = tileentity.worldObj.getLightBrightnessForSkyBlocks(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, 0);
		int l1 = l % 65536;
		int l2 = l / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) l1, (float) l2);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		FestivitiesBlockRenderer.render(this, r.renderID, id, meta, r.xCoord, r.yCoord, r.zCoord);
		GL11.glPopMatrix();
	}
}
