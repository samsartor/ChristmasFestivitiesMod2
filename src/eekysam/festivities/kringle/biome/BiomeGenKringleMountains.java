package eekysam.festivities.kringle.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenKringleMountains extends BiomeGenKringle
{
	public BiomeGenKringleMountains(int id, float candy, float plant)
	{
		super(id, candy, plant);
		
		this.topBlock = Block.grass.blockID;
		this.fillerBlock = Block.dirt.blockID;
		
		this.getDecorator().peppermintPolesPerChunk = 6;
		this.getDecorator().treesPerChunk = 5;
	}
	
    public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
    {
        return (WorldGenerator)(par1Random.nextInt(3) == 0 ? new WorldGenTaiga1() : new WorldGenTaiga2(false));
    }

}
