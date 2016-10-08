package downey.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.*;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class DataStorage {
	private ArrayList<Person> people;
	private ArrayList<Connection> connections;
	private String selectedName;
	private static DataStorage mainDataStorage = new DataStorage();

	private DataStorage() {
		people = new ArrayList<Person>();
		connections = new ArrayList<Connection>();
	}
	
	public static DataStorage getMainDataStorage() {
		return mainDataStorage;
	}
	/**
	 * This method creates a new Person object, given the required parameters, and
	 * adds it to the people ArrayList
	 *
	 * @param  name
	 * @param culture
	 * @param occupation
	 * @param gender
	 * @param bio
	 * @return      void
	 */
	public boolean addPerson(String name, String culture, String occupation, String gender, String bio) {
		if (!containsPerson(name)){
			Person temp = new Person(name, culture, occupation, gender, bio);
			people.add(temp);
			return true;
		}
		return false;
	}
	public boolean containsPerson(String name){
		for (Person p : people){
			if (p.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
		
	}
	/**
	 * This method returns all connections that a Person object is involved in. 
	 * 
	 * 
	 * @param  fileName, the name of the file to store the saved people.
	 * @return      void
	 */
	public String getConnectionsForPerson(String name) {
		String result = "Connections " + name + " is involved with:\n\n";
		Person person = getPersonObject(name);
		for (Connection c : connections) {
			if (c.getSender().equals(person) || c.getReceivers().contains(person)) {
				result += c.toString();
			}
		}
		return result;
	}
	/**
	 * Removes a Person object from the ArrayList people
	 *
	 * @param  name, the person to be removed
	 * @return      void
	 */
	public void removePerson(String name){
		people.remove(searchPerson(name));
	}
	/**
	 * Private auxiliary method used to find the index of a Person object in the ArrayList
	 * of people given a name. This way the user can enter a name and this class will perform
	 * modifications given only a String.
	 *
	 * @param  name, the name of the person to be found
	 * @return      int, the index of the person
	 */
	public int searchPerson(String name){
		for (int i = 0; i < people.size(); i++){
				if (people.get(i).getName().equalsIgnoreCase(name)){
					return i;	
			}
		}
		return -1;
	}
	
	private int searchConnection(String names, String location, String date) throws IOException{
		for (int i = 0; i < connections.size(); i++){
			if (connections.get(i).getReceivers().equals(convertToPersonArray(names)) && connections.get(i).getLocation().equals(location)){
				return i;
			}
		}
		return -1;
	}
	public Connection getConnectionObject(String names, String location, String date) throws IOException{
		int index = searchConnection(names,location,date);
		return connections.get(index);
	}
	/**
	 * Accepts all parameters required for a Connection object, then creates a new Connection 
	 * and adds it to the ArrayList of connections
	 *
	 * @param  sender, person establishing connection (null for group connections)
	 * @param people, the people receiving a connection from the sender, or eachother if sender is null
	 * @param date, the date upon which the connection occured
	 * @param type, the form of communication
	 * @param location, where the connection took place
	 * @param citation, evidence for connection
	 * @param notes, notes on the connection
	 * @return      void
	 */
	public void addConnection(Person sender, ArrayList<Person> people, String date, String type, String location,
			String citation, String notes) {		
		connections.add(new Connection(sender, people, date, type, location, citation, notes));
	}
	public void addConnection(ArrayList<Person> people, String date, String type, String location,
			String citation, String notes){
		connections.add(new Connection(people,date,type,location,citation,notes));
	}
	/**
	 * Returns a String containing all of the people stored in the system
	 *
	 * @return      String, all Person objects in the system
	 */
	public String displayPeople() {
		String result = "\n\nCurrent people in the system:\n";
		for (Person p: people){
			result += p.toString();
		}
		return result + "\n\n";
	}
	/**
	 * Display all connections in the system
	 *
	 * @return      String, the list of current connections
	 */
	public String displayConnections() {
		String result = "Current connections in the system:\n";
		for (Connection c: connections){
			result += c.toString();
		}
		return result;
	}
	public void storeSelectedName(String s){
		selectedName = s;
	}
	public String getSelectedName(){
		return selectedName;
	}
	
	/**
	 * This method accepts a String name as a parameter and displays information
	 * about that person object.
	 *
	 * @param  name, the name of the person whos information is to be displayed
	 * @return      void
	 */
	public String displayPerson(String name) {
		for (Person p : people) {
			if (p.getName().equalsIgnoreCase(name)) {
				return p.toString();
			}
		}
		return "Person not found";
	}
	public Person getPersonObject(String name){
		return people.get(searchPerson(name));
	}
//	public Connection getConnectionObject()
	/**
	 * Accepts a string of Person object names and returns an ArrayList of type Person
	 * filled with the Person object corresponding to each name.
	 * Precondition: people is a String with each name separated by a comma
	 * Postcondition: returns an ArrayList containing all people with those names.
	 *
	 * @param  people, the comma separated String of names to be converted
	 * @return      ArrayList<Person>
	 */
	public ArrayList<Person> convertToPersonArray(String people) throws IOException {
		ArrayList<Person> temp = new ArrayList<>();
		List<String> thePeople = Arrays.asList(people.split(", "));
		for (String person : thePeople) {
			temp.add(getPersonObject(person));
		}
		return temp;

	}
	/**
	 * Loads a text file containing information for connections and converts that information
	 * into Connection objects, which are then stored into the connections ArrayList.
	 * 
	 * We don't need to pass in a file name. Should discuss.
	 *
	 * @param  fileName, the name of the file that stores the connection information
	 * @return      void
	 */
	public void loadConnections(String fileName) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(fileName));
		List<String[]> myRows = reader.readAll();
			for (String[] row : myRows) {
				if (row.length==7){
					Person sender = getPersonObject(row[0]);
					ArrayList<Person> receivers = convertToPersonArray(row[1]);
					String date = row[2];
					String source = row[3];
					String location = row[4];
					String citation = row[5];
					String notes = row[6];
					connections.add(new Connection(sender, receivers, date, source, location, citation, notes));
				}
				else {
					ArrayList<Person> receivers = convertToPersonArray(row[0]);
					String date = row[1];
					String source = row[2];
					String location = row[3];
					String citation = row[4];
					String notes = row[5];
					connections.add(new Connection(receivers, date, source, location, citation, notes));
				}
			}
	}
	/**
	 * Saves the current ArrayList of Connection objects into a specified text file.
	 *
	 * @param  fileName, the name of the file to store the connections
	 * @return      void
	 */
	public void saveConnections(String fileName) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileName));
		for (Connection c : connections) {
			writer.writeNext(c.toCSVRowArray());
		}
		writer.close();
	}
	/**
	 * Saves the current ArrayList of Person objects into a specified text file.
	 *
	 * @param  fileName, the name of the file to store the saved people.
	 * @return      void
	 */
	public void savePeople(String fileName) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileName));
		for (Person p : people) {
			writer.writeNext(p.toCSVRowArray());
		}
		writer.close();
	}
	/**
	 * Takes a file that stores information for a number of person objects and
	 * converts that information into person objects and stores them into the
	 * ArrayList of people
	 *
	 * @param  fileName, the name of the file that stores information for Person objects
	 * @return      void
	 */
	public void loadPeople(String fileName) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(fileName));
		List<String[]> myRows = reader.readAll();
		for (String[] row : myRows) {
			String name = row[0];
			String occupation = row[1];
			String culture = row[2];
			String gender = row[3];
			String bio = row[4];
			people.add(new Person(name, occupation, culture, gender, bio));
		}
	}
	
	public ArrayList<Person> getPeopleArray(){
//		try {
//			loadPeople("people");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return people;
	}
	
	public ArrayList<Connection> getConnectionArray(){
//		try {
//			loadConnections("connections");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return connections;
	}
	
	// might be needed if want to display only names in the drop-down list
//	public ArrayList<String> getPeopleNames() {
//		ArrayList<String> peopleNames = new ArrayList<>();
//		for (Person person: people) {
//			peopleNames.add(person.getName());
//		}
//		return peopleNames;
//		
//	}
}
