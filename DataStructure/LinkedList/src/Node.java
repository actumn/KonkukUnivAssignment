
public class Node {
	private String value;
	private Node prev;
	private Node next;
	
	public Node(){
		prev = null;
		next = null;
		value = "¾øÀ½";
	}
	
	public Node(Node prev, Node next, String value){
		this.prev = prev;
		this.next = next;
		this.value = value;
	}
	
	public void setValue(String str){
		value = str;
	}
	
	public void setPrev(Node p){
		prev = p;
	}
	public void setNext(Node n){
		next = n;
	}
	public Node getPrev(){
		return prev;
	}
	public Node getNext(){
		return next;
	}
	public String getString(){
		return value;
	}
	
	public boolean equal(Node p){
		return value == p.value;
	}
}
