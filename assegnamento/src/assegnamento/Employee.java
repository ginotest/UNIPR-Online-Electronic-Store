package assegnamento;

import java.util.ArrayList;

public class Employee extends ManageData {

	private static ArrayList<String> employee;
	protected static ArrayList<ArrayList<String>> orders;
	protected static ArrayList<ArrayList<String>> productsToRestock;

	public boolean login(String username, String password) {
		employee = getProfile("employee", username);
		if(employee.isEmpty())
			return false;
		else if(!employee.isEmpty() && ((employee.get(1) != null) && (employee.get(1).equals(password))))
			return true;
		else
			employee.clear();
		return false;
	}

	public void readOrders() {
		orders = readAll("delivery");
	}

	public void showOrders() {
		orders = readAll("delivery");
		if(orders.size() == 0) {
			System.out.print("\nNo Orders made yet.\n");
			return;
		}

		String[] array;
		ArrayList<ArrayList<String>> products = readAll("product");

		for (int i = 0; i < orders.size(); i++) {
			System.out.print("\n" + (i+1) + ") ORDER " + orders.get(i).get(0) + "\n");

			array = orders.get(i).get(3).split(",");
			System.out.format("%-25s%-25s%-25s\n","\tNAME", "MANUFACTURER", "QUANTITY");

			for (int j = 0; j < array.length; j++) {
				for (int k = 0; k < products.size(); k++) {
					if(products.get(k).get(0).equals(array[j])) {
						System.out.print("\t");
						for (int l = 1; l < products.get(k).size()-2; l++)
							System.out.format("%-25s",products.get(k).get(l));
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

	public boolean showNotification() {
		productsToRestock = readAll("restock");
		if(productsToRestock.size() == 0) {
			System.out.print("\nNo Notification\n");
			return false;
		}

		System.out.format("%-25s%-25s%-25s\n","\tNAME", "MANUFACTURER");
		for (int i = 0; i < productsToRestock.size(); i++) {
			System.out.print("\n" + (i+1) + ")  ");
			System.out.format("%-25s",productsToRestock.get(i).get(1));
			System.out.format("%-25s",productsToRestock.get(i).get(2));
			System.out.println();
		}
		System.out.println();
		return true;
	}

	public void restock(int select, int quantity) {
		/*if(editData("product", 4, productsToRestock.get(select), Integer.toString(quantity)))
			System.out.println("Product successfully restocked.");
		else 
			System.out.println("Error in restocking Product.");*/
	}
}
