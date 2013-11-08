package eekysam.festivities.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import eekysam.festivities.EnumTheme;
import eekysam.festivities.Festivities;

public class SnowglobeScene
{
	public static List<SnowglobeScene> list = new ArrayList<SnowglobeScene>();
	public static SnowglobeScene SnowTree = new SnowglobeScene("snowWorld", EnumTheme.Snow, EnumTheme.Candycane, EnumTheme.Plants);
	public static SnowglobeScene CandyWorld = new SnowglobeScene("candyWorld", EnumTheme.Candycane, EnumTheme.Candy, EnumTheme.Snow);
	//public static SnowglobeScene testGrid = new SnowglobeScene("grid");
			
	public String texture;
	public EnumTheme[] themes;
	
	public SnowglobeScene(String tex, EnumTheme... themes)
	{
		this.texture = tex;
		this.themes = themes;
		this.list.add(this);
	}
	
	public String getTexture()
	{
		return "scene_" + this.texture + ".png";
	}
	
	public ResourceLocation getResource()
	{
		return new ResourceLocation(Festivities.ID,"textures/snowglobe/" + this.getTexture());
	}
}
