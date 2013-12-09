package eekysam.festivities.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.ITipItem;

public class BlockCandyLog extends Block implements ITipItem
{
	@SideOnly(Side.CLIENT)
	private Icon dlSide;
	@SideOnly(Side.CLIENT)
	private Icon urSide;
	@SideOnly(Side.CLIENT)
	private Icon drSide;
	@SideOnly(Side.CLIENT)
	private Icon ulSide;
	@SideOnly(Side.CLIENT)
	private Icon rEnd;
	@SideOnly(Side.CLIENT)
	private Icon lEnd;

	public BlockCandyLog(int par1)
	{
		super(par1, Material.wood);
	}

	@SideOnly(Side.CLIENT)
	protected Icon getSideIcon(int l, int d)
	{
		if (d == -2)
		{
			return this.urSide;
		}
		if (d == -1)
		{
			return this.drSide;
		}
		if (d == 1)
		{
			return this.dlSide;
		}
		if (d == 2)
		{
			return this.ulSide;
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	protected Icon getEndIcon(int l, int d)
	{
		if (d == -1)
		{
			return this.rEnd;
		}
		if (d == 1)
		{
			return this.lEnd;
		}
		return null;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return 0;
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z,
	 * side, hitX, hitY, hitZ, block metadata
	 */
	@Override
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

	@Override
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
			if (s == 0 || s == 1)
			{
				return this.getEndIcon(l, 1);
			}
			return this.getSideIcon(l, 1);
		}
		if (k == 4)
		{
			if (s == 4)
			{
				return this.getEndIcon(l, -1);
			}
			if (s == 5)
			{
				return this.getEndIcon(l, 1);
			}
			if (s == 0)
			{
				return this.getSideIcon(l, -2);
			}
			if (s == 1)
			{
				return this.getSideIcon(l, 1);
			}
			if (s == 2)
			{
				return this.getSideIcon(l, 2);
			}
			if (s == 3)
			{
				return this.getSideIcon(l, 1);
			}
		}
		if (k == 8)
		{
			if (s == 2)
			{
				return this.getEndIcon(l, 1);
			}
			if (s == 3)
			{
				return this.getEndIcon(l, -1);
			}
			if (s == 0)
			{
				return this.getSideIcon(l, 2);
			}
			if (s == 1)
			{
				return this.getSideIcon(l, -2);
			}
			if (s == 4)
			{
				return this.getSideIcon(l, 2);
			}
			if (s == 5)
			{
				return this.getSideIcon(l, 1);
			}
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg)
	{
		this.dlSide = reg.registerIcon(this.getTextureName() + "_DLside");
		this.ulSide = reg.registerIcon(this.getTextureName() + "_ULside");
		this.drSide = reg.registerIcon(this.getTextureName() + "_DRside");
		this.urSide = reg.registerIcon(this.getTextureName() + "_URside");
		this.rEnd = reg.registerIcon(this.getTextureName() + "_Rend");
		this.lEnd = reg.registerIcon(this.getTextureName() + "_Lend");
	}

	@Override
	public String[] getTip(EntityPlayer player, ItemStack stack)
	{
		return new String[] { "Strong and delicious!" };
	}

	@Override
	public String[] getShiftTip(EntityPlayer player, ItemStack stack)
	{
		return null;
	}
}
