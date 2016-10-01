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

	public String displayConnections() {
		return connections.toString();
	}
	public void removePerson(String name){
		people.remove(searchPerson(name));
	}
	private int searchPerson(String name){
		for (int i = 0; i < people.size(); i++){
				if (people.get(i).getName().equalsIgnoreCase(name)){
					return i;	
			}
		}
		return -1;
	}
	public Person getPersonObject(String name){
		return people.get(searchPerson(name));
	}


	/**
	 * Load all current connections into csv file
	 */
	public void loadConnections(String fileName) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(fileName));
		List<String[]> myRows = reader.readAll();
		for (String[] row : myRows) {
			Person sender = convertToPerson(row[0]);
			ArrayList<Person> receivers = convertToPeople(row[1]);
			String date = row[2];
			String source = row[3];
			String location = row[4];
			String citation = row[5];
			String notes = row[6];
			connections.add(new Connection(sender, receivers, date, source, location, citation, notes));
		}
	}

	public void saveConnections(String fileName) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileName));
		for (Connection c : connections) {
			writer.writeNext(c.toCSVRowArray());
		}
		writer.close();
	}

	public void savePeople(String fileName) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileName));
		for (Person p : people) {
			writer.writeNext(p.toCSVRowArray());
		}
		writer.close();
	}

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

	public void addConnection(Person sender, ArrayList<Person> people, String date, String type, String location,
			String citation, String notes) {
		Connection temp = new Connection(sender, people, date, type, location, citation, notes);
		connections.add(temp);

	}

	public String displayPeople() {
		return people.toString();
	}

	public ArrayList<Person> getAllPeople() {
		return people;
	}

	public void displayPerson(String name) {
		for (Person p : people) {
			if (p.getName().equalsIgnoreCase(name)) {
				System.out.println(p);
			}
		}
	}

	public Person convertToPerson(String name) {
		for (Person p : people) {
			if (p.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}

	public ArrayList<Person> convertToPeople(String people) throws IOException {
		ArrayList<Person> temp = new ArrayList<>();
		List<String> thePeople = Arrays.asList(people.split(","));
		for (String person : thePeople) {
			temp.add(convertToPerson(person));
		}
		return temp;

	}
}
