package application;


import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpBox {
	static Stage alertWindow;
	static boolean answer;
	static String user, pwd;

	public static void display(String title, String message) {
		alertWindow = new Stage();

		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle(title);
		alertWindow.setMinWidth(250);

		Label msgLabel = new Label(message);
		Button closeBtn = new Button("Close");
		closeBtn.setOnAction(e -> alertWindow.close());

		HBox layout = new HBox(10);
		layout.getChildren().addAll(msgLabel, closeBtn);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 200, 200);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
	}

	public static boolean confirm(String title, String message) {
		alertWindow = new Stage();

		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle(title);
		alertWindow.setMinWidth(100);

		Label msgLabel = new Label(message);

		Button yesBtn = new Button("YES");
		Button noBtn = new Button("NO");
		yesBtn.setOnAction(e -> {
			answer = true;
			alertWindow.close();
		});
		noBtn.setOnAction(e -> {
			answer = false;
			alertWindow.close();
		});

		HBox layout = new HBox(10);
		layout.getChildren().addAll(msgLabel, yesBtn, noBtn);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 200, 200);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();

		return answer;
	}

	public static void login(Employee employee) {
		alertWindow = new Stage();
		user = pwd = "";

		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle("LOGIN");
		alertWindow.setMinWidth(400);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(false); //DEBUG

		Label msgLabel = new Label("Enter Login Details");
		GridPane.setConstraints(msgLabel, 1, 0);

		Label usernameLabel = new Label("Username:");
		GridPane.setConstraints(usernameLabel, 0, 1);
		TextField username = new TextField(user);
		GridPane.setConstraints(username, 1, 1);

		Label passwordLabel = new Label("Password:");
		GridPane.setConstraints(passwordLabel, 0, 2);
		PasswordField password = new PasswordField();
		password.setPromptText("password");
		GridPane.setConstraints(password, 1, 2);

		final Label error = new Label("");
		GridPane.setConstraints(error, 1, 5);

		Button loginBtn = new Button("Login");
		GridPane.setConstraints(loginBtn, 1, 3);
		Button cancelBtn = new Button("Cancel");
		GridPane.setConstraints(cancelBtn, 2, 3);

		GridPane.setColumnSpan(username, 2);
		GridPane.setColumnSpan(password, 2);

		loginBtn.setOnAction(e -> {
			user = username.getText().trim();
			pwd =  password.getText().trim();
			if(employee.login(user, pwd)) {
				alertWindow.close();
			}
			else
				error.setText("Username or Password Incorrect!");
			error.setTextFill(Color.RED);
		});
		cancelBtn.setOnAction(e -> {
			user = pwd = "";
			alertWindow.close();
		});
		cancelBtn.setMaxWidth(Double.MAX_VALUE);

		grid.getChildren().addAll(msgLabel, usernameLabel, username, passwordLabel, password, loginBtn, cancelBtn, error);
		alertWindow.setResizable(false);
		Scene scene = new Scene(grid, 300, 200);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest(e -> {
			e.consume();
			user = pwd = "";
			alertWindow.close();
		});
	}


	public static void login(Client client) {
		alertWindow = new Stage();
		user = pwd = "";

		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle("LOGIN");
		alertWindow.setMinWidth(400);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(false); //DEBUG

		Label msgLabel = new Label("Enter Login Details");
		GridPane.setConstraints(msgLabel, 1, 0);

		Label usernameLabel = new Label("Username:");
		GridPane.setConstraints(usernameLabel, 0, 1);
		TextField username = new TextField(user);
		GridPane.setConstraints(username, 1, 1);

		Label passwordLabel = new Label("Password:");
		GridPane.setConstraints(passwordLabel, 0, 2);
		PasswordField password = new PasswordField();
		password.setPromptText("password");
		GridPane.setConstraints(password, 1, 2);

		final Label error = new Label("");
		GridPane.setConstraints(error, 1, 5);

		Button loginBtn = new Button("Login");
		GridPane.setConstraints(loginBtn, 1, 3);
		Button cancelBtn = new Button("Cancel");
		GridPane.setConstraints(cancelBtn, 2, 3);

		GridPane.setColumnSpan(username, 2);
		GridPane.setColumnSpan(password, 2);

		loginBtn.setOnAction(e -> {
			user = username.getText().trim();
			pwd =  password.getText().trim();
			if(client.Login(user, pwd)) {
				alertWindow.close();
			}
			else
				error.setText("Username or Password Incorrect!");
			error.setTextFill(Color.RED);
		});
		cancelBtn.setOnAction(e -> {
			user = pwd = "";
			alertWindow.close();
		});
		cancelBtn.setMaxWidth(Double.MAX_VALUE);

		grid.getChildren().addAll(msgLabel, usernameLabel, username, passwordLabel, password, loginBtn, cancelBtn, error);
		alertWindow.setResizable(false);
		Scene scene = new Scene(grid, 300, 200);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest(e -> {
			e.consume();
			user = pwd = "";
			alertWindow.close();
		});
	}


	@SuppressWarnings("static-access")
	public static void register(Client client) {
		alertWindow = new Stage();
		user = pwd = "";

		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle("CREATE ACCOUNT");
		alertWindow.setMinWidth(200);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label usernameLabel = new Label("Username:");
		GridPane.setConstraints(usernameLabel, 1, 4);
		TextField username = new TextField(user);
		GridPane.setConstraints(username, 1, 5);

		Label nameLabel = new Label("Name:");
		GridPane.setConstraints(nameLabel, 1, 0);
		TextField name = new TextField(user);
		GridPane.setConstraints(name, 1, 1);

		Label surnameLabel = new Label("Surname:");
		GridPane.setConstraints(surnameLabel, 1, 2);
		TextField surname = new TextField(user);
		GridPane.setConstraints(surname, 1, 3);

		Label passwordLabel = new Label("Password:");
		GridPane.setConstraints(passwordLabel, 1, 6);
		PasswordField password = new PasswordField();
		password.setPromptText("password");
		GridPane.setConstraints(password, 1, 7);

		PasswordField passwordRepeat = new PasswordField();
		passwordRepeat.setPromptText("repeat password");
		GridPane.setConstraints(passwordRepeat, 1, 8);

		final Label error = new Label("");
		GridPane.setConstraints(error, 1, 10);
		error.setTextFill(Color.RED);

		Button loginBtn = new Button("Sign up");
		GridPane.setConstraints(loginBtn, 1, 9);
		loginBtn.setOnAction(e -> {
			user = username.getText().trim();
			pwd =  password.getText().trim();
			if(!client.exist("user", username.getText().trim()) && !name.getText().trim().equals("") && !surname.getText().trim().equals("") && !username.getText().trim().equals("") && !password.getText().trim().equals("") && !passwordRepeat.getText().trim().equals("") && (password.getText().trim().equals(passwordRepeat.getText().trim()))) {

				client.registerNew(name.getText().trim(),surname.getText().trim(),username.getText().trim(),password.getText().trim());
				alertWindow.close();

			}
			else {
				if(client.exist("user", username.getText().trim()))
					error.setText("Username already taken. ");

				else if(!password.getText().trim().equals(passwordRepeat.getText().trim()))
					error.setText("The passwords do not match.");
				else
					error.setText("Please fill out all required fields.");

			}
		});

		grid.getChildren().addAll( nameLabel, name, surnameLabel, surname, usernameLabel, username, passwordLabel, password, passwordRepeat, loginBtn, error);
		alertWindow.setResizable(false);

		Scene scene = new Scene(grid, 210, 340);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest(e -> {
			e.consume();
			user = pwd = "";
			alertWindow.close();
		});
	}


	@SuppressWarnings("static-access")
	public static void addProduct(Admin admin) {
		alertWindow = new Stage();

		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle("ADD PRODUCT TYPE");
		alertWindow.setMinWidth(200);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label IDLabel = new Label("ID:");
		GridPane.setConstraints(IDLabel, 1, 4);
		TextField id = new TextField();
		GridPane.setConstraints(id, 1, 5);

		Label productLabel = new Label("Product:");
		GridPane.setConstraints(productLabel, 1, 0);
		TextField product = new TextField();
		GridPane.setConstraints(product, 1, 1);

		Label manufacturerLabel = new Label("Manufacturer:");
		GridPane.setConstraints(manufacturerLabel, 1, 2);
		TextField manufacturer = new TextField();
		GridPane.setConstraints(manufacturer, 1, 3);

		Label priceLabel = new Label("Price:");
		GridPane.setConstraints(priceLabel, 1, 6);
		TextField price = new TextField();
		GridPane.setConstraints(price, 1, 7);

		Label quantityLabel = new Label("Quantity:");
		GridPane.setConstraints(quantityLabel, 1, 8);
		TextField quantity = new TextField();
		GridPane.setConstraints(quantity, 1, 9);

		final Label error = new Label("");
		GridPane.setConstraints(error, 1, 11);
		error.setTextFill(Color.RED);

		Button addBtn = new Button("Add");
		GridPane.setConstraints(addBtn, 1, 10);
		addBtn.setOnAction(e -> {

			if(!admin.exist("product", id.getText().trim()) &&  !id.getText().trim().equals("") && !product.getText().trim().equals("") && !manufacturer.getText().trim().equals("") && !price.getText().trim().equals("") && !quantity.getText().trim().equals("")) {
				admin.addProduct(id.getText().trim(), product.getText().trim(), manufacturer.getText().trim(), price.getText().trim(), quantity.getText().trim());
				alertWindow.close();
			}
			else if(admin.exist("product", id.getText().trim()))
				error.setText("ID already taken.");
			else
				error.setText("Please fill out all required fields.");

		});

		grid.getChildren().addAll( productLabel, product, manufacturerLabel, manufacturer, IDLabel, id, priceLabel, price, quantityLabel , quantity, addBtn, error);
		alertWindow.setResizable(false);

		Scene scene = new Scene(grid, 210, 360);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest(e -> {
			e.consume();
			alertWindow.close();
		});
	}


	@SuppressWarnings("static-access")
	public static void editEmployee(Admin admin, ArrayList<String> data) {
		Stage alertWindow;

		alertWindow = new Stage();

		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle("EDIT ACCOUNT");

		alertWindow.setMinWidth(600);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20,20,20,20));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(false); //DEBUG


		Label output = new Label("");
		GridPane.setConstraints(output, 0, 11);
		output.setTextFill(Color.GREEN);

		Label lName = new Label("Name:");
		Label lSurname = new Label("Surname:");
		Label lUsername = new Label("Username:");
		Label lPassword = new Label("Password:");
		Label lAdminship = new Label("Adminship:");

		TextField name = new TextField(data.get(0));
		TextField surname = new TextField(data.get(1));
		TextField username = new TextField(data.get(2));
		TextField password = new TextField(data.get(3));
		TextField adminship = new TextField(data.get(4));

		Button setName = new Button("Change");
		Button setSurname = new Button("Change");
		Button setUsername = new Button("Change");
		Button setPassword = new Button("Change");
		Button setAdminship = new Button("Change");

		setName.setOnAction(evenChangeName -> {

			if(!name.getText().trim().equals("")) {
				admin.edit("employee", 2, data.get(2), name.getText().trim());
				lName.setText("Name:");
				lName.setTextFill(Color.BLACK);
				GridPane.setConstraints(output, 2, 2);
				output.setText("Done!");
				
			}
			
			else {
				lName.setText("This field cannot be empty.");
				lName.setTextFill(Color.RED);
			}
		});


		setSurname.setOnAction(evenChangeSurname -> {

			if(!surname.getText().trim().equals("")) {
				admin.edit("employee", 3, data.get(2), surname.getText().trim());
				lSurname.setText("Name:");
				lSurname.setTextFill(Color.BLACK);
				GridPane.setConstraints(output, 6, 2);
				output.setText("Done!");
			}
			else {
				lSurname.setText("This field cannot be empty.");
				lSurname.setTextFill(Color.RED);
			}
		});

		setAdminship.setOnAction(evenChangeAddress -> {

			if(!adminship.getText().trim().equals("")) {
				admin.edit("employee", 4, data.get(2), adminship.getText().trim());
				lAdminship.setText("Name:");
				lAdminship.setTextFill(Color.BLACK);
				GridPane.setConstraints(output, 6, 5);
				output.setText("Done!");
			}
			else {
				lAdminship.setText("This field cannot be empty.");
				lAdminship.setTextFill(Color.RED);
			}
		});

		setPassword.setOnAction(evenChangePassword -> {

			if(!password.getText().trim().equals("")) {
				admin.edit("employee", 1, data.get(2), password.getText().trim());
				lPassword.setText("Name:");
				lPassword.setTextFill(Color.BLACK);
				GridPane.setConstraints(output, 5, 10);
				output.setText("Done!");
			}
			else {
				lPassword.setText("This field cannot be empty.");
				lPassword.setTextFill(Color.RED);
			}
		});

		setUsername.setOnAction(evenChangeUsername -> {

			if(!username.getText().trim().equals(""))
				if(!admin.exist("employee", username.getText().trim())) {
					admin.edit("employee", 1, data.get(2), username.getText().trim());
					lUsername.setText("Name:");
					lUsername.setTextFill(Color.BLACK);
					GridPane.setConstraints(output, 2, 5);
					output.setText("Done!");
				}
				else {
					lUsername.setText("Username already taken.");
					lUsername.setTextFill(Color.RED);
				
				}
			else {
				lUsername.setText("This field cannot be empty.");
				lUsername.setTextFill(Color.RED);
			}
		});



		GridPane.setConstraints(lName, 1, 0);
		GridPane.setConstraints(lSurname, 5, 0);

		GridPane.setConstraints(lUsername, 1, 3);
		GridPane.setConstraints(lAdminship, 5, 3);

		GridPane.setConstraints(lPassword, 2, 6);

		GridPane.setConstraints(setName, 1, 2);
		GridPane.setConstraints(setSurname, 5, 2);
		GridPane.setConstraints(setUsername, 1, 5);
		GridPane.setConstraints(setAdminship, 5, 5);
		GridPane.setConstraints(setPassword, 4, 10);

		grid.add(name, 1, 1);
		grid.add(surname, 5, 1);
		grid.add(adminship, 5, 4);
		grid.add(username, 1, 4);
		grid.add(password, 2, 7);

		GridPane.setColumnSpan(name, 4);
		GridPane.setColumnSpan(lName, 4);
		GridPane.setColumnSpan(lSurname, 4);
		GridPane.setColumnSpan(surname, 20);
		GridPane.setColumnSpan(username, 4);
		GridPane.setColumnSpan(adminship, 20);
		GridPane.setColumnSpan(password, 4);
		//address.setMaxWidth(Double.MAX_VALUE);
		GridPane.setFillWidth(adminship, true);
		GridPane.setFillWidth(surname, true);

		grid.getChildren().addAll(output, setName, setSurname, setUsername, setAdminship, setPassword, lName, lSurname, lUsername, lAdminship, lPassword );
		alertWindow.setResizable(false);
		Scene scene = new Scene(grid, 600, 350);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest(e -> {
			e.consume();
			alertWindow.close();
		});
	}

	@SuppressWarnings("static-access")
	public static void editProductType(Admin admin, ArrayList<String> data) {
		Stage alertWindow;

		alertWindow = new Stage();

		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle("EDIT ACCOUNT");

		alertWindow.setMinWidth(600);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20,20,20,20));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(false); //DEBUG


		Label output = new Label("");
		GridPane.setConstraints(output, 0, 11);
		output.setTextFill(Color.GREEN);

		Label lProduct = new Label("Product:");
		Label lManufacturer = new Label("Manufacturer:");
		Label lID = new Label("ID:");
		Label lPrice = new Label("Price:");
		Label lQuantity = new Label("Quantity:");

		TextField product = new TextField(data.get(1));
		TextField manufacturer = new TextField(data.get(2));
		TextField id = new TextField(data.get(0));
		TextField price = new TextField(data.get(3));
		TextField quantity = new TextField(data.get(4));
		
		Button setProduct = new Button("Change");
		Button setManufacturer = new Button("Change");
		Button setID = new Button("Change");
		Button setPrice = new Button("Change");
		Button setQuantity = new Button("Change");

		setProduct.setOnAction(evenChangeName -> {

			if(!product.getText().trim().equals("")) {
				admin.edit("product", 1, data.get(0), product.getText().trim());
				lProduct.setText("Product:");
				lProduct.setTextFill(Color.BLACK);
				GridPane.setConstraints(output, 2, 2);
				output.setText("Done!");
				
			}
			
			else {
				lProduct.setText("This field cannot be empty.");
				lProduct.setTextFill(Color.RED);
			}
		});


		setManufacturer.setOnAction(evenChangeSurname -> {

			if(!manufacturer.getText().trim().equals("")) {
				admin.edit("product", 2, data.get(0), manufacturer.getText().trim());
				lManufacturer.setText("Manufacturer:");
				lManufacturer.setTextFill(Color.BLACK);
				GridPane.setConstraints(output, 6, 2);
				output.setText("Done!");
			}
			else {
				lManufacturer.setText("This field cannot be empty.");
				lManufacturer.setTextFill(Color.RED);
			}
		});

		setQuantity.setOnAction(evenChangeAddress -> {

			if(!quantity.getText().trim().equals("")) {
				admin.edit("product", 4, data.get(0), quantity.getText().trim());
				lQuantity.setText("Quantity:");
				lQuantity.setTextFill(Color.BLACK);
				GridPane.setConstraints(output, 6, 5);
				output.setText("Done!");
			}
			else {
				lQuantity.setText("This field cannot be empty.");
				lQuantity.setTextFill(Color.RED);
			}
		});

		setPrice.setOnAction(evenChangePassword -> {

			if(!price.getText().trim().equals("")) {
				admin.edit("product", 3, data.get(0), price.getText().trim());
				lPrice.setText("Price:");
				lPrice.setTextFill(Color.BLACK);
				GridPane.setConstraints(output, 5, 10);
				output.setText("Done!");
			}
			else {
				lPrice.setText("This field cannot be empty.");
				lPrice.setTextFill(Color.RED);
			}
		});

		setID.setOnAction(evenChangeUsername -> {

			if(!id.getText().trim().equals(""))
				if(!admin.exist("product", id.getText().trim())) {
					admin.edit("product", 0, data.get(0), id.getText().trim());
					lID.setText("ID:");
					lID.setTextFill(Color.BLACK);
					GridPane.setConstraints(output, 2, 5);
					output.setText("Done!");
				}
				else {
					lID.setText("ID already taken.");
					lID.setTextFill(Color.RED);
				
				}
			else {
				lID.setText("This field cannot be empty.");
				lID.setTextFill(Color.RED);
			}
		});



		GridPane.setConstraints(lProduct, 1, 0);
		GridPane.setConstraints(lManufacturer, 5, 0);

		GridPane.setConstraints(lID, 1, 3);
		GridPane.setConstraints(lQuantity, 5, 3);

		GridPane.setConstraints(lPrice, 2, 6);

		GridPane.setConstraints(setProduct, 1, 2);
		GridPane.setConstraints(setManufacturer, 5, 2);
		GridPane.setConstraints(setID, 1, 5);
		GridPane.setConstraints(setQuantity, 5, 5);
		GridPane.setConstraints(setPrice, 4, 10);

		grid.add(product, 1, 1);
		grid.add(manufacturer, 5, 1);
		grid.add(quantity, 5, 4);
		grid.add(id, 1, 4);
		grid.add(price, 2, 7);

		GridPane.setColumnSpan(product, 4);
		GridPane.setColumnSpan(lProduct, 4);
		GridPane.setColumnSpan(lManufacturer, 4);
		GridPane.setColumnSpan(manufacturer, 20);
		GridPane.setColumnSpan(id, 4);
		GridPane.setColumnSpan(quantity, 20);
		GridPane.setColumnSpan(price, 4);
		//address.setMaxWidth(Double.MAX_VALUE);
		GridPane.setFillWidth(quantity, true);
		GridPane.setFillWidth(manufacturer, true);

		grid.getChildren().addAll(output, setProduct, setManufacturer, setID, setQuantity, setPrice, lProduct, lManufacturer, lID, lQuantity, lPrice );
		alertWindow.setResizable(false);
		Scene scene = new Scene(grid, 600, 350);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest(e -> {
			e.consume();
			alertWindow.close();
		});
	}


	@SuppressWarnings("static-access")
	public static void addEmployee(Admin admin) {
		alertWindow = new Stage();

		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle("ADD NEW EMPLOYEE");
		alertWindow.setMinWidth(200);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label usernameLabel = new Label("Username:");
		GridPane.setConstraints(usernameLabel, 1, 4);
		TextField username = new TextField();
		GridPane.setConstraints(username, 1, 5);

		Label nameLabel = new Label("Name:");
		GridPane.setConstraints(nameLabel, 1, 0);
		TextField name = new TextField();
		GridPane.setConstraints(name, 1, 1);

		Label surnameLabel = new Label("Surname:");
		GridPane.setConstraints(surnameLabel, 1, 2);
		TextField surname = new TextField();
		GridPane.setConstraints(surname, 1, 3);

		Label passwordLabel = new Label("Password:");
		GridPane.setConstraints(passwordLabel, 1, 6);
		TextField password = new TextField();
		GridPane.setConstraints(password, 1, 7);

		Label adminshipLabel = new Label("Adminship (true/false):");
		GridPane.setConstraints(adminshipLabel, 1, 8);
		TextField adminship = new TextField();
		GridPane.setConstraints(adminship, 1, 9);

		final Label error = new Label("");
		GridPane.setConstraints(error, 1, 11);
		error.setTextFill(Color.RED);

		Button addBtn = new Button("Add");
		GridPane.setConstraints(addBtn, 1, 10);
		addBtn.setOnAction(e -> {

			if(!admin.exist("employee", username.getText().trim()) &&  !username.getText().trim().equals("") && !name.getText().trim().equals("") && !surname.getText().trim().equals("") && !password.getText().trim().equals("") && !adminship.getText().trim().equals("") && (adminship.getText().trim().equalsIgnoreCase("true")|| adminship.getText().trim().equalsIgnoreCase("false"))) {
				admin.addEmployee(username.getText().trim(), name.getText().trim(), surname.getText().trim(), password.getText().trim(), adminship.getText().trim());
				alertWindow.close();
			}
			else if(admin.exist("employee", username.getText().trim()))
				error.setText("Username already taken.");

			else if(!adminship.getText().trim().equalsIgnoreCase("true") || !adminship.getText().trim().equalsIgnoreCase("false"))
				error.setText("Adminship must be\neither 'true' or 'false'");
			else
				error.setText("Please fill out all required fields.");

		});

		grid.getChildren().addAll( nameLabel, name, surnameLabel, surname, usernameLabel, username, passwordLabel, password, adminshipLabel , adminship, addBtn, error);
		alertWindow.setResizable(false);

		Scene scene = new Scene(grid, 210, 400);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest(e -> {
			e.consume();
			alertWindow.close();
		});
	}


	public static boolean login(Admin admin) {
		user = pwd = "";
		//login();
		return false;
	}

	/*public static boolean login(Employee employee) {
		user = pwd = "";
		//login(); overload
		return false;
	}*/


	static void sleep(int sec) {
		try {
			Thread.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
