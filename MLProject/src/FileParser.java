import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.TreeMap;


public class FileParser {
	Hashtable<String,Integer> btable;
	Hashtable<String,Integer> ttable;
	int pc,nc;
	Hashtable<String,Double> utable = new Hashtable<String,Double>();

	public String parse(TreeMap<String, Integer> userWords, String[] temp, String defaultName){


		try {
			fetchAll(userWords,temp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File folder = new File("C:/Users/Prathish/Downloads/enron_mail_20150507/train");
		File[] listOfDir = folder.listFiles();
		for(File file :listOfDir){
			if(file.isFile()){

				try {
					//System.out.println("up "+file.getName());
					updateWeights(file,userWords);
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		String maxKey=defaultName+".txt";
		Double maxValue = Double.MIN_VALUE; 
		for(Entry<String, Double> entry : utable.entrySet()) {
			
			//System.out.println("User: "+entry.getKey()+" Weight: "+entry.getValue());
			
		     if(entry.getValue() > maxValue) {
		         maxValue = entry.getValue();
		         maxKey = entry.getKey();
		     }
		}
		
		return maxKey;


	}

	private void updateWeights(File file, TreeMap<String, Integer> userWords) throws NumberFormatException, IOException {
		double w=0.0;
		String fileName = file.getName();
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		double bsize=0.0, tsize=0.0,hsize=0.0;
		while(br.ready()){
			
			for(int i=1;i<=100;i++){
				//System.out.println(i +" i ");
				String b =br.readLine();
				double val = Double.parseDouble(br.readLine());
				bsize+=val;
			}
			//br.readLine();
			for(int j=1;j<=100;j++){
				//System.out.println(j +" j");
				String b =br.readLine();
				double val = Double.parseDouble(br.readLine());
				tsize+=val;
			}
			//br.readLine();
			for(int z=1;z<=50;z++){
				//System.out.println(z +" z");
				String b =br.readLine();
				double val = Double.parseDouble(br.readLine());
				hsize+=val;
			}
			break;
			
		}
		br.close();
		fr.close();
		
		FileReader fr1 = new FileReader(file);
		BufferedReader br1 = new BufferedReader(fr1);
		while (br1.ready())
		{
			//br.readLine();
			for(int i=1;i<=100;i++){
				//System.out.println(i +" i ");
				String b =br1.readLine();
				double val = Double.parseDouble(br1.readLine());
				if(btable.contains(b)) w=w+(btable.get(b)*30*val/bsize);
			}
			//br.readLine();
			for(int j=1;j<=100;j++){
				//System.out.println(j +" j");
				String b =br1.readLine();
				double val = Double.parseDouble(br1.readLine());
				if(ttable.contains(b)) w=w+(ttable.get(b)*40*val/tsize);
			}
			//br.readLine();
			for(int z=1;z<=50;z++){
				//System.out.println(z +" z");
				String b =br1.readLine();
				double val = Double.parseDouble(br1.readLine());
				if(userWords.containsKey(b)) w=w+(userWords.get(b)*20*val/hsize);
			}
			//br.readLine();
			double pval = Double.parseDouble(br1.readLine());
			//br.readLine();
			double nval = Double.parseDouble(br1.readLine());
			w=w+pval/(pval+nval)*15*pc+nval/(pval+nval)*15*nc;
			//System.out.println("here");
			utable.put(fileName, w);
			break;
		}
		
		
		br1.close();
		fr1.close();

	}

	private void fetchAll(TreeMap<String, Integer> userWords, String[] aryLines) throws IOException {
		String bigram;
		btable = new Hashtable<String,Integer>();

		for(int i=0;i<aryLines.length-1;i++){

			bigram = aryLines[i] + " " + aryLines[i+1];
			//Object item = table.get(bigram);
			//If bigram has been seen, add 1 to value
			if(btable.containsKey(bigram)){
				Integer prevValue = btable.get(bigram);
				btable.put(bigram, prevValue + 1);
			}
			//else create new bigram entry in table
			else {
				btable.put(bigram, 1);
			}
		}


		ttable = new Hashtable<String,Integer>();

		for(int i=0;i<aryLines.length-2;i++){

			bigram = aryLines[i] + " " + aryLines[i+1]+" "+aryLines[i+2];
			//Object item = table.get(bigram);
			//If bigram has been seen, add 1 to value
			if(ttable.containsKey(bigram)){
				Integer prevValue = ttable.get(bigram);
				ttable.put(bigram, prevValue + 1);
			}
			//else create new bigram entry in table
			else {
				ttable.put(bigram, 1);
			}
		}

		PositiveNegativeAnalyzer analyze = new PositiveNegativeAnalyzer();
		pc= analyze.Analyzer("positive_words.txt", userWords);
		nc=analyze.Analyzer("negative_words.txt", userWords);

	}

}
