package eekysam.festivities.kringle.biome;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.DeferredBiomeDecorator;

public abstract class BiomeGenKringle extends BiomeGenBase
{
	public static BiomeGenKringle kringlePlains;
	public static BiomeGenKringle kringleIce;
	public static BiomeGenKringle kringleMountains;
	public static BiomeGenKringle kringleChristmasForrest;
	public static BiomeGenKringle kringleForrest;
	public static BiomeGenKringle kringlePeaks;
	
	public float candy;
	public float plant;
	
	public static List<BiomeGenKringle> kringleBiomes = new ArrayList<BiomeGenKringle>();
	
	public static void registerBiomes(int base)
	{
		kringlePlains = (BiomeGenKringle) new BiomeGenKringlePlains(base + 1, 0.75F, 0.3F).setBiomeName("Plains").setMinMaxHeight(0.1F, 0.3F);
		//GameRegistry.addBiome(kringlePlains);
		kringleIce = (BiomeGenKringle) new BiomeGenKringleIce(base + 2, 0.1F, 0.2F).setBiomeName("Ice").setMinMaxHeight(0.05F, 0.1F);
		//GameRegistry.addBiome(kringleIce);
		kringleMountains = (BiomeGenKringle) new BiomeGenKringleMountains(base + 3, 0.4F, 0.7F).setBiomeName("Mountains").setMinMaxHeight(0.3F, 0.9F);
		//GameRegistry.addBiome(kringleMountains);
		kringleChristmasForrest = (BiomeGenKringle) new BiomeGenKringleChristmasForrest(base + 4, 0.6F, 0.7F).setBiomeName("Christmas Forrest").setMinMaxHeight(0.3F, 0.6F);
		//GameRegistry.addBiome(kringleChristmasForrest);
		kringleForrest = (BiomeGenKringle) new BiomeGenKringleForrest(base + 5, 0.2F, 0.9F).setBiomeName("Forrest").setMinMaxHeight(0.4F, 0.6F);
		//GameRegistry.addBiome(kringleForrest);
		kringlePeaks = (BiomeGenKringle) new BiomeGenKringlePeaks(base + 6, 0.2F, 0.4F).setBiomeName("Peaks").setMinMaxHeight(0.1F, 1.3F);
		//GameRegistry.addBiome(kringlePeaks);
	}
	
	public int topBlock;
	public int fillerBlock;
	
	public BiomeGenKringle(int id, float candy, float plant)
	{
        super(id);
        this.candy = candy;
        this.plant = plant;
        this.setEnableSnow();
        this.setColor(0xFF0511);
        this.setTemperatureRainfall(0.0F, 0.5F);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        
        this.topBlock = Block.blockSnow.blockID;
        this.fillerBlock = Block.blockSnow.blockID;
        
        this.kringleBiomes.add(this);
	}
	
	public KringleDecorator getDecorator()
	{
		return (KringleDecorator) this.theBiomeDecorator;
	}
	
    public BiomeDecorator createBiomeDecorator()
    {   
        return new KringleDecorator(this);
    }

    @SideOnly(Side.CLIENT)

    /**
     * takes temperature, returns color
     */
    public int getSkyColorByTemp(float temp)
    {
    	float s = temp * -0.5F;
    	float v = temp * 0.2F - 0.1F;
        return Color.getHSBColor(0.6F, 0.8F + s, 0.27F + v).getRGB();
    }


    /**
     * Returns true if the biome have snowfall instead a normal rain.
     */
    public boolean getEnableSnow()
    {
        return true;
    }

    /**
     * Return true if the biome supports lightning bolt spawn, either by have the bolts enabled and have rain enabled.
     */
    public boolean canSpawnLightningBolt()
    {
        return false;
    }

    /**
     * Checks to see if the rainfall level of the biome is extremely high
     */
    public boolean isHighHumidity()
    {
        return false;
    }

    public void decorate(World world, Random rand, int chunkX, int chunkZ)
    {
        super.decorate(world, rand, chunkX, chunkZ);
    }

    @SideOnly(Side.CLIENT)

    public int getBiomeGrassColor()
    {
    	return  Color.getHSBColor(0.55F, 0.1F, 1.0F).getRGB();
    }

    @SideOnly(Side.CLIENT)

    public int getBiomeFoliageColor()
    {
    	return super.getBiomeFoliageColor();
    }

    public BiomeDecorator getModdedBiomeDecorator(BiomeDecorator original)
    {
        return original;
    }

    public int getWaterColorMultiplier()
    {
    	return super.getWaterColorMultiplier();
    }
    
    public BiomeGenBase setBiomeName(String name)
    {
    	return super.setBiomeName("Kringle " + name);
    }
    
    public static BiomeGenKringle getBiome(float plant, float candy)
    {
    	plant /= 2;
    	candy /= 2;
    	float dist = 999.9F;
    	BiomeGenKringle biome = null;
    	for (int i = 0; i < kringleBiomes.size(); i++)
    	{
    		BiomeGenKringle b = kringleBiomes.get(i);
    		float ca = b.candy - candy;
    		float pl = b.plant - plant;
    		float d = ca * ca + pl * pl;
    		if (d < dist)
    		{
    			dist = d;
    			biome = b;
    		}
    	}
    	return biome;
    }
}
