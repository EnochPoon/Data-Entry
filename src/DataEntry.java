
import java.util.*;
import java.io.*;
public class DataEntry {

	static HashMap<String, Integer> data = new HashMap<String, Integer>();

	public static void main(String[]args)throws Exception{
		BufferedReader file = new BufferedReader(new FileReader("data.txt"));
		Scanner scan = new Scanner(System.in);
		//FileWriter w = new FileWriter("C:\\Users\\ndsli_000\\Documents/bye.txt");
		try{
			while(true){
				String[]lineData = file.readLine().split(" ");
				data.put(lineData[0], Integer.parseInt(lineData[1]));
			}
		}catch(NullPointerException e){
			//okay get out
		}catch(Exception e){
			System.out.println("ERROR: DATA FILE CORRUPTED.");
			e.printStackTrace();
			return;
		}

		String getInput = scan.nextLine();
		while(!getInput.equals("quit")){
			String[]inputArray = getInput.split(" ");//splits input into array of strings. {command, data, newvalue}
			try{
				if(inputArray[0].equals("help")){
					help();
				}else if(inputArray[0].equals("get")){
					query(inputArray[1]);
				}else if(inputArray[0].equals("add")){
					update(inputArray[1], data.get(inputArray[1]) + Integer.parseInt(inputArray[2]));
				}else if(inputArray[0].equals("removeAmount")){
					update(inputArray[1], data.get(inputArray[1]) - Integer.parseInt(inputArray[2]));
				}else if(inputArray[0].equals("update")){
					update(inputArray[1], Integer.parseInt(inputArray[2]));
				}else if(inputArray[0].equals("removeItem")){
					removeItem(inputArray[1]);
				}else if(inputArray[0].equals("listItems")){
					listItems();
				}else{
					System.out.println("Not a valid input. Type \"help\" for list of commands.");
				}
			}catch(NullPointerException e){
				System.out.println("No data found for " + inputArray[1]);
			}catch(Exception e){
				System.out.println("Invalid arguments. Type \"help\" for list of commands.");
			}
			getInput = scan.nextLine();
		}

		//write updates to file
		PrintWriter w = new PrintWriter("data.txt");
		Set<String>items = data.keySet();
		for(String item : items){
			w.format("%s %d%n", item, data.get(item));
		}
		w.close();
	}

	private static void update(String item, int amount){
		if(amount < 0){
			System.out.println("Error: negative final amount of " + item);
			return;
		}
		if(data.containsKey(item)){
			data.replace(item, amount);
		}else{
			System.out.println("Added new item.");
			data.put(item, amount);
		}
		System.out.printf("%s: %d\n", item, amount);
	}

	private static void query(String item){
		if(data.containsKey(item)){
			System.out.println("Amount: "+ data.get(item));
		}else{
			System.out.println("No data found for " + item);
		}
	}

	private static void removeItem(String item){
		if(data.containsKey(item)){
			data.remove(item);
			System.out.println(item + " removed.");
		}else{
			System.out.println("No data found for " + item);
		}
	}
	
	private static void listItems(){
		Set<String>items = data.keySet();
		for(String item : items){
			System.out.printf("%s: %d%n", item, data.get(item));
		}
	}
	public static void help(){
		String[]commands = {"help : Lists the different commands.",
				"update (item amount) : changes the amount of item",
				"get (item) : gets the amount of item",
				"add (item amount) : adds the amount of item",
				"removeAmount (item amount) : removes the amount from item.",
				"removeItem (item) : totally removes item",
				"listItems : lists all items and their corresponding amount."
		};
		for(String com : commands){
			System.out.println(com);
		}
	}
}
