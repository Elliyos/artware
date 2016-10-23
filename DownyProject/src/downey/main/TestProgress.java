package downey.main;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import com.opencsv.CSVReader;
public class TestProgress {
	public static void main(String[] args) throws IOException{
		Person p1 = new Person("k", "dfd", "fs", "sf", "sfs");
		DataStorage test = DataStorage.getMainDataStorage();
		test.loadPeople();
		test.loadConnections();
		System.out.print(test.getConnectionArray());
		
		
		
	
	}

}
