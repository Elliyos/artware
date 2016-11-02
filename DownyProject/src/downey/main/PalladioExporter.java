package downey.main;

import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import com.opencsv.CSVWriter;

public class PalladioExporter implements Exporter {
	private ArrayList<Connection> connections;

	public PalladioExporter(ArrayList<Connection> connections) {
		this.connections = connections;
	}

	public void export(String stem) throws IOException {
		saveEdges(stem + "_Palladio_Export.csv");
	}

	/**
	 * Saves the information of the connections
	 * 
	 * @param edgeFileName
	 *            - the name of the file being saved
	 * @throws IOException
	 */
	public void saveEdges(String edgeFileName) throws IOException {

		String[] header = {"Source", "Target"};
            try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(edgeFileName),"UTF-8"))) {
                writer.writeNext(header);
                connections.forEach((c) -> {
                    ArrayList<Person> receivers = c.getReceivers();
                    if (!c.getSender().getName().equals("Group Connection")){
                        for (int i = 0; i < receivers.size(); i++){
                        	
                            String[] temp = {c.getSender().getNickname(), receivers.get(i).getNickname()};
                            writer.writeNext(temp);
                        }
                    }
                    else {
                        for (int i = 0; i < receivers.size()-1; i++){
                            for (int j = i+1; j < receivers.size(); j++){
                                String[] temp = {c.getReceivers().get(i).getNickname(), c.getReceivers().get(j).getNickname()};
                                writer.writeNext(temp);
                            }
                        }
                    }
                    });
            }
	}
}
