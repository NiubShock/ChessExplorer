package com.example.chessexplorer;

import java.util.ArrayList;
import java.util.List;

public class ChessRuler {

    ChessboardSquare[] chessboard;

    public ChessRuler(ChessboardSquare[] chessboardSquare){
        chessboard = chessboardSquare;
    }

    public ArrayList<Integer> getPossibleMoves(int selected_square){
        ArrayList<Integer> possible_moves = new ArrayList<>();
        switch(chessboard[selected_square].getPieceType()){
            case pawn:
                return (checkPawn(selected_square));
            case rook:
                return (checkRook(selected_square));
            case knight:
                break;
            case bishop:
                break;
            case queen:
                break;
            case king:
                break;
            case none:
                return possible_moves;
        }
        return possible_moves;
    }

    private ArrayList<Integer> checkPawn(int selected_square){
        ArrayList<Integer> possible_moves = new ArrayList<>();

        /* Check if it is white */
        if (chessboard[selected_square].getPiece().getColor() == ChessPiece.chess_colors.white){
            /* Check if it is the first move */
            if (selected_square > 47 && selected_square < 56){
                if (chessboard[selected_square - 8].isEmpty() == true){
                    possible_moves.add(selected_square - 8);
                }
                if (chessboard[selected_square - 16].isEmpty() == true) {
                    possible_moves.add(selected_square - 16);
                }
            }
            /* Not the first move */
            else {
                if (selected_square - 8 > 0){
                    if (chessboard[selected_square - 8].isEmpty() == true){
                        possible_moves.add(selected_square - 8);
                    }
                }
            }

            /* Check if it is in the last row */
            if (selected_square > 8) {
                /* Check if can capture */
                if (chessboard[selected_square - 7].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                    possible_moves.add(selected_square - 7);
                }
                if (chessboard[selected_square - 9].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                    possible_moves.add(selected_square - 9);
                }
            }

            /* TODO: Promotion check */
        }
        else {
            /* Check if it is the first move */
            if (selected_square > 7 && selected_square < 16){
                if (chessboard[selected_square + 8].isEmpty() == true){
                    possible_moves.add(selected_square + 8);
                }
                if (chessboard[selected_square + 16].isEmpty() == true){
                    possible_moves.add(selected_square + 16);
                }
            }
            /* Not the first move */
            else {
                if (selected_square + 8 < 64){
                    if (chessboard[selected_square + 8].isEmpty() == true){
                        possible_moves.add(selected_square + 8);
                    }
                }
            }

            /* Check if it is in the last row */
            if (selected_square < 56) {
                /* Check if can capture */
                if (chessboard[selected_square + 7].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                    possible_moves.add(selected_square + 7);
                }
                if (chessboard[selected_square + 9].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                    possible_moves.add(selected_square + 9);
                }
            }

            /* TODO: Promotion check */
        }

        return possible_moves;
    }

    private ArrayList<Integer> checkRook(int selected_sqare){
        ArrayList<Integer> possible_moves = new ArrayList<>();

        boolean stop_search = false;
        int     square_counter = selected_sqare;

        /* Must check on the vertical direction as long as there's no pieces, both direction */
        do{
            square_counter = square_counter + 8;

            if (square_counter < 64) {
                /* Collision with another piece */
                if (chessboard[square_counter].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                    stop_search = true;

                    /* Check if it is a capture */
                    if (chessboard[selected_sqare].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                        possible_moves.add(square_counter);
                    }
                }
                else {
                    possible_moves.add(square_counter);
                }
            }
            else {
                stop_search = true;
            }
        }while(stop_search == false);

        square_counter = selected_sqare;
        stop_search = false;

        /* Must check on the vertical direction as long as there's no pieces, both direction */
        do{
            square_counter = square_counter - 8;

            if (square_counter > 0) {
                /* Collision with another piece */
                if (chessboard[square_counter].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                    stop_search = true;

                    /* Check if it is a capture */
                    if (chessboard[selected_sqare].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                        possible_moves.add(square_counter);
                    }
                }
                else {
                    possible_moves.add(square_counter);
                }
            }
            else{
                stop_search = true;
            }
        }while(stop_search == false);

        square_counter = selected_sqare;
        stop_search = false;

        /* Must check on the vertical direction as long as there's no pieces, both direction */
        do{
            square_counter = square_counter + 1;

            int limit = (selected_sqare - selected_sqare % 8) + 8;

            if (square_counter < limit) {
                /* Collision with another piece */
                if (chessboard[square_counter].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                    stop_search = true;

                    /* Check if it is a capture */
                    if (chessboard[selected_sqare].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                        possible_moves.add(square_counter);
                    }
                }
                else {
                    possible_moves.add(square_counter);
                }
            }
            else{
                stop_search = true;
            }
        }while(stop_search == false);

        square_counter = selected_sqare;
        stop_search = false;

        /* Must check on the vertical direction as long as there's no pieces, both direction */
        do{
            square_counter = square_counter - 1;

            int limit = (selected_sqare - selected_sqare % 8);

            if (square_counter > limit) {
                /* Collision with another piece */
                if (chessboard[square_counter].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                    stop_search = true;

                    /* Check if it is a capture */
                    if (chessboard[selected_sqare].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                        possible_moves.add(square_counter);
                    }
                }
                else {
                    possible_moves.add(square_counter);
                }
            }
            else{
                stop_search = true;
            }
        }while(stop_search == false);

        return possible_moves;
    }

    public void updateChessboard(ChessboardSquare[] new_chessboard){
        chessboard = new_chessboard;
    }
}
