package downey.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public final class GephiExporter implements Exporter {
	public static void main(String[] args) throws IOException{
		DataStorage DS = DataStorage.getMainDataStorage();
		DS.loadPeople();
		DS.loadConnections();
		Exporter gephiExporter = new GephiExporter();
		gephiExporter.export("data", "Paris");
	}
	private static final DataStorage DS = DataStorage.getMainDataStorage();
	public void export(String folderName, String stem) throws IOException{
		saveNodes(folderName + "/" + stem + "_Gephi_Export_Nodes.csv");
		mapNodesToEdges(folderName + "/" + stem + "_Gephi_Export_Edges.csv");
	}

	public void saveNodes(String fileName) throws IOException {
		String[] header = {"ID", "Label"};
		ArrayList<Person> people = DS.getPeopleArray();
            try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
                writer.writeNext(header);
                for (int i = 0; i < people.size(); i++){
                    String[] numberedNode = new String[2];
                    numberedNode[0] = Integer.toString(i+1);
                    numberedNode[1] = people.get(i).getName();
                    writer.writeNext(numberedNode);
                }
            }
	}
	public void mapNodesToEdges(String fileName) throws IOException{
		String[] header = {"Source", "Target", "Edge ID", "Date", "Interaction Type", "Location", "Citation", "Notes"};
            try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
                int currCount = 0;
                writer.writeNext(header);
                for (Connection c : DS.getConnectionArray()) {
                    currCount++;
                    ArrayList<Person> receivers = c.getReceivers();
                    if (!c.getSender().getName().equals("Group Connection")){
                        for (int i = 0; i < receivers.size(); i++){
                            String[] temp = {Integer.toString(c.getSender().getID()), Integer.toString(receivers.get(i).getID()), Integer.toString(currCount),c.getDate(),c.getInteractionType(),c.getLocation(),c.getCitation(),c.getNotes()};
                            writer.writeNext(temp);
                        }
                    }
                    else {
                        for (int i = 0; i < receivers.size()-1; i++){
                            for (int j = i+1; j < receivers.size(); j++){
                                String[] temp = {Integer.toString(receivers.get(i).getID()), Integer.toString(receivers.get(j).getID()), Integer.toString(currCount),c.getDate(),c.getInteractionType(),c.getLocation(),c.getCitation(),c.getNotes()};
                                writer.writeNext(temp);
                            }
                        }
                    }
                }
            }
	}
	}
