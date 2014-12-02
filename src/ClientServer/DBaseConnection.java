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
    //values to determine completetion of commands for conenction thread
    private String flowValues = "";
    //user entered 
    private String newPassword = "";
    //account exists/doesnt variable
    private boolean accountExists = true;
    //account old Password confirmation
    private boolean oldPassword = false;
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
	                	setflowValues("success"+ "," + "account");
	                	System.out.print("AHHH YEA");
	                }
	                else if(attempts > 4)
	                {
	                	setflowValues("locked"+ "," + "account");
	                	System.out.println("Locked Account");
	                }
	                else
	                {
	                	incrementLoginAttempts();
	                	setflowValues("invalid"+ "," + "account");
	                	System.out.println("Invalid Password");
	                }

	            }
	            else
	            {
	            	setflowValues("missing"+ "," + "account");
	            	System.out.println("account not in DB");
	            	//lp.accountIsNotInDB();
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
    			System.out.println(attempts);
          		attempts = rset.getInt(1);
    			System.out.println(attempts);
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
	public void checkForExistingAcc()
	{
		url = "jdbc:mysql://localhost:3306/userdatabase";
    	try
    	{
			conn = DriverManager.getConnection(url, user, dpassword);
            stmt = conn.createStatement();
    		rset = stmt.executeQuery("SELECT username FROM userdatabase.user WHERE username =" + "'" +_account+"';");
    		if (rset.next()) 
	        {
          		System.out.println("account exists");
          		accountExists = true;
	        }	
    		else
    		{
    			System.out.println("Account doesnt exist");
    			accountExists = false;
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
	public void createAccount(String account, String password, String email)
	{
		_account = account;
		url = "jdbc:mysql://localhost:3306/userdatabase";
    	try
    	{
			conn = DriverManager.getConnection(url, user, dpassword);
            stmt = conn.createStatement();
            checkForExistingAcc();
            System.out.println(accountExists);
            if(!accountExists)
            {
            	stmt.executeUpdate("INSERT INTO userdatabase.user(username, email, password, attempts) VALUES ('" +account+"','"+email+"','"+password+"', 0);");
            	setflowValues("created"+ "," + "account");
            	System.out.println("competed account add");
            }
            else
            {
            	setflowValues("exists"+ "," + "account");
            	System.out.println("Account Existed");
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
	public void userQuery()
	{
    	setflowValues("user"+ "," + "user");
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
	public void changePassword(String accName, String oldPass, String newPass)
	{
		_account = accName;
		//check for password match
		checkForExistingAcc();
		checkMatchedPassword(oldPass, newPass);
		url = "jdbc:mysql://localhost:3306/userdatabase";
		if(accountExists)
		{
			if(oldPassword)
			{
		    	try
		    	{
					conn = DriverManager.getConnection(url, user, dpassword);
		            stmt = conn.createStatement();
		        	stmt.executeUpdate("UPDATE userdatabase.user SET password = '"+newPass+"' WHERE username = '"+_account+"';");
		        	setflowValues("success"+ "," + "account");
		    	}
		    	catch (SQLException ex) 
		    	{
					// handle any errors
					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());
				}
			}
			else
			{
				
				//case
				setflowValues("oldpasswordfail" + "," + "account");
				System.out.println("Account old password does not match");
			}
		}
		else
		{
			//case
			setflowValues("missing"+ "," + "account");
			System.out.println("Account entered does not Exist");
		}
	}
	public void checkMatchedPassword(String oldPass, String newPass)
	{
		url = "jdbc:mysql://localhost:3306/userdatabase";
    	try
    	{
			conn = DriverManager.getConnection(url, user, dpassword);
            stmt = conn.createStatement();
        	rset = stmt.executeQuery("SELECT password FROM userdatabase.user WHERE username =" + "'" +_account+"';");
        	 if (rset.next()) 
	            {
	            	
	            	String accountTest = null;
	            	accountTest = rset.getString(1);
	            	System.out.println(accountTest);
	            	System.out.println(oldPass);
	                if(accountTest.equals(oldPass))
	                {
	                	oldPassword = true;
	                	resetLoginAttempts();
	                	System.out.print("Passwords matched");
	                }
	                else
	                {
	                	System.out.println("Invalid Password Match");
	                }

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
	public void checkPasswordExp()
	{
		url = "jdbc:mysql://localhost:3306/userdatabase";
    	try
    	{
			conn = DriverManager.getConnection(url, user, dpassword);
            stmt = conn.createStatement();
        	stmt.executeUpdate("UPDATE userdatabase.user SET password = '" +newPassword+"' WHERE username = '"+_account+"';");
    	}
    	catch (SQLException ex) 
    	{
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
public static void main(String[] args) {

		DBaseConnection dbc = new DBaseConnection();
	

	}

	public String getflowValues() {
		return flowValues;
	}

	public void setflowValues(String flowValues) {
		this.flowValues = flowValues;
	}

}
