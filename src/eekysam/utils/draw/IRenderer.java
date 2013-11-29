package eekysam.utils.draw;

import net.minecraft.util.ResourceLocation;

public interface IRenderer
{
	public void rendererBindTexture(ResourceLocation loc);
	
	public int getAnimNum();
}
