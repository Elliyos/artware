package downey.main;

import java.util.*;

public class Person {
	private String occupation;
	private String name;
	private String culture;
	private String gender;
	private String bio;

	public Person(String name, String culture, String occupation, String gender, String bio) {
		this.occupation = occupation;
		this.name = name;
		this.culture = culture;
		this.gender = gender;
		this.bio = bio;
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
			return (name.equals(other.name));
		}
		return false;
	}
	/**
	 * Returns the Person object as a string
	 * 
	 * @param 
	 * @return String
	 */
	public String toString(){
		return "\nName: " + name + ", Occupation: " + occupation + ", Culture: " + culture + ", Gender: " + gender
				+ ", Bio: " + bio;
	}
	public String[] toCSVRowArray() {
		return new String[] { name, culture ,occupation, gender, bio};
	}

	public String getOccupation() {
		return occupation;
	}

	public String getName() {
		return name;
	}

	public String getCulture() {
		return culture;
	}


	public String getGender() {
		return gender;
	}

	public String getBio() {
		return bio;
	}

	public boolean editPerson(String name, String culture, String occupation, String gender, String bio){
		if (name == null || culture == null || occupation == null || gender == null || bio == null){
			return false;
		}
		this.name = name;
		this.culture = culture;
		this.occupation = occupation;
		this.gender = gender;
		this.bio = bio;
		return true;
		
		
	}

}
