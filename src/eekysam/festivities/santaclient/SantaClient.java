/*
package eekysam.festivities.santaclient;


import java.io.IOException;
import java.io.InputStream;

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

public class SantaClient
{
	protected SantaClient()
	{
		
	}
	
	public static SantaClient getClient()
	{
		return new SantaClient();
	}
	
	private InputStream postData(byte[] data, String url) throws IOException
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
}
 */
