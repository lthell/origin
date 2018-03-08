import java.sql.*;
//import java.String.*;

 

// exekveras med java DB2Luw_employee //127.0.0.1:50000/SAMPLE user password  OBS ! Använd inte ODBC Data Source namn utan db2 instans dbname
public class DB2Luw_employee 
{

  public static void main(String[] args)
  {  

  String rsEMPNO;
  String rsFIRSTNME;
  String rsLASTNAME;
  String rsWORKDEPT;
  String rsJOB;
  String rsSALARY;
  String rsBONUS;
  String rsCOMM;
  String stmtsrc;
  Connection con;
  Statement stmt;
  ResultSet rs;

  String url;
  String user;
  String password;

  

      url = "jdbc:db2://127.0.0.1:50000/SAMPLE";
      user = "LAT1";
	  password = "********";	
	
   try	
   {	  
    // Load the driver
    Class.forName("com.ibm.db2.jcc.DB2Driver");                               
    System.out.println("**** Loaded the JDBC driver");

    // Create the connection using the IBM Data Server Driver for JDBC and SQLJ
    con = DriverManager.getConnection (url, user, password);                  
    // Commit changes manually
    con.setAutoCommit(false);
    System.out.println("**** Created a JDBC connection to the data source");

	stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
	ResultSet.CONCUR_UPDATABLE);  
	// Create a Statement object
	// for a scrollable, updatable
	// ResultSet
	stmtsrc = "SELECT EMPNO, FIRSTNME, LASTNAME, WORKDEPT, JOB,  SALARY , BONUS, COMM FROM LAT1.EMPLOYEE ";  // + 
	// "FOR UPDATE OF FIRSTNME";
	rs = stmt.executeQuery(stmtsrc); // Create the ResultSet  
	rs.afterLast(); // Position the cursor at the end of
					// the ResultSet
	System.out.format("%-6s, %-15s, %-12s, %-8s, %-8s, %10s, %10s, %10s\n", "EMPNO", "FIRSTNME", "LASTNAME", "WORKDEPT", "JOB", "SALARY",
	  "BONUS", "COMM");
	System.out.format("%-70s\n"," "); 
	  
	while (rs.previous()) { // Position the cursor backward
	rsEMPNO = rs.getString("EMPNO"); // Retrieve the employee number  
						   // (column 1 in the result table)
	rsFIRSTNME = rs.getString("FIRSTNME"); // Retrieve the employee firstname  
						      // (column 2 in the result table)
	rsLASTNAME = rs.getString("LASTNAME"); // Retrieve the employee firstname  
						      // (column 3 in the result table)
	rsWORKDEPT = rs.getString("WORKDEPT"); // Retrieve the employee firstname  
						      // (column 4 in the result table)
	rsJOB = rs.getString("JOB"); // Retrieve the employee firstname  
						      // (column 5 in the result table)
	rsSALARY = rs.getString("SALARY"); // Retrieve the employee firstname  
						      // (column 6 in the result table)
	rsBONUS = rs.getString("BONUS"); // Retrieve the employee firstname  
						      // (column 7 in the result table)
	rsCOMM = rs.getString("COMM"); // Retrieve the employee firstname  
						      // (column 8 in the result table)								  
	
	System.out.format("%-6s, %-15s, %-12s, %-8s, %-8s, %10s, %10s, %10s\n", rsEMPNO, rsFIRSTNME, rsLASTNAME, rsWORKDEPT, rsJOB, rsSALARY,
	  rsBONUS, rsCOMM);
	
	
	// Print the column value
	//if (s.compareTo("000010") == 0) { // Look for employee 000010
	//rs.updateString("FIRSTNME","QHRISTINE"); // Update their firstname number
	//rs.updateRow(); 						 // Update the row
	//con.commit();							 // Commit the update
    }
//  }
   rs.close(); // Close the ResultSet  
   stmt.close(); // Close the Statement
   
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
}
}   // End DB2Luw_employee.java
