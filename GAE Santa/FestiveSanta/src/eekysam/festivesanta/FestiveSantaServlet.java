package eekysam.festivesanta;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class FestiveSantaServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		System.out.println("Get");
		res.getWriter().println("Christmas Festivities Mod 2");
		res.getWriter().println("by eekysam");
		res.getWriter().println("");
		res.getWriter().println("Secret Santa Item Server");
		res.getWriter().println("on Google App Engine");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		System.out.println("Post");
		InputStream input = req.getInputStream();
		OutputStream output = res.getOutputStream();
		res.setContentType("application/gzip");
		String dat = "";
		boolean comma = false;
		while (true)
		{
			int i = input.read();
			if (i >= 0)
			{
				if (comma)
				{
					dat += ", ";
				}
				else
				{
					comma = true;
				}
				dat += i;
				output.write(i);
			}
			else
			{
				break;
			}
		}
		System.out.println(dat);
	}
}
