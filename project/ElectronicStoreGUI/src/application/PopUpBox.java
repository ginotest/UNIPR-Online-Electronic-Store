package application;


import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Class for displaying alert windows, dialog boxes and various windows
 */
public class PopUpBox extends ManageData{

	static Stage alertWindow;
	static boolean answer;
	static String user, pwd;
	private static int resultManageProfile;

	/**
	 * displays an alert window with a custom message
	 * @param title the title of the window
	 * @param message the message to be displayed
	 * @param width width minimum width to display the message
	 */
	public static void alert(String title, String message, int width) {

		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle(title);
		alertWindow.setMinWidth(width);

		Label msgLabel = new Label(message);
		msgLabel.setId("labelAlert");

		Button closeBtn = new Button("Close");
		closeBtn.setId("btnAlert");


		closeBtn.setOnAction( e -> alertWindow.close() );

		GridPane grid = new GridPane();
		grid.setId("alert");
		grid.setPadding(new Insets(5,5,5,5));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		GridPane.setConstraints(msgLabel, 1, 0);
		GridPane.setConstraints(closeBtn, 1, 1);
		GridPane.setHalignment(closeBtn, HPos.CENTER);

		grid.getChildren().addAll(msgLabel, closeBtn);
		Scene scene = new Scene(grid, width, 100);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
	}

	/**
	 * displays a window
	 * @param title the title of the window
	 * @param pane the content to be displayed
	 * @param width minimum width to display the content
	 */
	public static void display(String title, @SuppressWarnings("exports") Pane pane, int width) {

		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle(title);
		alertWindow.setMinWidth(width);

		Scene scene = new Scene(pane, width, 200);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
	}

	/**
	 * displays a dialog box with a custom message
	 * @param title the title of the window
	 * @param message the message to be displayed
	 * @return returns true if the user selected 'yes'
	 */
	public static boolean confirm(String title, String message) {

		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle(title);
		alertWindow.setMinWidth(300);

		Label msgLabel = new Label(message);
		msgLabel.setId("labelConfirm");
		Button yesBtn = new Button("Yes");
		yesBtn.setId("btnConfirm");
		Button noBtn = new Button("No");
		noBtn.setId("btnConfirm");


		yesBtn.setOnAction( e -> {

			answer = true;
			alertWindow.close();

		} );

		noBtn.setOnAction(e -> {

			answer = false;
			alertWindow.close();

		} );


		GridPane grid = new GridPane();
		grid.setId("confirm");
		grid.setPadding(new Insets(5,5,5,5));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		GridPane.setConstraints(msgLabel, 1, 0);
		GridPane.setConstraints(yesBtn, 1, 1);
		GridPane.setHalignment(yesBtn, HPos.LEFT);
		GridPane.setConstraints(noBtn, 1, 1);
		GridPane.setHalignment(noBtn, HPos.RIGHT);

		grid.getChildren().addAll(msgLabel, yesBtn, noBtn);
		Scene scene = new Scene(grid, 300, 100);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();

		return answer;

	}

	/**
	 * displays a window for the login in order to access the employee's area
	 * @param employee employee object for the login
	 */
	public static void login(Employee employee) {

		user = pwd = "";
		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle("EMPLOYEE LOGIN");
		alertWindow.setMinWidth(400);

		GridPane grid = new GridPane();
		grid.setId("login");
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);

		Label msgLabel = new Label("Enter Login Details");
		msgLabel.setId("labelLogin");
		GridPane.setConstraints(msgLabel, 1, 0);

		Label usernameLabel = new Label("Username:");
		usernameLabel.setId("labelLogin");
		GridPane.setConstraints(usernameLabel, 0, 1);

		TextField username = new TextField(user);
		GridPane.setConstraints(username, 1, 1);

		Label passwordLabel = new Label("Password:");
		passwordLabel.setId("labelLogin");
		GridPane.setConstraints(passwordLabel, 0, 2);

		PasswordField password = new PasswordField();
		password.setPromptText("password");
		GridPane.setConstraints(password, 1, 2);

		Label error = new Label("");
		error.setId("errorLogin");
		GridPane.setConstraints(error, 1, 5);

		Button loginBtn = new Button("Login");
		loginBtn.setId("btnLogin");
		GridPane.setConstraints(loginBtn, 1, 3);

		Button cancelBtn = new Button("Cancel");
		cancelBtn.setId("btnLogin");
		GridPane.setConstraints(cancelBtn, 2, 3);

		GridPane.setColumnSpan(username, 2);
		GridPane.setColumnSpan(password, 2);

		username.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				password.requestFocus();

		} );

		password.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				loginBtn.fire();

		} );

		loginBtn.setOnAction( e -> {

			user = username.getText().trim();
			pwd =  password.getText().trim();

			if(employee.login(user, pwd))
				alertWindow.close();
			else
				error.setText("Username or Password Incorrect!");

			error.setTextFill(Color.RED);
		} );

		loginBtn.setDisable(true);
		ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
			StringProperty textProperty = (StringProperty) observable ;
			TextField textField = (TextField) textProperty.getBean();
			newValue = newValue.trim();  
			if(textField == username) {
				if(!newValue.matches("\\sa-zA-Z0-9*"))
					newValue= newValue.replaceAll("[^\\sa-zA-Z0-9]", "");
				if ((newValue.length() > 0)  && (!Character.toString(newValue.charAt(0)).matches("\\sa-zA-Z")))
					newValue = newValue.replaceAll("^[\\d]", "");
			}
			textField.setText(newValue);
			loginBtn.setDisable((username.getText().trim().length() == 0) || (password.getText().trim().length() == 0));
			error.setText("");
		});
		username.textProperty().addListener(listener);
		password.textProperty().addListener(listener);

		cancelBtn.setOnAction( e -> {

			user = pwd = "";
			alertWindow.close();

		} );

		cancelBtn.setMaxWidth(Double.MAX_VALUE);

		grid.getChildren().addAll(msgLabel, usernameLabel, username, passwordLabel, password, loginBtn, cancelBtn, error);

		Scene scene = new Scene(grid, 400, 200);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest( e -> {

			e.consume();
			user = pwd = "";
			alertWindow.close();

		} );
	}

	/**
	 * displays a window for the client's login 
	 * @param client client object for the login
	 */
	public static void login(Client client) {

		user = pwd = "";
		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle("CLIENT LOGIN");
		alertWindow.setMinWidth(400);

		GridPane grid = new GridPane();
		grid.setId("login");
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);

		Label msgLabel = new Label("Enter Login Details");
		msgLabel.setId("labelLogin");
		GridPane.setConstraints(msgLabel, 1, 0);

		Label usernameLabel = new Label("Username:");
		usernameLabel.setId("labelLogin");
		GridPane.setConstraints(usernameLabel, 0, 1);

		TextField username = new TextField(user);
		GridPane.setConstraints(username, 1, 1);

		Label passwordLabel = new Label("Password:");
		passwordLabel.setId("labelLogin");
		GridPane.setConstraints(passwordLabel, 0, 2);

		PasswordField password = new PasswordField();
		password.setPromptText("password");
		GridPane.setConstraints(password, 1, 2);

		Label error = new Label("");
		error.setId("errorLogin");
		GridPane.setConstraints(error, 1, 5);

		Button loginBtn = new Button("Login");
		loginBtn.setId("btnLogin");
		GridPane.setConstraints(loginBtn, 1, 3);

		Button cancelBtn = new Button("Cancel");
		cancelBtn.setId("btnLogin");
		GridPane.setConstraints(cancelBtn, 2, 3);

		GridPane.setColumnSpan(username, 2);
		GridPane.setColumnSpan(password, 2);

		username.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				password.requestFocus();

		} );

		password.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				loginBtn.fire();

		} );

		loginBtn.setOnAction( e -> {

			user = username.getText().trim();
			pwd =  password.getText().trim();

			if(client.Login(user, pwd)) {
				resultManageProfile=0;
				alertWindow.close();
			}
			else
				error.setText("Username or Password Incorrect!");
			error.setTextFill(Color.RED);
		} );

		loginBtn.setDisable(true);
		ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
			StringProperty textProperty = (StringProperty) observable ;
			TextField textField = (TextField) textProperty.getBean();
			newValue = newValue.trim();  
			if(textField == username) {
				if(!newValue.matches("\\sa-zA-Z0-9*"))
					newValue= newValue.replaceAll("[^\\sa-zA-Z0-9]", "");
				if ((newValue.length() > 0)  && (!Character.toString(newValue.charAt(0)).matches("\\sa-zA-Z")))
					newValue = newValue.replaceAll("^[\\d]", "");
			}
			textField.setText(newValue);
			loginBtn.setDisable((username.getText().trim().length() == 0) || (password.getText().trim().length() == 0));
			error.setText("");
		});
		username.textProperty().addListener(listener);
		password.textProperty().addListener(listener);

		cancelBtn.setMaxWidth(Double.MAX_VALUE);
		cancelBtn.setOnAction( e -> {

			user = pwd = "";
			alertWindow.close();

		} );

		grid.getChildren().addAll(msgLabel, usernameLabel, username, passwordLabel, password, loginBtn, cancelBtn, error);

		Scene scene = new Scene(grid, 400, 200);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest(e -> {

			e.consume();
			user = pwd = "";
			alertWindow.close();

		});
	}

	/**
	 * displays a window for the client's sign up 
	 * @param client client object for the sign up
	 */
	@SuppressWarnings("static-access")
	public static void register(Client client) {

		user = pwd = "";
		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle("CREATE ACCOUNT");
		alertWindow.setMinWidth(210);

		GridPane grid = new GridPane();
		grid.setId("register");
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label usernameLabel = new Label("* Username:");
		usernameLabel.setId("labelRegister");
		GridPane.setConstraints(usernameLabel, 1, 4);

		TextField username = new TextField(user);
		GridPane.setConstraints(username, 1, 5);

		Label nameLabel = new Label("* Name:");
		nameLabel.setId("labelRegister");
		GridPane.setConstraints(nameLabel, 1, 0);

		TextField name = new TextField(user);
		GridPane.setConstraints(name, 1, 1);

		Label surnameLabel = new Label("* Surname:");
		surnameLabel.setId("labelRegister");
		GridPane.setConstraints(surnameLabel, 1, 2);

		TextField surname = new TextField(user);
		GridPane.setConstraints(surname, 1, 3);

		Label addressLabel = new Label("Address:");
		addressLabel.setId("labelRegister");
		GridPane.setConstraints(addressLabel, 1, 6);

		TextField address = new TextField(user);
		GridPane.setConstraints(address, 1, 7);

		Label passwordLabel = new Label("* Password:");
		passwordLabel.setId("labelRegister");
		GridPane.setConstraints(passwordLabel, 1, 8);

		PasswordField password = new PasswordField();
		password.setPromptText("password");
		GridPane.setConstraints(password, 1, 9);

		PasswordField passwordRepeat = new PasswordField();
		passwordRepeat.setPromptText("repeat password");
		GridPane.setConstraints(passwordRepeat, 1, 10);

		final Label error = new Label("");
		error.setId("errorRegister");
		GridPane.setConstraints(error, 1, 12);
		error.setTextFill(Color.RED);

		Button registerBtn = new Button("Sign up");
		registerBtn.setId("btnRegister");
		GridPane.setConstraints(registerBtn, 1, 11);

		name.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				surname.requestFocus();

		} );

		surname.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				username.requestFocus();

		} );

		username.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				address.requestFocus();

		} );

		address.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				password.requestFocus();

		} );

		password.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				passwordRepeat.requestFocus();

		} );

		passwordRepeat.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				registerBtn.fire();

		} );

		ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
			StringProperty textProperty = (StringProperty) observable ;
			TextField textField = (TextField) textProperty.getBean();
			if(textField == name || textField == surname)
				if (!newValue.matches("\\sa-zA-Z*")) {
					textField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
				}
			if(textField == username) {
				newValue = newValue.trim();
				if(!newValue.matches("\\sa-zA-Z0-9*"))
					newValue= newValue.replaceAll("[^\\sa-zA-Z0-9]", "");
				if ((newValue.length() > 0)  && (!Character.toString(newValue.charAt(0)).matches("\\sa-zA-Z")))
					newValue = newValue.replaceAll("^[\\d]", "");
				textField.setText(newValue); 
			}
		});
		name.textProperty().addListener(listener);
		surname.textProperty().addListener(listener);
		username.textProperty().addListener(listener);

		registerBtn.setOnAction( e -> {

			user = username.getText().trim();
			pwd =  password.getText().trim();

			if(!client.exist("client", username.getText().trim()) && !name.getText().trim().equals("") && !surname.getText().trim().equals("") && !username.getText().trim().equals("") && !password.getText().trim().equals("") && !passwordRepeat.getText().trim().equals("") && (password.getText().trim().equals(passwordRepeat.getText().trim()))) {

				client.registerNew(name.getText().trim(),surname.getText().trim(),username.getText().trim(),password.getText().trim());
				if(!address.getText().trim().equals(""))
					client.setAddress(address.getText().trim());
				alertWindow.close();

			}
			else {

				if(client.exist("client", username.getText().trim()))
					error.setText("Username already taken. ");

				else if(!password.getText().trim().equals(passwordRepeat.getText().trim()))
					error.setText("The passwords do not match.");

				else
					error.setText("Please fill out all required(*)\nfields.");

			}

		} );

		grid.getChildren().addAll( nameLabel, name, surnameLabel, surname, usernameLabel, username, passwordLabel, password, passwordRepeat, registerBtn, error, addressLabel, address);

		Scene scene = new Scene(grid, 210, 420);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest( e -> {

			e.consume();
			user = pwd = "";
			alertWindow.close();

		} );
	}

	/**
	 * displays a window for adding new product types into the database
	 * @param admin the admin object that performs the action
	 * @return returns true if the product has been added successfully
	 */
	@SuppressWarnings("static-access")
	public static boolean addProduct(Admin admin) {

		answer=false;
		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle("ADD PRODUCT TYPE");
		alertWindow.setMinWidth(210);

		GridPane grid = new GridPane();
		grid.setId("add");
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label IDLabel = new Label("ID:");
		IDLabel.setId("labelAdd");
		GridPane.setConstraints(IDLabel, 1, 4);

		TextField id = new TextField();
		GridPane.setConstraints(id, 1, 5);

		Label productLabel = new Label("Product:");
		productLabel.setId("labelAdd");
		GridPane.setConstraints(productLabel, 1, 0);

		TextField product = new TextField();
		GridPane.setConstraints(product, 1, 1);

		Label manufacturerLabel = new Label("Manufacturer:");
		manufacturerLabel.setId("labelAdd");
		GridPane.setConstraints(manufacturerLabel, 1, 2);

		TextField manufacturer = new TextField();
		GridPane.setConstraints(manufacturer, 1, 3);

		Label priceLabel = new Label("Price:");
		priceLabel.setId("labelAdd");
		GridPane.setConstraints(priceLabel, 1, 6);

		TextField price = new TextField();
		GridPane.setConstraints(price, 1, 7);

		Label error = new Label("");
		error.setId("errorAdd");
		GridPane.setConstraints(error, 1, 11);
		error.setTextFill(Color.RED);

		Button addBtn = new Button("Add");
		addBtn.setId("btnAdd");
		GridPane.setConstraints(addBtn, 1, 10);

		addBtn.setOnAction( e -> {

			if(!admin.exist("product", id.getText().trim()) &&  !id.getText().trim().equals("") && !product.getText().trim().equals("") && !manufacturer.getText().trim().equals("") && !price.getText().trim().equals("")) {

				answer=true;

				if(price.getText().equals("0."))
					price.setText("0.0");

				admin.addProduct(id.getText().trim(), product.getText().trim(), manufacturer.getText().trim(), price.getText().trim());
				alertWindow.close();

			}

			else if(admin.exist("product", id.getText().trim()))
				error.setText("ID already taken.");

			else
				error.setText("Please fill out all required fields.");

		} );

		product.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				manufacturer.requestFocus();

		} );

		manufacturer.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				id.requestFocus();

		} );

		id.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				price.requestFocus();

		} );

		price.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				addBtn.fire();

		} );

		id.textProperty().addListener( new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (!newValue.matches("\\d*"))
					id.setText(newValue.replaceAll("[^\\d]", ""));

			}
		} );

		price.textProperty().addListener( new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (!newValue.matches("\\d*(\\.\\d{0,2})?"))
					price.setText(oldValue);

				if(price.getText().startsWith("."))
					price.setText("0".concat(price.getText()));

			}
		} );

		grid.getChildren().addAll(productLabel, product, manufacturerLabel, manufacturer, IDLabel, id, priceLabel, price, addBtn, error);

		Scene scene = new Scene(grid, 210, 360);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest( e -> {

			e.consume();
			alertWindow.close();

		} );

		return answer;
	}

	/**
	 * displays a window for editing a certain employee's account
	 * @param admin the admin object that performs the action
	 * @param data the employee's information
	 * @return returns true if the employee has been edited
	 */
	@SuppressWarnings("static-access")
	public static boolean editEmployee(Admin admin, ArrayList<String> data) {

		answer=false;
		resultManageProfile_SET(0);
		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle("EDIT ACCOUNT");
		alertWindow.setMinWidth(350);

		GridPane grid = new GridPane();
		grid.setId("edit");
		grid.setPadding(new Insets(20,20,20,20));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label output = new Label("");
		output.setId("editOutput");
		GridPane.setConstraints(output, 0, 11);
		output.setTextFill(Color.GREEN);

		Label nameLabel = new Label("Name:");
		nameLabel.setId("labelEdit");

		Label surnameLabel = new Label("Surname:");
		surnameLabel.setId("labelEdit");

		Label usernameLabel = new Label("Username:");
		usernameLabel.setId("labelEdit");

		Label passwordLabel = new Label("Password:");
		passwordLabel.setId("labelEdit");

		Label adminshipLabel = new Label("Adminship:");
		adminshipLabel.setId("labelEdit");

		TextField name = new TextField(data.get(0));
		TextField surname = new TextField(data.get(1));
		TextField username = new TextField(data.get(2));
		TextField password = new TextField(data.get(3));

		ChoiceBox<String> adminship = new ChoiceBox<>(FXCollections.observableArrayList("true", "false"));
		adminship.setValue(data.get(4));

		Button setNameBtn = new Button("Change");
		setNameBtn.setId("btnEdit");

		Button setSurnameBtn = new Button("Change");
		setSurnameBtn.setId("btnEdit");

		Button setUsernameBtn = new Button("Change");
		setUsernameBtn.setId("btnEdit");

		Button setPasswordBtn = new Button("Change");
		setPasswordBtn.setId("btnEdit");

		Button setAdminshipBtn = new Button("Change");
		setAdminshipBtn.setId("btnEdit");

		ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
			StringProperty textProperty = (StringProperty) observable ;
			TextField textField = (TextField) textProperty.getBean();
			if(textField == name || textField == surname)
				if (!newValue.matches("\\sa-zA-Z*")) {
					textField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
				}
			if(textField == username) {
				newValue = newValue.trim();
				if(!newValue.matches("\\sa-zA-Z0-9*"))
					newValue= newValue.replaceAll("[^\\sa-zA-Z0-9]", "");
				if ((newValue.length() > 0)  && (!Character.toString(newValue.charAt(0)).matches("\\sa-zA-Z")))
					newValue = newValue.replaceAll("^[\\d]", "");
				textField.setText(newValue); 
			}
		});
		name.textProperty().addListener(listener);
		surname.textProperty().addListener(listener);
		username.textProperty().addListener(listener);

		setNameBtn.setOnAction( e -> {

			if(!name.getText().trim().equals("")) {

				admin.edit("employee", 2, data.get(2), name.getText().trim());
				nameLabel.setText("Name:");
				nameLabel.setTextFill(Color.WHITE);
				GridPane.setConstraints(output, 2, 2);
				GridPane.setHalignment(output, HPos.LEFT);
				output.setText("Done!");
				answer=true;

			}
			else {

				nameLabel.setText("This field cannot be empty.");
				nameLabel.setTextFill(Color.RED);

			}

		} );

		setSurnameBtn.setOnAction( e -> {

			if(!surname.getText().trim().equals("")) {


				admin.edit("employee", 3, data.get(2), surname.getText().trim());
				surnameLabel.setText("Surname:");
				surnameLabel.setTextFill(Color.WHITE);
				GridPane.setConstraints(output, 6, 2);
				GridPane.setHalignment(output, HPos.LEFT);
				output.setText("Done!");
				answer=true;

			}
			else {

				surnameLabel.setText("This field cannot be empty.");
				surnameLabel.setTextFill(Color.RED);

			}

		} );

		setAdminshipBtn.setOnAction( e -> {

			if(admin.getUsername().equals(data.get(2)) && adminship.getValue().trim().equals("false")) {

				Stage alertWindow2 = new Stage();

				alertWindow2.initModality(Modality.APPLICATION_MODAL);
				alertWindow2.initStyle(StageStyle.UTILITY);
				alertWindow2.setTitle("adminship confirmation");
				alertWindow.setMinWidth(300);

				Label msgLabel = new Label("You are about to Relinquish your right to\nAdmin Privileges.\nPROCEED?");
				msgLabel.setId("labelEdit");

				Button yesBtn = new Button("Yes");
				yesBtn.setId("btnEdit");

				Button noBtn = new Button("No");
				noBtn.setId("btnEdit");

				yesBtn.setOnAction( ev -> {
					admin.edit("employee", 4, data.get(2), adminship.getValue().trim());
					resultManageProfile_SET(2);
					alertWindow2.close();
					alertWindow.close();
					answer = true;
				} );

				noBtn.setOnAction(ev -> {
					alertWindow2.close();
				} );

				GridPane grid2 = new GridPane();
				grid2.setId("edit");

				grid2.setPadding(new Insets(5,5,5,5));
				grid2.setVgap(8);
				grid2.setHgap(8);
				grid2.setAlignment(Pos.CENTER);

				GridPane.setConstraints(msgLabel, 1, 0);
				GridPane.setConstraints(yesBtn, 1, 1);
				GridPane.setHalignment(yesBtn, HPos.LEFT);
				GridPane.setConstraints(noBtn, 1, 1);
				GridPane.setHalignment(noBtn, HPos.RIGHT);

				grid2.getChildren().addAll(msgLabel, yesBtn, noBtn);

				Scene scene2 = new Scene(grid2, 300, 100);
				scene2.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
				alertWindow2.setScene(scene2);
				alertWindow2.showAndWait();
			}
			else {
				admin.edit("employee", 4, data.get(2), adminship.getValue().trim());
				GridPane.setConstraints(output, 4, 11);
				GridPane.setHalignment(output, HPos.CENTER);
				output.setText("Done!");
				answer=true;
			}

		} );

		setPasswordBtn.setOnAction( e -> {

			if(!password.getText().trim().equals("")) {

				admin.edit("employee", 1, data.get(2), password.getText().trim());
				passwordLabel.setText("Password:");
				passwordLabel.setTextFill(Color.WHITE);
				GridPane.setConstraints(output, 6, 5);
				GridPane.setHalignment(output, HPos.LEFT);
				output.setText("Done!");
				answer=true;

			}
			else {

				passwordLabel.setText("This field cannot be empty.");
				passwordLabel.setTextFill(Color.RED);

			}

		} );

		setUsernameBtn.setOnAction( e -> {

			if(!username.getText().trim().equals(""))
				if(!admin.exist("employee", username.getText().trim())) {

					admin.edit("employee", 1, data.get(2), username.getText().trim());
					usernameLabel.setText("Name:");
					usernameLabel.setTextFill(Color.WHITE);
					GridPane.setConstraints(output, 2, 5);
					GridPane.setHalignment(output, HPos.LEFT);
					output.setText("Done!");
					answer=true;

				}
				else {

					usernameLabel.setText("Username already taken.");
					usernameLabel.setTextFill(Color.RED);

				}
			else {

				usernameLabel.setText("This field cannot be empty.");
				usernameLabel.setTextFill(Color.RED);

			}

		} );

		GridPane.setConstraints(nameLabel, 1, 0);
		GridPane.setConstraints(surnameLabel, 5, 0);
		GridPane.setConstraints(usernameLabel, 1, 3);
		GridPane.setConstraints(adminshipLabel, 3, 9);
		GridPane.setConstraints(passwordLabel, 5, 3);
		GridPane.setConstraints(setNameBtn, 1, 2);
		GridPane.setConstraints(setSurnameBtn, 5, 2);
		GridPane.setConstraints(setUsernameBtn, 1, 5);
		GridPane.setConstraints(setAdminshipBtn, 4, 10);
		GridPane.setConstraints(setPasswordBtn, 5, 5);

		grid.add(name, 1, 1);
		grid.add(surname, 5, 1);
		grid.add(adminship, 5, 9);
		grid.add(username, 1, 4);
		grid.add(password, 5, 4);

		GridPane.setColumnSpan(name, 4);
		GridPane.setColumnSpan(nameLabel, 4);
		GridPane.setColumnSpan(surnameLabel, 4);
		GridPane.setColumnSpan(surname, 4);
		GridPane.setColumnSpan(username, 4);
		GridPane.setColumnSpan(adminship, 2);
		GridPane.setColumnSpan(adminshipLabel, 2);
		GridPane.setColumnSpan(setAdminshipBtn, 2);
		GridPane.setColumnSpan(password, 4);
		GridPane.setColumnSpan(output, 2);
		GridPane.setFillWidth(adminship, true);
		GridPane.setFillWidth(surname, true);
		GridPane.setHalignment(adminshipLabel, HPos.CENTER);
		GridPane.setHalignment(adminship, HPos.CENTER);
		GridPane.setHalignment(setAdminshipBtn, HPos.CENTER);

		grid.getChildren().addAll(output, setNameBtn, setSurnameBtn, setUsernameBtn, setAdminshipBtn, setPasswordBtn, nameLabel, surnameLabel, usernameLabel, adminshipLabel, passwordLabel);

		Scene scene = new Scene(grid, 350, 350);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest( e -> {

			e.consume();
			alertWindow.close();

		} );

		return answer;
	}

	/**
	 * displays a window for editing a certain product type
	 * @param admin the admin object that performs the action
	 * @param data the product's information
	 * @return returns true if the product has been edited
	 */
	@SuppressWarnings("static-access")
	public static boolean editProductType(Admin admin, ArrayList<String> data) {

		answer=false;
		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle("EDIT PRODUCT TYPE");
		alertWindow.setMinWidth(350);

		GridPane grid = new GridPane();
		grid.setId("edit");
		grid.setPadding(new Insets(20,20,20,20));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label output = new Label("");
		output.setId("editOutput");
		GridPane.setConstraints(output, 0, 7);
		output.setTextFill(Color.GREEN);

		Label productLabel = new Label("Product:");
		productLabel.setId("labelEdit");

		Label manufacturerLabel = new Label("Manufacturer:");
		manufacturerLabel.setId("labelEdit");

		Label IDLabel = new Label("ID:");
		IDLabel.setId("labelEdit");

		Label priceLabel = new Label("Price:");
		priceLabel.setId("labelEdit");

		TextField product = new TextField(data.get(1));
		TextField manufacturer = new TextField(data.get(2));
		TextField id = new TextField(data.get(0));
		TextField price = new TextField(data.get(3));

		Button setProductBtn = new Button("Change");
		setProductBtn.setId("btnEdit");

		Button setManufacturerBtn = new Button("Change");
		setManufacturerBtn.setId("btnEdit");

		Button setIDBtn = new Button("Change");
		setIDBtn.setId("btnEdit");

		Button setPriceBtn = new Button("Change");
		setPriceBtn.setId("btnEdit");

		setProductBtn.setOnAction( e -> {

			if(!product.getText().trim().equals("")) {

				admin.edit("product", 1, data.get(0), product.getText().trim());
				productLabel.setText("Product:");
				productLabel.setTextFill(Color.WHITE);
				GridPane.setConstraints(output, 2, 2);
				output.setText("Done!");
				answer=true;

			}
			else {

				productLabel.setText("This field cannot be empty.");
				productLabel.setTextFill(Color.RED);

			}

		} );

		setManufacturerBtn.setOnAction( e -> {

			if(!manufacturer.getText().trim().equals("")) {

				admin.edit("product", 2, data.get(0), manufacturer.getText().trim());
				manufacturerLabel.setText("Manufacturer:");
				manufacturerLabel.setTextFill(Color.WHITE);
				GridPane.setConstraints(output, 6, 2);
				output.setText("Done!");
				answer=true;

			}
			else {

				manufacturerLabel.setText("This field cannot be empty.");
				manufacturerLabel.setTextFill(Color.RED);

			}
		});

		setPriceBtn.setOnAction(e -> {

			if(!price.getText().trim().equals("")) {

				admin.edit("product", 3, data.get(0), price.getText().trim());
				priceLabel.setText("Price:");
				priceLabel.setTextFill(Color.WHITE);
				GridPane.setConstraints(output, 6, 5);
				output.setText("Done!");
				answer=true;

			}
			else {

				priceLabel.setText("This field cannot be empty.");
				priceLabel.setTextFill(Color.RED);

			}
		});

		setIDBtn.setOnAction(e -> {

			if(!id.getText().trim().equals(""))
				if(!admin.exist("product", id.getText().trim())) {

					admin.edit("product", 0, data.get(0), id.getText().trim());
					IDLabel.setText("ID:");
					IDLabel.setTextFill(Color.WHITE);
					GridPane.setConstraints(output, 2, 5);
					output.setText("Done!");
					answer=true;

				}
				else {

					IDLabel.setText("ID already taken.");
					IDLabel.setTextFill(Color.RED);

				}
			else {

				IDLabel.setText("This field cannot be empty.");
				IDLabel.setTextFill(Color.RED);

			}
		});

		id.textProperty().addListener( new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (!newValue.matches("\\d*"))
					id.setText(newValue.replaceAll("[^\\d]", ""));

			}

		} );

		price.textProperty().addListener( new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (!newValue.matches("\\d*(\\.\\d{0,2})?"))
					price.setText(oldValue);

				if(price.getText().startsWith("."))
					price.setText("0".concat(price.getText()));

			}

		} );

		GridPane.setConstraints(productLabel, 1, 0);
		GridPane.setConstraints(manufacturerLabel, 5, 0);
		GridPane.setConstraints(IDLabel, 1, 3);
		GridPane.setConstraints(priceLabel, 5, 3);
		GridPane.setConstraints(setProductBtn, 1, 2);
		GridPane.setConstraints(setManufacturerBtn, 5, 2);
		GridPane.setConstraints(setIDBtn, 1, 5);
		GridPane.setConstraints(setPriceBtn, 5, 5);

		grid.add(product, 1, 1);
		grid.add(manufacturer, 5, 1);
		grid.add(id, 1, 4);
		grid.add(price, 5, 4);

		GridPane.setColumnSpan(product, 4);
		GridPane.setColumnSpan(productLabel, 4);
		GridPane.setColumnSpan(manufacturerLabel, 4);
		GridPane.setColumnSpan(manufacturer, 4);
		GridPane.setColumnSpan(id, 4);
		GridPane.setColumnSpan(price, 4);

		grid.getChildren().addAll(output, setProductBtn, setManufacturerBtn, setIDBtn, setPriceBtn, productLabel, manufacturerLabel, IDLabel, priceLabel);

		Scene scene = new Scene(grid, 350, 250);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest( e -> {

			e.consume();
			alertWindow.close();

		});

		return answer;
	}

	/**
	 * displays a window for adding new employee's account into the database
	 * @param admin the admin object that performs the action
	 * @return returns true if the employee has been added successfully
	 */
	@SuppressWarnings("static-access")
	public static boolean addEmployee(Admin admin) {

		answer = false;
		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle("ADD NEW EMPLOYEE");
		alertWindow.setMinWidth(210);

		GridPane grid = new GridPane();
		grid.setId("add");
		grid.setPadding(new Insets(15,15,15,15));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label usernameLabel = new Label("Username:");
		usernameLabel.setId("labelAdd");
		GridPane.setConstraints(usernameLabel, 1, 4);

		TextField username = new TextField();
		GridPane.setConstraints(username, 1, 5);

		Label nameLabel = new Label("Name:");
		nameLabel.setId("labelAdd");
		GridPane.setConstraints(nameLabel, 1, 0);

		TextField name = new TextField();
		GridPane.setConstraints(name, 1, 1);

		Label surnameLabel = new Label("Surname:");
		surnameLabel.setId("labelAdd");
		GridPane.setConstraints(surnameLabel, 1, 2);

		TextField surname = new TextField();
		GridPane.setConstraints(surname, 1, 3);

		Label passwordLabel = new Label("Password:");
		passwordLabel.setId("labelAdd");
		GridPane.setConstraints(passwordLabel, 1, 6);

		TextField password = new TextField();
		GridPane.setConstraints(password, 1, 7);

		ChoiceBox<String> adminship = new ChoiceBox<>(FXCollections.observableArrayList("true", "false"));
		adminship.setValue("false");
		GridPane.setConstraints(adminship, 1, 9);

		Label adminshipLabel = new Label("Adminship:");
		adminshipLabel.setId("labelAdd");
		GridPane.setConstraints(adminshipLabel, 1, 8);

		Label error = new Label("");
		error.setId("errorAdd");
		GridPane.setConstraints(error, 1, 13);
		error.setTextFill(Color.RED);

		Button addBtn = new Button("Add Employee");
		addBtn.setId("btnAdd");
		GridPane.setConstraints(addBtn, 1, 12);

		addBtn.setOnAction( e -> {

			if(!admin.exist("employee", username.getText().trim()) &&  !username.getText().trim().equals("") && !name.getText().trim().equals("") && !surname.getText().trim().equals("") && !password.getText().trim().equals("") && !adminship.getValue().trim().equals("") && (adminship.getValue().trim().equalsIgnoreCase("true")|| adminship.getValue().trim().equalsIgnoreCase("false"))) {

				answer=true;
				admin.addEmployee(name.getText().trim(), surname.getText().trim(),username.getText().trim(), password.getText().trim(), adminship.getValue().trim());
				alertWindow.close();

			}

			else if(admin.exist("employee", username.getText().trim()))
				error.setText("Username already taken.");

			else
				error.setText("Please fill out all\nrequired fields.");

		} );

		ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
			StringProperty textProperty = (StringProperty) observable ;
			TextField textField = (TextField) textProperty.getBean();
			if(textField == name || textField == surname)
				if (!newValue.matches("\\sa-zA-Z*")) {
					textField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
				}
			if(textField == username) {
				newValue = newValue.trim();
				if(!newValue.matches("\\sa-zA-Z0-9*"))
					newValue= newValue.replaceAll("[^\\sa-zA-Z0-9]", "");
				if ((newValue.length() > 0)  && (!Character.toString(newValue.charAt(0)).matches("\\sa-zA-Z")))
					newValue = newValue.replaceAll("^[\\d]", "");
				textField.setText(newValue);
			}
		});
		name.textProperty().addListener(listener);
		surname.textProperty().addListener(listener);
		username.textProperty().addListener(listener);

		grid.getChildren().addAll(nameLabel, name, surnameLabel, surname, usernameLabel, username, passwordLabel, password, adminshipLabel , adminship, addBtn, error);

		Scene scene = new Scene(grid, 210, 420);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest( e -> {

			e.consume();
			alertWindow.close();

		} );

		return answer;
	}

	/**
	 * displays a window for adding a certain product to the client's cart
	 * @param client the client object that performs the action
	 * @param id the product's ID
	 * @param quantity the amount of products to be added to the client's cart
	 * @param title the window's title
	 */
	public static void addToCart(Client client, String id, String quantity, String title) {

		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle(title);
		alertWindow.setMinWidth(200);

		GridPane grid = new GridPane();
		grid.setId("add");
		grid.setPadding(new Insets(5,5,5,5));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label msgLabel = new Label("How many?");
		msgLabel.setId("labelAdd");
		Label quantityLabel = new Label("");
		quantityLabel.setId("labelAdd");
		GridPane.setConstraints(msgLabel, 1, 0);

		Slider selectQuantity = new Slider(1, Integer.parseInt(quantity), 1);
		selectQuantity.setBlockIncrement(1);
		selectQuantity.setMajorTickUnit(1);
		selectQuantity.setMinorTickCount(0);
		selectQuantity.setShowTickLabels(false);
		selectQuantity.setSnapToTicks(true);
		GridPane.setFillWidth(selectQuantity, true);

		if(quantity.equals("0")) {
			alertWindow.close();
			PopUpBox.alert("WARNING", "This product is currently unavailable!", 300);
		}
		else if(quantity.equals("1")) {
			
			if(!isProductInCart(client.getUsername(), id)) {
				selectQuantity.setVisible(false);
				msgLabel.setText("Only 1 item available");
				GridPane.setColumnSpan(msgLabel, 4);
				GridPane.setHalignment(msgLabel, HPos.CENTER);
				quantityLabel.setText("1");
			}
			else
				PopUpBox.alert("WARNING","This product is already in your cart!", 250);

		}
		else {

			quantityLabel.textProperty().bind(Bindings.format("%.0f", selectQuantity.valueProperty()));
			grid.add(quantityLabel, 2, 0);

		}

		grid.add(selectQuantity, 1, 1);
		GridPane.setColumnSpan(selectQuantity, 4);

		Button addBtn = new Button("Add");
		addBtn.setId("btnAdd");
		GridPane.setConstraints(addBtn, 1, 2);
		addBtn.setMaxWidth(Double.MAX_VALUE);

		Button cancelBtn = new Button("Cancel");
		cancelBtn.setId("btnAdd");
		GridPane.setConstraints(cancelBtn, 4, 2);
		cancelBtn.setMaxWidth(Double.MAX_VALUE);

		addBtn.setOnAction( e -> {

			client.addToCart(id,quantityLabel.getText().trim());
			cancelBtn.fire();
			alert("success", "succesfully added to the cart!", 250);
			
		} );

		cancelBtn.setOnAction( e -> {
			alertWindow.close();

		} );

		grid.getChildren().addAll(msgLabel, cancelBtn, addBtn);

		Scene scene = new Scene(grid, 200, 100);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);

		if(!quantity.equals("0") && !(quantity.equals("1") && isProductInCart(client.getUsername(), id))) 
			alertWindow.showAndWait();

		alertWindow.setOnCloseRequest(e -> {

			e.consume();
			alertWindow.close();

		});
	}

	/**
	 * displays a window for changing the quantity of a product in the client's cart
	 * @param client the client object that performs the action
	 * @param id the product's ID which quantity will be changed
	 * @return returns true if the quantity has been successfully edited
	 */
	public static boolean editQuantityCart(Client client, String id) {

		answer = false;
		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle("");
		alertWindow.setMinWidth(200);

		GridPane grid = new GridPane();
		grid.setId("edit");
		grid.setPadding(new Insets(5,5,5,5));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label msgLabel = new Label("New quantity:");
		msgLabel.setId("labelEdit");
		GridPane.setConstraints(msgLabel, 1, 0);

		TextField quantity = new TextField("");
		GridPane.setFillWidth(quantity, true);
		GridPane.setColumnSpan(quantity, 4);
		grid.add(quantity, 1, 1);

		Button enterBtn = new Button("Enter");
		enterBtn.setId("btnEdit");
		GridPane.setConstraints(enterBtn, 1, 2);
		enterBtn.setMaxWidth(Double.MAX_VALUE);
		enterBtn.setDisable(true);

		Button cancelBtn = new Button("Cancel");
		cancelBtn.setId("btnEdit");
		GridPane.setConstraints(cancelBtn, 4, 2);
		cancelBtn.setMaxWidth(Double.MAX_VALUE);

		quantity.textProperty().addListener( (ob, o, n) -> {
			if (!n.matches("\\d*")) 
				quantity.setText(n.replaceAll("[^\\d]", ""));
			enterBtn.setDisable(!(quantity.getText().trim().length() > 0));
		} );

		quantity.setOnKeyPressed( e -> {

			if( e.getCode() == KeyCode.ENTER )
				enterBtn.fire();

		} );

		enterBtn.setOnAction( e -> {
			cancelBtn.fire();

			if(Integer.parseInt(quantity.getText()) == 0) {
				if(confirm("", "Remove Product from cart?"))
					client.removeProduct(id);
			}
			else
				if(checkQuantity(id,quantity.getText()).equals("ok")) {
					answer = true;
					editQuantity(client.getUsername(), id, Integer.parseInt(quantity.getText()));
				}
				else if(!checkQuantity(id,quantity.getText()).equals("0") && !checkQuantity(id,quantity.getText()).equals("ok"))
					PopUpBox.alert("WARNING", "There are only " + checkQuantity(id,quantity.getText()) + " items available!", 300);

				else if(checkQuantity(id,quantity.getText()).equals("0")){
					PopUpBox.alert("WARNING", "This product is no longer available", 300);
				}

		} );

		cancelBtn.setOnAction( e -> {
			alertWindow.close();
		} );

		grid.getChildren().addAll(msgLabel, cancelBtn, enterBtn);

		Scene scene = new Scene(grid, 200, 100);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest( e -> {

			e.consume();
			alertWindow.close();

		} );

		return answer;
	}

	/**
	 * displays a window for executing a client's order
	 * @param client the client object that performs the action
	 */
	public static void placeOrder(Client client) {

		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle("");
		alertWindow.setMinWidth(340);

		GridPane grid = new GridPane();
		grid.setId("order");
		grid.setPadding(new Insets(5,5,5,5));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label msgLabel = new Label("Please, specify an address");
		msgLabel.setId("labelOrder");
		GridPane.setConstraints(msgLabel, 1, 0);

		TextField address = new TextField("");
		GridPane.setFillWidth(address, true);
		grid.add(address, 1, 1);
		GridPane.setColumnSpan(address, 4);

		Button continueBtn = new Button("Continue");
		continueBtn.setId("btnOrder");
		GridPane.setConstraints(continueBtn, 1, 3);
		continueBtn.setMaxWidth(Double.MAX_VALUE);

		CheckBox saveAddress = new CheckBox("Save address for future reference.");
		saveAddress.setId("labelSave");
		GridPane.setConstraints(saveAddress, 1, 2);
		saveAddress.setMaxWidth(Double.MAX_VALUE);

		Button cancelBtn = new Button("Cancel");
		cancelBtn.setId("btnOrder");
		GridPane.setConstraints(cancelBtn, 4, 3);
		cancelBtn.setMaxWidth(Double.MAX_VALUE);

		continueBtn.setOnAction( e -> {

			if(!address.getText().trim().equals("")) {
				alertWindow.close();
				placeOrder(client.getUsername(), address.getText().trim());
				if(saveAddress.isSelected()) {
					client.setNewAddress(address.getText().trim());
					editData("client", 5, client.getUsername(), address.getText().trim());
				}

			}

		} );

		cancelBtn.setOnAction(e -> {

			alertWindow.close();
			PopUpBox.alert("ORDER CANCELLED", "The order has been cancelled.", 300);


		});

		grid.getChildren().addAll(msgLabel, saveAddress, cancelBtn, continueBtn);

		Scene scene = new Scene(grid, 340, 150);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest(e -> {

			e.consume();
			alertWindow.close();

		});

	}

	/**
	 * @return returns 1 if the client has changed the username, returns 2 if the client has deleted his account
	 */
	static int resultManageProfile_GET() {
		return resultManageProfile;
	}

	private static void resultManageProfile_SET(int x) {
		resultManageProfile=x;
	}

	/**
	 * displays a window for changing the client's information
	 * @param client the client object that performs the action
	 */
	public static void manageProfileGUI(Client client) {

		resultManageProfile_SET(0);
		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle("MANAGE PROFILE");
		alertWindow.setMinWidth(400);

		GridPane grid = new GridPane();
		grid.setId("manageProfile");
		grid.setPadding(new Insets(20,20,20,20));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label output = new Label("");
		output.setId("manageProfileOutput");
		GridPane.setConstraints(output, 2, 2);
		output.setTextFill(Color.GREEN);

		TextField name = new TextField(client.getName());
		TextField surname = new TextField(client.getSurname());
		TextField username = new TextField(client.getUsername());
		TextField address = new TextField(client.getAddress());

		PasswordField password = new PasswordField();
		PasswordField passwordRepeat1 = new PasswordField();
		PasswordField passwordRepeat2 = new PasswordField();
		PasswordField passwordDeleteAccount = new PasswordField();
		PasswordField passwordRepeatDeleteAccount = new PasswordField();

		name.setPromptText("Name");
		surname.setPromptText("Surname");
		username.setPromptText("Username");
		address.setPromptText("Address");
		password.setPromptText("Current password");
		passwordRepeat1.setPromptText("New password");
		passwordRepeat2.setPromptText("New password, again");
		passwordDeleteAccount.setPromptText("Current password");
		passwordRepeatDeleteAccount.setPromptText("Repeat password");

		Label nameLabel = new Label("Name:");
		nameLabel.setId("manageProfileLabel");
		
		Label surnameLabel = new Label("Surname:");
		surnameLabel.setId("manageProfileLabel");
		
		Label usernameLabel = new Label("Username:");
		usernameLabel.setId("manageProfileLabel");
		
		Label addressLabel = new Label("Address:");
		addressLabel.setId("manageProfileLabel");
		
		Label passwordLabel = new Label("Password:");
		passwordLabel.setId("manageProfileLabel");
		
		Label deleteAccountLabel = new Label("DELETE ACCOUNT:");
		deleteAccountLabel.setId("manageProfileLabel");

		Button setNameBtn = new Button("Change");
		setNameBtn.setId("manageProfileBtn");
		
		Button setSurnameBtn = new Button("Change");
		setSurnameBtn.setId("manageProfileBtn");
		
		Button setUsernameBtn = new Button("Change");
		setUsernameBtn.setId("manageProfileBtn");
		
		Button setAddressBtn = new Button("Set");
		setAddressBtn.setId("manageProfileBtn");
		
		Button setPasswordBtn = new Button("Change");
		setPasswordBtn.setId("manageProfileBtn");
		
		Button deleteAccountBtn = new Button("DELETE ACCOUNT");
		deleteAccountBtn.setId("manageProfileBtn");

		setNameBtn.setDisable(true); 
		setSurnameBtn.setDisable(true);
		setUsernameBtn.setDisable(true); 
		setAddressBtn.setDisable(true); 
		setPasswordBtn.setDisable(true); 
		deleteAccountBtn.setDisable(true); 

		name.textProperty().addListener((observer) -> setNameBtn.setDisable(!(name.getText().trim().length() > 0)));
		surname.textProperty().addListener((observer) -> setSurnameBtn.setDisable(!(surname.getText().trim().length() > 0)));
		username.textProperty().addListener((observer) -> setUsernameBtn.setDisable(!(username.getText().trim().length() > 0)));
		address.textProperty().addListener((observer) -> {setAddressBtn.setDisable(!(address.getText().trim().length() > 0)); output.setText("");});

		ChangeListener<String> listener2 = ((observable, oldValue, newValue) -> {
			StringProperty textProperty = (StringProperty) observable ;
			TextField textField = (TextField) textProperty.getBean();
			if(textField == name || textField == surname)
				if (!newValue.matches("\\sa-zA-Z*")) {
					textField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
				}
			if(textField == username) {
				newValue = newValue.trim();
				if(!newValue.matches("\\sa-zA-Z0-9*"))
					newValue= newValue.replaceAll("[^\\sa-zA-Z0-9]", "");
				if ((newValue.length() > 0)  && (!Character.toString(newValue.charAt(0)).matches("\\sa-zA-Z")))
					newValue = newValue.replaceAll("^[\\d]", "");
				textField.setText(newValue); 
			}
			output.setText("");
		});
		
		name.textProperty().addListener(listener2);
		surname.textProperty().addListener(listener2);
		username.textProperty().addListener(listener2);

		ChangeListener<String> listener = ((ob, o, n) -> {
			setPasswordBtn.setDisable(!(password.getText().trim().length() > 0 && passwordRepeat1.getText().trim().length() > 0 && passwordRepeat2.getText().trim().length() > 0));
			deleteAccountBtn.setDisable(!(passwordDeleteAccount.getText().trim().length() > 0 && passwordRepeatDeleteAccount.getText().trim().length() > 0));

			output.setText("");
		});
		
		password.textProperty().addListener(listener);
		passwordRepeat1.textProperty().addListener(listener);
		passwordRepeat2.textProperty().addListener(listener);
		passwordDeleteAccount.textProperty().addListener(listener);
		passwordRepeatDeleteAccount.textProperty().addListener(listener);

		if(address.getText().trim().equals("none"))
			address.setText("");

		if(!address.getText().trim().equals(""))
			setAddressBtn.setText("Change");

		setNameBtn.setOnAction( e -> {

			if(name.getText().trim().equals(client.getName())) {
				nameLabel.setText("same with previous name!");
				nameLabel.setTextFill(Color.RED);
			}
			else if(!name.getText().trim().equals("")) {

				client.changeName(name.getText().trim());
				nameLabel.setText("Name:");
				nameLabel.setTextFill(Color.WHITE);
				GridPane.setConstraints(output, 2, 2);
				GridPane.setHalignment(output, HPos.LEFT);
				output.setText("Done!");

			}
			else {

				nameLabel.setText("This field cannot be empty.");
				nameLabel.setTextFill(Color.RED);

			}

		} );


		setSurnameBtn.setOnAction( e -> {

			if(surname.getText().trim().equals(client.getSurname())) {
				surnameLabel.setText("same with previous surname!");
				surnameLabel.setTextFill(Color.RED);
			}
			else if(!surname.getText().trim().equals("")) {

				client.changeSurname(surname.getText().trim());
				surnameLabel.setText("Surname:");
				surnameLabel.setTextFill(Color.WHITE);
				GridPane.setConstraints(output, 5, 2);
				GridPane.setHalignment(output, HPos.RIGHT);
				output.setText("Done!");

			}
			else {

				surnameLabel.setText("This field cannot be empty.");
				surnameLabel.setTextFill(Color.RED);

			}

		} );

		setAddressBtn.setOnAction( e -> {

			if(address.getText().trim().equals(client.getAddress())) {
				addressLabel.setText("same with previous address!");
				addressLabel.setTextFill(Color.RED);
			}
			else if(!address.getText().trim().equals("")) {

				client.setAddress(address.getText().trim());
				addressLabel.setText("Address:");
				addressLabel.setTextFill(Color.WHITE);
				GridPane.setConstraints(output, 5, 5);
				GridPane.setHalignment(output, HPos.RIGHT);
				output.setText("Done!");

			}
			else {

				addressLabel.setText("This field cannot be empty.");
				addressLabel.setTextFill(Color.RED);

			}
		} );

		setPasswordBtn.setOnAction( e -> {

			if(passwordRepeat1.getText().trim().equals(passwordRepeat2.getText().trim())) {

				if(passwordRepeat1.getText().trim().equals(client.getPassword())) {
					passwordLabel.setText("same with previous password!");
					passwordLabel.setTextFill(Color.RED);
				}
				else if(client.changePassword(password.getText().trim(), passwordRepeat1.getText().trim())) {

					passwordLabel.setText("Password:");
					passwordLabel.setTextFill(Color.WHITE);
					GridPane.setConstraints(output, 2, 10);
					GridPane.setHalignment(output, HPos.LEFT);
					output.setText("Done!");

				}
				else {

					passwordLabel.setText("Incorrect Password");
					passwordLabel.setTextFill(Color.RED);

				}
			}
			else {

				passwordLabel.setText("Passwords do not match");
				passwordLabel.setTextFill(Color.RED);

			}

		} );

		deleteAccountBtn.setOnAction(e -> {

			if(passwordDeleteAccount.getText().trim().equals(passwordRepeatDeleteAccount.getText().trim()) && client.getPassword().equals(passwordDeleteAccount.getText().trim())) {
				alertWindow.close();
				if(PopUpBox.confirm("WARNING", "DO YOU REALLY WANT TO\nDELETE YOUR ACCOUNT?")) {

					client.removeUser();
					resultManageProfile_SET(2);

				}

			}
			else if(!passwordDeleteAccount.getText().trim().equals(passwordRepeatDeleteAccount.getText().trim())) {

				deleteAccountLabel.setText("Passwords do not match");
				deleteAccountLabel.setTextFill(Color.RED);

			}
			else if(!client.getPassword().equals(passwordDeleteAccount.getText().trim())) {

				deleteAccountLabel.setText("Incorrect Password");
				deleteAccountLabel.setTextFill(Color.RED);

			}

		} );

		setUsernameBtn.setOnAction( e -> {

			if(!username.getText().trim().equals("")) {

				if(username.getText().trim().equals(client.getUsername())) {
					usernameLabel.setText("same with previous username!");
					usernameLabel.setTextFill(Color.RED);
				}
				else if(client.changeUsername(username.getText().trim())) {

					resultManageProfile_SET(1);
					usernameLabel.setText("Username:");
					usernameLabel.setTextFill(Color.WHITE);
					GridPane.setConstraints(output, 2, 5);
					GridPane.setHalignment(output, HPos.LEFT);
					output.setText("Done!");
				}

			}
			else {

				usernameLabel.setText("This field cannot be empty.");
				usernameLabel.setTextFill(Color.RED);

			}

		} );

		GridPane.setConstraints(nameLabel, 1, 0);
		GridPane.setConstraints(surnameLabel, 5, 0);
		GridPane.setConstraints(usernameLabel, 1, 3);
		GridPane.setConstraints(addressLabel, 5, 3);
		GridPane.setConstraints(passwordLabel, 1, 6);
		GridPane.setConstraints(deleteAccountLabel, 5, 6);
		GridPane.setConstraints(setNameBtn, 1, 2);
		GridPane.setConstraints(setSurnameBtn, 5, 2);
		GridPane.setConstraints(setUsernameBtn, 1, 5);
		GridPane.setConstraints(setAddressBtn, 5, 5);
		GridPane.setConstraints(setPasswordBtn, 1, 10);
		GridPane.setConstraints(deleteAccountBtn, 5, 9);

		grid.add(name, 1, 1);
		grid.add(surname, 5, 1);
		grid.add(address, 5, 4);
		grid.add(username, 1, 4);
		grid.add(password, 1, 7);
		grid.add(passwordRepeat1, 1, 8);
		grid.add(passwordRepeat2, 1, 9);
		grid.add(passwordDeleteAccount, 5, 7);
		grid.add(passwordRepeatDeleteAccount, 5, 8);

		GridPane.setColumnSpan(name, 4);
		GridPane.setColumnSpan(nameLabel, 4);
		GridPane.setColumnSpan(setNameBtn, 2);
		GridPane.setColumnSpan(surnameLabel, 4);
		GridPane.setColumnSpan(surname, 4);
		GridPane.setColumnSpan(setSurnameBtn, 2);
		GridPane.setColumnSpan(username, 4);
		GridPane.setColumnSpan(setUsernameBtn, 2);
		GridPane.setColumnSpan(address, 4);
		GridPane.setColumnSpan(setAddressBtn, 2);
		GridPane.setColumnSpan(password, 4);
		GridPane.setColumnSpan(passwordRepeat1, 4);
		GridPane.setColumnSpan(passwordRepeat2, 4);
		GridPane.setColumnSpan(setPasswordBtn, 2);
		GridPane.setColumnSpan(passwordDeleteAccount, 4);
		GridPane.setColumnSpan(passwordRepeatDeleteAccount, 4);
		GridPane.setFillWidth(address, true);
		GridPane.setFillWidth(surname, true);
		GridPane.setFillWidth(passwordDeleteAccount, true);
		GridPane.setFillWidth(passwordRepeatDeleteAccount, true);

		grid.getChildren().addAll(output, setNameBtn, setSurnameBtn, setUsernameBtn, setAddressBtn, setPasswordBtn, nameLabel, surnameLabel, usernameLabel, addressLabel, passwordLabel, deleteAccountLabel, deleteAccountBtn);

		Scene scene = new Scene(grid, 400, 350);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		alertWindow.setOnCloseRequest( e -> {

			e.consume();
			alertWindow.close();

		} );
	}

	/**
	 * @param client the client object that performs the action
	 * @return returns 1 if the client has changed the username, returns 2 if the client has deleted his account
	 */
	public static int manageProfile(Client client) {

		manageProfileGUI(client);
		return 	resultManageProfile_GET();

	}

	/**
	 * displays a window for performing the restock of a certain product
	 * @param employee the employee object that performs the action
	 * @param data the ObservableList containing the information of the product
	 * @param inStock if the product is still in stock
	 */
	public static void restock(Employee employee, ObservableList<String> data, boolean inStock) {
		alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);

		GridPane grid = new GridPane();
		grid.setId("restock");
		grid.setPadding(new Insets(5,5,5,5));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label msgLabel1 = new Label(data.get(1));
		msgLabel1.setUnderline(true);
		msgLabel1.setId("labelRestock");

		GridPane.setConstraints(msgLabel1, 1, 0);

		Label msgLabel = new Label("quantity: ");
		msgLabel.setId("labelRestock");
		GridPane.setConstraints(msgLabel, 1, 1);

		TextField quantity = new TextField();
		GridPane.setFillWidth(quantity, true);
		GridPane.setConstraints(quantity, 1, 2);

		Button enter = new Button("Enter");
		enter.setId("btnRestock");
		GridPane.setConstraints(enter, 1, 3);
		enter.setMaxWidth(Double.MAX_VALUE);
		enter.setDisable(true);

		Button cancelBtn = new Button("Cancel");
		cancelBtn.setId("btnRestock");
		GridPane.setConstraints(cancelBtn, 4, 3);
		cancelBtn.setMaxWidth(Double.MAX_VALUE);

		quantity.textProperty().addListener( (ob, o, n) -> {
			if (!n.matches("\\d*")) 
				quantity.setText(n.replaceAll("[^\\d]", ""));
			enter.setDisable(!((quantity.getText().trim().length() > 0) && (((Integer.parseInt(quantity.getText()) > 0) && inStock == false) || ((Integer.parseInt(quantity.getText()) >= 0) && inStock == true))));
		} );

		GridPane.setColumnSpan(quantity, 4);

		enter.setOnAction( ev -> {
			alertWindow.close();
			if (Integer.parseInt(quantity.getText()) == 0 && inStock == true) {
				if(PopUpBox.confirm("restock confirmation", "OUT OF STOCK?")) {
					employee.restock(data.get(0), + 0);
					employee.addRestock(data.toArray(new String[data.size()]));
				}	
			}
			else if(PopUpBox.confirm("restock confirmation", "Confirm Restock?")) {
				employee.restock(data.get(0), + Integer.parseInt(quantity.getText()));
			}
		} );

		cancelBtn.setOnAction( ev -> {

			alertWindow.close();

		} );

		quantity.setOnKeyPressed( event -> {

			if( event.getCode() == KeyCode.ENTER )
				enter.fire();

		} );

		grid.getChildren().addAll(msgLabel1, msgLabel, quantity, cancelBtn, enter);

		Scene scene = new Scene(grid, 200, 130);
		scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/application/application.css");
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();

	}

}
