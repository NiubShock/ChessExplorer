package com.example.chessexplorer;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;

public class NBQ_Piece extends ChessPiece{

    public NBQ_Piece(Bitmap bmp, pieces_number piece_type, Rect rect_size, chess_colors color) {
        super(bmp, piece_type, rect_size, color);
    }

    @Override
    public ArrayList<ChessMoves> getPossibleMoves(int selected_square, ChessboardSquare[] chessboard) {

        switch (piece_type){
            case knight:
                return checkKnight(selected_square, chessboard);
            case bishop:
                return checkBishop(selected_square, chessboard);
            case queen:
                return checkQueen(selected_square, chessboard);
        }

        return null;
    }

    @Override
    public void moveTo(int selected_square) {

    }

    private ArrayList<ChessMoves> checkBishop(int selected_square, ChessboardSquare[] chessboard){
        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

        possible_moves.addAll(checkSE(selected_square, chessboard));
        possible_moves.addAll(checkSO(selected_square, chessboard));
        possible_moves.addAll(checkNE(selected_square, chessboard));
        possible_moves.addAll(checkNO(selected_square, chessboard));

        return possible_moves;
    }

    private ArrayList<ChessMoves> checkQueen(int selected_square, ChessboardSquare[] chessboard){
        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

        possible_moves.addAll(checkUP(selected_square,   chessboard));
        possible_moves.addAll(checkDOWN(selected_square, chessboard));
        possible_moves.addAll(checkLEFT(selected_square, chessboard));
        possible_moves.addAll(checkRIGHT(selected_square,chessboard));

        possible_moves.addAll(checkNO(selected_square, chessboard));
        possible_moves.addAll(checkNE(selected_square, chessboard));
        possible_moves.addAll(checkSO(selected_square, chessboard));
        possible_moves.addAll(checkSE(selected_square, chessboard));

        return possible_moves;
    }

    private ArrayList<ChessMoves> checkKnight(int selected_square, ChessboardSquare[] chessboard){
        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

        int move_square;
        int limit;

        /* Check the low two moves */
        /* ----------------------- */

        move_square = selected_square + 16 - 1;
        limit = selected_square - selected_square % 8 + 16;

        if (move_square > limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.move));
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.capture));
            }
        }

        move_square = selected_square + 16 + 1;
        limit = selected_square - selected_square % 8 + 16 + 7;

        if (move_square <= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.move));
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.capture));
            }
        }

        /* Check the low two moves */
        /* -------------=--------- */

        move_square = selected_square - 16 - 1;
        limit = selected_square - selected_square % 8 - 16;

        if (move_square >= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.move));
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.capture));
            }
        }

        move_square = selected_square - 16 + 1;
        limit = selected_square - selected_square % 8 - 16 + 7;

        if (move_square <= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.move));
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.capture));
            }
        }

        /* Check the left two moves */
        /* ------------------------ */

        move_square = selected_square - 2 + 8;
        limit = selected_square - selected_square % 8 + 8;

        if (move_square >= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.move));
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.capture));
            }
        }

        move_square = selected_square - 2 - 8;
        limit = selected_square - selected_square % 8 - 8;

        if (move_square >= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.move));
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.capture));
            }
        }

        /* Check the right two moves */
        /* ------------------------- */

        move_square = selected_square + 2 + 8;
        limit = selected_square - selected_square % 8 + 15;

        if (move_square <= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.move));
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.capture));
            }
        }

        move_square = selected_square + 2 - 8;
        limit =  selected_square - selected_square % 8 - 1;

        if (move_square <= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.move));
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(new ChessMoves(move_square, ChessMoves.move_types.capture));
            }
        }

        return possible_moves;
    }
}
