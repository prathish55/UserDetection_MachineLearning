import java.io.IOException;
import edu.smu.tspell.wordnet.*;

public class UserDetection {

	public static void main(String[] args) {
	
		System.setProperty("wordnet.database.dir", "dict");
		EmailRead er = new EmailRead();
		try {
			 er.readUser();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
