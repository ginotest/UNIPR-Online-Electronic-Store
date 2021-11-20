package application;

import java.util.ArrayList;

/**
 * 
 * Employee class
 *
 */
public class Employee extends ManageData {

	private static ArrayList<String> employee;
	/**
	 * arrayList of arrayList that contains the orders to be shipped
	 */
	protected static ArrayList<ArrayList<String>> orders;
	/**
	 * arrayList of arrayList that contains the products to be restocked
	 */
	protected static ArrayList<ArrayList<String>> productsToRestock;

	/**
	 * Employee constructor
	 */
	Employee(){
		employee =  new ArrayList<String>();
	}



	/**
	 * @param username employee's username
	 * @param password employee's password
	 * @return true if employee exists
	 */
	public boolean login(String username, String password) {

		employee = getProfile("employee", username);

		if(employee.isEmpty())
			return false;
		else if(!employee.isEmpty() && ((employee.get(1) != null) && (employee.get(1).equals(password))))
			return true;
		else
			employee =  new ArrayList<String>();

		return false;
	}

	/**
	 * @return true if employee is logged
	 */
	boolean session() {
		if(employee.isEmpty())
			return false;
		return true;
	}

	/**
	 * @return true if employee is also an admin
	 */
	boolean isAdmin() {
		if(!employee.get(2).equals("true"))
			return false;
		return true;
	}

	/**
	 * @return username of an employee
	 */
	String getUsername() {
		return employee.get(0);
	}

	/**
	 * @return password of an employee
	 */
	String getPassword() {
		return employee.get(1);
	}


	/**
	 * reads orders to be delivered from database
	 */
	public void readOrders() {
		orders = readAll("delivery");
	}

	/**
	 * prints out orders to be delivered read from database
	 */
	public void showOrders() {

		orders = readAll("delivery");

		if(orders.size() == 0) {
			System.out.print("\nNo Orders made yet.\n");
			return;
		}

		String[] array;
		ArrayList<ArrayList<String>> products = readAll("product");
		System.out.println("\n\t\t~{{ORDERS TO DELIVER}}~\n");
		for (int i = 0; i < orders.size(); i++) {

			System.out.print("\n" + (i+1) + ") ORDER " + orders.get(i).get(0) + "\n");
			array = orders.get(i).get(3).split(",");
			System.out.format("%-25s%-25s%-25s\n","\t  NAME", "MANUFACTURER", "QUANTITY");

			for (int j = 0; j < array.length; j++) {

				for (int k = 0; k < products.size(); k++) {

					if(products.get(k).get(0).equals(array[j])) {
						System.out.print("\t");
						System.out.print((j+1) + ")  ");

						for (int z = 1; z < products.get(k).size()-2; z++)
							System.out.format("%-25s",products.get(k).get(z));

						break;
					}
				}
				System.out.format("%-25s",array[++j]);
				System.out.println();
			}

			System.out.println("\n------------------------------------------------------------------");
		}
		System.out.println();
	}

	/**
	 * ship products ordered
	 * @param select product to be shipped 
	 */
	public void shipProduct(int select) {

		if((select > 0) && (select <= orders.size())) {

			System.out.println("ORDER " + orders.get(select-1).get(0) + " has been Shipped to '" +
					orders.get(select-1).get(1) + "' to address '" + orders.get(select-1).get(2) + "'");

			removeData("delivery", orders.get(select-1).get(0));
		}
		else {
			System.out.println("ORDER TO BE SHIPPED NOT FOUND.");
		}

	}

	/**
	 * controls and shows notification if exists
	 * @return true if there is any notification
	 */
	public boolean showNotification() {

		productsToRestock = readAll("restock");

		if(productsToRestock.size() == 0) {
			System.out.print("\nNo Notification\n");
			return false;
		}

		System.out.println("\n\t\t~{{PRODUCTS TO RESTOCK}}~\n");

		System.out.format("%-25s%-25s\n","\tNAME", "MANUFACTURER");

		for (int i = 0; i < productsToRestock.size(); i++) {

			System.out.print("\n" + (i+1) + ")  ");
			System.out.format("%-25s",productsToRestock.get(i).get(1));
			System.out.format("%-25s",productsToRestock.get(i).get(2));
			System.out.println();
		}
		System.out.println();
		return true;
	}

	/**
	 * manages the restock of a product
	 * @param select product to restock
	 * @param quantity quantity to restock
	 */
	public void restock(int select, int quantity) {

		if((select > 0) && (select <= productsToRestock.size()))
			if(editData("product", 4, productsToRestock.get(select-1).get(0), Integer.toString(quantity))) {
				removeData("restock", productsToRestock.get(select-1).get(0));
				System.out.println("Product successfully restocked.");
				return;
			}

		System.out.println("Error in restocking Product.");		
	}

	/**
	 * clears employee profile logged in
	 */
	public void logout() {
		employee =  new ArrayList<String>();
	}
}
