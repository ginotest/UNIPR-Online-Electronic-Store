package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * Client class
 *
 */
public class Client extends ManageData{
	
	/**
	 * reads input from console
	 */
	public Scanner input = new Scanner(System.in);
	private static ArrayList<String> user;
	private static int resultManageProfile;
	
	/**
	 * constructor
	 */
	Client() {
		user = new ArrayList<String>();
		resultManageProfile=0;
	}

	int resultManageProfile_GET() {
		return resultManageProfile;
	}

	
	void resultManageProfile_SET(int x) {
		resultManageProfile=x;
	}

	/**
	 * @return username of a client
	 */
	String getUsername() {
		return user.get(0);
	}

	
	/**
	 * @return address of a client
	 */
	String getAddress() {
		return user.get(4);
	}
	
	
	/**
	 * registers a new client
	 */
	void registerNew(String name, String surname, String username, String password) {

		String [] fields = {name, surname, username, password};
		for(int i=0; i<4; i++) {

			if(i == 2)
				if(exist("user", fields[i])) {
					return;
				}

		}

		addData("client", fields);	
		user = new ArrayList<String>();
		user.add(fields[2]);
		user.add(fields[3]);
		user.add(fields[0]);
		user.add(fields[1]);
		user.add("none");
		//System.out.println("Registration successful!");
	}

	
	
	
	/**
	 * @param username client's username
	 * @param password client's password
	 * @return true if client exist
	 */
	boolean Login(String username, String password) {

		user = getProfile("client", username);
		System.out.println("DEBUG: " + user);
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
		String[][] dataArray = elements.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		return createTableView(dataArray);
	}


	private TableView<ObservableList<String>> createTableView(String[][] dataArray) {
		TableView<ObservableList<String>> tableView = new TableView<>();
		tableView.setItems(buildData(dataArray));
		String[] col = {"PRODUCT", "MANUFACTURER", "PRICE"};
		if(dataArray.length != 0)
			for (int i = 1; i < dataArray[0].length-1; i++) {
				final int curCol = i;
				final TableColumn<ObservableList<String>, String> column = new TableColumn<>(
						col[i-1]
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

	/**
	 * calls the method to place a client order
	 */
	void order(){
		placeOrder(user.get(0), user.get(4));
	}

	/**
	 * calls the method to change the quantity of a specific product in client's cart
	 */
	void changeQuantity(){

		//editQuantity(user.get(0));
	}

	/**
	 * calls the method to remove a specific product from client's cart
	 */
	void removeProduct(){

		removeFromCart(user.get(0));
	}

	/**
	 * calls the method to remove a specific product from client's cart
	 */
	void removeProductGUI(String id){

		removeFromCartGUI(user.get(0), id);
	}

	/**
	 * accesses the cart of a client
	 */
	void cart() {
		readProducts();
		//showCart(user.get(0));
		readCartGUI(user.get(0));
	}

	TableView<ObservableList<String>> loadCart() {
		String[][] dataArray = elementsCart.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		return createCartTableView(dataArray);
	}


	private TableView<ObservableList<String>> createCartTableView(String[][] dataArray) {
		TableView<ObservableList<String>> tableView = new TableView<>();
		tableView.setItems(buildData(dataArray));
		System.out.println( dataArray[0].length);
		String[] col = {"PRODUCT", "QUANTITY",  "MANUFACTURER", "PRICE"};
		if(dataArray.length != 0)
			for (int i = 1; i < dataArray[0].length; i++) {
				final int curCol = i;
				final TableColumn<ObservableList<String>, String> column = new TableColumn<>(
						col[i-1]
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
	 * adds product to cart
	 */
	void addToCart() {

		int quantity, selection;
		ArrayList<ArrayList<String>> product = elements;

		System.out.print("Which product number? : ");

		try {
			selection = input.nextInt()-1;

		}catch(InputMismatchException e){
			System.out.println("Incorrect choice.");
			return;
		}

		if(selection <= product.size()-1 && selection >= 0) {

			if(Integer.parseInt(product.get(selection).get(4))>0) {

				System.out.print("How many? ");

				try {
					quantity = input.nextInt();

				}catch(InputMismatchException e){
					System.out.println("That is not a number.");
					return;
				}


				if(quantity <= Integer.parseInt(product.get(selection).get(4))) {

					System.out.println("You have added: " + quantity + " " + product.get(selection).get(2) + " " + product.get(selection).get(1) + " to the cart.");
					editData("client", 4, user.get(0), product.get(selection).get(0) + "," + quantity);
				}
				else
					System.out.println("There are only " + product.get(selection).get(4) + " " + product.get(selection).get(2) + " " + product.get(selection).get(1) + " available!");
			}
			else
				System.out.println("This product is currently unavailable!");
		}
		else
			System.out.println("This product does not exist.");
	}


	void addToCartGUI(String id, String quantity) {

		//to do: alertwindow
		//ArrayList<ArrayList<String>> product = elements;
	
		System.out.println(editData("client", 4, user.get(0), id + "," + quantity));

	}

	public void inputselectquantity(String id) {
		Stage alertWindow;

		alertWindow = new Stage();

		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle("");
		alertWindow.setMinWidth(200);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5,5,5,5));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(false); //DEBUG

		Label msgLabel = new Label("How many?");
		GridPane.setConstraints(msgLabel, 1, 0);


		TextField username = new TextField("");
		GridPane.setFillWidth(username, true);

		grid.add(username, 1, 1);
		
		Button loginBtn = new Button("Enter");
		GridPane.setConstraints(loginBtn, 1, 2);
		Button cancelBtn = new Button("Cancel");
		GridPane.setConstraints(cancelBtn, 4, 2);
		cancelBtn.setMaxWidth(Double.MAX_VALUE);
		loginBtn.setMaxWidth(Double.MAX_VALUE);

		GridPane.setColumnSpan(username, 4);
		loginBtn.setOnAction(e -> {

			if(!username.getText().trim().equals("")) {
				addToCartGUI(id,username.getText().trim());
				alertWindow.close();
			}
		});
		cancelBtn.setOnAction(e -> {
			alertWindow.close();
		});

		grid.getChildren().addAll(msgLabel, cancelBtn, loginBtn);
		alertWindow.setResizable(false);
		Scene scene = new Scene(grid, 70, 100);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest(e -> {
			e.consume();
			alertWindow.close();
		});
	}

	public void inputeditquantity(String id) {
		Stage alertWindow;

		alertWindow = new Stage();

		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle("");
		alertWindow.setMinWidth(200);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5,5,5,5));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(false); //DEBUG

		Label msgLabel = new Label("New quantity:");
		GridPane.setConstraints(msgLabel, 1, 0);


		TextField username = new TextField("");
		GridPane.setFillWidth(username, true);

		grid.add(username, 1, 1);

		Button loginBtn = new Button("Enter");
		GridPane.setConstraints(loginBtn, 1, 2);
		Button cancelBtn = new Button("Cancel");
		GridPane.setConstraints(cancelBtn, 4, 2);
		cancelBtn.setMaxWidth(Double.MAX_VALUE);
		loginBtn.setMaxWidth(Double.MAX_VALUE);

		GridPane.setColumnSpan(username, 4);
		loginBtn.setOnAction(e -> {

			if(!username.getText().trim().equals("")) {
				editQuantity(user.get(0), id, Integer.parseInt(username.getText().trim()));
				alertWindow.close();
			}
		});
		cancelBtn.setOnAction(e -> {
			alertWindow.close();
		});

		grid.getChildren().addAll(msgLabel, cancelBtn, loginBtn);
		alertWindow.setResizable(false);
		Scene scene = new Scene(grid, 70, 100);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest(e -> {
			e.consume();
			alertWindow.close();
		});
	}

	public void orderGUI(String id) {
		Stage alertWindow;

		alertWindow = new Stage();

		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle("");
		alertWindow.setMinWidth(340);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5,5,5,5));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(false); //DEBUG

		Label msgLabel = new Label("Please, specify an address");
		GridPane.setConstraints(msgLabel, 1, 0);


		TextField username = new TextField("");
		GridPane.setFillWidth(username, true);

		grid.add(username, 1, 1);


		Button loginBtn = new Button("Continue");
		GridPane.setConstraints(loginBtn, 1, 2);
		Button cancelBtn = new Button("Save");
		GridPane.setConstraints(cancelBtn, 4, 2);
		cancelBtn.setMaxWidth(Double.MAX_VALUE);
		loginBtn.setMaxWidth(Double.MAX_VALUE);

		GridPane.setColumnSpan(username, 4);
		loginBtn.setOnAction(e -> {

				if(!username.getText().trim().equals("")) {
					placeOrder(user.get(0), username.getText().trim());
				alertWindow.close();
				
				}
			
		});
		cancelBtn.setOnAction(e -> {
			
			editData("client", 5, user.get(0), username.getText().trim());
			System.out.println("The address has been saved!");

		});

		grid.getChildren().addAll(msgLabel, cancelBtn, loginBtn);
		alertWindow.setResizable(false);
		Scene scene = new Scene(grid, 340, 100);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest(e -> {
			e.consume();
			alertWindow.close();
		});
	}


	public void manageProfileGUI() {
		Stage alertWindow;

		alertWindow = new Stage();

		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle("");
		alertWindow.setMinWidth(600);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20,20,20,20));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(false); //DEBUG

		TextField name = new TextField(user.get(2));
		TextField surname = new TextField(user.get(3));
		TextField username = new TextField(user.get(0));
		TextField password = new TextField("");
		TextField passwordRepeat1 = new TextField("");
		TextField passwordRepeat2 = new TextField("");
		TextField address = new TextField(user.get(4));

		password.setPromptText("Current password");
		passwordRepeat1.setPromptText("New password");
		passwordRepeat2.setPromptText("New password, again");

		Button setName = new Button("Change");
		Button setSurname = new Button("Change");
		Button setUsername = new Button("Change");
		Button setAddress = new Button("Set");
		Button setPassword = new Button("Change");

		if(address.getText().trim().equals("none"))
			address.setText("");


		if(!address.getText().trim().equals(""))
			setAddress.setText("Change");

		setName.setOnAction(evenChangeName -> {

			changeName(name.getText().trim());
			user.set(2, name.getText().trim());

		});


		setSurname.setOnAction(evenChangeSurname -> {

			changeSurname(surname.getText().trim());
			user.set(3, surname.getText().trim());

		});

		setAddress.setOnAction(evenChangeAddress -> {

			setAddress(address.getText().trim());
			user.set(4, address.getText().trim());

		});

		setPassword.setOnAction(evenChangePassword -> {

			if(passwordRepeat1.getText().trim().equals(passwordRepeat2.getText().trim()))
				if(changePassword(password.getText().trim(), passwordRepeat1.getText().trim()))
					user.set(2, passwordRepeat1.getText().trim());

		});

		setUsername.setOnAction(evenChangeUsername -> {


			if(changeUsername(username.getText().trim())) {
				user.set(0, username.getText().trim());
				resultManageProfile_SET(1);
			}
			//else bla bla bla

		});

		Label lName = new Label("Name:");
		Label lSurname = new Label("Surname:");
		Label lUsername = new Label("Username:");
		Label lAddress = new Label("Address:");
		Label lPassword = new Label("Password:");









		GridPane.setConstraints(lName, 1, 0);
		GridPane.setConstraints(lSurname, 5, 0);

		GridPane.setConstraints(lUsername, 1, 3);
		GridPane.setConstraints(lAddress, 5, 3);

		GridPane.setConstraints(lPassword, 2, 6);

		GridPane.setConstraints(setName, 1, 2);
		GridPane.setConstraints(setSurname, 5, 2);
		GridPane.setConstraints(setUsername, 1, 5);
		GridPane.setConstraints(setAddress, 5, 5);
		GridPane.setConstraints(setPassword, 4, 10);






		System.out.println(user);
		//GridPane.setFillWidth(username, true);

		//username.setPromptText("username");
		grid.add(name, 1, 1);
		grid.add(surname, 5, 1);

		grid.add(address, 5, 4);
		grid.add(username, 1, 4);

		grid.add(password, 2, 7);
		grid.add(passwordRepeat1, 2, 8);
		grid.add(passwordRepeat2, 2, 9);




		//final Label error = new Label("");
		//GridPane.setConstraints(error, 1, 5);

		Button loginBtn = new Button("Enter");
		GridPane.setConstraints(loginBtn, 1, 7);
		Button cancelBtn = new Button("Cancel");
		GridPane.setConstraints(cancelBtn, 3, 7);
		cancelBtn.setMaxWidth(Double.MAX_VALUE);
		loginBtn.setMaxWidth(Double.MAX_VALUE);

		GridPane.setColumnSpan(name, 4);
		GridPane.setColumnSpan(lName, 4);
		GridPane.setColumnSpan(lSurname, 4);
		GridPane.setColumnSpan(surname, 20);
		GridPane.setColumnSpan(username, 4);
		GridPane.setColumnSpan(address, 20);
		GridPane.setColumnSpan(password, 4);
		GridPane.setColumnSpan(passwordRepeat1, 4);
		GridPane.setColumnSpan(passwordRepeat2, 4);
		//address.setMaxWidth(Double.MAX_VALUE);
		GridPane.setFillWidth(address, true);
		GridPane.setFillWidth(surname, true);


		loginBtn.setOnAction(e -> {

			if(!username.getText().trim().equals("")) {
				//editQuantity(user.get(0), id, Integer.parseInt(username.getText().trim()));
				alertWindow.close();
			}
		});
		cancelBtn.setOnAction(e -> {
			alertWindow.close();
		});

		grid.getChildren().addAll(setName, setSurname, setUsername, setAddress, setPassword, lName, lSurname, lUsername, lAddress, lPassword );
		alertWindow.setResizable(false);
		Scene scene = new Scene(grid, 600, 350);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest(e -> {
			e.consume();
			alertWindow.close();
		});
	}



	public int manageProfile() {

		manageProfileGUI();
		return 	resultManageProfile_GET();

	}



	void changeName(String newName){
		editData("client", 2,  user.get(0), newName);
		System.out.println("The name has been set!");
	}


	void changeSurname(String newSurname){
		editData("client", 3,  user.get(0), newSurname);
		System.out.println("The surname has been set!");
	}


	/**
	 * calls the method to read products from database
	 */
	void readProducts() {
		readAll("product");
	}

	/**
	 * calls the method to remove products from database
	 */
	void removeUser(){
		removeData("client",user.get(0));
	}

	/**
	 * sets new username
	 * @param newUsername new username to be set
	 */
	boolean changeUsername(String newUsername){

		if(!exist("client", newUsername)) {

			editData("client", 0, user.get(0), newUsername);
			user.set(0, newUsername);
			System.out.println("Username changed");
			return true;
		}
		else {
			System.out.println("Username already exists");
			return false;
		}
	}

	/**
	 * sets the address/new address of a client
	 * @param newAddress the address/new address to be set
	 */
	void setAddress(String newAddress){
		editData("client", 5,  user.get(0), newAddress);
		System.out.println("The address has been set!");
	}

	/**
	 * changes the password of a client
	 * @param oldPassword old password to be changed
	 * @param newPassword new pasword to be set
	 */
	boolean changePassword(String oldPassword, String newPassword){

		if (user.get(1).equals(oldPassword)) {

			editData("client", 1, user.get(0), newPassword);
			System.out.println("Password changed");
			return true;
		}
		else {
			System.out.println("Your old password was entered incorrectly, please enter it again.");
			return false;
		}
	}

	/**
	 * searches a product
	 * @param search category to be searched
	 * @param str the value to be searched for
	 */
	void searchProducts(String str) {
		filterList(1, str);
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
	 * sorts products in ascendind/descending order
	 * @param string the order in which it is to be sorted (asc/dec)
	 */
	public void sortProducts(String string) {
		if(string.equals("asc"))
			elements = new ArrayList<ArrayList<String>>(elements.stream().sorted((product1, product2) -> Double.compare(Double.parseDouble(product1.get(3).substring(1)), Double.parseDouble(product2.get(3).substring(1)))).collect(Collectors.toList()));
		elements = new ArrayList<ArrayList<String>>(elements.stream().sorted((product1, product2) -> Double.compare(Double.parseDouble(product2.get(3).substring(1)), Double.parseDouble(product1.get(3).substring(1)))).collect(Collectors.toList()));
	}
}
