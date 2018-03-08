import java.sql.*;
import com.ibm.db2.jcc.*; 
import java.util.*;                                                            

// exekveras med java EzJava //adb2connect01:50000/FSYS_BDB user password
// exekveras med java EzJava_Incavt //localhost:50000/INCAVT user password
public class EzJava_Incavt 
{
  public static void main(String[] args) 
  {
    String urlPrefix = "jdbc:db2:";
    String url;
    String user;
    String password;
    String tabellid;
	String id;
	String skapad_tidp;
	String forandrad_tidp;
    Connection con;
    Statement stmt;
    ResultSet rs;
	    
    System.out.println ("**** Enter class EzJava");
    
    // Check the that first argument has the correct form for the portion
    // of the URL that follows jdbc:db2:,
    // as described
    // in the Connecting to a data source using the DriverManager 
    // interface with the IBM Data Server Driver for JDBC and SQLJ topic.
    // For example, for IBM Data Server Driver for 
    // JDBC and SQLJ type 2 connectivity, 
    // args[0] might be MVS1DB2M. For 
    // type 4 connectivity, args[0] might
    // be //stlmvs1:10110/MVS1DB2M.

    if (args.length!=3)
    {
      System.err.println ("Invalid value. First argument appended to "+
       "jdbc:db2: must specify a valid URL.");
      System.err.println ("Second argument must be a valid user ID.");
      System.err.println ("Third argument must be the password for the user ID.");
      System.exit(1);
    }
    url = urlPrefix + args[0];
    user = args[1];
    password = args[2];
    try 
    {                                                                        
      // Load the driver
      Class.forName("com.ibm.db2.jcc.DB2Driver");                               
      System.out.println("**** Loaded the JDBC driver");

      // Create the connection using the IBM Data Server Driver for JDBC and SQLJ
      // original! con = DriverManager.getConnection (url, user, password); 	  
      // Commit changes manually
      // original!con.setAutoCommit(false);
	  	  
	  Properties properties = new java.util.Properties(); // Create Properties object
	  properties.put("user", user); // Set user ID for the connection
	  properties.put("password", password); // Set password for the connection
	  properties.put("securityMechanism", "1");
	  new String("" + + com.ibm.db2.jcc.DB2BaseDataSource.CLEAR_TEXT_PASSWORD_SECURITY +"");
	  con = DriverManager.getConnection(url, properties);
	  
      System.out.println("**** Created a JDBC connection to the data source");

      // Create the Statement
      stmt = con.createStatement();                                             
      System.out.println("**** Created JDBC Statement object");

      // Execute a query and generate a ResultSet instance
      rs = stmt.executeQuery("SELECT TABELLID, ID, SKAPAD_TIDP, FORANDRAD_TIDP FROM TDB2.GDB010");                    
      System.out.println("**** Created JDBC ResultSet object");

      // Print all of the employee numbers to standard output device
      while (rs.next()) {
        tabellid = rs.getString(1);
		id = rs.getString(2);
		skapad_tidp = rs.getString(3);
		forandrad_tidp = rs.getString(4);
        System.out.println("Tabellid = " + tabellid + " Id = " + id + " Skapad_tidp = " + skapad_tidp + " Forandrad_tidp = " + forandrad_tidp);
      }
      System.out.println("**** Fetched all rows from JDBC ResultSet");
      // Close the ResultSet
      rs.close();
      System.out.println("**** Closed JDBC ResultSet");
      
      // Close the Statement
      stmt.close();
      System.out.println("**** Closed JDBC Statement");

      // Connection must be on a unit-of-work boundary to allow close
      con.commit();
      System.out.println ( "**** Transaction committed" );
      
      // Close the connection
      con.close();                                                              
      System.out.println("**** Disconnected from data source");

      System.out.println("**** JDBC Exit from class EzJava - no errors");

    }
    
    catch (ClassNotFoundException e)
    {
      System.err.println("Could not load JDBC driver");
      System.out.println("Exception: " + e);
      e.printStackTrace();
    }

    catch(SQLException ex)                                                       
    {
      System.err.println("SQLException information");
      while(ex!=null) {
        System.err.println ("Error msg: " + ex.getMessage());
        System.err.println ("SQLSTATE: " + ex.getSQLState());
        System.err.println ("Error code: " + ex.getErrorCode());
        ex.printStackTrace();
        ex = ex.getNextException(); // For drivers that support chained exceptions
      }
    }
  }  // End main
}    // End EzJava