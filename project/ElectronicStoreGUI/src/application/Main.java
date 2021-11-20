package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class Main extends Application{
	Stage window;
	int loginOrRegister=1;
	boolean passed=false;
	boolean backToEmployeeArea=false;

	static Map<String, String> imagesPath;
	/**
	 * creates an Admin object
	 */
	static Admin admin = new Admin();
	/**
	 * creates a User object
	 */
	static Client user = new Client();
	/**
	 * creates an Employee object
	 */
	static Employee employee = new Employee();

	public static void main(String[] args) {
		imagesPath = new HashMap<String, String>();
		imagesPath.put("logo", "/images/logo.png");
		imagesPath.put("searchIcon", "/images/search.png");
		imagesPath.put("cartIcon", "/images/cart.png");
		imagesPath.put("userIcon", "/images/user.png");

		launch(args);
	}
	TableView<ObservableList<String>> getTable;
	ArrayList<String> getSelectedItem;

	@SuppressWarnings("unchecked")
	@Override
	public void start(@SuppressWarnings("exports") Stage primaryStage) {


		window = primaryStage;
		window.getIcons().add(new Image(getClass().getResource(imagesPath.get("logo")).toExternalForm()));
		window.setTitle("UNIPR ONLINE ELECTRONIC STORE");

		BorderPane borderPane = new BorderPane();
		StackPane products = new StackPane();

		Button shipOrders = new Button("Ship Orders");
		Button notifications = new Button("Notifications");
		Button manageEmployees = new Button("Manage Employees");
		Button manageProductType = new Button("Manage Product Type");

		HBox hbox1 = new HBox(5);
		HBox hbox2 = new HBox(5);
		HBox hboxEmployeeArea = new HBox(5);
		HBox hboxAdminArea = new HBox(5);
		hboxEmployeeArea.setAlignment(Pos.CENTER);
		hboxAdminArea.setAlignment(Pos.CENTER);

		BorderPane top = new BorderPane();
		BorderPane bottom = new BorderPane();
		BorderPane employeeAreaCenter = new BorderPane();
		BorderPane adminAreaCenter = new BorderPane();
		hboxEmployeeArea.getChildren().addAll(shipOrders,notifications);
		hboxAdminArea.getChildren().addAll(manageEmployees,manageProductType);

		employeeAreaCenter.setCenter(hboxEmployeeArea);
		adminAreaCenter.setCenter(hboxAdminArea);

		Button addtocart = new Button("Add to Cart");

		MenuBar menuBarLogin = new MenuBar();
		Menu loginMenu = new Menu("Login/Register");
		Menu loginUsers = new Menu("Users");
		MenuItem signIn = new MenuItem("Sign in");
		MenuItem signUp = new MenuItem("Sign up");
		loginUsers.getItems().addAll(signIn , signUp);
		Menu loginEmployees = new Menu("Employees");
		MenuItem signInEmployee = new MenuItem("Sign in");
		loginEmployees.getItems().add(signInEmployee);
		loginMenu.getItems().addAll(loginUsers,loginEmployees);
		menuBarLogin.getMenus().add(loginMenu);

		Button editQuantity = new Button("Edit Quantity");
		Label output = new Label("");
		Button back = new Button("Back");
		Button placeOrder = new Button("Place Order");
		Button removeItem = new Button("Remove Item");
		Button removeItemAdmin = new Button("Remove Item");
		Button addItemAdmin = new Button("Add Item");
		Button editItemAdmin = new Button("Edit Item");
		Button removeEmployeeAdmin = new Button("Remove Account");
		Button addEmployeeAdmin = new Button("Add Account");
		Button editEmployeeAdmin = new Button("Edit Account");

		Button homePage = new Button("Home");
		homePage.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("logo")).toExternalForm(), 15, 15, true, true)));
		homePage.setOnAction(e -> {
			products.getChildren().clear();
			user.readProducts();
			products.getChildren().add(user.loadProducts());
			if(!menuBarLogin.isVisible())
				addtocart.setVisible(true);
			editQuantity.setVisible(false);
			output.setText("");
			back.setVisible(false);
			placeOrder.setVisible(false);
			removeItem.setVisible(false);
			removeItemAdmin.setVisible(false);

		});

		TextField searchInput = new TextField();
		searchInput.setPromptText("search product");
		searchInput.setAlignment(Pos.CENTER);

		Button searchBtn = new Button("search");
		searchBtn.setOnAction(eventSearch -> {
			String str = searchInput.getText();
			if ((!str.trim().equals(""))) {
				products.getChildren().clear();
				user.searchProducts(str);
				products.getChildren().add(user.loadProducts());

			}
		});

		searchBtn.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("searchIcon")).toExternalForm(), 15, 15, true, true)));
		searchBtn.setAlignment(Pos.CENTER);

		signInEmployee.setOnAction(eventLoginEmployee -> {

			if(!passed) {
				if(backToEmployeeArea) {
					passed =true;
				}

				else if(!backToEmployeeArea)
					if(employeeArea())
						passed=true;
					else
						passed = false;
			}
			if(passed) {

				homePage.setOnAction(e -> {

					removeItemAdmin.setVisible(false);
					addItemAdmin.setVisible(false);
					editItemAdmin.setVisible(false);
					removeEmployeeAdmin.setVisible(false);
					addEmployeeAdmin.setVisible(false);
					editEmployeeAdmin.setVisible(false);
					signInEmployee.fire();
				});

				products.getChildren().clear();
				MenuBar menuBarEmployee = new MenuBar();
				Menu employeeMenu = new Menu(employee.getUsername());
				employeeMenu.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("userIcon")).toExternalForm(), 15, 15, true, true)));

				MenuItem adminArea = new MenuItem("Admin Area");

				adminArea.setOnAction(eventAdminArea ->{
					
					removeItemAdmin.setVisible(false);
					addItemAdmin.setVisible(false);
					editItemAdmin.setVisible(false);
					removeEmployeeAdmin.setVisible(false);
					addEmployeeAdmin.setVisible(false);
					editEmployeeAdmin.setVisible(false);

					if(!backToEmployeeArea) {
						admin.login(employee.getUsername(), employee.getPassword());
						adminArea.setText("Employee Area");

						products.getChildren().clear();
						products.getChildren().add(adminAreaCenter);

						homePage.setOnAction(e -> {

							removeItemAdmin.setVisible(false);
							addItemAdmin.setVisible(false);
							editItemAdmin.setVisible(false);
							removeEmployeeAdmin.setVisible(false);
							addEmployeeAdmin.setVisible(false);
							editEmployeeAdmin.setVisible(false);

							backToEmployeeArea=false;

							adminArea.fire();

						});

						backToEmployeeArea=true;
					}

					else if(backToEmployeeArea) {
						admin.login(employee.getUsername(), employee.getPassword());
						adminArea.setText("Admin Area");

						products.getChildren().clear();
						products.getChildren().add(adminAreaCenter);

						homePage.setOnAction(e -> {

							removeItemAdmin.setVisible(false);
							addItemAdmin.setVisible(false);
							editItemAdmin.setVisible(false);
							removeEmployeeAdmin.setVisible(false);
							addEmployeeAdmin.setVisible(false);
							editEmployeeAdmin.setVisible(false);

						});

						signInEmployee.fire();
						backToEmployeeArea=false;

					}

					manageProductType.setOnAction(eventManageProducts -> {

						removeItemAdmin.setVisible(true);
						addItemAdmin.setVisible(true);
						editItemAdmin.setVisible(true);


						products.getChildren().clear();
						admin.readAllProducts();
						products.getChildren().add(admin.loadProducts());

						HBox hbox3 = new HBox(5);

						hbox3.getChildren().addAll(addItemAdmin,editItemAdmin,removeItemAdmin);
						hbox3.setAlignment(Pos.CENTER);

						bottom.setLeft(hbox3);

						borderPane.setBottom(bottom);

						removeItemAdmin.setOnAction(eventRemoveItemAdmin ->{

							getTable = (TableView<ObservableList<String>>) products.getChildren().get(0);
							getSelectedItem = new ArrayList<>(getTable.getItems().get(getTable.getSelectionModel().getSelectedIndex()));
							admin.removeProduct(getSelectedItem.get(0));
							products.getChildren().clear();
							admin.readAllProducts();
							products.getChildren().add(admin.loadProducts());
						});

						editItemAdmin.setOnAction(eventEditItemAdmin ->{

							getTable = (TableView<ObservableList<String>>) products.getChildren().get(0);
							getSelectedItem = new ArrayList<>(getTable.getItems().get(getTable.getSelectionModel().getSelectedIndex()));
							ArrayList<String> data = new ArrayList<String>();
							data.add(getSelectedItem.get(0));
							data.add(getSelectedItem.get(1));
							data.add(getSelectedItem.get(2));
							data.add(getSelectedItem.get(3));
							data.add(getSelectedItem.get(4));

							PopUpBox.editProductType(admin, data);
							products.getChildren().clear();
							admin.readAllProducts();
							products.getChildren().add(admin.loadProducts());
						});
						
						addItemAdmin.setOnAction(eventAddItemAdmin ->{

							PopUpBox.addProduct(admin);
							products.getChildren().clear();
							admin.readAllProducts();
							products.getChildren().add(admin.loadProducts());

						});

					});


					manageEmployees.setOnAction(eventManageEmployees -> {

						removeEmployeeAdmin.setVisible(true);
						addEmployeeAdmin.setVisible(true);
						editEmployeeAdmin.setVisible(true);


						products.getChildren().clear();
						admin.readAllEmployees();
						products.getChildren().add(admin.loadEmployees());

						HBox hbox3 = new HBox(5);

						hbox3.getChildren().addAll(addEmployeeAdmin,editEmployeeAdmin,removeEmployeeAdmin);
						hbox3.setAlignment(Pos.CENTER);

						bottom.setLeft(hbox3);

						borderPane.setBottom(bottom);

						removeEmployeeAdmin.setOnAction(eventRemoveEmployeeAdmin ->{

							getTable = (TableView<ObservableList<String>>) products.getChildren().get(0);
							getSelectedItem = new ArrayList<>(getTable.getItems().get(getTable.getSelectionModel().getSelectedIndex()));
							admin.removeEmployee(getSelectedItem.get(2));
							products.getChildren().clear();
							admin.readAllEmployees();
							products.getChildren().add(admin.loadEmployees());
						});
						
						editEmployeeAdmin.setOnAction(eventEditItemAdmin ->{

							getTable = (TableView<ObservableList<String>>) products.getChildren().get(0);
							getSelectedItem = new ArrayList<>(getTable.getItems().get(getTable.getSelectionModel().getSelectedIndex()));
							ArrayList<String> data = new ArrayList<String>();
							data.add(getSelectedItem.get(0));
							data.add(getSelectedItem.get(1));
							data.add(getSelectedItem.get(2));
							data.add(getSelectedItem.get(3));
							data.add(getSelectedItem.get(4));

							PopUpBox.editEmployee(admin, data);
							products.getChildren().clear();
							admin.readAllEmployees();
							products.getChildren().add(admin.loadEmployees());
						});

						addEmployeeAdmin.setOnAction(eventAddEmployeeAdmin ->{

							PopUpBox.addEmployee(admin);
							products.getChildren().clear();
							admin.readAllEmployees();
							products.getChildren().add(admin.loadEmployees());
						});

					});

				});

				
				shipOrders.setOnAction(eventShipOrders ->{
					
					//do your stuff here
					
				});
				
				notifications.setOnAction(eventNotifications ->{
					
					//do your stuff here
					
				});


				MenuItem logout = new MenuItem("Logout");

				searchBtn.setVisible(false);
				searchInput.setVisible(false);

				logout.setOnAction(eventLogout ->{

					homePage.setOnAction(e -> {
						products.getChildren().clear();
						user.readProducts();
						products.getChildren().add(user.loadProducts());
						if(!menuBarLogin.isVisible())
							addtocart.setVisible(true);
						editQuantity.setVisible(false);
						output.setText("");
						back.setVisible(false);
						placeOrder.setVisible(false);
						removeItem.setVisible(false);
						removeItemAdmin.setVisible(false);
						addItemAdmin.setVisible(false);
						editItemAdmin.setVisible(false);
						removeEmployeeAdmin.setVisible(false);
						addEmployeeAdmin.setVisible(false);
						editEmployeeAdmin.setVisible(false);

					});

					backToEmployeeArea=false;
					loginOrRegister=1;
					passed=false;
					menuBarLogin.setVisible(true);
					addtocart.setVisible(false);
					searchBtn.setVisible(true);
					searchInput.setVisible(true);
					employee.logout();

					homePage.fire();

					hbox1.setAlignment(Pos.CENTER);
					hbox2.getChildren().clear();
					hbox2.getChildren().addAll(menuBarLogin);

					top.setLeft(homePage);
					top.setCenter(hbox1);
					top.setRight(hbox2);

					borderPane.setTop(top);

				});				
				addtocart.setVisible(true);
				menuBarLogin.setVisible(false);




				if(employee.isAdmin()) {

					employeeMenu.getItems().addAll(adminArea);

				}
				
				employeeMenu.getItems().addAll(logout);
				menuBarEmployee.getMenus().add(employeeMenu);
				products.getChildren().add(employeeAreaCenter);
				hbox1.setAlignment(Pos.CENTER);
				hbox2.getChildren().clear();
				hbox2.getChildren().addAll(menuBarEmployee);
				top.setLeft(homePage);
				top.setCenter(hbox1);
				top.setRight(hbox2);


				borderPane.setTop(top);

			}
		});

		signUp.setOnAction(eventRegister -> {
			if(register()) {
				signIn.fire();
			}
		});

		signIn.setOnAction(eventLogin -> {

			if(loginOrRegister==0) {
				passed =true;
			}

			else if(loginOrRegister==1)
				if(clientArea())
					passed=true;
				else
					passed = false;

			if(passed == true) {

				addtocart.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("cartIcon")).toExternalForm(), 30, 15, true, true)));
				addtocart.setOnAction(eventAddToCart -> {

					getTable = (TableView<ObservableList<String>>) products.getChildren().get(0);
					getSelectedItem = new ArrayList<>(getTable.getItems().get(getTable.getSelectionModel().getSelectedIndex()));
					user.inputselectquantity(getSelectedItem.get(0));
				});


				MenuBar menuBarUser = new MenuBar();
				Menu userMenu = new Menu(user.getUsername());
				userMenu.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("userIcon")).toExternalForm(), 15, 15, true, true)));

				MenuItem goToCart = new MenuItem("Go to Cart");
				MenuItem manageProfile = new MenuItem("Manage Profile");
				MenuItem logout = new MenuItem("Logout");



				goToCart.setOnAction(eventCart -> {

					products.getChildren().clear();
					user.cart();
					products.getChildren().add(user.loadCart());	


					removeItem.setOnAction(eventRemove ->{

						getTable = (TableView<ObservableList<String>>) products.getChildren().get(0);
						getSelectedItem = new ArrayList<>(getTable.getItems().get(getTable.getSelectionModel().getSelectedIndex()));		
						user.removeProductGUI(getSelectedItem.get(0));
						products.getChildren().clear();
						user.cart();
						products.getChildren().add(user.loadCart());

					});

					addtocart.setVisible(false);
					editQuantity.setVisible(true);

					editQuantity.setOnAction(eventQuantity ->{

						getTable = (TableView<ObservableList<String>>) products.getChildren().get(0);
						getSelectedItem = new ArrayList<>(getTable.getItems().get(getTable.getSelectionModel().getSelectedIndex()));		
						user.inputeditquantity(getSelectedItem.get(0));
						products.getChildren().clear();
						user.cart();
						products.getChildren().add(user.loadCart());


					});

					output.setText("");
					back.setVisible(true);
					placeOrder.setVisible(true);


					placeOrder.setOnAction(orderEvent ->{

						if(user.getAddress().equals("none")) {
							user.orderGUI("");
							goToCart.fire();
						}
						else {

							user.placeOrder(user.getUsername(), user.getAddress());
							goToCart.fire();
						}

					});

					removeItem.setVisible(true);

					HBox hbox3 = new HBox(5);
					HBox hbox4 = new HBox(5);
					HBox hbox5 = new HBox(5);

					hbox3.getChildren().addAll(removeItem, editQuantity);
					hbox3.setAlignment(Pos.CENTER);

					hbox4.getChildren().add(output);
					hbox4.setAlignment(Pos.CENTER);

					hbox5.getChildren().addAll(back, placeOrder);
					hbox5.setAlignment(Pos.CENTER);

					bottom.setLeft(hbox3);
					bottom.setCenter(hbox4);
					bottom.setRight(hbox5);

					borderPane.setBottom(bottom);
				});


				manageProfile.setOnAction(eventProfile ->{

					int result =user.manageProfile();
					System.out.println(result);
					if(result == 1)
						userMenu.setText(user.getUsername());

				});


				logout.setOnAction(eventLogout ->{

					loginOrRegister=1;
					passed=false;
					menuBarLogin.setVisible(true);
					addtocart.setVisible(false);
					user.logout();

					homePage.fire();

					hbox1.setAlignment(Pos.CENTER);
					hbox2.getChildren().clear();
					hbox2.getChildren().addAll(menuBarLogin);

					top.setLeft(homePage);
					top.setCenter(hbox1);
					top.setRight(hbox2);

					borderPane.setTop(top);

				});

				addtocart.setVisible(true);
				menuBarLogin.setVisible(false);
				userMenu.getItems().addAll(goToCart,manageProfile,logout);
				userMenu.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("userIcon")).toExternalForm(), 15, 15, true, true)));
				menuBarUser.getMenus().add(userMenu);

				hbox1.setAlignment(Pos.CENTER);
				hbox2.getChildren().clear();
				hbox2.getChildren().addAll(addtocart);
				hbox2.getChildren().addAll(menuBarUser);

				top.setLeft(homePage);
				top.setCenter(hbox1);
				top.setRight(hbox2);


				borderPane.setTop(top);

			}

		});

		hbox1.getChildren().addAll(searchInput, searchBtn);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.getChildren().addAll(menuBarLogin);

		top.setLeft(homePage);
		top.setCenter(hbox1);
		top.setRight(hbox2);

		borderPane.setTop(top);

		user.readProducts();
		products.getChildren().add(user.loadProducts());
		borderPane.setCenter(products);

		HBox hbox3 = new HBox(5);
		hbox3.getChildren().addAll(output);
		hbox3.setAlignment(Pos.CENTER);
		borderPane.setBottom(hbox3);

		window.setScene(new Scene(borderPane, 500, 300, Color.BEIGE));
		window.show();

		window.setOnCloseRequest(e -> {
			e.consume();
			closeStore();

		});
	}

	public boolean clientArea() {
		PopUpBox.login(user);
		try {
			if(!user.getUsername().equals("")) {
				loginOrRegister=1;
				return true;
			}
		}catch(IndexOutOfBoundsException e){
			return false;
		}
		return false;

	}

	public boolean register() {
		PopUpBox.register(user);

		try {
			if(!user.getUsername().equals("")) {

				loginOrRegister=0;
				return true;

			}
		}catch(IndexOutOfBoundsException e){
			return false;
		}
		return false;

	}

	public boolean employeeArea() {
		PopUpBox.login(employee);
		try {
			if(!employee.getUsername().equals("")) {
				return true;
			}
		}catch(IndexOutOfBoundsException e){
			return false;
		}
		return false;
	}

	public void closeStore() {
		//if(PopUpBox.confirm("close store", "Exit store?"))
		window.close();
	}
}