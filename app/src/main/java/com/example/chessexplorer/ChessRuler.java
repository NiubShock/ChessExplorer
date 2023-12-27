package com.example.chessexplorer;

import java.lang.reflect.Array;
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
                return (checkKnight(selected_square));
            case bishop:
                return (checkBishop(selected_square));
            case queen:
                return (checkQueen(selected_square));
            case king:
                return (checkKing(selected_square));
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

            /* Check if it is not in the last row */
            if (selected_square > 8) {
                /* Check if can capture */
                if (chessboard[selected_square - 7].getPiece().getPieceType() != ChessPiece.pieces_number.none &&
                    chessboard[selected_square - 7].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                    possible_moves.add(selected_square - 7);
                }
                if (chessboard[selected_square - 9].getPiece().getPieceType() != ChessPiece.pieces_number.none &&
                    chessboard[selected_square - 9].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                    possible_moves.add(selected_square - 9);
                }
            }
            /* Pawn in the last row - Possible promotion */
            else {
                /* In case of promotion return the same square as the piece doesnt move */
                possible_moves.add(selected_square);
            }
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
                if (chessboard[selected_square + 7].getPiece().getPieceType() != ChessPiece.pieces_number.none &&
                    chessboard[selected_square + 7].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                    possible_moves.add(selected_square + 7);
                }
                if (chessboard[selected_square + 9].getPiece().getPieceType() != ChessPiece.pieces_number.none &&
                    chessboard[selected_square + 9].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                    possible_moves.add(selected_square + 9);
                }
            }
            /* Pawn in the last row - Possible promotion */
            else {
                /* In case of promotion return the same square as the piece doesnt move */
                possible_moves.add(selected_square);
            }
        }

        return possible_moves;
    }

    private ArrayList<Integer> checkRook(int selected_square){
        ArrayList<Integer> possible_moves = new ArrayList<>();

        possible_moves.addAll(checkUP(selected_square));
        possible_moves.addAll(checkDOWN(selected_square));
        possible_moves.addAll(checkLEFT(selected_square));
        possible_moves.addAll(checkRIGHT(selected_square));

        return possible_moves;
    }

    private ArrayList<Integer> checkKnight(int selected_square){
        ArrayList<Integer> possible_moves = new ArrayList<>();
        int move_square;
        int limit;

        /* Check the low two moves */
        /* ----------------------- */

        move_square = selected_square + 16 - 1;
        limit = selected_square - selected_square % 8 + 16;

        if (move_square > limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].getPiece().getPieceType() == ChessPiece.pieces_number.none) {
                possible_moves.add(move_square);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(move_square);
            }
        }

        move_square = selected_square + 16 + 1;
        limit = selected_square - selected_square % 8 + 16 + 7;

        if (move_square <= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].getPiece().getPieceType() == ChessPiece.pieces_number.none) {
                possible_moves.add(move_square);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(move_square);
            }
        }

        /* Check the low two moves */
        /* -------------=--------- */

        move_square = selected_square - 16 - 1;
        limit = selected_square - selected_square % 8 - 16;

        if (move_square >= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].getPiece().getPieceType() == ChessPiece.pieces_number.none) {
                possible_moves.add(move_square);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(move_square);
            }
        }

        move_square = selected_square - 16 + 1;
        limit = selected_square - selected_square % 8 - 16 + 7;

        if (move_square <= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].getPiece().getPieceType() == ChessPiece.pieces_number.none) {
                possible_moves.add(move_square);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(move_square);
            }
        }

        /* Check the left two moves */
        /* ------------------------ */

        move_square = selected_square - 2 + 8;
        limit = selected_square - selected_square % 8 + 8;

        if (move_square >= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].getPiece().getPieceType() == ChessPiece.pieces_number.none) {
                possible_moves.add(move_square);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(move_square);
            }
        }

        move_square = selected_square - 2 - 8;
        limit = selected_square - selected_square % 8 - 8;

        if (move_square >= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].getPiece().getPieceType() == ChessPiece.pieces_number.none) {
                possible_moves.add(move_square);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(move_square);
            }
        }

        /* Check the right two moves */
        /* ------------------------- */

        move_square = selected_square + 2 + 8;
        limit = selected_square - selected_square % 8 + 15;

        if (move_square <= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].getPiece().getPieceType() == ChessPiece.pieces_number.none) {
                possible_moves.add(move_square);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(move_square);
            }
        }

        move_square = selected_square + 2 - 8;
        limit =  selected_square - selected_square % 8 - 1;

        if (move_square <= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].getPiece().getPieceType() == ChessPiece.pieces_number.none) {
                possible_moves.add(move_square);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                possible_moves.add(move_square);
            }
        }

        return possible_moves;
    }

    private ArrayList<Integer> checkBishop(int selected_square){
        ArrayList<Integer> possible_moves = new ArrayList<>();

        possible_moves.addAll(checkSE(selected_square));
        possible_moves.addAll(checkSO(selected_square));
        possible_moves.addAll(checkNE(selected_square));
        possible_moves.addAll(checkNO(selected_square));
        
        return possible_moves;
    }

    private ArrayList<Integer> checkQueen(int selected_square){
        ArrayList<Integer> possible_moves = new ArrayList<>();

        possible_moves.addAll(checkUP(selected_square));
        possible_moves.addAll(checkDOWN(selected_square));
        possible_moves.addAll(checkLEFT(selected_square));
        possible_moves.addAll(checkRIGHT(selected_square));

        possible_moves.addAll(checkNO(selected_square));
        possible_moves.addAll(checkNE(selected_square));
        possible_moves.addAll(checkSO(selected_square));
        possible_moves.addAll(checkSE(selected_square));

        return possible_moves;
    }

    private ArrayList<Integer> checkKing(int selected_square){
        ArrayList<Integer> possible_moves = new ArrayList<>();

        int square_counter;
        int limit;

        limit = selected_square - selected_square % 8 + 8;

        square_counter = selected_square + 8 - 1;
        if (square_counter <= 63 && square_counter >= limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(square_counter);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(square_counter);
            }
        }

        square_counter = selected_square + 8;
        if (square_counter <= 63){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(square_counter);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(square_counter);
            }
        }

        limit = selected_square - selected_square % 8 + 8 + 8;
        square_counter = selected_square + 8 + 1;
        if (square_counter <= 63 && square_counter < limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(square_counter);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(square_counter);
            }
        }

        limit = selected_square - selected_square % 8 + 8;
        square_counter = selected_square + 1;
        if (square_counter <= 63 && square_counter < limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(square_counter);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(square_counter);
            }
        }

        limit = selected_square - selected_square % 8 - 8;
        square_counter = selected_square - 8 - 1;
        if (square_counter >= 0 && square_counter >= limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(square_counter);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(square_counter);
            }
        }

        square_counter = selected_square - 8;
        if (square_counter >= 0){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(square_counter);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(square_counter);
            }
        }

        limit = selected_square - selected_square % 8 - 8 + 8;
        square_counter = selected_square - 8 + 1;
        if (square_counter >= 0 && square_counter < limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(square_counter);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(square_counter);
            }
        }

        limit = selected_square - selected_square % 8;
        square_counter = selected_square - 1;
        if (square_counter >= 0 && selected_square > limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(square_counter);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(square_counter);
            }
        }

        return possible_moves;
    }
    
    private ArrayList<Integer> checkUP(int selected_square){
        ArrayList<Integer> possible_moves = new ArrayList<>();
        
        boolean stop_search = false;
        int     square_counter = selected_square;

        /* Must check on the vertical direction as long as there's no pieces, both direction */
        do{
            square_counter = square_counter + 8;

            /* Check if out of range */
            if (square_counter < 64) {
                /* Collision with another piece */
                if (chessboard[square_counter].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                    stop_search = true;

                    /* Check if it is a capture */
                    if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                        possible_moves.add(square_counter);
                    }
                }
                /* Only movement available */
                else {
                    possible_moves.add(square_counter);
                }
            }
            else {
                stop_search = true;
            }
        }while(stop_search == false);
        
        return possible_moves;
    }
    
    private ArrayList<Integer> checkDOWN(int selected_square){

        ArrayList<Integer> possible_moves = new ArrayList<>();

        boolean stop_search = false;
        int     square_counter = selected_square;

        /* Must check on the vertical direction as long as there's no pieces, both direction */
        do{
            square_counter = square_counter - 8;

            /* Check if out range */
            if (square_counter > 0) {
                /* Collision with another piece */
                if (chessboard[square_counter].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                    stop_search = true;

                    /* Check if it is a capture */
                    if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                        possible_moves.add(square_counter);
                    }
                }
                /* Movement available */
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
    
    private ArrayList<Integer> checkLEFT(int selected_square){

        ArrayList<Integer> possible_moves = new ArrayList<>();

        boolean stop_search = false;
        int     square_counter = selected_square;

        /* Must check on the vertical direction as long as there's no pieces, both direction */
        do{
            square_counter = square_counter - 1;

            /* Check the horizontal limit */
            int limit = (selected_square - selected_square % 8);

            /* Check if out of range */
            if (square_counter >= limit) {
                /* Collision with another piece */
                if (chessboard[square_counter].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                    stop_search = true;

                    /* Check if it is a capture */
                    if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                        possible_moves.add(square_counter);
                    }
                }
                /* Movement available */
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
    
    private ArrayList<Integer> checkRIGHT(int selected_square){

        ArrayList<Integer> possible_moves = new ArrayList<>();

        boolean stop_search = false;
        int     square_counter = selected_square;

        /* Check if the selected square is the last row */
        if ((selected_square + 1) % 8 == 0){
            stop_search = true;
        }
        else {
            /* Must check on the vertical direction as long as there's no pieces, both direction */
            do{
                square_counter = square_counter + 1;

                /* Calculate the limit on the horizontal */
                int limit = (selected_square - selected_square % 8) + 8;

                /* Check if out of limit */
                if (square_counter < limit) {
                    /* Collision with another piece */
                    if (chessboard[square_counter].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            possible_moves.add(square_counter);
                        }
                    }
                    /* Movement available */
                    else {
                        possible_moves.add(square_counter);
                    }
                }
                else{
                    stop_search = true;
                }
            }while(stop_search == false);
        }
        
        return possible_moves;
    }

    private ArrayList<Integer> checkSE(int selected_square){
        ArrayList<Integer> possible_moves = new ArrayList<>();

        boolean stop_search = false;
        int     square_counter = selected_square;

        if ((selected_square + 1) % 8 != 0){
            /* Must check on the vertical direction as long as there's no pieces, both direction */
            do{
                square_counter = square_counter + 1 + 8;

                /* Calculate the limit on the horizontal */
                int limit = (square_counter - square_counter % 8) + 7;

                /* Check if out of limit */
                if (square_counter <= limit && square_counter < 64) {
                    /* Collision with another piece */
                    if (chessboard[square_counter].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            possible_moves.add(square_counter);
                        }
                    }
                    /* Movement available */
                    else {
                        possible_moves.add(square_counter);
                    }

                    if (square_counter == limit){
                        stop_search = true;
                    }
                }
                else{
                    stop_search = true;
                }
            }while(stop_search == false);
        }

        return possible_moves;
    }

    private ArrayList<Integer> checkSO(int selected_square){
        ArrayList<Integer> possible_moves = new ArrayList<>();

        boolean stop_search = false;
        int     square_counter = selected_square;

        if (selected_square % 8 != 0){
            /* Must check on the vertical direction as long as there's no pieces, both direction */
            do{
                square_counter = square_counter - 1 + 8;

                /* Calculate the limit on the horizontal */
                int limit = (square_counter - square_counter % 8);

                /* Check if out of limit */
                if (square_counter >= limit && square_counter < 64) {
                    /* Collision with another piece */
                    if (chessboard[square_counter].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            possible_moves.add(square_counter);
                        }
                    }
                    /* Movement available */
                    else {
                        possible_moves.add(square_counter);
                    }

                    if (square_counter == limit){
                        stop_search = true;
                    }
                }
                else{
                    stop_search = true;
                }
            }while(stop_search == false);
        }

        return possible_moves;
    }

    private ArrayList<Integer> checkNE(int selected_square){
        ArrayList<Integer> possible_moves = new ArrayList<>();

        boolean stop_search = false;
        int     square_counter = selected_square;

        /* Check if the selected square is the last row */
        if ((selected_square + 1) % 8 != 0) {
            /* Must check on the vertical direction as long as there's no pieces, both direction */
            do {
                square_counter = square_counter + 1 - 8;

                /* Calculate the limit on the horizontal */
                int limit = (square_counter - square_counter % 8) + 7;

                /* Check if out of limit */
                if (square_counter <= limit && square_counter >= 0) {
                    /* Collision with another piece */
                    if (chessboard[square_counter].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            possible_moves.add(square_counter);
                        }
                    }
                    /* Movement available */
                    else {
                        possible_moves.add(square_counter);
                    }

                    if (square_counter == limit) {
                        stop_search = true;
                    }
                } else {
                    stop_search = true;
                }
            } while (stop_search == false);
        }
        return possible_moves;
    }

    private ArrayList<Integer> checkNO(int selected_square){
        ArrayList<Integer> possible_moves = new ArrayList<>();

        boolean stop_search = false;
        int     square_counter = selected_square;

        if (selected_square % 8 != 0){
            /* Must check on the vertical direction as long as there's no pieces, both direction */
            do{
                square_counter = square_counter - 1 - 8;

                /* Calculate the limit on the horizontal */
                int limit = (square_counter - square_counter % 8);

                /* Check if out of limit */
                if (square_counter >= limit && square_counter >= 0) {
                    /* Collision with another piece */
                    if (chessboard[square_counter].getPiece().getPieceType() != ChessPiece.pieces_number.none) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            possible_moves.add(square_counter);
                        }
                    }
                    /* Movement available */
                    else {
                        possible_moves.add(square_counter);
                    }

                    if (square_counter == limit){
                        stop_search = true;
                    }
                }
                else{
                    stop_search = true;
                }
            }while(stop_search == false);
        }

        return possible_moves;
    }
    
    
    public void updateChessboard(ChessboardSquare[] new_chessboard){
        chessboard = new_chessboard;
    }
}
