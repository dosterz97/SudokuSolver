import java.util.*;
import java.io.*;
/**
 * Solves a
import java.util.*;
import java.io.*;
/**
 * Solves a Soduku puzzle
 * 
 * @Zach Doster 
 * @1/18/17
 */
public class Soduku
{
    // instance variables - replace the example below with your own
    static private int SIZE = 9;
    private int[][] board = new int[SIZE][SIZE];
    private int[][][] possible = new int [SIZE][SIZE][SIZE];
    
    static public void main(String[] args)
    {
    	Soduku b = new Soduku();
        System.out.println("Zach Doster");
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.print("Please enter your file name: ");
    	boolean again= false;
    	do {
	        
	        String fName = reader.nextLine();
	        b.readFile(fName);
	        b.printBoard();
	        b.solve();
	        b.printBoard();
	        System.out.print("Press enter to quit or type in a new file name.");
	        fName = reader.nextLine();
	        if(fName == "") 
	        	again= true;
    	} while(again != false);
    	reader.close();
    }
    
    
    /**
     * Constructor for objects of class Soduku
     */
    public Soduku()
    {
        // the board to all 0
        for(int i =0; i < SIZE; i++)
        {
            for(int k=0; k < SIZE; k++)
            {
                board[k][i]=0;
                for(int j=0; j<SIZE; j++)
                    possible[k][i][j]=0;
            }
        }
    }

    
    //solves the puzzle by looking at the possible openings
    public void solve()
    {
        int counter=0;
        do
        {
            //get the possibilities for each square
            for(int i = 0; i < SIZE; i++)
            {
                for(int k = 0; k <SIZE; k++)
                {
                    if(board[k][i]==0)
                    {
                        //get the possible numbers already in each row,col,box
                        ArrayList<Integer> row = getRowList(i);
                        ArrayList<Integer> col = getCollumnList(k);
                        ArrayList<Integer> box = getBoxList(k/3 + (i/3)*3);
                        //find the possible numbers for the square
                        ArrayList<Integer> result = getPossible(row,col,box);
                        
                        if(result.size()==1)//Only one possible solution
                            board[k][i]=result.get(0).intValue();
                        
                        else {
                            //add it to the the array
                            for(int j =0; j < result.size(); j++)
                                possible[k][i][j]=result.get(j).intValue();
                        }
                    }
                }
            }
            
            //now that you have gotten possibilities see what you can do
            for(int i = 0; i < SIZE; i++)
            {
                for(int k = 0; k <SIZE; k++)
                {
                    if(board[k][i]==0)
                    {
                        //do nothing if there are no possibilities
                        if(possible[k][i][0]==0)
                        {}                            
                        //if one possibility do that but it should not go here anyway
                        else if(possible[k][i][1]==0)
                        {
                            board[k][i]=possible[k][i][0];
                        }
                    }
                }
            }
            counter++;
        } while(counter<10);
    }
    
    
    /**
     * Takes in list of the numbers in the row collumn and box
     * Returns list of the possible numbers
     */
    public ArrayList<Integer> getPossible(ArrayList<Integer> row,
    ArrayList<Integer> col,ArrayList<Integer> box)
    {
        int numbers[] = new int[SIZE];
        
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for(int i =0 ; i < SIZE; i++)
            numbers[i]=0;
            
        for(int i =0 ; i < row.size(); i++)
            numbers[row.get(i).intValue()-1]=1;
        
        for(int i =0 ; i < col.size(); i++)
            numbers[col.get(i).intValue()-1]=1;
        
        for(int i =0 ; i < box.size(); i++)
        	numbers[box.get(i).intValue()-1]=1;
        
        for(int i =0; i <SIZE;i++)
        {
            if(numbers[i]!=1)
                temp.add(new Integer(i+1));
        }
        return temp;
    }
    
    
    /**
     * Prints the Soduku board
     */
    public void printBoard() {
    	System.out.println("********");
        for(int i =0; i < SIZE; i++)
        {
            for(int k=0; k < SIZE; k++)
            {
            	if(board[k][i]==0)
            		System.out.print(".");
            	else
            		System.out.print(board[k][i]);
            }
            System.out.print("\n");
        }
    	System.out.println("********");
    }
    
    
    /**
     * Receives the file name of .txt
     * Reads in the file and sets the board
     * Returns Void
     */
    public void readFile(String fName) {
        try {
            //Create the file reader
            Scanner fileScanner = new Scanner(new File(fName));
            for(int i = 0 ; i < 9; i++)
            {
                for(int k = 0; k < 9; k++)
                {
                    board[k][i]=(fileScanner.nextInt());
                }
            }
            fileScanner.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Could not find" + fName);
        }
    }
      
    
    //returns a list of all the numbers in the row
    public ArrayList<Integer> getRowList(int rowNumber)
    {
        ArrayList<Integer> numsInRow = new ArrayList<Integer>();
        for(int i = 0; i < SIZE; i++)
        {
            if(board[i][rowNumber] != 0)
            {
                Integer b = new Integer(board[i][rowNumber]);
                numsInRow.add(b);  
            }
        }
        return numsInRow;
    }
    
    
    //returns a list of all the numbers in the collumn Given
    public ArrayList<Integer> getCollumnList(int collumnNumber)
    {
        ArrayList<Integer> numsInCollumn = new ArrayList<Integer>();
        for(int i = 0; i < SIZE; i++)
        {
            if(board[collumnNumber][i] != 0)
            {
                Integer b = new Integer(board[collumnNumber][i]);
                numsInCollumn.add(b);
            }
        }
        return numsInCollumn;
    }
    
    //Location 0 = 0-2,0-2,1 = 3-5,0-2
    //Returns a list of all the numbers in the box given
    public ArrayList<Integer> getBoxList(int location)
    {
        ArrayList<Integer> numsInBox = new ArrayList<Integer>();
        
        //choose the 3 collumns
        for(int i = (location / 3) * 3; i < 3 + (location / 3) * 3; i++)
        {
            //choose the rows
            for(int k = (location % 3) * 3; k < 3 + (location%3)* 3; k ++)
            {
                if(board[k][i]!=0)
                {
                    Integer b = new Integer(board[k][i]);
                    numsInBox.add(b);
                }
            }
        }
        return numsInBox;
    }
    
    //Returns true if the puzzle is solved
    public boolean isSolved()
    {
        for(int i = 0; i < SIZE; i++)
        {
            for(int k=0; k < SIZE; k++)
            {
                if(board[i][k]==0)
                    return false;
            }
        }
        return true;
    }
} sudoku puzzle
 * 
 * @Zach Doster 
 * @12/5/16
 */
public class Sudoku
{
    // instance variables - replace the example below with your own
    static private int SIZE = 9;
    private int[][] board = new int[SIZE][SIZE];
    private int[][][] possible = new int [SIZE][SIZE][SIZE];
    
    static public void main(String[] args)
    {
        Sudoku b = new Sudoku();
        System.out.println("Zach Doster");
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.print("Please enter your file name: ");
        String fName = reader.nextLine();
        b.readFile(fName);
        b.printBoard();
        b.solve();
    }
    
    
    /**
     * Constructor for objects of class Sudoku
     */
    public Sudoku()
    {
        // the board to all 0
        for(int i =0; i < SIZE; i++)
        {
            for(int k=0; k < SIZE; k++)
            {
                board[k][i]=0;
                for(int j=0; j<SIZE; j++)
                    possible[k][i][j]=0;
            }
        }
    }

    
    //solves the puzzle by looking at the possible openings
    public void solve()
    {
        boolean change;
        int counter=0;
        do
        {
            change = false;
            //get the possibilites for each square
            for(int i = 0; i < SIZE; i++)
            {
                for(int k = 0; k <SIZE; k++)
                {
                    if(board[k][i]==0)
                    {
                        //get the possible numbers already in each row,col,box
                        ArrayList<Integer> row = getRowList(i);
                        ArrayList<Integer> col = getCollumnList(k);
                        ArrayList<Integer> box = getBoxList(k/3 + (i/3)*3);
                        //find the possible numbers for the square
                        System.out.println("x: " + k + " y: " + i);
                        ArrayList<Integer> result = getPossible(row,col,box);
                        
                        if(result.size()==1) {
                            System.out.print(result.get(0).intValue());
                            board[k][i]=result.get(0).intValue();
                        }
                        else {
                            //add it to the the array
                            for(int j =0; j < result.size(); j++)
                            {
                                possible[k][i][j]=result.get(j).intValue();
                            }  
                        }
                    }
                }
            }
            
            //now that you have gotten possibilities see what you can do
            for(int i = 0; i < SIZE; i++)
            {
                for(int k = 0; k <SIZE; k++)
                {
                    if(board[k][i]==0)
                    {
                        //do nothing if there are no possibilites
                        if(possible[k][i][0]==0)
                        {}                            
                        //if one possibility do that but it shouldnt go here anyway
                        else if(possible[k][i][1]==0)
                        {
                            board[k][i]=possible[k][i][0];
                        }
                        //find the possibilities in the box and see
                        //if a number cant go anywhere else besides a place
                        else {
                            eliminateSquare(k,i);
                        }
                    }
                }
            }
            printBoard();
            counter++;
        }while( counter<10);
    }
    
    
    /**
     * Takes a location and finds if other spots are
     * unable to host one of the possibilites
     */
    public int eliminateSquare(int x, int y)
    {
        int location = x/3 + (y/3)*3;
        
        return 4;
    }
    /**
     * Takes in list of the numbers in the row collumn and box
     * Returns list of the possible numbers
     */
    public ArrayList<Integer> getPossible(ArrayList<Integer> row,
    ArrayList<Integer> col,ArrayList<Integer> box)
    {
        int numbers[] = new int[SIZE];
        int num=-1;
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for(int i =0 ; i < SIZE; i++)
        {
            numbers[i]=0;
        }
        for(int i =0 ; i < row.size(); i++)
        {
            //System.out.println("Row Num: " +row.get(i).intValue());
            numbers[row.get(i).intValue()-1]=1;
        }
        for(int i =0 ; i < col.size(); i++)
        {
            //System.out.println("Col Num: " +col.get(i).intValue());
            numbers[col.get(i).intValue()-1]=1;
        }
        for(int i =0 ; i < box.size(); i++)
        {
            //System.out.println("Box Num: " +box.get(i).intValue());
            numbers[box.get(i).intValue()-1]=1;
        }
        for(int i =0; i <SIZE;i++)
        {
            System.out.println("index : " + i + " " + numbers[i]);
            int count;
            if(numbers[i]!=1)
            {
                Integer b = new Integer(i+1);
                temp.add(b);
            }
        }
        return temp;
    }
    
    
    /**
     * Prints the sudoku board
     */
    public void printBoard() {
        for(int i =0; i < SIZE; i++)
        {
            for(int k=0; k < SIZE; k++)
            {
                System.out.print(board[k][i]);
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }
    
    
    /**
     * Receives the file name of .txt
     * Reads in the file and sets the board
     * Returns Void
     */
    public void readFile(String fName) {
        String line = null;
        try {
            //Create the file reader
            Scanner fileScanner = new Scanner(new File(fName));
            for(int i = 0 ; i < 9; i++)
            {
                for(int k = 0; k < 9; k++)
                {
                    board[k][i]=(fileScanner.nextInt());
                }
            }
            fileScanner.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Could not find" + fName);
        }
    }
      
    
    //returns a list of all the numbers in the row
    public ArrayList<Integer> getRowList(int rowNumber)
    {
        ArrayList<Integer> numsInRow = new ArrayList<Integer>();
        for(int i = 0; i < SIZE; i++)
        {
            if(board[i][rowNumber] != 0)
            {
                Integer b = new Integer(board[i][rowNumber]);
                numsInRow.add(b);  
            }
        }
        return numsInRow;
    }
    
    
    //returns a list of all the numbers in the collumn Given
    public ArrayList<Integer> getCollumnList(int collumnNumber)
    {
        ArrayList<Integer> numsInCollumn = new ArrayList<Integer>();
        for(int i = 0; i < SIZE; i++)
        {
            if(board[collumnNumber][i] != 0)
            {
                Integer b = new Integer(board[collumnNumber][i]);
                numsInCollumn.add(b);
            }
        }
        return numsInCollumn;
    }
    
    //Location 0 = 0-2,0-2,1 = 3-5,0-2
    //Returns a lsit of all the numbers in the box given
    public ArrayList<Integer> getBoxList(int location)
    {
        ArrayList<Integer> numsInBox = new ArrayList<Integer>();
        
        //choose the 3 collumns
        for(int i = (location / 3) * 3; i < 3 + (location / 3) * 3; i++)
        {
            //choose the rows
            for(int k = (location % 3) * 3; k < 3 + (location%3)* 3; k ++)
            {
                if(board[k][i]!=0)
                {
                    Integer b = new Integer(board[k][i]);
                    numsInBox.add(b);
                }
            }
        }
        
        return numsInBox;
    }
    
    
    //Returns true if the puzle is solved
    public boolean isSolved()
    {
        for(int i = 0; i < SIZE; i++)
        {
            for(int k=0; k < SIZE; k++)
            {
                if(board[i][k]==0)
                    return false;
            }
        }
        return true;
    }
    
    
}
