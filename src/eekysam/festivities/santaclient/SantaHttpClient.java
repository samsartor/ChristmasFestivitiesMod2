package eekysam.festivities.santaclient;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

class SantaHttpClient extends SantaClient
{
	protected SantaHttpClient()
	{

	}

	public static SantaClient getHttpClient()
	{
		return new SantaHttpClient();
	}

	private InputStream doPostData(byte[] data, String url) throws IOException
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);

		HttpEntity entity = EntityBuilder.create().setBinary(data).gzipCompress().build();
		httppost.setEntity(entity);

		CloseableHttpResponse response = httpclient.execute(httppost);
		GzipDecompressingEntity repentity = new GzipDecompressingEntity(response.getEntity());

		if (entity != null)
		{
			InputStream instream = repentity.getContent();
			return instream;
		}

		return null;
	}

	protected DataInput postData(byte[] data, String url) throws IOException
	{
		return new DataInputStream(this.doPostData(data, url));
	}
}
