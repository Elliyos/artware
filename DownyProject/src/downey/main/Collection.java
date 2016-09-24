package downey.main;
import java.util.*;

public class Collection {
public ArrayList<Person> people;
public ArrayList<Connection> connections;

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
	public int search(Person person){
		for (int i = 0; i < people.size(); i++){
			Person current = people.get(i);
			if (current.equals(person)){
				return i;
			}
		}
		return -1;
		
	}
	public String getConnections(Person person){
		int index = search(person);
		return people.get(index).getConnections();
	}
	
	public void search(Connection connect){
		
	}
}
