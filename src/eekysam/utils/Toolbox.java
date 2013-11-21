package eekysam.utils;

public class Toolbox
{
	public static String[] mergeStringArrays(String[] ar1, String[] ar2)
	{
		String[] ar = new String[ar1.length + ar2.length];
		int j = 0;
		for (int i = 0; i < ar1.length; i++)
		{
			ar[j] = ar1[i];
			j++;
		}
		for (int i = 0; i < ar2.length; i++)
		{
			ar[j] = ar2[i];
			j++;
		}
		return ar;
	}
}
