package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

/**
 * 
 * a class that manages data in database
 *
 */
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
	protected static ArrayList<String> productsManufacturerList;
	private static ArrayList<String> productsPriceList;
	private static ArrayList<String> productsQuantityList;


	private static String[] files= {"Database/employees.xml", "Database/products.xml", "Database/users.xml", "Database/deliveries.xml", "Database/restocks.xml"};
	private static String[] client = {"name", "surname", "username", "password", "address", "cart"};
	private static String[] product = {"id", "name", "manufacturer", "price", "quantity"};
	private static String[] delivery = {"id", "name", "address", "cart"};
	private static String[] restock = {"id", "name", "manufacturer"};
	private static String[] employee = {"name", "surname", "username", "password", "admin"};
	/**
	 * arrayList of arrayList of string type of data read from XML file
	 */
	protected static ArrayList<ArrayList<String>> elements;
	protected static ArrayList<ArrayList<String>> elementsCart;
	protected static float totalPrice;

	/**
	 * adds data to database
	 * @param type data type to add to database (users, products, etc..) 
	 * @param content data to add
	 */
	public static void addData(String type, String[] content)  {

		String[][] xElements = {employee, product, client, delivery, restock};
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
		else if(type == "client") {
			xmlFile = files[2];
			idxElements = 2;
			type="user";
		}
		else if(type == "delivery") {
			xmlFile = files[3];
			idxElements = 3;
		}
		else if(type == "restock") {
			xmlFile = files[4];
			idxElements = 4;
		}

		File fXmlFile = new File(".\\src\\" + xmlFile);
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
				data.appendChild(doc.createTextNode(content[4]));
				newElement.appendChild(data);
			}

			nList.appendChild(newElement);
			save(doc, xmlFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * removes data from database
	 * @param type data type to remove from database (users, products, etc..) 
	 * @param elementToDelete data to remove
	 */
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
		else if(type == "client") {
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

		File file = new File(".\\src\\" + xmlFile);
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

				if (currIdentifier.equals(elementToDelete)) {
					xElement.getParentNode().removeChild(xElement);
					save(doc, xmlFile);
				}



			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * edits data in database
	 * @param type data type to edit in database (users, products, etc..) 
	 * @param option index of data in database
	 * @param select element to edit
	 * @param content new value
	 * @return true if edited successfully
	 */
	public static boolean editData(String type, int option, String select, String content) {

		ArrayList<String> products;
		String xmlFile = "";
		boolean productAlreadyInCart=false;
		String[][] xElement = {{"username", "password", "name", "surname", "admin"}, product, {"username", "password", "name", "surname", "cart","address"}};
		int idxElement = 0;

		if(type == "client") {
			xmlFile = files[2]; idxElement = 2;
			type="user";
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
								editData("client",4 ,select , "replace" + products.toString().replace("[", "").replace("]", "").replace(" ", ""));
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


	/**
	 * reads all data to database
	 * @param type data type to remove to database (users, products, etc..) 
	 * @return arrayList of arrayList of strings containing the data read
	 */
	public static ArrayList<ArrayList<String>> readAll(String type){

		productsIdList = new ArrayList<String>();
		productsNameList = new ArrayList<String>();
		productsManufacturerList = new ArrayList<String>();
		productsQuantityList = new ArrayList<String>();
		productsPriceList = new ArrayList<String>();
		elements = new ArrayList<ArrayList<String>>();

		String xmlFile = "";
		String[][] xElements= {employee, product, client, delivery, restock};
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
							arrayData.add(data);
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

			if(type == "client") {
				cart = getTextValue(doc, "cart", i);
				if ((cart != null) && (!cart.isEmpty())){
					arrayData.add(cart);
				}
			}
			
			if(type == "product") {
				if(Integer.parseInt(getTextValue(doc, "quantity", i)) == 0 && !exist("restock", getTextValue(doc, "id", i))) {
					addData("restock", arrayData.toArray(new String[arrayData.size()]));
				}
			}

			elements.add(arrayData);
		}
		return elements;
	}

	/**
	 * @param type the profile type to get from database
	 * @param usrn username (key)
	 * @return returns an ArrayList of string type that contains the profile gotten from the database
	 */
	public static ArrayList<String> getProfile(String type, String usrn){

		String xmlFile = "";

		if(type == "employee") {
			xmlFile = files[0];
		}
		else if(type == "product") {
			xmlFile = files[1];
		}
		else if(type == "client") {
			type="user";
			xmlFile = files[2];
		}

		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(".\\src\\"+xmlFile);
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

					if(type == "user") {
						data = getTextValue(doc, "name", i);
						if (data != null) {
							if (!data.isEmpty())
								arrayData.add(data);
						}
					}

					if(type == "user") {
						data = getTextValue(doc, "surname", i);
						if (data != null) {
							if (!data.isEmpty())
								arrayData.add(data);
						}
					}

					if(type == "user") {
						data = getTextValue(doc, "address", i);
						if (data != null) {
							if (!data.isEmpty())
								arrayData.add(data);
						}
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

	/**
	 * defines API to obtain DOM document instances from an XML file
	 * @param file an XML file
	 * @return returns the root that contains other elements
	 */
	@SuppressWarnings("exports")
	public static Element docBuilder(String file) {

		Document dom = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(".\\src\\"+file);
			
		} catch (Exception e)    {
			e.printStackTrace();  
		} 
		
		return dom.getDocumentElement();
	}

	/**
	 * @param doc the document element that is been referred to
	 * @param tag an XML tag in which the value is to be retrieved
	 * @param index the index (position) of the XML tag
	 * @return the value of the gotten from database
	 */
	static String getTextValue(Element doc, String tag, int index) {

		String value = "";
		NodeList nl;
		nl = doc.getElementsByTagName(tag);

		if (nl.getLength() > 0 && nl.item(index).hasChildNodes()) {
			
			value = nl.item(index).getFirstChild().getNodeValue();
			
		}

		return value;
	}

	/**
	 * sets the value of an XML element in the database
	 * @param doc the document element that is been referred to
	 * @param tag the tag whose value is to be modified
	 * @param index the index (position) of the XML tag
	 * @param content the value to be set
	 * @param file the XML file to work on
	 */
	static void setTextValue(Element doc, String tag, int index, String content, String file) {

		NodeList nl;
		nl = doc.getElementsByTagName(tag);

		if (nl.getLength() > 0 && nl.item(index).hasChildNodes()) {
			
			nl.item(index).getFirstChild().setNodeValue(content);
			
		}

		save(doc, file);
	}

	/**
	 * save a file (modified)
	 * @param doc the document that is been referred to
	 * @param output file to be saved
	 */
	@SuppressWarnings("exports")
	public static void save(Document doc, String output) {

		try {
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new File(".\\src\\Database\\trim-whitespace.xslt")));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
			transformer.transform(new DOMSource(doc), new StreamResult(".\\src\\" + output));
			
		} catch (Exception e)    {
			e.printStackTrace();  
		} 
	}

	/**
	 * save a file (modified)
	 * @param doc  doc the document that is been referred to
	 * @param output  file to be saved
	 */
	@SuppressWarnings("exports")
	public static void save(Element doc, String output) {

		try {
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new File(".\\src\\Database\\trim-whitespace.xslt")));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
			transformer.transform(new DOMSource(doc), new StreamResult(".\\src\\" + output));
			
		} catch (Exception e)    {
			e.printStackTrace();  
		} 
	}

	/**
	 * verifies if data exists already in database
	 * @param type data type
	 * @param select value to control
	 * @return true if data exists already in database
	 */
	public static boolean exist(String type, String select) {

		String xmlFile = "", identifier = "";

		if(type == "client") {
			xmlFile = files[2]; identifier = "username";
			type="user";
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
		else if(type == "restock") {
			xmlFile = files[4]; identifier = "id";
		}

		Element doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName(type).getLength(); i++) {
			
			elementIdentifier = getTextValue(doc, identifier, i);
			
			if (elementIdentifier != null && !elementIdentifier.isEmpty() && elementIdentifier.equals(select))
				return true;
		}
		
		return false;
	}

	/**
	 * edits the quantity in the cart of a client
	 * @param client client's username
	 * @param id product's ID
	 * @param newQuantity new quantity
	 */
	public static void editQuantity(String client, String id, int newQuantity){

		ArrayList<String> products = null;
		String xmlFile = files[2];
		int fixedIndex = 0;

		Element doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {
			username = getTextValue(doc, "username", i);

			if (username != null && !username.isEmpty() && username.equals(client)) {

				if(getTextValue(doc, "cart", i).equals("none")) {
					return;
				}

				products = new ArrayList <> (Arrays.asList(getTextValue(doc, "cart", i).split(",")));
				break;
			}

		}
		
		for(int i = 0; products.size()-1 != 0; i++) {
			
			if(i%2 == 0) {
				
				if(id.equals(products.get(i))) {
					
					fixedIndex = i+1;
					break;
					
				}
			}
		}

		products.set(fixedIndex, Integer.toString(newQuantity));
		editData("client",4 ,client , "replace" + products.toString().replace("[", "").replace("]", "").replace(" ", ""));

	}
	
	
	/**
	 * checks if a product is in the cart
	 * @param client client's username
	 * @param id product's ID
	 * @return true if the product is in the cart
	 */
	public static boolean isProductInCart(String client, String id){

		ArrayList<String> products = null;
		String xmlFile = files[2];

		Element doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {
			username = getTextValue(doc, "username", i);

			if (username != null && !username.isEmpty() && username.equals(client)) {

				if(getTextValue(doc, "cart", i).equals("none"))
					return false;

				products = new ArrayList <> (Arrays.asList(getTextValue(doc, "cart", i).split(",")));
				break;
			}

		}
		
		for(int i = 0; i < products.size(); i++)
			if(i%2 == 0)
				if(id.equals(products.get(i)))
					return true;
		
		return false;
	}
	

	/**
	 * checks if a product is still available
	 * @param id product's ID
	 * @param newQuantity quantity to check
	 * @return "ok" if the product is still available, otherwise it returns the quantity still available
	 */
	public static String checkQuantity(String id, String newQuantity) {

		int idx = productsIdList.indexOf(id);
		
		if(Integer.parseInt(newQuantity) <= Integer.parseInt(productsQuantityList.get(idx)))
			return "ok";
		else
			return productsQuantityList.get(idx);

	}


	/**
	 * removes a product from cart
	 * @param client client's username
	 * @param id product's ID
	 * @return returns true if the product is successfully removed from the cart
	 */
	public static boolean removeFromCart(String client, String id){

		ArrayList<String> products = null;
		String xmlFile = files[2];
		Element doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {
			
			username = getTextValue(doc, "username", i);
			
			if (username != null && !username.isEmpty() && username.equals(client)) {
				
				if(getTextValue(doc, "cart", i).equals("none"))
					return false;
				
				products = new ArrayList <> (Arrays.asList(getTextValue(doc, "cart", i).split(",")));
				break;
			}
			
		}
		
		if(products.indexOf(id)!=-1) {
			
			int idxProductRemoved = products.indexOf(id);
			products.remove(idxProductRemoved);
			products.remove(idxProductRemoved);
			
			if(!products.isEmpty())
				editData("client",4 ,client , "replace" + products.toString().replace("[", "").replace("]", "").replace(" ", ""));
			else
				editData("client",4 ,client , "replacenone");

			return true;
		}
		
		return false;
		
	}

	/**
	 * removes a certain product type from all carts and deliveries
	 * @param id ID of the product to remove
	 */
	public static void removeProductType(String id){

		ArrayList<String> products = null;
		String xmlFile;
		ArrayList<String> deliveriesToDelete = new ArrayList<String>();
		ArrayList<String> cartsToEdit = new ArrayList<String>();
		String idDelivery="", username="";
		String[][] xElements= {employee, product, client, delivery, restock};
		int idxElements = 0;
		xmlFile = files[3]; idxElements = 3;

		Element doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName("delivery").getLength(); i++) {

			arrayData = new ArrayList<String>();
			data = getTextValue(doc, xElements[idxElements][0], i);

			if ((data != null) && (!data.isEmpty())) {

				idDelivery=data;
				products = new ArrayList <> (Arrays.asList(getTextValue(doc, "cart", i).split(",")));

				if(products.indexOf(id)!=-1) {

					int idxProductRemoved = products.indexOf(id);
					products.remove(idxProductRemoved);
					products.remove(idxProductRemoved);

					if(!products.isEmpty()) {

						NodeList nl;
						nl = doc.getElementsByTagName(xElements[idxElements][3]);

						if (nl.getLength() > 0 && nl.item(i).hasChildNodes()) {
							nl.item(i).getFirstChild().setNodeValue(products.toString().replace("[", "").replace("]", "").replace(" ", ""));
						}

					}
					else 
						deliveriesToDelete.add(idDelivery);
				}
			}
		}
		save(doc, xmlFile);

		for(int i=0;i<deliveriesToDelete.size();i++)
			removeData("delivery", deliveriesToDelete.get(i));


		xmlFile = files[2]; idxElements = 2;
		doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {

			arrayData = new ArrayList<String>();
			data = getTextValue(doc, xElements[idxElements][2], i);

			if ((data != null) && (!data.isEmpty())) {

				username=data;

				if(removeFromCart(username,id))
					cartsToEdit.add(username);

			}
		}

		save(doc, xmlFile);

		for(int i=0;i<cartsToEdit.size();i++)
			removeFromCart(cartsToEdit.get(i),id);
	}

	/**
	 * reads the info of a client's cart
	 * @param client client's username
	 */
	public static void readCart(String client) {
		
		elementsCart = new ArrayList<ArrayList<String>>();

		ArrayList<String> products = null;
		String xmlFile = files[2];
		int idx = 0;
		float total = 0;

		Element doc = docBuilder(xmlFile);

		for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {

			username = getTextValue(doc, "username", i);
			
			if (username != null && !username.isEmpty() && username.equals(client)) {
				
				products = new ArrayList <> (Arrays.asList(getTextValue(doc, "cart", i).split(",")));
				break;
				
			}

		}

		for(int i = 0; i < products.size();i++) {
			arrayData = new ArrayList<String>();

			if(i % 2 == 0)
				idx = productsIdList.indexOf(products.get(i));
			
			else {
				
				arrayData.add(productsIdList.get(idx));
				arrayData.add(productsNameList.get(idx));
				arrayData.add(products.get(i));
				arrayData.add(productsManufacturerList.get(idx));
				arrayData.add(Float.toString((Float.parseFloat(productsPriceList.get(idx)) * Integer.parseInt(products.get(i)))));
				elementsCart.add(arrayData);

				total+=Float.parseFloat(productsPriceList.get(idx))*Integer.parseInt(products.get(i));
				totalPrice = total;
			}
		}
	}

	/**
	 * executes a client's order
	 * @param client client's username
	 * @param address client's shipping address
	 */
	public static void placeOrder(String client, String address){

		if(PopUpBox.confirm("PLACE ORDER", "Do you wish to proceed with the\npurchase? You are going to buy all the\nproducts in your cart.")) {
			ArrayList<String> products = null;
			String[] order = new String[4];
			String xmlFile = files[2], decision, idProductOrdered = "", idProduct = "", saveForLater = "", availableProductsTemp = "";
			int deliveryID = 0, newQuantity = 0, idx = 0, discardItem = 0;;
			boolean unavailableItems=false;

			Element doc = docBuilder(xmlFile);

			for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {
				
				username = getTextValue(doc, "username", i);
				
				if (username != null && !username.isEmpty() && username.equals(client)) {
					
					order[1] = getTextValue(doc, "name", i) + " " + getTextValue(doc, "surname", i);
					order[2] = address;
					order[3] = getTextValue(doc, "cart", i);
					products = new ArrayList <> (Arrays.asList(order[3].split(",")));
					break;
					
				}
			}

			readAll("product");
			
			for(int i = 0; i < products.size();i++) {
				
				if(i % 2 == 0)
					idx = productsIdList.indexOf(products.get(i));
				
				else {

					if(Integer.parseInt(products.get(i)) > Integer.parseInt(productsQuantityList.get(idx))){

						unavailableItems=true;

						if(Integer.parseInt(productsQuantityList.get(idx)) == 0) {

							if(PopUpBox.confirm("WARNING", productsManufacturerList.get(idx) + " " + productsNameList.get(idx) + " is currently \nout of stock, therefore it cannot be sent. \nDo you want to keep it in the cart?"))
								decision = "y";
							else
								decision = "n";

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

							if(PopUpBox.confirm("WARNING", "Unfortunately, there are only \n" + productsQuantityList.get(idx) + " " + productsManufacturerList.get(idx) + " " + productsNameList.get(idx) + " available. \nDo you still wish to purchase it?"))
								decision = "y";
							else
								decision = "n";

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
				
				if(PopUpBox.confirm("WARNING", "Do you still wish to proceed with the \norder of the remaining available items?"))
					decision = "y";
				else
					decision = "n";

				if((!decision.equalsIgnoreCase("y"))) { 

					order[3] = ""; 

					if(!saveForLater.equals(""))
						saveForLater += "," + availableProductsTemp;
					else
						saveForLater += availableProductsTemp;
				}
			}

			if(saveForLater.equals(""))
				editData("client",4 ,client , "replacenone");
			else
				editData("client",4 ,client , "replace" + saveForLater);

			if(order[3].equals("")) {
				PopUpBox.alert("ORDER CANCELLED", "The order has been cancelled.", 300);
				return;
			}

			for(int j = 0; j < products.size()-1; j++)
				deliveryID = Integer.parseInt(products.get(j)) + Integer.parseInt(products.get(j+1));

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

				if(i % 2 == 0)
					idProductOrdered = products.get(i);
				
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

			PopUpBox.alert("ORDERED SUCCESSFULLY", "Order confirmed to address \n'" + order[2] + "'", 300);
			addData("delivery", order);
		}
		
		else
			PopUpBox.alert("ORDER CANCELLED", "The order has been cancelled.", 300);
		
		return;
	}

	/**
	 * filters a list according to the request (serves to search a product)
	 * @param index value position to use in filtering
	 * @param str value of comparison
	 * @return returns the filtered list
	 */
	public static ArrayList<ArrayList<String>> filterList(int index, String str) {

		ArrayList<ArrayList<String>> filteredList = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < elements.size(); i++) {

			if(index == 2) {
				if((elements.get(i).get(index)).toLowerCase().contains(str.toLowerCase()))
					filteredList.add(elements.get(i));
			}
			
			else {
				
				if((elements.get(i).get(1)).toLowerCase().contains(str.toLowerCase()))
					if(!filteredList.contains(elements.get(i)))
						filteredList.add(elements.get(i));
				
				if((elements.get(i).get(2)).toLowerCase().contains(str.toLowerCase()))
					if(!filteredList.contains(elements.get(i)))
						filteredList.add(elements.get(i));
			}
			
		} 
		if(index == 1) {
			elements = new ArrayList<ArrayList<String>>();
			elements = filteredList;	
		}
		return filteredList;
	}
}


