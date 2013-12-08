package eekysam.festivities.santaclient;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import eekysam.festivities.Festivities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public abstract class SantaClient
{
	public static SantaClient getClient()
	{
		return SantaHttpClient.getHttpClient();
	}

	protected abstract DataInput postData(byte[] data, String url) throws IOException;

	public NBTTagCompound sendAndReceiveNBT(NBTTagCompound item, String url) throws IOException
	{
		DataInput instream;
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		DataOutput outstream = new DataOutputStream(bytestream);
		NBTBase.writeNamedTag(item, outstream);
		byte[] bytes = bytestream.toByteArray();
		instream = this.postData(bytes, url);

		if (instream != null)
		{
			NBTBase base = NBTBase.readNamedTag(instream);
			if (base instanceof NBTTagCompound)
			{
				return (NBTTagCompound) base;
			}
		}
		return null;
	}

	protected ItemStack doSendAndReceiveItem(ItemStack item, String url, String username) throws IOException
	{
		NBTTagCompound tags = item.getTagCompound();
		if (tags == null)
		{
			tags = new NBTTagCompound();
		}
		
		tags.setString("santaname", username);
		
		NBTTagCompound disp = tags.getCompoundTag("display");
		NBTTagList lore = disp.getTagList("Lore");
		lore.appendTag(new NBTTagString("", "From: " + username));
		disp.setTag("Lore", lore);
		tags.setTag("display", disp);
		
		item.setTagCompound(tags);
		
		return this.doSendAndReceiveItem(item, url);
	}
	
	protected ItemStack doSendAndReceiveItem(ItemStack item, String url) throws IOException
	{
		item = Festivities.instance.convertFromConfiged(item);
		NBTTagCompound compound = new NBTTagCompound();
		item.writeToNBT(compound);
		NBTTagCompound ret = this.sendAndReceiveNBT(compound, url);
		if (ret != null)
		{
			return Festivities.instance.convertToConfiged(ItemStack.loadItemStackFromNBT(ret));
		}
		return null;
	}

	public ItemStack sendAndReceiveItem(ItemStack item, String url, String username)
	{
		try
		{
			return this.doSendAndReceiveItem(item, url, username);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public ItemStack sendAndReceiveItem(ItemStack item, String url)
	{
		try
		{
			return this.doSendAndReceiveItem(item, url);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
