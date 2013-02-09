package GroupOperations;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class InputPermutation extends ThePermGroup{
	
	/*
	 * ----------------------------------------------------------------
	 * VARIABLES, CONSTRUCTORS, AND INITIALIZING SUBROUTINES
	 * ----------------------------------------------------------------
	 */
	
	private int[] PermutationOutput;//entered string as permutation
	private int[][] CycleDecompOutput;//entered string as cycle decomposition
	
	/*
	 * For the permutation, the first used number is 0 to mark it as invalid,
	 *        so InvalidPerm[1] = 0. 
	 * For the cycle decomposition, the first number is 0 to mark it as invalid,
	 *        so InvalidCycle[0][0] = 0. 
	 */
	private int[] InvalidPerm;//if permutation is invalid, this is returned
	private int[][] InvalidCycle;//if cycle decomposition is invalid, this is returned
	
	private char OriginalInput;//tells if original input is invalid, a permutation, or cycle decomposition
	private String input; //original input

	public InputPermutation(int nn){
		super(nn);
		MakeInvalid();
	}
	public InputPermutation(int nn, String GivenInput){
		super(nn);
		MakeInvalid();
		UseNewInput(GivenInput);
	}
	
	//makes sure invalid matrices get the right value for first entry
	private void MakeInvalid(){
		InvalidPerm = new int[n+1];
		InvalidPerm[1] = 0;
		
		InvalidCycle = new int[1][n];
		InvalidCycle[0][0] = 0;
	}
	
	/*
	 * ----------------------------------------------------------------
	 * PUBLIC METHODS THAT TAKE ADVANTAGE OF ALL SUBROUTINES BELOW
	 * ----------------------------------------------------------------
	 */
	
	/*
	 * Specifies a new string as input and calculates both outputs. 
	 * Then, there is the option of getting the output
	 */
	public void UseNewInput(String NewInput){
		input = NewInput;
		Execute();
		PermutationOutput = GetThePerm();
		CycleDecompOutput = GetTheCycle();
	}
	public int[] GetFinalPerm(){
		return PermutationOutput;
	}
	public int[][] GetFinalCycle(){
		return CycleDecompOutput;
	}
	public void DisplayPerm(){
		if(OriginalInput == 'X'){
			System.out.print("Invalid"); return;
		}
		DisplayPerm(PermutationOutput);
	}
	public void DisplayCycle(){
		if(OriginalInput == 'X'){
			System.out.print("Invalid"); return;
		}
		DisplayCycleDecomp(CycleDecompOutput);
	}
	
	/*
	 * Goes directly from new string to desired output, 
	 * so only one of the outputs is computed. 
	 */
	public int[] StringToPerm(String GivenInput){
		input = GivenInput;
		Execute();
		return GetThePerm();
	}
	public int[][] StringToCycle(String GivenInput){
		input = GivenInput;
		Execute();
		return GetTheCycle();
	}
	
	/*
	 * ----------------------------------------------------------------
	 * PRIVATE METHODS THAT BRING TOGETHER INFORMATION FROM SUBROUTINES
	 * ----------------------------------------------------------------
	 */
	
	/*
	 * Method tells whether the String is good and what it is, cycle or permutation
	 * Result is stored in the original input variable. 
	 * 'X': Completely Incorrect
	 * 'P': Permutation
	 * 'C': Cycle Decomposition
	 */
	private void Execute(){
		if(!GoodString()){
			OriginalInput = 'X'; return;
		}
		if(!ProperPermOrCycle()){
			OriginalInput = 'X'; return;
		}
		if(isCycle()){
			if(!ProperCycle()){
				OriginalInput = 'X'; return;
			}
			if(!CorrectTokensCycleDecomp()){
				OriginalInput = 'X'; return;
			}
			CycleDecompOutput = StringToCycleDecomp();
			if(!CorrectCycleDecomp(CycleDecompOutput)){
				OriginalInput = 'X'; return;
			}
			OriginalInput = 'C'; return;
		}
		else{
			if(!ProperPerm()){
				OriginalInput = 'X'; return;
			}
			PermutationOutput = StringToPerm();
			if(!Correct(PermutationOutput)){
				OriginalInput = 'X'; return;
			}
			OriginalInput = 'P'; return;
		}
	}
	
	/*
	 * Use information acquired about nature of input to calculate the output as a 
	 *      permutation or a cycle decomposition.
	 */
	private int[] GetThePerm(){
		if(OriginalInput == 'X'){
			return InvalidPerm;
		}
		if(OriginalInput == 'P'){
			return PermutationOutput;
		}
		if(OriginalInput == 'C'){
			return CycleDecompToPerm(CycleDecompOutput);
		}
		return InvalidPerm;
	}	
	private int[][] GetTheCycle(){
		if(OriginalInput == 'X'){
			return InvalidCycle;
		}
		if(OriginalInput == 'P'){
			return PermToCycleDecomp(PermutationOutput);
		}
		if(OriginalInput == 'C'){
			return CycleDecompOutput;
		}
		return InvalidCycle;
	}	

	/*
	 * ----------------------------------------------------------------
	 * TESTS THE CORRECTNESS OF THE STRING USED TO 
	 * SPECIFY PERMUTATION OR CYCLE DECOMPOSITION
	 * ----------------------------------------------------------------
	 */
	
	/*
	 * Makes sure that the only characters in the string are numbers
	 *       or proper delimiters.  
	 */
	private boolean GoodString(){
		char[] Acceptable = { ' ' , ',' , '{' , '}' , '(' , ')' };
		for(int j = 0; j < input.length(); j++){
			boolean goodInput = false; //says whether the string is invalid
			if(input.charAt(j) >= '0' && input.charAt(j) <= '9'){
				goodInput = true;
			}
			if(!goodInput){
				for(int i = 0; i < Acceptable.length; i++){
					if(input.charAt(j) == Acceptable[i]){
						goodInput = true;
						break;
					}
				}
				if(!goodInput){
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * This method tests whether or not the permutation or cycle was input correctly. 
	 * It makes sure () or {} is there, not both. 
	 * () is used for cycles. {} is used for permutations
	 * If () is there, it makes sure that ( is followed by )
	 * If {} is there, it makes sure that there is exactly one of { and }
	 */
	private boolean ProperPermOrCycle(){
		int NumLeftBrackets = 0; //number of '{'
		int NumLeftParenth = 0; //number of '('
		int NumRightBrackets = 0; //number of '}'
		int NumRightParenth = 0; //number of ')'
		for(int j = 0; j < input.length(); j++){
			if(input.charAt(j) == '{'){
				NumLeftBrackets++;
			}
			if(input.charAt(j) == '}'){
				NumRightBrackets++;
			}
			if(input.charAt(j) == '('){
				NumLeftParenth++;
			}
			if(input.charAt(j) == ')'){
				NumRightParenth++;
			}
		}
		if(NumLeftBrackets != NumRightBrackets){
			return false;
		}
		if(NumLeftParenth != NumRightParenth){
			return false;
		}
		if(NumLeftBrackets > 1){
			return false;
		}
		if(NumLeftBrackets == 1){
			if(NumLeftParenth > 0){
				return false;
			}
		}
		if(NumLeftParenth > 0){
			if(NumLeftBrackets > 0){
				return false;
			}
		}
		return true;
	}
	
	/*
	 * This method tests whether we have a cycle or permutation. 
	 * It returns true if it is a cycle and false if it is a permutation. 
	 * If "(" appears first, then it immediately returns true for cycle 
	 * If "{" or a number appears first, then it returns false for permutation. 
	 * Later on, the form is tested. 
	 */
	private boolean isCycle(){
		for(int j = 0; j < input.length(); j++){
			char ch = input.charAt(j);
			if(ch == '('){
				return true;
			}
			if(ch >= '0' && ch <= '9'){
				return false;
			}
			if(ch == '{'){
				return false;
			}
		}
		return false;
	}
	
	/*
	 * Makes sure the parenthesis and numbers are in the right order
	 * Cycles must be in form (a b c ...)(d e f..)...(g h ...)
	 *      
	 * There are two stages depending on what was proceeding it. 
	 * The code starts at stage one. Here are the stages
	 * 
	 * Stage One: Before "(". In this stage, nothing is allowed except "(" and spaces. Goes to stage two 
	 *           when "(" appears.
	 * Stage Two: Once "(" appears, there must be only numbers, spaces, or ",". Once ")" appears, 
	 *           the code must goes back to stage one. 
	 */
	private boolean ProperCycle(){
		boolean AtStageOne = true;//says what stage the checking is ine
		for(int j = 0; j < input.length(); j++){//makes sure the first non-space character is (
			char ch = input.charAt(j);
			if(AtStageOne){
				if(ch == '('){
					AtStageOne = false;
				}
				if(ch != '(' && ch != ' '){
					return false;
				}
			}
			else{
				if(!StageTwo(ch)){
					return false;
				}
				if(ch == ')'){
					AtStageOne = true;
				}
			}
		}
		return true;
	}
	private boolean StageTwo(char ch){
		if(ch >= '0' && ch <= '9'){
			return true;
		}
		if(ch == ' ' || ch == ',' || ch == ')'){
			return true;
		}
		return false;
	}
	
	/*
	 * Makes sure the string is in permutation form
	 * it must look like {a,b,c d} or   a b c d
	 * 
	 * There are three stages, but they do not cycle due to the form. 
	 * Stage One: Before "{" or first number. Only spaces allowed
	 * Stage Two: Numbers, "," and spaces allowed. "}" makes it go to stage three. 
	 * Stage Three: Only spaces allowed. 
	 */
	private boolean ProperPerm(){
		int index = 0; //index of input being checked
		
		//Stage One
		while(index < input.length()){//makes sure the first non-space character is { or a number
			char ch = input.charAt(index);
			index++;
			if(StageOneEnder(ch)){//sees if character is a number or "{"
				break;
			}
			if(ch != ' '){//if character is not a space, then the string is invalid
				return false;
			}
		}
		
		//Stage Two
		while(index < input.length()){
			char ch = input.charAt(index);
			index++;
			if(!StageTwoProper(ch)){
				return false;
			}
			if(ch == '}'){
				break;
			}
		}
		
		//Stage Three
		while(index < input.length()){
			char ch = input.charAt(index);
			index++;
			if(ch != ' '){
				return false;
			}
		}
		return true;
	}
	private boolean StageOneEnder(char ch){
		if(ch >= '0' && ch <= '9'){
			return true;
		}
		if(ch == '{'){
			return true;
		}
		return false;
	}
	private boolean StageTwoProper(char ch){
		if(ch >= '0' && ch <= '9'){
			return true;
		}
		if(ch == ' ' || ch == ',' || ch == '}'){
			return true;
		}
		return false;
	}

	/*
	 * --------------------------------------------------------------
	 * CONVERTS STRING INTO ARRAY FOR PERMUTATION OR CYCLE DECOMPOSITION
	 * --------------------------------------------------------------
	 */
	
	/*
	 * This goes from a string in permutation form to an integer array
	 */
	private int[] StringToPerm(){
		StringTokenizer theInput = new StringTokenizer(input," , {}");
		int size = theInput.countTokens();
		int[] thePerm = new int[size+1];//accounts for the unused index at 0 
		int index = 1;//index of permutation starts at 1
		while(theInput.hasMoreTokens()){
			thePerm[index] = Integer.parseInt(theInput.nextToken());
			index++;
		}
		return thePerm;
	}
	
	/*
	 * Going from a string in cycle form to an integer array is a bit more complicated.
	 * First it tokenizes by the parenthesis and then tokenizes each string to make sure
	 *       that it has the right number of tokens so that there will not be an 
	 *       error when adding to the entries of the integer array. 
	 * After the check, it retokenizes the string and puts each string into an entry
	 *       of a string array. 
	 * Each String in the array is then tokenized to get the entries. 
	 */
	private boolean CorrectTokensCycleDecomp(){
		StringTokenizer theInput = new StringTokenizer(input,"()");//separates the individual cycles
		while(theInput.hasMoreElements()){
			String Current = theInput.nextToken();
			StringTokenizer CurrentTokens = new StringTokenizer(Current, " ,");
			if(CurrentTokens.countTokens() > n){
				return false;
			}
		}
		return true;
	}
	private int[][] StringToCycleDecomp(){
		String[] TheCycles = SeparateStringIntoIndividualCycles();
		if(TheCycles.length == 1){//possibly the identity
			StringTokenizer OneCycle = new StringTokenizer(TheCycles[0]," ,");
			if(OneCycle.countTokens() == 1){//definitely the identity. 
				int[][] Identity = new int[1][1]; Identity[0][0] = 0;
				return Identity;
			}
		}
		return StringsToCycleDecomp(TheCycles);
	}
	private String[] SeparateStringIntoIndividualCycles(){
		StringTokenizer theInput = new StringTokenizer(input,"()");//separates the individual cycles
		String[] TheCycles = new String[theInput.countTokens()];
		int index = 0;
		while(theInput.hasMoreElements()){
			TheCycles[index] = theInput.nextToken();
			index++;
		}
		return TheCycles;
	}
	/*
	 * This method takes each string from above and tokenizes it to get the cycle it represents.
	 * Each string has a number of tokens less than or equal to n, so indices are not a concern. 
	 * However, some strings might have no tokens, so the variable "CycleNumber" is used 
	 *        for the row in the array. The variable is only advanced to the next row if
	 *        there are tokens in the String. 
	 * That way, there is not a row of 0's between two rows of cycles in the array.
	 * Furthurmore, the format for cycle decompositions dictates that once there is a 0 in the 
	 *        first entry of a row, the rest is trivial, so that step is necessary.  
	 */
	private int[][] StringsToCycleDecomp(String[] input){
		
		int[][] CycleDecomp = new int[input.length][n];//sets up cycle decomp array
		for(int i = 0; i < input.length; i++){//makes sure each entry is zero
			for(int j = 0; j < n; j++){
				CycleDecomp[i][j] = 0;
			}
		}
		
		int CycleNumber = -1;//used to keep track of cycle number. 
		for(int i = 0; i < input.length; i++){
			StringTokenizer CurrentCycle = new StringTokenizer(input[i]," ,");
			if(CurrentCycle.hasMoreTokens()){//makes sure it's non-empty
				CycleNumber++;
			}
			int CycleIndex = 0;
			while(CurrentCycle.hasMoreTokens()){
				CycleDecomp[CycleNumber][CycleIndex] = Integer.parseInt(CurrentCycle.nextToken());
				CycleIndex++;
			}
		}
		return CycleDecomp;
	}
	
	/*
	 * --------------------------------------------------------------
	 * TESTS THE PERMUTATION OR CYCLE DECOMPOSTION IN INTEGER ARRAY
	 * 		FORM TO MAKE SURE THAT THEY ARE CORRECT
	 * --------------------------------------------------------------
	 */
	
	/*
	 * This tests a cycle decomposition to see if it is correct. 
	 * This method will be called by other classes. 
	 */
	private boolean CorrectCycleDecomp(int[][] CycleD){
		int CycleNumber = 0;
		int CycleIndex = 0;
		ArrayList<Integer> cycle = new ArrayList<Integer>(n);
		while(CycleD[CycleNumber][0] != 0){
			while(CycleD[CycleNumber][CycleIndex] != 0){
				cycle.add(CycleD[CycleNumber][CycleIndex]);
				CycleIndex++;
				if(CycleIndex >= CycleD[CycleNumber].length){//we have finished the last column
					break;
				}
			}
			int[] theCycle = new int[cycle.size()];
			for(int j = 0; j < theCycle.length; j++){
				theCycle[j] = cycle.get(j);
			}
			if(!CorrectCycle(theCycle)){
				return false;
			}
			cycle.clear();
			CycleIndex = 0;
			CycleNumber++;
			if(CycleNumber >= CycleD.length){//we have finished the last row 
				break;
			}
		}
		return true;
	}
	
	/*
	 * This method tests a permutation in the raw form
	 *      and makes sure that it is correct.
	 * It must have n+1 elements. the 0 index is unused, 
	 *      hence the reason for the "+1"
	 */
	private boolean Correct(int[] perm){
		if(perm.length != n+1){
			return false;
		}
		
		/*The variable "used" is used to ensure that we have a permutation in 
		 * 	proper raw form. 
		 */
		int[] used = new int[perm.length];
		for(int j = 1; j < perm.length; j++){
			used[j] = 0;
		}
		
		for(int j = 1; j < perm.length; j++){
			if(perm[j] > n || perm[j] < 1){
				return false;
			}
			used[perm[j]]++;
		}
		for(int j = 1; j < perm.length; j++){
			if(used[j] != 1){
				return false;
			}
		}
		return true;
	}
	
	/*This tests input in cycle form to make sure that
	 *     it is in the correct format.
	 *Single cycles are not allowed because they are just the identity. 
	 *This method only tests input, so this method insures that the user
	 *     does not input the identity. 
	 */
	private boolean CorrectCycle(int[] cycle){
		if(cycle.length < 2){
			return false;
		}
		
		/*The variable "used" is used to ensure that we have a cycle in 
		 * 	proper form. 
		 */
		int[] used = new int[n+1];
		for(int j = 1; j <= n; j++){//sets it equal to zero
			used[j] = 0;
		}
		
		for(int j = 0; j < cycle.length; j++){
			if(cycle[j] > n || cycle[j] < 1){//makes sure entry is in range
				return false;
			}
			used[cycle[j]]++;//counts the number of times and entry is in the cycle
		}
		for(int j = 1; j <= n; j++){
			if(used[j] > 1){//if entry is repeated
				return false;
			}
		}
		return true;
	}
	
}
