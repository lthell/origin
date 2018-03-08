import java.sql.*;
import com.ibm.db2.jcc.*; 
//import com.ibm.db2.jcc4.*;
import java.util.*;                                                            

// exekveras med java EzJava //adb2connect01:50000/FSYS_BDB user password
// exekveras med java EzJava_Incavt //localhost:50000/INCAVT user password
// exekveras med java EzJava_Forsakomrad //DB2ConnectEE:50000/FSYS_ADB user password

public class EzJava_Forsakomrad 
{
  public static void main(String[] args) 
  {
    String urlPrefix = "jdbc:db2:";
    String url;
    String user;
    String password;
    String avtalsid;
	String systemid;
	String produktid_extern;
	String valcentralid;
	String forsakomrad_extern;
    String avtalsbenamning;
    String avtalsstatus;
    String startorsak_id;   	
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
      rs = stmt.executeQuery(" select g001.avtalsid, g001.systemid, g001.produktid_extern, g001.valcentralid," +
    	               "g001.forsakomrad_extern, g001.avtals_benamning, g001.avtalsstatus, g001.startorsak_id " +
                       "from pdb2.gdb001 g001 " +
                       "left join " + 
                       "pdb2.gdb113 g113 " +
                       " on " +
                       "g001.produktid_extern = g113.produktid_extern " +
                       "and coalesce(g001.valcentralid,0)               = g113.valcentralid -- För valcentral är (null) likvärdigt med 0 i GDB113 " +  
                       "and coalesce(g001.forsakomrad_extern,char(' ')) = g113.forsomr_id   -- För forsakomrad är (null) likvärdigt med blankt i GDB113 " + 
                       "where (     g113.valcentralid is null  -- left join ingen träff på valcentral " +
                       "or    g113.forsomr_id is null    -- left join ingen träff på forsomr_id       " +       
                       ") " +
                       "and exists ( select 1 from pdb2.gdb113 g113x " +
                       "where g001.produktid_extern = g113x.produktid_extern ) " +
                       "order by g001.produktid_extern ; ); " ) ;
	  
      System.out.println("**** Created JDBC ResultSet object");

      // Print all of the result set to standard output device
      while (rs.next()) {
        avtalsid           = rs.getString(1);
		systemid           = rs.getString(2);
		produktid_extern   = rs.getString(3);
		valcentralid       = rs.getString(4);
		forsakomrad_extern = rs.getString(5);
		avtalsbenamning    = rs.getString(6);
		avtalsstatus       = rs.getString(7);
		startorsak_id      = rs.getString(8);
     //   System.out.println("Tabellid = " + tabellid + " Id = " + id + " Skapad_tidp = " + skapad_tidp + " Forandrad_tidp = " + forandrad_tidp);
	    System.out.println(avtalsid + systemid + produktid_extern + valcentralid + forsakomrad_extern + avtalsbenamning + avtalsstatus + startorsak_id);
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
}    // End EzJava_Forsakomrad