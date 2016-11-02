package downey.main;

public class Person {

	private String occupation, name, culture, gender, bio, nickname;
	private final int ID;

	public Person(String name, String nickname, String culture, String occupation, String gender, String bio) {
		this.name = name;
		this.nickname = nickname;
		this.culture = culture;
		this.occupation = occupation;
		this.gender = gender;
		this.bio = bio;
		ID = DataStorage.getMainDataStorage().getNumPeople() + 1;
	}

	public int getID() {
		return ID;
	}

	/**
	 * Compares two Person objects and return true if they are the same, false
	 * otherwise
	 * 
	 * @param Object
	 * @return Boolean
	 */
	@Override
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
	@Override
	public String toString() {
		return "\nName: " + name + ", Occupation: " + occupation + ", Culture: " + culture + ", Gender: " + gender
				+ ", Bio: " + bio;
	}

	public String[] toCSVRowArray() {
		return new String[] { name, nickname, culture, occupation, gender, bio };
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCulture() {
		return culture;
	}

	public void setCulture(String culture) {
		this.culture = culture;
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

	/**
	 * Edits the information in the person
	 * 
	 * @param name
	 *            - String persons name
	 * @param nickname
	 *            - String persons nickname
	 * @param culture
	 *            - String persons culture
	 * @param occupation
	 *            - String persons occupation
	 * @param gender
	 *            - String persons Gender
	 * @param bio
	 *            - String other information about the person
	 * @return
	 */
	public boolean editPerson(String name, String nickname, String culture, String occupation, String gender,
			String bio) {
		setName(name);
		setNickname(nickname);
		setCulture(culture);
		setOccupation(occupation);
		setGender(gender);
		setBio(bio);
		return true;
	}

}
