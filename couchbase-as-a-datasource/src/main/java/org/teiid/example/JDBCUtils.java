/*
 * Copyright Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags and
 * the COPYRIGHT.txt file distributed with this work.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.teiid.example;

import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class JDBCUtils {
	
	public static Connection getDriverConnection(String driver, String url, String user, String pass) throws Exception {
		Class.forName(driver);
		return DriverManager.getConnection(url, user, pass); 
	}
	
	public static Connection getDriverConnection(String driver, String url, Properties connectionProps) throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, connectionProps); 
    }

	public static void close(Connection conn) throws SQLException {
		close(null, null, conn);
	}
	
	public static void close(Statement stmt) throws SQLException {
		close(null, stmt, null);
	}
	
	public static void close(ResultSet rs, Statement stmt) throws SQLException {
		close(rs, stmt, null);
	}
	
	public static void close(Statement stmt, Connection conn) throws SQLException {
		close(null, stmt, conn);
	}
	
	public static void close(ResultSet rs, Statement stmt, Connection conn) throws SQLException {

		if (null != rs) {
			rs.close();
			rs = null;
		}
		
		if(null != stmt) {
			stmt.close();
			stmt = null;
		}
		
		if(null != conn) {
			conn.close();
			conn = null;
		}
	}
	
	public static void execute(Connection connection, String sql) throws Exception {
	    execute(connection, sql, false);
	}
	
	public static void execute(Connection connection, String sql, boolean closeConn) throws Exception {
	    
	    System.out.println("SQL: " + sql); //$NON-NLS-1$ 
        
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = connection.createStatement();
            boolean hasResults = stmt.execute(sql);
            if (hasResults) {
                rs = stmt.getResultSet();
                new ResultSetRenderer(rs).renderer();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs, stmt);
            if(closeConn)
                close(connection);
        }   
        System.out.println();
    }
	

	public static void executeQuery(Connection conn, String sql) throws SQLException {
		
		System.out.println("Query SQL: " + sql);
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			new ResultSetRenderer(rs).renderer();
		} finally {
			close(rs, stmt);
		}
		
		System.out.println();
		
	}
	
	public static Set<String> query(Connection conn, String sql) throws Exception {
	    
	    Set<String> list = new HashSet<String>();
	    
	    Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                list.add(rs.getString(1));
            }
        } finally {
            close(rs, stmt);
        }
	    
	    return list;
	    
	}
	
	public static long executeQueryCount(Connection conn, String sql) throws SQLException {
		
		System.out.println("Query SQL: " + sql);
		
		long start = System.currentTimeMillis();
		long end;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			end = System.currentTimeMillis();
			int columns = rs.getMetaData().getColumnCount();
			while(rs.next()) {
				for (int i = 0 ; i < columns ; ++i) {
					rs.getObject(i+1);
				}
			}
		} finally {
			close(rs, stmt);
		}
		
		return end - start;
	}

	public static boolean executeUpdate(Connection conn, String sql) throws SQLException {
			
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} finally {
			close(stmt);
		}
		return true ;
	}
	
	private static class ResultSetRenderer extends TableRenderer implements AutoCloseable{
		
		private final ResultSet rs;
		private final ResultSetMetaData meta;
		private final int columns;
		
		private final long clobLimit = 8192;
		
		public ResultSetRenderer(ResultSet rs) throws SQLException {
			super(new PrintStreamOutputDevice(System.out));
			this.rs = rs;
			this.meta = rs.getMetaData();
			this.columns = meta.getColumnCount();
			setMeta(getDisplayMeta(meta));
		}
		
		@Override
		public void renderer() {
			
			try {
				while(rs.next()) {
					Column[] currentRow = new Column[ columns ];
					for (int i = 0 ; i < columns ; ++i) {
					    int col = i+1;
			                    String colString;
			                    if (meta.getColumnType( col ) == Types.CLOB) {
			                        colString = readClob(rs.getClob( col ));
			                    }
			                    else {
			                        colString = rs.getString( col );
			                    }
					    Column thisCol = new Column(colString);
					    currentRow[i] = thisCol;
					}
					addRow(currentRow);
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
				
			super.renderer();
		}

		private ColumnMetaData[] getDisplayMeta(ResultSetMetaData m) throws SQLException {
	    	
			ColumnMetaData result[] = new ColumnMetaData [ columns ];
		
			for (int i = 0; i < result.length; ++i) {
			    int col = i+1;
			    int alignment  = ColumnMetaData.ALIGN_LEFT;
			    String columnLabel = m.getColumnLabel( col );
			    /*
			    int width = Math.max(m.getColumnDisplaySize(i),
						 columnLabel.length());
			    */
			    switch (m.getColumnType( col )) {
			    case Types.NUMERIC:  
			    case Types.INTEGER: 
			    case Types.REAL:
			    case Types.SMALLINT:
			    case Types.TINYINT:
					alignment = ColumnMetaData.ALIGN_RIGHT;
					break;
			    }
			    result[i] = new ColumnMetaData(columnLabel,alignment);
			}
			return result;
	    }
		
		private String readClob(Clob c) throws SQLException {
	        if (c == null) return null;
	        StringBuffer result = new StringBuffer();
	        long restLimit = clobLimit;
	        try {
	            Reader in = c.getCharacterStream();
	            char buf[] = new char [ 4096 ];
	            int r;

	            while (restLimit > 0 
	                   && (r = in.read(buf, 0, (int)Math.min(buf.length,restLimit))) > 0) 
	                {
	                    result.append(buf, 0, r);
	                    restLimit -= r;
	                }
	        }
	        catch (Exception e) {
	           out.println(e.toString());
	        }
	        if (restLimit == 0) {
	            result.append("...");
	        }
	        return result.toString();
	    }

		@Override
		public void close() throws Exception {
			out.close();
		}
		
	}

}
