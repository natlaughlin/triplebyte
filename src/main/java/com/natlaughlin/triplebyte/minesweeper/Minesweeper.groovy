package com.natlaughlin.triplebyte.minesweeper

class Cell{
    int x
    int y
    boolean isBomb
    boolean isRevealed
    List<Cell> adjacentCells
    int numberOfAdjacentBombs
}

class Minesweeper {

    Cell[][] cellarray
    List<Cell> cells

    static void main(String... args) {
        Minesweeper ms = new Minesweeper()

        def params = args ?: [10,10,0.1]
        ms.play(*params)
    }

    void play(String m, String n, String p){
        play(Integer.valueOf(m), Integer.valueOf(n), Double.valueOf(p))
    }
    
    void play(int m, int n, double p){

        createBoard(m, n, p)
        
        while(1){
            printBoard()
            readCommand()
            checkWin()
        }

    }

    void createBoard(int m, int n, double p){

        cellarray = new Cell[m+2][n+2]
        cells = []
        for(x in 1..m){
            for(y in 1..n){
                Cell c = new Cell(
                    x : x,
                    y : y,
                    isBomb: (Math.random() < p),
                    isRevealed: false,
                )
                cells << c
                cellarray[x][y] = c
            }
        }
        cells.each{ cell ->
            def x = cell.x
            def y = cell.y
            cell.adjacentCells = [
                cellarray[x][y+1],
                cellarray[x+1][y+1],
                cellarray[x+1][y],
                cellarray[x+1][y-1],
                cellarray[x][y-1],
                cellarray[x-1][y-1],
                cellarray[x-1][y],
                cellarray[x-1][y+1],
            ].findAll{
                it != null
            }
            cell.numberOfAdjacentBombs = cell.adjacentCells.count{
                it.isBomb == true
            }
        }
        
    }

    void printBoard(){

        println("######")

        def groups = cells.groupBy { cell ->
            cell.y
        }
        groups.each{ group ->
            def out = group.value.collect{ cell ->
                if(cell.isRevealed){
                    if(cell.isBomb){
                        "*"
                    }
                    else{
                        "${cell.numberOfAdjacentBombs}"
                    }
                }
                else{
                    "-"
                }
            }

            println(out.join(" "))
        }

        println("######")

    }

    void revealCell(Stack<Cell> visitCellStack){

        Cell cell = visitCellStack.pop()
        if(cell.isBomb){
            lose()
        }
        else if(!cell.isRevealed) {
            cell.isRevealed = true
            if(cell.numberOfAdjacentBombs == 0){
                cell.adjacentCells.each{ adjacentCell ->
                    if(!adjacentCell.isRevealed){
                        visitCellStack.push(adjacentCell)
                    }
                }
            }
        }

    }

    void readCommand(){
        
        println("type a tile coordinate to reveal (X Y from top left)")
        def cmd = System.in.newReader().readLine().split()

        if(cmd.size() == 2){
            Cell cell
            try{
                int x = Integer.valueOf(cmd[0])
                int y = Integer.valueOf(cmd[1])
                cell = cells.find{ it.x == x && it.y == y}
            }
            catch(NumberFormatException ex){
                 println("invalid number format")
            }
            if(cell){
                Stack<Cell> visitCellStack = new Stack<Cell>()
                visitCellStack.push(cell)
                while(visitCellStack.size() > 0){
                   revealCell(visitCellStack)
                }
            }else{
                println("invalid cell")
            }
        } else{
            println("invalid command")
        }


    }

    void checkWin(){

        boolean won = cells.every{ cell ->
            cell.isRevealed || cell.isBomb
        }
        if(won){
            win()
        }
        
    }

    void showBoard(){
        cells.each{ cell ->
            cell.isRevealed = true
        }
        printBoard()
    }

    void lose(){
        println("you lose")
        showBoard()
        System.exit(0)
    }

    void win(){
        println("you win")
        showBoard()
        System.exit(0)
    }

    

}
