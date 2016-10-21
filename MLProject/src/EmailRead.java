import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import edu.smu.tspell.wordnet.*;


public class EmailRead {

	TreeMap<String, Integer> allWords;

	public String readEmail(String path)throws IOException
	{
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		String s="",x="";
		for (File file : listOfFiles) 
		{
			if (file.isFile()) 
			{
				// System.out.println(file.getName());
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				while(br.ready() && !br.readLine().equals(""));

				while (br.ready())
				{
					x=br.readLine().trim();
					if(x.contains("-----"))
						break;
					//x.replaceAll("[^a-zA-Z]","");
					s += x + "\n";
				}
				br.close();
				fr.close();
			}
			else if(file.isDirectory())System.out.println(file.getName());
		}
		return s;
	}

	public String[] convert2Array(String str)
	{
		String[] temp=new String[str.length()];
		temp=str.split("\\s+");
		return temp;
	}

	public void readUser ()throws IOException
	{
		File folder = new File("G:/ML Project/maildir");
		File[] listOfDir = folder.listFiles();
		for (File dir : listOfDir){
			if(dir.isDirectory()){
			//if(dir.isDirectory() && dir.getName().equals("allen-p")){
				System.out.println(dir.getName());
				File file1 = new File("C:/Users/Prathish/Downloads/enron_mail_20150507/train/"+dir.getName()+".txt");
				if (!file1.exists()) {
					file1.createNewFile();
				}
				FileWriter fw = new FileWriter(file1.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				File[] subDir = dir.listFiles();
				String str="";
				for(File sub :subDir){

					if(sub.getName().contains("sent")) {
						str += readEmail(sub.getAbsolutePath());
						//bw.write(str);
						//bw.newLine();
						System.out.println(sub.getAbsolutePath());
					}
				}
				String[] temp =convert2Array(str);
				//String[] temp =stopWordsRemoval(str);
				putToMap(temp);
				//bw.write("*****bigram");
				//bw.newLine();
				Bigrams bGms = new Bigrams();
				bGms.doBiGrams(temp, bw);
				//BiGrams(temp,bw);
				//bw.write("*****trigram");
				//bw.newLine();
				Trigrams tGms = new Trigrams();
				tGms.doTriGrams(temp, bw);
				//TriGrams(temp,bw);
				//bw.write("*****high frequency");
				//bw.newLine();
				HighFrequency freq =new HighFrequency();
				freq.findFreqWords(bw, allWords);
				//find5FreqWords(bw);
				PositiveNegativeAnalyzer analyze = new PositiveNegativeAnalyzer();
				int pc= analyze.Analyzer("positive_words.txt", allWords);
				int nc=analyze.Analyzer("negative_words.txt", allWords);
				//bw.write("*****Positive");
				//bw.newLine();
				bw.write(((double)pc) + "");
				//bw.newLine();
				//bw.write("*****Negative");
				bw.newLine();
				bw.write(((double)nc) + "");
				bw.newLine(); 
				bw.close();
				fw.close();
			}
		}
	}

	private void putToMap(String[] s2) {
		allWords=new TreeMap<String,Integer>();
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
					if (!allWords.containsKey(s2[i])) {
						allWords.put(s2[i], 1);
					} else {
						allWords.put(s2[i], (Integer) allWords.get(s2[i]) + 1);
					}
				}
			}
		}
		/*for (Map.Entry<String,Integer> entry : allWords.entrySet()) {
		     System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
		}*/
	}

	public String[] stopWordsRemoval(String str) throws IOException
	{
		str=str.toLowerCase();
		int numberOfLines;

		String[] splitarray=new String[str.length()];
		str=str.replaceAll("[\\n\\t\\d]+"," ");
		splitarray=str.split("\\s+");

		for(int k=0;k<splitarray.length;k++)
		{
			if(splitarray[k].contains("'"))
				continue;
			//else	
			//splitarray[k]=splitarray[k].replaceAll("[^a-zA-Z]","");
		}
		FileReader fr = new FileReader("stopwords.txt");
		BufferedReader textReader = new BufferedReader(fr);

		numberOfLines = readLines();
		String[ ] textData = new String[numberOfLines];

		int i,j;
		for (i=0; i < numberOfLines; i++)
		{
			textData[ i ] = textReader.readLine();
			//System.out.println(textData[i]);
			for(j=0;j<splitarray.length;j++)
			{
				if(splitarray[j].equals(textData[i]))
				{
					splitarray[j]="";
				}
			}

		}
		textReader.close( );
		return splitarray;
	}

	public int readLines() throws IOException
	{
		FileReader file_to_read = new FileReader("stopwords.txt");
		BufferedReader bf = new BufferedReader(file_to_read);

		String aLine;
		int numberofLines=0;
		while ( ( aLine = bf.readLine( ) ) != null ) {
			numberofLines++;
		}
		bf.close();
		return numberofLines;
	}
}
