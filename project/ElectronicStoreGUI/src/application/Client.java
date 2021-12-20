package application;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * Client class
 */
public class Client extends ManageData{

	private static ArrayList<String> user;
	static Map<String, String> imagesPath;
	private static float totalPrice;
	ArrayList<ArrayList<String>> filteredProducts;
	ArrayList<ArrayList<String>> manufacturerBackup;
	ArrayList<ArrayList<String>> minMaxbackup;

	/**
	 * constructor
	 */
	Client() {

		user = new ArrayList<String>();
		totalPrice = 0;
		filteredProducts = new ArrayList<ArrayList<String>>();
		manufacturerBackup = new ArrayList<ArrayList<String>>();
		minMaxbackup  = new ArrayList<ArrayList<String>>();

		imagesPath = new HashMap<String, String>();
		imagesPath.put("edit", "/images/edit.png");
		imagesPath.put("remove", "/images/remove.png");
		imagesPath.put("addtocart", "/images/addtocart.png");
	}


	/**
	 * @return username of a client
	 */
	String getUsername() {

		return user.get(0);

	}
	/**
	 * changes the username of a client
	 * @param newUsername new username
	 */
	void setNewUsername(String newUsername) {

		user.set(0, newUsername);

	}
	
	/**
	 * @return the total price of the products in the client's cart
	 */
	public String getTotalPrice() {
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(totalPrice);
	}

	/**
	 * @return password of a client
	 */
	String getPassword() {

		return user.get(1);

	}

	/**
	 * changes the password of a client
	 * @param newPassword new password
	 */
	void setNewPassword(String newPassword) {

		user.set(1, newPassword);

	}

	/**
	 * @return name of a client
	 */
	String getName() {

		return user.get(2);

	}

	/**
	 * changes the name of a client
	 * @param newName new name
	 */
	void setNewName(String newName) {

		user.set(2, newName);

	}

	/**
	 * @return surname of a client
	 */
	String getSurname() {

		return user.get(3);

	}

	/**
	 * changes the surname of a client
	 * @param newSurname new surname
	 */
	void setNewSurname(String NewSurname) {

		user.set(3, NewSurname);

	}

	/**
	 * @return address of a client
	 */
	String getAddress() {

		return user.get(4);

	}

	/**
	 * changes the address of a client
	 * @param newAddr new address
	 */
	void setNewAddress(String newAddr) {

		user.set(4, newAddr);

	}


	/**
	 * registers a new client
	 * @param name client's name
	 * @param surname client's surname
	 * @param username client's username
	 * @param password client's password
	 */
	void registerNew(String name, String surname, String username, String password) {

		String [] fields = {name, surname, username, password};

		for(int i=0; i<4; i++) {
			if(i == 2)
				if(exist("client", fields[i]))
					return;

		}

		addData("client", fields);	
		user = new ArrayList<String>();
		user.add(fields[2]);
		user.add(fields[3]);
		user.add(fields[0]);
		user.add(fields[1]);
		user.add("none");
	}


	/**
	 * @param username client's username
	 * @param password client's password
	 * @return true if client exist
	 */
	boolean Login(String username, String password) {

		user = getProfile("client", username);

		if(user.isEmpty())
			return false;

		else if(!user.isEmpty() && ((user.get(1) != null) && (user.get(1).equals(password))))
			return true;

		else
			user = new ArrayList<String>();

		return false;

	}



	/**
	 * prints product according to clients request
	 */
	TableView<ObservableList<String>> loadProducts() {

		if(session()) 
			return createTableView(filteredProducts, true);
		return createTableView(filteredProducts, false);

	}

	private TableView<ObservableList<String>> createTableView(ArrayList<ArrayList<String>> products, Boolean addButton) {

		String[][] dataArray = products.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		TableView<ObservableList<String>> tableView = new TableView<>();
		tableView.setItems(buildData(dataArray));
		String[] col = {"PRODUCT", "MANUFACTURER", "PRICE (USD)"};
		tableView.setPlaceholder(new Label("No Product in Store"));
		if(dataArray.length != 0) {
			for (int i = 1; i < dataArray[0].length-1; i++) {

				final int curCol = i;

				if(i == 3) {

					final TableColumn<ObservableList<String>, Float> column = new TableColumn<>(col[i-1]);
					column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(Float.parseFloat(param.getValue().get(curCol))));
					tableView.getColumns().add(column);

				}
				else {

					final TableColumn<ObservableList<String>, String> column = new TableColumn<>(col[i-1]);
					column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol)));
					tableView.getColumns().add(column);

				}
			}	

			if(addButton) {
				TableColumn<ObservableList<String>, Void> colBtn = new TableColumn<ObservableList<String>, Void>();

				Callback<TableColumn<ObservableList<String>, Void>, TableCell<ObservableList<String>, Void>> cellFactory = new Callback<TableColumn<ObservableList<String>, Void>, TableCell<ObservableList<String>, Void>>() {
					@Override
					public TableCell<ObservableList<String>, Void> call(final TableColumn<ObservableList<String>, Void> param) {
						final TableCell<ObservableList<String>, Void> cell = new TableCell<ObservableList<String>, Void>() {

							private final Button btn = new Button("Add to Cart");

							{
								btn.setId("buttonTable");
								btn.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("addtocart")).toExternalForm(), 15, 15, true, true)));                    	
								btn.setOnAction(e -> {
									ObservableList<String> getSelectedItem = getTableView().getItems().get(getIndex());
									PopUpBox.addToCart(Client.this,getSelectedItem.get(0), getSelectedItem.get(4), getSelectedItem.get(1));
								});

							}

							@Override
							public void updateItem(Void item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
								} else {
									setGraphic(btn);
									if(getTableRow() != null)
										if(Integer.parseInt(dataArray[getTableRow().getIndex()][4]) == 0) {
											btn.setId("buttonTable");
											btn.setDisable(true);
											btn.setText("Out Of Stock!");
											btn.setGraphic(null);
										}
								}
							}
						};
						return cell;
					}
				};

				colBtn.setCellFactory(cellFactory);
				tableView.getColumns().add(colBtn);
			}
		}
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setSelectionModel(null);

		return tableView;
	}

	private ObservableList<ObservableList<String>> buildData(String[][] dataArray) {

		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

		for (String[] row : dataArray)
			data.add(FXCollections.observableArrayList(row));

		return data;
	}

	/**
	 * calls the method to place a client order
	 */
	void order(){

		if(!PopUpBox.confirm("WARNING", "Confirm Delivery Address:\n'" + user.get(4) + "' ?"))
			PopUpBox.placeOrder(Client.this);
		else 
			placeOrder(user.get(0), user.get(4));

	}

	/**
	 * calls the method to remove a specific product from client's cart
	 * @param ID of the product to remove
	 * @return false in case the cart is empty
	 */
	boolean removeProduct(String id){

		return removeFromCart(user.get(0), id);
	}

	/**
	 * accesses the cart of a client
	 */
	void cart() {

		readProducts();
		readCart(user.get(0));

	}

	TableView<ObservableList<String>> loadCart() {

		String[][] dataArray = elementsCart.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		TableView<ObservableList<String>> table = createCartTableView(dataArray);
		return table;
	}


	private void calculateTotalPrice(String[][] dataArray) {
		totalPrice = 0;
		for (String[] product : dataArray) {
			totalPrice += (Float.parseFloat(product[4])); 
		}
	}


	private TableView<ObservableList<String>> createCartTableView(String[][] dataArray) {

		TableView<ObservableList<String>> tableView = new TableView<>();
		tableView.setItems(buildData(dataArray));
		String[] col = {"PRODUCT", "QUANTITY",  "MANUFACTURER", "PRICE (USD)"};
		totalPrice = (float) 0.0;

		if(dataArray.length != 0)
			for (int i = 1; i < dataArray[0].length; i++) {

				final int curCol = i;

				if(i == 2) {

					final TableColumn<ObservableList<String>, Integer> column = new TableColumn<>(col[i-1]);
					column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(Integer.parseInt(param.getValue().get(curCol))));
					tableView.getColumns().add(column);

				}
				else if(i == 4) {

					final TableColumn<ObservableList<String>, Float> column = new TableColumn<>(col[i-1]);
					column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(Float.parseFloat(param.getValue().get(curCol))));
					tableView.getColumns().add(column);
				}
				else {

					final TableColumn<ObservableList<String>, String> column = new TableColumn<>(col[i-1]);
					column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol)));
					tableView.getColumns().add(column);
				}

			}

		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		calculateTotalPrice(dataArray);
		return tableView;

	}

	/**
	 * adds product to cart
	 * @param id product ID
	 * @param selectedQuantity quantity
	 */
	void addToCart(String id, String selectedQuantity) {

		editData("client", 4, user.get(0), id + "," + selectedQuantity);

	}

	/**
	 * calls the method to remove user from database
	 */
	void removeUser(){

		removeData("client",user.get(0));

	}

	/**
	 * changes the name of a client on the XML file
	 * @param newName new name
	 */
	void changeName(String newName){

		editData("client", 2,  user.get(0), newName);
		setNewName(newName);

	}

	/**
	 * changes the surname of a client on the XML file
	 * @param newSurname new surname
	 */
	void changeSurname(String newSurname){

		editData("client", 3,  user.get(0), newSurname);
		setNewSurname(newSurname);

	}

	/**
	 * calls the method to read products from database
	 */
	void readProducts() {
		filteredProducts = readAll("product");
	}

	/**
	 * sets new username
	 * @param newUsername new username to be set
	 * @return returns true if changed successfully, otherwise it returns false
	 */
	boolean changeUsername(String newUsername){

		if(!exist("client", newUsername)) {

			editData("client", 0, user.get(0), newUsername);
			setNewUsername(newUsername);
			return true;

		}
		else
			return false;
	}

	/**
	 * sets the address/new address of a client
	 * @param newAddress the address/new address to be set
	 */
	void setAddress(String newAddress){

		editData("client", 5,  user.get(0), newAddress);
		setNewAddress(newAddress);

	}

	/**
	 * changes the password of a client
	 * @param oldPassword old password to be changed
	 * @param newPassword new password to be set
	 */
	boolean changePassword(String oldPassword, String newPassword){

		if (user.get(1).equals(oldPassword)) {

			editData("client", 1, user.get(0), newPassword);
			setNewPassword(newPassword);
			return true;

		}
		else
			return false;
	}

	/**
	 * searches a product
	 * @param search the type of search: 
	 * "string" for searching without filters; "minMax" for filtering the price;
	 * "manufacturerSelected" and "manufacturerUnselected" for filtering the manufacturers.
	 * @param str the value to be searched for (if search = "string" ); selected/unselected manufacturers (if search = "manufacturerSelected"/"manufacturerUnselected"); 
	 * minimum price (if search = "minMax").
	 * @param str2 maximum price (if search = "minMax").
	 */
	void searchProducts(String search, String str, String str2) {

		switch(search) {

		case "string":
			filteredProducts =  filterList(1, str);
			break;

		case "minMax":
			minMaxbackup.addAll(filteredProducts);
			filteredProducts = new ArrayList<ArrayList<String>>();
			for (int i = 0; i < minMaxbackup.size(); i++)
				if((Float.parseFloat(minMaxbackup.get(i).get(3)) <= Float.parseFloat(str)) &&
						(Float.parseFloat(minMaxbackup.get(i).get(3)) >= Float.parseFloat(str2))) {
					filteredProducts.add(minMaxbackup.get(i));
					minMaxbackup.remove(minMaxbackup.get(i));
					i--;
				}

			break;

		case "manufacturerSelected":
			ArrayList<ArrayList<String>> temp =  filterList(2, str);
			for(ArrayList<String> product : temp)
				manufacturerBackup.add(product);
			filteredProducts = manufacturerBackup;
			break;

		case "manufacturerUnselected":
			for (int i = 0; i < manufacturerBackup.size(); i++)
				if(manufacturerBackup.get(i).get(2).equals(str)) {
					manufacturerBackup.remove(manufacturerBackup.get(i));
					i--;
				}
			filteredProducts = manufacturerBackup;
			break;

		default:
			break;
		}
	}

	/**
	 * @return true if client is logged in
	 */
	public boolean session() {
		if(user.isEmpty())
			return false;
		return true;
	}

	/**
	 * clear a client logged in
	 */
	public void logout() {
		user = new ArrayList<String>();
	}

	/**
	 * @param minOrMax minimum or maximum price
	 * @return returns the minimum or maximum price selected by the user for filtering the products
	 */
	public float getPrice(String minOrMax) {
		ArrayList<ArrayList<String>> temp = filteredProducts;
		float price = 0;
		if(temp.size() > 0) {
			if(minOrMax.equals("min"))
				temp = new ArrayList<ArrayList<String>>(temp.stream().sorted((product1, product2) -> Float.compare(Float.parseFloat(product1.get(3)), Float.parseFloat(product2.get(3)))).collect(Collectors.toList()));
			else
				temp = new ArrayList<ArrayList<String>>(temp.stream().sorted((product1, product2) -> Float.compare(Float.parseFloat(product2.get(3)), Float.parseFloat(product1.get(3)))).collect(Collectors.toList()));

			price = Float.parseFloat(temp.get(0).get(3));
		}
		return price;
	}

	/**
	 * @return returns the list of the manufacturers
	 */
	public ArrayList<String> getManufacturers() {
		return productsManufacturerList;
	}

	/**
	 * clears the filters for the product manufacturers
	 */
	public void resetFilteredProductsManufacturer() {
		manufacturerBackup = new ArrayList<ArrayList<String>>();	
	}
	
	/**
	 * clears the filters for the minimum and maximum price
	 */
	public void resetFilteredProductsMinMAx() {	
		minMaxbackup = new ArrayList<ArrayList<String>>(); 
	}
}
