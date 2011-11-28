package nl.profict.albero.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.profict.albero.utilities.Logger;
import nl.profict.albero.utilities.StringUtilities;

/**
 * Imitates the Albero servlet, which is useful if the client would like to use it from JavaScript and it is running on
 * a different server.
 * <p>
 * The location of the Albero servlet can be set using init parameter {@value #ALBERO_SERVLET_LOCATION}.
 *
 * @author levi_h
 */
public class ProxyServlet extends HttpServlet {
	private String alberoServletLocation;

	private Logger logger;

	/**
	 * Creates a proxy servlet.
	 */
	public ProxyServlet() {
		logger = Logger.get(getClass());
	}

	@Override
	public void init() throws ServletException {
		logger.debug("destroying proxy servlet");

		alberoServletLocation = getInitParameter(ALBERO_SERVLET_LOCATION);

		if (alberoServletLocation == null) {
			throw new ServletException(StringUtilities.join("the location of the Albero servlet was not set; ",
				"this can be done using the '", ALBERO_SERVLET_LOCATION, "' init parameter"));
		} else {
			logger.debug("location of Albero servlet: ", alberoServletLocation);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String queryString = request.getQueryString();

		logger.debug("proxying ", queryString);

		URLConnection connection = new URL(alberoServletLocation + "?" + queryString).openConnection();

		logger.debug("opened connection to Albero servlet, now reading");

		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);

		InputStream inputStream = connection.getInputStream();

		try {
			OutputStream outputStream = response.getOutputStream();

			try {
				byte[] buffer = new byte[4096];
				int bytesRead;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				logger.debug("finished proxying ", queryString);
			} finally {
				outputStream.close();
			}
		} finally {
			inputStream.close();
		}
	}

	@Override
	public void destroy() {
		logger.debug("destroying proxy servlet");

		alberoServletLocation = null;
	}

	/** The name of the init parameter that contains the location of the Albero servlet. */
	public final static String ALBERO_SERVLET_LOCATION = "albero_servlet.location";

	private final static long serialVersionUID = 20090825L;
}