import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Iterator;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("test.txt")));
		Hashtable<String, Integer> table = new Hashtable<String, Integer>();
		String line;
		
		while((line = br.readLine()) != null){
			String words[] = line.split(" ");
			for(String word : words){
				
				Integer count = table.get(word);
				if(count == null){
					count = 0;
				}
				table.put(word, count + 1);
			}
		}
		br.close();
		
		Iterator<String> it = table.keySet().iterator();
		while(it.hasNext()){
			String word = it.next();
			System.out.println(word + " : " + table.get(word));
		}
	}

}
