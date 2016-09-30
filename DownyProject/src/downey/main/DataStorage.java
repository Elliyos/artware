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

	public DataStorage() {
		people = new ArrayList<Person>();
		connections = new ArrayList<Connection>();
	}

	public void addPerson(String name, String culture, String occupation, String gender, String bio) {
		Person temp = new Person(name, culture, occupation, gender, bio);
		people.add(temp);
	}

	public ArrayList<Connection> getConnectionsForPerson(Person person) {
		ArrayList<Connection> hisConnections = new ArrayList<>();
		for (Connection c : connections) {
			if (c.getSender().equals(person) || c.getReceivers().contains(person)) {
				hisConnections.add(c);
			}
		}

		return hisConnections;
	}

	/**
	 * Load all current connections into csv file
	 */
	public void loadConnections(String fileName) throws IOException {

	}

	public void saveConnections(String fileName) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileName));
		for (Connection c : connections) {
			writer.writeNext(c.toCSVRowArray());
		}
		writer.close();
	}

	public void addConnection(Person sender, ArrayList<Person> people, String date, String type, String location,
			String citation, String notes) {
		Connection temp = new Connection(sender, people, date, type, location, citation, notes);
		connections.add(temp);

	}

	/**
	 * This method returns the index of a Person object
	 * 
	 * @param person
	 * @return String
	 */
	// public int search(String name){
	// for (int i = 0; i < people.size(); i++){
	// Person current = people.get(i);
	// if (current.getName().equals(name)){
	// return i;
	// }
	// }
	// return -1;
	//
	// }
	// public String getConnections(String name){
	// int index = search(name);
	// String contacts = people.get(index).getConnections();
	// if (contacts != null) {
	// return contacts;
	// }
	// return "None";
	// }

	public void search(Connection connect) {

	}

	public ArrayList<Person> getAllPeople() {
		return people;
	}
}
