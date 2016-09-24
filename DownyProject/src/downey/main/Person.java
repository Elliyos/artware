package downey.main;

import java.util.*;

public class Person {
	private String occupation, name, cultureID, gender, bio;
	private int connectionCount;
	private ArrayList<Connection> contacts;

	public Person(String occupation, String name, String cultureID, String gender, String bio) {
		this.occupation = occupation;
		this.name = name;
		this.cultureID = cultureID;
		this.gender = gender;
		this.bio = bio;
	}

	/**
	 * Returns a String of all the connections a Person has.
	 * 
	 * @param
	 * @return String
	 */
	public String getConnections() {
		String result = null;
		for (int i = 0; i < contacts.size(); i++) {
			result += contacts.get(i).toString() + "\n";
		}
		return result;
	}

	/**
	 * Adds a connection to the a specific person's list of connections.
	 * 
	 * @param Connection
	 * @return void
	 */
	public void addConnection(Connection c) {
		getContacts().add(c);
	}

	/**
	 * Compares two Person objects and return true if they are the same, false
	 * otherwise
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
			Person other = (Person) obj;
			return (name.equals(other.name) && contacts.equals(other.contacts));
		}
		return false;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCultureID() {
		return cultureID;
	}

	public void setCultureID(String cultureID) {
		this.cultureID = cultureID;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public ArrayList<Connection> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<Connection> contacts) {
		this.contacts = contacts;

	}

	public int getConnectionCount() {
		return connectionCount;
	}

	public void setConnectionCount(int connectionCount) {
		this.connectionCount = connectionCount;
	}

	public void incrementContactCount() {
		this.connectionCount++;
	}

}
