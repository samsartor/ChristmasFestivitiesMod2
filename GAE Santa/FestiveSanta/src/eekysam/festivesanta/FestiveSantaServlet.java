package eekysam.festivesanta;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class FestiveSantaServlet extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		InputStream input = req.getInputStream();
		OutputStream output = res.getOutputStream();
		res.setContentType("application/gzip");
	}
}
