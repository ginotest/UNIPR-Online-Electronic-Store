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
	
	void add(String type) {
		int select = 0, length = 0;
		String [] fields = new String[5];
		String[][] message = {{"Name:", "Surname: ", "Username: ", "Password: ", "Admin: "},{"ID: ", "Name Product: ", "Manufacturer: ", "Price (USD): " }};
		
		if(type=="employee") {
			select=0; length=5;
		}
		else if(type=="product") {
			select=1; length=4;
		}
		
		for(int i=0; i<length; i++) {
			System.out.print(message[select][i]);
			fields[i] = input.next();
		}
		addData(type, fields);
	}

	void editUser(){
		readAll("employee");
	}

	void remove(String type) {
		
		int select=0;
		String[] message = {"Write the USERNAME of the user you want to delete: ","Write the ID of the product you want to delete: " };
		String remove = input.next();
		if(type=="employee")
			select=0;
		else if(type=="product")
			select=1;
		System.out.print(message[select]);
		removeData(type, remove);
		
	}

}
