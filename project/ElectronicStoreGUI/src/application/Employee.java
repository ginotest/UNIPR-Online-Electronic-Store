package application;

import java.util.ArrayList;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


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
		
		if(!employee.isEmpty() && !employee.get(2).equals("true"))
			return false;

		return true;

	}
	
	/**
	 * sets the adminship of the employee to false
	 */
	void setAdminshipFalse() {
		employee.set(2, "false");
	}

	/**
	 * @return username of an employee
	 */
	String getUsername() {
		if(employee.isEmpty())
			return "";
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
	 * @return number of orders to be delivered
	 */
	public int getNumberOrders() {

		return readAll("delivery").size();

	}

	/**
	 * @return number of products to restock
	 */
	public int getNumberNotifications() {

		return readAll("restock").size();

	}
	
	/**
	 * @return number of products in the warehouse
	 */
	public int getNumberProducts() {
		return readAll("product").size();
	}
	
	/**
	 * @return number of employees
	 */
	public int getNumberEmployees() {

		return readAll("employee").size();

	}

	/**
	 * @return orders to be delivered from database
	 */
	ArrayList<ArrayList<String>> getOrders() {

		return orders;

	}

	/**
	 * @return info products from database
	 */
	ArrayList<ArrayList<String>> readProducts() {

		return readAll("product");

	}
	
	/**
	 * displays a certain order
	 * @param index product index in array
	 */
	public void displayOrder(int index) {
		String[] array;
		
		array = orders.get(index).get(3).split(",");
		
		ArrayList<ArrayList<String>> productsOrdered = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> products = readProducts();
		ArrayList<String> temp;   

		for (int j = 0; j < array.length; j++) {

			for (int k = 0; k < products.size(); k++) {

				if(products.get(k).get(0).equals(array[j])) {

					temp = new ArrayList<String>();
					temp.add(products.get(k).get(1));
					temp.add(products.get(k).get(2));
					temp.add(array[++j]);
					productsOrdered.add(temp);

				}
			}
		}
		
		String[][] dataArray = productsOrdered.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		String[] col = {"PRODUCT", "MANUFACTURER", "QUANTITY"};
		
		HBox hbox = new HBox();
		
		Button ship = new Button("Ship");
		ship.setId("btnBottom");
		
		Button cancel = new Button("cancel");
		cancel.setId("btnBottom");
		
		hbox.getChildren().addAll(ship, cancel);
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(20);
		
		VBox vbox = new VBox();
		vbox.getChildren().addAll(createTableView(dataArray, col, "No Order to Show"), hbox);
		
		ship.setOnAction( e -> {
			if(PopUpBox.confirm("ship ORDER" + orders.get(index).get(0), "Confirm Shipping?")) {
				cancel.fire();
				shipProduct(index);
			}
		} );
		cancel.setOnAction(e -> {
			Node node = (Node) e.getSource();
		    Stage thisStage = (Stage) node.getScene().getWindow();
		    thisStage.close();
		});
		
		PopUpBox.display("ORDER " + orders.get(index).get(0), vbox, 500);
	}
	
	
	 TableView<ObservableList<String>> getProducts(){
		 ArrayList<ArrayList<String>> products = readProducts();
		 for(int i = 0; i < products.size(); i++) 
			 if(Integer.parseInt(products.get(i).get(4)) == 0) {
				 products.remove(products.get(i));
				 i--;
			 }
		 
		 String[][] dataArray = products.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		 String[] col = {"ID", "PRODUCT", "MANUFACTURER", "PRICE", "QUANTITY"};
		 return createTableView(dataArray, col, "No Product to Show");
	 }

	/**
	 * @param dataArray data to be displayed on the table 
	 * @param col column titles
	 * @return TableView with products to be shipped or restock
	 */
	 TableView<ObservableList<String>> createTableView(String[][] dataArray, String[] col, String msg) {

		TableView<ObservableList<String>> tableView = new TableView<>();
		tableView.setItems(buildData(dataArray));
		tableView.setPlaceholder(new Label(msg));
		
		if(dataArray.length != 0)
			for (int i = 0; i < dataArray[0].length; i++) {
				final int curCol = i;
				final TableColumn<ObservableList<String>, String> column = new TableColumn<>(col[i]);
				column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol)));
				tableView.getColumns().add(column);
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
	 * ship products ordered
	 * @param select product to be shipped 
	 */
	public void shipProduct(int select) {
		
		PopUpBox.alert("success", "ORDER " + orders.get(select).get(0) + " has been Shipped to:\n Client:" +
				orders.get(select).get(1) + "\n Address:" + orders.get(select).get(2), 300);
		
		removeData("delivery", orders.get(select).get(0));
		
	}

	/**
	 * controls and shows notification if exists
	 */
	TableView<ObservableList<String>> getNotifications() {
		
		productsToRestock = readAll("restock");
		String[][] dataArray = productsToRestock.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		String[] col = {"ID", "PRODUCT", "MANUFACTURER"};
		return createTableView(dataArray, col, "No Notification.");
		
	}

	/**
	 * manages the restock of a product
	 * @param id product to restock
	 * @param quantity quantity to restock
	 */
	public void restock(String id, int quantity) {
		
		if(editData("product", 4, id, Integer.toString(quantity))) {
			
			removeData("restock", id);
			PopUpBox.alert("success", "SUCCESS.", 300);
			return;
			
		}
		else
			PopUpBox.alert("fail", "Error in restocking Product.", 300);
	}
	
	/**
	 * adds out of stock product to the restocks.xml file
	 * @param content the product to be added to the restock list
	 */
	public void addRestock(String[] content) {
		addData("restock" , content);
	}

	/**
	 * clears employee profile logged in
	 */
	public void logout() {
		
		employee =  new ArrayList<String>();
		
	}
}
