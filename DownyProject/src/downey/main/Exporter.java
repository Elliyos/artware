package downey.main;

import java.io.IOException;

public interface Exporter {
	public void export(String folderName, String stem) throws IOException;

}
