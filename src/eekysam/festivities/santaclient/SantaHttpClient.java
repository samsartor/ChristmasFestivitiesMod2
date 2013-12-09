package eekysam.festivities.santaclient;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import eekysam.festivities.Festivities;

class SantaHttpClient extends SantaClient
{
	protected SantaHttpClient()
	{

	}

	public static SantaClient getHttpClient()
	{
		return new SantaHttpClient();
	}

	private InputStream doPostData(byte[] data, String url, String username) throws IOException
	{
		URL u = new URL(url + "?user=" + username);
		HttpURLConnection connection = (HttpURLConnection) u.openConnection();

		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/gzip");

		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		GZIPOutputStream gz = new GZIPOutputStream(ba);
		gz.write(data);
		gz.close();
		byte[] bytes = ba.toByteArray();

		if (Festivities.DEBUG)
		{
			FileOutputStream testout = new FileOutputStream(new File("santaItem.dat"));
			testout.write(bytes);
			testout.close();
		}

		connection.setRequestProperty("Content-Length", "" + Integer.toString(bytes.length));

		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.write(bytes);
		wr.flush();
		wr.close();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
		{
			return new GZIPInputStream(connection.getInputStream());
		}
		else
		{
			return null;
		}

	}

	@Override
	protected DataInput postData(byte[] data, String url, String username) throws IOException
	{
		InputStream input = this.doPostData(data, url, username);
		if (input != null)
		{
			return new DataInputStream(input);
		}
		return null;
	}
}
