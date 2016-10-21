import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;


public class FileReplace {
	Hashtable<String,Double> ftable = new Hashtable<String,Double>();
	public static void main() throws IOException {
		
		File folder = new File("G:/ML Project/train");
		File[] listOfDir = folder.listFiles();
		for (File file : listOfDir){
			if(file.isFile() && file.getName().equals("allen-p")){
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				while (br.ready())
				{
					String x=br.readLine().trim();
					String y = br.readLine().trim();
				}
				
			}
			
		}
	}
	
}
