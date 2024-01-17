package com.example.chessexplorer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

public class Rook extends ChessPiece{

    protected boolean       piece_moved = false;

    public Rook(Bitmap bmp, Rect rect_size, chess_colors color) {
        super(bmp, pieces_number.rook, rect_size, color);
    }

    @Override
    public ArrayList<ChessMoves> getPossibleMoves(int selected_square, ChessboardSquare[] chessboard) {
        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

        possible_moves.addAll(checkUP(selected_square,   chessboard));
        possible_moves.addAll(checkDOWN(selected_square, chessboard));
        possible_moves.addAll(checkLEFT(selected_square, chessboard));
        possible_moves.addAll(checkRIGHT(selected_square,chessboard));

        return possible_moves;
    }

    @Override
    public void moveTo(int selected_square, int new_move_square, ChessboardSquare[] chessboard, Canvas canvas) {
        chessboard[new_move_square].loadPiece(chessboard[selected_square].getPiece());
        chessboard[new_move_square].drawSquare(canvas);

        chessboard[selected_square].emptyPiece();
        chessboard[selected_square].drawSquare(canvas);

        piece_moved = true;
    }

    @Override
    public boolean checkPawnPromotion(int selected_square, ChessboardSquare[] chessboard){
        return false;
    }
}
