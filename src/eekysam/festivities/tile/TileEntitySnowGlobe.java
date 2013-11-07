package eekysam.festivities.tile;

import net.minecraft.block.BlockContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntitySnowglobe extends TileEntity
{
	public int type;
	
	public TileEntitySnowglobe(World world, BlockContainer block)
	{
		this.blockType = block;
		this.worldObj = world;
		this.type = this.worldObj.rand.nextInt(16);
	}
}
