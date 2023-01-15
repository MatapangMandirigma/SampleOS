import java.io.*;
import java.lang.Math;
import java.lang.Runtime;
import java.util.Scanner;

public class Project1 {
	
	public static void main(String[] args) {
		
		// These are the registers saved for the CPU to use
		// PC is program counter
		// SP is stack pointer
		// SysSP is system stack pointer
		// time is from the time for timer
		int PC = 0, SP = 1000, SysSP = 2000, IR = 0, AC = 0, X = 0, Y = 0, time = 0;

		// User mode = 0
		// Kernel mode = 1
		int mode = 0;
		
		// Interrupt enabled = 0;
		// Interrupt disabled = 1;
		int interrupt = 0;

		// Time interrupt = > 1;
		int timeInt = 0;

		try {
			Runtime rt = Runtime.getRuntime();
			
			// Save fileName
			String fileName = args[0];

			// Save timer
			int timer = Integer.parseInt(args[1]);

			// Process, input, and output setup
			Process memory = rt.exec("java Memory");
			
			// Pipe created for the processes
			InputStream is = memory.getInputStream();
			OutputStream os = memory.getOutputStream();
			
			// Makes printwriter to write to Memory
			// Prints filename to Memory
			PrintWriter pw = new PrintWriter(os);
			pw.printf("%s\n", fileName);
			pw.flush();

			// Scanner and string to read from memory
			Scanner scan = new Scanner(is);
			String line;

			// Loop that is used for the fetch and
			// execute instruction cycle
			while(true){
				// Checks if the timer has expired and
				// Needs to run the timer interrupt
				if(timeInt > 0 && interrupt == 0){
					
					// Transfer to system stack
					// Pushes SP and PC to that system stack
					SysSP--;
					pw.printf("w\n");
				    pw.printf("%d\n", SysSP);
				    pw.printf("%d\n", SP);
				    pw.flush();
					
					SysSP--;
					PC--;
				    pw.printf("w\n");
				    pw.printf("%d\n", SysSP);
				    pw.printf("%d\n", PC);
				    pw.flush();

					// Changes to the PC to 1000
					// and the SP to SysSP
					PC = 1000;
					SP = SysSP;

					// Switches to kernel mode, disables 
					// interrupts, and disables timer 
					mode = 1;
					timeInt = 1;
					interrupt = 1;
				}

				// Fetch of instruction from memory
				pw.printf("%d\n", PC);
				pw.flush();
			
				line = scan.next();
				IR = Integer.parseInt(line);

				// Checks for memory violations done by the CPU
				// while in user mode
				if(mode == 0 && PC > 999 && PC == 999 && IR != 50){
					if(PC > 999){
						System.out.println("Memory violation: accessing system address " + PC + " in user mode");
					}
					else{
						System.out.println("Memory violation: accessing system address 1000 in user mode");
					}

					// Sends end code to memory
					pw.printf("e\n");
					pw.flush();
					scan.close();
					return;
		        }

				switch(IR){
					//Load the value into the AC
					case 1:
						// Increment of PC for instruction
						PC++;

						// Fetch for data from memory
						pw.printf("%d\n", PC);
						pw.flush();

						line = scan.next();
						AC = Integer.parseInt(line);

						// Increment of PC
						PC++;
						break;
					//Load the value at the address into the AC
					case 2:
						// Increment of PC for instruction
						PC++;

						// Fetches address from memory
						pw.printf("%d\n", PC);
						pw.flush();
	
						line = scan.next();
						AC = Integer.parseInt(line);

						// Checks for memory violations done by the CPU
						// while in user mode
						if(mode == 0 && AC > 999){
							System.out.println("Memory violation: accessing system address " + AC + " in user mode");
							pw.printf("e\n");
							pw.flush();
							return;
						}
						
						// Fetches data from memory
						pw.printf("%d\n", AC);
						pw.flush();

						line = scan.next();
						AC = Integer.parseInt(line);

						// Increment of PC
						PC++;
						break;
					//Load the value from the address found in the given address into the AC
					//(for example, if LoadInd 500, and 500 contains 100, then load from 100).
					case 3:
						// Increment of PC for instruction
						PC++;

						// Fetches address from memory
						pw.printf("%d\n", PC);
						pw.flush();
	
						line = scan.next();
						AC = Integer.parseInt(line);
						
						// Checks for memory violations done by the CPU
						// while in user mode
						if(mode == 0 && AC > 999){
							System.out.println("Memory violation: accessing system address " + AC + " in user mode");
							pw.printf("e\n");
							pw.flush();
							return;
						}

						// Fetches address from memory
						pw.printf("%d\n", AC);
						pw.flush();
					
						line = scan.next();
						AC = Integer.parseInt(line);
						
						// Checks for memory violations done by the CPU
						// while in user mode
						if(mode == 0 && AC > 999){
							System.out.println("Memory violation: accessing system address " + AC + " in user mode");
							pw.printf("e\n");
							pw.flush();
							return;
						}

						// Fetches data from memory
						pw.printf("%d\n", AC);
						pw.flush();
	
						line = scan.next();
						AC = Integer.parseInt(line);

						// Increment of PC
						PC++;
						break;
					//Load the value at (address+X) into the AC
					//(for example, if LoadIdxX 500, and X contains 10, then load from 510).
					case 4:
						// Increment of PC for instruction
						PC++;

						// Fetches address from memory
						pw.printf("%d\n", PC);
						pw.flush();
	
						line = scan.next();
						AC = Integer.parseInt(line);
						
						// Checks for memory violations done by the CPU
						// while in user mode
						if(mode == 0 && (AC+X) > 999){
							System.out.println("Memory violation: accessing system address " + (AC+X) + " in user mode");
							pw.printf("e\n");
							pw.flush();
							return;
						}

						// Fetches data from memory
						pw.printf("%d\n", (AC+X));
						pw.flush();
								
						line = scan.next();
						AC = Integer.parseInt(line);

						// Increment of PC
						PC++;
						break;
					//Load the value at (address+Y) into the AC
					case 5:
						// Increment of PC for instruction
						PC++;

						// Fetches address from memory
						pw.printf("%d\n", PC);
						pw.flush();
	
						line = scan.next();
						AC = Integer.parseInt(line);
						
						// Checks for memory violations done by the CPU
						// while in user mode
						if(mode == 0 && (AC+Y) > 999){
							System.out.println("Memory violation: accessing system address " + (AC+Y) + " in user mode");
							pw.printf("e\n");
							pw.flush();
							return;
						}

						// Fetches data from memory
						pw.printf("%d\n", (AC+Y));
						pw.flush();
	
						line = scan.next();
						AC = Integer.parseInt(line);

						// Increment of PC
						PC++;
						break;
					//Load from (Sp+X) into the AC (if SP is 990, and X is 1, load from 991).
					case 6:
						// Checks for memory violations done by the CPU
						// while in user mode
						if(mode == 0 && (SP+X) > 999){
							System.out.println("Memory violation: accessing system address " + (SP+X) + " in user mode");
							pw.printf("e\n");
							pw.flush();
							return;
						}
						
						// Fetches data from memory
						pw.printf("%d\n", (SP+X));
						pw.flush();
						
						line = scan.next();
						AC = Integer.parseInt(line);

						// Increment of PC
						PC++;
						break;
					//Store the value in the AC into the address
					case 7:
						// Increment of PC for instruction
						PC++;

						// Fetches address from memory
						pw.printf("%d\n", PC);
						pw.flush();
	
						line = scan.next();
						int address = Integer.parseInt(line);
						
						// Checks for memory violations done by the CPU
						// while in user mode
						if(mode == 0 && address > 999){
							System.out.println("Memory violation: accessing system address " + address + " in user mode");
							pw.printf("e\n");
							pw.flush();
							return;
						}
						
						// Write data to address
						pw.printf("w\n");
						pw.printf("%d\n", address);
						pw.printf("%d\n", AC);
						pw.flush();

						// Increment of PC
						PC++;
						break;
					//Gets a random int from 1 to 100 into the AC
					case 8:
						int rand = (int)(Math.random() * 100) + 1;
						AC = rand;
						PC++;
						break;
					//If port=1, writes AC as an int to the screen
					//If port=2, writes AC as a char to the screen
					case 9:
						// Increment of PC for instruction
						PC++;

						// Fetches port from memory
						pw.printf("%d\n", PC);
						pw.flush();
	
						line = scan.next();
						int port = Integer.parseInt(line);

						// Checks which port is wanted
						if(port == 1){
							System.out.print(AC);
						}else if(port == 2){
							System.out.print((char)AC);
						}

						// Increment of PC
						PC++;
						break;
					//Add the value in X to the AC
					case 10:
						AC += X;

						// Increment of PC
						PC++;
						break;
					//Add the value in Y to the AC
					case 11:
						AC += Y;

						// Increment of PC
						PC++;
						break;
					//Subtract the value in X from the AC
					case 12:
						AC -= X;
						
						// Increment of PC
						PC++;
						break;
					//Subtract the value in Y from the AC
					case 13:
						AC -= Y;

						// Increment of PC
						PC++;
						break;
					//Copy the value in the AC to X
					case 14:
						X = AC;

						// Increment of PC
						PC++;
						break;
					//Copy the value in X to the AC
					case 15:
						AC = X;

						// Increment of PC
						PC++;
						break;
					//Copy the value in the AC to Y
					case 16:
						Y = AC;

						// Increment of PC
						PC++;
						break;
					//Copy the value in the AC to Y
					case 17:
						AC = Y;

						// Increment of PC
						PC++;
						break;
					//Copy the value in AC to the SP
					case 18:
						SP = AC;

						// Increment of PC
						PC++;
						break;
					//Copy the value in SP to the AC 
					case 19:
						AC = SP;

						// Increment of PC
						PC++;
						break;
					//Jump to the address
					case 20:
						// Increment of PC for instruction
						PC++;

						// Fetch of address from memory
						pw.printf("%d\n", PC);
						pw.flush();
	
						line = scan.next();
						PC = Integer.parseInt(line);

						break;
					//Jump to the address only if the value in the AC is zero
					case 21:
						// Increment of PC for instruction
						PC++;

						if(AC == 0){
							// Fetch of address from memory
							pw.printf("%d\n", PC);
							pw.flush();
	
							line = scan.next();
							PC = Integer.parseInt(line);
						}else{
							// Increment of PC
							PC++;
						}
						break;
					//Jump to the address only if the value in the AC is not zero
					case 22:
						// Increment of PC for instruction
						PC++;
	
						if(AC != 0){
							// Fetch of address from memory
							pw.printf("%d\n", PC);
							pw.flush();

							line = scan.next();
							PC = Integer.parseInt(line);
						}else{
							// Increment of PC
							PC++;
						}
						break;
					//Push return address onto stack, jump to the address
					case 23:
						// Increment of PC for instruction
						PC++;

						// Decrement of stack pointer
						SP--;

						// Write/Push of PC/Address to Stack
						pw.printf("w\n");
						pw.printf("%d\n", SP);
						pw.printf("%d\n", PC);

						// Fetch address from memory
						pw.printf("%d\n", PC);
						pw.flush();
						
						line = scan.next();
						PC = Integer.parseInt(line);

						break;
					//Pop return address from the stack, jump to the address
					case 24:
						// Fetch/Pop of address to Stack
						pw.printf("%d\n", SP);
						pw.flush();
						// Increment of stack pointer
						SP++;
	
						line = scan.next();
						PC = Integer.parseInt(line);

						// Increment of PC
						PC++;
						break;
					//Increment the value in X
					case 25:
						X++;

						// Increment of PC
						PC++;
						break;
					//Decrement the value in X
					case 26:
						X--;

						// Increment of PC
						PC++;
						break;
					//Push AC onto stack
					case 27:
						// Decrement of Stack Pointer
						SP--;
						// Write/Push of AC onto the stack
						pw.printf("w\n");
						pw.printf("%d\n", SP);
						pw.printf("%d\n", AC);
						pw.flush();

						// Increment of PC
						PC++;
						break;
					//Pop from stack into AC
					case 28:
						// Read/Pop of stack onto the AC
						pw.printf("%d\n", SP);
						pw.flush();
						// Increment of Stack Pointer
						SP++;

						line = scan.next();
						AC = Integer.parseInt(line);

						// Increment of PC
						PC++;
						break;
					//Perform system call
					case 29:
						// Checks if interrupts are enabled
						if(interrupt == 0){
							// Switches to kernel mode and disables
							// interrupts
							mode = 1;
							interrupt = 1;

							// Transfer to system stack
							// Pushes SP and PC to that system stack
							SysSP--;
							pw.printf("w\n");
							pw.printf("%d\n", SysSP);
							pw.printf("%d\n", SP);
							pw.flush();

							SysSP--;
							pw.printf("w\n");
							pw.printf("%d\n", SysSP);
							pw.printf("%d\n", PC);
							pw.flush();

							// Changes to the PC to 1500
							// and the SP to SysSP
							SP = SysSP;
							PC = 1500;
						}else{
							// If system call is skipped go
							// to the next instruction
							PC++;
						}
						break;
					//Return from system call
					case 30:
						// Switches mode to user and
						// enables interrupts
						mode = 0;
						interrupt = 0;

						// If the interrupt was a timer
						// interrupt it resets the timer
						// interrupt
						if(timeInt == 1){
							timeInt = 0;
						}

						// Transfers PC and SysSP back to
						// the original PC and SP
						pw.printf("%d\n", SysSP);
						SysSP++;
						pw.flush();
									
						line = scan.next();
						PC = Integer.parseInt(line);

						pw.printf("%d\n", SysSP);
						SysSP++;
						pw.flush();

						line = scan.next();
						SP = Integer.parseInt(line);

						// Increment of PC
						PC++;
						break;
					//End execution
					case 50:
						scan.close();
						// Sends end code to memory
						pw.printf("e\n");
						return;
					//Error code
					default:
						scan.close();
						System.out.println("Invalid or unwritten code");
						return;
				}
				
				// Increment of the timer
				time++;
				// If timer expires then change to two
				// so that the system knows and can
				// start counting from 0 again
				if(time >= timer){
					timeInt = 2;
					time = 0;
				}

			}

		}
		catch(Throwable t) {
			t.printStackTrace();
		}

	}

}
	

