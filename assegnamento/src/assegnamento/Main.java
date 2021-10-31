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
	
	static void unavailable() {
		System.out.println("\nUNAVAILABLE!");
		sleep(3000);
	}
	
	static void goToLogin(String msg) {
		sleep(1000);
		System.out.println(msg);
		sleep(4000);
		mainMenu();
	}
	
	static void login(String who) {

		String username= "", password = "";
		clearScreen();
		title(0);
		
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
		boolean run = true;
		while(run) {
			clearScreen();
			title(1);
			menu(10);
			switch(input.nextLine()) {

			case "0":
				closeStore();
				break;

			case "1":
				sleep(2000);
				break;

			case "2":
				sleep(1500);
				notificationMenu();
				break;

			case "9":
				sleep(2000);
				mainMenu();
				break;

			default:
				unavailable();
				mainMenu();
				break;
			}
		}
	}

	private static void notificationMenu() {
		boolean run = true;
		while(run) {
			clearScreen();
			title(1);
			menu(11);
			switch(input.nextLine()) {

			case "0":
				closeStore();
				break;

			case "1":
				sleep(2000);
				break;

			case "9":
				sleep(2000);
				mainMenu();
				break;

			default:
				unavailable();
				employeeMenu();
				break;
			}
		}
	}

	
	static void adminMenu() {
		boolean run = true;
		while(run) {
			clearScreen();
			title(2);
			menu(2);
			switch(input.nextLine()) {

			case "0":
				run = false;
				closeStore();
				break;

			case "1":
				adminSubMenu_user();
				break;

			case "2":
				adminSubMenu_product();
				break;

			case "9":
				mainMenu();
				break;

			default:
				unavailable();
				adminMenu();
				break;
			}
		}
	}


	static void adminSubMenu_user() {

		boolean run = true;

		while(run) {
			clearScreen();
			title(2);
			menu(3);

			switch(input.nextLine()) {

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

			default:
				unavailable();
				adminSubMenu_user();
				break;
			}
		}
	}


	static void adminSubMenu_product() {

		boolean run = true;

		while(run) {
			clearScreen();
			title(2);
			menu(4);

			switch(input.nextLine()) {

			case "1":
				admin.add("product");
				sleep(2000);
				break;

			case "2":
				admin.remove("product");
				sleep(2500);
				break;
			default:
				unavailable();
				adminSubMenu_product();
				break;
			}
		}
	}

	
	static void showProducts() {
		clearScreen();
		title(1);
		user.showProducts();
		productsMenu();
	}

	static void userMenu() {
		boolean run = true;
		while(run) {
			clearScreen();
			title(1);
			menu(5);
			switch(input.nextLine()) {

			case "0":
				closeStore();
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

			case "9":
				sleep(2000);
				mainMenu();
				break;

			default:
				unavailable();
				userMenu();
				break;
			}
		}
	}

	static void productsMenu() {
		boolean run = true;
		while(run) {
			menu(6);
			switch(input.nextLine()) {

			case "0":
				closeStore();
				break;

			case "1":
				user.addToCart();
				sleep(2000);
				clearScreen();
				showProducts();
				userMenu();
				sleep(2000);
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

			default:
				unavailable();
				productsMenu();
				break;
			}
		}
	}
	
	
	static void searchProductMenu_sub(String choice) {
		String str;
		switch(choice) {
		
		case "0":
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
			sleep(2000);
			showProducts();
			break;

		default:
			unavailable();
			searchProductMenu();
			break;
		}
	}


	static void searchProductMenu() {
		String[] array;
		boolean run = true;
		while(run) {
			clearScreen();
			title(1);
			menu(7);
			
			String choices = input.nextLine().trim();
			if(!(choices.length() == 0)) {
				array = choices.split(" ");
				
				user.readProducts();
				for(String choice: array) {
					if (choice.trim().length() == 1)
						searchProductMenu_sub(choice);
				}
				sleep(2000);
				showProducts();
			}
			else
				searchProductMenu();
		}
	}

	
	static void profileMenu(){
		boolean run = true;
		while(run) {
			clearScreen();
			title(1);
			menu(8);
			switch(input.nextLine()) {

			case "0":
				closeStore();
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
					if(newPassword == newPassword2) {
						user.changePassword(oldPassword, newPassword);
					}
					else {
						System.out.println("Passwords do not match!");
					}
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
						mainMenu();
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

			default:
				unavailable();
				profileMenu();
				break;
			}
		}
	}	
	
	
	static void cartMenu() {
		boolean run = true;
		while(run) {
			clearScreen();
			title(3);
			user.cart();
			menu(9);
			switch(input.nextLine()) {

			case "0":
				closeStore();
				break;

			case "1":
				user.removeProduct();
				cartMenu();
				sleep(2000);
				break;

			case "2":
				sleep(1000);
				user.changeQuantity();
				cartMenu();
				sleep(2000);
				break;
				
			case "5":
				sleep(2000);
				user.order();
				sleep(4000);
				break;

			case "9":
				user.readProducts();
				sleep(2000);
				showProducts();
				break;

			default:
				unavailable();
				cartMenu();
				break;
			}
		}
	}


	static void mainMenu() {

		boolean run = true;

		while(run) {
			clearScreen();
			title(1);
			menu(1);

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

			default:
				unavailable();
				mainMenu();
				break;
			}
		}
	}




	public static void main(String[] args) {
		mainMenu();
	}

}
