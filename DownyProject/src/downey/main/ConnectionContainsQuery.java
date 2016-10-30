package downey.main;

import java.util.ArrayList;

public class ConnectionContainsQuery implements ConnectionQuery{
	private String target;
	private String fieldName;

	public ConnectionContainsQuery(String target, String fieldName) {
		this.target = target;
		this.fieldName = fieldName;
	}
	@Override
	public boolean accepts(Connection c) {
		switch (fieldName) {
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
