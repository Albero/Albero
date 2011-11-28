package nl.profict.albero.repositories;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import nl.profict.albero.configuration.Configuration;
import nl.profict.albero.configuration.ConfigurationException;
import nl.profict.albero.utilities.Logger;

/**
 * A repository that reads trees from the class path. All resources are expected to reside in the {@code trees}
 * directory. The parser that's being used is obtained by looking at the resource's extension.
 *
 * @author levi_h
 */
public class ClassPathRepository implements Repository {
	private Map<String, TreeInformation> trees;

	private final Logger logger;

	/**
	 * Creates a class path repository.
	 */
	public ClassPathRepository() {
		logger = Logger.get(getClass());
	}

	public void initialise(Configuration configuration, Map<String, String> parameters) {
		try {
			findTrees();
		} catch (IOException exception) {
			throw new ConfigurationException(exception, "can't find trees");
		}
	}

	private void findTrees() throws IOException {
		trees = new HashMap<String, TreeInformation>();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		for (URL url: Collections.list(classLoader.getResources("trees"))) {
			String path = URLDecoder.decode(url.getPath(), "utf-8");
			boolean jar = url.getProtocol().equals("jar");

			if (jar) {
				path = path.substring(0, path.indexOf('!'));
			}

			if (path.startsWith("file:")) {
				path = path.substring(5);
			}

			File file = new File(path);

			if (jar) {
				findTreesInJar(file);
			} else {
				findTreesInDirectory(file);
			}
		}
	}

	private void findTreesInJar(File jarFile) throws IOException {
		JarInputStream in = new JarInputStream(new FileInputStream(jarFile));

		try {
			JarEntry entry;

			while ((entry = in.getNextJarEntry()) != null) {
				String entryName = entry.getName();

				if (!entry.isDirectory() && entryName.startsWith("trees/")) {
					String name = entryName.substring(6);

					if (!name.contains("/")) {
						addTree(name, in);
					}
				}
			}
		} finally {
			in.close();
		}
	}

	private void findTreesInDirectory(File directory) throws IOException {
		File[] files = directory.listFiles();

		if (files != null) {
			for (File file: files) {
				if (file.isFile()) {
					addTree(file.getName(), new FileInputStream(file));
				}
			}
		}
	}

	private void addTree(String name, InputStream in) throws IOException {
		int separatorLocation = name.lastIndexOf('.');

		if (separatorLocation == -1) {
			logger.warn("skipping '", name, "'");
		} else {
			String code = name.substring(0, separatorLocation);
			String parser = name.substring(separatorLocation + 1);

			logger.debug("adding tree, code: '", code, "', parser: '", parser, "'");

			byte[] buffer = new byte[4096];
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			int bytesRead;

			while ((bytesRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, bytesRead);
			}

			trees.put(code, new TreeInformation(out.toString(), parser));
		}
	}

	public void destroy() {
		trees = null;
	}

	public TreeInformation locate(String code) throws RepositoryException {
		if (!trees.containsKey(code)) {
			throw new RepositoryException("can't locate tree with code '", code, "'");
		}

		return trees.get(code);
	}

	@Override
	public String toString() {
		return String.format("class path repository (trees: %s)", trees);
	}
}