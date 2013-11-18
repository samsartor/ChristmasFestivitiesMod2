package eekysam.utils;

import java.awt.Color;

public class MakeColor
{
	public static int RGB(int r, int g, int b)
	{
		return (new Color(r, g, b)).getRGB();
	}
	
	public static int RGB(float r, float g, float b)
	{
		return (new Color(r, g, b)).getRGB();
	}
	
	public static int RGB(String hex)
	{
		return (int) Integer.parseInt(hex, 16);
	}
	
	public static int HSV(int h, int s, int v)
	{
		return HSV(h / 360.0F, s / 100.0F, v / 100.0F);
	}
	
	public static int HSV(float h, float s, float v)
	{
		return Color.getHSBColor(h, s, v).getRGB();
	}
}
