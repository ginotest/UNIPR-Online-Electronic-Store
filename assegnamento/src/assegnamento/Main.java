package assegnamento;


public class Main extends User_Interface{

	static Admin admin = new Admin();
	static User user = new User();
	static Employee employee = new Employee();

	static void closeStore() {
		clearScreen();
		System.exit(0);
	} 

	static void sleep(int sec) {
		try {
			Thread.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static void showMsg(String msg) {
		System.out.println();
		System.out.println(msg);
		sleep(3000);
	}

	static void goToLogin(String msg) {
		sleep(1000);
		System.out.println(msg);
		sleep(4000);
		loginMenu();
	}

	static void showProducts() {
		clearScreen();
		title("store");
		user.showProducts();
		productsMenu();
	}

	private static void logout(String type) {

		showMsg("logging out..");

		switch(type) {

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
		sleep(2000);
		homePage(true);
	}

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

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				employeeMenu();
				break;
			}
		}
	}

	private static void ordersMenu() {

		while(true) {

			clearScreen();
			employee.readOrders();
			employee.showOrders();
			menu("ordersMenu");

			switch(input.nextLine()) {

			case "0":
				logout("employee");
				break;

			case "1":
				sleep(1000);
				System.out.print("\nWhat number do you want to ship? ");
				String select = input.nextLine();
				sleep(2000);
				employee.shipProduct(Integer.parseInt(select));
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
				String quantity =  input.nextLine();
				employee.restock(Integer.parseInt(select), Integer.parseInt(quantity));
				sleep(4000);
				clearScreen();
				if(employee.showNotification())
					notificationMenu();
				employeeMenu();
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
				adminSubMenu_user();
				break;

			case "2":
				sleep(2000);
				adminSubMenu_product();
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


	static void adminSubMenu_user() {

		while(true) {

			clearScreen();
			title("adminArea");
			menu("adminManageUsersMenu");

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

			case "9":
				sleep(2000);
				adminMenu();
				break;

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				adminSubMenu_user();
				break;
			}
		}
	}


	static void adminSubMenu_product() {

		while(true) {

			clearScreen();
			title("adminArea");
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

			case "9":
				sleep(2000);
				adminMenu();
				break;

			case "":
				break;

			default:
				showMsg("UNAVAILABLE");
				adminSubMenu_product();
				break;
			}
		}
	}


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
				login("admin");

			case "4":
				run = false;
				sleep(1000);
				login("employee");
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


	public static void main(String[] args) {
		homePage(true);
	}

}
