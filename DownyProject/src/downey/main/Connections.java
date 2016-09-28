package downey.main;

import java.util.ArrayList;

public abstract class Connections {
	private String date;
	private String notes;
	private String citation;
	private String location;
	private String source;
	public Connections(String date, String source, String location,
			  			String citation, String notes){
		this.date = date;
		this.notes = notes;
		this.citation = citation;
		this.location = location;
		this.source = source;

	}
	public String toString(){
			return "Date: " + date + ", Notes: " + notes + ", Citation: " + citation + ", Location: " + location
			+ ", Interaction Type: " + source;

	}
}
