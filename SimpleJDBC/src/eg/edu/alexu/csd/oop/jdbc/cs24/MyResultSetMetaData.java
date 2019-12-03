package eg.edu.alexu.csd.oop.jdbc.cs24;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MyResultSetMetaData implements ResultSetMetaData {
	private String tableName;
	private String [] columns;
	private Object[][] result;

	public MyResultSetMetaData(String tn,String[] col,Object[][]res){
		this.tableName=tn;
		this.columns=col;
		this.result=res;

	}

	public int getColumnCount() throws SQLException {
		if (columns!=null) return columns.length;
		else return 0;
	}
	
	public String getColumnLabel(int column) throws SQLException {
		if(this.columns!=null&&column>=this.columns.length&&column!=0){
			return columns[column-1];
		}
		else throw new SQLException();
	}


	public String getColumnName(int column) throws SQLException {
		if(this.columns!=null&&column>=this.columns.length&&column!=0){
			return columns[column-1];
		}
		else throw new SQLException();
	}
	
	public String getTableName(int column) throws SQLException {
       if(tableName!=null){return tableName;}
       else {throw new SQLException();}
	}
	
	public int getColumnType(int column) throws SQLException {
		if(this.result!=null&&column>=this.columns.length&&column!=0){
			if (int.class.isInstance(result[0][column-1])){return 4;}
			else if(String.class.isInstance(result[0][column-1])){return 12;}
		}
		throw new SQLException();
	}

//	================================ UNUSED METHODS ================================
	
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
