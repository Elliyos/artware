package downey.main;
import java.util.*;
public class TestProgress {
	@SuppressWarnings("deprecation")
	public static void main(String[] args){
		Collection test = new Collection();
		ArrayList<Person> people = new ArrayList<Person>();
		Person p1 = new Person("Artist", "Degas", "French", "M", "Born near the sea");
		Person p2 = new Person("Writer", "Charles", "English", "M", "Born away from the sea");
		people.add(p1);
		people.add(p2);
		System.out.println(people.toString());
		test.addPerson(p1);
		test.addPerson(p2);
		ArrayList<String>testConnect = new ArrayList();
		testConnect.add(p2.getName());
		p1.addConnection(new Connection(new Date(1995, 20,7), "Interacted with another person", "Page 20", "Paris", "Letter", testConnect));
		System.out.println(test.getConnections("Degas"));
	}

}
