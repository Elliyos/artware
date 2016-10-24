package downey.main;

public class SelectedInformationTracker {

	private static String selectedName;
	private static String selectedRecipientNames;
	private static Connection selectedConnection;

	public static void storeSelectedName(String s) {
		selectedName = s;
	}

	public static String getSelectedName() {
		return selectedName;
	}

	public static void storeSelectedNames(String s) {
		selectedRecipientNames = s;
	}

	public static String getSelectedNames() {
		return selectedRecipientNames;
	}

	public static void storeSelectedConnection(Connection c) {
		selectedConnection = c;
	}

	public static Connection getSelectedConnection() {
		return selectedConnection;
	}


}
