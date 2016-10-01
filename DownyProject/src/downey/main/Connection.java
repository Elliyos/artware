package downey.main;
import java.util.*;
/**
 * 
 * To store a group connection sender will be null (no direction), to store a one to many connection
 * sender will store the one and receivers will store the many
 *
 */
public class Connection{
	private String date;
	private String notes;
	private String citation;
	private String location;
	private String source;
	private Person sender;
	private ArrayList<Person> receivers = new ArrayList<Person>();
	
	/**
	 * 
	 * 
	 * @param date
	 * @param source
	 * @param location
	 * @param citation
	 * @param notes
	 * @param sender
	 * @param people
	 */
	public Connection(Person sender, ArrayList<Person> people, String date, String type,
			          String location, String citation, String notes){
		
			this.date = date;
			this.notes = notes;
			this.citation = citation;
			this.location = location;
			this.source = type;
			this.sender = sender;
			receivers = people;
			 
	}
	public Person getSender(){
		return sender;
	}
	public ArrayList<Person> getReceivers(){
		return receivers;
	}
	public boolean contains(Person person){
		for (Person p: receivers){
			if (p.equals(person)){
				return true;
			}
		}
		return false;
	}
	public String[] toCSVRowArray() {
		return new String[] { sender.getName(), getReceiverNames() , date, source,location,citation, notes };
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
	public String toString(){
		return sender.getName() + ": Receivers: " + getReceiverNames() + ", Date: " + date + ", Interaction Type: " + source
				+ ",Location: " + location + ",Citation:" + citation + ",Notes: " + notes +  "\n";
	}
	
	public String getReceiverNames(){
		if (receivers.isEmpty()){
			return "No contacts";
		}
		String temp = receivers.get(0).getName();
		for (int i = 1; i < receivers.size(); i++){
			temp += receivers.get(i).getName() +", ";
		}
		return temp;
	}


}
