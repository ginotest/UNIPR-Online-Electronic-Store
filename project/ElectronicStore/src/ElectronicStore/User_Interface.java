package ElectronicStore;

import java.util.Scanner;

/**
 * 
 * manages the interface of the software
 *
 */
public class User_Interface {

	/**
	 * reads input from console
	 */
	static Scanner input = new Scanner(System.in);
	
	/**
	 * emulates the linux 'cls' command.
	 * clears the screen for further functionalities
	 */
	protected static void clearScreen() {  
		for (int i = 0; i < 100; ++i) System.out.println();
	}  

	/**
	 * manages the title of each menu(interface)
	 * @param type indicates the title to be shown
	 */
	protected static void title(final String type) {  

		switch(type) {

		case "login":
			System.out.println("\t  _      ____   _____ _____ _   _ ");
			System.out.println("\t | |    / __ \\ / ____|_   _| \\ | |");
			System.out.println("\t | |   | |  | | |  __  | | |  \\| |");
			System.out.println("\t | |   | |  | | | |_ | | | | . ` |");
			System.out.println("\t | |___| |__| | |__| |_| |_| |\\  |");
			System.out.println("\t |______\\____/ \\_____|_____|_| \\_|\n");
			break;

		case "store":
			System.out.println("\t      _____ _                 ");
			System.out.println("\t     / ____| |                ");
			System.out.println("\t    | (___ | |_ ___  _ __ ___ ");
			System.out.println("\t     \\___ \\| __/ _ \\| '__/ _ \\");
			System.out.println("\t     ____) | || (_) | | |  __/");
			System.out.println("\t    |_____/ \\__\\___/|_|  \\___|\n");
			break;

		case "adminArea":
			System.out.println("              _           _                                 ");
			System.out.println("     /\\      | |         (_)           /\\                   ");
			System.out.println("    /  \\   __| |_ __ ___  _ _ __      /  \\   _ __ ___  __ _ ");
			System.out.println("   / /\\ \\ / _` | '_ ` _ \\| | '_ \\    / /\\ \\ | '__/ _ \\/ _` |");
			System.out.println("  / ____ | (_| | | | | | | | | | |  / ____ \\| | |  __| (_| |");
			System.out.println(" /_/    \\_\\__,_|_| |_| |_|_|_| |_| /_/    \\_|_|  \\___|\\__,_|\n");
			break;

		case "cart":
			System.out.println("\t\t\t   _____          _____ _______ ");
			System.out.println("\t\t\t  / ____|   /\\   |  __ \\__   __|");
			System.out.println("\t\t\t | |       /  \\  | |__) | | |   ");
			System.out.println("\t\t\t | |      / /\\ \\ |  _  /  | |   ");
			System.out.println("\t\t\t | |____ / ____ \\| | \\ \\  | |   ");
			System.out.println("\t\t\t  \\_____/_/    \\_\\_|  \\_\\ |_|   \n");
			break;
		}

	}  

	/**
	 * manages the menu(possibilities) of each software functions
	 * @param type indicates the title to be shown
	 */
	protected static void menu(final String type) {

		switch(type) {

		case "loginMenu":
			System.out.println("\t\t~{{USER}}~");
			System.out.println("-----------------\t--------------------");
			System.out.println("| Type 1: LOGIN |\t| Type 2: REGISTER |");
			System.out.println("-----------------\t--------------------\n\n");
			System.out.println("-----------------\t-------------------------------");
			System.out.println("| Type 0: EXIT  |\t| Type 9: Return to home page |");
			System.out.println("-----------------\t-------------------------------\n");
			System.out.println("\n  ~{{ADMIN}}~\t\t ~{{EMPLOYEE}}~");
			System.out.println("-----------------\t-----------------");
			System.out.println("| Type 3: LOGIN |\t| Type 4: LOGIN |");
			System.out.println("-----------------\t-----------------");
			System.out.print("\nWhat do you want to do?  ");
			break;

		case "adminMenu":
			System.out.println("-------------------------------\t\t--------------------------------");
			System.out.println("| Type 1: Manage Employees    |\t\t| Type 2: Manage Products Type |");
			System.out.println("-------------------------------\t\t--------------------------------\n");
			System.out.println("-------------------------------\t\t--------------------------------");
			System.out.println("| Type 9: RETURN TO MAIN MENU |\t\t| Type 0: LOGOUT               |");
			System.out.println("-------------------------------\t\t--------------------------------");
			System.out.print("\nWhat do you want to do?  ");
			break;

		case "adminManageEmployeeMenu":
			System.out.println("------------------------  -------------------------  ---------------------------");
			System.out.println("| Type 1: Add employee |  | Type 2: Edit employee |  | Type 3: Delete employee |");
			System.out.println("------------------------  -------------------------  ---------------------------");
			System.out.println("-----------------------------\t------------------------------------\t-------------------");
			System.out.println("| Type 4: Show all Employee |\t| Type 9: RETURN TO PREVIOUS MENU  |\t| Type 0: LOGOUT  |");
			System.out.println("-----------------------------\t------------------------------------\t-------------------\n");
			System.out.print("\nWhat do you want to do?  ");
			break;

		case "adminManageProductsTypeMenu":
			System.out.println("----------------------------\t-------------------------------    -----------------------------");
			System.out.println("| Type 1: Add Product Type |\t| Type 2: Remove Product Type |    | Type 3: Show all Products |");
			System.out.println("----------------------------\t-------------------------------    -----------------------------");
			System.out.println("\t\t------------------------------------\t-------------------");
			System.out.println("\t\t| Type 9: RETURN TO PREVIOUS MENU  |\t| Type 0: LOGOUT  |");
			System.out.println("\t\t------------------------------------\t-------------------\n");
			System.out.print("\nWhat do you want to do?  ");
			break;

		case "userMenu":
			System.out.println("------------------------\t---------------------------");
			System.out.println("| Type 1: Shop Products|\t| Type 0:  LOGOUT         |");
			System.out.println("------------------------\t---------------------------\n");
			System.out.println("------------------------\t---------------------------");
			System.out.println("| Type 7: Go TO CART   |\t| Type 8:  Manage Profile |");
			System.out.println("------------------------\t---------------------------\n");
			System.out.print("\nWhat do you want to do?  ");
			break;

		case "productsMenu":
			System.out.println("---------------------------------\t-----------------------------");
			System.out.println("| Type 1: Add Product to Cart   |\t| Type 2: Show all Products |");
			System.out.println("---------------------------------\t-----------------------------\n");
			System.out.println("---------------------------------\t-----------------------------");
			System.out.println("| Type 3: Search/Filter Product |\t| Type 7: GO TO CART        |");
			System.out.println("---------------------------------\t-----------------------------\n");
			System.out.println("---------------------------------\t-----------------------------");
			System.out.println("| Type 9: RETURN TO MAIN MENU   |\t| Type 0: LOGOUT            |");
			System.out.println("---------------------------------\t-----------------------------");
			System.out.print("\nWhat do you want to do?  ");
			break;

		case "searchProductMenu":
			System.out.print("              SEARCH/FILTER PRODUCTS           \n\n");
			System.out.println("-------------------------------\t\t--------------------------------");
			System.out.println("| Type 1: by NAME             |\t\t|   Type 2: by MANUFACTURER    |");
			System.out.println("-------------------------------\t\t--------------------------------\n");
			System.out.println("-------------------------------\t\t--------------------------------");
			System.out.println("| Type 3: by HIGHEST PRICE    |\t\t|   Type 4: by LOWEST PRICE    |");
			System.out.println("-------------------------------\t\t--------------------------------\n");
			System.out.println("-------------------------------\t\t--------------------------------");
			System.out.println("| Type 5: SORT ascending      |\t\t|   Type 6: SORT descending    |");
			System.out.println("-------------------------------\t\t--------------------------------\n");
			System.out.println("-----------------------------------\t--------------------------------");
			System.out.println("| Type 9: RETURN TO PREVIOUS MENU |\t|    Type 0: EXIT/LOGOUT       |");
			System.out.println("-----------------------------------\t--------------------------------");
			System.out.print("\nEnter multiple choice separated by space\n");
			System.out.print("\nWhat do you want to do?  ");
			break;

		case "manageProfileMenu":
			System.out.println("------------------------------\t\t        --------------------------------");
			System.out.println("| Type 1: change USERNAME    |\t\t        | Type 2: change PASSWORD      |");
			System.out.println("------------------------------\t\t        --------------------------------");
			System.out.println("------------------------------\t\t        --------------------------------");
			System.out.println("| Type 3: set ADDRESS        |\t\t        | Type 8: DELETE ACCOUNT       |");
			System.out.println("------------------------------\t\t        --------------------------------");
			System.out.println("-----------------------------------\t\t--------------------");
			System.out.println("| Type 9: RETURN TO PREVIOUS MENU |\t\t| Type 0: LOGOUT   |");
			System.out.println("-----------------------------------\t\t--------------------");
			System.out.print("\nWhat do you want to do?  ");
			break;

		case "cartMenu":
			System.out.println("------------------------------\t\t        ---------------------------");
			System.out.println("| Type 1: Remove product     |\t\t        | Type 2: Change quantity |");
			System.out.println("------------------------------\t\t        ---------------------------");
			System.out.println("------------------------------\t\t        ---------------------------");
			System.out.println("| Type 3: Shop more Products |\t\t        | Type 5: Place Order     |");
			System.out.println("------------------------------\t\t        ---------------------------");
			System.out.println("-----------------------------------\t\t---------------------------");
			System.out.println("| Type 9: RETURN TO PREVIOUS MENU |\t\t| Type 0: LOGOUT          |");
			System.out.println("-----------------------------------\t\t---------------------------");
			System.out.print("\nWhat do you want to do?  ");
			break;

		case "employeeMenu":
			System.out.println("----------------------------\t-----------------------------");
			System.out.println("| Type 1: Orders to Ship   |\t|   Type 2: Notifications   |");
			System.out.println("----------------------------\t-----------------------------\n");
			System.out.println("----------------------------\t\t--------------------");
			System.out.println("| Type 9: Return to home   |\t\t| Type 0: LOGOUT   |");
			System.out.println("----------------------------\t\t--------------------");
			System.out.print("\nWhat do you want to do?  ");
			break;

		case "notificationMenu":
			System.out.println("               ----------------------------");
			System.out.println("               | Type 1: Restock Product  |");
			System.out.println("               ----------------------------\n");
			System.out.println("---------------------------\t----------------------");
			System.out.println("| Type 9: RETURN TO HOME  |\t|   Type 0: LOGOUT   |");
			System.out.println("---------------------------\t----------------------");
			System.out.print("\nWhat do you want to do?  ");
			break;

		case "ordersMenu":
			System.out.println("--------------------------\t-----------------------------------");
			System.out.println("| Type 1: Ship Product/s |\t| Type 9: RETURN TO PREVIOUS MENU |");
			System.out.println("--------------------------\t-----------------------------------\n");
			System.out.println("                      ------------------        ");
			System.out.println("                      | Type 0: LOGOUT |        ");
			System.out.println("                      ------------------        ");
			System.out.print("\nWhat do you want to do?  ");	
			break;

		case "homePageMenu":
			System.out.println("-------------------------------\t-------------------");
			System.out.println("|   Type 1: search Products   |\t|  Type 2: LOGIN  |");
			System.out.println("-------------------------------\t-------------------");
			System.out.println("-------------------------------\t-------------------");
			System.out.println("|   Type 3: Show All Products |\t|  Type 0: EXIT   |");
			System.out.println("-------------------------------\t-------------------");
			System.out.print("\nWhat do you want to do?  ");	
			break;
		}

	}
}