package assegnamento;

import java.util.Scanner;

public class User extends ManageData{
	public Scanner input = new Scanner(System.in);
	private static String usernameLogged;

	void register() {
		String [] fields = new String[4];
		String[] message = {"Name: ", "Surname: ", "Username: ", "Password: "};
		System.out.println();
		for(int i=0; i<4; i++) {
			System.out.print(message[i]);
			fields[i] = input.next();
			if(i == 2)
				if(exist("user", fields[i])) {
					System.out.println("\nUsername already exists\n");
					i--;
				}
		}
		addData("user", fields);	
		User.usernameLogged = fields[2];
	}

	boolean Login(String username, String password) {
		String pwd = getPassword("user", username);
		if((pwd != null) && (pwd.equals(password))) {
			User.usernameLogged = username;
			return true;
		}
		return false;
	}

	String getUsername() {
		return User.usernameLogged;
	}

	void showProducts() {

		System.out.format("%-25s%-25s%-25s","\tNAME", "MANUFACTURER", "PRICE");
		System.out.println("\n------------------------------------------------------------------");
		printList();

	}
	
	void readProducts() {
		readAll("product");
	}

	void removeUser(){
		removeData("user",usernameLogged);
	}

	void changeUsername(String newUsername){
		if(!exist("user", newUsername)) {
			editData("user", 0, usernameLogged, newUsername);
			User.usernameLogged = newUsername;
			System.out.println("Username changed");
		}
		else
			System.out.println("Username already exists");
	}

	boolean changePassword(String oldPassword, String newPassword, String newPassword2){


		if(newPassword.equals(newPassword2)) {
			if (Login(usernameLogged, oldPassword)) {
				editData("user", 1, usernameLogged, newPassword);
				System.out.println("Password changed");
				return true;
			}
			else {
				System.out.println("Your old password was entered incorrectly, please enter it again.");
				return false;
			}
		}
		else {
			System.out.println("Passwords do not match");
			return false;
		}

	}

	 void searchProducts(String type, String str) {
		 switch(type) {
		 case "name":
			 filterList(1, str);
			 break;
			 
		 case "manufacturer":
			 filterList(2, str);
			 break;
			 
		 case "highestPrice":
			 filterList(3, str);
			 break;
			 
		 case "lowestPrice":
			 filterList(4, str);
			 break;
			 
		default:
			break;
		 }
	}
}
