                          


_________________________________________________________________________________
Start of ReadMe file



                          Welcome to

--------------------------------------------------------------
               Permutation Group Illustrator
--------------------------------------------------------------

             
                           Created by
                        Zachary DeStefano




-------------------------
Introduction
-------------------------
The goal of this program is to illustrate the group theoretic 
properties of the permuation group. It accomplishes this by
performing many of the operations that are done when one first
learns about Abstract Algebra, including finding the product,
inverse, and conjugacy class of permutations. The program also
lists all the permuations in a permutation group. 


-------------------------
Format of Permutation
-------------------------
Elements of the Permutation Group can be represented one of two ways
in this program: Bottom Row of Matrix showing group action or 
product of cycles. 

Bottom Row of Matrix: Every element of S_n can be represented by a matrix
of the form  ( 1     2   ... n   )
             ( a_1   a_2 ... a_n )
where a_1,a_2,...,a_n is a permutation of the elements 1,2,...,n. 

Product of Cycles: Every element of S_n is also the product of cycles, such as
( 1 2 )( 3 4) in S_4. 


-------------------------
Inputting a Permutation
-------------------------
Anytime the program asks for a permutation, you can input it into either
       of the formats above. Here are the instructions for using those formats.

Bottom Row of Matrix: Use brackets or nothing to denote start and end of bottom row. 
       Then, use spaces or commas to separate the numbers in the bottom row. 
       The entry must be a permutation of the elements 1,...,n. 

Product of Cycles: Use parenthesis to denote each cycle. Use commas or spaces 
       to separate the numbers in the cycle. Each cycle must have at least two elements.
       However, you can use (1) to denote the identity permutation. 

If entry invalid: If you enter an invalid entry for the permutation, which is everything
       except something in the format above, the program returns you to the options screen. 


---------------------------------------------
Permutation Output: One Permutation per Line
---------------------------------------------
The output looks a little different depending on what the program is set to.

Bottom Row of Matrix: Displayed as "{ a[1] a[2] ... a[n] }" where a[1],a[2],...,a[n] 
       is a permutation of 1,...,n. 

Cycle Decomposition: Displays a product of disjoint cycles such as (1 3)(2 4). 
       (k) for any integer k denotes the identity permutation in cycle decomposition format. 

Both: Displays the bottom row one first, then the product of disjoint cycles to the right of that. 


------------------------------------------------------------
Permutation Output: Permutation and its inverse in one line
------------------------------------------------------------
With Bottom Row of Matrix and Cycle Decomposition format, the program displays 
the permutation and then its inverse in the same format to the right of the 
original permutation. 

When both formats are asked for, the program displays the permutation and then its inverse
in the bottom row of matrix form. Then, to the right, it displays the permutation and 
then its inverse in the cycle decomposition format. 






End of ReadMe file
____________________________________________________________________________________

