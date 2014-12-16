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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.LocalDate;

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
    //generate a date 
    private String _accountDate;
    //currentdate
    private LocalDate _currentDate;
    //password
    private String _tempPassword;
    private SendEmailUsingGMailSMTP emailservice = new SendEmailUsingGMailSMTP();
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
            rset = stmt.executeQuery("SELECT * FROM user;");

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
	                if(accountTest.equals(password) && attempts < 4 && !checkExpiration() )
	                {
	                	resetLoginAttempts();
	                	setflowValues("success"+ "," + "account");
	                	System.out.print("AHHH YEA");
	                }
	                else if(attempts > 4 )
	                {
	                	setflowValues("locked"+ "," + "account");
	                	System.out.println("Locked Account");
	                }
	                else if(checkExpiration())
	                {
	                	setflowValues("expired"+ "," + "account");

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
	//increments failed logins
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
	//retrieves current accounts login attemptes
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
	//checks for current account in db
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
	//handles account creation
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
            	modifyPassDate();
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
    	String notification = "Your account : "+_account+": was created :)  ";
		System.out.println(_account + _tempPassword + getAccountEmail()+ notification);
		emailservice.sendNotificationEmail(_account, getAccountEmail(), notification);
  
	}
	//pull from world db
	public void pullFromDB(String databaseCity, String cityInfo)
	{
		url = "jdbc:mysql://localhost:3306/world";
    	try
    	{
			conn = DriverManager.getConnection(url, user, dpassword);
            stmt = conn.createStatement();
            if(checkValidCity(databaseCity) && checkValidColumn(cityInfo))
            {
            	System.out.print("SELECT " +cityInfo+ " FROM world.city WHERE city =" + "'" +databaseCity+"';");
	    		rset = stmt.executeQuery("SELECT " +cityInfo+ " FROM world.city WHERE name =" + "'" +databaseCity+"';");
	    		if (rset.next()) 
		        {
	    			System.out.println(rset);
	    			String temp = rset.getString(1);
	          		setflowValues("success" + "," + "query" + "," + temp );
	    			System.out.println(attempts);
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
	//checks world db for valid city entry
	public boolean checkValidCity(String query)
	{
		url = "jdbc:mysql://localhost:3306/world";
    	try
    	{
			conn = DriverManager.getConnection(url, user, dpassword);
            stmt = conn.createStatement();
            System.out.print("test");
    		rset = stmt.executeQuery("SELECT * FROM world.city WHERE Name = '"+query+ "';");
    		if (rset.next()) 
	        {
    			return true;
	        }
    		else
    		{
    	  		setflowValues("failureforcity" + "," + "query" + "," + null);
    	  		System.out.println("Failed to find city");
    	  		return false;
    		}
    	}
    	catch (SQLException ex) 
    	{
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return false;
	}
	//future function for more info grabbing
	public boolean checkValidColumn(String query)
	{
		url = "jdbc:mysql://localhost:3306/world";
    	try
    	{
			conn = DriverManager.getConnection(url, user, dpassword);
            stmt = conn.createStatement();
    		rset = stmt.executeQuery("SELECT "+query+" FROM world.city;");
    		if (rset.next()) 
	        {
    			return true;
	        }
    		else
    		{
    	  		setflowValues("failureforcolumn" + "," + "query" + "," + null);
    	  		System.out.println("Failed to find column");
    	  		return false;
    		}
    	}
    	catch (SQLException ex) 
    	{
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return false;
	}
	//resets on every success login
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
	//password change
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
		        	resetLoginAttempts();
		        	modifyPassDate();
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
	public boolean checkExpiration()
	{
		getAccountDate();
		_currentDate = getCurrentDate();
		LocalDate checkDate = new LocalDate(_accountDate);
		checkDate = checkDate.plusDays(180);
		System.out.println(_currentDate);
		if(checkDate.isBefore(_currentDate))
		{
			return true;
		}
		
		return false;
		
		

	}
	public void getAccountDate()
	{
		url = "jdbc:mysql://localhost:3306/userdatabase";
    	try
    	{
			conn = DriverManager.getConnection(url, user, dpassword);
            stmt = conn.createStatement();
        	rset = stmt.executeQuery("SELECT passdate FROM userdatabase.user WHERE username = '" +_account+ "';");
        	if(rset.next())
        	{
        		String temp = rset.getString(1);
        		_accountDate = temp;
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
	public void modifyPassDate()
	{
		_currentDate = getCurrentDate();
		
		url = "jdbc:mysql://localhost:3306/userdatabase";
    	try
    	{
			conn = DriverManager.getConnection(url, user, dpassword);
            stmt = conn.createStatement();
        	stmt.executeUpdate("UPDATE userdatabase.user SET passdate = '" +_currentDate+"' WHERE username = '"+_account+"';");
    	}
    	catch (SQLException ex) 
    	{
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	public void setRandomPassword(String accName)
	{
		_account = accName;
		//check for password match
		checkForExistingAcc();
		_tempPassword = getRandomPassword() + "";
		url = "jdbc:mysql://localhost:3306/userdatabase";
		if(accountExists)
		{
		    	try
		    	{
					conn = DriverManager.getConnection(url, user, dpassword);
		            stmt = conn.createStatement();
		        	stmt.executeUpdate("UPDATE userdatabase.user SET password = '"+_tempPassword+"' WHERE username = '"+_account+"';");
		        	resetLoginAttempts();
		        	modifyPassDate();
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
			setflowValues("missing"+ "," + "account");
			System.out.println("Account entered does not Exist");
		}
		String notification = "Here is your temporary password : " + _tempPassword;
		System.out.println(_account + _tempPassword + getAccountEmail()+ notification);
		emailservice.sendRecoveryEmail(_account, _tempPassword, getAccountEmail(), notification);
    	setflowValues("success"+ "," + "email");

	}
	public String getAccountEmail()
	{
		url = "jdbc:mysql://localhost:3306/userdatabase";
    	try
    	{
			conn = DriverManager.getConnection(url, user, dpassword);
            stmt = conn.createStatement();
        	rset = stmt.executeQuery("SELECT email FROM userdatabase.user WHERE username = '" +_account+ "';");
        	if(rset.next())
        	{
        		String temp = rset.getString(1);
        		return temp;
        	}
    	}
    	catch (SQLException ex) 
    	{
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
    	return null;
	}
	
	public int getRandomPassword()
	{
		return (int)(Math.random()* (1000000 - 100000) + 100000);
	}
	public LocalDate getCurrentDate()
	{
		LocalDate now = LocalDate.now();
		LocalDate currentDate;
		currentDate = now;
		return currentDate;
	}
	public String getflowValues() {
		return flowValues;
	}

	public void setflowValues(String flowValues) {
		this.flowValues = flowValues;
	}

}
