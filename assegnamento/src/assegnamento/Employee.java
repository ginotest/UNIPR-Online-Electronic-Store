package assegnamento;

import java.util.ArrayList;

public class Employee extends ManageData {

	private static ArrayList<String> employee;

	public boolean login(String username, String password) {
		employee = getProfile("employee", username);
		if((employee.get(1) != null) && (employee.get(1).equals(password)))
			return true;
		else
			employee.clear();
		return false;
	}

}
