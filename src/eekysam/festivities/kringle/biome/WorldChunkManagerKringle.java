package eekysam.festivities.kringle.biome;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

//TODO Make system for multiple biomes
public class WorldChunkManagerKringle extends WorldChunkManager
{
    private BiomeGenBase biomeToUse;
    
    private float kringleTemp = 0.0F;
    private float kringleHumid = 0.5F;

    public WorldChunkManagerKringle()
    {
        this.biomeToUse = BiomeGenKringle.kringlePlains;
    }

    public BiomeGenBase getBiomeGenAt(int par1, int par2)
    {
        return this.biomeToUse;
    }

    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
    {
        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
        {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }

        Arrays.fill(par1ArrayOfBiomeGenBase, 0, par4 * par5, this.biomeToUse);
        return par1ArrayOfBiomeGenBase;
    }

    public float[] getTemperatures(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
    {
        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
        {
            par1ArrayOfFloat = new float[par4 * par5];
        }

        Arrays.fill(par1ArrayOfFloat, 0, par4 * par5, this.kringleTemp);
        return par1ArrayOfFloat;
    }

    public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
    {
        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
        {
            par1ArrayOfFloat = new float[par4 * par5];
        }

        Arrays.fill(par1ArrayOfFloat, 0, par4 * par5, kringleHumid);
        return par1ArrayOfFloat;
    }

    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
    {
        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
        {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }

        Arrays.fill(par1ArrayOfBiomeGenBase, 0, par4 * par5, this.biomeToUse);
        return par1ArrayOfBiomeGenBase;
    }

    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6)
    {
        return this.loadBlockGeneratorData(par1ArrayOfBiomeGenBase, par2, par3, par4, par5);
    }

    public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random)
    {
        return par4List.contains(this.biomeToUse) ? new ChunkPosition(par1 - par3 + par5Random.nextInt(par3 * 2 + 1), 0, par2 - par3 + par5Random.nextInt(par3 * 2 + 1)) : null;
    }

    public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
    {
        return par4List.contains(this.biomeToUse);
    }
}
