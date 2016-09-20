import java.util.*;
public class Connection {
	private Date date;
	private String notes, citation, location, interactionType;
	private Person contact;
	private int numOfPeople;
	
	
	public Connection(Date date, String notes, String citation, String location, String interactionType,int people){
		
			this.date = date;
			this.notes = notes;
			this.citation = citation;
			this.location = location;
			this.interactionType = interactionType;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCitation() {
		return citation;
	}
	public void setCitation(String citation) {
		this.citation = citation;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getInteractionType() {
		return interactionType;
	}
	public void setInteractionType(String interactionType) {
		this.interactionType = interactionType;
	}
	public Person getContact() {
		return contact;
	}
	public void setContact(Person contact) {
		this.contact = contact;
	}
	public int getNumPeople(){
		return numOfPeople;
	}
	public void setNumPeople(int numPeople){
		this.numOfPeople = numPeople;
	}

}
