package downey.main;
import java.io.IOException;
import java.util.*;
public class TestProgress {
	public static void main(String[] args) throws IOException{
		DataStorage test = new DataStorage();
		test.loadPeople("people");
		test.loadConnections("connections");
		test.displayPerson("avash");
		
//		Person p2 = new Person("kevin", "german","worker","M","Rock Islander");
//		ArrayList<Person> p = new ArrayList<Person>();
//		p.add(p2);
//		test.addConnection(p1,p ,"7/7/7", "Letter", "Chicago", "ibid23", "....");
//		test.addConnection(p1,p ,"7/7/7", "Letter", "Chicago", "ibid23", "....");
//		test.addConnection(p1,p ,"7/7/7", "Letter", "Chicago", "ibid23", "....");
//		test.addConnection(p1,p ,"7/7/7", "Letter", "Chicago", "ibid23", "....");
//		System.out.print(test.getConnectionsForPerson(p1));
//		test.saveConnections("connections");
//		test.addConnection(p1,p ,"7/7/7", "Letter", "Chicago", "ibid23", "....");
//		test.addConnection(p1,p ,"7/7/7", "Letter", "Chicago", "ibid23", "....");
//		test.saveConnections("connections");
		
	}

}
