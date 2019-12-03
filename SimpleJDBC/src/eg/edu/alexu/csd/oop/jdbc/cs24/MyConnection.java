package eg.edu.alexu.csd.oop.jdbc.cs24;

import java.io.IOException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import eg.edu.alexu.csd.oop.db.cs24.CommandChecker;

public class MyConnection implements Connection {
	
	private String path = "";
	public CommandChecker cm;
	Statement statement;
    private static Logger logger = Logger.getLogger(String.valueOf(MyConnection.class));

    public MyConnection(String path) {
		this.path = path;
		cm = instaceOfCommandChecker();
		WriteInLog();
	}

	public Statement createStatement() throws SQLException {
        logger.info("Statement created successfully!");
		return new MyStatement(this, path);
	}
	
	public void close() throws SQLException {
		if(statement != null) {
			statement.close();
            logger.info("Connection has been closed !");
            return;
		}
        logger.severe("No connection found to be closed !");
		throw new SQLException("No statement found");
	}
	
	private CommandChecker instaceOfCommandChecker() {
		if(cm == null) {
			cm = new CommandChecker();
		}
		return cm;
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

//	================================ UNUSED METHODS ================================
	
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public String nativeSQL(String sql) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean getAutoCommit() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void commit() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void rollback() throws SQLException {
		throw new UnsupportedOperationException();
	}


	public boolean isClosed() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public boolean isReadOnly() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void setCatalog(String catalog) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public String getCatalog() throws SQLException {
throw new UnsupportedOperationException();

	}

	
	public void setTransactionIsolation(int level) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int getTransactionIsolation() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void setHoldability(int holdability) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int getHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public Savepoint setSavepoint() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public Savepoint setSavepoint(String name) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void rollback(Savepoint savepoint) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public Clob createClob() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public Blob createBlob() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public NClob createNClob() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public SQLXML createSQLXML() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public boolean isValid(int timeout) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		throw new UnsupportedOperationException();
	}

	
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
       throw new UnsupportedOperationException();

	}

	
	public String getClientInfo(String name) throws SQLException {
		throw new UnsupportedOperationException();

	}

	
	public Properties getClientInfo() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void setSchema(String schema) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public String getSchema() throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void abort(Executor executor) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
	public int getNetworkTimeout() throws SQLException {
		throw new UnsupportedOperationException();
	}

}
