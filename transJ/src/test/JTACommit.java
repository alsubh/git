/**
 * 
 */
package test;

/**
 * @author AnasR
 *
 */
import java.sql.*;

import javax.sql.*;

import java.util.*;

import javax.transaction.*;
import javax.transaction.xa.*;

import com.arjuna.ats.jdbc.TransactionalDriver;
import com.arjuna.ats.jta.xa.XidImple;
import com.arjuna.ats.internal.arjuna.objectstore.jdbc.drivers.h2_driver;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbc.*;
import org.h2.*;
import org.h2.jdbcx.JdbcDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Properties;

import javax.naming.*;


public class JTACommit {

    public static void main(java.lang.String[] args) {
        JTACommit test = new JTACommit();

        try {
			test.setup();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        test.run();
    }


    /**
     * Handle the previous cleanup run so that this test can recommence.
     * @throws SQLException 
     */
    public void setup() throws SQLException {

        Connection c = null;
        Statement s = null;
        try {
        	Class.forName("com.mysql.jdbc.Driver"); 
        	
        	c=DriverManager.getConnection("jdbc:mysql://localhost:3306/mysqldb","root","tedi"); 
        	
            s = c.createStatement();

            try {
                s.executeUpdate("DROP TABLE JTATABLE");
            } catch (SQLException e) {
                // Ignore... does not exist
            }

            s.executeUpdate("CREATE TABLE JTATABLE (COL1 CHAR (50))");
            s.close();
        } catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
            if (c != null) {
                c.close();
            }
        }
    }


    /** 
     * This test uses JTA support to handle transactions.
     */
    public void run() {
        Connection c = null;

        try {
            Context ctx = new InitialContext();

            // Assume the data source is backed by a UDBXADataSource.
            MysqlXADataSource ds = (MysqlXADataSource) ctx.lookup("MysqlXADS");

            // From the DataSource, obtain an XAConnection object that
            // contains an XAResource and a Connection object.
            XAConnection  xaConn = ds.getXAConnection();
            XAResource    xaRes  = xaConn.getXAResource();
            			  c      = xaConn.getConnection();

            // For XA transactions, a transaction identifier is required.
            // An implementation of the XID interface is not included with the 
            // JDBC driver. See Transactions with JTA for a description of
            // this interface to build a class for it.                
            Xid xid = new XidImple();

            // The connection from the XAResource can be used as any other 
            // JDBC connection.
            Statement stmt = c.createStatement();

            // The XA resource must be notified before starting any 
            // transactional work.
            xaRes.start(xid, XAResource.TMNOFLAGS);

            // Standard JDBC work is performed.
            int count = 
              stmt.executeUpdate("INSERT INTO JTATABLE VALUES('JTA is pretty fun.')");

            // When the transaction work has completed, the XA resource must 
            // again be notified.
            xaRes.end(xid, XAResource.TMSUCCESS);

            // The transaction represented by the transaction ID is prepared
            // to be committed.
            int rc = xaRes.prepare(xid);

            // The transaction is committed through the XAResource.
            // The JDBC Connection object is not used to commit
            // the transaction when using JTA.
            xaRes.commit(xid, false);


        } catch (Exception e) {
            System.out.println("Something has gone wrong.");
            e.printStackTrace();
        } finally {
            try {
                if (c != null)
                    c.close();
            } catch (SQLException e) {
                System.out.println("Note:  Cleaup exception.");
                e.printStackTrace();
            }
        }
    }
}