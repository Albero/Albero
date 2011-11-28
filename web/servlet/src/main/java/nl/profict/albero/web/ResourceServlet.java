/* Copyright 2011-2012 Profict Holding 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.profict.albero.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.profict.albero.utilities.Logger;

/**
 * Serves resources that Albero clients can use.
 */
public class ResourceServlet extends HttpServlet {
	private Map<String, Resource> resources;

	private final Logger logger;

	/**
	 * Creates a resource servlet.
	 */
	public ResourceServlet() {
		resources = new HashMap<String, Resource>();
		resources.put("/albero", new Resource("albero.js", "application/javascript"));
		resources.put("/albero-elements", new Resource("albero-elements.js", "application/javascript"));

		logger = Logger.get(getClass());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String resourceName = request.getPathInfo();

		if (resourceName == null) {
			resourceName = "";
		}

		logger.debug("resource '", resourceName, "' requested");

		if (resources.containsKey(resourceName)) {
			resources.get(resourceName).sendTo(response);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
				String.format("'%s' is not a valid resource; please use one of %s.", resourceName, resources.keySet()));
		}
	}

	private static class Resource {
		private String path;
		private String contentType;

		public Resource(String path, String contentType) {
			this.path = path;
			this.contentType = contentType;
		}

		public void sendTo(HttpServletResponse response) throws IOException {
			response.setContentType(contentType);

			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			OutputStream out = response.getOutputStream();

			try {
				byte[] buffer = new byte[4096];

				int bytesRead;

				while ((bytesRead = in.read(buffer)) > 0) {
					out.write(buffer, 0, bytesRead);
				}
			} finally {
				in.close();
			}
		}
	}

	private static final long serialVersionUID = 20111125L;
}