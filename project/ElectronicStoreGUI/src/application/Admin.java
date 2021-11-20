package application;

import java.util.Scanner;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * 
 * Admin class
 *
 */
public class Admin extends ManageData{

	/**
	 * reads input from console
	 */
	public Scanner input = new Scanner(System.in);
	private static ArrayList<String> admin;


	/**
	 * admin constructor
	 */
	Admin() {
		admin = new  ArrayList<String>();
	}

	/**
	 * @param username username of admin
	 * @param password password of admin
	 * @return true if the admin profile exists
	 */
	boolean login(String username, String password) {

		admin = getProfile("employee", username);

		if(admin.isEmpty())
			return false;
		else if(!admin.isEmpty() && (admin.get(1).equals(password) && admin.get(2).equals("true")))
			return true;
		else
			admin = new  ArrayList<String>();

		return false;
	}

	/**
	 * @return true if admin is logged in
	 */
	boolean session() {
		if(admin.isEmpty())
			return false;
		return true;
	}


	void readAllProducts() {
		readAll("product");
	}


	/**
	 * adds a new employee or product
	 * 
	 * @param type new data to add
	 */
	void addProduct(String id, String product, String manufacturer, String price, String quantity) {

		String [] fields = {id, product, manufacturer, price, quantity};

		addData("product", fields);
	}

	void addEmployee(String name, String surname, String username, String password, String admin) {

		String [] fields = {name, surname, username, password, admin.toLowerCase()};

		addData("employee", fields);
	}

	/**
	 * adds a new employee or product
	 * 
	 * @param type new data to add
	 */
	void add(String type) {

		int select = 0;
		String [] fields = new String[5];
		String[][] message = {{"Name:", "Surname: ", "Username: ", "Password: ", "Admin: "},{"ID: ", "Name Product: ", "Manufacturer: ", "Price (USD): "}};

		if(type == "employee") {
			select = 0;
		}
		else if(type == "product") {
			select = 1;
		}


		for(int i = 0; i < message[select].length ; i++) {

			System.out.print(message[select][i]);
			fields[i] = input.nextLine();

			if(exist("product", fields[i]) && i == 0 && type == "product") {
				System.out.println("\nA product with this ID already exists\n");
				i--;
			}
		}
		addData(type, fields);
	}

	/**
	 * edits an employee profile or a product in database
	 * 
	 * @param type data to edit
	 */
	void edit(String type){

		int idx = 0;
		String[][] message = {{"Username", "Password", "Name", "Surname", "Adminship (true/false)"},{"ID", "Name Product", "Manufacturer", "Price (USD)", "Quantity"}};

		if(type=="employee") {
			idx=0;
		}
		else if(type=="product") {
			idx=1;
		}

		System.out.print("Type the " + message[idx][0].toUpperCase() + " of the " + type + " you want to edit: ");
		String selectedIdentifier = input.next();
		System.out.println("You can change the following information: ");

		for(int i = 0; i < 5 ; i++)
			System.out.println(i+1 +") " + message[idx][i]);

		System.out.print("What do you want to change?  ");

		int choice;
		try {
			choice = input.nextInt()-1;

		}catch(InputMismatchException e){
			System.out.println("Incorrect choice.");
			return;
		}

		input.nextLine();
		System.out.print("Write the new " + message[idx][choice].toLowerCase() + ": ");
		String content = input.nextLine();

		if(((message[idx][choice].equals(message[0][0])) && !exist("employee", content)) || !message[idx][choice].equals(message[0][0])   ) {
			editData("employee", choice, selectedIdentifier, content);
			System.out.println("\nSuccessfully edited\n");
		}
		else
			System.out.println("Username already exists");
	}
	/**
	 * prints product according to admin request
	 */
	TableView<ObservableList<String>> loadProducts() {
		String[][] dataArray = elements.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		return createTableView(dataArray);
	}


	private TableView<ObservableList<String>> createTableView(String[][] dataArray) {
		TableView<ObservableList<String>> tableView = new TableView<>();
		tableView.setItems(buildData(dataArray));
		String[] col = {"ID", "PRODUCT", "MANUFACTURER", "PRICE", "QUANTITY"};
		if(dataArray.length != 0)
			for (int i = 0; i < dataArray[0].length; i++) {
				final int curCol = i;
				final TableColumn<ObservableList<String>, String> column = new TableColumn<>(
						col[i]
						);
				column.setCellValueFactory(
						param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol))
						);

				tableView.getColumns().add(column);

			}
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return tableView;
	}

	private ObservableList<ObservableList<String>> buildData(String[][] dataArray) {
		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

		for (String[] row : dataArray) {
			data.add(FXCollections.observableArrayList(row));
		}

		return data;
	}


	void readAllEmployees() {
		readAll("employee");
	}

	void edit(String type, int idx, String identifier, String newValue){
		editData(type, idx,  identifier, newValue);

	}

	/**
	 * prints employees according to admin request
	 */
	TableView<ObservableList<String>> loadEmployees() {
		String[][] dataArray = elements.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		return createTableViewEmployees(dataArray);
	}


	private TableView<ObservableList<String>> createTableViewEmployees(String[][] dataArray) {
		TableView<ObservableList<String>> tableView = new TableView<>();
		tableView.setItems(buildData(dataArray));
		String[] col = {"NAME", "SURNAME", "USERNAME", "PASSWORD", "ADMINSHIP"};
		if(dataArray.length != 0)
			for (int i = 0; i < dataArray[0].length-1; i++) {
				final int curCol = i;
				final TableColumn<ObservableList<String>, String> column = new TableColumn<>(
						col[i]
						);
				column.setCellValueFactory(
						param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol))
						);

				tableView.getColumns().add(column);

			}
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return tableView;
	}


	/**
	 * removes employee profile o a product
	 * 
	 * @param type data to remove
	 */
	void remove(String type) {

		int select=0;
		String[] message = {"Write the USERNAME of the user you want to delete: ","Write the ID of the product you want to delete: " };
		String identifier = input.next();

		if(type=="employee")
			select=0;
		else if(type=="product")
			select=1;

		System.out.print(message[select]);
		removeData(type, identifier);

	}
	/**
	 * removes employee profile o a product
	 * 
	 * @param type data to remove
	 */
	void removeProduct(String id) {

		removeData("product", id);

	}

	void removeEmployee(String username) {

		removeData("employee", username);

	}

	/**
	 * clears admin profile logged in
	 */
	public void logout() {
		admin = new  ArrayList<String>();
	}

	/**
	 * @param string data type to be read
	 */
	public void readData(String string) {
		readAll(string);
		System.out.println("\n");
		if(string == "employee") {
			System.out.format("%-25s%-25s%-25s%-25s%-25s","NAME", "SURNAME", "USERNAME", "PASSWORD", "ADMINSHIP");
			System.out.println("\n-------------------------------------------------------------------------------------");
		}
		else {
			System.out.format("%-25s%-25s%-25s%-25s%-25s","ID", "NAME", "MANUFACTURER", "PRICE", "QUANTITY");
			System.out.println("\n------------------------------------------------------------------------------------------------------------------");
		}

		if(elements.size() == 0) {
			System.out.print("\nNo Result\n");
			return;
		}

		for (int i = 0; i < elements.size(); i++) {

			System.out.print("\n" + (i+1) + ")  ");

			for (int j = 0; j < elements.get(i).size(); j++) {
				System.out.format("%-25s",elements.get(i).get(j));	
			}
			System.out.println();
		}
		System.out.println();	
	}

}
