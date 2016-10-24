package downey.main;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import com.opencsv.CSVReader;
public class TestProgress {
	public static void main(String[] args) throws IOException{
		Person p1 = new Person("k", "dfd", "fs", "sf", "sfs");
		ArrayList<Person> pp = new ArrayList<>();
		pp.add(p1);
		DataStorage test = DataStorage.getMainDataStorage();
		test.loadPeople();
		test.loadConnections();
		System.out.print(test.getConnectionArray());
		test.getConnectionArray().get(0).editConnection(p1, pp, "sdasd", "sdas", "fgsadgs", "gsafg", "sgadgd");
		test.saveConnections();
		test.loadConnections();
		System.out.println();
		System.out.println(test.getConnectionArray());

		
		
		
	
	}

}
