package assegnamento;

import java.util.Scanner;

public class User_Interface {
	
	static Scanner input = new Scanner(System.in);
	
	protected static void clearScreen() {  
		for (int i = 0; i < 100; ++i) System.out.println();
	}  
	
	protected static void title(final int type) {  
		switch(type) {
		case 0:
			System.out.println("\t  _      ____   _____ _____ _   _ ");
			System.out.println("\t | |    / __ \\ / ____|_   _| \\ | |");
			System.out.println("\t | |   | |  | | |  __  | | |  \\| |");
			System.out.println("\t | |   | |  | | | |_ | | | | . ` |");
			System.out.println("\t | |___| |__| | |__| |_| |_| |\\  |");
			System.out.println("\t |______\\____/ \\_____|_____|_| \\_|\n");
			break;
		case 1:
			System.out.println("\t      _____ _                 ");
			System.out.println("\t     / ____| |                ");
			System.out.println("\t    | (___ | |_ ___  _ __ ___ ");
			System.out.println("\t     \\___ \\| __/ _ \\| '__/ _ \\");
			System.out.println("\t     ____) | || (_) | | |  __/");
			System.out.println("\t    |_____/ \\__\\___/|_|  \\___|\n");
			break;
		case 2:
			System.out.println("              _           _                                 ");
			System.out.println("     /\\      | |         (_)           /\\                   ");
			System.out.println("    /  \\   __| |_ __ ___  _ _ __      /  \\   _ __ ___  __ _ ");
			System.out.println("   / /\\ \\ / _` | '_ ` _ \\| | '_ \\    / /\\ \\ | '__/ _ \\/ _` |");
			System.out.println("  / ____ | (_| | | | | | | | | | |  / ____ \\| | |  __| (_| |");
			System.out.println(" /_/    \\_\\__,_|_| |_| |_|_|_| |_| /_/    \\_|_|  \\___|\\__,_|\n");
			break;
		}
		
	}  
	protected static void menu(final int type) {

		switch(type) {
		case 1:
			System.out.println("\t\t    ~{{USER}}~");
			System.out.println("-----------------\t\t-------------------");
			System.out.println("| Type 1: LOGIN |\t\t| Type 2: REGISTER |");
			System.out.println("-----------------\t\t-------------------");
			System.out.println("\n                 ----------------        ");
			System.out.println("                 | Type 0: EXIT |        ");
			System.out.println("                 ----------------        ");
			System.out.println("\n~{{ADMIN}}~\t\t\t~{{EMPLOYEE}}~");
			System.out.println("-----------------\t\t----------------");
			System.out.println("| Type 3: LOGIN |\t\t| Type 4: LOGIN |");
			System.out.println("-----------------\t\t----------------");
			System.out.print("\nWhat do you want to do?  ");
			break;
		case 2:
			System.out.println("------------------------------\t\t--------------------------------");
			System.out.println("| Type 1: Manage Users        |\t\t| Type 2: Manage Products Type |");
			System.out.println("------------------------------\t\t--------------------------------\n");
			System.out.println("------------------------------\t\t--------------------------------");
			System.out.println("| Type 9: RETURN TO MAIN MENU |\t\t| Type 0: EXIT                 |");
			System.out.println("------------------------------\t\t--------------------------------");
			System.out.print("\nWhat do you want to do?  ");
			break;
		case 3:
			System.out.println("--------------------\t---------------------\t-----------------------");
			System.out.println("| Type 1: Add User |\t| Type 2: Edit User |\t| Type 3: Delete User |");
			System.out.println("--------------------\t---------------------\t-----------------------");
			System.out.print("\nWhat do you want to do?  ");
			break;
		case 4:
			System.out.println("----------------------------\t-------------------------------");
			System.out.println("| Type 1: Add Product Type |\t| Type 2: Remove Product Type |");
			System.out.println("----------------------------\t-------------------------------");
			System.out.print("\nWhat do you want to do?  ");
			break;
			
		case 5:
			System.out.println("                  -----------------------------");
			System.out.println("                 |   Type 1: Shop Products     |");
			System.out.println("                  -----------------------------\n");
			System.out.println("------------------------------\t\t--------------------------------");
			System.out.println("| Type 7: Go TO CART          |\t\t| Type 8:  Manage Profile      |");
			System.out.println("------------------------------\t\t--------------------------------\n");
			System.out.println("------------------------------\t\t--------------------------------");
			System.out.println("| Type 9: RETURN TO MAIN MENU |\t\t| Type 0: EXIT                 |");
			System.out.println("------------------------------\t\t--------------------------------");
			System.out.print("\nWhat do you want to do?  ");
			break;
			
		case 6:
			System.out.println("                  -----------------------------------");
			System.out.println("                 |   Type 1: Add Product to Cart     |");
			System.out.println("                  -----------------------------------\n");
			System.out.println("------------------------------\t\t------------------------");
			System.out.println("| Type 2: Search Product     |\t\t| Type 7: GO TO CART    |");
			System.out.println("------------------------------\t\t------------------------\n");
			System.out.println("----------------------------------\t\t--------------------------------");
			System.out.println("| Type 9: RETURN TO PREVIOUS MENU |\t\t| Type 0: EXIT                 |");
			System.out.println("----------------------------------\t\t--------------------------------");
			System.out.print("\nWhat do you want to do?  ");
			break;
			
		case 7:
			System.out.print("\n               SEARCH PRODUCTS           \n\n");
			System.out.println("------------------------------\t\t--------------------------------");
			System.out.println("| Type 1: by NAME             |\t\t| Type 2: by MANUFACTURER      |");
			System.out.println("------------------------------\t\t--------------------------------\n");
			System.out.println("------------------------------\t\t--------------------------------");
			System.out.println("| Type 3: by HIGHEST PRICE    |\t\t| Type 4: by LOWEST PRICE     |");
			System.out.println("------------------------------\t\t--------------------------------\n");
			System.out.println("----------------------------------\t\t--------------------------------");
			System.out.println("| Type 9: RETURN TO PREVIOUS MENU |\t\t| Type 0: EXIT                 |");
			System.out.println("----------------------------------\t\t--------------------------------");
			System.out.print("\nEnter multiple choice separated by space\n");
			System.out.print("\nWhat do you want to do?  ");
			break;
		
		case 8:
			System.out.println("------------------------------\t\t--------------------------------");
			System.out.println("| Type 1: change USERNAME     |\t\t| Type 2: change PASSWORD      |");
			System.out.println("------------------------------\t\t--------------------------------");
			System.out.println("                 --------------------------        ");
			System.out.println("                 | Type 8: DELETE ACCOUNT |        ");
			System.out.println("                 --------------------------        ");
			System.out.println("----------------------------------\t\t--------------------------------");
			System.out.println("| Type 9: RETURN TO PREVIOUS MENU |\t\t| Type 0: EXIT                 |");
			System.out.println("----------------------------------\t\t--------------------------------");
			System.out.print("\nWhat do you want to do?  ");
			break;
		}
		
	}
}