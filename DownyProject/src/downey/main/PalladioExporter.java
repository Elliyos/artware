package downey.main;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class PalladioExporter {
	public static void main(String[] args) throws IOException{
		PalladioExporter p = new PalladioExporter();
		p.saveEdges();
	}
	private DataStorage DS = DataStorage.getMainDataStorage();
//	public void loadConnections() throws IOException {
//		CSVReader reader = new CSVReader(new FileReader("connections"));
//		List<String[]> myRows = reader.readAll();
//			for (String[] row : myRows) {
//				
//	}

	public void saveEdges() throws IOException {
		DS.loadPeople();
		DS.loadConnections();
		CSVWriter writer = new CSVWriter(new FileWriter("Palladio_Export_File"));
		for (Connection c : DS.getConnectionArray()) {
			ArrayList<Person> receivers = c.getReceivers();
			if (!c.getSender().getName().equals("Group Connection")){
				for (int i = 0; i < receivers.size(); i++){
					String[] temp = {c.getSender().getName(), receivers.get(i).getName()};
					writer.writeNext(temp);
				}
			}
			else {
				for (int i = 0; i < receivers.size()-1; i++){
					for (int j = i+1; j < receivers.size(); j++){
						String[] temp = {c.getReceivers().get(i).getName(), c.getReceivers().get(j).getName()};
						writer.writeNext(temp);
					}
				}
			}
		}
		writer.close();
	}
}
//
//	public void savePeople() throws IOException {
//		CSVWriter writer = new CSVWriter(new FileWriter("people"));
//		for (Person p : people) {
//			writer.writeNext(p.toCSVRowArray());
//		}
//		writer.close();
//	}
//
//	public void loadPeople() throws IOException {
//		CSVReader reader = new CSVReader(new FileReader("people"));
//		List<String[]> myRows = reader.readAll();
//		for (String[] row : myRows) {
//			String name = row[0];
//			String occupation = row[1];
//			String culture = row[2];
//			String gender = row[3];
//			String bio = row[4];
//			people.add(new Person(name, occupation, culture, gender, bio));
//		}
//	}
//
//}
