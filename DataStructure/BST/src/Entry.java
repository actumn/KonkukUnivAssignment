
public class Entry {
	private int key;
	private Object obj;
	
	public Entry(int key, Object obj){
		this.key = key;
		this.obj = obj;
	}
	
	public int getKey(){
		return this.key;
	}
	public Object getObj(){
		return this.obj;
	}
	public String toString(){
		return "{" + key + "," + obj.toString() + "}";
	}
	public void printDataAndKey(){
		System.out.println("{" + key + ", " + obj.toString() + "}");
	}
}
