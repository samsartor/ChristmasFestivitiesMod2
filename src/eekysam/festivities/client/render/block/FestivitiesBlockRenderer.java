package eekysam.festivities.client.render.block;

import net.minecraft.client.renderer.Tessellator;
import eekysam.festivities.Festivities;
import eekysam.festivities.item.ItemOrnament;
import eekysam.utils.draw.BoxDrawBasic;
import eekysam.utils.draw.BoxDrawFakeShade;
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
			case 2:
				renderFireplace(render, id, meta, x, y, z);
				return;
		}
	}
	
	public static void renderFireplace(IRenderer render, int id, int meta, int x, int y, int z)
	{
		Tessellator tess = Tessellator.instance;
		BoxDrawBasic draw = new BoxDrawFakeShade(render);
		draw.setTexture(Festivities.ID, "textures/tile/fireplace.png", 16, 32);
		tess.startDrawingQuads();
		
		draw.cube(1, 0, 1, 14, 5, 14);
		draw.selectUV(0, 8);
		draw.faceIn();
		draw.drawSidesSameTexture();
		
		tess.draw();
		tess.startDrawingQuads();
		
		draw.faceOut();
		
		draw.cube(1, 0, 1, 14, 1, 14);
		draw.selectUV(0, 13);
		draw.YDown();
		draw.YUp();
		
		tess.draw();
		tess.startDrawingQuads();
		
		draw.setTexture(Festivities.ID, "textures/tile/burnlog.png", 8, 14);
		
		int a = render.getAnimNum();
		
		drawLog(draw, 5, 2, 4, 2, a);//(a + 1) % 4);
		drawLog(draw, 9, 2, 4, 2, a);//(a + 2) % 4);
		drawLog(draw, 4, 4, 7, 0, a);//(a + 0) % 4);
		
		tess.draw();
	}
	
	private static void drawLog(BoxDrawBasic draw, int x, int y, int z, int dir, int anim)
	{
		dir = dir % 3;
		int v = anim * 2;
		if (dir == 0)
		{
			draw.cube(x, y, z, 8, 2, 2);
			draw.selectUV(0, v);
			draw.YUp();
			draw.YDown();
			draw.ZUp();
			draw.ZDown();
			draw.selectUV(8, v);
			draw.XUp();
			draw.XDown();
		}
		if (dir == 1)
		{
			draw.cube(x, y, z, 2, 8, 2);
			draw.selectUV(0, v);
			draw.XUp();
			draw.XDown();
			draw.ZUp();
			draw.ZDown();
			draw.selectUV(8, v);
			draw.YUp();
			draw.YDown();
		}
		if (dir == 2)
		{
			draw.cube(x, y, z, 2, 2, 8);
			draw.selectUV(0, v);
			draw.setRotUVWorldMapping(true);
			draw.YUp();
			draw.YDown();
			draw.setRotUVWorldMapping(false);
			draw.XUp();
			draw.XDown();
			draw.selectUV(8, v);
			draw.ZUp();
			draw.ZDown();
		}
	}
	
	public static void renderOrnament(IRenderer render, int id, int meta, int x, int y, int z)
	{
		Tessellator tess = Tessellator.instance;
		BoxDrawBasic draw = new BoxDrawBasic(render);
		draw.setTexture(Festivities.ID, "textures/tile/ornament.png", 64, 32);
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
