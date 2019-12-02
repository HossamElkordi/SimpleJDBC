package eg.edu.alexu.csd.oop.jdbc.cs24;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public class MyDriver implements Driver {

	public Connection connect(String url, Properties info) throws SQLException {
		if(acceptsURL(url)) {
			return new MyConnection(((File)info.get("path")).getPath());
		}
		return null;
	}

	public boolean acceptsURL(String url) throws SQLException {
		boolean returning = true;
		String[] urlParts = url.split(":");
		
		if(!urlParts[0].equalsIgnoreCase("jdbc")){
			returning = false;
		}
		if(!urlParts[1].equalsIgnoreCase("xmldb")){
			returning = false;
		}
		if(!urlParts[2].equalsIgnoreCase("//localhost")){
			returning = false;
		}
		
		return returning;
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		DriverPropertyInfo[] dpi = new DriverPropertyInfo[info.size()];
		int i = 0;
		Set<?> set = info.entrySet();
		Iterator<?> iter = set.iterator();
		while(iter.hasNext()) {
			dpi[i++] = (DriverPropertyInfo)iter.next();
		}
		return dpi;
	}
	
//	================================ UNUSED METHODS ================================

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
