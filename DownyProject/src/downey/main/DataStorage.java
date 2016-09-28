package downey.main;
import java.util.*;

public class DataStorage {
public ArrayList<Person> people;
public ArrayList<Connections> connections;
	public DataStorage(){
		people = new ArrayList<Person>();
		connections = new ArrayList<Connections>();
	}
//	public DataStorage(ArrayList<Person> people, ArrayList<Connection> connections){
//		this.people = people;
//		this.connections = connections;
//	}

	public void addPerson(String name, String culture, String occupation, String gender, String bio){
		Person temp = new Person(name, culture, occupation, gender, bio);
		people.add(temp);
	}
	
	public void addSingleConnection(Person p1, Person p2, String date,
				String source, String location, String citation, String notes){
		p1.getContacts().add(new SingleConnection(date,source,location,citation, notes, p2));

	}
	public Person getPerson(String name){
		for (Person p: people){
			if (p.getName().equalsIgnoreCase(name)){
				return p;
			}
		}
		throw new IllegalArgumentException("Person not found");
	}
//	public void addMultiConnection(Person p1, )

	/**
	 * This method returns the index of a Person object
	 * 
	 * @param person
	 * @return String
	 */
	public int search(String name){
		for (int i = 0; i < people.size(); i++){
			Person current = people.get(i);
			if (current.getName().equals(name)){
				return i;
			}
		}
		return -1;
		
	}
	public String getConnections(String name){
		int index = search(name);
		String contacts = people.get(index).getConnections();
		if (contacts != null) {
			return contacts;
		}
		return "None";
	}
	
	public void search(Connection connect){
		
	}
	
	public ArrayList<Person> getAllPeople(){
		return people;
	}
}
