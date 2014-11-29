package ClientServer;

// -- download MySQL from: http://dev.mysql.com/downloads/
//    Community Server version
// -- Installation instructions are here: http://dev.mysql.com/doc/refman/5.7/en/installing.html
// -- open MySQL Workbench to see the contents of the database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

// -- MAKE SURE THE JDBC CONNECTOR JAR IS IN THE BUILD PATH
//    workspace -> properties -> Java Build Path -> Libraries -> Add External JARs...
public class DBaseConnection {

	// -- objects to be used for database access
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rset = null;
    private LoginPanel lp;
    private int attempts = 0;
    private int dataBaseSelect = 1;
    private String _account = "";
    // -- connect to the world database
    // -- this is the connector to the database, default port is 3306
    private String url = null;
    // -- this is the username/password, created during installation and in MySQL Workbench
    //    When you add a user make sure you give them the appropriate Administrative Roles
    //    (DBA sets all which works fine)
    private String user = "Mike";
    private String dpassword = "test123";

	public DBaseConnection() {
	}
	
	public void executeQ()
	{
		switch(dataBaseSelect)
		{

		case 1:
			 url = "jdbc:mysql://localhost:3306/world";
			break;
		case 2:
			 url = "jdbc:mysql://localhost:3306/userdatabase";
			break;
		default:
			System.out.println("There is an error with the DB selection");
			break;
		}
		try {
            // -- make the connection to the database
			conn = DriverManager.getConnection(url, user, dpassword);
            
			// -- These will be used to send queries to the database
            stmt = conn.createStatement();
            rset = stmt.executeQuery("SELECT VERSION()");
            rset = stmt.executeQuery("SELECT * FROM user");

            System.out.println(url);
            if (rset.next()) 
            {
            	String tester = null;
            	tester = rset.getString(1);
                System.out.println(tester);
                if(tester.equals("test"))
                {
                	System.out.print("AHHH YEA");
                } 
            }
        
            if(rset.getString(1) == rset.getString(1))
            {
            	System.out.print("AHHH YEA");
            }
		}		
		catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	//handles login procedure
	public void executeLogin(String account, String password)
	{
		_account = account;		
		 url = "jdbc:mysql://localhost:3306/userdatabase";
		 try {
			 	//System.out.println(account);
	            // -- make the connection to the database
				conn = DriverManager.getConnection(url, user, dpassword);

				// -- These will be used to send queries to the database
	            stmt = conn.createStatement();
	            //rset = stmt.executeQuery("SELECT VERSION()");
	            rset = stmt.executeQuery("SELECT password FROM userdatabase.user WHERE username =" + "'" +account+"';");
	            if (rset.next()) 
	            {
	            	
	            	String accountTest = null;
	            	accountTest = rset.getString(1);
	            	getLoginAttempts();
	            	//System.out.println(tester);
	                if(accountTest.equals(password) && attempts < 4 )
	                {
	                	resetLoginAttempts();
	                	System.out.print("AHHH YEA");
	                }
	                else if(attempts > 4)
	                {
	                	System.out.println("Locked Account");
	                }
	                else
	                {
	                	incrementLoginAttempts();
	                	System.out.println("Invalid Password");
	                }

	            }
	            else
	            {
	            	lp.accountIsNotInDB();
	            }
	            
	            if(rset.getString(1) == rset.getString(1))
	            {
	            	System.out.print("AHHH YEA");
	            }
			}
	    		
			catch (SQLException ex) {
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		
	}
	public void getLoginAttempts()
	{
		url = "jdbc:mysql://localhost:3306/userdatabase";
    	try
    	{
			conn = DriverManager.getConnection(url, user, dpassword);
            stmt = conn.createStatement();
    		rset = stmt.executeQuery("SELECT attempts FROM userdatabase.user WHERE username =" + "'" +_account+"';");
    		if (rset.next()) 
	        {
          		attempts = rset.getInt(1);
	        }
    	}
    	catch (SQLException ex) 
    	{
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	
	}
	public void incrementLoginAttempts()
	{
		attempts++;
		url = "jdbc:mysql://localhost:3306/userdatabase";
    	try
    	{
			conn = DriverManager.getConnection(url, user, dpassword);
            stmt = conn.createStatement();
        	stmt.executeUpdate("UPDATE userdatabase.user SET attempts = '" +attempts+"' WHERE username = '"+_account+"';");
    	}
    	catch (SQLException ex) 
    	{
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	public void resetLoginAttempts()
	{
		url = "jdbc:mysql://localhost:3306/userdatabase";
    	try
    	{
			conn = DriverManager.getConnection(url, user, dpassword);
            stmt = conn.createStatement();
        	stmt.executeUpdate("UPDATE userdatabase.user SET attempts = '" +0+"' WHERE username = '"+_account+"';");
    	}
    	catch (SQLException ex) 
    	{
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	public void setSelection(int select)
	{
		dataBaseSelect = select;
	}
	public static void main(String[] args) {

		DBaseConnection dbc = new DBaseConnection();
	

	}

}
