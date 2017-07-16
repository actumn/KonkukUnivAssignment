
public class LinkedList {
	Node header = new Node();
	Node trailer = new Node();
	int size;
	
	//header trailer
	public LinkedList(){
		header.setNext(trailer);
		trailer.setPrev(header);
		size = 0;
	}
	
	public int getSize(){
		return size;
	}
	public boolean isEmpty(){
		return size == 0;
	}
	
	
	
	//header / nodeNew / ... ... / treailer
	public void addFrist(String value){
		Node nodeNew = new Node(header, header.getNext(), value);
		header.setNext(nodeNew);
		header.getNext().setPrev(nodeNew);
		size++;
	}
	
	//header / ... ...  / nodeNew / trailer
	public void addLast(String value){
		Node nodeNew = new Node(trailer.getPrev(), trailer, value);
		trailer.getPrev().setNext(nodeNew);
		trailer.setPrev(nodeNew);
		size++;
	}
	
	
	
	
	
	
	//header / ... ... / (n-1) / nodeNew / temp / ... / trailer
	public boolean addBefore(int n, String value){
		Node temp = header.getNext();
		int index = 0;
		while(temp != trailer){
			if(index == n){
				Node nodeNew = new Node(temp.getPrev(), temp, value);
				temp.getPrev().setNext(nodeNew);
				temp.setPrev(nodeNew);
				size++;
				return true;
			}
			temp = temp.getNext();
			index++;
		}
		
		return false;
	}
	
	//Overload
	//Search n , and add value Before n
	public boolean addBefore(String n, String value){
		Node temp = header.getNext();
		while(temp != trailer){
			if(temp.getString() == n){
				Node nodeNew = new Node(temp.getPrev(), temp, value);
				temp.getPrev().setNext(nodeNew);
				temp.setPrev(nodeNew);
				size++;
				return true;
			}
			temp = temp.getNext();
		}
		
		return false;
	}
	
	
	
	
	
	//header / ... ... / temp / nodeNew / (n+1) / ... / trailer
	public boolean addAfter(int n, String value){
		Node temp = header.getNext();
		int index = 0;
		while(temp != trailer){
			if(index == n){
				Node nodeNew = new Node(temp, temp.getNext(), value);
				temp.setNext(nodeNew);
				temp.getNext().setPrev(nodeNew);
				size++;
				return true;
			}
			temp = temp.getNext();
			index++;
		}
		
		return false;
	}
	
	//Overload
	//Search n, and add value after n
	public boolean addAfter(String n, String value){
		Node temp = header.getNext();
		while(temp != trailer){
			if(temp.getString() == n){
				Node nodeNew = new Node(temp, temp.getNext(), value);
				temp.setNext(nodeNew);
				temp.getNext().setPrev(nodeNew);
				size++;
				return true;
			}
			temp = temp.getNext();
		}
		
		return false;
	}
	
	
	
	//header / ... ... / (n-1) / temp->setValue(value) / (n+1) / ... ... / trailer
	public boolean set(int n, String value){
		Node temp = header.getNext();
		int index = 0;
		while(temp != trailer){
			if(index == n){
				temp.setValue(value);
				return true;
			}
			index++;
			temp = temp.getNext();
		}
		
		return false;
	}
	
	//Overload
	//Search n, and setValue(Value)
	public boolean set(String n, String value){
		Node temp = header.getNext();
		while(temp != trailer){
			if(temp.getString() == n){
				temp.setValue(value);
				return true;
			}
			temp = temp.getNext();
		}
		return false;
	}
	
	
	
	
	
	
	//header / ... / temp.Prev (temp) temp.Next / ... / trailer
	public String remove(Node p){
		Node temp = header.getNext();
		while(temp != trailer){
			if(temp.equals(p)){
				temp.getPrev().setNext(temp.getNext());
				temp.getNext().setPrev(temp.getPrev());
				temp = null;
				size--;
				return p.getString();
			}
			temp = temp.getNext();
		}
		return null;
	}
	
	//Overload
	//Serach n, and remove
	public String remove(String value){
		Node temp = header.getNext();
		while(temp != trailer){
			if(temp.getString() == value){
				temp.getPrev().setNext(temp.getNext());
				temp.getNext().setPrev(temp.getPrev());
				temp = null;
				size--;
				return value;
			}
			temp = temp.getNext();
		}
		return null;
	}
	
	
	
	
	
	
	// ¼øÂ÷ print
	public void printAllNode(){
		Node temp = header.getNext();
		while(temp != trailer){
			System.out.print(temp.getString());
			temp = temp.getNext();
		}
		System.out.println();
	}
	
	// reverse print
	public void printRev(){
		Node temp = trailer.getPrev();
		while(temp != header){
			System.out.print(temp.getString());
			temp = temp.getPrev();
		}
		System.out.println();
	}
}
