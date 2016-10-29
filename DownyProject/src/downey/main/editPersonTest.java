package downey.main;

import static org.junit.Assert.*;

import org.junit.Test;

public class editPersonTest {

	@Test
	public void testEditPersonChangeName() {
		Person person1 = new Person("Joe", "American", "Artist", "Male", "28 years old");
		Person person2 = new Person("Chris", "American", "Artist", "Male", "28 years old");
		person1.editPerson("Chris", "American", "Artist", "Male", "28 years old");
		
		assertEquals(person2.getName(), person1.getName());
	}
	@Test
	public void testEditPersonChangeCulture() {
		Person person1 = new Person("Joe", "American", "Artist", "Male", "28 years old");
		Person person2 = new Person("Joe", "French", "Artist", "Male", "28 years old");
		person1.editPerson("Joe", "French", "Artist", "Male", "28 years old");
		
		assertEquals(person2.getCulture(), person1.getCulture());
	}
	@Test
	public void testEditPersonChangeOccupation() {
		Person person1 = new Person("Joe", "American", "Artist", "Male", "28 years old");
		Person person2 = new Person("Joe", "American", "Sculptor", "Male", "28 years old");
		person1.editPerson("Joe", "American", "Sculptor", "Male", "28 years old");
		
		assertEquals(person2.getOccupation(), person1.getOccupation());
	}
	@Test
	public void testEditPersonChangeGender() {
		Person person1 = new Person("Joe", "American", "Artist", "Male", "28 years old");
		Person person2 = new Person("Joe", "American", "Artist", "Female", "28 years old");
		person1.editPerson("Joe", "American", "Artist", "Female", "28 years old");
		
		assertEquals(person2.getGender(), person1.getGender());
	}
	@Test
	public void testEditPersonChangeNotes() {
		Person person1 = new Person("Joe", "American", "Artist", "Male", "28 years old");
		Person person2 = new Person("Joe", "American", "Artist", "Male", "45 years old");
		person1.editPerson("Joe", "American", "Artist", "Male", "45 years old");
		
		assertEquals(person2.getBio(), person1.getBio()  );
	}
}
