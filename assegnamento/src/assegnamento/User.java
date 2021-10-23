package assegnamento;

import java.util.ArrayList;
import java.util.Scanner;

public class User extends ManageData{
	public Scanner input = new Scanner(System.in);
	private static String usernameLogged;
	
	void register() {
		String [] fields = new String[4];
		String[] message = {"Name:", "Surname: ", "Username: ", "Password: "};

		for(int i=0; i<4; i++) {
			System.out.print(message[i]);
			fields[i] = input.next();
		}

		addData("user", fields);
	}

	boolean Login(String username, String password) {
		String pwd = getPassword("user", username);
		if((pwd != null) && (pwd.equals(password))) {
			User.usernameLogged = username;
			return true;
		}
		return false;
	}
	
	void showProducts() {
	 ArrayList<ArrayList<String>> products = getAllProducts();
	 //System.out.println(products);
     System.out.format("%-25s%-25s%-25s","\tNAME", "MANUFACTURER", "PRICE");
	 //System.out.println("    NAME\t\t\tMANUFACTURER\t\tPRICE");
	 for (int i = 0; i < products.size(); i++) {
		 System.out.print(i+1 + ")  ");
		    for (int j = 1; j < products.get(i).size(); j++) 
	        System.out.format("%-25s",products.get(i).get(j));
		        //System.out.print(products.get(i).get(j)+"\t\t\t");
		    System.out.println();
		}
	 System.out.println();
	}
	
	void removeUser(){
		removeData("user",usernameLogged);
	}
	
	void changeUsername(String newUsername){
		
	}
}
