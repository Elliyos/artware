package downey.main;

import java.util.ArrayList;

public class ConnectionContainsQuery implements ConnectionQuery{
	private String target;
	private String fieldName;

	public ConnectionContainsQuery(String target, String fieldName) {
		this.target = target.toLowerCase();
		this.fieldName = fieldName.toLowerCase();
	}
	@Override
	public boolean accepts(Connection c) {
		switch (fieldName) {
			case "sender":
				return c.getSender().getName().toLowerCase().contains(target);
			case "receivers":
				for (String name : c.getReceiverNameList())
					if (name.toLowerCase().contains(target))
						return true;
			case "date":
				return c.getDate().contains(target);
			case "type":
				return c.getInteractionType().contains(target);
			case "location":
				return c.getLocation().contains(target);
			case "citation":
				return c.getCitation().contains(target);
			case "notes":
				return c.getNotes().contains(target);
			default:
				return false;
		}
	}
}
