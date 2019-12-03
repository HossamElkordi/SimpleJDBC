package eg.edu.alexu.csd.oop.jdbc.cs24;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyResultSetMetaData implements ResultSetMetaData {
	private String tableName;
	private String [] columns;
	private Object[][] result;
	private static Logger logger = Logger.getLogger(String.valueOf(MyResultSetMetaData.class));

	public MyResultSetMetaData(String tn,String[] col,Object[][]res){
		this.tableName=tn;
		this.columns=col;
		this.result=res;
		WriteInLog();
	}

	public int getColumnCount() throws SQLException {
		if (columns!=null) {logger.info("Getting numbers of column");return columns.length;}
		logger.severe("Couldn't get number of columns");
		throw new SQLException();
	}
	
	public String getColumnLabel(int column) throws SQLException {
		if(this.columns!=null&&column>=this.columns.length&&column!=0){
			logger.info("Getting column label");
			return columns[column-1];
		}
		logger.severe("Couldn't get column label");
		throw new SQLException();
	}


	public String getColumnName(int column) throws SQLException {
		if(this.columns!=null&&column>=this.columns.length&&column!=0){
			logger.info("Getting column name");
			return columns[column-1];
		}
		logger.severe("Couldn't get column name");
		throw new SQLException();
	}
	
	public String getTableName(int column) throws SQLException {
       if(tableName!=null){logger.info("Getting table name");return tableName;}
       else {logger.severe("Couldn't get table name");throw new SQLException();}
	}
	
	public int getColumnType(int column) throws SQLException {
		if(this.result!=null&&column>=this.columns.length&&column!=0){
			logger.info("Getting column type");
			if (int.class.isInstance(result[0][column-1])){return 4;}
			else if(String.class.isInstance(result[0][column-1])){return 12;}
		}
		logger.severe("Couldn't get column type");
		throw new SQLException();
	}
    private static void WriteInLog()
    {
        try
        {
            System.setProperty("java.util.logging.SimpleFormatter.format",
                    "%1$tA %1$td %1$tB %1$tY %1$tH:%1$tM:%1$tS.%1$tL %tZ %4$s %2$s %5$s%6$s%n");
            FileHandler handler = new FileHandler("Logs"+System.getProperty("file.separator")+((int)(Math.random() * 100000))+"_MyLog.log", true);
            logger.addHandler(handler);
            SimpleFormatter formatter = new SimpleFormatter();
            handler.setFormatter(formatter);
        }catch (IOException e)
        {
            new File("Logs").mkdir();
            WriteInLog();
            e.printStackTrace();
        }
    }

//	================================ UNUSED METHODS ================================//
	
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public boolean isAutoIncrement(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public boolean isCaseSensitive(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public boolean isSearchable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public boolean isCurrency(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public int isNullable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public boolean isSigned(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public int getColumnDisplaySize(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public String getSchemaName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public int getPrecision(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public int getScale(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public String getCatalogName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public String getColumnTypeName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public boolean isReadOnly(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public boolean isWritable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public boolean isDefinitelyWritable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public String getColumnClassName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

}
