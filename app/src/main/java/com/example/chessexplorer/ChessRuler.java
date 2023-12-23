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
                break;
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
        ChessPiece piece = chessboard[selected_square].getPiece();
        ArrayList<Integer> possible_moves = new ArrayList<>();

        /* Check if it is white */
        if (piece.getColor() == ChessPiece.chess_colors.white){
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
                    if (chessboard[selected_square - 8].isEmpty() == true){
                        possible_moves.add(selected_square - 8);
                    }
                }
            }

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

    public void updateChessboard(ChessboardSquare[] new_chessboard){
        chessboard = new_chessboard;
    }
}
