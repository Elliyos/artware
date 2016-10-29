package downey.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public final class GephiExporter {
	private static final DataStorage DS = DataStorage.getMainDataStorage();
	public void export() throws IOException{
		saveNodes();
		mapNodesToEdges();
	}

	public void saveNodes() throws IOException {
		String[] header = {"ID", "Label"};
		DS.loadPeople();
		ArrayList<Person> people = DS.getPeopleArray();
            try (CSVWriter writer = new CSVWriter(new FileWriter("data/Gephi_Export_File"))) {
                writer.writeNext(header);
                for (int i = 0; i < people.size(); i++){
                    String[] numberedNode = new String[2];
                    numberedNode[0] = Integer.toString(i+1);
                    numberedNode[1] = people.get(i).getName();
                    writer.writeNext(numberedNode);
                }
            }
	}
	public void mapNodesToEdges() throws IOException{
		String[] header = {"Source", "Target", "Edge ID", "Date", "Interaction Type", "Location", "Citation", "Notes"};
		DS.loadPeople();
		DS.loadConnections();
            try (CSVWriter writer = new CSVWriter(new FileWriter("data/Gephi_Nodes_Edges"))) {
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
