package GroupOperations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * This does permutation group operations
 * It represents a permutation in raw form by the bottom row of the matrix
 *           1    2    3    ... n
 *           a[1] a[2] a[3] ...a[n]
 *          
 * It is an array of integers with the 0 index being unused 
 *    in order to make the programming easier 
 */

public class PermGroupMain {
	
	/*
	 * ------------------------------------------------------------------
	 * MAIN CLASS AND MAIN VARIABLES
	 * ------------------------------------------------------------------
	 */
	
	public static Scanner sc = new Scanner(System.in);
	public static PermGroupInterface Interface;
	
	/*
	 * Put somewhere:
	 * System.out.println(Entering invalid permutation returns you to the options screen. 
	 */
	public static void main(String[] args) throws FileNotFoundException{
		System.out.println("Welcome to Permutation Group Illustrator!");
		System.out.println();
		System.out.println("By Zachary DeStefano");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Unless otherwise specified here, the program defaults to n=4 and ");
		System.out.println("displays results in cycle decomposition format");
		System.out.println();
		System.out.println();
		System.out.println("We are working in S_n.");
		Interface = new PermGroupInterface(4,'C');
		ChangeNvalue();
		System.out.println();
		ChangeOutputOption();
		
		while(true){
			int opt = OptionAsk();
			if(opt == 2){
				break;
			}
			else if(opt == 0){
				OptionsScreen();
			}
			else if(opt == 1){
				ShowReadMe();
			}
			else{
				OptionAction(opt);
			}
		}
	}
	
	/*
	 * ------------------------------------------------------------------
	 * OPTION METHODS
	 * ------------------------------------------------------------------
	 */
	
	private static void OptionAction(int opt){
		if(opt == 11){
			Interface.ProductTwoPermutations(); return;
		}
		if(opt == 12){
			Interface.ProductPermutations(); return;
		}
		if(opt == 13){
			Interface.InversePermutation(); return;
		}
		if(opt == 14){
			Interface.DisplayBoth(); return;
		}
		if(opt == 15){
			Interface.OrderOfPermutation(); return;
		}
		if(opt == 21){
			Interface.ShowAllPerms(); return;
		}
		if(opt == 22){
			Interface.ShowAllPermsAndInverses(); return;
		}
		if(opt == 31){
			Interface.ConjugacyClassElement('E'); return;
		}
		if(opt == 32){
			Interface.ConjugacyClassElement('I'); return;
		}
		if(opt == 33){
			Interface.ConjugacyClassElement('O'); return;
		}
		if(opt == 34){
			Interface.ConjugacyClasses(); return;
		}
		if(opt == 35){
			Interface.ConjugacyClassesAndInverses(); return;
		}
		if(opt == 36){
			Interface.ConjugacyClassesOrder(); return;
		}
		if(opt == 37){
			Interface.ConjugacyClassesOneElement(); return;
		}
		if(opt == 38){
			Interface.ConjugacyClassesOneElementOrder(); return;
		}
		if(opt == 41){
			ChangeOutputOption(); return;
		}
		if(opt == 42){
			ChangeNvalue(); return;
		}
		System.out.println("Entry not valid");
		System.out.println();
	}
	
	
	private static void OptionsScreen(){
		System.out.println();
		System.out.println("Options");
		System.out.println("------------------------------------------------------------");
		System.out.println("Simple Operations");
		System.out.println("11: Find Product of Two Permutations");
		System.out.println("12: Find Product of Multiple Permutations");
		System.out.println("13: Find Inverse of a Permutation");
		System.out.println("14: Convert between Permutation and Cycle Decomposition");
		System.out.println("15: Find the Order of an Element");
		System.out.println("------------------------------------------------------------");
		System.out.println("List Every Element");
		System.out.println("21: List all permutations in S_n");
		System.out.println("22: List all permutations then their inverses in S_n");
		System.out.println("------------------------------------------------------------");
		System.out.println("Find Conjugacy Classes");
		System.out.println("31: Find Conjugacy Class of a Permutation");
		System.out.println("32: Find Conjugacy Class of a Permutation with inverse of each element");
		System.out.println("33: Find Conjugacy Class of a Permutation with order of each element in class");
		System.out.println("34: List all conjugacy classes in S_n");
		System.out.println("35: List all conjugacy classes in S_n with inverse of each element");
		System.out.println("36: List all conjugacy classes in S_n with order of each element in class");
		System.out.println("37: List all conjugacy classes in S_n (only one representative per class)");
		System.out.println("38: List all conjugacy classes in S_n (only one representative per class), then order of elements");
		System.out.println("------------------------------------------------------------");
		System.out.println("Change Settings");
		System.out.println("41: Change Output Options");
		System.out.println("42: Change Value of n");
		System.out.println("------------------------------------------------------------");
		System.out.println("1: Display ReadMe with Information and Instructions");
		System.out.println("2: Quit");
		System.out.println();
	}
	
	public static void ShowReadMe() throws FileNotFoundException{
		System.out.println();
		Scanner theFile = new Scanner(new File("/Users/Zach/workspace/Permutations/src/GroupOperations/ReadMe.txt"));
		while(theFile.hasNext()){
			System.out.println(theFile.nextLine());
		}
		System.out.println();
	}
	
	/*
	 * ------------------------------------------------------------------
	 * METHODS THAT GET INPUT FROM USER
	 * ------------------------------------------------------------------
	 */
	
	private static int OptionAsk(){
		System.out.println("What Option (Type 0 for Options Screen)?");
		sc = new Scanner(System.in);
		int opt = GetIntegerFromString(sc.nextLine());
		return opt;
	}
	
	private static void ChangeOutputOption(){
		System.out.println();
		System.out.println("Specify Output Format");
		System.out.println("Type P for bottom row of matrix");
		System.out.println("Type C for cycle decomposition");
		System.out.println("Type B for both");
		System.out.println("Type anything besides P,C, or B to exit.");
		System.out.println();
		sc = new Scanner(System.in);
		System.out.print("Output Format:");
		String entry = sc.nextLine();
		char output = InterpretCharEntry(entry);
		if(output == 'X'){
			System.out.println("No Change in Format");
			System.out.println();
			return;
		}
		if(output == 'C'){
			Interface.ChangeOutputOption('C');
			System.out.println("Set to Cycle Decomposition Format");
			System.out.println();
			return;
		}
		if(output == 'P'){
			Interface.ChangeOutputOption('P');
			System.out.println("Set to Bottom Row of Matrix Format");
			System.out.println();
			return;
		}
		if(output == 'B'){
			Interface.ChangeOutputOption('B');
			System.out.println("Set to Both Formats displaying simultaneously");
			System.out.println();
			return;
		}
	}
	
	private static void ChangeNvalue(){
		System.out.println();
		System.out.println("What Value of n do you want (must be between 3 and 14)?");
		sc = new Scanner(System.in);
		int opt = GetIntegerFromString(sc.nextLine());
		if(opt < 3 || opt > 14){
			System.out.println("Entry not valid. Value of n unchanged.");
			System.out.println();
		}
		else{
			Interface = new PermGroupInterface(opt,'C');
			System.out.println();
		}
	}
	
	
	/*
	 * ------------------------------------------------------------------
	 * METHODS THAT TEST THE USER INPUT
	 * ------------------------------------------------------------------
	 */
	
	/*
	 * This method returns the integer that the user enters.
	 * It is used when the user specifies n or the option.
	 * It returns 50 if the string was not an integer.
	 */
	private static int GetIntegerFromString(String entry){
		if(!IsNumber(entry)){
			return 50;
		}
		return Integer.parseInt(entry);
	}
	
	private static boolean IsNumber(String entry){
		for(int j = 0; j < entry.length(); j++){
			if(entry.charAt(j) < '0' || entry.charAt(j) > '9'){
				return false;
			}
		}
		return true;
	}
	
	/*
	 * method interprets the entry by the user of the output format
	 * if the user entered anything besides B,C, or P, this method
	 *       returns 'X'. Otherwise, it returns what the user specified.
	 */
	private static char InterpretCharEntry(String entry){
		if(entry.length() != 1){
			return 'X';
		}
		char[] Good = {'B','C','P'};
		for(int j = 0; j < Good.length; j++){
			if(entry.charAt(0) == Good[j]){
				return entry.charAt(0);
			}
		}
		return 'X';
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
