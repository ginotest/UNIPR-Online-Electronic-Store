package ElectronicStore;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 
 * Client class
 *
 */
public class Client extends ManageData{
	/**
	 * reads input from console
	 */
	public Scanner input = new Scanner(System.in);
	private static ArrayList<String> user;

	/**
	 * constructor
	 */
	Client() {
		user = new ArrayList<String>();
	}
	
	/**
	 * registers a new client
	 */
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
		System.out.println("Registration successful!");
	}

	/**
	 * @param username client's username
	 * @param password client's password
	 * @return true if client exist
	 */
	boolean Login(String username, String password) {

		user = getProfile("user", username);

		if(user.isEmpty())
			return false;
		else if(!user.isEmpty() && ((user.get(1) != null) && (user.get(1).equals(password))))
			return true;
		else
			user = new ArrayList<String>();
		
		return false;
	}

	/**
	 * @return username of a client
	 */
	String getUsername() {
		return user.get(0);
	}

	/**
	 * prints product according to clients request
	 */
	void showProducts() {
		System.out.println("\n\t\t~{{PRODUCTS}}~\n");
		System.out.format("%-25s%-25s%-25s","\tNAME", "MANUFACTURER", "PRICE");
		System.out.println("\n------------------------------------------------------------------");
		printList();
	}

	/**
	 * calls the method to place a client order
	 */
	void order(){
		placeOrder(user.get(0));
	}

	/**
	 * calls the method to change the quantity of a specific product in client's cart
	 */
	void changeQuantity(){

		editQuantity(user.get(0));
	}

	/**
	 * calls the method to remove a specific product from client's cart
	 */
	void removeProduct(){

		removeFromCart(user.get(0));
	}

	/**
	 * accesses the cart of a client
	 */
	void cart() {
		readProducts();
		showCart(user.get(0));
	}

	/**
	 * adds product to cart
	 */
	void addToCart() {

		int quantity, selection;
		ArrayList<ArrayList<String>> product = elements;

		System.out.print("Which product number? : ");

		try {
			selection = input.nextInt()-1;

			}catch(InputMismatchException e){
				System.out.println("Incorrect choice.");
				return;
			}
		
		if(selection <= product.size()-1 && selection >= 0) {

			if(Integer.parseInt(product.get(selection).get(4))>0) {

				System.out.print("How many? ");
				
				try {
					quantity = input.nextInt();

					}catch(InputMismatchException e){
						System.out.println("That is not a number.");
						return;
					}
				 
				
				if(quantity <= Integer.parseInt(product.get(selection).get(4))) {

					System.out.println("You have added: " + quantity + " " + product.get(selection).get(2) + " " + product.get(selection).get(1) + " to the cart.");
					editData("user", 4, user.get(0), product.get(selection).get(0) + "," + quantity);
				}
				else
					System.out.println("There are only " + product.get(selection).get(4) + " " + product.get(selection).get(2) + " " + product.get(selection).get(1) + " available!");
			}
			else
				System.out.println("This product is currently unavailable!");
		}
		else
			System.out.println("This product does not exist.");
	}
	
	/**
	 * calls the method to read products from database
	 */
	void readProducts() {
		readAll("product");
	}

	/**
	 * calls the method to remove products from database
	 */
	void removeUser(){
		removeData("user",user.get(0));
	}

	/**
	 * sets new username
	 * @param newUsername new username to be set
	 */
	void changeUsername(String newUsername){

		if(!exist("user", newUsername)) {

			editData("user", 0, user.get(0), newUsername);
			user.set(0, newUsername);
			System.out.println("Username changed");
		}
		else
			System.out.println("Username already exists");
	}

	/**
	 * sets the address/new address of a client
	 * @param newAddress the address/new address to be set
	 */
	void setAddress(String newAddress){
		editData("user", 5,  user.get(0), newAddress);
		System.out.println("The address has been set!");
	}

	/**
	 * changes the password of a client
	 * @param oldPassword old password to be changed
	 * @param newPassword new password to be set
	 */
	void changePassword(String oldPassword, String newPassword){

		if (Login( user.get(0), oldPassword)) {

			editData("user", 1, user.get(0), newPassword);
			System.out.println("Password changed");
		}
		else {
			System.out.println("Your old password was entered incorrectly, please enter it again.");
		}
	}

	/**
	 * searches a product
	 * @param search category to be searched
	 * @param str the value to be searched for
	 */
	void searchProducts(String search, String str) {

		switch(search) {

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

	/**
	 * @return true if client is logged in
	 */
	public boolean session() {
		if(user.isEmpty())
			return false;
		return true;
	}

	/**
	 * clear a client logged in
	 */
	public void logout() {
		user = new ArrayList<String>();
	}

	/**
	 * sorts products in ascending/descending order
	 * @param string the order in which it is to be sorted (asc/dec)
	 */
	public void sortProducts(String string) {
		if(string.equals("asc"))
			elements = new ArrayList<ArrayList<String>>(elements.stream().sorted((product1, product2) -> Double.compare(Double.parseDouble(product1.get(3).substring(1)), Double.parseDouble(product2.get(3).substring(1)))).collect(Collectors.toList()));
		elements = new ArrayList<ArrayList<String>>(elements.stream().sorted((product1, product2) -> Double.compare(Double.parseDouble(product2.get(3).substring(1)), Double.parseDouble(product1.get(3).substring(1)))).collect(Collectors.toList()));
	}
}
