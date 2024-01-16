package com.example.chessexplorer;

public class ChessMoves {

    enum move_types{
        move,
        capture
    }

    int         move_square;
    move_types  move_type;

    public ChessMoves(int move_square, move_types move_type) {
        this.move_square = move_square;
        this.move_type = move_type;
    }
}
