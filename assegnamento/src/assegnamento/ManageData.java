package assegnamento;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public abstract class ManageData {

	private static String data;
	private static String name;
	private static String username;
	private static String password;
	private static String isAdmin;
	private static ArrayList<String> array;
	private static ArrayList<ArrayList<String>> elements;
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
		String[][] xElement = {{"username", "password", "name", "surname", "admin"},{"id", "name", "manufacturer", "price" }, {"name", "surname", "username", "password"}};

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
			username = getTextValue(username, doc, "username", i);
			if (username != null && !username.isEmpty() && username.equals(select)) 
				setTextValue(doc, xElement[idx][option], i, content, xFile);
		}

	}

	public static ArrayList<ArrayList<String>> readAll(String type){
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

		elements = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < doc.getElementsByTagName(type).getLength(); i++) {

			array = new ArrayList<String>();

			for (int j = 0; j < 4; j++) {

				data = getTextValue(data, doc, xElements[idx][j], i);
				if (data != null) {
					if (!data.isEmpty())
						array.add(data);
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
			nl.item(index).getFirstChild().setNodeValue(content);;
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


}


