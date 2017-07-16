
public class Dictionary {
	private Entry[] entries = new Entry[20];
	private int index;
	public Dictionary(){
		for(int i = 0; i < entries.length; i++){
			entries[i] = null;
		}
		index = 0;
	}
	
	public Entry[] findAll(int key){
		Entry[] tempL = new Entry[20];
		int Lindex = -1;
		
		int Lsize = 0;
		for(int i = 0; i < index; i++){
			if(entries[i].getKey() == key){
				tempL[++Lindex] = entries[i];
				Lsize++;
			}
		}
		Entry[] L = new Entry[Lsize];
		for(int i = 0; i < Lsize; i++){
			L[i] = tempL[i];
		}
		
		tempL = null;
		
		return L;
	}
	public void insert(int key, Object value){
		Entry newEntry = new Entry(key, value);
		
		if(index == entries.length){
			System.out.println("Full Array");
		}
		else{
			entries[index] = newEntry;
			index++;
		}
	}
	public Entry remove(Entry e){
		Entry res = e;
		int removeIndex = 0;
		for(int i = 0; i < index; i++){
			if(entries[i] == e){
				removeIndex = i;
				break;
			}
		}
		if(res != null){
			for(int i = removeIndex; i < index; i++){
				entries[i] = entries[i+1];
			}
			index--;
		}
		
		return res;
	}
	public void printAll(){
		for(int i = 0; i < index; i++){
			entries[i].printDataAndKey();
		}
	}
}
