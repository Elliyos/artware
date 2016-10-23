package downey.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.*;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class DataStorage {
	private int numPeople = 0;
	private ArrayList<Person> people;
	// private Person groupConnection = new Person("Group Connection", "", "",
	// "", "");
	private ArrayList<Connection> connections;
	private String selectedName;
	private String selectedRecipientNames;
	private Connection selectedConnection;
	private static DataStorage mainDataStorage = new DataStorage();

	private DataStorage() {
		people = new ArrayList<Person>();
		connections = new ArrayList<Connection>();
	}

	public int getNumPeople() {
		int numPeople = 0;
		for (int i = 0; i < people.size(); i++) {
			numPeople++;
		}
		return numPeople;

	}

	public static DataStorage getMainDataStorage() {
		return mainDataStorage;
	}

	/**
	 * This method creates a new Person object, given the required parameters,
	 * and adds it to the people ArrayList
	 *
	 * @param name
	 * @param culture
	 * @param occupation
	 * @param gender
	 * @param bio
	 * @return void
	 */
	public boolean addPerson(String name, String culture, String occupation, String gender, String bio) {
		numPeople = getNumPeople() + 1;
		if (!containsPerson(name)) {
			Person temp = new Person(name, culture, occupation, gender, bio);
			people.add(temp);
			return true;
		}
		return false;
	}

	public boolean containsPerson(String name) {
		for (Person p : people) {
			if (p.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * This method returns all connections that a Person object is involved in.
	 * 
	 * 
	 * @param fileName,
	 *            the name of the file to store the saved people.
	 * @return void
	 */
	public ArrayList<String> getConnectionsForPerson(String name) {
		ArrayList<String> personConnections = new ArrayList<>();
		// String result = "Connections " + name + " is involved with:\n\n";
		Person person = getPersonObject(name);
		for (Connection c : connections) {
			if (c.getSender().equals(person) || c.getReceivers().contains(person)) {
				personConnections.add(c.toString());
			}
		}
		return personConnections;
	}

	/**
	 * Private auxiliary method used to find the index of a Person object in the
	 * ArrayList of people given a name. This way the user can enter a name and
	 * this class will perform modifications given only a String.
	 *
	 * @param name,
	 *            the name of the person to be found
	 * @return int, the index of the person
	 */
	public int searchPerson(String name) {
		for (int i = 0; i < people.size(); i++) {
			if (people.get(i).getName().equalsIgnoreCase(name)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Private auxiliary method used to find the index of a Connection object in
	 * the ArrayList of connections. This way the user can enter names,
	 * location, and a date and this class will perform modifications given only
	 * String objects
	 *
	 * @param name,
	 *            the name of the person to be found
	 * @return int, the index of the person
	 */
	private int searchConnection(String names, String location, String date) throws IOException {
		for (int i = 0; i < connections.size(); i++) {
			if (connections.get(i).getReceivers().equals(convertToPersonArray(names))
					&& connections.get(i).getLocation().equals(location)) {
				return i;
			}
		}
		return -1;
	}

	public Connection getConnectionObject(String names, String location, String date) throws IOException {
		int index = searchConnection(names, location, date);
		return connections.get(index);
	}

	/**
	 * Accepts all parameters required for a Connection object, then creates a
	 * new Connection and adds it to the ArrayList of connections
	 *
	 * @param sender,
	 *            person establishing connection (null for group connections)
	 * @param people,
	 *            the people receiving a connection from the sender, or
	 *            eachother if sender is null
	 * @param date,
	 *            the date upon which the connection occured
	 * @param type,
	 *            the form of communication
	 * @param location,
	 *            where the connection took place
	 * @param citation,
	 *            evidence for connection
	 * @param notes,
	 *            notes on the connection
	 * @return void
	 */
	public void addConnection(Person sender, ArrayList<Person> people, String date, String type, String location,
			String citation, String notes) {
		connections.add(new Connection(sender, people, date, type, location, citation, notes));
	}

	public void addConnection(ArrayList<Person> people, String date, String type, String location, String citation,
			String notes) {
		connections.add(new Connection(people, date, type, location, citation, notes));
	}

	public void storeSelectedName(String s) {
		selectedName = s;
	}

	public String getSelectedName() {
		return selectedName;
	}

	public void storeSelectedNames(String s) {
		selectedRecipientNames = s;
	}

	public String getSelectedNames() {
		return selectedRecipientNames;
	}

	public void storeSelectedConnection(Connection c) {
		selectedConnection = c;
	}

	public Connection getSelectedConnection() {
		return selectedConnection;
	}

	public Person getPersonObject(String name) {
		return people.get(searchPerson(name));
	}

	/**
	 * Accepts a string of Person object names and returns an ArrayList of type
	 * Person filled with the Person object corresponding to each name.
	 * Precondition: people is a String with each name separated by a comma
	 * Postcondition: returns an ArrayList containing all people with those
	 * names.
	 *
	 * @param people,
	 *            the comma separated String of names to be converted
	 * @return ArrayList<Person>
	 */
	public ArrayList<Person> convertToPersonArray(String people) throws IOException {
		ArrayList<Person> personArray = new ArrayList<>();
		List<String> peopleList = Arrays.asList(people.split(", "));
		for (String person : peopleList) {
			personArray.add(getPersonObject(person));
		}
		return personArray;

	}

	/**
	 * Loads a text file containing information for connections and converts
	 * that information into Connection objects, which are then stored into the
	 * connections ArrayList.
	 * 
	 * We don't need to pass in a file name. Should discuss.
	 *
	 * @param fileName,
	 *            the name of the file that stores the connection information
	 * @return void
	 */
	public void loadConnections() throws IOException, EOFException {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("stored_connections"));
			connections = (ArrayList<Connection>) in.readObject();
		} catch (EOFException ex) {

		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}

	}

	/**
	 * Saves the current ArrayList of Connection objects into a specified text
	 * file.
	 *
	 * @param fileName,
	 *            the name of the file to store the connections
	 * @return void
	 */
	public void saveConnections() throws IOException {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("stored_connections"));
			out.writeObject(connections);
		}

		catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Saves the current ArrayList of Person objects into a specified text file.
	 *
	 * @param fileName,
	 *            the name of the file to store the saved people.
	 * @return void
	 */
	public void savePeople() throws IOException {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("stored_people"));
			out.writeObject(people);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Takes a file that stores information for a number of person objects and
	 * converts that information into person objects and stores them into the
	 * ArrayList of people
	 *
	 * @param fileName,
	 *            the name of the file that stores information for Person
	 *            objects
	 * @return void
	 */
	public void loadPeople() throws IOException, EOFException {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("stored_people"));
			people = (ArrayList<Person>) in.readObject();
		} catch (EOFException ex) {

		} catch (Exception ex) {
			ex.printStackTrace();
			people = new ArrayList<>();
			System.exit(1);
		}
	}

	public ArrayList<Person> getPeopleArray() {
		return people;
	}

	public ArrayList<Connection> getConnectionArray() {
		return connections;
	}

}
