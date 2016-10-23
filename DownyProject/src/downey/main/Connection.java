package downey.main;

import java.io.Serializable;
import java.util.*;

/**
 * 
 * To store a group connection sender will be null (no direction), to store a
 * one to many connection sender will store the one and receivers will store the
 * many
 *
 */
public class Connection implements Serializable {
	private Person groupConnection = new Person("Group Connection", "", "", "", "");
	private String notes, citation, location, interactionType;
	private String date;
	private Person sender;
	private ArrayList<Person> receivers = new ArrayList<Person>();

	/**
	 * Constructor method for a many-to-many connection
	 * 
	 * @param date
	 * @param interactionType
	 * @param location
	 * @param citation
	 * @param notes
	 * @param sender
	 * @param people
	 */
	public Connection(ArrayList<Person> people, String date, String type, String location, String citation,
			String notes) {
		this.date = date;
		this.notes = notes;
		this.citation = citation;
		this.location = location;
		this.interactionType = type;
		this.sender = null;
		receivers = people;
	}

	/**
	 * Constructor method for a one-to-many connection
	 * 
	 * @param sender
	 * @param people
	 * @param date
	 * @param type
	 * @param location
	 * @param citation
	 * @param notes
	 */
	public Connection(Person sender, ArrayList<Person> people, String date, String type, String location,
			String citation, String notes) {
		this(people, date, type, location, citation, notes);
		this.sender = sender;
	}

	public Person getSender() {
		if (sender != null) {
			return sender;
		}
		return groupConnection;
	}

	public ArrayList<Person> getReceivers() {
		return receivers;
	}

	public boolean contains(Person person) {
		for (Person p : receivers) {
			if (p.equals(person)) {
				return true;
			}
		}
		return false;
	}

	public String[] toCSVRowArray() {
		if (sender != null) {
			return new String[] { sender.getName(), getReceiverNames(), date, interactionType, location, citation,
					notes };
		}
		return new String[] { getReceiverNames(), date, interactionType, location, citation, notes };
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
			return (date.equals(other.date) && receivers.equals(other.receivers)
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
	public String toString() {
		String connectionString = "Receivers: " + getReceiverNames() + ", Date: " + date + ", Interaction Type: "
				+ interactionType + ", Location: " + location + ", Citation: " + citation + ", Notes: " + notes;
		if (sender != null) {
			return "\nSender: " + sender.getName() + ": " + connectionString;
		}

		return "\n" + connectionString;
	}

	/**
	 * this method returns the names of all of the receivers for this
	 * connection, not the sender.
	 * 
	 * @return
	 */
	public String getReceiverNames() {
		if (receivers.isEmpty()) {
			return "No contacts";
		}
		String temp = "";
		for (int i = 0; i < receivers.size() - 1; i++) {
			temp += receivers.get(i).getName() + ", ";
		}
		temp += receivers.get(receivers.size() - 1).getName();
		return temp;
	}

	/**
	 * This method returns the names of all people who are involved in a
	 * conncetion with this person
	 * 
	 * @param name,
	 *            person with the connections
	 * @return the people involved, string
	 */
//	public String getConnectedPeople(String name) {
//		String result;
//		if (sender != null && !sender.getName().equalsIgnoreCase(name)) {
//			result = sender.getName();
//		} else {
//			result = receivers.get(0).getName() + "";
//		}
//		for (int i = 1; i < receivers.size(); i++) {
//			if (!receivers.get(i).getName().equalsIgnoreCase(name))
//				result += ", " + receivers.get(i).getName();
//		}
//		return result + "\n";
//
//	}

	public boolean editConnection(ArrayList<Person> people, String date, String type, String location, String citation,
			String notes) {
		if (people == null || date == null || type == null || location == null || citation == null || notes == null) {
			return false;
		}
		setReceivers(people);
		setDate(date);
		setInteractionType(type);
		setLocation(location);
		setCitation(citation);
		setNotes(notes);
		return true;
	}

	public boolean editConnection(Person sender, ArrayList<Person> people, String date, String type, String location,
			String citation, String notes) {
		if (sender == null || people == null || date == null || type == null || location == null || citation == null
				|| notes == null) {
			return false;
		}
		editConnection(people, date, type, location, citation, notes);
		setSender(sender);
		return true;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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

	public void setSender(Person sender) {
		this.sender = sender;
	}

	public void setReceivers(ArrayList<Person> receivers) {
		this.receivers = receivers;
	}

}
