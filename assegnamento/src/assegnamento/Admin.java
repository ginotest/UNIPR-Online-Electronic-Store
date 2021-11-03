package assegnamento;

import java.util.Scanner;
import java.util.ArrayList;

public class Admin extends ManageData{

	public Scanner input = new Scanner(System.in);
	public Scanner input2 = new Scanner(System.in);
	private static ArrayList<String> admin;
	
	
	public Admin() {
		admin = new ArrayList<String>();
	}

	boolean login(String username, String password) {
		admin = getProfile("employee", username);
		if(admin.isEmpty())
			return false;
		else if(!admin.isEmpty() && (admin.get(1).equals(password) && admin.get(2).equals("true")))
			return true;
		else
			admin.clear();
		return false;
	}

	void add(String type) {
		int select = 0;
		String [] fields = new String[5];
		String[][] message = {{"Name:", "Surname: ", "Username: ", "Password: ", "Admin: "},{"ID: ", "Name Product: ", "Manufacturer: ", "Price (USD): "}};

		if(type=="employee") {
			select=0;
		}
		else if(type=="product") {
			select=1;
		}


		for(int i=0; i < message[select].length ; i++) {
			System.out.print(message[select][i]);
			fields[i] = input.nextLine();
			if(exist("product", fields[i]) && i==0 && type=="product") {
				System.out.println("\nA product with this ID already exists\n");
				i--;
			}
		}
		addData(type, fields);
	}

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
		String select = input.next();
		System.out.println("You can change the following information: ");
		for(int i=0; i<5;i++)
			System.out.println(i+1 +") " + message[idx][i]);

		System.out.print("What do you want to change?  ");
		int n = input.nextInt()-1;
		System.out.print("Write the new " + message[idx][n].toLowerCase() + ": ");
		String content = input2.nextLine();
		if(((message[idx][n].equals(message[0][0])) && !exist("employee", content)) || !message[idx][n].equals(message[0][0])   ) {
			editData("employee", n, select, content);
		}
		else
			System.out.println("Username already exists");
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
	
	public void logout() {
		admin = new ArrayList<String>();
	}

}
