package eekysam.festivities.client.render.block;

import net.minecraft.client.renderer.Tessellator;
import eekysam.festivities.Festivities;
import eekysam.festivities.item.ItemOrnament;
import eekysam.utils.draw.BoxDrawBasic;
import eekysam.utils.draw.IRenderer;

public class FestivitiesBlockRenderer
{
	public static void render(IRenderer render, int renderid, int id, int meta, int x, int y, int z)
	{
		switch (renderid)
		{
			case 1:
				renderOrnament(render, id, meta, x, y, z);
				return;
		}
	}
	
	public static void renderOrnament(IRenderer render, int id, int meta, int x, int y, int z)
	{
		Tessellator tess = Tessellator.instance;
		BoxDrawBasic draw = new BoxDrawBasic(render);
		draw.setTexture(Festivities.ID, "textures/blocks/ornament.png", 64, 32);
		tess.startDrawingQuads();
		
		boolean clear = id == Festivities.clearOrnamentBlock.blockID && id != Festivities.coloredOrnamentBlock.blockID;
		
        long poshash = (long)(x * 3129871) ^ (long)y * 116129781L ^ (long)z;
        poshash = poshash * poshash * 42317861L + poshash * 11L;
        int dir = (int)(poshash >> 16 & 3L);
        
		draw.cube(3, 3, 3, 10, 10, 10);
		
		if (clear)
		{
			draw.selectUV(0, 0);
			draw.YUp();
			draw.selectUV(10, 0);
			draw.YDown();

			draw.selectUV((dir == 0 ? 10 : 0), 10);
			draw.ZDown();
			draw.selectUV((dir == 1 ? 10 : 0), 10);
			draw.ZUp();
			draw.selectUV((dir == 2 ? 10 : 0), 10);
			draw.XDown();
			draw.selectUV((dir == 3 ? 10 : 0), 10);
			draw.XUp();
		}
		else
		{
			tess.setColorOpaque_I(ItemOrnament.ornamentColors[meta]);
			
			draw.selectUV(20, 0);
			draw.YUp();
			draw.selectUV(30, 0);
			draw.YDown();

			draw.selectUV((dir == 0 ? 30 : 20), 10);
			draw.ZDown();
			draw.selectUV((dir == 1 ? 30 : 20), 10);
			draw.ZUp();
			draw.selectUV((dir == 2 ? 30 : 20), 10);
			draw.XDown();
			draw.selectUV((dir == 3 ? 30 : 20), 10);
			draw.XUp();
		}
		
		tess.draw();
		tess.startDrawingQuads();
		
		tess.setColorOpaque_I(0xFFFFFF);
		
		draw.selectUV(40, 0);
		draw.YUp();
		draw.selectUV(50, 0);
		draw.YDown();

		draw.selectUV((dir == 0 ? 50 : 40), 10);
		draw.ZDown();
		draw.selectUV((dir == 1 ? 50 : 40), 10);
		draw.ZUp();
		draw.selectUV((dir == 2 ? 50 : 40), 10);
		draw.XDown();
		draw.selectUV((dir == 3 ? 50 : 40), 10);
		draw.XUp();
		
		tess.draw();
	}
}
