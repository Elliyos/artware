package downey.main;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ConnectionEditTest {
	private Person testPerson1 = new Person("Paul","10/29/2016", "Letter", "Olin", "No Notes");
	private Person testPerson2 = new Person("Steve","10/29/2016", "Letter", "Olin", "No Notes");
	private Person testPerson3 = new Person("Jimmy","10/29/2016", "Letter", "Olin", "No Notes");
	private Person testPerson4 = new Person("Lasean","10/29/2016", "Letter", "Olin", "No Notes");
	@Test
	public void testEditSender() {
		ArrayList<Person> testPeople = new ArrayList<>();
		testPeople.add(testPerson1);
		testPeople.add(testPerson2);
		Connection testConnection = new Connection(testPerson3, testPeople, "10/25/2016","Meeting"
				,"Paris", "aaaa", "No Notes");
		testConnection.editConnection(testPerson1,testPeople, "10/25/2016","Meeting"
				,"Paris", "aaaa", "No Notes");
		assertEquals("Paul", testConnection.getSender().getName());
	}
	@Test
	public void testEditGroupConnectionOnePerson(){
		ArrayList<Person> testPeople = new ArrayList<>();
		testPeople.add(testPerson1);
		testPeople.add(testPerson2);
		testPeople.add(testPerson3);
		
		Connection testConnection = new Connection(testPeople, "10/25/2016","Meeting"
				,"Paris", "aaaa", "No Notes");
		
		ArrayList<Person> newPeople = new ArrayList<>();
		newPeople.add(testPerson2);
		newPeople.add(testPerson3);
		
		testConnection.editConnection(newPeople, "10/25/2016","Meeting"
				,"Paris", "aaaa", "No Notes");
		assertEquals(newPeople, testConnection.getReceivers());
	}
	@Test
	//Test if the date changes properly
	public void testEditDate(){
		ArrayList<Person> testPeople = new ArrayList<>();
		testPeople.add(testPerson1);
		testPeople.add(testPerson2);
		testPeople.add(testPerson3);
		Connection testConnection = new Connection(testPeople, "10/25/2016","Meeting"
				,"Paris", "aaaa", "No Notes");
		testConnection.editConnection(testPeople, "5/24/2015","Meeting"
				,"Paris", "aaaa", "No Notes");
		String newDate = testConnection.getDate();
		assertEquals(newDate, testConnection.getDate());
	}
	@Test
	//If the user enters nothing for a date, set it to Unknown
	public void testEditLocationNull(){
		ArrayList<Person> testPeople = new ArrayList<>();
		testPeople.add(testPerson1);
		testPeople.add(testPerson2);
		testPeople.add(testPerson3);
		Connection testConnection = new Connection(testPeople, "10/25/2016","Meeting"
				,"Paris", "aaaa", "No Notes");
		testConnection.editConnection(testPeople, "10/25/2016","Meeting"
				,"", "aaaa", "No Notes");
		String newLocation = testConnection.getLocation();
		assertEquals("Unknown", newLocation);
	}
}
