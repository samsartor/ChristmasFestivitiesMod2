package eekysam.festivities.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.Festivities;
import eekysam.festivities.ITipItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockMintPlant extends Block implements ITipItem
{
	private Icon[] icons;

	public BlockMintPlant(int par1)
	{
		super(par1, Material.plants);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.icons = new Icon[2];
		this.icons[0] = par1IconRegister.registerIcon(this.getTextureName() + "_bot");
		this.icons[1] = par1IconRegister.registerIcon(this.getTextureName() + "_top");
	}

	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta)
	{
		return this.icons[meta % 2];
	}

	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		if (world.isAirBlock(x, y, z) && world.isAirBlock(x, y + 1, z))
		{
			return super.canPlaceBlockAt(world, x, y, z) && this.canThisPlantGrowOnThisBlockID(world.getBlockId(x, y - 1, z));
		}
		return false;
	}

	protected boolean canThisPlantGrowOnThisBlockID(int id)
	{
		return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.blockSnow.blockID;
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, int newid)
	{
		super.onNeighborBlockChange(world, x, y, z, newid);
		if (newid != this.blockID)
		{
			this.onPlantChange(world, x, y, z);
		}
	}

	protected void onPlantChange(World world, int x, int y, int z)
	{
		if (!this.canBlockStay(world, x, y, z))
		{
			int meta = world.getBlockMetadata(x, y, z);
			this.onPlantDestroy(world, x, y, z, meta);
			if (meta == 0)
			{
				this.dropBlockAsItem_do(world, x, y, z, new ItemStack(Festivities.mintLeaf));				
			}
			world.setBlockToAir(x, y, z);
		}
	}

	protected void onPlantDestroy(World world, int x, int y, int z, int meta)
	{
		if (meta == 0)
		{
			if (world.getBlockId(x, y + 1, z) == this.blockID)
			{
				world.setBlockToAir(x, y + 1, z);
			}
		}
		else
		{
			if (world.getBlockId(x, y - 1, z) == this.blockID)
			{
				world.setBlockToAir(x, y - 1, z);
			}
		}
	}

	public int getMobilityFlag()
	{
		return 1;
	}

	public boolean canBlockStay(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 1)
		{
			y--;
		}
		if (this.canThisPlantGrowOnThisBlockID(world.getBlockId(x, y - 1, z)))
		{
			if (world.getBlockId(x, y, z) == this.blockID && world.getBlockId(x, y + 1, z) == this.blockID && world.getBlockMetadata(x, y, z) == 0 && world.getBlockMetadata(x, y + 1, z) == 1)
			{
				return true;
			}
		}
		return false;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public int getRenderType()
	{
		return 1;
	}

	@Override
	public String[] getTip(EntityPlayer player, ItemStack stack)
	{
		return new String[] { "Oops...", "Use the mint item", "This is a technical block" };
	}

	@Override
	public String[] getShiftTip(EntityPlayer player, ItemStack stack)
	{
		return null;
	}

	public int idDropped(int meta, Random rand, int fort)
	{
		return meta == 0 ? Festivities.mintLeaf.itemID : 0;
	}

	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Festivities.mintLeaf.itemID;
	}

	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player)
	{
		this.onPlantDestroy(world, x, y, z, meta);
	}
}
