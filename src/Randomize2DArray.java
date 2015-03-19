import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;


public class Randomize2DArray {

	public static void main(String[] args) {
		
		ArrayList <ArrayList<String>> Master2DXMLRequestArrayList = new ArrayList <ArrayList<String>>();
		
		ArrayList<String> One = new ArrayList<String>();
		One.add("0");One.add("1");One.add("2");One.add("3");One.add("4");
		One.add("5");One.add("6");One.add("7");One.add("8");One.add("9");
		
		ArrayList<String> Two = new ArrayList<String>();
		Two.add("0");Two.add("1");Two.add("2");Two.add("3");Two.add("4");
		Two.add("5");Two.add("6");Two.add("7");Two.add("8");Two.add("9");
		
		ArrayList<String> Three = new ArrayList<String>();
		Three.add("0");Three.add("1");Three.add("2");Three.add("3");Three.add("4");
		Three.add("5");Three.add("6");Three.add("7");Three.add("8");Three.add("9");
		
		Master2DXMLRequestArrayList.add(One);
		Master2DXMLRequestArrayList.add(Two);
		Master2DXMLRequestArrayList.add(Three);
		
		// Declare and get the LongRandomKey for randomizing the 2D Array List
		Long LongRandomKey = null;
		String LongRandomDatabaseKey = "14476493761145"; // DB Key
		LongRandomDatabaseKey="";
		if(LongRandomDatabaseKey != null && !LongRandomDatabaseKey.equals("")){
			//System.out.println("Make Key from the Database!");
			LongRandomKey = Long.parseLong(LongRandomDatabaseKey);
		}else{
			//System.out.println("Make Key from scratch!");
			LongRandomKey = System.nanoTime();
		}

		System.out.println(Master2DXMLRequestArrayList);
		//print2DArrayList(Master2DXMLRequestArrayList);
		randomize2DArrayList(Master2DXMLRequestArrayList, LongRandomKey);
		//print2DArrayList(Master2DXMLRequestArrayList);
		System.out.println(Master2DXMLRequestArrayList);
		
	}
	
	// Randomize all Nodes of a 2D ArrayList using a Long data type Key to keep the randomizing consistency
	public static void randomize2DArrayList(ArrayList <ArrayList<String>> Fixed2DArrayList, long LongKey){
		
		// Randomize all Nodes from the 2D Array List
		for(int i = 0 ; i < Fixed2DArrayList.size(); i++){
		Collections.shuffle(Fixed2DArrayList.get(i), new Random(LongKey));
		}
		
	}
	
	public static void print2DArrayList(ArrayList <ArrayList<String>> Master2DXMLRequestArrayList){
		
		Iterator<ArrayList<String>> CurrentReuest = Master2DXMLRequestArrayList.iterator();
		
		while (CurrentReuest.hasNext()) {
				ArrayList<String> CurrentReuestIndex = CurrentReuest.next();
				Iterator<String> CurrentNE = CurrentReuestIndex.iterator();
				
				// As we loop through the entire Master2DMappedNetworkElementArrayList, figure out any Invalid NEs and remove them
				while (CurrentNE.hasNext()) {
				String CurrentNEIndex = CurrentNE.next();
				System.out.print("" + CurrentNEIndex + " ");
				
			}
				System.out.println("");
		}
		
	}

}