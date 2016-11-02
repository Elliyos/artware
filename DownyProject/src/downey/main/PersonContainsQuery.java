package downey.main;

public class PersonContainsQuery implements PersonQuery {
	private String target;
	private String fieldName;

	public PersonContainsQuery(String target, String fieldName) {
		this.target = target.toLowerCase();
		this.fieldName = fieldName.toLowerCase();
	}
	
	/**
	 * Checks to see if the query will accept the person into its set
	 * 
	 * @param p,
	 *            A Person that is used to check against the target for the
	 *            field defined by the query
	 * 
	 *            Returns true to add it to the query if the target matches the
	 */
	
	@Override
	public boolean accepts(Person p) {
		switch (fieldName) {
			case "occupation":
				return p.getOccupation().toLowerCase().contains(target);
			case "name":
				return p.getName().toLowerCase().contains(target);
			case "nickname":
				return p.getNickname().toLowerCase().contains(target);
			case "culture":
				return p.getCulture().toLowerCase().contains(target);
			case "gender":
				return p.getGender().toLowerCase().equals(target);
			case "bio":
				return p.getBio().toLowerCase().contains(target);
			default:
				return false;
		}
	}
}
