package downey.main;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import com.opencsv.CSVReader;
public class TestProgress {
	public static void main(String[] args) throws IOException{
		DataStorage test = DataStorage.getMainDataStorage();
		test.loadPeople("people");
		test.addConnection(test.getPeopleArray(), "10/05/2016", "Meeting", "Olin 204", "CSC285", "Scrum meeting");
		test.saveConnections("connections");
	
	}

}
