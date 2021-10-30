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
	private static ArrayList<String> array;

	private static ArrayList<String> cids;
	private static ArrayList<String> cnames;
	private static ArrayList<String> cmanufacturers;
	private static ArrayList<String> cprices;
	private static ArrayList<String> cquantities;


	private static String[] files= {"employees.xml", "products.xml", "users.xml", "deliveries.xml"};
	private static String[] users = {"name", "surname", "username", "password"};
	private static String[] product = {"id", "name", "manufacturer", "price", "quantity"};
	private static String[] delivery = {"id", "name", "address", "cart"};

	protected static ArrayList<ArrayList<String>> elements;
	public static ArrayList<ArrayList<String>> getElements(){
		return ManageData.elements;
	}

	public static void addData(String type, String[] content)  {
		String[] xNodes = new String[5];
		String xFile = "";
		Element newData = null, data = null;

		if(type == "product") {
			xFile = files[1];
			xNodes = product;
		}
		else if(type == "employee") {
			xFile = files[0];
			xNodes = users;
		}
		else if(type=="user") {
			xFile = files[2];
			xNodes = users;
		}
		else if(type=="delivery") {
			xFile = files[3];
			xNodes = delivery;
		}

		File fXmlFile = new File(".\\src\\assegnamento\\" + xFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder dBuilder;

		try {

			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			Element nList = doc.getDocumentElement();

			newData = doc.createElement(type);

			for(int i=0; i<4; i++) {
				data = doc.createElement(xNodes[i]);
				data.appendChild(doc.createTextNode(content[i]));
				newData.appendChild(data);
			}


			if(type == "employee") {
				data = doc.createElement("admin");
				data.appendChild(doc.createTextNode(content[4]));
				newData.appendChild(data);
			}
			if(type == "user") {
				data = doc.createElement("address");
				data.appendChild(doc.createTextNode("none"));
				newData.appendChild(data);
			}
			if(type == "user") {
				data = doc.createElement("cart");
				data.appendChild(doc.createTextNode("none"));
				newData.appendChild(data);
			}
			if(type == "product") {
				data = doc.createElement("quantity");
				data.appendChild(doc.createTextNode("0"));
				newData.appendChild(data);
			}
			nList.appendChild(newData);

			save(doc, xFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void removeData(String type, String deletedElement) {
		try {

			String[][]  data = {{"employee", "product", "user"},{ "username", "id", "username"}};
			int selectType = 0;
			String xFile ="";

			if(type == "product") {
				xFile = files[1];
				selectType=1;
			}
			else if(type == "employee") {
				xFile = files[0];
				selectType=0;
			}
			else if(type == "user") {
				xFile = files[2];
				selectType=2;
			}

			File file = new File(".\\src\\assegnamento\\" + xFile);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;

			builder = factory.newDocumentBuilder();

			Document doc = builder.parse(file);
			NodeList nodes = doc.getElementsByTagName(data[0][selectType].toString());

			for (int i = 0; i < nodes.getLength(); i++) {
				Element person = (Element)nodes.item(i);
				Element name = (Element)person.getElementsByTagName(data[1][selectType].toString()).item(0);
				String pName = name.getTextContent();
				if (pName.equals(deletedElement)) {
					person.getParentNode().removeChild(person);

				}
				save(doc, xFile);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void editData(String type, int option, String select, String content) {
		String xFile = "";
		int idx = 0;
		String[][] xElement = {{"username", "password", "name", "surname", "admin"},{"id", "name", "manufacturer", "price", "quantity" }, {"username", "password", "name", "surname", "cart","address"}};

		if(type == "user") {
			xFile = files[2]; idx = 2;
		}
		else if(type == "product") {
			xFile = files[1]; idx = 1;
		}
		else if(type == "employee") {
			xFile = files[0]; idx = 0;
		}

		Element doc = docBuilder(xFile);
		for (int i = 0; i < doc.getElementsByTagName(type).getLength(); i++) {
			username = getTextValue(username, doc, xElement[idx][0], i);
			if (username != null && !username.isEmpty() && username.equals(select)) {
				if(xElement[idx][option] != "cart")
					setTextValue(doc, xElement[idx][option], i, content, xFile);
				else {
					cart = getTextValue(cart, doc, xElement[idx][option], i);
					if(cart.equals("none")) {
						setTextValue(doc, xElement[idx][option], i, content, xFile);
					}
					else {
						if(content.contains("replace"))
							setTextValue(doc, xElement[idx][option], i, content.replace("replace", ""), xFile);
						else
							setTextValue(doc, xElement[idx][option], i, cart + "," + content, xFile);
					}


				}
			}
		}

	}

	public static ArrayList<ArrayList<String>> readAll(String type){
		cids = new ArrayList<String>();
		cnames = new ArrayList<String>();
		cmanufacturers = new ArrayList<String>();
		cquantities = new ArrayList<String>();
		cprices = new ArrayList<String>();
		elements = new ArrayList<ArrayList<String>>();
		String xFile = "";
		int idx =0;
		if(type == "user") {
			xFile = files[2];
		}
		else if(type == "product") {
			xFile = files[1]; idx =1;
		}
		else if(type == "employee") {
			xFile = files[0];
		}
		String[][] xElements= {users, product};

		Element doc = docBuilder(xFile);

		for (int i = 0; i < doc.getElementsByTagName(type).getLength(); i++) {

			array = new ArrayList<String>();

			for (int j = 0; j < 5; j++) {
				data = getTextValue(data, doc, xElements[idx][j], i);
				if (data != null) {
					if (!data.isEmpty()) {
						if(type == "product" && xElements[idx][j].equals("id")) {
							array.add(data);
							cids.add(data);
						}
						else if(type == "product" && xElements[idx][j].equals("name")) {
							array.add(data);
							cnames.add(data);
						}
						else if(type == "product" && xElements[idx][j].equals("manufacturer")) {
							array.add(data);
							cmanufacturers.add(data);
						}
						else if(type == "product" && xElements[idx][j].equals("price")) {
							array.add("$"+ data);
							cprices.add(data);
						}
						else if(type == "product" && xElements[idx][j].equals("quantity")) {
							array.add(data);
							cquantities.add(data);
						}
						else
							array.add(data);
					}

				}
			}

			if(type == "employee") {
				isAdmin = getTextValue(isAdmin, doc, "admin", i);
				if (isAdmin != null) {
					if (!isAdmin.isEmpty())
						array.add(isAdmin);
				}
			}

			elements.add(array);
		}


		return elements;
	}


	public static ArrayList<String> getAdmin(){

		String xFile = files[0];
		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(".\\src\\assegnamento\\"+xFile);
			Element doc = dom.getDocumentElement();

			array = new ArrayList<String>();


			isAdmin = getTextValue(isAdmin, doc, "admin", 0);
			if (isAdmin != null) {
				if (!isAdmin.isEmpty())
					array.add(isAdmin);
			}

			username = getTextValue(username, doc, "username", 0);
			if (username != null) {
				if (!username.isEmpty())
					array.add(username);
			}

			password = getTextValue(password, doc, "password", 0);
			if ( password != null) {
				if (!password.isEmpty())
					array.add(password);
			}

		} catch (Exception e)    {
			e.printStackTrace();  
		} 

		return array;
	}

	public static String getPassword(String type, String usern){
		String xFile = "";
		if(type == "user") {
			xFile = files[2];
		}
		else if(type == "employee") {
			xFile = files[0];
		}

		Element doc = docBuilder(xFile);

		for (int i = 0; i < doc.getElementsByTagName(type).getLength(); i++) {
			username = getTextValue(username, doc, "username", i);
			if ((username != null) && (!username.isEmpty()) && (username.equals(usern))) {
				password = getTextValue(password, doc, "password", i);
				if (( password != null) && (!password.isEmpty())) 
					return password;
			}
		}

		return null;
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

	static String getTextValue(String def, Element doc, String tag, int index) {

		String value = def;
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
		String xFile = "";
		String id = "";
		if(type == "user") {
			xFile = files[2]; id = "username";
		}
		else if(type == "product") {
			xFile = files[1]; id = "id";
		}
		else if(type == "employee") {
			xFile = files[0]; id = "username";
		}

		Element doc = docBuilder(xFile);

		for (int i = 0; i < doc.getElementsByTagName(type).getLength(); i++) {
			username = getTextValue(username, doc, id, i);
			if (username != null && !username.isEmpty() && username.equals(select)) {	
				return true;}
		}
		return false;

	}

	public static void editQuantity(String user, int index, int newQuantity){

		ArrayList<String> products = null;
		String xFile = "";
		String id = ""; 
		int idx = 0;

		xFile = files[2]; 

		Element doc = docBuilder(xFile);

		for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {
			username = getTextValue(username, doc, "username", i);
			if (username != null && !username.isEmpty() && username.equals(user)) {
				products = new ArrayList <> (Arrays.asList(getTextValue(username, doc, "cart", i).split(",")));
				break;
			}

		}
		for(int i = 0; i < products.size() && i <= index;i++) {
			System.out.println(i+ ")");
			id = products.get(i);
			i++;
		}

		idx = cids.indexOf(id);
		if(newQuantity <= Integer.parseInt(cquantities.get(idx))) {
			products.set(products.indexOf(id)+1, Integer.toString(newQuantity));
			editData("user",4 ,user , "replace" + products.toString().replace("[", "").replace("]", "").replace(" ", ""));}


	}

	public static void removeFromCart(String user, int index){

		ArrayList<String> products = null;
		String xFile = "";
		String id = ""; 
		int idx = 0;

		xFile = files[2]; 

		Element doc = docBuilder(xFile);

		for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {
			username = getTextValue(username, doc, "username", i);
			if (username != null && !username.isEmpty() && username.equals(user)) {
				products = new ArrayList <> (Arrays.asList(getTextValue(username, doc, "cart", i).split(",")));
				break;
			}

		}
		for(int i = 0; i < products.size() && i <= index;i++) {
			System.out.println(i+ ")");
			id = products.get(i);
			System.out.println(id);
			i++;
		}
		System.out.println(cids);
		idx = products.indexOf(id);
		products.remove(idx);
		products.remove(idx);
		editData("user",4 ,user , "replace" + products.toString().replace("[", "").replace("]", "").replace(" ", ""));


	}

	public static void showCart(String user) {
		ArrayList<String> products = null;
		String xFile = "";
		int idx = 0, count=1;
		float total = 0;

		xFile = files[2]; 

		Element doc = docBuilder(xFile);

		for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {
			username = getTextValue(username, doc, "username", i);
			if (username != null && !username.isEmpty() && username.equals(user)) {
				products = new ArrayList <> (Arrays.asList(getTextValue(username, doc, "cart", i).split(",")));
				break;
			}

		}
		System.out.format("%-25s%-25s%-25s%-25s","PRODUCT", "QUANTITY", "MANUFACTURER", "PRICE");
		System.out.println("\n----------------------------------------------------------------------------------");

		for(int i = 0; i < products.size();i++) {
			if(i %2 ==0) {
				idx = cids.indexOf(products.get(i));
			}
			else {
				System.out.format("%-25s%-25s%-25s%-25s", count + ") " + cnames.get(idx),  "x" +products.get(i), cmanufacturers.get(idx) , "$" + Float.parseFloat(cprices.get(idx))*Integer.parseInt(products.get(i)));
				System.out.println();
				count++;
				total+=Float.parseFloat(cprices.get(idx))*Integer.parseInt(products.get(i));
			}

		}
		System.out.format("%-25s%-23s%-20s%1s","", "", "", "Total: $" + total);
		System.out.println();
	}

	public static void placeOrder(String user){
		String[] order = new String[4];
		Scanner input = new Scanner(System.in);
		ArrayList<String> products = null;
		String xFile = "";
		String decision, id = "";
		int tempID=0, quantity = 0;
		xFile = files[2]; 

		Element doc = docBuilder(xFile);

		for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {
			username = getTextValue(username, doc, "username", i);
			if (username != null && !username.isEmpty() && username.equals(user)) {
				order[1] = getTextValue(username, doc, "name", i) + " " + getTextValue(username, doc, "surname", i);
				order[2] = getTextValue(username, doc, "address", i);
				order[3] = getTextValue(username, doc, "cart", i);
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
					System.out.println("\nThe products will be sent to " + order[2]);
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

		for(int j = 0; j < products.size()-1; j++) {
			tempID = Integer.parseInt(products.get(j)) + Integer.parseInt(products.get(j+1));
		}
		tempID += ThreadLocalRandom.current().nextInt(1, 10000 + 1);

		order[0] = "#"+Integer.toString(tempID);


		Element doc2 = docBuilder("products.xml");

		for(int i = 0; i < products.size();i++) {


			if(i %2 ==0) {
				id = products.get(i);
			}
			else {
				for (int j = 0; j < doc2.getElementsByTagName("product").getLength(); j++) {
					username = getTextValue(username, doc2, "id", j);
					if (username != null && !username.isEmpty() && username.equals(id)) {
						quantity = Integer.parseInt(getTextValue(username, doc2, "quantity", j));
						setTextValue(doc2, "quantity", j, Integer.toString(quantity - Integer.parseInt(products.get(i))), "products.xml");
					}
				}
			}
		}


		editData("user",4 ,user , "replacenone");
		addData("delivery", order);
	}

	public static void filterList(int index, String str) {
		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < elements.size(); i++) {
			if(index == 3) {
				if(Float.parseFloat(elements.get(i).get(index).substring(1)) < Float.parseFloat(str))
					temp.add(elements.get(i));
			}
			else if(index == 4) {
				if(Float.parseFloat(elements.get(i).get(index-1).substring(1)) > Float.parseFloat(str))
					temp.add(elements.get(i));
			}
			else {
				if((elements.get(i).get(index)).toLowerCase().contains(str.toLowerCase()))
					temp.add(elements.get(i));
			}
		} 
		elements = new ArrayList<ArrayList<String>>();
		elements = temp;
	}


	public void printList(){
		if(elements.size() == 0) {
			System.out.print("\nNo Result\n");
			return;
		}
		for (int i = 0; i < elements.size(); i++) {
			System.out.print("\n" + (i+1) + ")  ");
			for (int j = 1; j < elements.get(i).size(); j++) 
				System.out.format("%-25s",elements.get(i).get(j));
			System.out.println();
		}
		System.out.println();	
	} 
}


