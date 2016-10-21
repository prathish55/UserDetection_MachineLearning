import java.io.BufferedWriter;
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
import java.util.TreeSet;
import java.util.Map.Entry;


public class Trigrams {

	public void doTriGrams(String[] aryLines, BufferedWriter bw) {
		String bigram;
		Hashtable<String,Integer> table = new Hashtable<String,Integer>();

		for(int i=0;i<aryLines.length-2;i++){

			bigram = aryLines[i] + " " + aryLines[i+1]+" "+aryLines[i+2];
			//Object item = table.get(bigram);
			//If bigram has been seen, add 1 to value
			if(table.containsKey(bigram)){
				Integer prevValue = table.get(bigram);
				table.put(bigram, prevValue + 1);
			}
			//else create new bigram entry in table
			else {
				table.put(bigram, 1);
			}
		}


		List<String> list = Arrays.asList(aryLines);
		Set<String> uniqueWords = new TreeSet<String>(list);
		DecimalFormat df = new DecimalFormat("#.##");
		/*for (String word : uniqueWords) {

	        bw.write((word + ":\t\t\t" + df.format((float)(Collections.frequency(list, word))/file.numberOfLines)));
	        bw.newLine();
	    }*/

		ArrayList<String> keys = new ArrayList<String>(table.keySet());
		//Sort the list of bigrams
		//Collections.sort(keys);
		int totalSum = 0;
		//Go through list of keys



		for(String curBigram : keys){
			totalSum  ++; //Update total count
			//Print bigram and count value

			Set<Entry<String, Integer>> set = table.entrySet();
			List<Entry<String, Integer>> list1 = new ArrayList<Entry<String, Integer>>(set);
			Collections.sort( list1, new Comparator<Map.Entry<String, Integer>>()
					{
				public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
				{
					return (o2.getValue()).compareTo( o1.getValue() );
				}
					} );

			String temp="";
			//int tempval=0;
			int store,count=0;
			int totalc=0;
			for(Map.Entry<String, Integer> entry:list1){
				count++;
				if(count>0&&count<101)
					totalc=totalc+entry.getValue();
				else if(count>101) break;
			}
			count=0;
			for(Map.Entry<String, Integer> entry:list1){
				temp=entry.getKey().toString();
				store=entry.getValue();
				try {
					count++;
					if(count>0&&count<101){
						bw.write(temp);
						bw.newLine();
						bw.write(((double)store)+"");
						bw.newLine();

					}
					else if(count>101) return;

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/*String[] splited = curBigram.split(" ");
			//System.out.println(splited[0]);
			if(curBigram.isEmpty()||splited[0]==null||splited[0].isEmpty())
				continue;
			else {
			if(!splited[0].trim().equals("")){
				Integer x=  table.get(curBigram);
				float y= (float)(Collections.frequency(list, splited[0]));
				//System.out.println(y);
				float z= (float)(x) ;
				try {
					bw.write(curBigram + "\t " + df.format(z));
					bw.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				}*/
		}
	}
}
