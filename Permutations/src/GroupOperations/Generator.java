package GroupOperations;

public class Generator extends ThePermGroup{

	private int[][] AllPerms; 
	private int nFactorial;
	
	/*
	 * This specifies whether the output is a permutation or cycle decomposition
	 * 'P' is for permutation. 'C' is for cycle decomposition. 
	 * 'B' is for both.
	 */
	private char OutputOption;
	
	public void setOutputOption(char outputOption) {//in case user wants to change output option
		OutputOption = outputOption;
	}
	public Generator(int nn) {
		super(nn);
		nFactorial = factorial(n);
		AllPerms = GenerateAllPerms();
		OutputOption = 1;
	}
	public Generator(int nn, char option) {
		super(nn);
		nFactorial = factorial(n);
		AllPerms = GenerateAllPerms();
		OutputOption = option;
	}
	
	private int[][] GenerateAllPerms(){
		int[] indices = new int[n];//indices of permutation
		int size = nFactorial;
		int[][] Sn = new int[size][n+1];
		PermutationGenerator x = new PermutationGenerator (n);//generates the permutations
		int index = 0;
		while (x.hasMore ()) {//while there are more permutations
		  indices = x.getNext ();//the permutation in raw index form
		  for (int i = 0; i < indices.length; i++) {
			/*+1 accounts for fact that index starts at 0,
			  whereas the permutation starts at 1*/   
			  Sn[index][i+1] = indices[i] + 1;
		  }
		  index++;
		}
		return Sn;
	}
	private int factorial(int nnn)
    {
        if( nnn <= 1 )     // base case
            return 1;
        else
            return nnn * factorial( nnn - 1 );
    }
	
	/*
	 * This specifies the location in the SeeAllPerms array of the permutation. 
	 * Since the list is in lexicographic order, the method goes down the list
	 *       until each entry is matched. 
	 */
	public int LocationOf(int[] perm){
		int index = 0;//row index of permutation. 
		for(int j = 1; j < perm.length; j++){
			while(AllPerms[index][j] != perm[j]){
				index++;
			}
		}
		return index;
	}
	
	/*
	 * ---------------------------------------------------
	 * VIEWS ALL THE PERMUTATIONS OF S_N
	 * ---------------------------------------------------
	 */
	public void ViewAllPerms() {
		int[] perm = new int[n+1];
		for(int j = 0; j < factorial(n); j++){
			for(int i = 0; i < n+1; i++){
				perm[i] = AllPerms[j][i];
			}
			SinglePermutationDisplay(perm,OutputOption);
			System.out.println();
		}
	}
	
	/*
	 * -------------------------------------------------------------
	 * VIEWS ALL THE PERMUTATIONS OF S_N FOLLOWED BY THEIR INVERSES
	 * -------------------------------------------------------------
	 */
	public void ViewAllPermsAndInverses() {
		int[] perm = new int[n+1];
		for(int j = 0; j < factorial(n); j++){
			for(int i = 0; i < n+1; i++){
				perm[i] = AllPerms[j][i];
			}
			PermutationAndInverseDisplay(perm,OutputOption);
			System.out.println();
		}
	}
	
	/*
	 * -------------------------------------------------------------
	 * VIEWS THE CONJUGACY CLASS OF AN ELEMENT
	 * -------------------------------------------------------------
	 */
	public void ViewConjugacyClasses(int[] perm, boolean DisplayOrder){
		//specifies if a permutation has been seen or not to avoid repeats
		boolean[] used = new boolean[nFactorial]; 
		if(DisplayOrder){
			System.out.println("Order: " + Order(perm));
		}
		for(int i = 0; i < nFactorial; i++){
			int[] operatingPerm = new int[n+1];
			for(int j = 1; j < n+1; j++){
				operatingPerm[j] = AllPerms[i][j];
			}
			int[] PermElement = Product(operatingPerm, perm);
			int[] PermElementInv = Product(PermElement,Inverse(operatingPerm));
			int PEIindex = LocationOf(PermElementInv);
			if(!used[PEIindex]){
				SinglePermutationDisplay(PermElementInv,OutputOption);
				System.out.println();
			}
			used[PEIindex] = true;
		}
	}
	
	/*
	 * -------------------------------------------------------------
	 * VIEWS THE CONJUGACY CLASS OF AN ELEMENT FOLLOWED BY THE INVERSE OF THAT ELEMENT
	 * -------------------------------------------------------------
	 */
	public void ViewConjugacyClassesAndInverses(int[] perm){
		//specifies if a permutation has been seen or not to avoid repeats
		boolean[] used = new boolean[nFactorial]; 
		for(int i = 0; i < nFactorial; i++){
			int[] operatingPerm = new int[n+1];
			for(int j = 1; j < n+1; j++){
				operatingPerm[j] = AllPerms[i][j];
			}
			int[] PermElement = Product(operatingPerm, perm);
			int[] PermElementInv = Product(PermElement,Inverse(operatingPerm));
			int PEIindex = LocationOf(PermElementInv);
			if(!used[PEIindex]){
				PermutationAndInverseDisplay(PermElementInv,OutputOption);
				System.out.println();
			}
			used[PEIindex] = true;
		}
	}
	
	/*
	 * -------------------------------------------------------------
	 * VIEWS THE CONJUGACY CLASSES
	 * -------------------------------------------------------------
	 */
	
	/*
	 * DisplayAll determine if it displays all the elements in each class or not.
	 * If not, then it will display just the first element
	 */
	public void ViewConjugacyClasses(boolean DisplayAll, boolean DisplayOrder){
		
		//specifies if a permutation has been seen or not to avoid repeats
		boolean[] used = new boolean[nFactorial];
		
		int[] perm = new int[n+1]; //specifies the permutation being used for conjugacy class
		
		for(int k = 1; k < nFactorial; k++){//specifies index of next permutation to find conjugacy class of
			if(!used[k]){ //if the permutation has not been assigned a conjugacy class
				for(int h = 1; h < perm.length; h++){//gets the permutation to use for that instance
					perm[h] = AllPerms[k][h];
				}
				if(DisplayOrder){
					System.out.println("Order: " + Order(perm));
				}
				if(!DisplayAll){
					SinglePermutationDisplay(perm,OutputOption);
					System.out.println();
				}
				for(int i = 0; i < nFactorial; i++){
					int[] operatingPerm = new int[n+1];
					for(int j = 1; j < n+1; j++){
						operatingPerm[j] = AllPerms[i][j];
					}
					int[] PermElement = Product(operatingPerm, perm);
					int[] PermElementInv = Product(PermElement,Inverse(operatingPerm));
					int PEIindex = LocationOf(PermElementInv);
					if(!used[PEIindex] && DisplayAll){
						SinglePermutationDisplay(PermElementInv,OutputOption);
						System.out.println();
					}
					used[PEIindex] = true;
				}
				System.out.println();
			}
		}
	}
	
	/*
	 * -------------------------------------------------------------
	 * VIEWS THE CONJUGACY CLASSES FOLLOWED BY INVERSE OF ELEMENTS
	 * -------------------------------------------------------
	 */
	public void ViewConjugacyClassesAndInverses(){
		
		//specifies if a permutation has been seen or not to avoid repeats
		boolean[] used = new boolean[nFactorial];
		
		int[] perm = new int[n+1]; //specifies the permutation being used for conjugacy class
		
		for(int k = 1; k < nFactorial; k++){//specifies index of next permutation to find conjugacy class of
			if(!used[k]){ //if the permutation has not been assigned a conjugacy class
				for(int h = 1; h < perm.length; h++){//gets the permutation to use for that instance
					perm[h] = AllPerms[k][h];
				}
				for(int i = 0; i < nFactorial; i++){
					int[] operatingPerm = new int[n+1];
					for(int j = 1; j < n+1; j++){
						operatingPerm[j] = AllPerms[i][j];
					}
					int[] PermElement = Product(operatingPerm, perm);
					int[] PermElementInv = Product(PermElement,Inverse(operatingPerm));
					int PEIindex = LocationOf(PermElementInv);
					if(!used[PEIindex]){
						PermutationAndInverseDisplay(PermElementInv,OutputOption);
						System.out.println();
					}
					used[PEIindex] = true;
				}
				System.out.println();
			}
		}
	}

}
