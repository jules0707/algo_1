
public class LinkedQueueOfIntegers {

	private Node first, last;
	
	private class Node {
		Integer item;
		Node next;
	}
	
	public boolean isEmpty() {return first == null;}
	
	
	public void enqueue(Integer item ){
		Node oldLast = last;	
		last = new Node();
		last.item = item;
		last.next = null;
		if(isEmpty()) first = last;
		else oldLast.next = last;
	}

	public Integer dequeue() {
		Integer item = first.item;
		first = first.next;
		if(isEmpty()) last = null;
		return item;
	}
}
