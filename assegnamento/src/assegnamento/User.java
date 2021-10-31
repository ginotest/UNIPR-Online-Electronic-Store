package assegnamento;

import java.util.ArrayList;
import java.util.Scanner;

public class User extends ManageData{
	public Scanner input = new Scanner(System.in);
	private static ArrayList<String> user;

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
		user = new ArrayList<String>();
		user.add(fields[2]);
		user.add(fields[3]);
	}

	boolean Login(String username, String password) {
		user = getProfile("user", username);
		if((user.get(1) != null) && (user.get(1).equals(password)))
			return true;
		else
			user.clear();
		return false;
	}

	String getUsername() {
		return user.get(0);
	}

	void showProducts() {

		System.out.format("%-25s%-25s%-25s","\tNAME", "MANUFACTURER", "PRICE");
		System.out.println("\n------------------------------------------------------------------");
		printList();

	}
	
	void order(){
		placeOrder(user.get(0));
	}
	
	void changeQuantity(){
		System.out.print("Which product? " );
		int id = input.nextInt();
		System.out.print("Enter the new quantity: " );
		int newQuantity = input.nextInt();
		editQuantity(user.get(0), id, newQuantity);
	}
	
	void removeProduct(){
		System.out.print("Which product? " );
		int id = input.nextInt();
		if(id == 1)
			id--;
		removeFromCart(user.get(0), id);
	}
	
	void cart() {
		readProducts();
		showCart(user.get(0));
	}
	
	void addToCart() {
		System.out.print("Which product? : ");
		int selection = input.nextInt()-1;
		System.out.print("How many? ");
		int quantity = input.nextInt();
		ArrayList<ArrayList<String>> product = getElements();
		System.out.println("You have added: " + quantity + " " + product.get(selection).get(1) + " to the cart.");
		editData("user", 4, user.get(0), product.get(selection).get(0) + "," + quantity);
	}
	
	void readProducts() {
		readAll("product");
	}

	void removeUser(){
		removeData("user",user.get(0));
	}

	void changeUsername(String newUsername){
		if(!exist("user", newUsername)) {
			editData("user", 0, user.get(0), newUsername);
			user.set(0, newUsername);
			System.out.println("Username changed");
		}
		else
			System.out.println("Username already exists");
	}
	
	void setAddress(String newAddress){
			editData("user", 5,  user.get(0), newAddress);
			System.out.println("The address has been set!");
	}

	void changePassword(String oldPassword, String newPassword){
			if (Login( user.get(0), oldPassword)) {
				editData("user", 1, user.get(0), newPassword);
				System.out.println("Password changed");
			}
			else {
				System.out.println("Your old password was entered incorrectly, please enter it again.");
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
