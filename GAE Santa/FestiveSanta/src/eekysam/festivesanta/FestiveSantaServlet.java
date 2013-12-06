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
		res.getWriter().println("Testing...");
		res.getWriter().println("1");
		res.getWriter().println("2");
		res.getWriter().println("3");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		System.out.println("Post");
		InputStream input = req.getInputStream();
		OutputStream output = res.getOutputStream();
		res.setContentType("application/gzip");
		while (true)
		{
			int i = input.read();
			if (i >= 0)
			{
				 output.write(i);
			}
			else
			{
				break;
			}
		}
	}
}
