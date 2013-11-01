package eekysam.festivities.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockCandyLog extends Block
{    
    @SideOnly(Side.CLIENT)
	private Icon side;
    @SideOnly(Side.CLIENT)
	private Icon rside;
    @SideOnly(Side.CLIENT)
	private Icon end;
	
	public BlockCandyLog(int par1)
	{
		super(par1, Material.wood);
	}

    @SideOnly(Side.CLIENT)
	protected Icon getSideIcon(int l, boolean f)
	{
    	if (f)
    	{
    		return this.rside;
    	}
    	else
    	{
    		return this.rside;
    	}
	}   
	
    @SideOnly(Side.CLIENT)
    protected Icon getEndIcon(int l)
    {
        return this.end;
    }
    
    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 31;
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int j1 = par9 & 3;
        byte b0 = 0;

        switch (par5)
        {
            case 0:
            case 1:
                b0 = 0;
                break;
            case 2:
            case 3:
                b0 = 8;
                break;
            case 4:
            case 5:
                b0 = 4;
        }

        return j1 | b0;
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int s, int m)
    {
        int k = m & 12;
        int l = m & 3;
        
        if (k == 0)
        {
        	if (s == 0 || s== 1)
        	{
        		return this.getEndIcon(l);
        	}
    		return this.getSideIcon(l, s == 2 || s == 4);
        }
        if (k == 4)
        {
        	if (s == 4 || s== 5)
        	{
        		return this.getEndIcon(l);
        	}
    		return this.getSideIcon(l, s == 0 || s == 3);
        }        
        if (k == 8)
        {
        	if (s == 2 || s== 3)
        	{
        		return this.getEndIcon(l);
        	}
    		return this.getSideIcon(l, s == 0 || s == 5);
        }
        return null;
    }
	
    @SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg)
    {
		this.side = reg.registerIcon(this.getTextureName() + "_side");
		this.rside = reg.registerIcon(this.getTextureName() + "_rside");
		this.end = reg.registerIcon(this.getTextureName() + "_top");
    }
}
