package eekysam.festivities;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatMessageComponent;
import net.minecraftforge.common.DimensionManager;
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
import eekysam.festivities.block.BlockFireplace;
import eekysam.festivities.block.BlockOrnament;
import eekysam.festivities.block.BlockSnowGlobe;
import eekysam.festivities.block.BlockTreatPlate;
import eekysam.festivities.command.CommandHome;
import eekysam.festivities.command.CommandKringle;
import eekysam.festivities.debugutils.PerlinTest;
import eekysam.festivities.events.ConnectionHandler;
import eekysam.festivities.events.EventHooks;
import eekysam.festivities.item.ItemMoreCookies;
import eekysam.festivities.item.ItemOrnament;
import eekysam.festivities.kringle.WorldProviderKringle;
import eekysam.festivities.kringle.biome.BiomeGenKringle;
import eekysam.festivities.network.PacketHandler;
import eekysam.festivities.player.PlayerData;
import eekysam.festivities.tile.TileEntityFireplace;
import eekysam.festivities.tile.TileEntityOrnament;
import eekysam.festivities.tile.TileEntityPlate;
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

	public static final int MAJOR = 1;
	public static final int MINOR = 1;
	public static final int BUILD = 3;

	public static final boolean DEBUG = false;

	public static final boolean TESTVERSION = false;
	public static final String[] TESTMSG = new String[] { "Christmas Festivities Mod 2", "Version " + "2." + Festivities.MAJOR + "." + Festivities.MINOR + "." + Festivities.BUILD + " is a TEST version!", "You will experience bugs and unfinished features.", "Download a proper release when possible." };
	public static final String[] TESTMSGDATED = new String[] { "This a TEST version of the Christmas Festivities Mod 2!", "You will experience bugs and unfinished features.", "Download a proper release when possible." };
	public static final String[] MSG = new String[] { "Christmas Festivities Mod 2", "Version " + "2." + Festivities.MAJOR + "." + Festivities.MINOR + "." + Festivities.BUILD };
	public static final String[] MSGDATED = new String[] {};

	public static CreativeTabs FestiveTab = new FestivitiesTab(CreativeTabs.getNextID(), "Christmas Festives 2");
	
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
	
	public static Block candyLog;
	public static Block snowglobe;
	public static Block treatplate;
	public static Block coloredOrnamentBlock;
	public static Block clearOrnamentBlock;
	public static Block fireplace;
	public static Block iceBrick;//icebrick
	public static Block icdBrickCarved;//icdbrick_carved
	public static Block iceBrickCracked;//icebrick_cracked
	public static Block cobbleIce;//cobbleice
	
	public static int blockItemRenderId;

	@SidedProxy(modId = Festivities.ID, clientSide = "eekysam.festivities.client.ClientProxy", serverSide = "eekysam.festivities.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;

		magicCandy = new Item(nextItemID()).setUnlocalizedName("magicCandy").setTextureName(Festivities.ID + ":magicCandy").setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerItem(magicCandy, "magicCandy");
		
		candyCane = new Item(nextItemID()).setUnlocalizedName("candyCane").setTextureName(Festivities.ID + ":candyCane").setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerItem(candyCane, "candyCane");
		
		candyLog = new BlockCandyLog(nextBlockID()).setCreativeTab(Festivities.FestiveTab).setUnlocalizedName("candyLog").setTextureName(Festivities.ID + ":candyLog");
		GameRegistry.registerBlock(candyLog, "candyLog");
		
		snowglobe = new BlockSnowGlobe(nextBlockID(), Material.glass).setCreativeTab(Festivities.FestiveTab).setUnlocalizedName("snowglobe").setTextureName(Festivities.ID + ":snowglobe");
		GameRegistry.registerBlock(snowglobe, "snowglobe");
		GameRegistry.registerTileEntity(TileEntitySnowglobe.class, "snowglobe");
		
		treatplate = new BlockTreatPlate(nextBlockID(), Material.cake).setCreativeTab(Festivities.FestiveTab).setUnlocalizedName("treatplate").setTextureName(Festivities.ID + ":treatplate");
		GameRegistry.registerBlock(treatplate, "treatplate");
		GameRegistry.registerTileEntity(TileEntityPlate.class, "treatplate");
		
		moreCookies = new ItemMoreCookies(nextItemID(), 2, 0.1F).setUnlocalizedName("morecookies").setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerItem(moreCookies, "morecookies");
		
		figgy = new ItemFood(nextItemID(), 4, 0.6F, false).setUnlocalizedName("figgy").setTextureName(Festivities.ID + ":figgy").setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerItem(figgy, "figgy");
		
		holly = new Item(nextItemID()).setUnlocalizedName("holly").setTextureName(Festivities.ID + ":holly").setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerItem(holly, "holly");
		
		berries = new Item(nextItemID()).setUnlocalizedName("berries").setTextureName(Festivities.ID + ":berries").setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerItem(berries, "berries");
		
		bluePie = new ItemFood(nextItemID(), 8, 0.3F, false).setUnlocalizedName("bluPie").setTextureName(Festivities.ID + ":blu_pie").setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerItem(bluePie, "bluPie");
		
		clearOrnamentBlock = new BlockOrnament(nextBlockID(), true).setUnlocalizedName("clearOrnamentBlock");
		GameRegistry.registerBlock(clearOrnamentBlock, "clearOrnamentBlock");
		
		coloredOrnamentBlock = new BlockOrnament(nextBlockID(), false).setUnlocalizedName("coloredOrnamentBlock");
		GameRegistry.registerBlock(coloredOrnamentBlock, "coloredOrnamentBlock");
		
		clearOrnament = new ItemOrnament(nextItemID(), clearOrnamentBlock, true).setUnlocalizedName("ornament").setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerItem(clearOrnament, "clearOrnament");
		
		coloredOrnament = new ItemOrnament(nextItemID(), coloredOrnamentBlock, false).setUnlocalizedName("ornament").setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerItem(coloredOrnament, "coloredOrnament");
		
		GameRegistry.registerTileEntity(TileEntityOrnament.class, "ornament");
		
		fireplace = new BlockFireplace(nextBlockID(), Material.rock).setUnlocalizedName("fireplace").setTextureName(Festivities.ID + ":fireplace").setLightValue(1.0F).setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerBlock(fireplace, "fireplace");
		GameRegistry.registerTileEntity(TileEntityFireplace.class, "fireplace");
		
		iceBrick = new Block(nextBlockID(), Material.ice).setUnlocalizedName("iceBrick").setTextureName(Festivities.ID + ":icebrick").setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerBlock(iceBrick, "iceBrick");

		icdBrickCarved = new Block(nextBlockID(), Material.ice).setUnlocalizedName("icdBrickCarved").setTextureName(Festivities.ID + ":icdbrick_carved").setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerBlock(icdBrickCarved, "icdBrickCarved");

		iceBrickCracked = new Block(nextBlockID(), Material.ice).setUnlocalizedName("iceBrickCracked").setTextureName(Festivities.ID + ":icebrick_cracked").setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerBlock(iceBrickCracked, "iceBrickCracked");
		
		cobbleIce = new Block(nextBlockID(), Material.ice).setUnlocalizedName("iceBrick").setTextureName(Festivities.ID + ":cobbleice").setCreativeTab(Festivities.FestiveTab);
		GameRegistry.registerBlock(cobbleIce, "cobbleIce");
		
		MinecraftForge.EVENT_BUS.register(new EventHooks());
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
		LanguageRegistry.addName(icdBrickCarved, "Carved Ice Brick");
		LanguageRegistry.addName(iceBrickCracked, "Cracked Ice Brick");
		LanguageRegistry.addName(cobbleIce, "Cobbled Ice");

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
}
