package application;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;

/**
 * Admin class
 */
public class Admin extends ManageData{

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
	
	String getUsername() {
		return admin.get(0);
	}

	/**
	 * @return true if admin is logged in
	 */
	boolean session() {
		
		if(admin.isEmpty())
			return false;
		
		return true;
		
	}

	/**
	 * read products info from database
	 */
	void readAllProducts() {
		
		readAll("product");
		
	}

	/**
	 * adds a new product
	 * @param id product's ID
	 * @param product product's name
	 * @param manufacturer product's manufacturer
	 * @param price product's price
	 */
	void addProduct(String id, String product, String manufacturer, String price) {

		String [] fields = {id, product, manufacturer, price, "0"};
		addData("product", fields);
		addData("restock", fields);
		
	}
	
	/**
	 * adds a new employee
	 * @param name employee's name
	 * @param surname employee's surname
	 * @param username employee's username
	 * @param password employee's password
	 * @param admin Adminship (true or false)
	 */
	void addEmployee(String name, String surname, String username, String password, String admin) {

		String [] fields = {name, surname, username, password, admin.toLowerCase()};
		addData("employee", fields);
		
	}

	/**
	 * prints products according to admin request
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
				
				if(i == 4 || i == 0) {
					
					final TableColumn<ObservableList<String>, Integer> column = new TableColumn<>(col[i]);
					column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(Integer.parseInt(param.getValue().get(curCol))));
					tableView.getColumns().add(column);
					
				}
				else if(i == 3) {

					final TableColumn<ObservableList<String>, Float> column = new TableColumn<>(col[i]);
					column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(Float.parseFloat(param.getValue().get(curCol))));
					tableView.getColumns().add(column);
					
				}
				else {

					final TableColumn<ObservableList<String>, String> column = new TableColumn<>(col[i]);
					column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol)));
					tableView.getColumns().add(column);

				}
				
			}
		
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return tableView;
		
	}

	private ObservableList<ObservableList<String>> buildData(String[][] dataArray) {
		
		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

		for (String[] row : dataArray)
			data.add(FXCollections.observableArrayList(row));

		return data;
		
	}

	/**
	 * read employees info from database
	 */
	void readAllEmployees() {
		
		readAll("employee");
		
	}

	/**
	 * edits an employee's profile or a product in database
	 * 
	 * @param type type of data to edit
	 * @param idx option index of data in database
	 * @param identifier identifier (username or ID)
	 * @param newValue new value
	 */
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
				final TableColumn<ObservableList<String>, String> column = new TableColumn<>(col[i]);
				column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol)));
				tableView.getColumns().add(column);

			}
		
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return tableView;
		
	}


	/**
	 * removes a product type
	 * @param id ID product to remove
	 */
	void removeProduct(String id) {

		removeData("product", id);
		removeData("restock", id);
		removeProductType(id);

	}

	/**
	 * removes an employee's account
	 * @param username employee's username to remove
	 */
	void removeEmployee(String username) {

		removeData("employee", username);

	}

	/**
	 * clears admin profile logged in
	 */
	public void logout() {
		
		admin = new  ArrayList<String>();
		
	}


}
