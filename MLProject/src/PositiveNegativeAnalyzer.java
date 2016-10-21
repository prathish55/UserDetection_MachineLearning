import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class PositiveNegativeAnalyzer {

	
	public int Analyzer(String filepath, TreeMap<String,Integer> hm) throws IOException
	{
		int numberOfLines;
		FileReader fr = new FileReader(filepath);
		BufferedReader textReader = new BufferedReader(fr);

		numberOfLines = readLines(filepath);
		String[ ] textData = new String[numberOfLines];

		int i,j,count=0;
		for (i=0; i < numberOfLines; i++)
		{
			textData[ i ] = textReader.readLine().trim();
			//System.out.println(textData[i]);
			if(hm.containsKey(textData[i]))
			{
				count=count+hm.get(textData[i]);
			}

		}
		textReader.close( );
		//bw.write(count+"");
		return count;
	}

	public int readLines(String filepath) throws IOException
	{
		FileReader file_to_read = new FileReader(filepath);
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