package eekysam.festivities.kringle;

import eekysam.festivities.Festivities;
import eekysam.festivities.kringle.biome.WorldChunkManagerKringle;
import eekysam.festivities.kringle.gen.ChunkProviderKringle;
import net.minecraft.block.Block;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderKringle extends WorldProvider
{
	public void registerWorldChunkManager()
	{
		this.worldChunkMgr = new WorldChunkManagerKringle();
		this.dimensionId = Festivities.kringleId;
	}

	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderKringle(worldObj, worldObj.getSeed());
	}

	public String getDimensionName()
	{
		return "Kringle";
	}

	public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
	{
		int i = this.worldObj.getBlockId(x, y, z);
		return (i == Block.waterStill.blockID || i == Block.waterMoving.blockID) && this.worldObj.getBlockMetadata(x, y, z) == 0;
	}

	public boolean canSnowAt(int x, int y, int z)
	{
		int l = this.worldObj.getBlockId(x, y - 1, z);
		int i1 = this.worldObj.getBlockId(x, y, z);

		if (i1 == 0 && Block.snow.canPlaceBlockAt(this.worldObj, x, y, z) && l != 0 && l != Block.ice.blockID && Block.blocksList[l].blockMaterial.blocksMovement())
		{
			return true;
		}
		return false;
	}
}
