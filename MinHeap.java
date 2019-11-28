import java.util.*;
import java.util.ArrayList;
/**
 * Jillian Baggett
 * MinHeap Class 
 */
public class MinHeap
{
    protected int size;
    protected int largestSize; 
    protected ArrayList<Node> heap;
    // public static void main(String[] args)
    // {
        // MinHeap example = new MinHeap();
        // //System.out.println("Polled element: " + example.poll().element.toString());
        // for (int i = 0; i < example.size; i++)
        // {
         // System.out.println("\n" + example.heap.get(i).distance);
        // }
        // //System.out.println("Second is " + example.heap.get(1).element.toString());
    // }
    /**
     * Constructor for objects of class MinHeap
     */
    //For the purpose of keeping the heap as an array, largest size must be specificed
    //by the programmer 
    public MinHeap()
    {
        //this.root = null;
        this.heap = new ArrayList<Node>();
       
        
    }
    
    //Takes in a node's index and uses relations from homework to find the parent index
    public int getParent(int currIndex)
    {
        if (currIndex == 0)
            { return 0;
            } 
        else
        {
            return (currIndex - 1)/2; 
        }
    }
    //Takes in the two indeces and switched the nodes,  
    public void switchPosition(int currIndex, int newPosIndex)
    {
        //Save current nodes information into a temporary node
        Node tempNode = new Node(heap.get(currIndex).distance, heap.get(currIndex).nodeID);
        //Switch the element at current index with that of the parent
        heap.get(currIndex).distance = heap.get(newPosIndex).distance;
        heap.get(currIndex).nodeID = heap.get(newPosIndex).nodeID;
        //insert the element into the new position from the temp element
        heap.get(newPosIndex).distance = tempNode.distance;
        heap.get(newPosIndex).nodeID = tempNode.nodeID;
    }
    
    public void insert(int distance, int nodeID)
    { //Increase the size of the heap
      this.size++;
      //Create new node with the given element 
      Node toInsert = new Node(distance, nodeID);
      //Insert that element to the tail end of the filled elements
      heap.add(toInsert);
      int currentIndex = size-1;
      //Check to make sure the heap is still a valid max heap 
      if (this.size > 1)
      {
             while (heap.get(currentIndex).distance < heap.get(getParent(currentIndex)).distance)
      {     switchPosition(currentIndex, getParent(currentIndex));
            currentIndex = getParent(currentIndex);
        }
        }
 
      
    }
     //helper class to locate a node with given NodeID
     public int getDistance(int searchNodeID)
     {  
         int currDist = -1;
         for (int i = 0; i < heap.size(); i++)
         { if (heap.get(i).nodeID == searchNodeID)
             { currDist = heap.get(i).distance;
                }
            }
          return currDist;
        }
        //helper class to locate a nodes index so its key can be decreased
      public int returnIndex(int searchNodeID)
     {  
         int index = -1;
         for (int i = 0; i < heap.size(); i++)
         { if (heap.get(i).nodeID == searchNodeID)
             { index = i;
                }
            }
         return index;
        } 
    
    public void heapify(ArrayList<Node> array, int size, int rootInd)
    { Node currRoot = heap.get(rootInd);
      int leftInd = rootInd * 2 + 1;
      int rightInd = rootInd * 2 + 2;
      int smallestInd = rootInd;
      
      // System.out.println("curr root: " + rootInd);
      // System.out.println("right ind: " + rightInd);
      // System.out.println("left ind: " + leftInd);
      // System.out.println("\n");
      
      if (leftInd < size && (array.get(leftInd).distance > array.get(rootInd).distance))
      {     smallestInd = leftInd;
        }
      
       if (rightInd < size && (array.get(rightInd).distance > array.get(smallestInd).distance))
      {     smallestInd = rightInd;
        }
        
       if (smallestInd != rootInd)
       {  switchPosition(rootInd,smallestInd);
          heapify(array, size, smallestInd);
        }
    }
    
    
    public void heapsort()
    {
        int size = heap.size();
        //Creating the heap - must call heapify on all nodes not leaves, aka less than n/2 - 1
        for (int i = size/2-1; i >= 0; i--){
         heapify(heap, size, i);
        }
        
        //Extracting the largest element and placing it at end of array
        for (int i = size-1; i>=1; i--)
        {
           Node temp = new Node(heap.get(0).distance, heap.get(0).nodeID);
           heap.get(0).distance = heap.get(i).distance;
           heap.get(0).nodeID = heap.get(i).nodeID;
           heap.get(i).distance = temp.distance;
           heap.get(i).nodeID = temp.nodeID;
            
           heapify(heap, i, 0);
        }
    }
        public Node peek() //aka remove minimum
    {
        if (heap.size() < 1)
        { return null;
        }
        else 
        {
        Node smallestElement = heap.get(0);
        return smallestElement;
    }
    }
    public Node poll() //aka remove minimum
    {
        int size = heap.size();
        Node smallestElement = heap.get(0);
        heap.remove(0);
        this.size = this.size - 1;
        heapsort();
        return smallestElement;
    }
    public void decreaseKey(int index, int newDist)
    {
        this.heap.get(index).distance = newDist;
        while (index != 0 && (this.heap.get((index-1)/2).distance > (this.heap.get(index).distance)))
        {  int tempDist = this.heap.get(index).distance;
           int tempNodeID = this.heap.get(index).nodeID;
           this.heap.get(index).distance = this.heap.get((index - 1)/2).distance;
           this.heap.get(index).nodeID = this.heap.get((index - 1)/2).nodeID; 
           index = (index -1)/2;
           this.heap.get(index).distance = tempDist;
           this.heap.get(index).nodeID = tempNodeID;
        }
    }
    public void completeNode(Node toUpdate)
    { toUpdate.checked = true;
    }
    public boolean getChecked(int nodeID)
    { return (this.heap.get(returnIndex(nodeID)).checked);
    }
        protected class Node {
        int distance;
        int nodeID;
        boolean checked;
        /**
         * Creates nodes in accordance to the ReadFromFile data structure, so each second node location is stored with its 
         * distance to that location 
         */
        public Node(int distance, int nodeID) {
            this.distance = distance; //this represents the distance 
            this.nodeID = nodeID;
            this.checked = false;
        }
    }
}
