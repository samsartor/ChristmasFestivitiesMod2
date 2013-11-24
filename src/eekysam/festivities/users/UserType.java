package eekysam.festivities.users;

import java.util.ArrayList;
import java.util.List;

public class UserType
{
	public static List<UserType> list = new ArrayList<UserType>();
	
	public static UserType sam = new UserType().setMembers("eekysam").setMsg("Welcome Back Sam!");
	public static UserType bonez = new UserType().setMembers("BoneZa5").setMsg("Nice to see that you have stopped slacking off :P JK LOL");
	public static UserType lily = new UserType().setMembers("catinabukkit").setMsg("What do you think?");
	public static UserType contributer  = new UserType().setMembers("zeklo").setMsg("Thank You!");
	public static UserType youtuber = new UserType().setMembers("captainsparklez", "xisumavoid", "xisuma", "Antvenom", "Skydoesminecraft", "Sethbling", "Ethoslab", "ScorpioDan", "Setosorcerer").setMsg("Thank You for Playing the Christmas Festivities Mod!");
	public static UserType forums = new UserType().setMembers("triplehex", "XtremelyNooby").setMsg("Christmas Festivities Mod!");
	public static UserType mojang  = new UserType().setMembers("notch", "dinnerbone", "grum").setMsg("Minecraft is Christmas!");
	
	private String[] included = new String[0];
	public String msg = "";
	private int id;
	
	protected UserType()
	{
		this.id = list.size();
		list.add(this);
	}
	
	protected UserType setMembers(String... members)
	{
		this.included = members;
		return this;
	}
	
	protected UserType setMsg(String msg)
	{
		this.msg = msg;
		return this;
	}
	
	public static UserType getUserType(String name)
	{
		name = name.toLowerCase();
		for (int i = 0; i < list.size(); i++)
		{
			UserType type = list.get(i);
			if (type.included != null)
			{
				for (int j = 0; j < type.included.length; j++)
				{
					if (name.equals(type.included[j].toLowerCase()))
					{
						return type;
					}
				}
			}
		}
		return null;
	}
	
	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (obj instanceof UserType)
			{
				UserType type = (UserType) obj;
				
				return type.id == this.id;
			}
		}
		return false;
	}
}
