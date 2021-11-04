package assegnamento;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public abstract class ManageData {

	private static String cart;
	private static String data;
	private static String username;
	private static String password;
	private static String isAdmin;
	private static String elementIdentifier;

	private static ArrayList<String> arrayData;

	private static ArrayList<String> productsIdList;
	private static ArrayList<String> productsNameList;
	private static ArrayList<String> productsManufacturerList;
	private static ArrayList<String> productsPriceList;
	private static ArrayList<String> productsQuantityList;


	private static String[] files= {"employees.xml", "products.xml", "users.xml", "deliveries.xml", "restocks.xml"};
	private static String[] user = {"name", "surname", "username", "password", "address", "cart"};
	private static String[] product = {"id", "name", "manufacturer", "price", "quantity"};
	private static String[] delivery = {"id", "name", "address", "cart"};
	private static String[] restock = {"id", "name", "manufacturer"};
	private static String[] employee = {"name", "surname", "username", "password", "admin"};

	protected static ArrayList<ArrayList<String>> elements;

	public static void addData(String type, String[] content)  {

		String[][] xElements = {employee, product, user, delivery, restock};
		int idxElements = 0;
		String xmlFile = "";
		Element newElement, data;

		if(type == "employee") {
			xmlFile = files[0];
			idxElements = 0;
		}
		else if(type == "product") {
			xmlFile = files[1];
			idxElements = 1;
		}
		else if(type == "user") {
			xmlFile = files[2];
			idxElements = 2;
		}
		else if(type == "delivery") {
			xmlFile = files[3];
			idxElements = 3;
		}
		else if(type == "restock") {
			xmlFile = files[4];
			idxElements = 4;
		}

		File fXmlFile = new File(".\\src\\assegnamento\\" + xmlFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder dBuilder;

		try {

			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			Element nList = doc.getDocumentElement();
			newElement = doc.createElement(type);

			if(type == "restock") 
				for(int i = 0; i < 3; i++) {
					data = doc.createElement(xElements[idxElements][i]);
					data.appendChild(doc.createTextNode(content[i]));
					newElement.appendChild(data);
				}
			else
				for(int i = 0; i < 4; i++) {
					data = doc.createElement(xElements[idxElements][i]);
					data.appendChild(doc.createTextNode(content[i]));
					newElement.appendChild(data);
				}


			if(type == "employee") {
				data = doc.createElement("admin");
				data.appendChild(doc.createTextNode(content[4]));
				newElement.appendChild(data);
			}

			if(type == "user") {
				data = doc.createElement("address");
				data.appendChild(doc.createTextNode("none"));
				newElement.appendChild(data);
				data = doc.createElement("cart");
				data.appendChild(doc.createTextNode("none"));
				newElement.appendChild(data);
			}

			if(type == "product") {
				data = doc.createElement("quantity");
				data.appendChild(doc.createTextNode("0"));
				newElement.appendChild(data);
			}

			nList.appendChild(newElement);
			save(doc, xmlFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void removeData(String type, String elementToDelete) {

		String[][] identifier = {{"employee", "product", "user", "delivery", "restock"},{ "username", "id","username", "id", "id"}};
		int idxTypeIdentifier = 0;
		String xmlFile = "";

		if(type == "employee") {
			xmlFile = files[0];
			idxTypeIdentifier = 0;
		}
		else if(type == "product") {
			xmlFile = files[1];
			idxTypeIdentifier = 1;
		}
		else if(type == "user") {
			xmlFile = files[2];
			idxTypeIdentifier = 2;
		}
		else if(type == "delivery") {
			xmlFile = files[3];
			idxTypeIdentifier = 3;
		}
		else if(type == "restock") {
			xmlFile = files[4];
			idxTypeIdentifier = 4;
		}

		File file = new File(".\\src\\assegnamento\\" + xmlFile);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		try {

			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList nodes = doc.getElementsByTagName(identifier[0][idxTypeIdentifier].toString());

			for (int i = 0; i < nodes.getLength(); i++) {

				Element xElement = (Element)nodes.item(i);
				Element xIdentifier = (Element)xElement.getElementsByTagName(identifier[1][idxTypeIdentifier].toString()).item(0);
				String currIdentifier = xIdentifier.getTextContent();

				if (currIdentifier.equals(elementToDelete))
					xElement.getParentNode().removeChild(xElement);

				save(doc, xmlFile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static boolean editData(String type, int option, String select, String content) {

		ArrayList<String> products;
		String xmlFile = "";
		boolean productAlreadyInCart=false;
		String[][] xElement = {{"username", "password", "name", "surname", "admin"}, product, {"username", "password", "name", "surname", "cart","address"}};
		int idxElement = 0;

		if(type == "user") {
			xmlFile = files[2]; idxElement = 2;
		}
		else if(type == "product") {
			xmlFile = files[1]; idxElement = 1;
		}
		else if(type == "employee") {
			xmlFile = files[0]; idxElement = 0;
		}

		Element doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName(type).getLength(); i++) {

			elementIdentifier = getTextValue(doc, xElement[idxElement][0], i);

			if (elementIdentifier != null && !elementIdentifier.isEmpty() && elementIdentifier.equals(select)) {

				if(xElement[idxElement][option] != "cart")
					setTextValue(doc, xElement[idxElement][option], i, content, xmlFile);

				else {
					cart = getTextValue(doc, xElement[idxElement][option], i);

					if(cart.equals("none"))
						setTextValue(doc, xElement[idxElement][option], i, content, xmlFile);

					else {

						if(content.contains("replace"))
							setTextValue(doc, xElement[idxElement][option], i, content.replace("replace", ""), xmlFile);

						else {

							products = new ArrayList <> (Arrays.asList(getTextValue(doc, "cart", i).split(",")));

							for(int j = 0; j < products.size() ;j++) {

								if( Arrays.asList(content.split(",")).get(0).equals(products.get(j))) {
									productAlreadyInCart=true;
									products.set(j+1, Integer.toString(Integer.parseInt(products.get(j+1)) + Integer.parseInt(Arrays.asList(content.split(",")).get(1))));
									break;
								}

								j++;
							}

							if(productAlreadyInCart)
								editData("user",4 ,select , "replace" + products.toString().replace("[", "").replace("]", "").replace(" ", ""));
							else
								setTextValue(doc, xElement[idxElement][option], i, cart + "," + content, xmlFile);
						}
					}
				}		
				return true;
			}
		}
		return false;
	}


	public static ArrayList<ArrayList<String>> readAll(String type){

		productsIdList = new ArrayList<String>();
		productsNameList = new ArrayList<String>();
		productsManufacturerList = new ArrayList<String>();
		productsQuantityList = new ArrayList<String>();
		productsPriceList = new ArrayList<String>();
		elements = new ArrayList<ArrayList<String>>();

		String xmlFile = "";
		String[][] xElements= {employee, product, user, delivery, restock};
		int idxElements = 0;

		if (type == "employee") {
			xmlFile = files[0]; idxElements = 0;
		}
		else if(type == "product") {
			xmlFile = files[1]; idxElements = 1;
		}
		if(type == "user") {
			xmlFile = files[2]; idxElements = 2;
		}
		else if(type == "delivery") {
			xmlFile = files[3]; idxElements = 3;
		}
		else if(type == "restock") {
			xmlFile = files[4]; idxElements = 4;
		}


		Element doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName(type).getLength(); i++) {

			arrayData = new ArrayList<String>();

			if(type == "restock") {
				for (int j = 0; j < 3; j++) {
					data = getTextValue(doc, xElements[idxElements][j], i);
					if ((data != null) && (!data.isEmpty())) {
						arrayData.add(data);
					}
				}

			}
			else
				for (int j = 0; j < 5; j++) {

					if ((type != "delivery")|| ((type == "delivery" )  && j < 4) )
						data = getTextValue(doc, xElements[idxElements][j], i);

					if ((data != null) && (!data.isEmpty())) {

						if(type == "product" && xElements[idxElements][j].equals("id")) {
							arrayData.add(data);
							productsIdList.add(data);
						}
						else if(type == "product" && xElements[idxElements][j].equals("name")) {
							arrayData.add(data);
							productsNameList.add(data);
						}
						else if(type == "product" && xElements[idxElements][j].equals("manufacturer")) {
							arrayData.add(data);
							productsManufacturerList.add(data);
						}
						else if(type == "product" && xElements[idxElements][j].equals("price")) {
							arrayData.add("$"+ data);
							productsPriceList.add(data);
						}
						else if(type == "product" && xElements[idxElements][j].equals("quantity")) {
							arrayData.add(data);
							productsQuantityList.add(data);
						}
						else
							arrayData.add(data);
					}
				}

			if(type == "employee") {
				isAdmin = getTextValue(doc, "admin", i);
				if ((isAdmin != null) && (!isAdmin.isEmpty())) {
					arrayData.add(isAdmin);
				}
			}

			if(type == "user") {
				cart = getTextValue(doc, "cart", i);
				if ((cart != null) && (!cart.isEmpty())){
					arrayData.add(cart);
				}
			}

			elements.add(arrayData);
		}

		return elements;
	}

	public static ArrayList<String> getProfile(String type, String usrn){

		String xmlFile = "";

		if(type == "employee") {
			xmlFile = files[0];
		}
		else if(type == "product") {
			xmlFile = files[1];
		}
		else if(type == "user") {
			xmlFile = files[2];
		}

		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(".\\src\\assegnamento\\"+xmlFile);
			Element doc = dom.getDocumentElement();

			arrayData = new ArrayList<String>();

			for (int i = 0; i < doc.getElementsByTagName(type).getLength(); i++) {

				username = getTextValue(doc, "username", i);

				if((username != null) && (!username.isEmpty()) && (username.equals(usrn))) {
					arrayData.add(username);

					password = getTextValue(doc, "password", i);
					if ( password != null) {
						if (!password.isEmpty())
							arrayData.add(password);
					}

					if(type != "user") {
						isAdmin = getTextValue(doc, "admin", i);
						if (isAdmin != null) {
							if (!isAdmin.isEmpty())
								arrayData.add(isAdmin);
						}
					}

					return arrayData;
				}
			}

		} catch (Exception e)    {
			e.printStackTrace();  
		} 

		return arrayData;
	}


	public static Element docBuilder(String file) {

		Document dom = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(".\\src\\assegnamento\\"+file);
		} catch (Exception e)    {
			e.printStackTrace();  
		} 
		return dom.getDocumentElement();
	}


	static String getTextValue(Element doc, String tag, int index) {

		String value = "";
		NodeList nl;
		nl = doc.getElementsByTagName(tag);

		if (nl.getLength() > 0 && nl.item(index).hasChildNodes()) {
			value = nl.item(index).getFirstChild().getNodeValue();
		}

		return value;
	}


	static void setTextValue(Element doc, String tag, int index, String content, String file) {

		NodeList nl;
		nl = doc.getElementsByTagName(tag);

		if (nl.getLength() > 0 && nl.item(index).hasChildNodes()) {
			nl.item(index).getFirstChild().setNodeValue(content);
		}

		save(doc, file);

	}


	public static void save(Document doc, String output) {

		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new File(".\\src\\assegnamento\\trim-whitespace.xslt")));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
			transformer.transform(new DOMSource(doc), new StreamResult(".\\src\\assegnamento\\" + output));
		} catch (Exception e)    {
			e.printStackTrace();  
		} 
	}

	public static void save(Element doc, String output) {

		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new File(".\\src\\assegnamento\\trim-whitespace.xslt")));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
			transformer.transform(new DOMSource(doc), new StreamResult(".\\src\\assegnamento\\" + output));
		} catch (Exception e)    {
			e.printStackTrace();  
		} 
	}


	public static boolean exist(String type, String select) {

		String xmlFile = "", identifier="";;

		if(type == "user") {
			xmlFile = files[2]; identifier = "username";
		}
		else if(type == "product") {
			xmlFile = files[1]; identifier = "id";
		}
		else if(type == "employee") {
			xmlFile = files[0]; identifier = "username";
		}
		else if(type == "delivery") {
			xmlFile = files[3]; identifier = "id";
		}

		Element doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName(type).getLength(); i++) {
			elementIdentifier = getTextValue(doc, identifier, i);
			if (elementIdentifier != null && !elementIdentifier.isEmpty() && elementIdentifier.equals(select)) {	
				return true;}
		}
		return false;

	}


	public static void editQuantity(String user){

		@SuppressWarnings("resource")
		Scanner input =	new Scanner(System.in);
		ArrayList<String> products = null;
		String xmlFile = files[2], idProduct = ""; ; 
		int fixedIndex = 0;

		Element doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {
			username = getTextValue(doc, "username", i);

			if (username != null && !username.isEmpty() && username.equals(user)) {

				if(getTextValue(doc, "cart", i).equals("none")) {
					System.out.println("The cart is empty! There is nothing to change.");
					return;
				}

				products = new ArrayList <> (Arrays.asList(getTextValue(doc, "cart", i).split(",")));
				break;
			}

		}

		System.out.print("Which product number? " );
		int indexProductChosen = input.nextInt();

		if(indexProductChosen > products.size()/2) {
			System.out.print("There are only " + products.size()/2 + " products in your cart!");
			return;
		}

		System.out.print("Enter the new quantity: " );
		int newQuantity = input.nextInt();

		if(indexProductChosen != 1) {
			indexProductChosen--;
			for(int i = 0; indexProductChosen != 0; i++, indexProductChosen--) {
				i++;
				if(indexProductChosen == 1) {
					i++;
					fixedIndex = i;
					idProduct = products.get(i);
				}
			}
		}
		else {
			idProduct = products.get(0);
			fixedIndex = 0;
		}

		int idx = productsIdList.indexOf(idProduct);
		if(newQuantity <= Integer.parseInt(productsQuantityList.get(idx))) {
			products.set(fixedIndex+1, Integer.toString(newQuantity));
			editData("user",4 ,user , "replace" + products.toString().replace("[", "").replace("]", "").replace(" ", ""));
			System.out.println("Done!");
		}
		else
			System.out.println("There are only " + productsQuantityList.get(idx) + " " + productsManufacturerList.get(idx) + " " + productsNameList.get(idx) + " available!");

	}


	public static void removeFromCart(String user){

		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		ArrayList<String> products = null;
		String xmlFile = files[2], idProduct = "";




		Element doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {
			username = getTextValue(doc, "username", i);
			if (username != null && !username.isEmpty() && username.equals(user)) {
				if(getTextValue(doc, "cart", i).equals("none")) {
					System.out.println("The cart is empty! There is nothing to remove.");
					return;
				}
				products = new ArrayList <> (Arrays.asList(getTextValue(doc, "cart", i).split(",")));
				break;
			}
		}


		System.out.print("Which product number? " );
		int indexProductChosen = input.nextInt();

		if(indexProductChosen > products.size()/2) {
			System.out.print("There are only " + products.size()/2 + " products in your cart!");
			return;
		}

		if(indexProductChosen == 1)
			indexProductChosen--;


		for(int i = 0; i < products.size() && i <= indexProductChosen;i++) {
			idProduct = products.get(i);
			i++;
		}
		int idxProductRemoved = products.indexOf(idProduct);
		products.remove(idxProductRemoved);
		products.remove(idxProductRemoved);
		if(!products.isEmpty())
			editData("user",4 ,user , "replace" + products.toString().replace("[", "").replace("]", "").replace(" ", ""));
		else
			editData("user",4 ,user , "replacenone");

		System.out.println("Done!");
	}


	public static void showCart(String user) {

		ArrayList<String> products = null;
		String xmlFile = files[2];
		int idx = 0, productNumber = 1;
		float total = 0;

		Element doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {

			username = getTextValue(doc, "username", i);
			if (username != null && !username.isEmpty() && username.equals(user)) {
				products = new ArrayList <> (Arrays.asList(getTextValue(doc, "cart", i).split(",")));
				break;
			}

		}

		System.out.format("%-25s%-25s%-25s%-25s","PRODUCT", "QUANTITY", "MANUFACTURER", "PRICE");
		System.out.println("\n----------------------------------------------------------------------------------");

		for(int i = 0; i < products.size();i++) {

			if(i % 2 == 0) {
				idx = productsIdList.indexOf(products.get(i));
			}
			else {
				System.out.format("%-25s%-25s%-25s%-25s", productNumber + ") " + productsNameList.get(idx),  "x" + products.get(i), productsManufacturerList.get(idx) , "$" + String.format("%.2f", Float.parseFloat(productsPriceList.get(idx)) * Integer.parseInt(products.get(i))));
				System.out.println();
				productNumber++;
				total+=Float.parseFloat(productsPriceList.get(idx))*Integer.parseInt(products.get(i));
			}

		}
		System.out.format("%-25s%-23s%-20s%1s","", "", "", "Total: $" + String.format("%.2f", total));
		System.out.println();
	}

	public static void placeOrder(String user){

		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		ArrayList<String> products = null;
		String[] order = new String[4];
		String xmlFile = files[2], decision, idProductOrdered = "", idProduct = "", saveForLater = "", availableProductsTemp = "";
		int deliveryID = 0, newQuantity = 0, idx = 0, discardItem = 0;;
		boolean unavailableItems=false;

		Element doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {
			username = getTextValue(doc, "username", i);
			if (username != null && !username.isEmpty() && username.equals(user)) {
				order[1] = getTextValue(doc, "name", i) + " " + getTextValue(doc, "surname", i);
				order[2] = getTextValue(doc, "address", i);
				order[3] = getTextValue(doc, "cart", i);
				if (order[3].equals("none")) {
					System.out.println("The cart is empty! Please add something to the cart.");
					return;
				}

				if(order[2].equals("none")) {

					System.out.print("The address hasn't been set, please specify an address: ");
					order[2] = input.nextLine();
					System.out.print("\n Do you want to save this address? (Y/N): ");
					decision = input.nextLine();

					if((decision.equalsIgnoreCase("y"))) {
						editData("user", 5, user, order[2]);
						System.out.println("The address has been saved!");
					}
				}
				else {

					System.out.println("\nThe products will be shipped to '" + order[2] + "'");
					System.out.print("\nDo you want to send them to this address? (Y/N): ");
					decision = input.nextLine();

					if((!decision.equalsIgnoreCase("y"))) {

						System.out.println("Please specify an address: ");
						order[2] = input.nextLine();
						System.out.print("\nDo you want to set this as your default address? (Y/N): ");
						decision = input.nextLine();

						if((decision.equalsIgnoreCase("y"))) {
							editData("user", 5, user, order[2]);
							System.out.println("The address has been saved!");
						}
					}
				}
				products = new ArrayList <> (Arrays.asList(order[3].split(",")));
				break;
			}
		}

		readAll("product");
		for(int i = 0; i < products.size();i++) {
			if(i % 2 == 0) {
				idx = productsIdList.indexOf(products.get(i));
			}
			else {

				if(Integer.parseInt(products.get(i)) > Integer.parseInt(productsQuantityList.get(idx))){

					unavailableItems=true;

					if(Integer.parseInt(productsQuantityList.get(idx)) == 0) {

						System.out.print("\n" + productsManufacturerList.get(idx) + " " + productsNameList.get(idx) + " is currently out of stock, therefore it cannot be sent. \nDo you want to keep it in the cart? (Y/N): ");
						decision = input.nextLine();
						if((decision.equalsIgnoreCase("y"))) {
							if(saveForLater.equals(""))
								saveForLater += productsIdList.get(idx) + "," + products.get(i);
							else
								saveForLater += "," + productsIdList.get(idx) + "," + products.get(i);
						}
						discardItem += 2;
						products.remove(i-1);
						products.remove(i-1);
						order[3] = products.toString().replace("[", "").replace("]", "").replace(" ", "");

					}

					else {

						System.out.print("\nUnfortunately, there are only " + productsQuantityList.get(idx) + " " + productsManufacturerList.get(idx) + " " + productsNameList.get(idx) + " available. \nDo you still wish to purchase it? (Y/N):  " );
						decision = input.nextLine();
						if((decision.equalsIgnoreCase("y"))) {

							products.set(i, productsQuantityList.get(idx));
							order[3] = products.toString().replace("[", "").replace("]", "").replace(" ", "");

						}
						else {
							if(saveForLater.equals(""))
								saveForLater += productsIdList.get(idx) + "," + products.get(i);
							else
								saveForLater += "," + productsIdList.get(idx) + "," + products.get(i);
							discardItem += 2;
							products.remove(i-1);
							products.remove(i-1);
							order[3] = products.toString().replace("[", "").replace("]", "").replace(" ", "");
						}
					}

				}

				else {					
					if(availableProductsTemp.equals(""))
						availableProductsTemp += productsIdList.get(idx) + "," + products.get(i);
					else
						availableProductsTemp += "," + productsIdList.get(idx) + "," + products.get(i);					
				}
				i -= discardItem;
				discardItem = 0;

			}
		}

		if(unavailableItems) {
			System.out.print("Do you still want to proceed with the order of the remaining available items?(Y/N): ");
			decision = input.nextLine();
			if((!decision.equalsIgnoreCase("y"))) { 

				order[3] = ""; 

				if(!saveForLater.equals(""))
					saveForLater += "," + availableProductsTemp;
				else
					saveForLater += availableProductsTemp;
			}
		}

		if(saveForLater.equals(""))
			editData("user",4 ,user , "replacenone");
		else
			editData("user",4 ,user , "replace" + saveForLater);

		if(order[3].equals("")) {
			System.out.println("The order has been cancelled.");
			return;
		}

		for(int j = 0; j < products.size()-1; j++) {
			deliveryID = Integer.parseInt(products.get(j)) + Integer.parseInt(products.get(j+1));
		}

		deliveryID += ThreadLocalRandom.current().nextInt(1, 10000 + 1);
		order[0] = "#" + Integer.toString(deliveryID);

		if(exist("delivery", order[0])) {

			while(exist("delivery", order[0])) {
				deliveryID += ThreadLocalRandom.current().nextInt(1, 10000 + 1);
				order[0] = "#" + Integer.toString(deliveryID);
			}

		}

		Element doc2 = docBuilder(files[1]);

		for(int i = 0; i < products.size();i++) {

			if(i % 2 == 0) {
				idProductOrdered = products.get(i);
			}
			else {
				for (int j = 0; j < doc2.getElementsByTagName("product").getLength(); j++) {
					idProduct = getTextValue(doc2, "id", j);
					if (idProduct != null && !idProduct.isEmpty() && idProduct.equals(idProductOrdered)) {
						newQuantity = Integer.parseInt(getTextValue(doc2, "quantity", j));
						newQuantity = newQuantity - Integer.parseInt(products.get(i));
						setTextValue(doc2, "quantity", j, Integer.toString(newQuantity), files[1]);

						if(newQuantity == 0) {
							String [] fields = new String[3];
							fields[0] = idProduct;
							fields[1] = getTextValue(doc2, "name", j);
							fields[2] = getTextValue(doc2, "manufacturer", j);
							addData("restock", fields);
						}
						break;
					}
				}
			}
		}

		System.out.println("\nOrder confirmed to address '" + order[2] + "'");
		addData("delivery", order);
	}


	public static void filterList(int index, String str) {

		ArrayList<ArrayList<String>> filteredList = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < elements.size(); i++) {

			if(index == 3) {
				if(Float.parseFloat(elements.get(i).get(index).substring(1)) < Float.parseFloat(str))
					filteredList.add(elements.get(i));
			}
			else if(index == 4) {
				if(Float.parseFloat(elements.get(i).get(index-1).substring(1)) > Float.parseFloat(str))
					filteredList.add(elements.get(i));
			}
			else {
				if((elements.get(i).get(index)).toLowerCase().contains(str.toLowerCase()))
					filteredList.add(elements.get(i));
			}
		} 

		elements = new ArrayList<ArrayList<String>>();
		elements = filteredList;
	}



	public void printList(){

		if(elements.size() == 0) {
			System.out.print("\nNo Result\n");
			return;
		}

		for (int i = 0; i < elements.size(); i++) {

			System.out.print("\n" + (i+1) + ")  ");

			for (int j = 1; j < elements.get(i).size()-1; j++) {

				if(j == 3 && (Integer.parseInt(elements.get(i).get(j+1)) == 0))
					System.out.format("%-25s","out of stock!");
				else
					System.out.format("%-25s",elements.get(i).get(j));	
			}
			System.out.println();
		}
		System.out.println();	
	} 
}


