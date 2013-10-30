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

	private static Item magicCandy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		System.out.println("Loading Items");
		magicCandy = new Item(2601).setMaxStackSize(16).setUnlocalizedName("magicCandy").setTextureName("festivities:magicCandy").setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(magicCandy, "magicCandy");
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		LanguageRegistry.addName(magicCandy, "Magic Candy");
	}
}
