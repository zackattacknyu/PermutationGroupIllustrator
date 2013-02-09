package GroupOperations;

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

public class PermGroupInterface {
	
	/*
	 * ------------------------------------------------------------------
	 * CLASS VARIABLES AND INITIALIZING METHODS
	 * ------------------------------------------------------------------
	 */
	private int n;
	
	/*
	 * Specifies the format of the output
	 * 'P':permutation 
	 * 'C':cycle decomposition
	 * 'B':both
	 */
	private char OutputOption;
	
	private ThePermGroup Operator;
	private InputPermutation Input;
	private Generator Generate; 
	
	public Scanner sc = new Scanner(System.in);
	
	public PermGroupInterface(int n) {
		super();
		OutputOption = 'C';
		ChangeNvalue(n);
	}
	
	public PermGroupInterface(int n, char output) {
		super();
		OutputOption = output;
		ChangeNvalue(n);
	}
	
	public void ChangeNvalue(int nn){
		n = nn;
		Operator = new ThePermGroup(n);
		Input = new InputPermutation(n);
		Generate = new Generator(n,OutputOption);
	}
	
	public void ChangeOutputOption(char output){
		OutputOption = output;
		Generate = new Generator(n,output);
	}
	
	public char getOutputOption(){
		return OutputOption;
	}
	
	/*
	 * ------------------------------------------------------------------
	 * SECTION 1 OF OPTIONS
	 * ------------------------------------------------------------------
	 */
	
	public void ProductTwoPermutations(){
		System.out.println();
		System.out.println("Finds PermA*PermB");
		System.out.print("PermA:");
		String PermAraw = sc.nextLine();
		int[] PermA = Input.StringToPerm(PermAraw);
		if(PermA[1] == 0){
			return;
		}
		System.out.print("PermB:");
		String PermBraw = sc.nextLine();
		int[] PermB = Input.StringToPerm(PermBraw);
		if(PermB[1] != 0){
			int[] Perm = Operator.Product(PermA, PermB);
			Operator.SinglePermutationDisplay(Perm, OutputOption);
		}
		System.out.println();
		System.out.println();
	}
	
	public void ProductPermutations(){
		int index = 0;
		int[] CurrentPerm = Operator.NewPerm();
		System.out.println();
		System.out.println("Finds Perm_1*Perm_2*...*Perm_k");
		System.out.println("Put in identity permutation when finished");
		System.out.println();
		while(true){
			index++;
			System.out.print("Perm_" + index + ":");
			String PermEntryraw = sc.nextLine();
			int[] PermEntry = Input.StringToPerm(PermEntryraw);
			if(PermEntry[1] == 0){
				return;
			}
			else if (Operator.isIdentity(PermEntry)){
				break;
			}
			else {
				CurrentPerm = Operator.Product(CurrentPerm, PermEntry);
			}
		}
		System.out.println();
		Operator.SinglePermutationDisplay(CurrentPerm, OutputOption);
		System.out.println();
		System.out.println();
	}
	
	
	
	public void InversePermutation(){
		System.out.println();
		System.out.println("Get Inverse of a Permutation");
		System.out.print("Enter Permutation:");
		String PermRaw = sc.nextLine();
		int[] Perm = Input.StringToPerm(PermRaw);
		Perm = Operator.Inverse(Perm);
		if(Perm[1] != 0){
			Operator.SinglePermutationDisplay(Perm, OutputOption);
		}
		System.out.println();
		System.out.println();
	}
	
	public void DisplayBoth(){
		System.out.println();
		System.out.println("Put Permutation into both Matrix Form and Cycle Decomposition Form");
		System.out.print("Enter Permutation:");
		String PermRaw = sc.nextLine();
		Input.UseNewInput(PermRaw);
		int[] Perm = Input.GetFinalPerm();
		int[][] Cycle = Input.GetFinalCycle();
		if(Perm[1] != 0){
			Operator.DisplayPerm(Perm);
			System.out.print("          ");
			Operator.DisplayCycleDecomp(Cycle);
		}
		System.out.println();
		System.out.println();
	}
	
	public void OrderOfPermutation(){
		System.out.println();
		System.out.println("Get the Order of a Permutation");
		System.out.print("Enter Permutation:");
		String PermRaw = sc.nextLine();
		int[] Perm = Input.StringToPerm(PermRaw);
		System.out.println();
		System.out.println("Order: " + Operator.Order(Perm));
		System.out.println();
		System.out.println();
	}
	
	/*
	 * ------------------------------------------------------------------
	 * SECTION 2 OF OPTIONS
	 * ------------------------------------------------------------------
	 */
	
	public void ShowAllPerms(){
		System.out.println();
		System.out.println("All of the Permutations in S_" + n);
		Generate.ViewAllPerms();
		System.out.println();
	}
	
	public void ShowAllPermsAndInverses(){
		System.out.println();
		System.out.println("All of the Permutations in S_" + n + " with their inverses");
		Generate.ViewAllPermsAndInverses();
		System.out.println();
	}
	
	/*
	 * ------------------------------------------------------------------
	 * SECTION 3 OF OPTIONS
	 * ------------------------------------------------------------------
	 */
	
	/*
	 * These methods show the conjugacy class of a single element. 
	 * There are three different options for the class
	 * 'E': Just the conjugacy class
	 * 'I': The conjugacy class with inverse of each element
	 * 'O': The conjugacy class with order of each element
	 */
	public void ConjugacyClassElement(char option){
		System.out.println();
		if(option == 'E'){
			System.out.println("Get Conjugacy Class of a Permutation");
		}
		if(option == 'I'){
			System.out.println("Get Conjugacy Class of a Permutation with Inverse of each Element");
		}
		if(option == 'O'){
			System.out.println("Get Conjugacy Class of a Permutation with Order of each element");
		}
		System.out.print("Enter Permutation:");
		String PermRaw = sc.nextLine();
		int[] perm = Input.StringToPerm(PermRaw);
		System.out.println();
		if(option == 'E'){
			Generate.ViewConjugacyClasses(perm,false);
		}
		if(option == 'I'){
			Generate.ViewConjugacyClassesAndInverses(perm);
		}
		if(option == 'O'){
			Generate.ViewConjugacyClasses(perm,true);
		}
		System.out.println();
	}
	
	public void ConjugacyClasses(){
		System.out.println();
		System.out.println("All the Conjugacy Classes in S_" + n);
		System.out.println();
		Generate.ViewConjugacyClasses(true,false);
		System.out.println();
	}
	
	public void ConjugacyClassesAndInverses(){
		System.out.println();
		System.out.println("All the Conjugacy Classes in S_" + n + " with inverse of each element");
		System.out.println();
		Generate.ViewConjugacyClassesAndInverses();
		System.out.println();
	}
	
	public void ConjugacyClassesOrder(){
		System.out.println();
		System.out.println("All the Conjugacy Classes in S_" + n + " with order of each element in a class");
		System.out.println();
		Generate.ViewConjugacyClasses(true,true);
		System.out.println();
	}
	
	public void ConjugacyClassesOneElement(){
		System.out.println();
		System.out.println("All the Conjugacy Classes in S_" + n);
		System.out.println();
		Generate.ViewConjugacyClasses(false,false);
		System.out.println();
	}
	
	public void ConjugacyClassesOneElementOrder(){
		System.out.println();
		System.out.println("All the Conjugacy Classes in S_" + n + " with order of each element in a class");
		System.out.println();
		Generate.ViewConjugacyClasses(false,true);
		System.out.println();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
