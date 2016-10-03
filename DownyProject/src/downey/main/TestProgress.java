package downey.main;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import com.opencsv.CSVReader;
public class TestProgress {
	public static void main(String[] args) throws IOException{
		DataStorage test = new DataStorage();
		test.loadPeople("people");
		test.loadConnections("connections");
		System.out.print(test.displayConnections());
		System.out.print(test.displayPeople());
	}

}
