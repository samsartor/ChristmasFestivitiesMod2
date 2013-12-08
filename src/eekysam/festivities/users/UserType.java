package eekysam.festivities.users;

import java.util.ArrayList;
import java.util.List;

public class UserType
{
	public static List<UserType> list = new ArrayList<UserType>();
	
	public static UserType sam = new UserType().setMembers("eekysam").setMsg("Welcome Back Sam!").setCreator();
	public static UserType bonez = new UserType().setMembers("BoneZa5").setMsg("Cool Blocks Bro!").setCreator();
	public static UserType lily = new UserType().setMembers("catinabukkit").setMsg("What do you think?").setCreator();
	public static UserType contributer  = new UserType().setMembers("zeklo", "WokenUpBySausage").setMsg("Thank You!").setCreator();
	public static UserType crafted = new UserType().setMembers("setosorcerer", "deadlox").setMsg("Thank You for Trying out the Mod!");
	public static UserType youtuber = new UserType().setMembers("captainsparklez", "xisumavoid", "xisuma", "Antvenom", "Skydoesminecraft", "Sethbling", "Ethoslab", "ScorpioDan").setMsg("Thank You for Playing the Christmas Festivities Mod!");
	public static UserType mojang  = new UserType().setMembers("notch", "dinnerbone", "grum").setMsg("Minecraft is Christmas!");
	
	private String[] included = new String[0];
	public String msg = "";
	private int id;
	protected boolean creator = false;
	
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
	
	protected UserType setCreator()
	{
		this.creator = true;
		return this;
	}
	
	public boolean isCreator()
	{
		return this.creator;
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
