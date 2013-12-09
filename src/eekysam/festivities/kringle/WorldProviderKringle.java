package eekysam.festivities.kringle;

import net.minecraft.block.Block;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eekysam.festivities.Festivities;
import eekysam.festivities.kringle.biome.WorldChunkManagerKringle;
import eekysam.festivities.kringle.gen.ChunkProviderKringle;

public class WorldProviderKringle extends WorldProvider
{
	@Override
	public void registerWorldChunkManager()
	{
		WorldChunkManagerKringle k = new WorldChunkManagerKringle();
		k.makeNoise(this.getSeed());
		this.worldChunkMgr = k;
		this.dimensionId = Festivities.kringleId;
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderKringle(worldObj, worldObj.getSeed());
	}

	@Override
	public String getDimensionName()
	{
		return "Kringle";
	}

	@Override
	public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
	{
		int i = this.worldObj.getBlockId(x, y, z);
		return (i == Block.waterStill.blockID || i == Block.waterMoving.blockID) && this.worldObj.getBlockMetadata(x, y, z) == 0;
	}

	@Override
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

	/**
	 * Calculates the angle of sun and moon in the sky relative to a specified
	 * time (usually worldTime)
	 */
	@Override
	public float calculateCelestialAngle(long par1, float par3)
	{
		return 0.5F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float par1)
	{
		return 1.0F;
	}

	@Override
	public String getWelcomeMessage()
	{
		return "Entering the Kringle ... This will take a while";
	}
}
