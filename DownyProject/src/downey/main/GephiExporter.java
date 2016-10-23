package downey.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;
import java.util.*;
public class GephiExporter {
	private static DataStorage DS = DataStorage.getMainDataStorage();
	public static void main(String[] args) throws IOException{
		GephiExporter g = new GephiExporter();
		g.saveNodes();
		g.mapNodesToEdges();
		
		
	}

	public void saveNodes() throws IOException {
		DS.loadPeople();
		ArrayList<Person> people = DS.getPeopleArray();
		CSVWriter writer = new CSVWriter(new FileWriter("Gephi_Export_File"));
		for (int i = 0; i < people.size(); i++){
			String[] numberedNode = new String[2];
			numberedNode[0] = Integer.toString(i+1);
			numberedNode[1] = people.get(i).getName();
			writer.writeNext(numberedNode);
		}
		writer.close();
	}
	public void mapNodesToEdges() throws IOException{
		DS.loadPeople();
		DS.loadConnections();
		CSVWriter writer = new CSVWriter(new FileWriter("Gephi_Nodes_Edges"));
		int currCount = 0;
		for (Connection c : DS.getConnectionArray()) {
			currCount++;
			ArrayList<Person> receivers = c.getReceivers();
			if (!c.getSender().getName().equals("Group Connection")){
				for (int i = 0; i < receivers.size(); i++){
					String[] temp = {Integer.toString(c.getSender().getID()), Integer.toString(receivers.get(i).getID()), Integer.toString(currCount)};
					writer.writeNext(temp);
				}
			}
			else {
				for (int i = 0; i < receivers.size()-1; i++){
					for (int j = i+1; j < receivers.size(); j++){
						String[] temp = {Integer.toString(receivers.get(i).getID()), Integer.toString(receivers.get(j).getID()), Integer.toString(currCount)};
						writer.writeNext(temp);
					}
				}
			}
		}
		writer.close();
	}
	}
