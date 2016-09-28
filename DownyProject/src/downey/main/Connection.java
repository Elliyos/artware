package downey.main;
import java.util.*;
public class Connection{
	private String date;
	private String notes;
	private String citation;
	private String location;
	private String source;
	private ArrayList<Person> contacts;
	private int numOfPeople;
	
	
	public Connection(String date, String source, String location,
					  String citation, String notes, ArrayList<Person> people){
		
			this.date = date;
			this.notes = notes;
			this.citation = citation;
			this.location = location;
			this.source = source;
			contacts = people;
			 
	}
//	/**
//	 * Returns true if the the connection is the same as the parameter.
//	 * 
//	 * @param Object
//	 * @return Boolean
//	 */
//	public boolean equals(Object obj) {
//		if (obj == this) {
//			return true;
//		}
//		if (obj == null) {
//			return false;
//		}
//		if (this.getClass() == obj.getClass()) {
//			Connection other = (Connection) obj;
//			return (date.equals(other.date) && contacts.equals(other.contacts)
//					&& interactionType.equals(other.interactionType));
//		}
//		return false;
//	}
//	/**
//	 * Returns a Connection object as a String
//	 * 
//	 * @param 
//	 * @return String
//	 */
//	public String toString(){
//		return "Date: " + date + ", Notes: " + notes + ", Citation: " + citation + ", Location: " + location
//				+ ", Interaction Type: " + interactionType;
//	}
	


}
