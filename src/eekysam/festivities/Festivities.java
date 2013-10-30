package eekysam.festivities;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "festivities", name = "ChristmasFestivities", version = "0.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Festivities
{
	@Instance("Festivities")
	public static Festivities instance;
}
