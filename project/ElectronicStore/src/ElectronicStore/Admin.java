package ElectronicStore;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * 
 * Admin class
 *
 */
public class Admin extends ManageData{

	/**
	 * reads input from console
	 */
	public Scanner input = new Scanner(System.in);
	private static ArrayList<String> admin;
	
	
	/**
	 * admin constructor
	 */
	Admin() {
		admin = new  ArrayList<String>();
	}

	/**
	 * @param username username of admin
	 * @param password password of admin
	 * @return true if the admin profile exists
	 */
	boolean login(String username, String password) {

		admin = getProfile("employee", username);

		if(admin.isEmpty())
			return false;
		else if(!admin.isEmpty() && (admin.get(1).equals(password) && admin.get(2).equals("true")))
			return true;
		else
			admin = new  ArrayList<String>();

		return false;
	}
	
	/**
	 * @return true if admin is logged in
	 */
	boolean session() {
		if(admin.isEmpty())
			return false;
		return true;
	}
	
	/**
	 * adds a new employee or product
	 * 
	 * @param type new data to add
	 */
	void add(String type) {

		int select = 0;
		String [] fields = new String[5];
		String[][] message = {{"Name:", "Surname: ", "Username: ", "Password: ", "Admin: "},{"ID: ", "Name Product: ", "Manufacturer: ", "Price (USD): "}};

		if(type == "employee") {
			select = 0;
		}
		else if(type == "product") {
			select = 1;
		}


		for(int i = 0; i < message[select].length ; i++) {

			System.out.print(message[select][i]);
			fields[i] = input.nextLine();

			if(exist("product", fields[i]) && i == 0 && type == "product") {
				System.out.println("\nA product with this ID already exists\n");
				i--;
			}
		}
		addData(type, fields);
	}

	/**
	 * edits an employee profile or a product in database
	 * 
	 * @param type data to edit
	 */
	void edit(String type){

		int idx = 0;
		String[][] message = {{"Username", "Password", "Name", "Surname", "Adminship (true/false)"},{"ID", "Name Product", "Manufacturer", "Price (USD)", "Quantity"}};

		if(type=="employee") {
			idx=0;
		}
		else if(type=="product") {
			idx=1;
		}

		System.out.print("Type the " + message[idx][0].toUpperCase() + " of the " + type + " you want to edit: ");
		String selectedIdentifier = input.next();
		System.out.println("You can change the following information: ");

		for(int i = 0; i < 5 ; i++)
			System.out.println(i+1 +") " + message[idx][i]);

		System.out.print("What do you want to change?  ");
		
		int choice;
		try {
			choice = input.nextInt()-1;

			}catch(InputMismatchException e){
				System.out.println("Incorrect choice.");
				return;
			}
		 
		input.nextLine();
		System.out.print("Write the new " + message[idx][choice].toLowerCase() + ": ");
		String content = input.nextLine();

		if(((message[idx][choice].equals(message[0][0])) && !exist("employee", content)) || !message[idx][choice].equals(message[0][0])   ) {
			editData("employee", choice, selectedIdentifier, content);
			System.out.println("\nSuccessfully edited\n");
		}
		else
			System.out.println("Username already exists");
	}

	/**
	 * removes employee profile o a product
	 * 
	 * @param type data to remove
	 */
	void remove(String type) {

		int select=0;
		String[] message = {"Write the USERNAME of the user you want to delete: ","Write the ID of the product you want to delete: " };
		String identifier = input.next();

		if(type=="employee")
			select=0;
		else if(type=="product")
			select=1;

		System.out.print(message[select]);
		removeData(type, identifier);

	}

	/**
	 * clears admin profile logged in
	 */
	public void logout() {
		admin = new  ArrayList<String>();
	}

	/**
	 * @param string data type to be read
	 */
	public void readData(String string) {
		readAll(string);
		int limit=0;
		
		System.out.println("\n");
		if(string == "employee") {
			System.out.format("%-25s%-25s%-25s%-25s%-25s","NAME", "SURNAME", "USERNAME", "PASSWORD", "ADMINSHIP");
			System.out.println("\n------------------------------------------------------------------------------------------------------------------");
			limit=1;
		}
		else {
			System.out.format("%-25s%-25s%-25s%-25s%-25s","ID", "NAME", "MANUFACTURER", "PRICE", "QUANTITY");
			System.out.println("\n------------------------------------------------------------------------------------------------------------------");
			limit=0;
		}
		
		if(elements.size() == 0) {
			System.out.print("\nNo Result\n");
			return;
		}

		for (int i = 0; i < elements.size(); i++) {

			System.out.print("\n" + (i+1) + ")  ");

			for (int j = 0; j < elements.get(i).size()-limit; j++) {
					System.out.format("%-25s",elements.get(i).get(j));	
			}
			System.out.println();
		}
		System.out.println();	
	}

}
