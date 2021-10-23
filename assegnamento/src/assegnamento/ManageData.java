package assegnamento;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

public abstract class ManageData {
	
	private static String name;
	private static String surname;
	private static String username;
	private static String password;
	private static String isAdmin;
	private static String id;
	private static String manufacturer;
	private static String price;
	private static ArrayList<String> array;
	private static ArrayList<ArrayList<String>> products;
	private static String[] files= {"employees.xml", "products.xml", "users.xml"};
	
	private static String[] users = {"name", "surname", "username", "password"};
	private static String[] product = {"id", "name", "manufacturer", "price" };


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

		File fXmlFile = new File(".\\src\\assegnamento\\" + xFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder dBuilder;

		try {

			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			Element nList = doc.getDocumentElement();

			System.out.println("-----------------------");

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
			nList.appendChild(newData);

			Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new File(".\\src\\assegnamento\\trim-whitespace.xslt")));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
			transformer.transform(new DOMSource(doc), new StreamResult(".\\src\\assegnamento\\" + xFile));

			System.out.println("DONE");

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
				// <name>
				Element name = (Element)person.getElementsByTagName(data[1][selectType].toString()).item(0);
				String pName = name.getTextContent();
				if (pName.equals(deletedElement)) {
					person.getParentNode().removeChild(person);

				}
				Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new File(".\\src\\assegnamento\\trim-whitespace.xslt")));
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
				transformer.transform(new DOMSource(doc), new StreamResult(".\\src\\assegnamento\\" + xFile));

				System.out.println("DONE");
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
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
		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(".\\src\\assegnamento\\"+xFile);
			NodeList nodeList = dom.getElementsByTagName(type); 
			Element doc = dom.getDocumentElement();
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				username = getTextValue(username, doc, "username", i);
				if ((username != null) && (!username.isEmpty()) && (username.equals(usern))) {
					password = getTextValue(password, doc, "password", i);
					if (( password != null) && (!password.isEmpty())) 
						return password;
				}
			}

		} catch (Exception e)    {
			e.printStackTrace();  
		} 

		return null;
	}
	
	public static ArrayList<String> readAll(String type){
		String xFile = "";
		if(type == "user") {
			xFile = files[2];
		}
		else if(type == "product") {
			xFile = files[1];
		}
		else if(type == "employee") {
			xFile = files[0];
		}
		
		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(".\\src\\assegnamento\\"+xFile);
			NodeList nodeList = dom.getElementsByTagName(type); 
			Element doc = dom.getDocumentElement();
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				array = new ArrayList<String>();
				

			name = getTextValue(name, doc, "name", i);
			if (name != null) {
				if (!name.isEmpty())
					array.add(name);
			}

			surname = getTextValue(surname, doc, "surname", i);
			if (surname != null) {
				if (!surname.isEmpty())
					array.add(surname);
			}

			username = getTextValue(username, doc, "username", i);
			if (username != null) {
				if (!username.isEmpty())
					array.add(username);
			}

			password = getTextValue(password, doc, "password", i);
			if ( password != null) {
				if (!password.isEmpty())
					array.add(password);
			}
			isAdmin = getTextValue(isAdmin, doc, "admin", i);
			if (isAdmin != null) {
				if (!isAdmin.isEmpty())
					array.add(isAdmin);
			}

			System.out.println(array); //debug
			}

		} catch (Exception e)    {
			e.printStackTrace();  
		} 

		return array;
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
	
	public static ArrayList<ArrayList<String>> getAllProducts(){
		String xFile = files[1], type = "product";
		
		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(".\\src\\assegnamento\\"+xFile);
			NodeList nodeList = dom.getElementsByTagName(type); 
			Element doc = dom.getDocumentElement();
			
			products = new ArrayList<ArrayList<String>>();
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				array = new ArrayList<String>();
				
			id = getTextValue(id, doc, "id", i);
			if ((id != null) && (!id.isEmpty())) 
					array.add(id);

			name = getTextValue(name, doc, "name", i);
			if ((name != null) && (!name.isEmpty())) 
				array.add(name);

			manufacturer = getTextValue(manufacturer, doc, "manufacturer", i);
			if ((manufacturer != null) && (!manufacturer.isEmpty())) 
				array.add(manufacturer);

			price = getTextValue(price, doc, "price", i);
			if ((price != null) && (!price.isEmpty())) 
				array.add(price);
			
			products.add(array);
			}

		} catch (Exception e)    {
			e.printStackTrace();  
		} 
		return products;
	}
}
