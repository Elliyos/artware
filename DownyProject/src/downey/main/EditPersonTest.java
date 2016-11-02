package downey.main;

import static org.junit.Assert.*;

import org.junit.Test;

public class EditPersonTest {

	// Checks if changing the persons name works properly
	@Test
	public void testEditPersonChangeName() {
		Person person1 = new Person("Joe", "JJ", "American", "Artist", "Male", "28 years old");
		Person person2 = new Person("Chris", "CM", "American", "Artist", "Male", "28 years old");
		person1.editPerson("Chris", "CM", "American", "Artist", "Male", "28 years old");

		assertEquals(person2.getName(), person1.getName());
	}

	// Checks if changing the persons culture works properly
	@Test
	public void testEditPersonChangeCulture() {
		Person person1 = new Person("Joe", "JJ", "American", "Artist", "Male", "28 years old");
		Person person2 = new Person("Samuel", "Sam", "French", "Artist", "Male", "38 years old");
		person1.editPerson("Joe", "JJ", "French", "Artist", "Male", "28 years old");

		assertEquals(person2.getCulture(), person1.getCulture());
	}

	// Checks if changing the persons Occupation works properly
	@Test
	public void testEditPersonChangeOccupation() {
		Person person1 = new Person("Joe", "JJ", "American", "Artist", "Male", "28 years old");
		Person person2 = new Person("Joseph", "Joe", "British", "Sculptor", "Male", "28 years old");
		person1.editPerson("Joe", "JJ", "American", "Sculptor", "Male", "28 years old");

		assertEquals(person2.getOccupation(), person1.getOccupation());
	}

	// Check to see if changing the Gender works properly
	@Test
	public void testEditPersonChangeGender() {
		Person person1 = new Person("Joe", "JJ", "American", "Artist", "Male", "28 years old");
		Person person2 = new Person("Sam", "", "American", "Artist", "Female", "28 years old");
		person1.editPerson("Joe", "JJ", "American", "Artist", "Female", "28 years old");

		assertEquals(person2.getGender(), person1.getGender());
	}

	// Checks to see if changing the Notes works properly
	@Test
	public void testEditPersonChangeNotes() {
		Person person1 = new Person("Joe", "JJ", "American", "Artist", "Male", "28 years old");
		Person person2 = new Person("Johnathan", "Jon", "American", "Artist", "Male", "45 years old");
		person1.editPerson("Joe", "JJ", "American", "Artist", "Male", "45 years old");

		assertEquals(person2.getBio(), person1.getBio());
	}

	// Checks to make sure edit person works when nothing is entered for name or
	// notes
	@Test
	public void testEditPersonNullParamater() {
		Person person1 = new Person("Joe", "JJ", "American", "Artist", "Male", "28 years old");
		Person person2 = new Person(" ", "", "American", "Artist", "Male", " ");
		person1.editPerson(" ", "Joe", "American", "Artist", "Male", " ");

		assertEquals(person2.getName(), person1.getName());
		assertEquals(person2.getBio(), person1.getBio());
	}
}
