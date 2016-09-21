package downey.main;
import java.util.*;

public class Collection {
public ArrayList<Person> people;
public ArrayList<Connection> connections;

	public void addContact(Person person){
		people.add(person);
	}
	
	public void addConnection(Connection connect){
		connections.add(connect);
	}

	
	public static void search(Person person){
		
	}
	
	public static void search(Connection connect){
		
	}
}
