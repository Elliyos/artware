package downey.main;

import java.util.ArrayList;
/**
 * 
 * IGNORE THIS CLASS FOR NOW
 *
 */
public abstract class Connections {
	private String date;
	private String notes;
	private String citation;
	private String location;
	private String interactionType;
	public Connections(String date, String interactionType, String location,
			  			String citation, String notes){
		this.date = date;
		this.notes = notes;
		this.citation = citation;
		this.location = location;
		this.interactionType = interactionType;

	}
	public String toString(){
			return "Date: " + date + ", Notes: " + notes + ", Citation: " + citation + ", Location: " + location
			+ ", Interaction Type: " + interactionType;

	}
}
