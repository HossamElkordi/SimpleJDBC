package eg.edu.alexu.csd.oop.jdbc.cs24;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class MyDriver implements Driver {

	public Connection connect(String url, Properties info) throws SQLException {
		return null;
	}

	public boolean acceptsURL(String url) throws SQLException {
		return false;
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return null;
	}

	public int getMajorVersion() {
		throw new UnsupportedOperationException();
	}

	public int getMinorVersion() {
		throw new UnsupportedOperationException();
	}

	public boolean jdbcCompliant() {
		throw new UnsupportedOperationException();
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new UnsupportedOperationException();
	}

}
