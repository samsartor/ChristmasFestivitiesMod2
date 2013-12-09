package eekysam.festivities.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import eekysam.festivities.EnumTheme;
import eekysam.festivities.Festivities;

public class SnowglobeScene
{
	public static List<SnowglobeScene> list = new ArrayList<SnowglobeScene>();
	public static SnowglobeScene SnowTree = new SnowglobeScene("snowWorld", Item.stick.itemID, Block.wood.blockID, Block.planks.blockID);
	public static SnowglobeScene CandyWorld = new SnowglobeScene("candyWorld", Festivities.candyLog.blockID, Festivities.peppermintStick.itemID, Festivities.candyCane.itemID);
	public static SnowglobeScene HillWorld = new SnowglobeScene("hillWorld", Block.ice.blockID, Festivities.cobbleIce.blockID);

	public String texture;
	public int[] items;

	public SnowglobeScene(String tex, int... items)
	{
		this.texture = tex;
		this.items = items;
		this.list.add(this);
	}

	public String getTexture()
	{
		return "scene_" + this.texture + ".png";
	}

	public ResourceLocation getResource()
	{
		return new ResourceLocation(Festivities.ID, "textures/snowglobe/" + this.getTexture());
	}

	public static SnowglobeScene getFromItem(ItemStack item)
	{
		int id = item.itemID;
		for (int i = 0; i < list.size(); i++)
		{
			 SnowglobeScene scene = list.get(i);
			 for (int j = 0; j < scene.items.length; j++)
			 {
				 if (scene.items[j] == id)
				 {
					 return scene;
				 }
			 }
		}
		return null;
	}
}
