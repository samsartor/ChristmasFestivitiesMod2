package eekysam.festivities.tileEntity;

import net.minecraft.tileentity.TileEntity;

public class TileEntityRenderEntity extends TileEntity
{
	public int renderID;
	
	public TileEntityRenderEntity(int render)
	{
		this.renderID = render;
	}
}
