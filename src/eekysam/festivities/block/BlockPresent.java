package eekysam.festivities.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.ITipItem;

public class BlockPresent extends Block implements ITipItem
{
    @SideOnly(Side.CLIENT)
    private Icon bottom;
    private Icon side;
    
    public BlockPresent(int par1)
    {
        super(par1, Material.glass);
        setStepSound(Block.soundClothFootstep);
    }
    
    public Icon getIcon(int s, int m)
    {
        if (s == 0 || s == 1)
            return this.bottom;
        else
            return this.side;
    }
    
        @Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg)
	{
            this.bottom = reg.registerIcon(this.getTextureName() + "_bottom");
            this.side = reg.registerIcon(this.getTextureName() + "_side");
	}
        
        @Override
	public String[] getTip(EntityPlayer player, ItemStack stack)
	{
		return new String[] { "A prize inside every one!" };
	}

	@Override
	public String[] getShiftTip(EntityPlayer player, ItemStack stack)
	{
		return new String[] { "Except this one..." };
	}
}
