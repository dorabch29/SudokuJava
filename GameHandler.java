import java.util.LinkedList;
import java.awt.*;

public class GameHandler {
    private LinkedList<Clickable> ClickableList;
    //Board that will be used to solve the board
    private int[][] SudokuOriginal = new int[9][9];
    //Board that is used to check the users board
    public int[][] SudokuUser = new int[9][9];
    //All the cells that are being displayed on the screen
    private Cell[] CellList = new Cell[81]; 
    //The cell that is currently being selected by the mouse handler
    private int selectedCell = 0; 
    private Timer timer; 

    

    public GameHandler(int SPACING){
        for(int i = 0; i < 81; i ++){
            CellList[i] = new Cell(i, SPACING); 
        }
    }

    //CREATING BOARD
    public void initializeSudoku(){
        SudokuOriginal = new int[9][9];
        initializeSudokuOriginal(SudokuOriginal , 1);
        SudokuUser = copyGrid(SudokuOriginal); 
        UpdateCells();
        timer.restart();
    }

    public void initializeSudoku(int Difficulty){
        timer.stop();
        resetCells(); 
        SudokuOriginal = new int[9][9];
        initializeSudokuOriginal(SudokuOriginal, Difficulty);
        SudokuUser = copyGrid(SudokuOriginal); 
        UpdateCells();
        timer.restart(); 
    }



    //LOCKING MECHANISMS
    public boolean isSelectedLocked(){
        return CellList[selectedCell].isLocked();
    }

    public void setToFail(){
        for(int i = 0 ; i < CellList.length; i ++){
            if(SudokuOriginal[i / 9][i % 9] == 0) CellList[i].unLock();
            CellList[i].setToFailedState();
        }
    }

    public void fullLock(){
        for(int i = 0; i < 81; i ++){
            CellList[i].lock();
        }
        for(int i = 0; i < ClickableList.size(); i++){
            ClickableList.get(i).lock(); 
        }
    }

    public void failedUnlock(){
        for(int i = 0; i < 81; i ++){
            if(SudokuOriginal[i/9][i%9] == 0)CellList[i].unLock();
        }
        for(int i = 0; i < ClickableList.size(); i++){
            ClickableList.get(i).unLock(); 
        }       
    }

    public void ClickableUnlock(){
        for(int i = 0; i < ClickableList.size(); i++){
            ClickableList.get(i).unLock(); 
        }  
    }

        //UPDATING AND LOCKING ALL CELLS
        //TO ONLY BE CALLED AFTER BUILT IN ALGORITHMS 
    public void UpdateCells(){
        for(int i = 0; i < 81;i ++){
            int num = getCellAt(SudokuUser , i);
            if(getCellAt(SudokuUser , i) != 0){
                CellList[i].setValue(num);
                CellList[i].lock(); 
            }
        }
    }

    public void UpdateCellNoLock(int[][] grid){
        for(int i = 0; i < 81; i ++){
            CellList[i].setValue(grid[i /9][i %9]);
        }
    }

    public void resetCells(){
        for(int i = 0 ; i< 81; i ++){
            CellList[i].reset();
        }
    }


    //BUTTON METHODS BUTTON METHODS BUTTON METHODS BUTTON METHODS TO BE CALLED BY ACTION LISTENER
    public void checkGame(){
        this.fullLock();
        if(isSolved(SudokuUser)){
            for(int i = 0; i < 81; i ++){
                CellList[i].setToVictoryState();
                try{ Thread.sleep(1);} catch (Exception e){}
                this.ClickableUnlock();
            }
            timer.stop(); 
        }
        else{
            setToFail(); 
            this.failedUnlock();
        }
    }
    public void solveBoard(){
        modifiedEmptyFill(SudokuOriginal, 0);
        SudokuUser = copyGrid(SudokuOriginal);
        UpdateCells();
    }


    public boolean modifiedEmptyFill(int[][] grid, int current){
        if(isFull(grid)){
            return true; 
        } 
        else if(getCellAt(grid, current) == 0){
            for(int i = 1; i <= 9; i ++){
                if(works(i, current / 9, current % 9, grid) ){
                    grid[current / 9][current % 9] = i;
                    this.UpdateCellNoLock(grid);
                    try{Thread.sleep(0, 4);}catch(Exception e){}
                    if( modifiedEmptyFill(grid, current + 1)) return true; 
                    grid[current / 9][current % 9] = 0;
                }
            }
            return false; 
        }
        else{
           return modifiedEmptyFill(grid, current + 1);
        }

    }        

    



    //DISPLAYING GRAPHICS
    public void render(Graphics g){
        for(int i = 0; i < CellList.length; i ++ ){
            CellList[i].render(g); 
        }
        timer.render(g);
    }




    //ADDING COMPONENTS
    public void addClickables(LinkedList<Clickable> clicks){
        ClickableList = clicks; 
    }

    public void addCellList(Cell[] initialList){
        CellList = initialList; 
    }

    public void addTimer(Timer timer){
        this.timer = timer; 
    }




    // MOUSE LISTENERS AND KEYBOARD LISTENER METHODS
    public void switchSelected(int toSelect){
        if(toSelect != selectedCell){
            CellList[selectedCell].unSelect();
            CellList[toSelect].select(); 
            selectedCell = toSelect;
        }

    }   

    public int getSelectedCell(){
        return selectedCell;
    }

    public void updateByKeyClick(int position, int num ){
        SudokuUser[position / 9][position % 9] = num;
        CellList[position].setValue(num);
    }





/*

ALGORITHIMS 
---------------------------------------------------------------------------------------
    THE FOLLOWING METHODS ARE RAW STATIC METHODS FOR THE INT[][] REPRESENTATIONS OF THE BOARD.
    THEY SHOULD NOT BE USED SINGULARLY ON ANY OF THE BOARD ATTRIBUTES 
    THE FOLLOWING METHODS ARE RAW STATIC METHODS FOR THE INT[][] REPRESENTATIONS OF THE BOARD.
    THEY SHOULD NOT BE USED SINGULARLY ON ANY OF THE BOARD ATTRIBUTES 
    THE FOLLOWING METHODS ARE RAW STATIC METHODS FOR THE INT[][] REPRESENTATIONS OF THE BOARD.
    THEY SHOULD NOT BE USED SINGULARLY ON ANY OF THE BOARD ATTRIBUTES 
    THE FOLLOWING METHODS ARE RAW STATIC METHODS FOR THE INT[][] REPRESENTATIONS OF THE BOARD.
    THEY SHOULD NOT BE USED SINGULARLY ON ANY OF THE BOARD ATTRIBUTES 
   
-----------------------------------------------------------------------------------------
*/


    //RECURSIVE ALGORITHIM TO FILL A BOARD
    public boolean emptyFill(int[][] grid, int current){
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
    public void initializeSudokuOriginal(int[][] emptyBoard , int Difficulty){
        LinkedList<Integer> valuesToUse = new LinkedList<Integer>(); 
        for(int i =1; i <= 9 ; i ++) valuesToUse.add(i); 
        for(int i = 0; i < 9; i ++){
            int index = (int) (Math.random() * valuesToUse.size());
            emptyBoard[0][i] = valuesToUse.get(index);  
            valuesToUse.remove(index); 
        }
        emptyFill(emptyBoard, 0);
        display(emptyBoard);
        clear (emptyBoard, Difficulty); 
        display(emptyBoard);
    }

    //USED TO CLEAR CELLS IN INITIALIZATION
    public static void clear(int[][] grid,int difficulty){
        int limit;
        switch(difficulty){
            case 0:
                limit = 40;
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
            if(( System.nanoTime() - startTime) >= 1000000000) return;           
        }

        System.out.print(hasOneSolution(grid));
    }
    
    //USED TO MAKE SURE WE ARE NOT CLEARING TOO MANY CELLS
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
    public static boolean boardSolved(int[][] grid){
        for(int i =0; i < 9; i ++){
            for(int j = 0; j < 9; j ++){
                if(!works(grid[i][j], i, j, grid) || grid[i][j] == 0) return false; 
            }
        }
        return true; 
    }

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