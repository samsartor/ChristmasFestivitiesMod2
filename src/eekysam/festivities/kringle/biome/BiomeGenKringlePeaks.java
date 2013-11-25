package eekysam.festivities.kringle.biome;

public class BiomeGenKringlePeaks extends BiomeGenKringle
{
	public BiomeGenKringlePeaks(int id, float candy, float plant)
	{
		super(id, candy, plant);

		this.getDecorator().peppermintPolesPerChunk = 8;
	}
}
