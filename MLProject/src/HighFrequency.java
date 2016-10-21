import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;


public class HighFrequency {
	public void findFreqWords( BufferedWriter bw, TreeMap<String,Integer> allWords) {

		Set<Entry<String, Integer>> set = allWords.entrySet();
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
				{
			public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
			{
				return (o2.getValue()).compareTo( o1.getValue() );
			}
				} );

		String temp="";
		int store,count=0;
		int totalc=0;
		for(Map.Entry<String, Integer> entry:list){
			count++;
			if(count>0&&count<51)
				totalc=totalc+entry.getValue();
			else if(count>101) break;
		}
		count=0;
		for(Map.Entry<String, Integer> entry:list){
			temp=entry.getKey().toString();
			store=entry.getValue();
			try {
				if(count<50){
					bw.write(temp);
					bw.newLine();
					bw.write(((double)store)+"");
					bw.newLine();
					count++;
				}else return;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
