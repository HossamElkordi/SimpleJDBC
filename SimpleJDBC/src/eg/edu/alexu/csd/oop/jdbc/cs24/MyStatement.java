package eg.edu.alexu.csd.oop.jdbc.cs24;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.cs24.CommandChecker;

public class MyStatement implements Statement {
	
	private Connection connection;
	private String dirPath = "";
	private CommandChecker cm;
	private ArrayList<String> batch;
    private static Logger logger = Logger.getLogger(String.valueOf(MyStatement.class));
	
	public MyStatement(Connection connection, String path) {
		this.connection = connection;
		this.dirPath = path;
		cm = ((MyConnection)connection).cm;
		batch = new ArrayList<String>();
		WriteInLog();
	}
	
	public void addBatch(String sql) throws SQLException {
		if(connection != null && (sql.toLowerCase().contains("insert") || sql.toLowerCase().contains("delete") || sql.toLowerCase().contains("update"))) {
			batch.add(sql);
			logger.info("Batch added successfully !");
		}else {
		    logger.severe("Error adding a batch!");
			throw new SQLException();
		}
	}

	public void clearBatch() throws SQLException {
		if(connection != null) {
			batch.clear();
			logger.info("Batch cleared successfully !");
		}else {
            logger.severe("Error clearing the batch !");
			throw new SQLException();
		}
	}
	
	public int[] executeBatch() throws SQLException {
		if(connection != null) {
			int[] updatedRowsFromBatch = new int[this.batch.size()];
			for (int i = 0; i < updatedRowsFromBatch.length; i++) {
				cm.directCommand(this.batch.get(i));
				updatedRowsFromBatch[i] = cm.getUpdatedRows();
				logger.info("Batch executed successfully !");
			}
			return updatedRowsFromBatch;
		}else {
            logger.severe("Error executing the batch!");
			throw new SQLException();
		}
	}

	public Connection getConnection() throws SQLException {
        logger.info("Getting connection ...");
		if(this.connection != null) {
		    logger.info("Connection returned successfully !");
			return this.connection;
		}else {
		    logger.severe("Error returning the connection !");
			throw new SQLException();
		}
	}
	//TODO log the rest
	public boolean execute(String sql) throws SQLException {
		// need to check the timeout stuff which i don't understand
		String newSql = sql.toLowerCase();
		if((newSql.contains("create") || newSql.contains("drop")) && newSql.contains("database")) {
			String[] s = sql.split("[\\s]+");
			s[s.length - 1] = this.dirPath + System.getProperty("file.separator") + s[s.length - 1];
			newSql = "";
			for (int i = 0; i < s.length; i++) {
				newSql += s[i] + " ";
			}
			sql = newSql.substring(0, newSql.length() - 1);
		}
		this.cm.directCommand(sql);
		return (cm.getUpdatedRows() != -1) && (cm.getDataSet() != null);
	}
	
	public ResultSet executeQuery(String sql) throws SQLException {
		// need to check the timeout stuff which i don't understand
		if(connection != null) {
			cm.directCommand(sql);
			return new MyResultSet(cm.getDataSet(), cm.getColumnsNames(), this, nameGetterEngine(sql));
		}else {
			throw new SQLException();
		}
	}

	
	public int executeUpdate(String sql) throws SQLException {
		// need to check the timeout stuff which i don't understand
		if(connection != null) {
			cm.directCommand(sql);
			return cm.getUpdatedRows();
		}else {
			throw new SQLException();
		}
	}
	
	public int getQueryTimeout() throws SQLException {

		return 0;
	}

	
	public void setQueryTimeout(int seconds) throws SQLException {


	}

	
	public void close() throws SQLException {
	    logger.info("Closing the state");
		this.connection = null;
		batch.clear();
	}

	private void WriteInLog()
	{
		try
		{
			System.setProperty("java.util.logging.SimpleFormatter.format",
					"%1$tA %1$td %1$tB %1$tY %1$tH:%1$tM:%1$tS %tZ %4$s %2$s %5$s%6$s%n");
			FileHandler handler = new FileHandler("MyLog.log", true);
			logger.addHandler(handler);
			SimpleFormatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);
		}catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private String nameGetter(String in){
		Pattern p=Pattern.compile("[^\\s]*");
		ArrayList<String> a=new ArrayList<>();
		Matcher matcher=p.matcher(in);
		while (matcher.find()) {String j=matcher.group();
			if(!j.equals("")){
				a.add(j);
			}

		}
		if(a.size()!=2) return null;
		else return a.get(1);

	}
	private String nameGetterEngine(String input){
		int fromi,conditioni;
		String s = input.toLowerCase();
		fromi=s.indexOf("from");
		if(!s.contains("where")){conditioni=-1;}
		else{conditioni=s.indexOf("where");}
		String tablename;
		if(conditioni==-1){
			tablename=nameGetter(s.substring(fromi));
		}
		else{tablename=nameGetter(s.substring(fromi,conditioni));}
		if(tablename==null)return null;
		else{return tablename;}

	}
	
//	================================ UNUSED METHODS ================================

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int getMaxFieldSize() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void setMaxFieldSize(int max) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int getMaxRows() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void setMaxRows(int max) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void setEscapeProcessing(boolean enable) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void cancel() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void setCursorName(String name) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public ResultSet getResultSet() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int getUpdateCount() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public boolean getMoreResults() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void setFetchDirection(int direction) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int getFetchDirection() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void setFetchSize(int rows) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int getFetchSize() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int getResultSetConcurrency() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int getResultSetType() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public boolean getMoreResults(int current) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public ResultSet getGeneratedKeys() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int getResultSetHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public boolean isClosed() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void setPoolable(boolean poolable) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public boolean isPoolable() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void closeOnCompletion() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public boolean isCloseOnCompletion() throws SQLException {
		throw new UnsupportedOperationException();
	}

}
