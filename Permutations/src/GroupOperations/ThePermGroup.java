package GroupOperations;

import java.util.ArrayList;

/*
 * This is the class that gives the information on the permutations and the cycle decompositions
 * The permutations are represented by the bottom row the matrix 
 *           ( 1 2 3 4...n
 *             a b c ...  )  
 * and the first index is unused so that the top row of the matrix corresponds to the index in the array
 * 
 * Cycle decompositions are represented by arrays, where the rows are the cycles
 *       and each entry in the cycle is represented in one of the entries in the columns. 
 * The multiplication is done in order and the cycle is written in order. 
 * There is no initial unused index.
 * The entries are from 1...n, so if there is a 0, it is not in the cycle decomposition. 
 * The cycle decomposition is initialized as a two-dimensional array and kept that way
 *     so a zero entry in a row represents where the cycle stops. 
 */
public class ThePermGroup {
	public int n; //specifies the "n" that we are working in

	public ThePermGroup(int n) {
		super();
		this.n = n;
	}
	/*------------------------------------------------------------------
	 * ALL THE INITIALIZING METHODS
	 *------------------------------------------------------------------
	 */
	
	/*
	 * Specifies a new cycle decomposition, which is in the form of 
	 * 		an n-by-n array. 
	 */
	private int[][] NewCycleDecomp(int length) {
		int[][] Cycle = new int[length][length];
		for(int i = 0; i < length; i++){
			for(int j = 0; j < length; j++){
				Cycle[i][j] = 0;
			}
		}
		return Cycle;
	}
	
	/*
	 * This sets up the array that says whether the perm to cycle method has accounted for all the cycles.
	 * If an element in the permutation has been counted, then the array says true.
	 * Otherwise, it is false. The default makes everything false. 
	 */
	public boolean[] SetUpArray(int length){
		boolean[] Seen = new boolean[length];
		for(int j = 0; j < length; j++){
			Seen[j] = false;
		}
		return Seen;
	}
	public boolean Finished(boolean[] Seen){
		for(int j = 1; j < Seen.length; j++){
			if(Seen[j] == false){
				return false;
			}
		}
		return true;
	}
	
	/*
	 * returns the identity permutation for S_n
	 */
	public int[] NewPerm(){
		int[] perm = new int[n+1];
		for(int j = 1; j < perm.length; j++){
			perm[j] = j;
		}
		return perm;
	}
	
	public boolean isIdentity(int[] entry){
		for(int j = 1; j < entry.length; j++){
			if(entry[j] != j){
				return false;
			}
		}
		return true;
	}
	
	/*
	 * ------------------------------------------------------------
	 * OPERATIONS WITH PERMUTATIONS IN RAW FORM
	 *       as bottom row of matrix 
	 * ------------------------------------------------------------
	 */
	
	/*
	 * This method computes the inverse of a permutation
	 * Make sure error checking is done
	 */
	public int[] Inverse(int[] Perm){
		int[] InvPerm = new int[Perm.length];
		for(int j = 1; j < Perm.length; j++){
			InvPerm[Perm[j]] = j;
		}
		return InvPerm;
	}
	
	/*
	 * This mention computes the product of two permutations 
	 * 		put into raw form.
	 * It does PermA*PermB in that order
	 * Make sure error checking is done before method is called. 
	 */
	public int[] Product(int[] PermA, int[] PermB){
		int[] perm = new int[PermA.length];
		for(int j = 1; j < PermA.length; j++){
			perm[j] = PermB[PermA[j]];
		}
		return perm;
	}
	
	public int Order(int[] Perm){
		int order = 1;
		int[] Current = Perm;
		while(!isIdentity(Current)){
			Current = Product(Perm,Current);
			order++;
		}
		return order;
	}
	
	/*
	 * ------------------------------------------------------------
	 * CONVERTS BETWEEN FORMATS, EITHER PERMUTATION IN RAW FORM OR 
	 * CYCLE DECOMPOSITION
	 * ------------------------------------------------------------
	 */
	
	/*
	 * Converts a permutation in cycle notation to 
	 *       standard representation.
	 * Only to be called after the test is done ensuring 
	 *       correctness of variables
	 */
	public int[] CycleToPerm(int[] cycle){
		int[] perm = NewPerm();
		for(int j = 0; j < cycle.length-1; j++){
			perm[cycle[j]] = cycle[j+1];
		}
		perm[cycle[cycle.length-1]] = cycle[0];
		return perm;
	}
	
	/*
	 * Converts between a cycle decomposition and a permutation
	 */
	public int[] CycleDecompToPerm(int[][] CycleDecomp){
		int CycleNumber = 0;
		int CycleIndex = 0;
		int[] CurrentPerm = NewPerm();
		ArrayList<Integer> cycle = new ArrayList<Integer>(n);
		while(CycleDecomp[CycleNumber][0] != 0){
			while(CycleDecomp[CycleNumber][CycleIndex] != 0){
				cycle.add(CycleDecomp[CycleNumber][CycleIndex]);
				CycleIndex++;
				if(CycleIndex >= CycleDecomp[CycleNumber].length){
					break;
				}
			}
			int[] theCycle = new int[cycle.size()];
			for(int j = 0; j < theCycle.length; j++){
				theCycle[j] = cycle.get(j);
			}
			CurrentPerm = Product(CurrentPerm,CycleToPerm(theCycle));
			CycleIndex = 0;
			cycle.clear();
			CycleNumber++;
			if(CycleNumber >= CycleDecomp.length){
				break;
			}
		}
		return CurrentPerm;
	}
	
	/*
	 * This method goes from a permutation in raw form to a cycle decomposition
	 * Must be in proper cycle form
	 * 
	 */
	public int[][] PermToCycleDecomp(int[] perm){
		int[][] Cycle = NewCycleDecomp(perm.length);
		boolean[] LookedAt = SetUpArray(perm.length);
		int FirstEntry = 1;//indicates where the cycle starts
		int LastEntry = 1;//indicates the last entry from the cycle
		int CycleNumber = 0; //indicates which cycle the method is on
		int CycleIndex = 1; //index of next entry in the cycle
		int Index = 1; //index of where to start inspecting for cycles
		while(Finished(LookedAt) == false){
			while(Index <= n){//finds out where the next cycle starts
				if(perm[Index] != Index && LookedAt[Index] == false){
					Cycle[CycleNumber][0] = Index;
					FirstEntry = Index;
					LastEntry = perm[Index];
					LookedAt[Index] = true;
					break;
				}
				LookedAt[Index] = true;
				Index++;
			}
			while(LastEntry != FirstEntry){//does a loop to get the cycle
				LookedAt[LastEntry] = true;
				Cycle[CycleNumber][CycleIndex] = LastEntry;
				LastEntry = perm[LastEntry];
				CycleIndex++;
			}
			CycleIndex = 1;
			CycleNumber++;
		}
		return Cycle;
	}
	
	/* ------------------------------------------------------------
	 * DISPLAYS PERMUTATIONS AND CYCLE DECOMPOSITIONS
	 * ------------------------------------------------------------
	 */
	
	/*
	 * Displays the permutation in its raw form
	 * 		Makes sure error checking is done. 
	 */
	public void DisplayPerm(int[] Perm){
		System.out.print("{ ");
		for(int j = 1; j < Perm.length; j++){
			System.out.print(Perm[j] + " ");
		}
		System.out.print("}");
	}
	
	/*
	 * This displays a cycle in normal form
	 * It returns the number of spaces used to display it,
	 *     which is a value used by some of the methods
	 *     when you want to display two cycle decompositions at the same time
	 */
	public void DisplayCycleDecomp(int[][] cycle){
		int CycleNum = 0;//used for the row  
		int CycleIndex = 0;//used for the index in the column
		if(cycle[CycleNum][0] == 0){//identity permutation
			System.out.print("(1)");
		}
		while(cycle[CycleNum][0] != 0){
			System.out.print("( ");
			while(cycle[CycleNum][CycleIndex] != 0){
				System.out.print(cycle[CycleNum][CycleIndex] + " ");
				CycleIndex++;
				if(CycleIndex >= cycle[CycleNum].length){
					break;
				}
			}
			System.out.print(")");
			CycleIndex = 0;
			CycleNum++;
			if(CycleNum >= cycle.length){
				break;
			}
		}
	}
	
	
	/*
	 * ------------------------------------------------------------
	 * CONVERTS TO OTHER FORMAT AND DISPLAYS THE RESULTS
	 * ------------------------------------------------------------
	 */
	
	/*
	 * This method converts a cycle decomposition to a permutation 
	 *      and then displays the results. 
	 */
	public void DisplayAsPerm(int[][] cycleD){
		int[] Perm = CycleDecompToPerm(cycleD);
		DisplayPerm(Perm);
	}
	
	/*
	 * This displays a permutation in cycle form
	 * It returns the number of spaces it takes up
	 */
	public void DisplayAsCycleDecomp(int[] perm){
		int[][] CycleD = PermToCycleDecomp(perm);
		DisplayCycleDecomp(CycleD);
	}
	
	/*
	 * ------------------------------------------------------------
	 * DISPLAYS THE RESULTS
	 * ------------------------------------------------------------
	 */
	
	public void SinglePermutationDisplay(int[] Perm, char opt){
		if(opt == 'P'){
			DisplayPerm(Perm);
		}
		if(opt == 'C'){
			DisplayAsCycleDecomp(Perm);
		}
		if(opt == 'B'){
			DisplayPerm(Perm);
			System.out.print("      ");
			DisplayAsCycleDecomp(Perm);
		}
	}
	
	public void PermutationAndInverseDisplay(int[] Perm, char opt){
		if(opt == 'P'){
			DisplayPerm(Perm);
			System.out.print("   ");
			DisplayPerm(Inverse(Perm));
		}
		if(opt == 'C'){
			DisplayAsCycleDecomp(Perm);
			System.out.print("   ");
			DisplayAsCycleDecomp(Inverse(Perm));
		}
		if(opt == 'B'){
			DisplayPerm(Perm);
			System.out.print("   ");
			DisplayPerm(Inverse(Perm));
			System.out.print("          ");
			DisplayAsCycleDecomp(Perm);
			System.out.print("   ");
			DisplayAsCycleDecomp(Inverse(Perm));
		}
	}
	
	
}
