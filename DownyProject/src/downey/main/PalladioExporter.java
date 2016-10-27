package downey.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.opencsv.CSVWriter;

public class PalladioExporter {
	public static void main(String[] args) throws IOException{
		PalladioExporter p = new PalladioExporter();
		p.saveEdges();
	}
	private final DataStorage DS = DataStorage.getMainDataStorage();

	public void saveEdges() throws IOException {
		String[] header = {"Source", "Target"};
		DS.loadPeople();
		DS.loadConnections();
            try (CSVWriter writer = new CSVWriter(new FileWriter("data/Palladio_Export_File"))) {
                writer.writeNext(header);
                DS.getConnectionArray().forEach((c) -> {
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
                    });
            }
	}
}
