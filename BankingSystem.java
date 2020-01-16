import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Manage connection to database and perform SQL statements.
 */
public class BankingSystem {
	// Connection properties
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	
	// JDBC Objects
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;
	
	// db properties
	private static int userID;	// keeps track of which customer ID is the latest
	private static int acctID;	// keeps track of which account ID is the latest
	/**
	 * Initialize database connection given properties file.
	 * @param filename name of properties file
	 */
	public static void init(String filename) {
		try {
			Properties props = new Properties();						// Create a new Properties object
			FileInputStream input = new FileInputStream(filename);	// Create a new FileInputStream object using our filename parameter
			props.load(input);										// Load the file contents into the Properties object
			driver = props.getProperty("jdbc.driver");				// Load the driver
			url = props.getProperty("jdbc.url");						// Load the url
			username = props.getProperty("jdbc.username");			// Load the username
			password = props.getProperty("jdbc.password");			// Load the password
			
			userID = 100;
			acctID = 1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test database connection.
	 */
	public static void testConnection() {
		System.out.println(":: TEST - CONNECTING TO DATABASE");
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			con.close();
			System.out.println(":: TEST - SUCCESSFULLY CONNECTED TO DATABASE");
			} catch (Exception e) {
				System.out.println(":: TEST - FAILED CONNECTED TO DATABASE");
				e.printStackTrace();
			}
	  }

	/**
	 * Create a new customer.
	 * @param name customer name
	 * @param gender customer gender
	 * @param age customer age
	 * @param pin customer pin
	 */
	public static void newCustomer(String name, String gender, String age, String pin) 
	{
		String query = "insert into p1.Customer(Name, Gender, Age, Pin) values('" + name + "', '" + gender + "', '" + age + "', '" + pin + "')";
		try {  
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
	        Statement stmt = con.createStatement();
	        stmt.execute(query);  
	        con.close();
	        System.out.println("User created - ID: " + userID);
	        System.out.println(":: CREATE NEW CUSTOMER - SUCCESS");
	        userID++;
		}
		catch(Exception e) {
			System.out.println(":: CREATE NEW CUSTOMER - FAILURE");
		}
	}

	/**
	 * Open a new account.
	 * @param id customer id
	 * @param type type of account
	 * @param amount initial deposit amount
	 */
	public static void openAccount(String id, String type, String amount) 
	{
		String query = "insert into p1.Account(ID, Balance, Type, Status) values('" + id + "', '" + amount + "', '" + type + "', 'A')";
		try {  
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
	        Statement stmt = con.createStatement();
	        stmt.execute(query);  
	        con.close();
	        System.out.println("Account created - #: " + acctID);
	        System.out.println(":: OPEN ACCOUNT - SUCCESS");
	        acctID++;
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Close an account.
	 * @param accNum account number
	 */
	public static void closeAccount(String accNum) 
	{
		String query = "update p1.Account set Status = 'I' where Number = " + accNum;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
	        Statement stmt = con.createStatement();
			stmt.execute(query);
			con.close();
			System.out.println(":: CLOSE ACCOUNT - SUCCESS");
		}
		catch(Exception e) {
			System.out.println(":: CLOSE ACCOUNT - FAILURE");
		}
	}

	/**
	 * Deposit into an account.
	 * @param accNum account number
	 * @param amount deposit amount
	 */
	public static void deposit(String accNum, String amount) 
	{
		String query = "update p1.Account set balance = balance + " + amount + " where Number = " + accNum;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
	        Statement stmt = con.createStatement();
			stmt.execute(query);
			con.close();
			System.out.println(amount + " has been successfully deposited into Account #" + accNum);
			System.out.println(":: DEPOSIT - SUCCESS");
		}
		catch(Exception e) {
			System.out.println(":: DEPOSIT - FAILURE");
		}
	}

	/**
	 * Withdraw from an account.
	 * @param accNum account number
	 * @param amount withdraw amount
	 */
	public static void withdraw(String accNum, String amount) 
	{
		String query = "update p1.Account set balance = balance - " + amount + " where Number = " + accNum;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
	        Statement stmt = con.createStatement();
			stmt.execute(query);
			con.close();
			System.out.println(amount + " has been successfully withdrawn from Account #" + accNum);
			System.out.println(":: WITHDRAW - SUCCESS");
		}
		catch(Exception e) {
			System.out.println(":: WITHDRAW - FAILURE");
		}
	}

	/**
	 * Transfer amount from source account to destination account. 
	 * @param srcAccNum source account number
	 * @param destAccNum destination account number
	 * @param amount transfer amount
	 */
	public static void transfer(String srcAccNum, String destAccNum, String amount) 
	{
		String query1 = "update p1.Account set balance = balance - " + amount + " where Number = "+ srcAccNum;
		String query2 = "update p1.Account set balance = balance + " + amount + " where Number = "+ destAccNum;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
	        Statement stmt = con.createStatement();
			stmt.execute(query1);	// remove money
			try {
    			stmt.execute(query2);	// deposit money
    			System.out.println(amount + " has been successfully transfered from Account #" + srcAccNum + " to Account#" + destAccNum);
    		}
    		catch(Exception e) {
    			System.out.println(":: TRANSFER - FAILURE");
    		}
			con.close();
		}
		catch(Exception e) {
			System.out.println(":: TRANSFER - FAILURE");
		}
	}

	/**
	 * Display account summary.
	 * @param cusID customer ID
	 */
	public static void accountSummary(String cusID) 
	{
		String query = "select Number, Type, Balance from p1.Account where ID = " + cusID + " and Status = 'A'";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
	        Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Account # 	 Account Type 		 Balance");
			System.out.println("---------------------------------------------");
	        while(rs.next()) {                                                                      
	          int num = rs.getInt(1);
	          String type = rs.getString(2);
	          int bal = rs.getInt(3);
	          System.out.println(num + "   \t\t" + type + " \t\t        " + bal);        
	        }
	        rs.close();
	        
	        System.out.println("");
	        
	        String query2 = "select SUM(Balance) as Total from p1.Account where ID = " + cusID + " and Status = 'A'";
	        System.out.println("Total Balance");
	        System.out.println("---------------");
	        ResultSet rs2 = stmt.executeQuery(query2);
	        while(rs2.next()) {                                                                      
	            int num = rs2.getInt(1);
	            System.out.println(num);        
	          }
	        rs2.close();
	        con.close();
	        System.out.println(":: ACCOUNT SUMMARY - SUCCESS");
	        System.out.println("");
		}
		catch(Exception e) {
			System.out.println(":: ACCOUNT SUMMARY - FAILURE");
		}
	}

	/**
	 * Display Report A - Customer Information with Total Balance in Decreasing Order.
	 */
	public static void reportA() 
	{
		String query = "select * from CustomerBalance order by TotalBalance desc";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
	        Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("ID 	 Name 		Age    Gender      Balance");
			System.out.println("---------------------------------------------------");
	        while(rs.next()) {                                                                      
	          int id = rs.getInt(1);
	          String name = rs.getString(2);
	          int age = rs.getInt(3);
	          String gender = rs.getString(4);
	          int balance = rs.getInt(5);
	          System.out.println(id + "   \t " + name + "    \t" + age + "     " + gender + "            " + balance);        
	        }
			System.out.println(":: REPORT A - SUCCESS");
		}
		catch(Exception e) {
			System.out.println(":: REPORT A - SUCCESS");
		}
	}

	/**
	 * Display Report B - Customer Information with Total Balance in Decreasing Order.
	 * @param min minimum age
	 * @param max maximum age
	 */
	public static void reportB(String min, String max) 
	{
		String query = "select avg(TotalBalance) from CustomerBalance where age >= " + min + " and age <= " + max;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
	        Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				System.out.println("Average Balance = " + rs.getInt(1));
			}
			
			System.out.println(":: REPORT B - SUCCESS");
		}
		catch(Exception e) {
			System.out.println(":: REPORT B - FAILURE");
		}
	}
}
