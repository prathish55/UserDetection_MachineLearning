import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

import edu.smu.tspell.wordnet.*;



public class UserTest {
	TreeMap<String, Integer> userWords;

	public static void main(String[] args) throws IOException {
		double tp=0.0;
		double fp=0.0;

		//UserTest test = new UserTest();
		//String str = test.readtestfile();
		System.setProperty("wordnet.database.dir", "dict");
		File folder = new File("C:/Users/Prathish/Downloads/enron_mail_20150507/test1");
		File[] listOfDir = folder.listFiles();
		for (File dir : listOfDir){
			if(dir.isDirectory() ){
				System.out.println(dir.getName());
				File file1 = new File("C:/Users/Prathish/Downloads/enron_mail_20150507/maildir/"+dir.getName()+".txt");

				File[] allfiles = dir.listFiles();

				for(File sub :allfiles){
					if(sub.isFile()) {

						UserTest test = new UserTest();
						String str = test.readtestfile(sub.getAbsolutePath());
						//if(!str.trim().equals("")){
						String[] temp =test.convert2Array(str);
						test.putToMap(temp);
						FileParser p= new FileParser();
						String predict = p.parse(test.userWords,temp,dir.getName());
						predict= predict.substring(0, predict.length()-4);
						if(dir.getName().equals(predict))tp++;
						else fp++;
						
						System.out.println("File name : "+sub.getName()+", original sender : "+dir.getName() +", predicted sender : "+predict);
						//}
					}
				}
			}
			System.out.println("Accuracy = "+((double)tp)/(tp+fp)*100 +"  true prediction =  "+tp +" false prediction = "+fp);
		}



	}

	String readtestfile(String file) throws IOException {
		String s="";
		// System.out.println(file.getName());
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		while(br.ready() && !br.readLine().equals(""));
		
		while (br.ready())
		{
			//counter++;
			String x=br.readLine().trim();
			if(x.contains("-----"))
				break;
			s += x + "\n";
		}

		br.close();
		return s;
	}

	public String[] convert2Array(String str)
	{
		String[] temp=new String[str.length()];
		temp=str.split("\\s+");
		return temp;
	}

	public void putToMap(String[] s2) {
		userWords=new TreeMap<String,Integer>();
		String s=" ",x="";
		WordNetDatabase database = WordNetDatabase.getFileInstance(); 
		for(int i=0;i<s2.length;i++)
		{
			//s2[i]= s2[i].replaceAll("[^a-zA-Z]","");
			if(!s2[i].isEmpty())
			{
				Synset[] synsets = database.getSynsets(s2[i]);

				if(synsets.length!=0)
				{
					//System.out.println(s2[i]);
					if (!userWords.containsKey(s2[i])) {
						userWords.put(s2[i], 1);
					} else {
						userWords.put(s2[i], (Integer) userWords.get(s2[i]) + 1);
					}
				}
			}
		}
		/*for (Map.Entry<String,Integer> entry : allWords.entrySet()) {
		     System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
		}*/
	}
}
