#README for CS 4348 Project 1

- Project1.java
This is the CPU of the system. This contains the registers and what to do for every instruction code.
The CPU also runs the memory and creates the pipe between them. It runs a fetch and execute 
instruction cycle throughout the program and uses the memory in concurrence with that. 
- Memory.java
This is the memory of the system. It has the basic functions of reading and writing with the CPU.
It also reads in the program from the text file given and saves it into its memory for the CPU
to use.
- sample5.txt
This sample will print out a ASCII art of a spider where about the first half will be printed out with the program and then using the system call it will print about the second half of the spider.
- How to run
Compile both Project1.java and Memory.java:
"javac Project1.java"
"javac Memory.java"
Then you can run:
"java Project1 (txt file) (timer amount)"
Where txt file is the program you would like to run
And where timer amount is how long you want the timer to be 
