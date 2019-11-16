import java.util.*;
import java.io.*;
import java.util.List;
/**
 * Class designed to take a file from the command line and read it in
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
        List<List<Integer>> storeEdges = readInGraph(path);
        for (int i = 0; i < storeEdges.size(); i++)
           { System.out.print(i + ": { ");
               for (int j = 0; j < storeEdges.get(i).size(); j++)
            { System.out.print(" " + storeEdges.get(i).get(j) + " ");
            }
            System.out.println("\n");
        }
            
    }
    //Method to read in the file and arrange its contents into adjacency list
    public static List<List<Integer>> readInGraph(String filePath)
    { 
        List<List<Integer>> storeEdges = new ArrayList<List<Integer>>();
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
                  { storeEdges.add(i, new ArrayList<Integer>());
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
    
                        storeEdges.get(firstNode).add(secondNode);
                        storeDistances.get(firstNode).add(distance);
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
        return storeDistances; //can change this to return whichever file is needed
    }
 
}
    




