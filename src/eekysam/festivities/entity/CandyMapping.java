package eekysam.festivities.entity;

import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraftforge.common.IExtendedEntityProperties;

public class CandyMapping
{
	private static HashMap<Class, Class> toCandy = new HashMap<Class, Class>();
	private static HashMap<Class, Class> toNormal = new HashMap<Class, Class>();
	
	public static boolean addMapping(Class<? extends Entity> normal, Class<? extends Entity> candy)
	{
		if (isCandy(candy) && !isCandy(normal))
		{
			toCandy.put(normal, candy);
			toNormal.put(candy, normal);
			return true;
		}
		System.err.println("Entities are not of proper type");
		return false;
	}
	
	public static Class<? extends Entity> getCandy(Class<? extends Entity> normal)
	{
		return toCandy.get(normal);
	}
	
	public static Class<? extends Entity> getNormal(Class<? extends Entity> candy)
	{
		return toNormal.get(candy);
	}
	
	public static String getCandyName(Class<? extends Entity> normal)
	{
		return getName(getCandy(normal));
	}
	
	public static String getNormalName(Class<? extends Entity> candy)
	{
		return getName(getNormal(candy));
	}
	
	private static String getName(Class<? extends Entity> entity)
	{
		return (String) EntityList.classToStringMapping.get(entity);
	}
	
	public static boolean isCandy(Class<? extends Entity> entity)
	{
		return entity.isAssignableFrom(ICandyMob.class);
	}
}
