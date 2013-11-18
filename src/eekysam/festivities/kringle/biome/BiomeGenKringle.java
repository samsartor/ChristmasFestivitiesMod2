package eekysam.festivities.kringle.biome;

import java.awt.Color;
import java.util.List;
import java.util.Random;

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
	public int topBlock;
	public int fillerBlock;
	
	public BiomeGenKringle(int id)
	{
        super(id);
        this.setEnableSnow();
        this.setColor(0xFF0511);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        
        this.topBlock = Block.blockSnow.blockID;
        this.fillerBlock = Block.blockSnow.blockID;
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
    	return super.setBiomeName("Kringle_" + name);
    }
}
