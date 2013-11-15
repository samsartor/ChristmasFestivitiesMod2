package eekysam.festivities;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import eekysam.festivities.block.BlockCandyLog;
import eekysam.festivities.block.BlockSnowglobe;
import eekysam.festivities.block.BlockTreatPlate;
import eekysam.festivities.tile.TileEntityPlate;
import eekysam.festivities.tile.TileEntitySnowglobe;
import eekysam.festivities.item.ItemMoreCookies;
import eekysam.festivities.network.PacketHandler;

@Mod(modid = Festivities.ID, name = Festivities.NAME, version = "2." + Festivities.MAJOR + "." + Festivities.MINOR + "." + Festivities.BUILD)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { Festivities.CHANNEL }, packetHandler = PacketHandler.class)
public class Festivities
{
	public static final String ID = "festivities";
	public static final String NAME = "festivities";
	public static final String CHANNEL = "festivities";

	public static final int MAJOR = 0;
	public static final int MINOR = 0;
	public static final int BUILD = 0;

	@Instance("Festivities")
	public static Festivities instance;

	public static Item magicCandy;
	public static Item candyCane;
	public static Item moreCookies;
	public static Item berries;
	public static Item holly;
	public static Item bluePie;
	public static Item figgy;
	public static Block candyLog;
	public static Block snowglobe;
	public static Block treatplate;

	@SidedProxy(modId = Festivities.ID, clientSide = "eekysam.festivities.client.ClientProxy", serverSide = "eekysam.festivities.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		magicCandy = new Item(2601).setUnlocalizedName("magicCandy").setTextureName(Festivities.ID + ":magicCandy").setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(magicCandy, "magicCandy");
		candyCane = new Item(2602).setUnlocalizedName("candyCane").setTextureName(Festivities.ID + ":candyCane").setCreativeTab(CreativeTabs.tabFood);
		GameRegistry.registerItem(candyCane, "candyCane");
		candyLog = new BlockCandyLog(2401).setCreativeTab(CreativeTabs.tabBlock).setUnlocalizedName("candyLog").setTextureName(Festivities.ID + ":candyLog");
		GameRegistry.registerBlock(candyLog, "candyLog");
		snowglobe = new BlockSnowglobe(2402, Material.glass).setCreativeTab(CreativeTabs.tabDecorations).setUnlocalizedName("snowglobe");
		GameRegistry.registerBlock(snowglobe, "snowglobe");
		GameRegistry.registerTileEntity(TileEntitySnowglobe.class, "snowglobe");
		treatplate = new BlockTreatPlate(2403, Material.cake).setCreativeTab(CreativeTabs.tabFood).setUnlocalizedName("treatplate");
		GameRegistry.registerBlock(treatplate, "treatplate");
		GameRegistry.registerTileEntity(TileEntityPlate.class, "treatplate");
		moreCookies = new ItemMoreCookies(2603, 2, 0.1F).setUnlocalizedName("morecookies").setCreativeTab(CreativeTabs.tabFood);
		GameRegistry.registerItem(moreCookies, "morecookies");
		figgy = new ItemFood(2604, 4, 0.6F, false).setUnlocalizedName("figgy").setTextureName(Festivities.ID + ":figgy").setCreativeTab(CreativeTabs.tabFood);
		GameRegistry.registerItem(figgy, "figgy");
		holly = new Item(2605).setUnlocalizedName("holly").setTextureName(Festivities.ID + ":holly").setCreativeTab(CreativeTabs.tabMaterials);
		GameRegistry.registerItem(holly, "holly");
		berries = new Item(2606).setUnlocalizedName("berries").setTextureName(Festivities.ID + ":berries").setCreativeTab(CreativeTabs.tabMaterials);
		GameRegistry.registerItem(berries, "berries");
		bluePie = new ItemFood(2607, 8, 0.3F, false).setUnlocalizedName("bluPie").setTextureName(Festivities.ID + ":blu_pie").setCreativeTab(CreativeTabs.tabFood);
		GameRegistry.registerItem(bluePie, "bluPie");
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		this.proxy.registerRenderers();
		LanguageRegistry.addName(magicCandy, "Magic Candy Cane");
		LanguageRegistry.addName(candyCane, "Candy Cane");
		LanguageRegistry.addName(candyLog, "Peppermint Log");
		LanguageRegistry.addName(snowglobe, "Snowglobe");
		LanguageRegistry.addName(treatplate, "Plate of Treats");
		LanguageRegistry.addName(new ItemStack(this.moreCookies, 1, 0), "Sugar Cookie");
		LanguageRegistry.addName(new ItemStack(this.moreCookies, 1, 1), "Chocolate Cookie");
		LanguageRegistry.addName(new ItemStack(this.moreCookies, 1, 2), "Cookie with Sprinkles");
		LanguageRegistry.addName(new ItemStack(this.moreCookies, 1, 3), "Peppermint Cookie");
		LanguageRegistry.addName(figgy, "Christmas Pudding");
		LanguageRegistry.addName(holly, "Holly");
		LanguageRegistry.addName(berries, "Seasonal Fruits");
		LanguageRegistry.addName(bluePie, "Blue Berry Pie");

		GameRegistry.addShapelessRecipe(new ItemStack(this.figgy, 1), new Object[] { this.holly, this.berries, this.berries, Item.sugar });
		GameRegistry.addRecipe(new ItemStack(this.moreCookies, 8, 0), new Object[] { "#X#", 'X', Item.sugar, '#', Item.wheat });
		GameRegistry.addRecipe(new ItemStack(this.moreCookies, 8, 1), new Object[] { "X#X", 'X', new ItemStack(Item.dyePowder, 1, 3), '#', Item.wheat });
		for (int i = 0; i < 16; i++)
		{
			if (i != 0 && i != 3 && i != 15)
			{
				GameRegistry.addRecipe(new ItemStack(this.moreCookies, 8, 2), new Object[] { " S ", "#X#", 'X', Item.sugar, '#', Item.wheat, 'S', new ItemStack(Item.dyePowder, 1, i) });
				GameRegistry.addRecipe(new ItemStack(this.moreCookies, 8, 2), new Object[] { "###", "#X#", "###", 'X', new ItemStack(Item.dyePowder, 1, i), '#', new ItemStack(this.moreCookies, 1, 0) });
			}
		}
		GameRegistry.addRecipe(new ItemStack(this.moreCookies, 8, 3), new Object[] { "X#X", 'X', this.candyCane, '#', Item.wheat });
		GameRegistry.addShapelessRecipe(new ItemStack(this.bluePie), new Object[] {this.berries, Item.sugar, Item.egg});
	}
}
