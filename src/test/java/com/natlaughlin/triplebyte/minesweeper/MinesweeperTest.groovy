package com.natlaughlin.triplebyte.minesweeper

import org.junit.Before
import org.junit.Test

class MinesweeperTest {

    Minesweeper ms

    @Before
    void before(){
        ms = new Minesweeper()
    }

    @Test
    void testCreateBoardSize(){

        def params = [100, 100, 1]
        ms.createBoard(*params)
        assert ms.cells.size() == 10000

    }

    @Test
    void testCreateBoardDefaultNotRevealed(){

        def params = [10, 10, 0]
        ms.createBoard(*params)
        assert ms.cells.every{ it.isRevealed == false }

    }

    @Test
    void testCreateBoardAllBombs(){

        def params = [10, 10, 1]
        ms.createBoard(*params)
        assert ms.cells.every{ it.isBomb == true }
        
    }

    @Test
    void testCreateBoardNoBombs(){

        def params = [10, 10, 0]
        ms.createBoard(*params)
        assert ms.cells.every{ it.isBomb == false }

    }
}
