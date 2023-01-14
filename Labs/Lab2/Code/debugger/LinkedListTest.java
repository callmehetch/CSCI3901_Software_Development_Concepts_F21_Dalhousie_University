package debugger;

public class LinkedListTest {

	public static void main(String[] args) {
		LinkedList theList = new LinkedList();
		
		theList.append( "hello" );
		theList.append( "world" );
		theList.append("!!");
		theList.print();
		if (theList.find("hello")) {
			System.out.println("Found hello");
		} else {
			System.out.println("Missing hello");
		}
		if (theList.find("!!")) {
			System.out.println("Found !!");
		} else {
			System.out.println("Missing !!");
		}

	}

}
