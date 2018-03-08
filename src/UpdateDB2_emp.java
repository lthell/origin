import java.sql.*; 

// exekveras med java UpdateDB2_1 //DB2connectTest:50000/FSYS_BDB user password
public class UpdateDB2_emp 
{

  public static void main(String[] args)
  {  

  String s;
  String f;
  String stmtsrc;
  Connection con;
  Statement stmt;
  ResultSet rs;

  String url;
  String user;
  String password;

  

      url = "jdbc:db2://DB2connectTest:50000/FSYS_BDB";
      user = "lte1";
	  password = "????????";	
	
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
stmtsrc = "SELECT EMPNO, FIRSTNME FROM LTE1.EMP " +
"FOR UPDATE OF FIRSTNME";
rs = stmt.executeQuery(stmtsrc); // Create the ResultSet  
rs.afterLast(); // Position the cursor at the end of
				// the ResultSet  
while (rs.previous()) { // Position the cursor backward
s = rs.getString("EMPNO"); // Retrieve the employee number  
						   // (column 1 in the result table)
f = rs.getString("FIRSTNME"); // Retrieve the employee firstname  
						      // (column 2 in the result table)
System.out.println("Employee number = " + s + " Firstname = " + f);
// Print the column value
if (s.compareTo("000010") == 0) { // Look for employee 000010
rs.updateString("FIRSTNME","QHRISTINE"); // Update their firstname number
rs.updateRow(); 						 // Update the row
con.commit();							 // Commit the update
     }
   }
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
}   // End UpdateDB2_emp.java
