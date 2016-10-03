package downey.main;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import com.opencsv.CSVReader;
public class TestProgress {
	public static void main(String[] args) throws IOException{
		DataStorage test = DataStorage.getMainDataStorage();
		test.loadPeople("people");
		test.loadConnections("connections");
		System.out.print(test.displayConnections());
		System.out.print(test.displayPeople());
		ArrayList<Person> temp = test.convertToPersonArray("tyler, kevin");
		Connection c = test.getConnectionObject("keenan, kyle, kevin", "Rock Island", "10/2/2016");
		c.editConnection(temp, "10/2/2016", "letter", "Rock Island", "sagaa", "asgag");
		test.saveConnections("connections");
		
	}

}
