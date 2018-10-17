package com.natlaughlin.triplebyte.tictactoe

import org.junit.Test

class TicTacToeTest {

    @Test
    void addTokenTest(){

        Token t = new Token()
        t.value = Token.X

        Board b = new Board()
        b.addToken(t, 1, 1)
        assert b.tokens[1][1].value == "X"

    }

    @Test
    void isBoardFull(){

        Board b = new Board()
        assert b.isFull() == false
    }

    @Test
    void moveAI(){

        Board b = new Board()
        AI.move(b)
        b.print()
    }

}


