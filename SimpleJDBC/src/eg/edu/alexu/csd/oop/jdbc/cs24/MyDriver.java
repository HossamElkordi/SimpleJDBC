package eg.edu.alexu.csd.oop.jdbc.cs24;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyDriver implements Driver {

    private static Logger logger = Logger.getLogger(String.valueOf(MyDriver.class));

    public MyDriver()
	{
		WriteInLog();
	}

	public Connection connect(String url, Properties info) throws SQLException {
		if(acceptsURL(url)) {
		    logger.info("Connection established successfully !");
		    String[] pathArr = info.get("path").toString().replace('\\', ' ').split(" ");
			return new MyConnection(pathArr[pathArr.length-2] + System.getProperty("file.separator") + pathArr[pathArr.length-1]);
		}
        logger.severe("Error establishing connection !");
		return null;
	}

	public boolean acceptsURL(String url) throws SQLException {
		boolean returning = true;
		String[] urlParts = url.split(":");
		
		if(!urlParts[0].equalsIgnoreCase("jdbc")){
            logger.severe("Error with the entered URL "+url);
			returning = false;
		}
		if(!urlParts[1].equalsIgnoreCase("xmldb")){
            logger.severe("Error with the entered URL "+url);
			returning = false;
		}
		if(!urlParts[2].equalsIgnoreCase("//localhost")){
            logger.severe("Error with the entered URL "+url);
			returning = false;
		}
		logger.info("URL returned successfully !");
		return returning;
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        logger.info("Getting property !");
	    DriverPropertyInfo[] dpi = new DriverPropertyInfo[info.size()];
		int i = 0;
		Set<?> set = info.entrySet();
		Iterator<?> iter = set.iterator();
		while(iter.hasNext()) {
			dpi[i++] = (DriverPropertyInfo)iter.next();
		}
		logger.info("Property returned successfully !");
		return dpi;
	}

	private static void WriteInLog()
	{
		try
		{
			System.setProperty("java.util.logging.SimpleFormatter.format",
					"%1$tA %1$td %1$tB %1$tY %1$tH:%1$tM:%1$tS.%1$tL %tZ %4$s %2$s %5$s%6$s%n");
			FileHandler handler = new FileHandler("Logs"+System.getProperty("file.separator")+"MyLog.log", true);
			logger.addHandler(handler);
			SimpleFormatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);
		}catch (IOException e)
		{
			new File("Logs").mkdirs();
			WriteInLog();
		}
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
