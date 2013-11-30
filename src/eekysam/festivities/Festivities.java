package eekysam.festivities;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.input.Keyboard;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatMessageComponent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.block.BlockCandyLog;
import eekysam.festivities.block.BlockFestive;
import eekysam.festivities.block.BlockFireplace;
import eekysam.festivities.block.BlockOrnament;
import eekysam.festivities.block.BlockSnowGlobe;
import eekysam.festivities.block.BlockSnowMachine;
import eekysam.festivities.block.BlockTreatPlate;
import eekysam.festivities.command.CommandHome;
import eekysam.festivities.command.CommandKringle;
import eekysam.festivities.debugutils.PerlinTest;
import eekysam.festivities.events.ConnectionHandler;
import eekysam.festivities.events.EventHooks;
import eekysam.festivities.item.ChristmasRecord;
import eekysam.festivities.item.ItemFestive;
import eekysam.festivities.item.ItemFestiveBlock;
import eekysam.festivities.item.ItemFoodFestive;
import eekysam.festivities.item.ItemMoreCookies;
import eekysam.festivities.item.ItemOrnament;
import eekysam.festivities.kringle.WorldProviderKringle;
import eekysam.festivities.kringle.biome.BiomeGenKringle;
import eekysam.festivities.network.PacketHandler;
import eekysam.festivities.player.PlayerData;
import eekysam.festivities.tile.TileEntityFireplace;
import eekysam.festivities.tile.TileEntityOrnament;
import eekysam.festivities.tile.TileEntityPlate;
import eekysam.festivities.tile.TileEntitySnowMachine;
import eekysam.festivities.tile.TileEntitySnowglobe;
import eekysam.utils.Toolbox;

@Mod(modid = Festivities.ID, name = Festivities.NAME, version = "2." + Festivities.MAJOR + "." + Festivities.MINOR + "." + Festivities.BUILD)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { Festivities.CHANNEL }, packetHandler = PacketHandler.class)
public class Festivities
{
	public static final String ID = "festivities";
	public static final String NAME = "Christmas Festivities Mod 2";
	public static final String CHANNEL = "festivities";

	public static final String CHATNAME = "Festivities";
	
	public static final String PLAYERDATA = "festivities";

	public static final int MAJOR = 2;
	public static final int MINOR = 1;
	public static final int BUILD = 1;

	public static final boolean DEBUG = false;

	public static final boolean TESTVERSION = false;
	public static final String[] TESTMSG = new String[] { "Christmas Festivities Mod 2", "Version " + "2." + Festivities.MAJOR + "." + Festivities.MINOR + "." + Festivities.BUILD + " is a TEST version!", "You will experience bugs and unfinished features.", "Download a proper release when possible." };
	public static final String[] TESTMSGDATED = new String[] { "This a TEST version of the Christmas Festivities Mod 2!", "You will experience bugs and unfinished features.", "Download a proper release when possible." };
	public static final String[] MSG = new String[] { "Christmas Festivities Mod 2", "Version " + "2." + Festivities.MAJOR + "." + Festivities.MINOR + "." + Festivities.BUILD, "", "Try with \"Not Enough Items\""};
	public static final String[] MSGDATED = new String[] {};
	
	public static final int kringleId = 3;
	
	private int itemId = 2600;
	private int blockId = 2400;

	@Instance("Festivities")
	public static Festivities instance;

	public static Item magicCandy;
	public static Item candyCane;
	public static Item moreCookies;
	public static Item berries;
	public static Item holly;
	public static Item bluePie;
	public static Item figgy;
	public static Item coloredOrnament;
	public static Item clearOrnament;
	public static Item flake;
	public static Item peppermintStick;
	//public static Item WeWishYouAMerryChristmas;
	
	public static Block candyLog;
	public static Block snowglobe;
	public static Block treatplate;
	public static Block coloredOrnamentBlock;
	public static Block clearOrnamentBlock;
	public static Block fireplace;
	public static Block iceBrick;//icebrick
	public static Block iceBrickCarved;//icebrick_carved
	public static Block iceBrickCracked;//icebrick_cracked
	public static Block cobbleIce;//cobbleice
	public static Block snowMachine;
	public static Block candyPlanks;
	
	public static int blockItemRenderId;
	
	public static FestivitiesTab foodTab = new FestivitiesTab(CreativeTabs.getNextID(), "Festive Foods");
	public static FestivitiesTab decorTab = new FestivitiesTab(CreativeTabs.getNextID(), "Festive Decorations");
	public static FestivitiesTab blockTab = new FestivitiesTab(CreativeTabs.getNextID(), "Festive Blocks");
	public static FestivitiesTab matTab = new FestivitiesTab(CreativeTabs.getNextID(), "Festive Materials");
	public static FestivitiesTab miscTab = new FestivitiesTab(CreativeTabs.getNextID(), "Festive Misc");
	
	public static Class blockItem = ItemFestiveBlock.class;
	
	public static final String shiftInfo = "\u00A7" + "o" + "Hold Shift For More...";
	
	@SidedProxy(modId = Festivities.ID, clientSide = "eekysam.festivities.client.ClientProxy", serverSide = "eekysam.festivities.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;

		magicCandy = new ItemFestive(nextItemID()).setTip("You probibly shouldn't eat this...").setShiftTip("All purpose test item").setUnlocalizedName("magicCandy").setTextureName(Festivities.ID + ":magicCandy").setCreativeTab(Festivities.miscTab);
		this.registerItem(magicCandy, "magicCandy");
		
		candyCane = new ItemFoodFestive(nextItemID(), 2, 0.1F, false).setTip("The meaning of Christmas...").setUnlocalizedName("candyCane").setTextureName(Festivities.ID + ":candyCane").setCreativeTab(Festivities.foodTab);
		this.registerItem(candyCane, "candyCane");
		
		candyLog = new BlockCandyLog(nextBlockID()).setCreativeTab(Festivities.blockTab).setUnlocalizedName("candyLog").setTextureName(Festivities.ID + ":candyLog");
		this.registerBlock(candyLog, "candyLog");
		
		snowglobe = new BlockSnowGlobe(nextBlockID(), Material.glass).setCreativeTab(Festivities.decorTab).setUnlocalizedName("snowglobe").setTextureName(Festivities.ID + ":snowglobe");
		this.registerBlock(snowglobe, "snowglobe");
		GameRegistry.registerTileEntity(TileEntitySnowglobe.class, "snowglobe");
		
		treatplate = new BlockTreatPlate(nextBlockID(), Material.glass).setCreativeTab(Festivities.blockTab).setUnlocalizedName("treatplate").setTextureName(Festivities.ID + ":treatplate");
		this.registerBlock(treatplate, "treatplate");
		GameRegistry.registerTileEntity(TileEntityPlate.class, "treatplate");
		
		moreCookies = new ItemMoreCookies(nextItemID(), 2, 0.1F).setTip("Everyone likes cookies!").setShiftTip("Can be stacked on a treat plate", "Stacks on plate up to 20 times").setUnlocalizedName("morecookies").setCreativeTab(Festivities.foodTab);
		this.registerItem(moreCookies, "morecookies");
		
		figgy = new ItemFoodFestive(nextItemID(), 4, 0.6F, false).setTip("Never tried it").setShiftTip("Can be displayed on a treat plate", "Stacks on plate twice").setUnlocalizedName("figgy").setTextureName(Festivities.ID + ":figgy").setCreativeTab(Festivities.foodTab);
		this.registerItem(figgy, "figgy");
		
		holly = new ItemFestive(nextItemID()).setTip("Pretty...but spiky too").setShiftTip("Dropped by grass blocks, tall grass, and other plants").setUnlocalizedName("holly").setTextureName(Festivities.ID + ":holly").setCreativeTab(Festivities.matTab);
		this.registerItem(holly, "holly");
		
		berries = new ItemFestive(nextItemID()).setTip("Not this season").setShiftTip("Dropped by grass blocks, tall grass, and other plants").setUnlocalizedName("berries").setTextureName(Festivities.ID + ":berries").setCreativeTab(Festivities.matTab);
		this.registerItem(berries, "berries");
		
		bluePie = new ItemFoodFestive(nextItemID(), 8, 0.3F, false).setTip("Mmmm, sweet").setShiftTip("Can be displayed on a treat plate", "Pumpkin pies also work").setUnlocalizedName("bluPie").setTextureName(Festivities.ID + ":blu_pie").setCreativeTab(Festivities.foodTab);
		this.registerItem(bluePie, "bluPie");
		
		clearOrnamentBlock = new BlockOrnament(nextBlockID(), true).setUnlocalizedName("clearOrnamentBlock");
		this.registerBlock(clearOrnamentBlock, "clearOrnamentBlock");
		
		coloredOrnamentBlock = new BlockOrnament(nextBlockID(), false).setUnlocalizedName("coloredOrnamentBlock");
		this.registerBlock(coloredOrnamentBlock, "coloredOrnamentBlock");
		
		clearOrnament = new ItemOrnament(nextItemID(), clearOrnamentBlock, true).setTip("A glass decoration for your tree!").setShiftTip("Right-Click to place", "Needs a block to sit or hang on").setUnlocalizedName("ornament").setCreativeTab(Festivities.decorTab);
		this.registerItem(clearOrnament, "clearOrnament");
		
		coloredOrnament = new ItemOrnament(nextItemID(), coloredOrnamentBlock, false).setTip("A colorful decoration for your tree!").setShiftTip("Right-Click to place", "Needs a block to sit or hang on").setUnlocalizedName("ornament").setCreativeTab(Festivities.decorTab);
		this.registerItem(coloredOrnament, "coloredOrnament");
		
		GameRegistry.registerTileEntity(TileEntityOrnament.class, "ornament");
		
		fireplace = new BlockFireplace(nextBlockID(), Material.rock).setUnlocalizedName("fireplace").setTextureName(Festivities.ID + ":fireplace").setLightValue(1.0F).setCreativeTab(Festivities.blockTab);
		this.registerBlock(fireplace, "fireplace");
		GameRegistry.registerTileEntity(TileEntityFireplace.class, "fireplace");
		
		iceBrick = new BlockFestive(nextBlockID(), Material.ice).setTip("Doesn't shatter!").setUnlocalizedName("iceBrick").setTextureName(Festivities.ID + ":icebrick").setCreativeTab(Festivities.blockTab);
		this.registerBlock(iceBrick, "iceBrick");

		iceBrickCarved = new BlockFestive(nextBlockID(), Material.ice).setTip("Oooh, pretty...").setUnlocalizedName("iceBrickCarved").setTextureName(Festivities.ID + ":iceBrick_carved").setCreativeTab(Festivities.blockTab);
		this.registerBlock(iceBrickCarved, "iceBrickCarved");

		iceBrickCracked = new BlockFestive(nextBlockID(), Material.ice).setTip("Maybe it does shatter...").setUnlocalizedName("iceBrickCracked").setTextureName(Festivities.ID + ":icebrick_cracked").setCreativeTab(Festivities.blockTab);
		this.registerBlock(iceBrickCracked, "iceBrickCracked");
		
		cobbleIce = new BlockFestive(nextBlockID(), Material.ice).setTip("Not as slippery").setUnlocalizedName("cobbleIce").setTextureName(Festivities.ID + ":cobbleice").setCreativeTab(Festivities.blockTab);
		this.registerBlock(cobbleIce, "cobbleIce");
		
		snowMachine = new BlockSnowMachine(nextBlockID(), Material.rock).setUnlocalizedName("snowMachine").setTextureName(Festivities.ID + ":snowMachine").setCreativeTab(Festivities.blockTab);
		this.registerBlock(snowMachine, "snowMachine");
		GameRegistry.registerTileEntity(TileEntitySnowMachine.class, "snowMachine");
		
		flake = new ItemFestive(nextItemID()).setTip("Catch one on your tongue!").setUnlocalizedName("flake").setTextureName(Festivities.ID + ":flake").setCreativeTab(Festivities.matTab);
		this.registerItem(flake, "flake");
		
		peppermintStick = new ItemFoodFestive(nextItemID(), 1, 0.1F, false).setTip("Not as bendy").setUnlocalizedName("peppermintStick").setTextureName(Festivities.ID + ":peppermintStick").setCreativeTab(Festivities.foodTab);
		this.registerItem(peppermintStick, "peppermintStick");
		
		candyPlanks= new BlockFestive(nextBlockID(), Material.wood).setTip("Sugary boards").setUnlocalizedName("candyPlanks").setTextureName(Festivities.ID + ":candyPlanks").setCreativeTab(Festivities.blockTab);
		this.registerBlock(candyPlanks, "candyPlanks");
		
		//WeWishYouAMerryChristmas = new ChristmasRecord(nextItemID(), "WeWishYouAMerryChristmas").setUnlocalizedName("record");
		//GameRegistry.registerItem(WeWishYouAMerryChristmas, "WeWishYouAMerryChristmas");
		
		this.foodTab.setIcon(candyCane);
		this.decorTab.setIcon(coloredOrnament);
		this.blockTab.setIcon(iceBrick);
		this.matTab.setIcon(holly);
		this.miscTab.setIcon(magicCandy);
		
		MinecraftForge.EVENT_BUS.register(new EventHooks());
	}
	
	protected void registerBlock(Block block, String name)
	{
		GameRegistry.registerBlock(block, this.blockItem, name);
	}
	
	protected void registerItem(Item item, String name)
	{
		GameRegistry.registerItem(item, name);
	}
	
	protected int nextItemID()
	{
		return ++this.itemId;
	}
	
	protected int nextBlockID()
	{
		return ++this.blockId;
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		if (this.DEBUG)
		{
			PerlinTest perlinTest = new PerlinTest(12965L, 8, 0.5F);
			perlinTest.makeWorld();
			perlinTest.saveImg("test.png", 256, 256);
		}

		NetworkRegistry.instance().registerConnectionHandler(new ConnectionHandler());

		BiomeGenKringle.registerBiomes(130);

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
		LanguageRegistry.addName(fireplace, "Fireplace");
		
		LanguageRegistry.addName(iceBrick, "Ice Brick");
		LanguageRegistry.addName(iceBrickCarved, "Carved Ice Brick");
		LanguageRegistry.addName(iceBrickCracked, "Cracked Ice Brick");
		LanguageRegistry.addName(cobbleIce, "Cobbled Ice");
		
		LanguageRegistry.addName(snowMachine, "Snow Machine");
		
		LanguageRegistry.addName(flake, "Snow Flake");
		LanguageRegistry.addName(peppermintStick, "Peppermint Stick");
		
		LanguageRegistry.addName(candyPlanks, "Candy Planks");

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
		GameRegistry.addShapelessRecipe(new ItemStack(this.bluePie), new Object[] { this.berries, Item.sugar, Item.egg });
		
		GameRegistry.addRecipe(new ItemStack(this.treatplate, 2), new Object[] { "CCC", 'C', Item.brick});
		GameRegistry.addRecipe(new ItemStack(this.treatplate, 2), new Object[] { "CCC", 'C', Block.glass});
		
		GameRegistry.addRecipe(new ItemStack(this.clearOrnament, 6), new Object[] { " N ", "G G", " G ", 'N', Item.goldNugget, 'G', Block.glass});
		
		for (int i = 0; i < 16; i++)
		{
				GameRegistry.addShapelessRecipe(new ItemStack(this.coloredOrnament, 1, i), new Object[] {new ItemStack(Item.dyePowder, 1, i), this.clearOrnament});
		}
		
		GameRegistry.addRecipe(new ItemStack(this.snowMachine, 2), new Object[] { "I I", " P ", "SDS", 'I', Item.ingotIron, 'P', Block.pistonBase, 'S', new ItemStack(Block.stoneSingleSlab, 1, 0), 'D', Block.dropper});
		
		GameRegistry.addRecipe(new ItemStack(this.fireplace, 1), new Object[] {"   ", "I I", "BWB", 'I', Block.fenceIron, 'B', Block.brick, 'W', Block.wood});
		
		GameRegistry.addRecipe(new ItemStack(this.flake, 4), new Object[] {" S ", "S S", " S ", 'S', Item.snowball});
		GameRegistry.addRecipe(new ItemStack(this.snowglobe), new Object[] {"GGG", "GSG", "WIW", 'S', this.flake, 'G', Block.glass, 'I', Item.ingotGold, 'W', Block.wood});
		
		GameRegistry.addRecipe(new ItemStack(this.iceBrick), new Object[] {"##", "##", '#', Block.ice});
		GameRegistry.addRecipe(new ItemStack(this.iceBrickCarved), new Object[] {"##", "##", '#', this.iceBrick});
		GameRegistry.addRecipe(new ItemStack(this.iceBrickCracked), new Object[] {"##", "##", '#', this.cobbleIce});
		GameRegistry.addRecipe(new ItemStack(this.cobbleIce), new Object[] {"#", '#', Block.ice});
		
		GameRegistry.addRecipe(new ItemStack(this.candyCane, 4), new Object[] {"#", '#', this.candyLog});
		GameRegistry.addRecipe(new ItemStack(this.candyLog), new Object[] {"##", "##", '#', this.candyCane});
		
		GameRegistry.addRecipe(new ItemStack(this.candyCane), new Object[] {"#", "#", '#', this.peppermintStick});
		GameRegistry.addRecipe(new ItemStack(this.peppermintStick, 2), new Object[] {"#", '#', this.candyCane});
		
		GameRegistry.addRecipe(new ItemStack(this.peppermintStick, 4), new Object[] {"#", "#", '#', this.candyPlanks});
		GameRegistry.addRecipe(new ItemStack(this.candyPlanks, 2), new Object[] {"##", "##", '#', this.peppermintStick});
		
		DimensionManager.registerProviderType(this.kringleId, WorldProviderKringle.class, false);
		DimensionManager.registerDimension(this.kringleId, this.kringleId);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandKringle());
		event.registerServerCommand(new CommandHome());
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER)
		{
			this.SendGloabalChat(Minecraft.getMinecraft().getIntegratedServer().getConfigurationManager(), this.getUpdateWarning());
		}
	}

	public static void SendChat(EntityPlayer player, String msg)
	{
		if (msg != null)
		{
			player.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("chat.type.announcement", new Object[] { CHATNAME, msg }));
		}
	}

	public static void SendChat(EntityPlayer player, String[] msg)
	{
		String s = "";
		if (msg != null)
		{
			for (int i = 0; i < msg.length; i++)
			{
				s += msg[i];
				if (i < msg.length - 1)
				{
					s += "\n";
				}
			}
		}
		SendChat(player, s);
	}

	public static void SendGlobalChat(ServerConfigurationManager server, String msg)
	{
		if (msg != null)
		{
			server.sendChatMsg(ChatMessageComponent.createFromTranslationWithSubstitutions("chat.type.announcement", new Object[] { CHATNAME, msg }));
		}
	}

	public static void SendGloabalChat(ServerConfigurationManager server, String[] msg)
	{
		String s = "";
		if (msg != null)
		{
			for (int i = 0; i < msg.length; i++)
			{
				s += msg[i];
				if (i < msg.length - 1)
				{
					s += "\n";
				}
			}
		}
		SendGlobalChat(server, s);
	}

	public String[] getUpdateWarning()
	{
		String[] msg = null;
		try
		{
			URL url = new URL("https://dl.dropboxusercontent.com/u/22114490/Christmas%20Festivities%20Mod%202/jars/version.txt");
			Scanner s = new Scanner(url.openStream());
			String line = s.nextLine();

			if (this.isOutOfDate(line))
			{
				msg = new String[] { "Christmas Festivities Mod 2 is out of date", "Current Version: " + "2." + Festivities.MAJOR + "." + Festivities.MINOR + "." + Festivities.BUILD, "Newest Version: " + line };
				String[] info = new String[0];
				while (s.hasNextLine())
				{
					line = s.nextLine();
					if (line.startsWith("?"))
					{
						String[] add = this.getUpdateInfo(line);
						if (add != null)
						{
							info = Toolbox.mergeStringArrays(info, add);
						}
					}
				}
				if (info.length != 0)
				{
					msg = Toolbox.mergeStringArrays(msg, new String[] {"", "You are missing out on:"});
					msg = Toolbox.mergeStringArrays(msg, info);
				}
			}
			s.close();
		}
		catch (IOException ex)
		{
		}
		return msg;
	}

	public String[] getUpdateInfo(String line)
	{
		String[] ln = line.split(" ");
		String v = ln[0];
		v = v.replaceFirst("\\?", "");
		v = v.trim();
		String msg = "";
		if (this.isOutOfDate(v))
		{
			for (int i = 1; i < ln.length; i++)
			{
				if (i > 1)
				{
					msg += " ";
				}
				msg += ln[i];
			}
			return new String[] {msg};
		}
		return null;
	}

	public boolean isOutOfDate(String version)
	{
		String[] nums = version.split("\\.");
		for (int i = 0; i < nums.length; i++)
		{
			int n = Integer.parseInt(nums[i]);
			System.out.println(n);
			if (i == 0 && n > 2)
			{
				return true;
			}
			if (i == 0 && n < 2)
			{
				return false;
			}
			if (i == 1 && n > this.MAJOR)
			{
				return true;
			}
			if (i == 1 && n < this.MAJOR)
			{
				return false;
			}
			if (i == 2 && n > this.MINOR)
			{
				return true;
			}
			if (i == 2 && n < this.MINOR)
			{
				return false;
			}
			if (i == 3 && n > this.BUILD)
			{
				return true;
			}
			if (i == 3 && n < this.BUILD)
			{
				return false;
			}
		}
		return false;
	}
	
	public static PlayerData getPlayerData(EntityPlayerMP player)
	{
		return (PlayerData) player.getExtendedProperties(PLAYERDATA);
	}
	
	@SideOnly(Side.CLIENT)
	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public static void addInformation(ITipItem tipItem, ItemStack stack, EntityPlayer player, List info, boolean advanced)
	{
		String[] tips;
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			tips = tipItem.getShiftTip(player, stack);
			if (tips == null)
			{
				tips = tipItem.getTip(player, stack);
			}
		}
		else
		{
			tips = tipItem.getTip(player, stack);
			String[] shifttips = tipItem.getShiftTip(player, stack);
			if (shifttips != null && shifttips.length != 0)
			{
				List<String> moretips = new ArrayList<String>();
				for (int i = 0; i < tips.length; i++)
				{
					moretips.add(tips[i]);
				}
				if (!tips[tips.length - 1].isEmpty())
				{
					moretips.add("");
				}
				moretips.add(Festivities.shiftInfo);
				
				tips = moretips.toArray(tips);
			}
		}
		if (tips == null)
		{
			return;
		}
		boolean flag = false;
		for (int i = 0; i < tips.length; i++)
		{
			String tip = tips[i];
			String[] tiplines = Toolbox.wrapString(tip, 40);
			if (tiplines.length > 1 && flag)
			{
				info.add("");
			}
			for (int j = 0; j < tiplines.length; j++)
			{
				info.add(tiplines[j]);
			}
			if (tiplines.length > 1 && i < tips.length - 1)
			{
				info.add("");
				flag = false;
			}
			else
			{
				flag = true;
			}
		}
	}
}
