package com.natlaughlin.triplebyte.tictactoe

class Token{

    static final X = "X"
    static final O = "O"

    String value
}

class AI{

    static void move(Board board){

        if(board.isFull()){
            throw new Exception("cant move")
        }

        for(x in 0..2){
            for(y in 0..2){
                if(board.tokens[x][y] == null){
                    Token t = new Token(
                            value: Token.O
                    )
                    board.addToken(t, x, y)
                    return
                }
            }
        }

    }

}

class Board{

    Token[][] tokens = new Token[3][3]

    void addToken(Token token, int x, int y){

        if(x < 0 || x > 2 || y < 0 || y > 2){
            throw new Exception("cant add token")
        }

        if(tokens[x][y] == null){
            tokens[x][y] = token
        }

    }

    boolean isFull(){

        for(x in 0..2){
            for(y in 0..2){
                if(tokens[x][y] == null){
                    return false
                }
            }
        }
        return true

    }

    void print(){



        for(x in 0..2){
            def row = []
            for(y in 0..2){
                def value = tokens[x][y]?.value ?: "-"
                row << value
            }
            print(row.join("|"))
            print("\n")
        }
    }
}

