package ElectronicStore;

import java.util.InputMismatchException;

/**
 * 
 * the main class, where the software begins
 *
 */
public class Main extends User_Interface{

	/**
	 * creates an Admin object
	 */
	static Admin admin = new Admin();
	/**
	 * creates a User object
	 */
	static Client user = new Client();
	/**
	 * creates an Employee object
	 */
	static Employee employee = new Employee();

	/**
	 * Causes the currently executing thread to exit
	 */
	static void closeStore() {
		clearScreen();
		System.exit(0);
	} 

	/**
	 * Causes the currently executing thread to sleep for the specified number of milliseconds
	 * 
	 * @param sec the length of time to sleep in milliseconds
	 */
	static void sleep(int sec) {
		try {
			Thread.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * prints out a specific message and causes the currently executing thread 
	 * to sleep for the specified number of milliseconds
	 * 
	 * @param msg message to be printed
	 */
	static void showMsg(String msg) {
		System.out.println();
		System.out.println(msg);
		sleep(3000);
	}

	/**
	 * redirects to the login menu after printing a message
	 * 
	 * @param msg message to be printed
	 */
	static void goToLogin(String msg) {
		sleep(1000);
		System.out.println(msg);
		sleep(4000);
		loginMenu();
	}

	/**
	 * prints out product
	 */
	static void showProducts() {
		clearScreen();
		title("store");
		user.showProducts();
		productsMenu();
	}

	/**
	 * logs out a user
	 * 
	 * @param who the user to be logged out
	 */
	private static void logout(String who) {

		showMsg("logging out..");

		switch(who) {

		case "user":
			user.logout();
			break;
		case "employee":
			employee.logout();
			break;
		case "admin":
			admin.logout();
			break;
		}
		homePage(true);
	}

	
	/**
	 * logs in a user
	 * 
	 * @param who the user to be logged in
	 */
	static void login(String who) {

		String username= "", password = "";
		clearScreen();
		title("login");

		System.out.print("\n username: ");
		username = input.nextLine();
		System.out.print("\n password: ");
		password = input.nextLine();

		switch(who.toLowerCase()) {

		case "user":
			if(user.Login(username, password)) {
				System.out.println("\nRedirecting...");
				sleep(2000);
				userMenu();
			}
			else {
				goToLogin("Incorrect Details!");
			}
			break;

		case "employee":
			if(employee.login(username, password)) {

				System.out.println("\nRedirecting...");
				sleep(1000);
				employeeMenu();
			}
			else {
				goToLogin("Incorrect Details!");
			}
			break;

		case "admin":
			if(admin.login(username, password)) {

				System.out.println("\nRedirecting...");
				sleep(1000);
				adminMenu();
			}
			else {
				goToLogin("Incorrect Details!");
			}
			break;
		}
	}


	/**
	 * manages the functions of an employee
	 */
	private static void employeeMenu() {

		while(true) {

			clearScreen();
			title("store");
			menu("employeeMenu");

			switch(input.nextLine()) {

			case "0":
				logout("employee");
				break;

			case "1":
				sleep(2000);
				ordersMenu();
				break;

			case "2":
				sleep(1500);
				notificationMenu();
				break;
				
			case "9":
				sleep(1500);
				homePage(true);
				break;

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				employeeMenu();
				break;
			}
		}
	}

	/**
	 * manages the functions of orders to ship
	 */
	private static void ordersMenu() {
		while(true) {

			clearScreen();
			employee.readOrders();
			employee.showOrders();
			menu("ordersMenu");
			
			String[] array;

			switch(input.nextLine()) {

			case "0":
				logout("employee");
				break;

			case "1":
				sleep(1000);
				System.out.print("\nEnter multiple choice separated by space\n");
				System.out.print("\nWhat number/s do you want to ship? ");
				String select = input.nextLine();
				array = select.trim().split(" ");
				sleep(2000);
				for(String order: array) {
					employee.shipProduct(Integer.parseInt(order));
					sleep(1000);
				}
				sleep(4000);
				ordersMenu();

			case "9":
				sleep(2000);
				employeeMenu();
				break;

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				ordersMenu();
				break;
			}
		}
	}

	/**
	 * manages notifications received
	 */
	private static void notificationMenu() {

		while(true) {

			clearScreen();

			if(!employee.showNotification()) {
				sleep(4000);
				employeeMenu();
			}

			menu("notificationMenu");

			switch(input.nextLine()) {

			case "0":
				logout("employee");
				break;

			case "1":
				sleep(1000);
				System.out.print("\nWhat number do you want to Restock? ");
				String select = input.nextLine();
				System.out.print("\nQuantity? ");
				int quantity;
				try {
					 quantity =  input.nextInt();

					}catch(InputMismatchException e){
						System.out.println("Incorrect quantity!");
						sleep(1000);
						return;
					}
				employee.restock(Integer.parseInt(select), quantity);
				sleep(4000);
				break;

			case "9":
				sleep(2000);
				employeeMenu();
				break;

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				notificationMenu();
				break;
			}
		}
	}

	/**
	 * manages the function of an administrator
	 */
	static void adminMenu() {

		while(true) {

			clearScreen();
			title("adminArea");
			menu("adminMenu");

			switch(input.nextLine()) {

			case "0":
				logout("admin");
				break;

			case "1":
				sleep(2000);
				adminSubMenu_employee(true);
				break;

			case "2":
				sleep(2000);
				adminSubMenu_product(true);
				break;

			case "9":
				sleep(2000);
				loginMenu();
				break;

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				adminMenu();
				break;
			}
		}
	}

	/**
	 * manages the functions of an employee through the administrator
	 */
	static void adminSubMenu_employee(boolean clear) {

		while(true) {

			if(clear) {
				clearScreen();
				title("adminArea");
			}
			menu("adminManageEmployeeMenu");

			switch(input.nextLine()) {

			case "0":
				logout("admin");

			case "1":
				admin.add("employee");
				sleep(2000);
				break;

			case "2":
				admin.edit("employee");
				sleep(2000);
				break;

			case "3":
				admin.remove("employee");
				sleep(2500);
				break;
				
			case "4":
				sleep(2000);
				clearScreen();
				title("adminArea");
				System.out.println("\n\t\t~{{EMPLOYEES}}~\n");
				admin.readData("employee");
				adminSubMenu_employee(false);
				break;

			case "9":
				sleep(2000);
				adminMenu();
				break;

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				adminSubMenu_employee(true);
				break;
			}
		}
	}


	/**
	 * manages the functions of products through the administrator
	 */
	static void adminSubMenu_product(boolean clear) {

		while(true) {
			
			if(clear) {
				clearScreen();
				title("adminArea");
			}
			menu("adminManageProductsTypeMenu");

			switch(input.nextLine()) {

			case "0":
				logout("admin");

			case "1":
				admin.add("product");
				sleep(2000);
				break;

			case "2":
				admin.remove("product");
				sleep(2500);
				break;
				
			case "3":
				sleep(2000);
				clearScreen();
				title("adminArea");
				System.out.println("\n\t\t~{{PRODUCTS}}~\n");
				admin.readData("product");
				adminSubMenu_product(false);
				break;

			case "9":
				sleep(2000);
				adminMenu();
				break;

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				adminSubMenu_product(true);
				break;
			}
		}
	}

	/**
	 * manages the functions of a user/buyer
	 */
	static void userMenu() {

		while(true) {

			clearScreen();
			title("store");
			menu("userMenu");

			switch(input.nextLine()) {

			case "0":
				logout("user");
				break;

			case "1":
				user.readProducts();
				sleep(2000);
				showProducts();
				break;

			case "7":
				sleep(1500);
				cartMenu();
				break;

			case "8":
				sleep(1500);
				profileMenu();
				break;

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				userMenu();
				break;
			}
		}
	}

	/**
	 * manages the functions of products
	 */
	static void productsMenu() {

		while(true) {

			menu("productsMenu");

			switch(input.nextLine()) {

			case "0":
				logout("user");
				break;

			case "1":
				user.addToCart();
				sleep(3000);
				showProducts();
				break;

			case "2":
				user.readProducts();
				sleep(2000);
				showProducts();
				break;

			case "3":
				sleep(1000);
				searchProductMenu();
				break;

			case "7":
				sleep(1500);
				cartMenu();
				break;

			case "9":
				sleep(2000);
				userMenu();
				break;

			case "":
				showProducts();
				break;

			default:
				showMsg("UNAVAILABLE");
				showProducts();
				break;
			}
		}
	}

	/**
	 * search/filters/sort products 
	 * 
	 * @param choice key that determines the search result
	 */
	static void searchProductMenu_sub(String choice) {

		String str;

		switch(choice) {

		case "0":
			if(user.session())
				logout("users");
			closeStore();
			break;

		case "1":
			System.out.print("\nEnter product Name: ");
			str = input.nextLine();
			user.searchProducts("name", str);
			break;

		case "2":
			System.out.print("\nEnter product Manufacturer: ");
			str = input.nextLine();
			user.searchProducts("manufacturer", str);
			break;

		case "3":
			System.out.print("\nEnter highest Price for products: ");
			str = input.nextLine();
			user.searchProducts("highestPrice", str);
			break;

		case "4":
			System.out.print("\nEnter lowest Price for products: ");
			str = input.nextLine();
			user.searchProducts("lowestPrice", str);
			break;
			
		case "5":
			user.sortProducts("asc");
			break;
			
		case "6":
			user.sortProducts("dsc");
			break;

		case "9":
			sleep(1000);
			if(user.session())
				showProducts();
			else
				homePage(true);
			break;

		case "":
			break;

		default:
			showMsg("UNAVAILABLE");
			searchProductMenu();
			break;
		}
	}


	/**
	 * manages the functions of product search
	 */
	static void searchProductMenu() {

		String[] array;

		while(true) {

			clearScreen();
			title("store");
			menu("searchProductMenu");

			String choices = input.nextLine().trim();

			if(!(choices.length() == 0)) {

				array = choices.split(" ");
				user.readProducts();

				for(String choice: array) {

					if (choice.trim().length() == 1)
						searchProductMenu_sub(choice);
				}
				sleep(2000);

				if(user.session())
					showProducts();
				else
					homePage(false);
			}
			else
				searchProductMenu();
		}
	}


	/**
	 * manages the details of a user/buyer
	 */
	static void profileMenu(){

		while(true) {

			clearScreen();
			title("store");
			menu("manageProfileMenu");

			switch(input.nextLine()) {

			case "0":
				logout("user");
				break;

			case "1":
				System.out.print("\n Enter new username: ");
				String newUsername = input.nextLine();
				sleep(1500);
				user.changeUsername(newUsername);
				System.out.println("\nRedirecting...");
				sleep(2000);
				userMenu();
				break;

			case "2":
				while(true) {

					System.out.print("\n Current password: ");
					String oldPassword = input.nextLine();
					System.out.print("\n New password: ");
					String newPassword = input.nextLine();
					System.out.print("\n New password, again: ");
					String newPassword2 = input.nextLine();
					if(newPassword == newPassword2)
						user.changePassword(oldPassword, newPassword);
					else
						System.out.println("Passwords do not match!");
					break;
				}
				System.out.println("\nRedirecting...");
				sleep(2000);
				userMenu();
				break;

			case "3":
				System.out.print("\n Enter your address: ");
				String newAddress = input.nextLine();
				user.setAddress(newAddress);
				System.out.println("\nRedirecting...");
				sleep(2000);
				userMenu();
				break;

			case "8":
				System.out.println("Password: ");
				String password = input.nextLine();

				if(user.Login(user.getUsername(), password)) {

					System.out.print("\n Are you sure you want to delete this account? (Y/N): ");
					String decision = input.nextLine();
					if((decision.equalsIgnoreCase("y"))) {

						user.removeUser();
						sleep(3000);
						loginMenu();
					}else {

						sleep(2000);
						profileMenu();
					}}
				else {

					System.out.println("Incorrect!");
					sleep(2000);
					profileMenu();
				}
				break;

			case "9":
				sleep(2000);
				userMenu();
				break;

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				profileMenu();
				break;
			}
		}
	}	


	/**
	 * manages the product of a user/buyer in cart
	 */
	static void cartMenu() {

		while(true) {

			clearScreen();
			title("cart");
			user.cart();
			menu("cartMenu");

			switch(input.nextLine()) {

			case "0":
				logout("user");
				break;

			case "1":
				user.removeProduct();
				sleep(2000);
				cartMenu();
				break;

			case "3":
				sleep(2000);
				showProducts();
				break;

			case "2":
				sleep(1000);
				user.changeQuantity();
				sleep(2000);
				cartMenu();
				break;

			case "5":
				sleep(2000);
				user.order();
				sleep(4000);
				cartMenu();
				break;

			case "9":
				user.readProducts();
				sleep(2000);
				userMenu();
				break;

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				cartMenu();
				break;
			}
		}
	}

	
	/**
	 * manages the login of all user buyers/sellers
	 */
	static void loginMenu() {

		boolean run = true;

		while(run) {
			clearScreen();
			title("store");
			menu("loginMenu");

			switch(input.nextLine()) {

			case "0":
				closeStore();
				break;

			case "1":
				run = false;
				sleep(1000);
				if(user.session())
					userMenu();
				else
					login("user");
				break;

			case "2":
				sleep(1000);
				user.register();
				sleep(3000);
				userMenu();
				break;

			case "3":
				run = false;
				sleep(1000);
				if(admin.session())
					adminMenu();
				else
					login("admin");

			case "4":
				run = false;
				sleep(1000);
				if(employee.session())
					employeeMenu();
				else
					login("employee");
				break;
				
			case "9":
				run = false;
				sleep(1000);
				homePage(true);
				break;

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				loginMenu();
				break;
			}
		}
	}

	/**
	 * manages the home page of the store
	 * 
	 * @param read true to read products from database
	 */
	static void homePage(boolean read) {

		while(true) {

			sleep(1000);
			clearScreen();
			title("store");

			if(read)
				user.readProducts();

			user.showProducts();
			menu("homePageMenu");

			switch(input.nextLine()) {

			case "0":
				closeStore();
				break;

			case "1":
				sleep(1000);
				searchProductMenu();
				break;

			case "2":
				sleep(1000);
				loginMenu();
				break;

			case "3":
				sleep(1000);
				homePage(true);
				break;

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				homePage(false);
				break;
			}
		}
	}


	/**
	 * starting point
	 * 
	 * @param args console arguments
	 */
	public static void main(String[] args) {
		homePage(true);
	}

}
