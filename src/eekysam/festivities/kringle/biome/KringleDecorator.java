package eekysam.festivities.kringle.biome;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CUSTOM;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LAKE;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIAMOND;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD;
import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import eekysam.festivities.kringle.Kringle;
import eekysam.festivities.kringle.gen.feature.WorldGenChristmasTree;
import eekysam.festivities.kringle.gen.feature.WorldGenIce;
import eekysam.festivities.kringle.gen.feature.WorldGenPeppermintPole;

public class KringleDecorator extends BiomeDecorator
{
	public int peppermintPolesPerChunk;
	public WorldGenerator peppermintPoleGen;
	
	public int christmasTreesPerChunk;
	public WorldGenerator christmasTreeGen;
	
	public KringleDecorator(BiomeGenBase biome)
	{
		super(biome);
		this.reset();
		
		this.coalGen = new WorldGenMinable(Block.oreCoal.blockID, 10, Kringle.getStone());
		this.goldGen = new WorldGenMinable(Block.oreGold.blockID, 10, Kringle.getStone());
		this.diamondGen = new WorldGenMinable(Block.oreDiamond.blockID, 4, Kringle.getStone());
		
		this.peppermintPoleGen = new WorldGenPeppermintPole(5, 9, 10);
		this.christmasTreeGen = new WorldGenChristmasTree(18, 8, 5, 3, 20);
		
		this.peppermintPolesPerChunk = 4;
		
		this.christmasTreesPerChunk = 0;
	}
	
	public void reset()
	{
		this.sandGen = null;
		this.gravelAsSandGen = null;
		this.dirtGen = null;
		this.gravelGen = null;
		this.ironGen = null;
		this.redstoneGen = null;
		this.lapisGen = null;
		this.plantYellowGen = null;
		this.plantRedGen = null;
		this.mushroomBrownGen = null;
		this.mushroomRedGen = null;
		this.bigMushroomGen = null;
		this.reedGen = null;
		this.cactusGen = null;
		this.waterlilyGen = null;
		this.flowersPerChunk = 0;
		this.grassPerChunk = 0;
		this.sandPerChunk = 0;
		this.sandPerChunk2 = 0;
		this.clayPerChunk = 0;
		this.generateLakes = false;
	}

	protected void decorate()
	{
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));

		this.generateOres();
		int x;
		int y;
		int z;

		boolean doGen;

		int trees = this.treesPerChunk;

		if (this.randomGenerator.nextInt(10) == 0)
		{
			++trees;
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, TREE);
		for (int i = 0; doGen && i < trees; ++i)
		{
			x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			WorldGenerator worldgenerator = this.biome.getRandomWorldGenForTrees(this.randomGenerator);
			worldgenerator.setScale(1.0D, 1.0D, 1.0D);
			worldgenerator.generate(this.currentWorld, this.randomGenerator, x, this.currentWorld.getHeightValue(x, z), z);
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, LAKE);
		if (doGen && this.generateLakes)
		{
			for (int i = 0; i < 50; ++i)
			{
				x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				y = this.randomGenerator.nextInt(this.randomGenerator.nextInt(120) + 8);
				z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				(new WorldGenIce(Block.ice.blockID)).generate(this.currentWorld, this.randomGenerator, x, y, z);
			}
		}
		
        doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, CUSTOM);
        for (int i = 0; doGen && i < this.peppermintPolesPerChunk; ++i)
        {
            x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            y = this.randomGenerator.nextInt(128);
            z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.peppermintPoleGen.generate(this.currentWorld, this.randomGenerator, x, y, z);
        }
        
        doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, CUSTOM);
        for (int i = 0; doGen && i < this.christmasTreesPerChunk; ++i)
        {
            x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            y = this.randomGenerator.nextInt(128);
            z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.christmasTreeGen.generate(this.currentWorld, this.randomGenerator, x, y, z);
        }

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
	}

	/**
	 * Generates ores in the current chunk
	 */
	protected void generateOres()
	{
		MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));
		if (TerrainGen.generateOre(currentWorld, randomGenerator, coalGen, chunk_X, chunk_Z, COAL))
		{
			this.genStandardOre1(20, this.coalGen, 0, 128);
		}
		if (TerrainGen.generateOre(currentWorld, randomGenerator, goldGen, chunk_X, chunk_Z, GOLD))
		{
			this.genStandardOre1(2, this.goldGen, 0, 32);
		}
		if (TerrainGen.generateOre(currentWorld, randomGenerator, diamondGen, chunk_X, chunk_Z, DIAMOND))
		{
			this.genStandardOre1(1, this.diamondGen, 0, 16);
		}
		MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
	}
}
