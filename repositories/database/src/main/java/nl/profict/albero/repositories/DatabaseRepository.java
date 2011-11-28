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
package nl.profict.albero.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import nl.profict.albero.configuration.Configuration;
import nl.profict.albero.configuration.ConfigurationException;
import nl.profict.albero.utilities.Logger;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * A repository that reads trees from a database.
 * <p>
 * The following ways of configuring a data source are supported:
 * <ul>
 * <li>Through JNDI, by providing a configuration parameter named {@value #JNDI_NAME};
 * <li>Via a basic datasource, by providing four configuration parameters: the database driver ({@value #DRIVER}), URL
 *     ({@value #URL}), user ({@value #USER}), and password ({@value #PASSWORD}), all of which are required.
 * </ul>
 *
 */
public class DatabaseRepository implements Repository {
	private DataSource dataSource;

	private final Logger logger;

	/**
	 * Creates a database repository.
	 */
	public DatabaseRepository() {
		logger = Logger.get(getClass());
	}

	public void initialise(Configuration configuration, Map<String, String> parameters) throws ConfigurationException {
		logger.debug("initialising database repository");

		if (parameters.containsKey(JNDI_NAME)) {
			logger.debug("using JNDI data source");

			dataSource = createJndiDataSource(parameters);
		} else {
			logger.debug("using basic data source");

			dataSource = createBasicDataSource(parameters);
		}
	}

	private DataSource createJndiDataSource(Map<String, String> parameters) throws ConfigurationException {
		try {
			Context context  = (Context) new InitialContext().lookup("java:/comp/env");

			return (DataSource) context.lookup(parameters.get(JNDI_NAME));
		} catch (NamingException exception) {
			throw new ConfigurationException(exception, "can't obtain data source from JNDI");
		}
	}

	private DataSource createBasicDataSource(Map<String, String> parameters) throws ConfigurationException {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(parameters.get(DRIVER));
		dataSource.setUrl(parameters.get(URL));
		dataSource.setUsername(parameters.get(USER));
		dataSource.setPassword(parameters.get(PASSWORD));

		return dataSource;
	}

	public void destroy() throws ConfigurationException {
		dataSource = null;
	}

	public TreeInformation locate(String code) throws RepositoryException {
		Connection connection;

		try {
			connection = dataSource.getConnection();

			try {
				PreparedStatement statement =
					connection.prepareStatement("select tree, parser from trees where code = ?");

				try {
					statement.setString(1, code);

					ResultSet resultSet = statement.executeQuery();

					try {
						if (resultSet.next()) {
							return new TreeInformation(resultSet.getString("tree"), resultSet.getString("parser"));
						} else {
							throw new RepositoryException("can't find tree with code '", code, "'");
						}
					} finally {
						resultSet.close();
					}
				} finally {
					statement.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException exception) {
			throw new RepositoryException(exception, "can't use database while locating tree with code '", code, "'");
		}
	}

	@Override
	public String toString() {
		return String.format("database repository (data source: %s)", dataSource);
	}

	/** The JNDI name of the data source. */
	public final static String JNDI_NAME = "albero.repositories.database.jndi_name";

	/** The name of the configuration parameter that contains the driver class to use. */
	public final static String DRIVER = "albero.repositories.database.driver";

	/** The name of the configuration parameter that contains the database URL. */
	public final static String URL = "albero.repositories.database.url";

	/** The name of the configuration parameter that contains the user to connect with. */
	public final static String USER = "albero.repositories.database.user";

	/** The name of the configuration parameter that contains the password to connect with. */
	public final static String PASSWORD = "albero.repositories.database.password";
}
