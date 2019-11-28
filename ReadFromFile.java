import java.util.*;
import java.io.*;
import java.util.List;
/**
 * 
 *
 * @author Jillian Baggett
 * @version (a version number or a date)
 */
public class ReadFromFile
{
    //Driver class to read in the graph

    public static void main(String[] args) throws IOException{

        String path = "";
        path ="/Users/jillianbaggett/Documents/USA-road-d.NY.gr";//Substitute this path with any file in computer (gr or txt)
        //The following can be used to test the function below 
        List<List<List<Integer>>> storeEdges = readInGraph(path);
        MinHeap test = new MinHeap();
        int[] beenChecked = new int[storeEdges.size() + 1];
        int[] prevDist = new int[storeEdges.size() + 1];
        int[] fromWhere = new int[storeEdges.size() + 1];
        ArrayList<Integer> pathToFinal = new ArrayList<Integer>();
        int sourceNode = 1; //whatever given start node is
        int finishNode = 1276;
        System.out.println("This is a print test");
        //All distances are set to math.max except for starting node, which is set to zero
        for (int j = 1; j < storeEdges.size(); j++)
        {
            beenChecked[j] = 0;
            if (j == sourceNode)
            {
                prevDist[j] = 0;
                test.insert(0, j);
            }
            else
            {
                prevDist[j] = Integer.MAX_VALUE;
                test.insert(Integer.MAX_VALUE, j); //inserting a node with distance infinity and identity number
            }
        }

        //access the node list for the starting node
        int nodeToCheck;
        while (test.peek() != null && beenChecked[test.peek().nodeID] == 0)
        {
            nodeToCheck = test.peek().nodeID;
            for (int k = 0; k < storeEdges.get(nodeToCheck).size(); k++) //iterate through every 3 element list stored at this node
            { 
                int nodeID = storeEdges.get(nodeToCheck).get(k).get(0);
                if (beenChecked[nodeID] == 0)
                {  
                    int newDist = storeEdges.get(nodeToCheck).get(k).get(1);
                    if (test.getDistance(nodeID) == Integer.MAX_VALUE)
                    { test.decreaseKey(test.returnIndex(nodeID), newDist + prevDist[nodeToCheck]);
                        prevDist[nodeID] = newDist + prevDist[nodeToCheck];
                        fromWhere[nodeID] = nodeToCheck;
                    }
                    else if (prevDist[nodeID] > (newDist + prevDist[nodeToCheck]))
                    { test.decreaseKey(test.returnIndex(nodeID), (newDist + test.getDistance(nodeToCheck)));
                        prevDist[nodeID] = newDist + prevDist[nodeToCheck];
                        fromWhere[nodeID] = nodeToCheck;
                    }
                    else //do nothing, distance should not be updated
                    { //mark it as completed?
                    }
                    //System.out.println("Node Checked: " + nodeID);
                    //System.out.println("Distance to node: " + nodeID + " is " + test.getDistance(nodeID));
                }

            } 
            beenChecked[nodeToCheck] = 1;
            test.poll();
        }
        
        int currNode = finishNode;
        //to find the path out backwards
        do
        { pathToFinal.add(currNode);
          currNode = fromWhere[currNode];
        } while (currNode != sourceNode);
        System.out.println(sourceNode);
        for (int j = pathToFinal.size() -1; j > -1; j--)
        { System.out.println(pathToFinal.get(j));
        }
       
         System.out.println("Final shortest distance to " + 1276 + " : " + prevDist[1276]);
       
        
        //while (nodeToCheck != finishNode);

        
        // //Used as tester to print everything out   
        // for (int i = 0; i < storeEdges.size(); i++)
        // { System.out.print(i + ": { ");
        // //System.out.println("Size of i:  " + storeEdges.get(i).size());
        // for (int j = 0; j < storeEdges.get(i).size(); j++)
        // { 
        // //System.out.println("Size of j: " + storeEdges.get(i).get(j));
        // //test.insert(Math.MAX, (storeEdges.get(i).get(j).get(1))); 
        // System.out.print(" edge: " + storeEdges.get(i).get(j).get(0) + " distance: " + storeEdges.get(i).get(j).get(1));
        // }
        // System.out.println("\n");
        // }

    }
    //Method to read in the file and arrange its contents into adjacency list
    public static List<List<List<Integer>>> readInGraph(String filePath)
    { 
        List<List<List<Integer>>> storeEdges = new ArrayList<List<List<Integer>>>();
        List<List<Integer>> storeDistances = new ArrayList<List<Integer>>();
        BufferedReader buffreader;
        int verticesNum;
        int edgesNum;
        int firstNode;
        int secondNode;
        int space1;
        int space2;
        int space3;
        int distance;
        try {
            buffreader = new BufferedReader(new FileReader(filePath));
            String line = buffreader.readLine();
            while (line != null)
            { if (line.charAt(0) == 'p')
                { space1 = line.lastIndexOf(' ');
                    verticesNum = Integer.parseInt(line.substring(5, space1)); 
                    for (int i = 0; i < verticesNum + 1; i++)
                    { storeEdges.add(i, new ArrayList<List<Integer>>());
                    }
                    for (int i = 0; i < verticesNum + 1; i++)
                    { storeDistances.add(i, new ArrayList<Integer>());
                    }
                }
                else if (line.charAt(0) == 'a')
                {  String temp = line.substring(2);
                    space2 = 2 + temp.indexOf(' '); //find the space separating first and second node
                    space3 = line.lastIndexOf(' ');
                    //check to make sure nothing is incorrect in gr file
                    if (space2 != -1 && space3 != -1)
                    {
                        firstNode = Integer.parseInt(line.substring(2, space2));
                        secondNode = Integer.parseInt(line.substring(space2 + 1, space3));
                        distance = Integer.parseInt(line.substring(space3 + 1));
                        // Integer[] toAdd = {secondNode, distance};
                        ArrayList<Integer> toAdd = new ArrayList<>();
                        toAdd.add(secondNode);
                        toAdd.add(distance);
                        toAdd.add(-1); //marking the node as unseen in place of boolean
                        storeEdges.get(firstNode).add(toAdd);

                        //do the same for the second node
                        ArrayList<Integer> toAdd2 = new ArrayList<>();
                        toAdd2.add(firstNode);
                        toAdd2.add(distance);
                        toAdd2.add(-1); //marking node as unseen in place of boolean
                        storeEdges.get(secondNode).add(toAdd2);
                        //storeDistances.get(firstNode).add(distance);
                    }

                
                } 
                else
                {}// do nothing if comment
                line = buffreader.readLine();
            }
            buffreader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return storeEdges; //can change this to return whichever file is needed
    }

}



