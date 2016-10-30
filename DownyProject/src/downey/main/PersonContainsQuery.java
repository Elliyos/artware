package downey.main;

public class PersonContainsQuery implements PersonQuery {
	private String target;
	private String fieldName;

	public PersonContainsQuery(String target, String fieldName) {
		this.target = target.toLowerCase();
		this.fieldName = fieldName.toLowerCase();
	}
	@Override
	public boolean accepts(Person p) {
		switch (fieldName) {
			case "occupation":
				return p.getOccupation().toLowerCase().contains(target);
			case "name":
				return p.getName().toLowerCase().contains(target);
			case "culture":
				return p.getCulture().toLowerCase().contains(target);
			case "gender":
				return p.getGender().toLowerCase().contains(target);
			case "bio":
				return p.getBio().toLowerCase().contains(target);
			default:
				return false;
		}
	}
}
