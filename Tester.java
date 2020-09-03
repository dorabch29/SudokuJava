import java.util.LinkedList;
import java.util.Scanner; 

public class Tester {

    public Tester(){
    }
    public static void main(String[] args){
        int[][] board=  new int[9][9];
        initializeSudokuOriginal(board);
        display(board);
        emptyFill(board , 0);
        display(board);
    }



    
    //VERSION 2 in the previous version we werent "Backtracking" however simply erasing cells and calling the function again when it failed
    //Now instead of calling the function again when it fails, it simply exits the current function and tries the next number
    //Creating and filling a board Recursive Methods
    //Current represents the index for every cell on the board from 0 to 80 inclusive
    //cant holds an array of values that the function may not place for each index 


/*
    THE FOLLOWING METHODS ARE FOR THE INT[][] REPRESENTATIONS OF THE BOARD.

*/


    //RECURSIVE ALGORITHIM TO FILL A BOARD
    public static boolean emptyFill(int[][] grid, int current){
        if(isFull(grid)){
            return true; 
        } 
        else if(getCellAt(grid, current) == 0){
            for(int i = 1; i <= 9; i ++){
                if(works(i, current / 9, current % 9, grid) ){
                    grid[current / 9][current % 9] = i;
                    if( emptyFill(grid, current + 1)) return true; 
                    grid[current / 9][current % 9] = 0;
                }
            }
            return false; 
        }
        else{
           return emptyFill(grid, current + 1);
        }

    }

    //CREATES A INT[][] REPRESENTATION OF SODUKU BOARD
    public static void initializeSudokuOriginal(int[][] emptyBoard){
        LinkedList<Integer> valuesToUse = new LinkedList<Integer>(); 
        for(int i =1; i <= 9 ; i ++) valuesToUse.add(i); 
        for(int i = 0; i < 9; i ++){
            int index = (int) (Math.random() * valuesToUse.size());
            emptyBoard[0][i] = valuesToUse.get(index);  
            valuesToUse.remove(index); 
        }
        emptyFill(emptyBoard, 0);
        display(emptyBoard);
        clear (emptyBoard, 2); 
    }

    //USED TO CLEAR CELLS IN INITIALIZATION
    public static void clear(int[][] grid,int difficulty){
        int limit;
        switch(difficulty){
            case 0:
                limit = 46;
            break; 
            case 1:
                limit = 52;
            break; 
            case 2:
                limit = 58;
            break;
            default:
                limit = 52;
        }
        LinkedList<Integer>  positions = new LinkedList<Integer>();
        for(int i = 0; i <= 80; i ++) positions.add(i); 

        for(int i = 0; i < limit && positions.size() > 1 ; i ++){
            int indexToRemove = (int)(Math.random() * positions.size() );
            int cellToRemove = positions.get(indexToRemove); 
            int temp = getCellAt(grid, cellToRemove); 
            setCellTo(grid, cellToRemove, 0);
            long startTime = System.nanoTime(); 
            if(hasOneSolution(grid)){
                positions.remove(indexToRemove); 
            }
            else{
                setCellTo(grid, cellToRemove, temp); 
                positions.remove(indexToRemove); 
                i --; 
            }
          //  if(( System.nanoTime() - startTime) >= 1000000000) return;           
        }
        System.out.println("reached");

        System.out.println(hasOneSolution(grid));
    }
    

    //Make sure we dont clear out too many cells
    //The recursive method counts the number of ways we can get a solution
    public static boolean hasOneSolution(int[][] grid){
        return (recursiveOneSolution(grid, 0) == 1);
    }

    public static long recursiveOneSolution(int[][] grid, int current){
        if(isFull(grid)){
            if (isSolved(grid)) return 1;
            else return 0; 
        }
        else if(getCellAt(grid, current) == 0){
            long counter = 0; 
            for(int i = 1; i <= 9; i ++){
                if(works(i, current / 9, current % 9 , grid) ){
                    int[][] newGrid = copyGrid(grid); 
                    setCellTo(newGrid, current, i); 
                    counter += recursiveOneSolution(newGrid, current + 1) ;
                }
            }
            return counter; 
        }
        else{
            return recursiveOneSolution(grid, current + 1); 
        }

    }
 
    
    //HELPER METHODS FOR INT[][]
    public static boolean works(int num, int row, int col, int[][] grid){
        if(worksInChunk(num, row, col, grid) && worksRowCol(num, row, col, grid) ) return true;
        else return false; 
    }

    
    public static boolean worksInChunk(int num, int row, int col, int[][] grid){
        int rowBoundary = ((row / 3) + 1) * 3; 
        int colBoundary = ((col/ 3) + 1) * 3; 
        for(int i = rowBoundary - 3; i < rowBoundary; i ++){
            for(int j = colBoundary -3; j < colBoundary; j ++){
                if(i != row || j != col){
                    if(num == grid[i][j]) return false; 
            }
            }
        }
        return true; 
    }

    public static boolean worksRowCol(int num, int row, int col, int[][] grid){
       for(int i = 0; i < 9 ; i ++){
          if( i != col){
            if(grid[row][i] == num)return false;
        }
        if(i != row){
          if(grid[i][col] == num) return false; 
        } 
       }
       return true; 
    }


    public static boolean isFull(int[][] grid){
        for(int i = 0; i < grid.length; i ++ ){
            for(int j = 0; j < grid.length; j ++){
                if(grid[i][j] == 0) return false; 
            }   
        }
        return true; 
    }

    
    public static boolean isSolved(int[][] grid){
        for(int i =0; i < grid.length; i ++){
            for(int j = 0; j < grid[i].length; j ++){
                if(!works(grid[i][j], i, j, grid)) return false; 
            }
        }
        return true; 
    }


    public static int getCellAt(int[][] grid, int space){
        return grid[space / 9][space %9]; 
    }

    public static void setCellTo(int[][] grid, int space, int num){
        grid[space / 9][space %9] = num; 
    }

    public static int[][] copyGrid(int[][] grid1){
        int[][] grid2 = new int[9][9];
        for(int i = 0; i < 9; i ++){
            for(int j = 0; j < 9; j ++){
                grid2[i][j] = grid1[i][j]; 
            }
        }
        return grid2; 
    }










    //helper methods relating to shuffle 
    public static void swap(boolean rowNotCollum, int swap1, int swap2, int[][] grid){
        for( int i = 0; i < 9; i ++){
            if(rowNotCollum){
                int temp = grid[swap1][i];
                grid[swap1][i] = grid[swap2][i];
                grid[swap2][i] = temp; 
            }
            else{
                int temp = grid[i][swap1];
                grid[i][swap1] = grid[i][swap2];
                grid[i][swap2] = temp; 
            }
        }
    }

    public static void shuffle(int[][] grid){
        for(int i = 0; i < 20; i ++){
            int base = ((int)(Math.random() * 3)) * 3;
            int swap1 = base +  (int)(Math.random() * 3); 
            int swap2 = base + (int)(Math.random() * 3);
            swap(i %2 == 0 , swap1, swap2, grid); 
        }
    }

    public static String newLetter(String letter, String rule){

        for(int i = 0 ; i < rule.length(); i ++){
            if(letter.charAt(0) == rule.charAt(i)){
                int index = (i + 1) % rule.length();
                return rule.substring(index , index + 1); 
            }
        } 
        return letter; 
    } 

    public static void display(int[][] grid){
        for(int i = 0; i < 9; i ++){
            for(int j =0; j < 9; j ++){
                if(grid[i][j] == 0){
                    System.out.print(" " + " " + " ");
                }
                else System.out.print(" " + grid[i][j] + " ");
                if(j == 2 || j == 5 || j == 8) System.out.print("|"); 
            }
            System.out.println("");
            if(i  == 2 || i == 5 || i == 8)System.out.println("----------------------------");
        }
    }



}