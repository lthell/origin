import java.sql.*;                                                             

// exekveras med java UpdateDB2_1 //adb2connect01:50000/FSYS_BDB user password
public class UpdateDB2_1 
{
  public static void main(String[] args) 
  {
    String urlPrefix = "jdbc:db2:";
    String url;
    String user;
    String password;
    String systemid;
	String systemparmid;
	String systemparmvarde;
    Connection con;
    Statement stmt;
	int numUpd;
    // ResultSet rs;
	    
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
    //try ?
    {                                                                        
      // Load the driver
      Class.forName("com.ibm.db2.jcc.DB2Driver");                               
      System.out.println("**** Loaded the JDBC driver");

      // Create the connection using the IBM Data Server Driver for JDBC and SQLJ
      con = DriverManager.getConnection (url, user, password);                  
      // Commit changes manually
      con.setAutoCommit(false);
      System.out.println("**** Created a JDBC connection to the data source");

      // Create the Statement
      stmt = con.createStatement();                                             
      System.out.println("**** Created JDBC Statement object");

      // Execute a query and generate a ResultSet instance
      numUpd = stmt.executeUpdate("UPDATE LTE1.EMPL SET FIRSTNME = ’QHRISTINE’ WHERE EMPNO=’000010’");   
      System.out.println("**** Created JDBC ResultSet object");

      // Print all of the employee numbers to standard output device
      
      System.out.println("**** Fetched all rows from JDBC ResultSet");
      // Close the ResultSet
      numUpd.close();
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
    
   // catch (ClassNotFoundException e)
   // {
   //   System.err.println("Could not load JDBC driver");
   //   System.out.println("Exception: " + e);
   //   e.printStackTrace();
   // }

   // catch(SQLException ex)                                                       
   // {
   //   System.err.println("SQLException information");
   //   while(ex!=null) {
   //     System.err.println ("Error msg: " + ex.getMessage());
   //     System.err.println ("SQLSTATE: " + ex.getSQLState());
   //     System.err.println ("Error code: " + ex.getErrorCode());
   //     ex.printStackTrace();
   //     ex = ex.getNextException(); // For drivers that support chained exceptions
   //   }
  //  } ?
  }  // End main
}    // End EzJava