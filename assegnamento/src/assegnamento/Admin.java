package assegnamento;

import java.util.Scanner;
import java.util.ArrayList;

public class Admin extends ManageData{

	public Scanner input = new Scanner(System.in);
	private static ArrayList<String> admin;
	
	
	boolean login(String username, String password) {
		admin = getAdmin();
		if(admin.get(0).equals("true") && admin.get(1).equals(username) && admin.get(2).equals(password))
			return true;
		return false;
	}
	
	//merge with the other function addUser()
	void addProductType(){
		
		String [] fields = new String[4];
		String[] message = {"ID: ", "Name Product: ", "Manufacturer: ", "Price (USD): " };

		for(int i=0; i<4; i++) {
			System.out.print(message[i]);
			fields[i] = input.next();
		}

		addData("product", fields);
		
	}
	void removeProductType(){
		
		System.out.print("Write the ID of the product you want to delete: ");
		String product = input.next();
		removeData("product",product);
		
	}
	void addUser(){

		String [] fields = new String[5];
		String[] message = {"Name:", "Surname: ", "Username: ", "Password: ", "Admin: "};

		for(int i=0; i<5; i++) {
			System.out.print(message[i]);
			fields[i] = input.next();
		}

		addData("employee", fields);
	}

	void editUser(){
		readAll("employee");
	}

	void removeUser(){
		System.out.print("Write the USERNAME of the user you want to delete: ");
		String user = input.next();
		removeData("employee",user);
	}
}
