package eekysam.festivities.kringle.biome;

import net.minecraft.block.Block;

public class BiomeGenKringleIce extends BiomeGenKringle
{
	public BiomeGenKringleIce(int id, float candy, float plant)
	{
		super(id, candy, plant);

		this.topBlock = Block.ice.blockID;
		this.fillerBlock = Block.ice.blockID;

		this.getDecorator().peppermintPolesPerChunk = 0;
	}

}
