package eekysam.festivities.santaclient;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;

public abstract class SantaClient
{	
	public static SantaClient getClient()
	{
		return SantaHttpClient.getHttpClient();
	}
	
	protected abstract DataInput postData(byte[] data, String url) throws IOException;
}
