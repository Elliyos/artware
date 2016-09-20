import java.util.*;
public class Person {
	private String occupation, name, cultureID, gender, bio;
	private int connectionCount;
	private ArrayList<Connection> contacts;

	
	public Person(String occupation, String name, String cultureID, String gender, String bio){
		this.occupation = occupation;
		this.name = name;
		this.cultureID = cultureID;
		this.gender =gender;
		this.bio = bio;
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
	
	public void incrementContactCount(){
		this.connectionCount ++;
	}
	}


