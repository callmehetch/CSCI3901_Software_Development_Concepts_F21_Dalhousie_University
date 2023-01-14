import java.util.Map;

//Implementation of Heap
public class implementHeap {
	public Node[] buildHTree = new Node[1000];
	private int num = -1;
	private int point;
	private int flag;
	Node n;
	Node p1;
	Node thisNode;
	Node k;

	//Methon to coinstruct a minHeap
	public void construct(Map<Character, Integer> M1) {
		//Looping through each entry of map and insert()
		for (Map.Entry<Character, Integer> p : M1.entrySet()) {
			n = new Node(Character.toString(p.getKey()), p.getValue());
			insert(n);
		}
	}
	//Method to insert nodes
	public void insert(Node n) {
		buildHTree[++num] = n;
		setPoint(getNum());
		//Loop through as long as point is not zero and not false of toCompare()
		while(point != 0 && toCompare(getH("parent", getPoint()), buildHTree[getPoint()])) {
			p1 = getH("parent", getPoint());
			//Building a heap tree
			thisNode = buildHTree[getPoint()];
			buildHTree[getPoint() /2 - (getPoint() +1)%2] = thisNode;
			buildHTree[getPoint()] = p1;
			setPoint(getPoint() /2 - (getPoint() +1)%2);
		}
	}
	//Method to pop elements
	public Node pop() {
		Node root = buildHTree[0];
		//If heap has elements - pop
		if(getNum() > 0 ) {
			k = buildHTree[getNum()];
			//Build everytime
			buildHTree[0] = k;
			buildHTree[getNum()] = null;
			num--;
			sortAgain(0);
		}
		//Else null
		else {
			buildHTree[0] = null;
			num--;
		}

		return root;
	}
	//Method to getHeap
	private Node getH(String s, int i) {
		//Left
		if(s == "left") {
			return buildHTree[2*i+1];
		}
		//Right
		else if(s == "right") {
			return buildHTree[2*i+2];
		}
		return buildHTree[i/2 - (i+1)%2];
	}
	//Method to sort everytime an element is popped
	private void sortAgain(int i) {
		while(i <= getNum()) {
			//left Node
			Node leftNode = getH("left", i);
			//right Node initialization
			Node rightNode = getH("right", i);
			//setting Flag
			setFlag(i);
			//Condition not to be null and compare
			if(leftNode !=null && toCompare(buildHTree[getFlag()], leftNode)) {
				//setting Flag
				setFlag(2*i+1);
			}
			//Condition not to be null and compare
			if(rightNode != null && toCompare(buildHTree[getFlag()], rightNode)) {
				//setting Flag
				setFlag(2*i+2);
			}
			//If index isn't flag
			if(i != getFlag()) {
				//Keep a parent node
				Node p1 = getH("parent", getFlag());
				//build heap with flag
				Node thisNode2 = buildHTree[getFlag()];
				//Swap
				buildHTree[getFlag() /2 - (getFlag() +1)%2] = thisNode2;
				//Build and Swap
				buildHTree[getFlag()] = p1;
				i = getFlag();

			} else {
				break;
			}
		}
	}
	//Method to compare
	private boolean toCompare(Node p, Node c) {
		//If frequency of parent is greater than that of child
		if(p.getFreq() > c.getFreq()) {
			//return true
			return true;
		}//else on comparing
		else if(p.getCh().compareTo(c.getCh()) > 0 && p.getCh() != "root" && c.getCh() !="root" && p.getFreq() == c.getFreq()) {
			//return true
			return true;
		}
		//return false
		return false;
	}


	//Getter and Setter
	public void setNum(int num) {
		this.num = num;
	}
	public int getNum() {
		return this.num;
	}
	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
