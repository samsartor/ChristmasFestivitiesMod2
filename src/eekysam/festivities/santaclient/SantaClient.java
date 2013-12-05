package eekysam.festivities.santaclient;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

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

		NBTBase base = NBTBase.readNamedTag(instream);
		if (base instanceof NBTTagCompound)
		{
			return (NBTTagCompound) base;
		}
		return null;
	}

	protected ItemStack doSendAndReceiveItem(ItemStack item, String url) throws IOException
	{
		NBTTagCompound compound = new NBTTagCompound();
		item.writeToNBT(compound);
		NBTTagCompound ret = this.sendAndReceiveNBT(compound, url);
		return ItemStack.loadItemStackFromNBT(ret);
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
