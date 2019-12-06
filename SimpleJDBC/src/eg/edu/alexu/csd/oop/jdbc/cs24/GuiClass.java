package eg.edu.alexu.csd.oop.jdbc.cs24;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import eg.edu.alexu.csd.oop.db.cs24.CommandChecker;
import javax.swing.JCheckBox;

public class GuiClass {

	private ResultSet resultSet;
	private ResultSetMetaData resultMetaData;
	private Driver driver;
	private Connection connection;
	private Statement statement;

	private JFrame frame;
	private JTable table;
	private JTextArea commandArea;
	private DefaultTableModel model;
	private JLabel updaredRowslbl;

	private CommandChecker comCheck;

	private String document = "";
	private JButton btnExecuteBatch;
	private JButton btnClearBatch;
	private JTextField timeoutField;
	private JTextField resultRows;
	private JTextField ColumnControlInput;
	private JTextField metaInputTxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			GuiClass window = new GuiClass();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Create the application.
	 */
	public GuiClass() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Java Database Connectivity");
		frame.setBounds(100, 100, 632, 730);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.setBounds(260, 11, 346, 399);
		frame.getContentPane().add(tableScrollPane);

		model = new DefaultTableModel();
		setTableModel();
		table = new JTable();
		table.setModel(model);
		tableScrollPane.setViewportView(table);

		setCommand();
		setupdaredRowslbl();
		setTimeoutStuff();
		setResultSetStuff();
		setColumnControl();
		setMetaDataStuff();
		setDriverConnectionStatementStuff();
	}

	private void setTableModel() {
		int count = model.getRowCount();
		for (int i = 0; i < count; i++) {
			model.removeRow(0);
		}
		if (comCheck != null) {
			String[] colName = comCheck.getColumnsNames();
			if(colName != null) {
				model.setColumnIdentifiers(colName);
			}else {
				model.setColumnIdentifiers(new String[0]);
			}
			Object[][] data = comCheck.getDataSet();
			if (data != null) {
				for (int i = 0; i < data.length; i++) {
					model.addRow(data[i]);
				}
			}
		}

	}

	private void setCommand() {
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 151, 237, 157);
		frame.getContentPane().add(scrollPane);
		
		JCheckBox chckbxDropIfExists = new JCheckBox("Drop if exists");
		chckbxDropIfExists.setSelected(true);
		chckbxDropIfExists.setBounds(10, 121, 97, 23);
		frame.getContentPane().add(chckbxDropIfExists);
		
		commandArea = new JTextArea();
		scrollPane.setViewportView(commandArea);
		commandArea.setTabSize(5);
		commandArea.setLineWrap(true);
		commandArea.setFont(new Font("Courier New", Font.PLAIN, 13));
		commandArea.setWrapStyleWord(true);
		commandArea.setEditable(false);

		JButton btnExecute = new JButton("Execute");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(statement != null) {
						String sql = getLastCommand().toLowerCase();
						comCheck.setDrop(chckbxDropIfExists.isSelected());
						if(sql.contains("select")) {
							resultSet = statement.executeQuery(sql);
							resultMetaData = resultSet.getMetaData();
						}else if(sql.contains("insert") || sql.contains("delete") || sql.contains("update")){
							statement.executeUpdate(sql);
						}else {
							statement.execute(sql);
						}
						updaredRowslbl.setText("Number of updated rows: " + comCheck.getUpdatedRows());
						setTableModel();
					}else {
						JOptionPane.showMessageDialog(null, "No statement created!");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnExecute.setBounds(10, 319, 104, 23);
		frame.getContentPane().add(btnExecute);

		JButton btnAddToBatch = new JButton("Add to Batch");
		btnAddToBatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(statement != null) {
					try {
						statement.addBatch(getLastCommand());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "No statement created!");
				}
			}
		});
		btnAddToBatch.setBounds(146, 319, 104, 23);
		frame.getContentPane().add(btnAddToBatch);

		btnExecuteBatch = new JButton("Execute Batch");
		btnExecuteBatch.setBounds(10, 353, 104, 23);
		frame.getContentPane().add(btnExecuteBatch);
		btnExecuteBatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(statement != null) {
					try {
						comCheck.setDrop(chckbxDropIfExists.isSelected());
						int[] arr = statement.executeBatch();
						updaredRowslbl.setText("Number of updated rows: " + arr.toString());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "No statement created!");
				}
			}
		});

		btnClearBatch = new JButton("Clear Batch");
		btnClearBatch.setBounds(146, 353, 104, 23);
		frame.getContentPane().add(btnClearBatch);
		btnClearBatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(statement != null) {
					try {
						statement.clearBatch();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "No statement created!");
				}
			}
		});
	}

	private void setResultSetStuff() {
		JLabel lblResultsetRows = new JLabel("ResultSet Rows");
		lblResultsetRows.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultsetRows.setBounds(62, 492, 118, 14);
		frame.getContentPane().add(lblResultsetRows);

		resultRows = new JTextField();
		resultRows.setBounds(10, 517, 215, 20);
		frame.getContentPane().add(resultRows);
		resultRows.setColumns(10);

		JButton btnAbsolute = new JButton("Set Absolute");
		btnAbsolute.setBounds(10, 548, 104, 23);
		frame.getContentPane().add(btnAbsolute);
		btnAbsolute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(resultSet != null) {
					try {
						String s = resultRows.getText();
						if(s.matches("[0-9]+")) {
							resultSet.absolute(Integer.parseInt(s));
						}else {
							JOptionPane.showMessageDialog(null, "Type mismatch!");
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		JButton btnNext = new JButton("Next");
		btnNext.setBounds(136, 548, 89, 23);
		frame.getContentPane().add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resultSet != null) {
					try {
						resultSet.next();
					} catch (SQLException ex) {
						ex.printStackTrace();
					} 
				}
			}
		});

		JButton btnPrevious = new JButton("Previous");
		btnPrevious.setBounds(136, 582, 89, 23);
		frame.getContentPane().add(btnPrevious);
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resultSet != null) {
					try {
						resultSet.previous();
					} catch (SQLException ex) {
						ex.printStackTrace();
					} 
				}
			}
		});

		JButton btnBeforePrevious = new JButton("BeforeFr");
		btnBeforePrevious.setBounds(10, 582, 104, 23);
		frame.getContentPane().add(btnBeforePrevious);
		btnBeforePrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resultSet != null) {
					try {
						resultSet.beforeFirst();
					} catch (SQLException ex) {
						ex.printStackTrace();
					} 
				}
			}
		});

		JButton btnNewButton = new JButton("AfterLast");
		btnNewButton.setBounds(10, 616, 104, 23);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resultSet != null) {
					try {
						resultSet.afterLast();
					} catch (SQLException ex) {
						ex.printStackTrace();
					} 
				}
			}
		});
	}

	private void setColumnControl() {
		JLabel lblColumnsControl = new JLabel("Columns Control");
		lblColumnsControl.setHorizontalAlignment(SwingConstants.CENTER);
		lblColumnsControl.setBounds(357, 421, 110, 14);
		frame.getContentPane().add(lblColumnsControl);

		ColumnControlInput = new JTextField();
		ColumnControlInput.setBounds(312, 448, 125, 20);
		frame.getContentPane().add(ColumnControlInput);
		ColumnControlInput.setColumns(10);
		
		JLabel lblResult = new JLabel("Result:  ");
		lblResult.setBounds(312, 483, 294, 14);
		frame.getContentPane().add(lblResult);

		JButton getRsultBtn = new JButton("Get Result");
		getRsultBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resultSet != null) {
					try {
						String s = ColumnControlInput.getText();
						if(s.matches("[0-9]+")) {
							lblResult.setText("Result:  " + resultSet.getString(Integer.parseInt(s)));
						}else {
							lblResult.setText("Result:  " + resultSet.getString(s));
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		getRsultBtn.setBounds(447, 447, 89, 23);
		frame.getContentPane().add(getRsultBtn);
	}

	private void setMetaDataStuff() {
		JLabel lblResultsetMetadata = new JLabel("ResultSet MetaData");
		lblResultsetMetadata.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultsetMetadata.setBounds(343, 516, 141, 14);
		frame.getContentPane().add(lblResultsetMetadata);

		metaInputTxt = new JTextField();
		metaInputTxt.setBounds(312, 549, 125, 20);
		frame.getContentPane().add(metaInputTxt);
		metaInputTxt.setColumns(10);

		JLabel lblResult_1 = new JLabel("Result: ");
		lblResult_1.setBounds(312, 657, 253, 14);
		frame.getContentPane().add(lblResult_1);

		JButton btnGetColName = new JButton("Get ColName");
		btnGetColName.setBounds(447, 548, 118, 23);
		frame.getContentPane().add(btnGetColName);
		btnGetColName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resultMetaData != null) {
					try {
						String s = metaInputTxt.getText();
						if (s.matches("[0-9]+")) {
							lblResult_1.setText("Result: " + resultMetaData.getColumnName(Integer.parseInt(s)));
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					} 
				}
			}
		});

		JButton GetTableName = new JButton("Get TableName");
		GetTableName.setBounds(447, 582, 118, 23);
		frame.getContentPane().add(GetTableName);
		GetTableName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resultMetaData != null) {
					try {
						String s = metaInputTxt.getText();
						if (s.matches("[0-9]+")) {
							lblResult_1.setText("Result: " + resultMetaData.getTableName(Integer.parseInt(s)));
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					} 
				}
			}
		});

		JButton btnGetColtype = new JButton("Get colType");
		btnGetColtype.setBounds(447, 616, 118, 23);
		frame.getContentPane().add(btnGetColtype);
		btnGetColtype.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resultMetaData != null) {
					try {
						String s = metaInputTxt.getText();
						if (s.matches("[0-9]+")) {
							lblResult_1.setText("Result: " + resultMetaData.getColumnType(Integer.parseInt(s)));
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					} 
				}
			}
		});

		JButton getColCount = new JButton("Get ColCount");
		getColCount.setBounds(312, 580, 125, 23);
		frame.getContentPane().add(getColCount);
		getColCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resultMetaData != null) {
					try {
						lblResult_1.setText("Result: " + resultMetaData.getColumnCount());
					} catch (SQLException ex) {
						ex.printStackTrace();
					} 
				}
			}
		});
	}

	private void setDriverConnectionStatementStuff() {
		JButton btnCreateDriver = new JButton("Create Driver");
		btnCreateDriver.setBounds(62, 25, 118, 23);
		frame.getContentPane().add(btnCreateDriver);
		btnCreateDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(driver != null) {
						if(connection != null) {
							connection.close();
							connection = null;
							comCheck = null;
						}
						if(statement != null) {
							statement.close();
							statement = null;
							commandArea.setEditable(false);
						}
						if(resultSet != null) {
							resultSet.close();
							resultSet = null;
							resultMetaData = null;
						}
					}
					driver = new MyDriver();
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Can't create a driver!");
				}
			}
		});

		JLabel lblConnection = new JLabel("Connection:");
		lblConnection.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnection.setBounds(10, 59, 65, 23);
		frame.getContentPane().add(lblConnection);

		JButton createConBtn = new JButton("Create");
		createConBtn.setBounds(90, 59, 72, 23);
		frame.getContentPane().add(createConBtn);
		createConBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(driver == null) {
						JOptionPane.showMessageDialog(null, "Create a driver first!");
						return;
					}
					if(connection != null) {
						JOptionPane.showMessageDialog(null, "Close the current connection first!");
						return;
					}
					Properties info = new Properties();
//					File dbDir = new File("sample" + System.getProperty("file.separator") + ((int)(Math.random() * 100000)));
					info.put("path", "");
					connection = driver.connect("jdbc:xmldb://localhost", info);
					comCheck = ((MyConnection)(connection)).cm;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

		JButton closeConBtn = new JButton("Close");
		closeConBtn.setBounds(169, 59, 72, 23);
		frame.getContentPane().add(closeConBtn);
		closeConBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if((connection == null) || (driver == null)) {
						JOptionPane.showMessageDialog(null, "No connection to close!");
					}else {
						connection.close();
						connection = null;
						if(statement != null) {
							statement.close();
							statement = null;
							comCheck = null;
							commandArea.setEditable(false);
						}
						if(resultSet != null) {
							resultSet.close();
							resultSet = null;
							resultMetaData = null;
						}
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

		JLabel lblStatement = new JLabel("Statement:");
		lblStatement.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatement.setBounds(10, 93, 65, 23);
		frame.getContentPane().add(lblStatement);

		JButton createStmBtn = new JButton("Create");
		createStmBtn.setBounds(90, 93, 72, 23);
		frame.getContentPane().add(createStmBtn);
		createStmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if((connection == null) || (driver == null)) {
						JOptionPane.showMessageDialog(null, "No connection to close!");
					}
					if(statement != null) {
						JOptionPane.showMessageDialog(null, "Close the current statement first!");
						return;
					}
					statement = connection.createStatement();
					commandArea.setEditable(true);
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

		JButton closeStmBtn = new JButton("Close");
		closeStmBtn.setBounds(169, 93, 72, 23);
		frame.getContentPane().add(closeStmBtn);
		closeStmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if((connection == null) || (driver == null) || (statement == null)) {
						JOptionPane.showMessageDialog(null, "No statement to close!");
					}else {
						statement.close();
						statement = null;
						commandArea.setEditable(false);
						if(resultSet != null) {
							resultSet.close();
							resultSet = null;
							resultMetaData = null;
						}
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	private void setTimeoutStuff() {
		timeoutField = new JTextField();
		timeoutField.setBounds(10, 448, 86, 20);
		frame.getContentPane().add(timeoutField);
		timeoutField.setColumns(10);

		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(statement != null) {
					try {
						int time = (int) Double.parseDouble(timeoutField.getText());
						if(time < 0) {
							JOptionPane.showMessageDialog(null, "No negative allowed!");
							return;
						}else {
							statement.setQueryTimeout(time);
						}
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Type mismatch!");
					}
				}
			}
		});
		btnSet.setBounds(106, 447, 56, 23);
		frame.getContentPane().add(btnSet);

		JButton btnGet = new JButton("Get");
		btnGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(statement != null) {
					try {
						timeoutField.setText("" + statement.getQueryTimeout());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "No statement created!");
				}
			}
		});
		btnGet.setBounds(169, 447, 56, 23);
		frame.getContentPane().add(btnGet);

		JLabel lblTimeoutzeroFor = new JLabel("Timeout (zero for default)");
		lblTimeoutzeroFor.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeoutzeroFor.setBounds(62, 423, 132, 14);
		frame.getContentPane().add(lblTimeoutzeroFor);
	}

	private void setupdaredRowslbl() {
		updaredRowslbl = new JLabel("Number of updated rows: 0");
		updaredRowslbl.setHorizontalAlignment(SwingConstants.CENTER);
		updaredRowslbl.setBounds(0, 387, 240, 23);
		frame.getContentPane().add(updaredRowslbl);
	}
	
	private String getLastCommand() {
		int count = document.length();
		String oldDoc = document;
		document = commandArea.getText().replaceAll("\n", "");
		if(document.lastIndexOf(oldDoc, 0) != -1) {
			return document.substring(count);
		}else {
			return document.substring(getCommandStartIndex(oldDoc));
		}
	}

	private int getCommandStartIndex(String oldDoc) {
		int start = 0;
		for (int i = 0; i < document.length(); i++) {
			if(oldDoc.charAt(i) != document.charAt(i)) {
				start = i;
				break;
			}
		}
		return start;
	}
}