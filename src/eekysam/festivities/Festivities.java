package eekysam.festivities;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "festivities", name = "festivities", version = "0.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Festivities
{
	@Instance("Festivities")
	public static Festivities instance;

	public static Item magicCandy;
	public static Item candyCane;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		magicCandy = new Item(2601).setUnlocalizedName("magicCandy").setTextureName("festivities:magicCandy").setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(magicCandy, "magicCandy");
		candyCane = new Item(2602).setUnlocalizedName("candyCane").setTextureName("festivities:candyCane").setCreativeTab(CreativeTabs.tabFood);
		GameRegistry.registerItem(candyCane, "candyCane");
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		LanguageRegistry.addName(magicCandy, "Magic Candy");
		LanguageRegistry.addName(candyCane, "Candy Cane");
	}
}
