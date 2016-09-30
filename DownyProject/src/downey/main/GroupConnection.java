package downey.main;
import java.util.Date;

public class GroupConnection extends Connection {

		private int numPeople;
		
	public GroupConnection(Date date, String notes, String citation, String location, String interactionType,
			int people) {
		super(date, notes, citation, location, interactionType);
		numPeople = people;
	}
	
}
