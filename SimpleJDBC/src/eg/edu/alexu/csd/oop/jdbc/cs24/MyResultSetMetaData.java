package eg.edu.alexu.csd.oop.jdbc.cs24;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MyResultSetMetaData implements ResultSetMetaData {


	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public int getColumnCount() throws SQLException {

		return 0;
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


	public String getColumnLabel(int column) throws SQLException {

		return null;
	}


	public String getColumnName(int column) throws SQLException {

		return null;
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


	public String getTableName(int column) throws SQLException {

		return null;
	}


	public String getCatalogName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public int getColumnType(int column) throws SQLException {

		return 0;
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
