package downey.main;
import java.util.*;
public class Connection extends Collection{
	private Date date;
	private String notes, citation, location, interactionType;
	private ArrayList<Person> contacts;
	private int numOfPeople;
	
	
	public Connection(Date date, String notes, String citation, String location, String interactionType,ArrayList<String> names){
		
			this.date = date;
			this.notes = notes;
			this.citation = citation;
			this.location = location;
			this.interactionType = interactionType;
			contacts = new ArrayList();
			for(int i = 0; i <= names.size(); i++){
				Person add = super.getAllPeople().get(super.search(names.get(i)));
				contacts.add(add);
			}
			 
	}
	/**
	 * Returns true if the the connection is the same as the parameter.
	 * 
	 * @param Object
	 * @return Boolean
	 */
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() == obj.getClass()) {
			Connection other = (Connection) obj;
			return (date.equals(other.date) && contacts.equals(other.contacts)
					&& interactionType.equals(other.interactionType));
		}
		return false;
	}
	/**
	 * Returns a Connection object as a String
	 * 
	 * @param 
	 * @return String
	 */
	public String toString(){
		return "Date: " + date + ", Notes: " + notes + ", Citation: " + citation + ", Location: " + location
				+ ", Interaction Type: " + interactionType;
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
	public int getNumPeople(){
		return numOfPeople;
	}
	public void setNumPeople(int numPeople){
		this.numOfPeople = numPeople;
	}


}
