package downey.main;

public class PersonContainsQuery implements PersonQuery {
	private String target;
	private String fieldName;

	public PersonContainsQuery(String target, String fieldName) {
		this.target = target;
		this.fieldName = fieldName;
	}
	@Override
	public boolean accepts(Person p) {
		switch (fieldName) {
			case "Occupation":
				return p.getOccupation().contains(target);
			case "Name":
				return p.getName().contains(target);
			case "Culture":
				return p.getCulture().contains(target);
			case "Gender":
				return p.getGender().contains(target);
			case "Bio":
				return p.getBio().contains(target);
			default:
				return false;
		}
	}
}
