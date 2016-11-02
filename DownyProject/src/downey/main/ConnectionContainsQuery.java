package downey.main;

public class ConnectionContainsQuery implements ConnectionQuery {
	private String target;
	private String fieldName;

	/**
	 * Construct.
	 * 
	 * @param target
	 *            - String that will be compared in the accepts method
	 * @param fieldName
	 *            - String of the specified field of a connection that will be
	 *            compared
	 */
	public ConnectionContainsQuery(String target, String fieldName) {
		this.target = target.toLowerCase();
		this.fieldName = fieldName.toLowerCase();
	}

	/**
	 * Checks to see if the query will accept the connection into its set
	 * 
	 * @param C,
	 *            A connection that is used to check against the target for the
	 *            field defined by the query
	 * 
	 *            Returns true to add it to the query if the target matches the
	 */
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
		case "interaction type":
			return c.getInteractionType().toLowerCase().contains(target);
		case "location":
			return c.getLocation().toLowerCase().contains(target);
		case "citation":
			return c.getCitation().toLowerCase().contains(target);
		case "notes":
			return c.getNotes().toLowerCase().contains(target);
		default:
			return false;
		}
	}
}
