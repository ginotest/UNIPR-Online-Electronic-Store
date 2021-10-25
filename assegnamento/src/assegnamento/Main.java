package assegnamento;

public class Main extends User_Interface{
	static Admin admin = new Admin();
	static User user = new User();
	static User userLogged;


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



	static void adminMenu() {
		boolean run = true;
		while(run==true) {
			clearScreen();
			title(2);
			menu(2);
			switch(input.nextInt()) {

			case 0:
				run = false;
				closeStore();
				break;

			case 1:
				adminSubMenu_user();
				break;

			case 2:
				adminSubMenu_product();
				break;

			case 9:
				mainMenu();
				break;

			default:
				break;
			}
		}
	}



	static void adminSubMenu_user() {

		boolean run = true;

		while(run==true) {
			clearScreen();
			title(2);
			menu(3);

			switch(input.nextInt()) {

			case 1:
				admin.add("employee");
				break;

			case 2:
				admin.edit("employee");
				break;

			case 3:
				admin.remove("employee");
				break;

			default:
				break;
			}
		}
	}



	static void adminSubMenu_product() {

		boolean run = true;

		while(run==true) {
			clearScreen();
			title(2);
			menu(4);

			switch(input.nextInt()) {

			case 1:
				admin.add("product");
				break;

			case 2:
				admin.remove("product");
				break;
			default:
				break;
			}
		}
	}



	static void login(String who) {

		String username= "", password = "";
		clearScreen();
		title(0);

		switch(who.toLowerCase()) {

		case "user":
			System.out.print("\n username: ");
			username = input.next();
			System.out.print("\n password: ");
			password = input.next();
			if(user.Login(username, password)) {
				System.out.println("\nRedirecting...");
				sleep(2000);
				userMenu();
			}
			else {
				System.out.println("Incorrect Details!");
				sleep(4000);
				mainMenu();
			}
			break;

		case "employee":
			break;

		case "admin":
			System.out.print("\n username: ");
			username = input.next();
			System.out.print("\n password: ");
			password = input.next();
			if(admin.login(username, password)) {
				System.out.println("\nRedirecting...");
				sleep(2000);
				adminMenu();
			}
			else {
				System.out.println("Incorrect Details!");
				sleep(4000);
				mainMenu();
			}
			break;
		}
	}




	static void userMenu() {
		boolean run = true;
		while(run==true) {
			clearScreen();
			title(1);
			menu(5);
			switch(input.nextInt()) {

			case 0:
				run = false;
				closeStore();
				break;

			case 1:
				clearScreen();
				title(1);
				user.showProducts();
				productsMenu();
				break;

			case 2:
				searchProductMenu();
				break;

			case 8:
				profileMenu();
				break;

			case 9:
				mainMenu();
				break;

			default:
				break;
			}
		}
	}

	static void productsMenu() {
		boolean run = true;
		while(run==true) {
			menu(6);
			switch(input.nextInt()) {

			case 0:
				run = false;
				closeStore();
				break;

			case 1:

				break;

			case 2:

				break;

			case 9:
				userMenu();
				break;

			default:
				break;
			}
		}
	}


	static void searchProductMenu() {
		boolean run = true;
		while(run==true) {
			clearScreen();
			title(1);
			menu(7);
			switch(input.nextInt()) {

			case 0:
				run = false;
				closeStore();
				break;

			case 1:
				break;

			case 2:

				break;

			case 9:
				userMenu();
				break;

			default:
				break;
			}
		}
	}

	static void profileMenu(){
		boolean run = true;
		while(run==true) {
			clearScreen();
			title(1);
			menu(8);
			switch(input.nextInt()) {

			case 0:
				run = false;
				closeStore();
				break;

			case 1:
				System.out.print("\n Enter new username: ");
				String newUsername = input.next();
				user.changeUsername(newUsername);
				System.out.println("\nRedirecting...");
				sleep(2000);
				userMenu();
				break;
			case 2:
				while(true) {
					System.out.print("\n Current password: ");
					String oldPassword = input.next();
					System.out.print("\n New password: ");
					String newPassword = input.next();
					System.out.print("\n New password, again: ");
					String newPassword2 = input.next();
					if (user.changePassword(oldPassword, newPassword, newPassword2))
						break;
				}
				System.out.println("\nRedirecting...");
				sleep(2000);
				userMenu();
				break;

			case 8:
				//hello
				System.out.println("Password: ");
				String password = input.next();
				if(user.Login(user.getUsername(), password)) {
					System.out.print("\n Are you sure you want to delete this account? (Y/N): ");
					String decision = input.next();
					if((decision.equalsIgnoreCase("y"))) {
						user.removeUser();
						sleep(3000);
						mainMenu();
					}else {
						sleep(1000);
						profileMenu();
					}}
				else {
					System.out.println("Incorrect!");
					sleep(1000);
					profileMenu();
				}
				break;
				
			case 9:
				userMenu();
				break;

			default:
				break;
			}
		}
	}	


	static void mainMenu() {

		boolean run = true;

		while(run==true) {
			clearScreen();
			title(1);
			menu(1);

			switch(input.nextInt()) {

			case 0:
				closeStore();
				break;

			case 1:
				run = false;
				login("user");
				break;

			case 2:
				user.register();
				sleep(3000);
				userMenu();
				break;

			case 3:
				run = false;
				login("admin");

			case 4:
				break;

			default:
				break;
			}
		}
	}




	public static void main(String[] args) {
		// TODO Auto-generated method stub
		mainMenu();
	}

}
