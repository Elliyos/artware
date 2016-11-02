package downey.main;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControlledVocab {
	private ObservableList<String> genderOptions = FXCollections.observableArrayList("Unknown");
	private ObservableList<String> occupationOptions = FXCollections.observableArrayList("Unknown");
	private ObservableList<String> cultureOptions = FXCollections.observableArrayList("Unknown");
	private ObservableList<String> interactionTypeOptions = FXCollections.observableArrayList("Unknown");
	private ObservableList<String> locationOptions = FXCollections.observableArrayList("Unknown");
	private static ControlledVocab mainControlledVocab = new ControlledVocab();

	private ControlledVocab() {
	}

	public static ControlledVocab getControlledVocab() {
		return mainControlledVocab;
	}

	public ObservableList<String> getGenderOptions() {
		return genderOptions;
	}

	public ObservableList<String> getOccupationOptions() {
		return occupationOptions;
	}

	public ObservableList<String> getCultureOptions() {
		return cultureOptions;
	}

	public ObservableList<String> getInteractionTypeOptions() {
		return interactionTypeOptions;
	}

	public ObservableList<String> getLocationOptions() {
		return locationOptions;
	}

	public void addGenderOption(String gender) {
		if (!isDuplicate(gender, genderOptions))
			genderOptions.add(gender);
	}

	public void addOccupationOption(String occupation) {
		if (!isDuplicate(occupation, occupationOptions))
			occupationOptions.add(occupation);
	}

	public void addCultureOption(String culture) {
		if (!isDuplicate(culture, cultureOptions))
			cultureOptions.add(culture);
	}

	public void addInteractionTypeOption(String interactionType) {
		if (!isDuplicate(interactionType, interactionTypeOptions))
			interactionTypeOptions.add(interactionType);
	}

	public void addLocationOption(String location) {
		if (!isDuplicate(location, locationOptions))
			locationOptions.add(location);
	}

	public void removeGenderOption(String gender) {
		genderOptions.remove(gender);
	}

	public void removeOccupationOption(String occupation) {
		occupationOptions.remove(occupation);
	}

	public void removeCultureOption(String culture) {
		cultureOptions.remove(culture);
	}

	public void removeInteractionTypeOption(String interactionType) {
		interactionTypeOptions.remove(interactionType);
	}

	public void removeLocationOption(String location) {
		locationOptions.remove(location);
	}

	/**
	 * Converts a CSV of the controlled vocabulary into a String array of the
	 * vocabulary
	 * 
	 * @param csvInput
	 *            takes in a CSV file of the controlled vocabulary
	 * @return the String array of the controlled vocabulary
	 */
	public String[] toCSVRowArray(ObservableList<String> csvInput) {
		String[] result = new String[csvInput.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = csvInput.get(i);
		}
		return result;
	}

	/**
	 * Checks to see if the new vocab word is already a part of the list
	 * 
	 * @param target
	 * @param vocabList
	 * @return true if the word is a part of the list false if the word is not a
	 *         part of the list
	 */
	public boolean isDuplicate(String target, ObservableList<String> vocabList) {
		for (String vocab : vocabList) {
			if (vocab.equalsIgnoreCase(target)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Saves the controlled vocabulary to a CSV file
	 * 
	 * @throws IOException
	 *             if the file is not found
	 */
	public void saveControlledVocab() throws IOException {
		try (CSVWriter writer = new CSVWriter(new FileWriter("data/controlledVocab.csv"))) {
			writer.writeNext(toCSVRowArray(genderOptions));
			writer.writeNext(toCSVRowArray(cultureOptions));
			writer.writeNext(toCSVRowArray(occupationOptions));
			writer.writeNext(toCSVRowArray(interactionTypeOptions));
			writer.writeNext(toCSVRowArray(locationOptions));
		}
	}

	/**
	 * Loads the controlled vocabulary from a CSV file
	 * 
	 * @throws IOException
	 *             if the file is not found
	 */
	public void loadControlledVocab() throws IOException {
		CSVReader reader = new CSVReader(new FileReader("data/controlledVocab.csv"));
		List<String[]> vocabArrayList = reader.readAll();

		String[] genderArray = vocabArrayList.get(0);
		for (String gender : genderArray) {
			addGenderOption(gender);
		}

		String[] cultureArray = vocabArrayList.get(1);
		for (String culture : cultureArray) {
			addCultureOption(culture);
		}

		String[] occupationArray = vocabArrayList.get(2);
		for (String occupation : occupationArray) {
			addOccupationOption(occupation);
		}

		String[] interactionTypeArray = vocabArrayList.get(3);
		for (String type : interactionTypeArray) {
			addInteractionTypeOption(type);
		}

		String[] locationArray = vocabArrayList.get(4);
		for (String location : locationArray) {
			addLocationOption(location);
		}
		reader.close();
	}

}