package eekysam.festivities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;



public final class FestivitiesTab extends CreativeTabs
{
public FestivitiesTab(int par1, String par2Str)
{
super(par1, par2Str);
}


@SideOnly(Side.CLIENT)
public int getTabIconItemIndex()
{

return Festivities.moreCookies.itemID;
}

public String getTranslatedTabLabel()
{
return "Christmas Festives 2";
}
}