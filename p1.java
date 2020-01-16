import java.util.*;
import java.io.*;
import java.sql.*;

class p1 {

  private static String driver;
  private static String url;
  private static String username;
  private static String password;

  public static void main(String argv[]) {
    if (argv.length != 1) {
      System.out.println("Need database properties filename");
    } else {
      init(argv[0]);

      try {
        Class.forName(driver);                                                                  //load the driver
        Connection con = DriverManager.getConnection(url, username, password);                  //Create the connection
        Statement stmt = con.createStatement();   												//Create a statement
        /*
        String query = "SELECT FIRSTNME, LASTNAME, EDLEVEL, SALARY FROM EMPLOYEE";              //The query to run
        ResultSet rs = stmt.executeQuery(query);                                                //Executing the query and storing the results in a Result Set
        while(rs.next()) {                                                                      //Loop through result set and retrieve contents of each row
          String firstname = rs.getString(1);
          String lastname = rs.getString(2);
          int edlevel = rs.getInt(3);
          double salary = rs.getDouble(4);
          System.out.println(firstname + ",\t" + lastname + "," + edlevel + ",\t\t" + salary);        //Print out each row's values to the screen
        }
        */
        BankingSystem.init(argv[0]);
        BankingSystem.testConnection();
        Scanner scanner = new Scanner(System.in);
        boolean exitProg = false;
        while(!exitProg) {
        	//System.out.println("");
        	System.out.println("Welcome to the Self Services Banking System! – Main Menu");
        	System.out.println("1. New Customer");
        	System.out.println("2. Customer Login");
        	System.out.println("3. Exit");
        	int menu1 = scanner.nextInt();

        	if(menu1 == 1) {
        		// insert sql
        		Scanner scanner1 = new Scanner(System.in);
    			System.out.println("Please enter Customer name: ");
    			String name = scanner1.nextLine();
    			System.out.println("Please enter Customer gender (M or F): ");
    			String gender = scanner1.nextLine();
    			System.out.println("Please enter Customer age: ");
    			String age = scanner1.nextLine();
    			System.out.println("Please enter Customer PIN: ");
    			String p = scanner1.nextLine();

    			BankingSystem.newCustomer(name, gender, age, p);
        	}
        	else if(menu1 == 2) {
        		Scanner scanner2 = new Scanner(System.in);
        		System.out.println("Please enter Customer ID: ");
    			String menu2id = scanner2.nextLine();
    			System.out.println("Please enter Customer PIN: ");
    			String menu2pin = scanner2.nextLine();
    			
    			String m2query = "select ID, Pin from p1.Customer where ID = " + menu2id + " and Pin = " + menu2pin;
    			boolean m2login = false;
    			if(menu2id.equals("0") && menu2pin.equals("0")) {	// user is admin, menu4
    				boolean exitAdmin = false;
    				while(!exitAdmin) {
    					Scanner scanner3 = new Scanner(System.in);
    		        	System.out.println("Administrator Main Menu");
    		        	System.out.println("1. Account Summary for a Customer");
    		        	System.out.println("2. Report A :: Customer Information with Total Balance in Decreasing Order");
    		        	System.out.println("3. Report B :: Find the Average Total Balance Between Age Groups");
    		        	System.out.println("4. Exit");
    		        	int adminMenu = scanner3.nextInt();
    		        	
    		        	// report A
    		        	if (adminMenu == 1) {
    		        		Scanner scanner4 = new Scanner(System.in);
    		        		System.out.println("Please enter the Customer's ID: ");
    		        		String id = scanner4.nextLine();
    		        		BankingSystem.accountSummary(id);
    		        	}
    		        	else if(adminMenu == 2) {
    		        		BankingSystem.reportA();
    		        	}
    		        	// report B
    		        	else if(adminMenu == 3) {
    		        		Scanner scanner4 = new Scanner(System.in);
    		        		System.out.println("Please enter the minimum age for the group: ");
    		        		String min = scanner4.nextLine();
    		        		System.out.println("Please enter the maxmimum age for the group: ");
    		        		String max = scanner4.nextLine();
    		        		
    		        		BankingSystem.reportB(min, max);
    		        	}
    		        	// exit
    		        	else if(adminMenu == 4) {
    		        		exitAdmin = true;
    		        	}
    		        	else {
    		        		System.out.println("Please enter a valid option");
    		        	}
    				}
		        	
    			}
    			else {	// user is not admin
    				try {              //The query to run
            	        ResultSet rs = stmt.executeQuery(m2query);                                                
            	        while(rs.next()) {   
            	        	System.out.println("Customer login successful");
            	        	m2login = true;
            	        }
            	        rs.close(); 
            		}
            		catch(Exception e) {
            			System.out.println(e);
            		}
    				if(m2login) {	// successful customer login, menu3
    					boolean exit3 = false;
    					while(!exit3) {
    						Scanner scanner3 = new Scanner(System.in);
        		        	System.out.println("Customer Main Menu");
        		        	System.out.println("1. Open Account");
        		        	System.out.println("2. Close Account");
        		        	System.out.println("3. Deposit");
        		        	System.out.println("4. Withdraw");
        		        	System.out.println("5. Transfer");
        		        	System.out.println("6. Account Summary");
        		        	System.out.println("7. Exit");
        		        	int menu3 = scanner3.nextInt();

        		        	System.out.println("");
        		        	if(menu3 == 1) {	// open account
        		        		Scanner scanner4 = new Scanner(System.in);
        		        		System.out.println("Would you like to open a Checking or Savings Account?");
        		        		System.out.println("C for Checking or S for Savings: ");
        		        		String acType = scanner4.nextLine();
        		        		System.out.println("Initial balance: ");
        		        		String bal = scanner4.nextLine();
        		        		
        		        		BankingSystem.openAccount(menu2id, acType, bal);
        		        		System.out.println("");
        		        	}
        		        	else if(menu3 == 2) {	// close account
        		        		Scanner scanner4 = new Scanner(System.in);
        		        		System.out.println("Which account would you like to close? (Account #): ");
        		        		String actNum = scanner4.nextLine();
        		        		
        		        		String query1 = "select Status from p1.Account where Number = " + actNum + " and ID = " + menu2id;
        		        		
        		        		boolean ownership = false;	// verify account belongs to user
        		        		try {
        		        			stmt.execute(query1);	
        		        			ResultSet rs = stmt.executeQuery(query1);                                               
        		        	        while(rs.next()) {                                                                      
        		        	        	ownership = true;
        		        	        }
        		        		}
        		        		catch(Exception e) {
        	            			System.out.println("The account does not belong to you");
        	            		}
        		        		if(ownership) BankingSystem.closeAccount(actNum);
        		        		System.out.println("");
        		        	}
        		        	else if(menu3 == 3) {	// deposit
        		        		Scanner scanner4 = new Scanner(System.in);
        		        		System.out.println("Which account would you like to deposit to? (Account #): ");
        		        		String actNum = scanner4.nextLine();
        		        		System.out.println("How much would you like to deposit: ");
        		        		String depAmt = scanner4.nextLine();
        		        		
        		        		BankingSystem.deposit(actNum, depAmt);
        		        		System.out.println("");
        		        	}
        		        	else if(menu3 == 4) {	// withdraw
        		        		Scanner scanner4 = new Scanner(System.in);
        		        		System.out.println("Which account would you like to withdraw from? (Account #): ");
        		        		String actNum = scanner4.nextLine();
        		        		System.out.println("How much would you like to withdraw: ");
        		        		String witAmt = scanner4.nextLine();
        		        		
        		        		String query1 = "select Status from p1.Account where Number = " + actNum + " and ID = " + menu2id;
        		        		
        		        		boolean ownership = false;	// verify account belongs to user
        		        		try {
        		        			stmt.execute(query1);	
        		        			ResultSet rs = stmt.executeQuery(query1);                                               
        		        	        while(rs.next()) {                                                                      
        		        	        	ownership = true;
        		        	        }
        		        		}
        		        		catch(Exception e) {
        	            			System.out.println("The account does not belong to you");
        	            		}
        		        		if(ownership) {
        		        			BankingSystem.withdraw(actNum, witAmt);
        		        		}
        		        		else {
        		        			System.out.println("The account does not belong to you");
        		        		}

        		        		System.out.println("");
        		        	}
        		        	else if (menu3 == 5) {	// transfer
        		        		Scanner scanner4 = new Scanner(System.in);
        		        		System.out.println("What is the account # you want to transfer from: ");
        		        		String actFrm = scanner4.nextLine();
        		        		System.out.println("What is the account # you want to transfer to: ");
        		        		String actTo = scanner4.nextLine();
        		        		System.out.println("How much do you want to transfer: ");
        		        		String amt = scanner4.nextLine();
        		        		
        		        		String query1 = "select Status from p1.Account where Number = " + actFrm + " and ID = " + menu2id;
        		        		
        		        		boolean ownership = false;	// verify account belongs to user
        		        		try {
        		        			stmt.execute(query1);	// remove money
        		        			ResultSet rs = stmt.executeQuery(query1);                                                //Executing the query and storing the results in a Result Set
        		        	        while(rs.next()) {                                                                      //Loop through result set and retrieve contents of each row
        		        	        	ownership = true;
        		        	        }
        		        		}
        		        		catch(Exception e) {
        	            			System.out.println("The source account does not belong to you");
        	            		}
        		        		if(ownership) BankingSystem.transfer(actFrm, actTo, amt);
        		        		System.out.println("");
        		        	}
        		        	else if (menu3 == 6) {	// account summary
        		        		BankingSystem.accountSummary(menu2id);
        		        	}
        		        	else if(menu3 == 7) {	// exit
        		        		exit3 = true;
        		        	}
        		        	else {
        		        		System.out.println("Invalid selection, try again");
        		        	}
        		        			        		        	
    					}
    				}
    				else {
    					System.out.println("Customer login unsuccessful");
    				}
    			}

    			
        	}
        	else if(menu1 == 3) {
        		exitProg = true;
        	}
        	else {
        		System.out.println("");
        	}
        }
        
                                                                                    //Close the result set after we are done with the result set
        stmt.close();                                                                           //Close the statement after we are done with the statement
        con.close();                                                                            //Close the connection after we are done with everything
      } catch (Exception e) {
        System.out.println("Exception in main()");
        e.printStackTrace();
      }
    }
  }//main

  static void init(String filename) {
    try {
      Properties props = new Properties();                                                      //Create a new Properties object
      FileInputStream input = new FileInputStream(filename);                                    //Create a new FileInputStream object using our filename parameter
      props.load(input);                                                                        //Load the file contents into the Properties object
      driver = props.getProperty("jdbc.driver");                                                //Load the driver
      url = props.getProperty("jdbc.url");                                                      //Load the url
      username = props.getProperty("jdbc.username");                                            //Load the username
      password = props.getProperty("jdbc.password");                                            //Load the password
    } catch (Exception e) {
      System.out.println("Exception in init()");
      e.printStackTrace();
    }
  }//init
}//sample
