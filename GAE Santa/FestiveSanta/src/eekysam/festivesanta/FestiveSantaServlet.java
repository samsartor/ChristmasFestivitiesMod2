package eekysam.festivesanta;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

@SuppressWarnings("serial")
public class FestiveSantaServlet extends HttpServlet
{
	private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	/**
	 * Default number of shards.
	 */
	private static final int NUM_SHARDS = 10;

	/**
	 * A random number generator, for distributing writes across shards.
	 */
	private final Random generator = new Random();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		System.out.println("Get");
		res.getWriter().println("Christmas Festivities Mod 2");
		res.getWriter().println("by eekysam");
		res.getWriter().println("");
		res.getWriter().println("Secret Santa Item Server");
		res.getWriter().println("on Google App Engine");
		res.getWriter().println("");
		res.getWriter().println("POST Count: " + this.getCount());
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		System.out.println("Post");

		InputStream input = req.getInputStream();
		OutputStream output = res.getOutputStream();
		res.setContentType("application/gzip");

		byte[] reqbytes = this.readInput(input);
		int shard = this.generator.nextInt(NUM_SHARDS);
		this.increment(shard);
		byte[] lastitem = this.getData(shard);

		if (lastitem.length == 0)
		{
			lastitem = reqbytes;
		}

		this.saveData(reqbytes, shard);

		output.write(lastitem);
	}

	public void saveData(byte[] data, int shard)
	{
		Key shardKey = KeyFactory.createKey("Santa", Integer.toString(shard));

		Blob blob = new Blob(data);

		Transaction tx = datastore.beginTransaction();
		Entity dataentity = new Entity(shardKey);

		dataentity.setProperty("data", blob);

		datastore.put(tx, dataentity);

		tx.commit();
	}

	public byte[] getData(int shard)
	{
		Key shardKey = KeyFactory.createKey("Santa", Integer.toString(shard));

		Transaction tx = datastore.beginTransaction();
		Entity dataentity;

		byte[] data = new byte[0];

		try
		{
			dataentity = datastore.get(tx, shardKey);
		}
		catch (EntityNotFoundException e)
		{
			tx.rollback();
			return data;
		}

		Object rawdata = dataentity.getProperty("data");

		if (rawdata instanceof Blob)
		{
			Blob blob = (Blob) rawdata;
			tx.commit();
			return blob.getBytes();
		}
		
		tx.rollback();
		
		return data;
	}

	public byte[] readInput(InputStream stream) throws IOException
	{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		while (true)
		{
			int i = stream.read();
			if (i >= 0)
			{
				buffer.write(i);
			}
			else
			{
				break;
			}
		}

		buffer.flush();

		return buffer.toByteArray();
	}

	public long getCount()
	{
		long sum = 0;

		Query query = new Query("Counter");
		for (Entity e : datastore.prepare(query).asIterable())
		{
			sum += (Long) e.getProperty("count");
		}

		return sum;
	}

	/**
	 * Increment the value of this sharded counter.
	 */
	public void increment(int shardNum)
	{
		Key shardKey = KeyFactory.createKey("Counter", Integer.toString(shardNum));

		Transaction tx = datastore.beginTransaction();
		Entity shard;
		try
		{
			shard = datastore.get(tx, shardKey);
			long count = (Long) shard.getProperty("count");
			shard.setUnindexedProperty("count", count + 1L);
		}
		catch (EntityNotFoundException e)
		{
			shard = new Entity(shardKey);
			shard.setUnindexedProperty("count", 1L);
		}
		datastore.put(tx, shard);
		tx.commit();
	}
}
