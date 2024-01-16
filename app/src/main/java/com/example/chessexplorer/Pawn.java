package com.example.chessexplorer;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;

public class Pawn extends ChessPiece{

    protected boolean piece_moved   = false;

    public Pawn(Bitmap bmp, Rect rect_size, chess_colors color) {
        super(bmp, pieces_number.pawn, rect_size, color);
    }

    @Override
    public ArrayList<ChessMoves> getPossibleMoves(int selected_square, ChessboardSquare[] chessboard) {

        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

        /* Check if it is white */
        if (color == ChessPiece.chess_colors.white){
            /* Check if it is the first move */
            if (selected_square > 47 && selected_square < 56){
                if (chessboard[selected_square - 8].isEmpty() == true){
                    possible_moves.add(new ChessMoves(selected_square - 8, ChessMoves.move_types.move));
                }
                if (chessboard[selected_square - 16].isEmpty() == true) {
                    possible_moves.add(new ChessMoves(selected_square - 16, ChessMoves.move_types.move));
                }
            }
            /* Not the first move */
            else {
                if (selected_square - 8 > 0){
                    if (chessboard[selected_square - 8].isEmpty() == true){
                        possible_moves.add(new ChessMoves(selected_square - 8, ChessMoves.move_types.move));
                    }
                }
            }

            /* Check if it is not in the last row */
            if (selected_square > 8) {
                /* Check if can capture */
                if (chessboard[selected_square - 7].isEmpty() == false &&
                        chessboard[selected_square - 7].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                    possible_moves.add(new ChessMoves(selected_square - 7, ChessMoves.move_types.capture));
                }
                if (chessboard[selected_square - 9].isEmpty() == false &&
                        chessboard[selected_square - 9].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                    possible_moves.add(new ChessMoves(selected_square - 9, ChessMoves.move_types.capture));
                }
            }
            /* Pawn in the last row - Possible promotion */
            else {
                /* In case of promotion return the same square as the piece doesnt move */
                possible_moves.add(new ChessMoves(selected_square, ChessMoves.move_types.move));
            }
        }
        else {
            /* Check if it is the first move */
            if (selected_square > 7 && selected_square < 16){
                if (chessboard[selected_square + 8].isEmpty() == true){
                    possible_moves.add(new ChessMoves(selected_square + 8, ChessMoves.move_types.move));
                }
                if (chessboard[selected_square + 16].isEmpty() == true){
                    possible_moves.add(new ChessMoves(selected_square + 16, ChessMoves.move_types.move));
                }
            }
            /* Not the first move */
            else {
                if (selected_square + 8 < 64){
                    if (chessboard[selected_square + 8].isEmpty() == true){
                        possible_moves.add(new ChessMoves(selected_square + 8, ChessMoves.move_types.move));
                    }
                }
            }

            /* Check if it is in the last row */
            if (selected_square < 56) {
                /* Check if can capture */
                if (chessboard[selected_square + 7].isEmpty() == false &&
                        chessboard[selected_square + 7].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                    possible_moves.add(new ChessMoves(selected_square + 7, ChessMoves.move_types.capture));
                }
                if (chessboard[selected_square + 9].isEmpty() == false &&
                        chessboard[selected_square + 9].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                    possible_moves.add(new ChessMoves(selected_square + 9, ChessMoves.move_types.capture));
                }
            }
            /* Pawn in the last row - Possible promotion */
            else {
                /* In case of promotion return the same square as the piece doesnt move */
                possible_moves.add(new ChessMoves(selected_square, ChessMoves.move_types.move));
            }
        }

        return possible_moves;
    }

    @Override
    public void moveTo(int selected_square) {

    }


}
