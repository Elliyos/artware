package downey.main;
import java.util.*;
public class SingleConnection extends Connections{
//	private String date;
//	private String notes;
//	private String citation;
//	private String location;
//	private String source;
	private Person other;
	
	
	public SingleConnection(String date, String source, String location,
					  String citation, String notes, Person other){
			super(date, source, location, citation, notes);
			this.other = other;
			 
	}
	public String toString(){
		return super.toString() + "\nPeople: " + other.getName();
	}
}