package downey.main;

import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.opencsv.CSVWriter;

public class PalladioExporter implements Exporter {
	public static void main(String[] args) throws EOFException, IOException{
		DataStorage DS = DataStorage.getMainDataStorage();
		DS.loadPeople();
		DS.loadConnections();
		Exporter palladioExporter = new PalladioExporter();
		palladioExporter.export("data", "Paris");
	}
	public void export(String folder, String stem) throws IOException{
		saveEdges(folder + "/" + stem + "_Palladio_Export.csv");
	}
	public void saveEdges(String edgeFileName) throws IOException {
		DataStorage DS = DataStorage.getMainDataStorage();

		String[] header = {"Source", "Target"};
            try (CSVWriter writer = new CSVWriter(new FileWriter(edgeFileName))) {
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
