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

import static org.teiid.example.util.JDBCUtils.*;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.h2.tools.RunScript;

/**
 * A Util class to insert data to H2 database table 'PERFTEST'
 * 
 *      CREATE TABLE PERFTEST(
 *          id CHAR(4), 
 *          col_a CHAR(16), 
 *          col_b CHAR(40), 
 *          col_c CHAR(40)
 *      );
 *      
 * PERFTEST's row size is 100 bytes(4 + 16 + 40 + 40). <tt>insert()</tt> methods used to insert rows to H2 PERFTEST table.
 * 
 *
 */
public class H2PERFTESTClient {
    
    static final String INSERT_SQL = "insert into PERFTEST values(?, ?, ?, ?)";
    
    public static final int KB = 1<<10;
    public static final int MB = 1<<20;
    public static final int GB = 1<<30;
    
    public static final String H2_JDBC_DRIVER = "org.h2.Driver";
    public static final String H2_JDBC_URL = "jdbc:h2:file:target/teiid-perf-ds;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1";
    public static final String H2_JDBC_USER = "sa";
    public static final String H2_JDBC_PASS = "sa";
    
    public static final String COL_ID = "1234";
    public static final String COL_A = "abcdefghabcdefgh";
    public static final String COL_B = "abcdefghigklmnopqrstabcdefghigklmnopqrst";
    public static final String COL_C = "1234567890123456789012345678901234567890";
    
    public static void insert(long row) throws Exception {
        insert(row, H2_JDBC_DRIVER, H2_JDBC_URL, H2_JDBC_USER, H2_JDBC_PASS, true);
    }
    
    public static void insert(long row, boolean reset) throws Exception {
        insert(row, H2_JDBC_DRIVER, H2_JDBC_URL, H2_JDBC_USER, H2_JDBC_PASS, reset);
    }
    
    public static void insert(long row, String driver, String url, String user, String pass, boolean reset) throws Exception {
        
        Connection conn = getDriverConnection(driver, url, user, pass);
        
        if(reset){
            executeSchema(conn);
        }
        
        System.out.print("\n" + Thread.currentThread().getName() + " thread inserting ");
        
        conn.setAutoCommit(false);
        long start = System.currentTimeMillis();
        PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL);
        
        int count = 0;
        for(long i = 0 ; i < row ; i ++) {
            pstmt.setString(1, COL_ID);
            pstmt.setString(2, COL_A);
            pstmt.setString(3, COL_B);
            pstmt.setString(4, COL_C);
            pstmt.addBatch();
            if((i + 1) % 1000 == 0){
                pstmt.executeBatch();
                conn.commit();
                count ++;
                if(count == 100) {
                    System.out.print(".");
                    count = 0;
                }
            } 
        }
        
        pstmt.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
        
        System.out.println("\n" + Thread.currentThread().getName() + " thread insert "  + row + " rows spend " +  + (System.currentTimeMillis() - start) + " ms\n");
    
        close(pstmt, conn);
 
    }
    
    static void executeSchema(Connection conn) throws SQLException {
        RunScript.execute(conn, new InputStreamReader(H2PERFTESTClient.class.getClassLoader().getResourceAsStream("h2-schema.sql")));
    }
    
}
