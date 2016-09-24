package downey.main;
import java.util.*;

public class Collection {
public ArrayList<Person> people;
public ArrayList<Connection> connections;
	public Collection(){
		people = new ArrayList<Person>();
		connections = new ArrayList<Connection>();
	}
	public Collection(ArrayList<Person> people, ArrayList<Connection> connections){
		this.people = people;
		this.connections = connections;
	}

	public void addPerson(Person person){
		people.add(person);
	}
	
	public void addConnection(Connection connect){
		connections.add(connect);
	}

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
}
