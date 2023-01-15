import java.io.*;
import java.util.Scanner;
import java.lang.Runtime;

public class Memory {

	public static void main(String[] args) {

		//2000 bit memory
		int[] Memory = new int[2000];
		// Used to save the program from text file
		int PC = 0;

		try {
			// Used during the read of the file
			// into memory
			int instruction = 0;

			// All important for the read and write of data
			int data = 0;
			int address = 0;
			int option = 0;

			// Scanner from CPU
			Scanner sc = new Scanner(System.in);

			// Read in filename
			String fileName = sc.nextLine();

			// Make file reader			
			File input = new File(fileName);
			// Scanner of file
			Scanner scan = new Scanner(input);
			
			// while loop to run through file and 
			// save program into memory for the CPU
			while(scan.hasNext()) {
				String inputLine = scan.next();
				// Skips rest of line with a // in it
				if(inputLine.startsWith("//")) {
					inputLine = scan.nextLine();
					continue;
				// Changes address of PC so memory is
				// saved from that PC in memory
				}else if(inputLine.startsWith(".")) {
					int MAddress = Integer.parseInt(inputLine.substring(1));
					PC = MAddress;
				//Adds intsruction and data into memory
				}else {
					instruction = Integer.parseInt(inputLine);
					Memory[PC] = instruction;
					inputLine = scan.nextLine();
					PC++;
				}
			}

			// Waits for calls from the CPU, to read
			// or write data, as well as end execution
			while(sc.hasNext()){
				String line = sc.next();
				if(option > 0){
					// Reads address for data
					if(option == 1){
						address = Integer.parseInt(line); 
						option++;
					}
					// Reads data to from the 
					// CPU that needs to be written
					else{
						data = Integer.parseInt(line);
						Memory[address] = data;
						option = 0;
					}
				}
				else{
					// Ends execution
					if(line.equals("e")){
						// Closing of both scanners
						sc.close();
						scan.close();
						return;
					}
					// Switches to write
					else if(line.equals("w")){
						option = 1;
					// Returns the data and the 
					// address given
					}else{
						address = Integer.parseInt(line);
						System.out.println(Memory[address]);
					}
				}
			}

			//Closing of both scanners
			scan.close();
			sc.close();
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
	}
}
