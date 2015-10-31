package org.idnsrules.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * The <code>JDBCConnectionUtil</code> class contains util methods that
 * facilitate connecting to DB using JDBC.
 *
 * @author VasanthaRaj
 * @author MurugesanRathinam
 * @version 1.0
 * @since 1.0
 */
public class JDBCConnectionUtil {

	/**
	 * 
	 */
	private static Logger logger = Logger.getLogger(JDBCConnectionUtil.class);

	/**
	 * 
	 */
	private static final Properties property = new Properties();

	/*
	 * static { logger.info("static above file load"); try {
	 * 
	 * 
	 * // property.(Thread.currentThread().getContextClassLoader()
	 * //.getResourceAsStream("class/student_jdbc.properties"));
	 * 
	 * InputStream jdbcResourceStream =
	 * JDBCConnectionUtil.class.getResourceAsStream("/student_jdbc.properties");
	 * property.load(jdbcResourceStream); logger.info("Property file founded ");
	 * 
	 * 
	 * } catch (IOException e) { logger.error(
	 * "Error occured while trying to load the connection properties", e); }
	 * 
	 * }
	 *//**
	 * The method returns a {@link Connection} JDBC Connection object
	 * 
	 * @return the {@link Connection} connection
	 * @author MurugesanRathinam
	 * @author VasanthaRaj
	 * @since 1.0
	 * @version 1.0
	 * @throws Exception
	 */

	public static Connection getConnection() throws IOException,
			ClassNotFoundException, SQLException {
		logger.info("this is {JDBC Connection Class} GetConnection() Method start");
		Connection connection = null;
		try {

			InputStream jdbcResourceStream = JDBCConnectionUtil.class
					.getResourceAsStream("/idns_jdbc.properties");
			property.load(jdbcResourceStream);
			logger.info("Property file founded ");

			// load the Driver Class
			Class.forName(property.getProperty("IDNS_JDBC_DRIVER_CLASS"));
			// create the connection now
			connection = DriverManager.getConnection(
					property.getProperty("IDNS_JDBC_URL"),
					property.getProperty("IDNS_JDBC_USERNAME"),
					property.getProperty("IDNS_JDBC_PASSWORD"));
			logger.info("DB Connection get connected");
		} catch (ClassNotFoundException | SQLException e) {
			logger.error(
					"Error occured while trying to load the connection properties",
					e);
			throw e;
		}
		return connection;
	}
}
