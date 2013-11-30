package eekysam.festivities.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.Festivities;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemOrnament extends ItemFestive
{
	private int spawnID;
	private boolean clear;

	public static final String[] ornamentColorNames = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
    public static final String[] ornamentNames = new String[] {"Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "Silver", "Gray", "Pink", "Lime", "Yellow", "Light Blue", "Magenta", "Orange", "White"};
    public static final int[] ornamentColors = new int[] {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};
    @SideOnly(Side.CLIENT)
    private Icon baseIcon;
    @SideOnly(Side.CLIENT)
    private Icon coloredIcon;
    @SideOnly(Side.CLIENT)
    private Icon clearIcon;
	
    public ItemOrnament(int par1, Block block, boolean clear)
	{
		super(par1);
		this.spawnID = block.blockID;
		this.clear = clear;
		if (!clear)
		{
	        this.setHasSubtypes(true);
	        this.setMaxDamage(0);
		}
        this.setCreativeTab(CreativeTabs.tabDecorations);
	}
    
    public boolean isClear()
	{
		return clear;
	}
    
    public String getItemDisplayName(ItemStack stack)
    {
    	if (this.clear)
    	{
    		return "Ornament";
    	}
    	else
    	{
    		int i = MathHelper.clamp_int(stack.getItemDamage(), 0, 15);
    		return this.ornamentNames[i] + " Ornament";
    	}
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
    	if (this.clear)
    	{
    		return super.getUnlocalizedName() + ".clear";
    	}
    	else
    	{
	        int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
	        return super.getUnlocalizedName() + "." + ornamentColorNames[i];
    	}
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	if (this.clear)
    	{
    		super.getSubItems(par1, par2CreativeTabs, par3List);
    	}
    	else
    	{
	        for (int j = 0; j < 16; ++j)
	        {
	            par3List.add(new ItemStack(par1, 1, j));
	        }
    	}
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
		if (this.clear)
		{
			this.clearIcon = par1IconRegister.registerIcon(Festivities.ID + ":ornament" + "_" + "clear");
		}
		else
		{
			this.baseIcon = par1IconRegister.registerIcon(Festivities.ID + ":ornament" + "_" + "base");
			this.coloredIcon = par1IconRegister.registerIcon(Festivities.ID + ":ornament" + "_" + "color");
		}
    }
    
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        int atId = world.getBlockId(x, y, z);

        if (atId == Block.snow.blockID && (world.getBlockMetadata(x, y, z) & 7) < 1)
        {
            side = 1;
        }
        else if (atId != Block.vine.blockID && atId != Block.tallGrass.blockID && atId != Block.deadBush.blockID)
        {
            if (side == 0)
            {
                --y;
            }

            if (side == 1)
            {
                ++y;
            }

            if (side == 2)
            {
                --z;
            }

            if (side == 3)
            {
                ++z;
            }

            if (side == 4)
            {
                --x;
            }

            if (side == 5)
            {
                ++x;
            }
        }

        if (!player.canPlayerEdit(x, y, z, side, stack))
        {
            return false;
        }
        else if (stack.stackSize == 0)
        {
            return false;
        }
        else
        {
            if (world.canPlaceEntityOnSide(this.spawnID, x, y, z, false, side, (Entity)null, stack))
            {
                Block block = Block.blocksList[this.spawnID];
                int meta = block.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, stack.getItemDamage());
                meta = stack.getItemDamage();
                
                if (world.setBlock(x, y, z, this.spawnID, meta, 3))
                {
                    if (world.getBlockId(x, y, z) == this.spawnID)
                    {
                        Block.blocksList[this.spawnID].onBlockPlacedBy(world, x, y, z, player, stack);
                        Block.blocksList[this.spawnID].onPostBlockPlaced(world, x, y, z, meta);
                    }

                    --stack.stackSize;
                }
            }

            return true;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return !this.clear;
    }
    
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int par1)
    {
        return this.clearIcon;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public Icon getIconFromDamageForRenderPass(int dmg, int pass)
    {
    	if (this.clear)
    	{
    		return this.clearIcon;
    	}
    	else
    	{
	        if (pass == 0)
	        {
	        	return this.baseIcon;
	        }
	        else
	        {
	        	return this.coloredIcon;
	        }
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int pass)
    {
        if (this.clear)
        {
        	return 0xFFFFFF;
        }
        else
        {
        	if (pass == 0)
        	{
        		return 0xFFFFFF;
        	}
        	else
        	{
        		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
        		return this.ornamentColors[i];
        	}
        }
    }
}
