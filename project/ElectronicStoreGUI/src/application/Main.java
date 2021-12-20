package application;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;



/**
 * 
 * the main class, where the software begins
 *
 */
public class Main extends Application{

	static Stage window;
	final int width = 760;
	final int height = 450;

	int loginOrRegister = 1;
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

	
	/**
	 * starting point
	 * 
	 * @param args console arguments
	 */
	public static void main(String[] args) {

		imagesPath = new HashMap<String, String>();
		imagesPath.put("logo", "/images/logo.png");
		imagesPath.put("login", "/images/login.png");
		imagesPath.put("home", "/images/homeEmployee.png");
		imagesPath.put("homeEmployee", "/images/homeEmployee.png");
		imagesPath.put("restock", "/images/restock.png");
		imagesPath.put("restockAdmin", "/images/restockAdmin.png");
		imagesPath.put("notification", "/images/notification.png");
		imagesPath.put("shipping", "/images/shipping.png");
		imagesPath.put("manageEmployees", "/images/manageEmployees.png");
		imagesPath.put("manageProductType", "/images/manageProducts.png");
		imagesPath.put("refresh", "/images/refresh.png");
		imagesPath.put("searchIcon", "/images/search.png");
		imagesPath.put("cartIcon", "/images/cart.png");
		imagesPath.put("userIcon", "/images/user.png");
		imagesPath.put("userMenu", "/images/userMenu.png");
		imagesPath.put("add", "/images/add.png");
		imagesPath.put("edit", "/images/edit.png");
		imagesPath.put("remove", "/images/remove.png");
		imagesPath.put("addAdmin", "/images/addAdmin.png");
		imagesPath.put("editAdmin", "/images/editAdmin.png");
		imagesPath.put("removeAdmin", "/images/removeAdmin.png");
		imagesPath.put("logout", "/images/logout.png");
		imagesPath.put("addtocart", "/images/addtocart.png");
		imagesPath.put("admin", "/images/admin.png");
		imagesPath.put("manageProfile", "/images/manageProfile.png");
		imagesPath.put("back", "/images/back.png");
		imagesPath.put("order", "/images/order.png");
		imagesPath.put("background", "/images/background.png");
		imagesPath.put("adminProfile", "/images/adminProfile.png");
		imagesPath.put("title","/images/titlebackground.png");

		launch(args);

	}

	TableView<ObservableList<String>> getTable;
	ArrayList<String> getSelectedItem;

	
	/**
	 * main window
	 * 
	 * @param primaryStage the primary stage
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void start(@SuppressWarnings("exports") Stage primaryStage) {

		window = primaryStage;
		window.getIcons().add(new Image(getClass().getResource(imagesPath.get("logo")).toExternalForm()));
		window.setTitle("UNIPR ONLINE ELECTRONIC STORE");

		BorderPane borderPane = new BorderPane();
		StackPane center = new StackPane();

		HBox searchPanel = new HBox();
		VBox borderPaneRight = new VBox();
		borderPaneRight.setId("right");
		VBox borderPaneLeft = new VBox();

		Button shipOrders = new Button("Ship Orders");
		shipOrders.setId("btnEmployeeArea");

		Button notifications = new Button("Notifications (" + employee.getNumberNotifications() + ")");
		notifications.setId("btnEmployeeArea");

		Button restock = new Button("Restock Products");
		restock.setId("btnEmployeeArea");

		Button manageEmployees = new Button("Manage Employees");
		manageEmployees.setId("btnEmployeeArea");

		Button manageProductType = new Button("Manage Product Type");
		manageProductType.setId("btnEmployeeArea");

		Label title = new Label("EMPLOYEE AREA");
		title.setId("titleEmployeeArea");

		Label numberOrders = new Label("");
		numberOrders.setId("numberOrders");

		Label numberEmployees = new Label("");
		numberEmployees.setId("numberEmployees");

		Label numberProducts = new Label("");
		numberProducts.setId("numberProducts");

		Label emptyLabel = new Label("");
		Label emptyLabel2 = new Label("");
		Label emptyLabel3 = new Label("");

		HBox hbox2 = new HBox(5);

		VBox vboxEmployeeAreaButtons = new VBox(5);
		vboxEmployeeAreaButtons.setAlignment(Pos.CENTER);

		VBox hboxAdminArea = new VBox(5);
		hboxAdminArea.setAlignment(Pos.CENTER);

		VBox vboxEmployeeArea = new VBox(5);
		vboxEmployeeArea.setAlignment(Pos.CENTER);
		vboxEmployeeArea.getChildren().addAll(numberOrders,shipOrders);

		VBox hboxAdminArea2 = new VBox(5);
		hboxAdminArea2.setAlignment(Pos.CENTER);
		hboxAdminArea2.getChildren().addAll(numberEmployees,manageEmployees);

		BorderPane top = new BorderPane();
		top.setId("top");

		BorderPane bottom = new BorderPane();
		bottom.setId("bottomHome");

		BorderPane employeeAreaCenter = new BorderPane();
		BorderPane adminAreaCenter = new BorderPane();

		vboxEmployeeAreaButtons.getChildren().addAll(emptyLabel, notifications, emptyLabel2, restock);
		hboxAdminArea.getChildren().addAll(emptyLabel3, numberProducts,manageProductType);

		vboxEmployeeArea.getChildren().add(vboxEmployeeAreaButtons);
		hboxAdminArea2.getChildren().add(hboxAdminArea);

		employeeAreaCenter.setCenter(vboxEmployeeArea);
		adminAreaCenter.setCenter(hboxAdminArea2);

		MenuBar menuBarLogin = new MenuBar();

		Menu loginMenu = new Menu("Login/Register");
		loginMenu.setId("loginRegister");
		loginMenu.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("login")).toExternalForm(), 40, 40, true, true)));

		Menu loginUsers = new Menu("Users");
		loginUsers.setId("usersLogin");
		loginUsers.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("userMenu")).toExternalForm(), 30, 15, true, true)));

		MenuItem signIn = new MenuItem("Sign in");
		signIn.setId("menu");

		MenuItem signUp = new MenuItem("Sign up");
		signUp.setId("menu");

		loginUsers.getItems().addAll(signIn , signUp);

		MenuItem loginEmployees = new MenuItem("Employees");
		loginEmployees.setId("menu");
		loginEmployees.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("admin")).toExternalForm(), 30, 15, true, true)));

		loginMenu.getItems().addAll(loginUsers,loginEmployees);
		menuBarLogin.getMenus().add(loginMenu);

		Label output = new Label("");
		output.setId("output");

		Button back = new Button("Back");
		back.setId("btnBottom");
		back.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("back")).toExternalForm(), 30, 30, true, true)));

		Button placeOrder = new Button("Place Order");
		placeOrder.setId("btnBottom");
		placeOrder.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("order")).toExternalForm(), 30, 30, true, true)));

		Button removeItemAdmin = new Button("Remove Item");
		removeItemAdmin.setId("btnBottom");
		removeItemAdmin.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("removeAdmin")).toExternalForm(), 30, 30, true, true)));

		Button addItemAdmin = new Button("Add Item");
		addItemAdmin.setId("btnBottom");
		addItemAdmin.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("addAdmin")).toExternalForm(), 30, 30, true, true)));

		Button editItemAdmin = new Button("Edit Item");
		editItemAdmin.setId("btnBottom");
		editItemAdmin.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("editAdmin")).toExternalForm(), 30, 30, true, true)));

		Button removeEmployeeAdmin = new Button("Remove Account");
		removeEmployeeAdmin.setId("btnBottom");
		removeEmployeeAdmin.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("removeAdmin")).toExternalForm(), 30, 30, true, true)));

		Button addEmployeeAdmin = new Button("Add Account");
		addEmployeeAdmin.setId("btnBottom");
		addEmployeeAdmin.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("addAdmin")).toExternalForm(), 30, 30, true, true)));

		Button editEmployeeAdmin = new Button("Edit Account");
		editEmployeeAdmin.setId("btnBottom");
		editEmployeeAdmin.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("editAdmin")).toExternalForm(), 30, 30, true, true)));

		Button homePage = new Button("Home");
		homePage.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("home")).toExternalForm(), 40, 40, true, true)));

		BackgroundImage myBIHome= new BackgroundImage(new Image(getClass().getResource(imagesPath.get("background")).toExternalForm(),300,300,true,true),
				BackgroundRepeat.ROUND, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		borderPane.setBackground(new Background(myBIHome));

		Button searchBtn = new Button("search");
		searchBtn.setDisable(true);
		searchBtn.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("searchIcon")).toExternalForm(), 15, 15, true, true)));
		searchBtn.setAlignment(Pos.CENTER);

		TextField searchInput = new TextField();
		searchInput.textProperty().addListener((observer) -> searchBtn.setDisable(!(searchInput.getText().trim().length() > 0)));
		searchInput.setPromptText("search product");
		searchInput.setAlignment(Pos.CENTER);
		searchInput.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				searchBtn.fire();

		} );

		loginEmployees.setOnAction( eventLoginEmployee -> {

			if(!passed) {
				if(backToEmployeeArea)
					passed =true;

				else if(!backToEmployeeArea)
					if(employeeArea())
						passed=true;
					else
						passed = false;
			}
			if(passed) {

				center.setId("centerEmployee");

				borderPane.getChildren().remove(borderPane.getRight());
				title.setText("EMPLOYEE AREA");

				homePage.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("refresh")).toExternalForm(), 40, 40, true, true)));
				homePage.setText("Refresh");

				homePage.setOnAction(e -> {

					output.setText("");
					removeItemAdmin.setVisible(false);
					addItemAdmin.setVisible(false);
					editItemAdmin.setVisible(false);
					removeEmployeeAdmin.setVisible(false);
					addEmployeeAdmin.setVisible(false);
					editEmployeeAdmin.setVisible(false);
					user.readProducts();
					loginEmployees.fire();
				});
				center.getChildren().clear();

				MenuBar menuBarEmployee = new MenuBar();
				Menu employeeMenu = new Menu();
				employeeMenu.setId("adminMenu");

				Label temp = new Label(employee.getUsername());
				temp.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("adminProfile")).toExternalForm(), 40, 40, true, true)));

				employeeMenu.setGraphic(temp);

				MenuItem adminArea = new MenuItem("Admin Area");
				adminArea.setId("menu");
				adminArea.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("admin")).toExternalForm(), 30, 15, true, true)));

				adminArea.setOnAction( eventAdminArea ->{

					title.setText("ADMIN AREA");

					homePage.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("refresh")).toExternalForm(), 40, 40, true, true)));
					homePage.setText("Refresh");
					output.setText("");
					removeItemAdmin.setVisible(false);
					addItemAdmin.setVisible(false);
					editItemAdmin.setVisible(false);
					removeEmployeeAdmin.setVisible(false);
					addEmployeeAdmin.setVisible(false);
					editEmployeeAdmin.setVisible(false);

					if(!backToEmployeeArea) {

						admin.login(employee.getUsername(), employee.getPassword());
						adminArea.setText("Employee Area");
						center.getChildren().clear();
						center.getChildren().add(adminAreaCenter);

						homePage.setOnAction( e -> {

							output.setText("");
							removeItemAdmin.setVisible(false);
							addItemAdmin.setVisible(false);
							editItemAdmin.setVisible(false);
							removeEmployeeAdmin.setVisible(false);
							addEmployeeAdmin.setVisible(false);
							editEmployeeAdmin.setVisible(false);
							backToEmployeeArea=false;
							adminArea.fire();

						} );

						backToEmployeeArea=true;

					}

					else if(backToEmployeeArea) {

						homePage.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("refresh")).toExternalForm(), 40, 40, true, true)));
						homePage.setText("Refresh");
						admin.login(employee.getUsername(), employee.getPassword());
						adminArea.setText("Admin Area");
						center.getChildren().clear();
						center.getChildren().add(adminAreaCenter);

						homePage.setOnAction(e -> {

							output.setText("");
							removeItemAdmin.setVisible(false);
							addItemAdmin.setVisible(false);
							editItemAdmin.setVisible(false);
							removeEmployeeAdmin.setVisible(false);
							addEmployeeAdmin.setVisible(false);
							editEmployeeAdmin.setVisible(false);

						});

						loginEmployees.fire();
						backToEmployeeArea=false;

					}
					numberEmployees.setText("There are currently " + employee.getNumberEmployees() + " employees.");
					numberProducts.setText("There are currently " + employee.getNumberProducts() + " product types in the warehouse.");

					manageProductType.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("manageProductType")).toExternalForm(), 30, 30, true, true)));

					manageProductType.setOnAction( eventManageProducts -> {

						title.setText("MANAGE PRODUCTS");

						homePage.setText("Home");
						homePage.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("homeEmployee")).toExternalForm(), 40, 40, true, true)));
						removeItemAdmin.setVisible(true);
						addItemAdmin.setVisible(true);
						editItemAdmin.setVisible(true);
						center.getChildren().clear();
						admin.readAllProducts();
						center.getChildren().add(admin.loadProducts());
						HBox hbox3 = new HBox(5);
						hbox3.getChildren().addAll(addItemAdmin,editItemAdmin,removeItemAdmin);
						hbox3.setAlignment(Pos.CENTER);
						HBox hbox4 = new HBox(5);
						hbox4.getChildren().add(output);
						hbox4.setAlignment(Pos.CENTER);
						bottom.setRight(hbox4);
						bottom.setLeft(hbox3);

						borderPane.setBottom(bottom);

						removeItemAdmin.setOnAction( eventRemoveItemAdmin ->{

							try {

								output.setText("");
								getTable = (TableView<ObservableList<String>>) center.getChildren().get(0);
								getSelectedItem = new ArrayList<>(getTable.getItems().get(getTable.getSelectionModel().getSelectedIndex()));

								if(PopUpBox.confirm("WARNING", "Do you really want to remove\n" + getSelectedItem.get(2) + " " + getSelectedItem.get(1) + "?")) {

									admin.removeProduct(getSelectedItem.get(0));
									center.getChildren().clear();
									admin.readAllProducts();
									center.getChildren().add(admin.loadProducts());
									output.setText(getSelectedItem.get(2) + " " + getSelectedItem.get(1) + " has been removed succesfully.  ");
									output.setTextFill(Color.GREEN);

								}

							}catch(IndexOutOfBoundsException e) {

								output.setText("Please select an item you wish to remove!  ");
								output.setTextFill(Color.RED);

							}

						} );

						editItemAdmin.setOnAction( eventEditItemAdmin ->{

							try {

								output.setText("");
								getTable = (TableView<ObservableList<String>>) center.getChildren().get(0);
								getSelectedItem = new ArrayList<>(getTable.getItems().get(getTable.getSelectionModel().getSelectedIndex()));

								ArrayList<String> data = new ArrayList<String>();
								data.add(getSelectedItem.get(0));
								data.add(getSelectedItem.get(1));
								data.add(getSelectedItem.get(2));
								data.add(getSelectedItem.get(3));
								data.add(getSelectedItem.get(4));

								if(PopUpBox.editProductType(admin, data)) {

									center.getChildren().clear();
									admin.readAllProducts();
									center.getChildren().add(admin.loadProducts());
									output.setText("Product edited succesfully!  ");
									output.setTextFill(Color.GREEN);

								}

							}catch(IndexOutOfBoundsException e) {

								output.setText("Please select an item you wish to edit!  ");
								output.setTextFill(Color.RED);

							}

						} );

						addItemAdmin.setOnAction( eventAddItemAdmin ->{

							output.setText("");

							if(PopUpBox.addProduct(admin)) {
								center.getChildren().clear();
								admin.readAllProducts();
								center.getChildren().add(admin.loadProducts());
								output.setText("Product added succesfully!  ");
								output.setTextFill(Color.GREEN);
							}

						} );

					} );

					manageEmployees.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("manageEmployees")).toExternalForm(), 30, 30, true, true)));
					manageEmployees.setOnAction( eventManageEmployees -> {

						title.setText("MANAGE EMPLOYEES");

						homePage.setText("Home");
						homePage.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("homeEmployee")).toExternalForm(), 40, 40, true, true)));
						removeEmployeeAdmin.setVisible(true);
						addEmployeeAdmin.setVisible(true);
						editEmployeeAdmin.setVisible(true);
						center.getChildren().clear();
						admin.readAllEmployees();
						center.getChildren().add(admin.loadEmployees());

						HBox hbox3 = new HBox(5);
						hbox3.getChildren().addAll(addEmployeeAdmin,editEmployeeAdmin,removeEmployeeAdmin);
						hbox3.setAlignment(Pos.CENTER);
						HBox hbox4 = new HBox(5);
						hbox4.getChildren().add(output);
						hbox4.setAlignment(Pos.CENTER);
						bottom.setRight(hbox4);
						bottom.setLeft(hbox3);

						borderPane.setBottom(bottom);

						removeEmployeeAdmin.setOnAction( eventRemoveEmployeeAdmin ->{

							output.setText("");

							try {

								getTable = (TableView<ObservableList<String>>) center.getChildren().get(0);
								getSelectedItem = new ArrayList<>(getTable.getItems().get(getTable.getSelectionModel().getSelectedIndex()));
								if(admin.getUsername().equals(getSelectedItem.get(2))) {
									if(PopUpBox.confirm("WARNING", "Do you really want to remove YOUR account? \n(" + getSelectedItem.get(2) + ")"))
									{
										admin.removeEmployee(getSelectedItem.get(2));
										PopUpBox.alert("alert", getSelectedItem.get(2) +"'s account deleted.", 300);
										backToEmployeeArea=false;
										loginOrRegister = 1;
										passed=false;
										restart(window);
									}
								}
								else if(PopUpBox.confirm("WARNING", "Do you really want to remove\n" + getSelectedItem.get(2) + "'s account?")) {

									admin.removeEmployee(getSelectedItem.get(2));
									center.getChildren().clear();
									admin.readAllEmployees();
									center.getChildren().add(admin.loadEmployees());
									output.setText(getSelectedItem.get(2) +"'s account deleted.");
									output.setTextFill(Color.GREEN);

								}

							}catch(IndexOutOfBoundsException e) {

								output.setText("Select an account you wish to remove!  ");
								output.setTextFill(Color.RED);

							}

						} );

						editEmployeeAdmin.setOnAction( eventEditItemAdmin ->{

							output.setText("");

							try {

								getTable = (TableView<ObservableList<String>>) center.getChildren().get(0);
								getSelectedItem = new ArrayList<>(getTable.getItems().get(getTable.getSelectionModel().getSelectedIndex()));

								ArrayList<String> data = new ArrayList<String>();
								data.add(getSelectedItem.get(0));
								data.add(getSelectedItem.get(1));
								data.add(getSelectedItem.get(2));
								data.add(getSelectedItem.get(3));
								data.add(getSelectedItem.get(4));

								if(PopUpBox.editEmployee(admin, data)) {
									if(PopUpBox.resultManageProfile_GET() == 2) {
										System.out.println(PopUpBox.resultManageProfile_GET());
										admin.logout();
										employee.setAdminshipFalse();
										backToEmployeeArea=true;
										loginEmployees.fire();
									}
									else {
										center.getChildren().clear();
										admin.readAllEmployees();
										center.getChildren().add(admin.loadEmployees());
										output.setText("Account edited succesfully!  ");
										output.setTextFill(Color.GREEN);
									}
								}
							}catch(IndexOutOfBoundsException e) {

								output.setText("Select an account you wish to edit!  ");
								output.setTextFill(Color.RED);

							}
						} );

						addEmployeeAdmin.setOnAction( eventAddEmployeeAdmin ->{

							output.setText("");

							if(PopUpBox.addEmployee(admin)) {

								center.getChildren().clear();
								admin.readAllEmployees();
								center.getChildren().add(admin.loadEmployees());
								output.setText("Account created successfully.  ");
								output.setTextFill(Color.GREEN);

							}

						} );

					} );

				} );

				numberOrders.setText(employee.getNumberOrders() + " orders to ship.");

				shipOrders.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("shipping")).toExternalForm(), 30, 30, true, true)));
				shipOrders.setOnAction( eventShipOrders ->{

					title.setText("SHIPPING ORDERS");

					homePage.setText("Home");
					homePage.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("homeEmployee")).toExternalForm(), 40, 40, true, true)));

					employee.readOrders();
					center.getChildren().clear();
					ArrayList<ArrayList<String>> orders = employee.getOrders();

					StackPane info = new StackPane();

					if(orders.size() == 0) {

						Label msgLabel = new Label("No Orders to Ship.");
						info.getChildren().addAll(msgLabel);
						info.setAlignment(Pos.CENTER);
						center.getChildren().add(info);

					}
					else {
						@SuppressWarnings("rawtypes")
						ListView ordersLayout = new ListView();

						for (int i = 0; i < orders.size(); i++) {

							final int index = i;
							BorderPane order = new BorderPane();

							Label orderid = new Label("ORDER " + orders.get(i).get(0));
							order.setLeft(orderid);


							VBox details = new VBox();
							Label client = new Label("Client: " + orders.get(i).get(1));
							Label address = new Label("Address: " + orders.get(i).get(2));
							details.getChildren().addAll(client, address);
							details.setAlignment(Pos.CENTER);
							order.setCenter(details);

							Button viewOrder = new Button("view ORDER " + orders.get(i).get(0));
							viewOrder.setId("buttonTable");
							viewOrder.setOnAction(e -> {
								employee.displayOrder(index);
								shipOrders.fire();
							});
							order.setRight(viewOrder);

							ordersLayout.getItems().add(order);

						}
						center.getChildren().add(ordersLayout);


					}
				} );

				notifications.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("notification")).toExternalForm(), 30, 30, true, true)));
				notifications.setText("Notifications (" + employee.getNumberNotifications() + ")");
				notifications.setOnAction( eventNotifications ->{

					title.setText("OUT OF STOCK PRODUCTS");

					homePage.setText("Home");
					homePage.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("homeEmployee")).toExternalForm(), 40, 40, true, true)));

					center.getChildren().clear();

					TableView<ObservableList<String>> table = employee.getNotifications();
					TableColumn<ObservableList<String>, Void> colBtn = new TableColumn<ObservableList<String>, Void>();
					Callback<TableColumn<ObservableList<String>, Void>, TableCell<ObservableList<String>, Void>> cellFactory = new Callback<TableColumn<ObservableList<String>, Void>, TableCell<ObservableList<String>, Void>>() {
						@Override
						public TableCell<ObservableList<String>, Void> call(final TableColumn<ObservableList<String>, Void> param) {

							final TableCell<ObservableList<String>, Void> cell = new TableCell<ObservableList<String>, Void>() {

								private final Button btn = new Button("ReStock");
								{
									btn.setId("buttonTable");
									btn.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("restock")).toExternalForm(), 15, 15, true, true)));
									btn.setOnAction( e -> {
										ObservableList<String> data = getTableView().getItems().get(getIndex());
										PopUpBox.restock(employee, data, false);
										notifications.fire();
									});

								}


								@Override
								public void updateItem(Void item, boolean empty) {

									super.updateItem(item, empty);

									if (empty)
										setGraphic(null);
									else
										setGraphic(btn);

								}

							};

							return cell;
						}

					};

					colBtn.setCellFactory(cellFactory);
					table.getColumns().add(colBtn);
					center.getChildren().add(table);

				});

				restock.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("restockAdmin")).toExternalForm(), 30, 30, true, true)));
				restock.setOnAction( eventRestock ->{
					
					title.setText("PRODUCTS IN STOCK");
					
					homePage.setText("Home");
					homePage.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("homeEmployee")).toExternalForm(), 40, 40, true, true)));
					
					center.getChildren().clear();
					TableView<ObservableList<String>> tableView = employee.getProducts();

					TableColumn<ObservableList<String>, Void> colBtn = new TableColumn<ObservableList<String>, Void>();

					Callback<TableColumn<ObservableList<String>, Void>, TableCell<ObservableList<String>, Void>> cellFactory = new Callback<TableColumn<ObservableList<String>, Void>, TableCell<ObservableList<String>, Void>>() {
						@Override
						public TableCell<ObservableList<String>, Void> call(final TableColumn<ObservableList<String>, Void> param) {
							final TableCell<ObservableList<String>, Void> cell = new TableCell<ObservableList<String>, Void>() {

								private final Button btn = new Button("Edit Quantity");

								{
									btn.setId("buttonTable");
									btn.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("restock")).toExternalForm(), 15, 15, true, true)));                    	
									btn.setOnAction(e -> {
										ObservableList<String> getSelectedItem = getTableView().getItems().get(getIndex());
										PopUpBox.restock(employee, getSelectedItem, true);
										restock.fire();
									});

								}

								@Override
								public void updateItem(Void item, boolean empty) {
									super.updateItem(item, empty);
									if (empty) {
										setGraphic(null);
									} else {
										setGraphic(btn);
									}
								}
							};
							return cell;
						}
					};

					colBtn.setCellFactory(cellFactory);
					tableView.getColumns().add(colBtn);

					center.getChildren().add(tableView);
				});


				MenuItem logout = new MenuItem("Logout");
				logout.setId("menu");
				logout.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("logout")).toExternalForm(), 30, 15, true, true)));

				searchBtn.setVisible(false);
				searchInput.setVisible(false);

				logout.setOnAction( eventLogout -> {
					if(PopUpBox.confirm("Staff Logout", "Confirm Sign out?")) {
						center.setId("centerHome");
						employee.logout();
						backToEmployeeArea=false;
						loginOrRegister = 1;
						passed=false;
						restart(window);
					}
				});				

				menuBarLogin.setVisible(false);

				if(employee.isAdmin()) {

					employeeMenu.getItems().addAll(adminArea);

				}

				employeeMenu.getItems().addAll(logout);
				menuBarEmployee.getMenus().add(employeeMenu);
				center.getChildren().add(employeeAreaCenter);
				hbox2.getChildren().clear();
				hbox2.getChildren().addAll(menuBarEmployee);
				top.setLeft(homePage);
				top.setCenter(title);
				top.setRight(hbox2);

				borderPane.setTop(top);

			}
		});

		signUp.setOnAction(eventRegister -> {

			if(register())
				signIn.fire();

		});

		signIn.setOnAction(eventLogin -> {

			if(loginOrRegister == 0)
				passed = true;

			else if(loginOrRegister == 1)
				if(clientArea())
					passed = true;
				else
					passed = false;

			if(passed == true) {
				center.getChildren().clear();
				center.getChildren().add(user.loadProducts());

				MenuBar menuBarUser = new MenuBar();
				Menu userMenu = new Menu(user.getUsername());
				userMenu.setId("loginRegister");
				userMenu.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("userIcon")).toExternalForm(), 15, 15, true, true)));

				MenuItem goToCart = new MenuItem("Go to Cart");
				goToCart.setId("menu");
				goToCart.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("cartIcon")).toExternalForm(), 30, 15, true, true)));

				MenuItem manageProfile = new MenuItem("Manage Profile");
				manageProfile.setId("menu");
				manageProfile.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("manageProfile")).toExternalForm(), 30, 15, true, true)));

				MenuItem logout = new MenuItem("Logout");
				logout.setId("menu");
				logout.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("logout")).toExternalForm(), 30, 15, true, true)));


				goToCart.setOnAction( eventCart -> {

					center.getChildren().clear();
					searchPanel.setVisible(false);
					borderPane.getChildren().remove(borderPane.getRight());
					user.cart();
					TableView<ObservableList<String>> table = user.loadCart();
					table.setPlaceholder(new Label("No Product in Cart"));
					table.setSelectionModel(null);


					TableColumn<ObservableList<String>, Void> removeCol = new TableColumn<ObservableList<String>, Void>();
					TableColumn<ObservableList<String>, Void> editCol = new TableColumn<ObservableList<String>, Void>();

					Callback<TableColumn<ObservableList<String>, Void>, TableCell<ObservableList<String>, Void>> cellFactoryRemove = new Callback<TableColumn<ObservableList<String>, Void>, TableCell<ObservableList<String>, Void>>() {
						@Override
						public TableCell<ObservableList<String>, Void> call(final TableColumn<ObservableList<String>, Void> param) {
							final TableCell<ObservableList<String>, Void> cell = new TableCell<ObservableList<String>, Void>() {

								private final Button btn = new Button("Remove");

								{
									btn.setId("buttonTable");
									btn.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("remove")).toExternalForm(), 15, 15, true, true)));
									btn.setOnAction(e -> {
										ObservableList<String> getSelectedItem = getTableView().getItems().get(getIndex());
										if(PopUpBox.confirm("WARNING", "Do you really want to remove\n " + getSelectedItem.get(3) + " "+ getSelectedItem.get(1) + " from your cart?")) {

											if(user.removeProduct(getSelectedItem.get(0))) {
												PopUpBox.alert("success", getSelectedItem.get(1) + " removed.", 250);
												goToCart.fire();

											}
										}
									});
								}

								@Override
								public void updateItem(Void item, boolean empty) {
									super.updateItem(item, empty);
									if (empty) {
										setGraphic(null);
									} else {
										setGraphic(btn);
									}
								}
							};
							return cell;
						}
					};

					Callback<TableColumn<ObservableList<String>, Void>, TableCell<ObservableList<String>, Void>> cellFactoryEdit = new Callback<TableColumn<ObservableList<String>, Void>, TableCell<ObservableList<String>, Void>>() {
						@Override
						public TableCell<ObservableList<String>, Void> call(final TableColumn<ObservableList<String>, Void> param) {
							final TableCell<ObservableList<String>, Void> cell = new TableCell<ObservableList<String>, Void>() {

								private final Button btn = new Button("Quantity");

								{
									btn.setId("buttonTable");
									btn.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("edit")).toExternalForm(), 15, 15, true, true)));
									btn.setOnAction(e -> {
										ObservableList<String> getSelectedItem = getTableView().getItems().get(getIndex());
										if(PopUpBox.editQuantityCart(user, getSelectedItem.get(0))) {
											PopUpBox.alert("success", "Quantity edited successfully.", 250);
											goToCart.fire();
										}
									});
								}

								@Override
								public void updateItem(Void item, boolean empty) {
									super.updateItem(item, empty);
									if (empty) {
										setGraphic(null);
									} else {
										setGraphic(btn);
									}
								}
							};
							return cell;
						}
					};

					removeCol.setCellFactory(cellFactoryRemove);
					table.getColumns().add(removeCol);

					editCol.setCellFactory(cellFactoryEdit);
					table.getColumns().add(editCol);

					Label totalPrice = new Label("Total Price: $" + user.getTotalPrice());
					totalPrice.setId("labelTotalPrice");
					totalPrice.setUnderline(true);
					totalPrice.setAlignment(Pos.CENTER);
					VBox vbox = new VBox();
					vbox.getChildren().addAll(table, totalPrice);
					vbox.setAlignment(Pos.CENTER);

					center.getChildren().add(vbox);	



					back.setVisible(true);
					back.setOnAction( backEvent ->{homePage.fire();} );

					placeOrder.setVisible(true);
					placeOrder.setOnAction( orderEvent ->{

						if(user.getAddress().equals("none")) {

							PopUpBox.placeOrder(user);
							goToCart.fire();

						}
						else {


							user.order();
							goToCart.fire();

						}

					} );

					HBox hbox3 = new HBox(5);
					HBox hbox4 = new HBox(5);
					HBox hbox5 = new HBox(5);
					hbox3.getChildren().add(back);
					hbox3.setAlignment(Pos.CENTER);

					hbox4.getChildren().add(output);
					hbox4.setAlignment(Pos.CENTER);

					hbox5.getChildren().add(placeOrder);
					hbox5.setAlignment(Pos.CENTER);

					bottom.setLeft(hbox3);
					bottom.setCenter(hbox4);
					bottom.setRight(hbox5);

					borderPane.setBottom(bottom);

				});

				manageProfile.setOnAction( eventProfile ->{

					int result = PopUpBox.manageProfile(user);

					if(result == 1)
						userMenu.setText(user.getUsername());

					if(result == 2) {
						loginOrRegister = 1;
						passed=false;
						user.logout();
						restart(window);
					}

				} );


				logout.setOnAction( eventLogout ->{
					if(PopUpBox.confirm("client logout", "Confirm Sign out?")) {
						loginOrRegister = 1;
						passed=false;
						user.logout();
						restart(window);
					}
				} );

				menuBarLogin.setVisible(false);

				userMenu.getItems().addAll(goToCart,manageProfile,logout);
				userMenu.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("userIcon")).toExternalForm(), 40, 40, true, true)));
				menuBarUser.getMenus().add(userMenu);

				hbox2.getChildren().clear();
				hbox2.getChildren().addAll(menuBarUser);

				top.setLeft(homePage);
				top.setCenter(searchPanel);
				top.setRight(hbox2);

				borderPane.setTop(top);

			}

		} );

		searchPanel.getChildren().addAll(searchInput, searchBtn);
		searchPanel.setAlignment(Pos.CENTER);
		hbox2.getChildren().addAll(menuBarLogin);
		top.setLeft(homePage);
		top.setCenter(searchPanel);
		top.setRight(hbox2);

		borderPane.setTop(top);

		user.readProducts();
		center.getChildren().add(user.loadProducts());
		borderPane.setCenter(center);

		Menu priceFiltersMenu = new Menu("MIN & MAX PRICE");

		priceFiltersMenu.setId("filterManufacturer");

		float minPrice = user.getPrice("min");
		float maxPrice = user.getPrice("max");
		Slider minSlider = new Slider(minPrice, maxPrice, minPrice);
		Slider maxSlider = new Slider(minPrice, maxPrice, maxPrice);

		minSlider.setShowTickLabels(true);
		minSlider.setBlockIncrement(50);
		CustomMenuItem customMenuItemMin = new CustomMenuItem();
		customMenuItemMin.setContent(minSlider);
		customMenuItemMin.setHideOnClick(false);
		priceFiltersMenu.getItems().add(customMenuItemMin);

		DecimalFormat df = new DecimalFormat("#.##");
		Label min = new Label("Min. Price: $" + df.format(minSlider.getValue()));
		min.setId("filterLabel");

		CustomMenuItem customMenuItemMin2 = new CustomMenuItem();
		customMenuItemMin2.setContent(min);
		customMenuItemMin2.setHideOnClick(false);
		priceFiltersMenu.getItems().add(customMenuItemMin2);

		minSlider.valueProperty().addListener((ob, o, n) -> {
			user.searchProducts("minMax",  df.format(maxSlider.getValue()), df.format(n));
			center.getChildren().clear();
			center.getChildren().add(user.loadProducts());
			min.setText("Min. Price: $" + df.format(n));
		});


		maxSlider.setShowTickLabels(true);
		maxSlider.setBlockIncrement(50);
		CustomMenuItem customMenuItemMax = new CustomMenuItem();
		customMenuItemMax.setContent(maxSlider);
		customMenuItemMax.setHideOnClick(false);
		priceFiltersMenu.getItems().add(customMenuItemMax);

		Label max = new Label("Max. Price: $" + df.format(maxSlider.getValue()));
		max.setId("filterLabel");
		CustomMenuItem customMenuItemMax2 = new CustomMenuItem();
		customMenuItemMax2.setContent(max);
		customMenuItemMax2.setHideOnClick(false);
		priceFiltersMenu.getItems().add(customMenuItemMax2);

		maxSlider.valueProperty().addListener((ob, o, n) -> {
			user.searchProducts("minMax", df.format(n), df.format(minSlider.getValue()));
			center.getChildren().clear();
			center.getChildren().add(user.loadProducts());
			max.setText("Max. Price: $" + df.format(n));
		});

		searchBtn.setOnAction( e -> {

			String str = searchInput.getText();

			if ((!str.trim().equals(""))) {
				user.readProducts();
				center.getChildren().clear();
				user.searchProducts("string", str, "");
				center.getChildren().add(user.loadProducts());
			}

			user.resetFilteredProductsMinMAx();
			float minPrice2 = user.getPrice("min");
			float maxPrice2 = user.getPrice("max");

			minSlider.setMin(minPrice2);
			minSlider.setMax(maxPrice2);
			minSlider.setValue(minPrice2);
			maxSlider.setMin(minPrice2);
			maxSlider.setMax(maxPrice2);
			maxSlider.setValue(maxPrice2);

		} );

		Menu filterManufacturer = new Menu("MANUFACTURERS");
		filterManufacturer.setId("filterManufacturer");
		HashSet<String> manufacturers = new HashSet<>(user.getManufacturers());
		ArrayList<CheckBox> checkBoxItems = new ArrayList<CheckBox>();
		for(String m : manufacturers) {
			CheckBox checkBoxItem = new CheckBox(m);
			CustomMenuItem customMenuItemManufacturer = new CustomMenuItem();
			customMenuItemManufacturer.setContent(checkBoxItem);
			customMenuItemManufacturer.setHideOnClick(false);
			checkBoxItems.add(checkBoxItem);
			filterManufacturer.getItems().add(customMenuItemManufacturer);
			checkBoxItem.selectedProperty().addListener((ob, o, n) -> {
				if(n)
					user.searchProducts("manufacturerSelected", m, "");
				else
					user.searchProducts("manufacturerUnselected", m, "");
				center.getChildren().clear();
				center.getChildren().add(user.loadProducts());

				user.resetFilteredProductsMinMAx();
				float minPrice3 = user.getPrice("min");
				float maxPrice3 = user.getPrice("max");

				minSlider.setMin(minPrice3);
				minSlider.setMax(maxPrice3);
				minSlider.setValue(minPrice3);
				maxSlider.setMin(minPrice3);
				maxSlider.setMax(maxPrice3);
				maxSlider.setValue(maxPrice3);
			});
		}

		MenuBar filtersPriceMenuBar = new MenuBar();
		filtersPriceMenuBar.setId("filtersMenuBar");
		filtersPriceMenuBar.getMenus().add(priceFiltersMenu);

		MenuBar filtersManufacturerMenuBar = new MenuBar();
		filtersManufacturerMenuBar.setId("filtersMenuBar");
		filtersManufacturerMenuBar.getMenus().add(filterManufacturer);

		Button showAllProducts = new Button("SHOW ALL PRODUCTS");
		showAllProducts.setId("showAll");
		showAllProducts.setOnAction(e -> homePage.fire());

		borderPaneRight.setPadding(new Insets(50, 10, 10, 20));
		minSlider.setMaxWidth(100);
		maxSlider.setMaxWidth(100);

		Label emptySpace = new Label("");
		Label emptySpace2 = new Label("");
		Label emptySpace3 = new Label("");

		Label titleFilters = new Label("");
		titleFilters.setId("filterTitle");
		titleFilters.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("title")).toExternalForm(), 100, 100, true, true)));

		borderPaneRight.getChildren().addAll(titleFilters, emptySpace, filtersPriceMenuBar, emptySpace2, filtersManufacturerMenuBar, emptySpace3, showAllProducts);
		borderPaneRight.setAlignment(Pos.TOP_CENTER);
		borderPane.setRight(borderPaneRight);
		borderPane.setLeft(borderPaneLeft);

		homePage.setOnAction( e -> {
			center.getChildren().clear();
			for(CheckBox m : checkBoxItems)
				((CheckBox) m).setSelected(false);
			user.resetFilteredProductsManufacturer();
			user.resetFilteredProductsMinMAx();
			user.readProducts();
			float minPrice4 = user.getPrice("min");
			float maxPrice4 = user.getPrice("max");
			minSlider.setMin(minPrice4);
			minSlider.setMax(maxPrice4);
			minSlider.setValue(minPrice4);
			maxSlider.setMin(minPrice4);
			maxSlider.setMax(maxPrice4);
			maxSlider.setValue(maxPrice4);

			center.getChildren().add(user.loadProducts());

			output.setText("");
			back.setVisible(false);
			placeOrder.setVisible(false);
			removeItemAdmin.setVisible(false);
			searchPanel.setVisible(true);
			borderPane.setRight(borderPaneRight);

			HBox hbox3 = new HBox(5);
			hbox3.getChildren().addAll(output);
			hbox3.setAlignment(Pos.CENTER);

			borderPane.setBottom(hbox3);

		} );


		center.setPadding(new Insets(10));
		center.setId("centerHome");
		Scene scene = new Scene(borderPane, width, height, Color.WHITE);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.show();
		window.setOnCloseRequest(e -> {

			e.consume();
			closeStore();

		});

	}

	/**
	 * @return returns true if the sign in is successful
	 */
	public boolean clientArea() {

		PopUpBox.login(user);

		try {

			if(user.session()) {

				loginOrRegister = 1;
				return true;

			}
		}catch(IndexOutOfBoundsException e){

			return false;
		}

		return false;

	}

	/**
	 * @return returns true if the sign up is successful
	 */
	public boolean register() {

		PopUpBox.register(user);

		try {

			if(user.session()) {

				loginOrRegister = 0;
				return true;

			}
		}catch(IndexOutOfBoundsException e){

			return false;
		}

		return false;

	}

	/**
	 * @return returns true if the sign in is successful
	 */
	public boolean employeeArea() {

		PopUpBox.login(employee);

		try {

			if(employee.session())
				return true;

		}catch(IndexOutOfBoundsException e){

			return false;

		}

		return false;
	}

	/**
	 * restart the stage
	 * @param stage stage to be restarted
	 */
	void restart(Stage stage) {
		start(stage);
	}

	/**
	 * closes the program
	 */
	public void closeStore() {

		if(PopUpBox.confirm("WARNING", "Do you want to close the program?"))
			window.close();

	}
}