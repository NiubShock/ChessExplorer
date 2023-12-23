package com.example.chessexplorer;

import java.util.List;

public class ChessRuler {

    ChessboardSquare[] chessboard;

    public ChessRuler(ChessboardSquare[] chessboardSquare){
        chessboard = chessboardSquare;
    }

    public int[] getPossibleMoves(int selected_square){
        int[] possible_moves;
        switch(chessboard[selected_square].getPieceType()){
            case pawn:
                possible_moves = checkPawn(selected_square);
                return possible_moves;
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
                possible_moves = new int[1];
                possible_moves[0] = -1;
                return possible_moves;
        }

        possible_moves = new int[1];
        possible_moves[0] = -1;
        return possible_moves;
    }

    private int[] checkPawn(int selected_square){
        ChessPiece piece = chessboard[selected_square].getPiece();
        int[] moves;

        /* Check if it is white */
        if (piece.getColor() == ChessPiece.chess_colors.white){
            /* Check if it is the first move */
            if (selected_square > 47 && selected_square < 56){
                moves = new int[2];
                moves[0] = selected_square - 8;
                moves[1] = selected_square - 16;
            }
            /* Not the first move */
            else {
                moves = new int[1];
                moves[0] = selected_square - 8;
            }
        }
        else {
            /* Check if it is the first move */
            if (selected_square > 7 && selected_square < 16){
                moves = new int[2];
                moves[0] = selected_square + 8;
                moves[1] = selected_square + 16;
            }
            /* Not the first move */
            else {
                moves = new int[1];
                moves[0] = selected_square + 8;
            }
        }

        return moves;
    }

    public void updateChessboard(ChessboardSquare[] new_chessboard){
        chessboard = new_chessboard;
    }
}
