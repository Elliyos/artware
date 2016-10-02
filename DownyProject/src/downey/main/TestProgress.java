package downey.main;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import com.opencsv.CSVReader;
public class TestProgress {
	public static void main(String[] args) throws IOException{
		DataStorage test = new DataStorage();
		test.addPerson("kevin", "american", "worker", "M", "asdgads");
		test.addPerson("keenan", "american", "workeraa", "M", "agsag");
		test.addPerson("Kyle", "american", "asgasdg", "M", "agsdgsdgds");
		ArrayList<Person> p = test.convertToPersonArray("kevin,keenan");
		System.out.println(p);
		
		//THIS TESTS FOR EDITING/REMOVING/SAVING/LOADING PEOPLE
		test.addPerson("tyler", "american", "sgadgda", "M", "agagdsgds");
		test.addPerson("Avash", "american", "sgadgda", "M", "agagdsgds");
		System.out.print(test.displayPeople());
		test.addConnection(test.getPersonObject("kevin"), test.convertToPersonArray("keenan,kyle,kevin"),
							"10/2/2016", "Letter", "Rock Island", "Me", "No notes");
		test.addConnection(test.getPersonObject("kyle"), test.convertToPersonArray("keenan"),
				"10/2/2016", "Letter", "Rock Island", "Me", "No notes");
		test.addConnection(test.convertToPersonArray("keenan,kyle,kevin"),
				"10/2/2016", "Letter", "Rock Island", "Me", "No notes");
		System.out.println(test.displayConnections());
		System.out.println(test.getConnectionsForPerson("kevin"));
		
		
		
		
		
//		test.savePeople("people");
//		System.out.print(test.displayConnections());
//	    test.displayPerson("keenan");
//	    System.out.print(test.displayPeople());
//	    test.savePeople("people");
//	    Person kevin = test.getPersonObject("kevin");
//	    kevin.editPerson("Kyle", "black", "worker", "M", "agdsgdsg");
//	    test.savePeople("people");
//	    
//		
//		Person p1 = new Person("kevin", "german","worker","M","Rock Islander");
//		ArrayList<Person> p = new ArrayList<Person>();
//		p.add(p1);
//		test.addConnection(p1, p ,"7/7/7", "Letter", "Chicago", "ibid23", "....");
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
