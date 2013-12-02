package eekysam.utils;

import java.util.ArrayList;
import java.util.List;

public class Timer
{
	private long startTime;
	private boolean timing = false;
	private List<Long> laps = new ArrayList<Long>();
	
	public double getTotalmili()
	{
		if (this.timing)
		{
			(new Exception("Still timing!")).printStackTrace();
			return -1;
		}
		return this.getTotalnano() * 0.000001D;
	}
	
	public long[] getLaps()
	{
		if (this.timing)
		{
			(new Exception("Still timing!")).printStackTrace();
			return null;
		}
		long[] laps = new long[this.laps.size()];
		for (int i = 0; i < this.laps.size(); i++)
		{
			laps[i] = this.laps.get(i);
		}
		return laps;
	}
	
	public long getTotalnano()
	{
		if (this.timing)
		{
			(new Exception("Still timing!")).printStackTrace();
			return -1;
		}
		long sum = 0;
		for (int i = 0; i < this.laps.size(); i++)
		{
			sum += this.laps.get(i);
		}
		return sum;
	}
	
	public void start()
	{
		if (this.timing)
		{
			return;
		}
		this.timing = true;
		this.startTime = System.nanoTime();
	}
	
	public void pause()
	{
		long t = System.nanoTime();
		if (!this.timing)
		{
			(new Exception("Not timing!")).printStackTrace();
			return;
		}
		this.timing = false;
		long time = t - startTime;
		if (time < 0)
		{
			time = Long.MAX_VALUE - startTime + t - Long.MIN_VALUE;
		}
		laps.add(time);
	}
	
	public long end()
	{
		if (this.timing)
		{
			this.pause();
		}
		long nano = this.getTotalnano();
		this.clear();
		return nano;
	}
	
	public void clear()
	{
		this.laps.clear();
	}
}
