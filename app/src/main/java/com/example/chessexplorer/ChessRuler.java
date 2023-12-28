package com.example.chessexplorer;

import java.util.ArrayList;

public class ChessRuler {

    ChessboardSquare[]      chessboard;
    boolean                 check_detected      = false;
    ChessPiece.chess_colors color_under_check   = ChessPiece.chess_colors.white;

    class Chess_Move{
        int move_square;
        move_types move_type;
    }

    enum move_types{
        move,
        capture
    }

    public ChessRuler(ChessboardSquare[] chessboardSquare){
        chessboard = chessboardSquare;
    }

    public ArrayList<Chess_Move> getPossibleMoves(int selected_square, boolean check_persistence){
        ArrayList<Chess_Move> possible_moves = new ArrayList<>();
        switch(chessboard[selected_square].getPieceType()){
            case pawn:
                possible_moves.addAll(checkPawn(selected_square));
                break;
            case rook:
                possible_moves.addAll((checkRook(selected_square)));
                break;
            case knight:
                possible_moves.addAll((checkKnight(selected_square)));
                break;
            case bishop:
                possible_moves.addAll((checkBishop(selected_square)));
                break;
            case queen:
                possible_moves.addAll((checkQueen(selected_square)));
                break;
            case king:
                possible_moves.addAll((checkKing(selected_square)));
                break;
        }

        boolean check_still_here = false;

        /* Check for checks detected previously */
        if (check_detected == true && check_persistence == true){
            for (int i = 0; i < possible_moves.size(); i++){
                check_still_here = checkCheckPersistence(color_under_check, selected_square, possible_moves.get(i).move_square);
                if (check_still_here == true){
                    possible_moves.remove(i);
                    i--;
                }
            }
        }

        return possible_moves;
    }

    private ArrayList<Chess_Move> checkPawn(int selected_square){
        ArrayList<Chess_Move> possible_moves = new ArrayList<>();

        /* Check if it is white */
        if (chessboard[selected_square].getPiece().getColor() == ChessPiece.chess_colors.white){
            /* Check if it is the first move */
            if (selected_square > 47 && selected_square < 56){
                if (chessboard[selected_square - 8].isEmpty() == true){
                    Chess_Move chess_move = new Chess_Move();
                    chess_move.move_square = selected_square - 8;
                    chess_move.move_type = move_types.move;
                    possible_moves.add(chess_move);
                }
                if (chessboard[selected_square - 16].isEmpty() == true) {
                    Chess_Move chess_move = new Chess_Move();
                    chess_move.move_square = selected_square - 16;
                    chess_move.move_type = move_types.move;
                    possible_moves.add(chess_move);
                }
            }
            /* Not the first move */
            else {
                if (selected_square - 8 > 0){
                    if (chessboard[selected_square - 8].isEmpty() == true){
                        Chess_Move chess_move = new Chess_Move();
                        chess_move.move_square = selected_square - 8;
                        chess_move.move_type = move_types.move;
                        possible_moves.add(chess_move);
                    }
                }
            }

            /* Check if it is not in the last row */
            if (selected_square > 8) {
                /* Check if can capture */
                if (chessboard[selected_square - 7].isEmpty() == false &&
                    chessboard[selected_square - 7].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                    Chess_Move chess_move = new Chess_Move();
                    chess_move.move_square = selected_square - 7;
                    chess_move.move_type = move_types.capture;
                    possible_moves.add(chess_move);
                }
                if (chessboard[selected_square - 9].isEmpty() == false &&
                    chessboard[selected_square - 9].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                    Chess_Move chess_move = new Chess_Move();
                    chess_move.move_square = selected_square - 9;
                    chess_move.move_type = move_types.capture;
                    possible_moves.add(chess_move);
                }
            }
            /* Pawn in the last row - Possible promotion */
            else {
                /* In case of promotion return the same square as the piece doesnt move */
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = selected_square;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
        }
        else {
            /* Check if it is the first move */
            if (selected_square > 7 && selected_square < 16){
                if (chessboard[selected_square + 8].isEmpty() == true){
                    Chess_Move chess_move = new Chess_Move();
                    chess_move.move_square = selected_square + 8;
                    chess_move.move_type = move_types.move;
                    possible_moves.add(chess_move);
                }
                if (chessboard[selected_square + 16].isEmpty() == true){
                    Chess_Move chess_move = new Chess_Move();
                    chess_move.move_square = selected_square + 16;
                    chess_move.move_type = move_types.move;
                    possible_moves.add(chess_move);
                }
            }
            /* Not the first move */
            else {
                if (selected_square + 8 < 64){
                    if (chessboard[selected_square + 8].isEmpty() == true){
                        Chess_Move chess_move = new Chess_Move();
                        chess_move.move_square = selected_square + 8;
                        chess_move.move_type = move_types.move;
                        possible_moves.add(chess_move);
                    }
                }
            }

            /* Check if it is in the last row */
            if (selected_square < 56) {
                Chess_Move chess_move = new Chess_Move();
                /* Check if can capture */
                if (chessboard[selected_square + 7].isEmpty() == false &&
                    chessboard[selected_square + 7].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                    chess_move.move_square = selected_square + 7;
                    chess_move.move_type = move_types.capture;
                    possible_moves.add(chess_move);
                }
                if (chessboard[selected_square + 9].isEmpty() == false &&
                    chessboard[selected_square + 9].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                    chess_move.move_square = selected_square + 9;
                    chess_move.move_type = move_types.capture;
                    possible_moves.add(chess_move);
                }
            }
            /* Pawn in the last row - Possible promotion */
            else {
                /* In case of promotion return the same square as the piece doesnt move */
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = selected_square;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
        }

        return possible_moves;
    }

    private ArrayList<Chess_Move> checkRook(int selected_square){
        ArrayList<Chess_Move> possible_moves = new ArrayList<>();

        possible_moves.addAll(checkUP(selected_square));
        possible_moves.addAll(checkDOWN(selected_square));
        possible_moves.addAll(checkLEFT(selected_square));
        possible_moves.addAll(checkRIGHT(selected_square));

        return possible_moves;
    }

    private ArrayList<Chess_Move> checkKnight(int selected_square){
        ArrayList<Chess_Move> possible_moves = new ArrayList<>();

        int move_square;
        int limit;

        /* Check the low two moves */
        /* ----------------------- */

        move_square = selected_square + 16 - 1;
        limit = selected_square - selected_square % 8 + 16;

        if (move_square > limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        move_square = selected_square + 16 + 1;
        limit = selected_square - selected_square % 8 + 16 + 7;

        if (move_square <= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        /* Check the low two moves */
        /* -------------=--------- */

        move_square = selected_square - 16 - 1;
        limit = selected_square - selected_square % 8 - 16;

        if (move_square >= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        move_square = selected_square - 16 + 1;
        limit = selected_square - selected_square % 8 - 16 + 7;

        if (move_square <= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        /* Check the left two moves */
        /* ------------------------ */

        move_square = selected_square - 2 + 8;
        limit = selected_square - selected_square % 8 + 8;

        if (move_square >= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        move_square = selected_square - 2 - 8;
        limit = selected_square - selected_square % 8 - 8;

        if (move_square >= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        /* Check the right two moves */
        /* ------------------------- */

        move_square = selected_square + 2 + 8;
        limit = selected_square - selected_square % 8 + 15;

        if (move_square <= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        move_square = selected_square + 2 - 8;
        limit =  selected_square - selected_square % 8 - 1;

        if (move_square <= limit && (move_square >= 0 && move_square <= 63)) {
            /* Empty move */
            if (chessboard[move_square].isEmpty() == true) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture piece */
            else if (chessboard[move_square].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()) {
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = move_square;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        return possible_moves;
    }

    private ArrayList<Chess_Move> checkBishop(int selected_square){
        ArrayList<Chess_Move> possible_moves = new ArrayList<>();

        possible_moves.addAll(checkSE(selected_square));
        possible_moves.addAll(checkSO(selected_square));
        possible_moves.addAll(checkNE(selected_square));
        possible_moves.addAll(checkNO(selected_square));
        
        return possible_moves;
    }

    private ArrayList<Chess_Move> checkQueen(int selected_square){
        ArrayList<Chess_Move> possible_moves = new ArrayList<>();

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

    private ArrayList<Chess_Move> checkKing(int selected_square){
        ArrayList<Chess_Move> possible_moves = new ArrayList<>();

        int square_counter;
        int limit;

        limit = selected_square - selected_square % 8 + 8;

        square_counter = selected_square + 8 - 1;
        if (square_counter <= 63 && square_counter >= limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        square_counter = selected_square + 8;
        if (square_counter <= 63){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        limit = selected_square - selected_square % 8 + 8 + 8;
        square_counter = selected_square + 8 + 1;
        if (square_counter <= 63 && square_counter < limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        limit = selected_square - selected_square % 8 + 8;
        square_counter = selected_square + 1;
        if (square_counter <= 63 && square_counter < limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        limit = selected_square - selected_square % 8 - 8;
        square_counter = selected_square - 8 - 1;
        if (square_counter >= 0 && square_counter >= limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        square_counter = selected_square - 8;
        if (square_counter >= 0){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        limit = selected_square - selected_square % 8 - 8 + 8;
        square_counter = selected_square - 8 + 1;
        if (square_counter >= 0 && square_counter < limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        limit = selected_square - selected_square % 8;
        square_counter = selected_square - 1;
        if (square_counter >= 0 && selected_square > limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.move;
                possible_moves.add(chess_move);
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                Chess_Move chess_move = new Chess_Move();
                chess_move.move_square = square_counter;
                chess_move.move_type = move_types.capture;
                possible_moves.add(chess_move);
            }
        }

        return possible_moves;
    }
    
    private ArrayList<Chess_Move> checkUP(int selected_square){
        ArrayList<Chess_Move> possible_moves = new ArrayList<>();
        
        boolean stop_search = false;
        int     square_counter = selected_square;

        /* Must check on the vertical direction as long as there's no pieces, both direction */
        do{
            square_counter = square_counter + 8;

            /* Check if out of range */
            if (square_counter < 64) {
                /* Collision with another piece */
                if (chessboard[square_counter].isEmpty() == false) {
                    stop_search = true;

                    /* Check if it is a capture */
                    if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                        Chess_Move chess_move = new Chess_Move();
                        chess_move.move_square = square_counter;
                        chess_move.move_type = move_types.capture;
                        possible_moves.add(chess_move);
                    }
                }
                /* Only movement available */
                else {
                    Chess_Move chess_move = new Chess_Move();
                    chess_move.move_square = square_counter;
                    chess_move.move_type = move_types.move;
                    possible_moves.add(chess_move);
                }
            }
            else {
                stop_search = true;
            }
        }while(stop_search == false);
        
        return possible_moves;
    }
    
    private ArrayList<Chess_Move> checkDOWN(int selected_square){

        ArrayList<Chess_Move> possible_moves = new ArrayList<>();

        boolean stop_search = false;
        int     square_counter = selected_square;

        /* Must check on the vertical direction as long as there's no pieces, both direction */
        do{
            square_counter = square_counter - 8;

            /* Check if out range */
            if (square_counter > 0) {
                /* Collision with another piece */
                if (chessboard[square_counter].isEmpty() == false) {
                    stop_search = true;

                    /* Check if it is a capture */
                    if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                        Chess_Move chess_move = new Chess_Move();
                        chess_move.move_square = square_counter;
                        chess_move.move_type = move_types.capture;
                        possible_moves.add(chess_move);
                    }
                }
                /* Movement available */
                else {
                    Chess_Move chess_move = new Chess_Move();
                    chess_move.move_square = square_counter;
                    chess_move.move_type = move_types.move;
                    possible_moves.add(chess_move);
                }
            }
            else{
                stop_search = true;
            }
        }while(stop_search == false);
        
        return possible_moves;
    }
    
    private ArrayList<Chess_Move> checkLEFT(int selected_square){

        ArrayList<Chess_Move> possible_moves = new ArrayList<>();

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
                if (chessboard[square_counter].isEmpty() == false) {
                    stop_search = true;

                    /* Check if it is a capture */
                    if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                        Chess_Move chess_move = new Chess_Move();
                        chess_move.move_square = square_counter;
                        chess_move.move_type = move_types.capture;
                        possible_moves.add(chess_move);
                    }
                }
                /* Movement available */
                else {
                    Chess_Move chess_move = new Chess_Move();
                    chess_move.move_square = square_counter;
                    chess_move.move_type = move_types.move;
                    possible_moves.add(chess_move);
                }
            }
            else{
                stop_search = true;
            }
        }while(stop_search == false);

        return possible_moves;
    }
    
    private ArrayList<Chess_Move> checkRIGHT(int selected_square){

        ArrayList<Chess_Move> possible_moves = new ArrayList<>();

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
                    if (chessboard[square_counter].isEmpty() == false) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            Chess_Move chess_move = new Chess_Move();
                            chess_move.move_square = square_counter;
                            chess_move.move_type = move_types.capture;
                            possible_moves.add(chess_move);
                        }
                    }
                    /* Movement available */
                    else {
                        Chess_Move chess_move = new Chess_Move();
                        chess_move.move_square = square_counter;
                        chess_move.move_type = move_types.move;
                        possible_moves.add(chess_move);
                    }
                }
                else{
                    stop_search = true;
                }
            }while(stop_search == false);
        }
        
        return possible_moves;
    }

    private ArrayList<Chess_Move> checkSE(int selected_square){
        ArrayList<Chess_Move> possible_moves = new ArrayList<>();

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
                    if (chessboard[square_counter].isEmpty() == false) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            Chess_Move chess_move = new Chess_Move();
                            chess_move.move_square = square_counter;
                            chess_move.move_type = move_types.capture;
                            possible_moves.add(chess_move);
                        }
                    }
                    /* Movement available */
                    else {
                        Chess_Move chess_move = new Chess_Move();
                        chess_move.move_square = square_counter;
                        chess_move.move_type = move_types.move;
                        possible_moves.add(chess_move);
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

    private ArrayList<Chess_Move> checkSO(int selected_square){
        ArrayList<Chess_Move> possible_moves = new ArrayList<>();

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
                    if (chessboard[square_counter].isEmpty() == false) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            Chess_Move chess_move = new Chess_Move();
                            chess_move.move_square = square_counter;
                            chess_move.move_type = move_types.capture;
                            possible_moves.add(chess_move);
                        }
                    }
                    /* Movement available */
                    else {
                        Chess_Move chess_move = new Chess_Move();
                        chess_move.move_square = square_counter;
                        chess_move.move_type = move_types.move;
                        possible_moves.add(chess_move);
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

    private ArrayList<Chess_Move> checkNE(int selected_square){
        ArrayList<Chess_Move> possible_moves = new ArrayList<>();

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
                    if (chessboard[square_counter].isEmpty() == false) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            Chess_Move chess_move = new Chess_Move();
                            chess_move.move_square = square_counter;
                            chess_move.move_type = move_types.capture;
                            possible_moves.add(chess_move);
                        }
                    }
                    /* Movement available */
                    else {
                        Chess_Move chess_move = new Chess_Move();
                        chess_move.move_square = square_counter;
                        chess_move.move_type = move_types.move;
                        possible_moves.add(chess_move);
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

    private ArrayList<Chess_Move> checkNO(int selected_square){
        ArrayList<Chess_Move> possible_moves = new ArrayList<>();

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
                    if (chessboard[square_counter].isEmpty() == false) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            Chess_Move chess_move = new Chess_Move();
                            chess_move.move_square = square_counter;
                            chess_move.move_type = move_types.capture;
                            possible_moves.add(chess_move);
                        }
                    }
                    /* Movement available */
                    else {
                        Chess_Move chess_move = new Chess_Move();
                        chess_move.move_square = square_counter;
                        chess_move.move_type = move_types.move;
                        possible_moves.add(chess_move);
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

    public int checkChecks(ChessPiece.chess_colors color){
        /* Check if one piece can attack the enemy king */
        for (int i = 0; i < 64; i++){
            /* Check the pieces of the same color */
            if (chessboard[i].getPiece().getColor() != color){
                ArrayList<ChessRuler.Chess_Move> check_move = new ArrayList<>(getPossibleMoves(i, false));

                /* Check all the moves */
                for (int j = 0; j < check_move.size(); j++){
                    /* Check the capture move */
                    if (check_move.get(j).move_type == ChessRuler.move_types.capture){
                        /* Check if attacks the king */
                        if (chessboard[check_move.get(j).move_square].getPiece().getPieceType() == ChessPiece.pieces_number.king){
                            check_detected = true;
                            color_under_check = chessboard[i].getPiece().getColor();
                            return check_move.get(j).move_square;
                        }
                    }
                }
            }
        }

        return -1;
    }

    public void resetCheckDetected(){
        check_detected = false;
    }

    private boolean checkCheckPersistence(ChessPiece.chess_colors color, int selected_square, int destination_square){
        boolean check_detected = false;

        /* Make the move */
        ChessPiece piece_mov = chessboard[destination_square].getPiece();
        chessboard[destination_square].loadPiece(chessboard[selected_square].getPiece());
        chessboard[selected_square].emptyPiece();

        /* Check if one piece can attack the enemy king */
        for (int i = 0; i < 64; i++){
            /* Check the pieces of the same color */
            if (chessboard[i].getPiece().getColor() == color){
                ArrayList<ChessRuler.Chess_Move> check_move = new ArrayList<>(getPossibleMoves(i, false));

                /* Check all the moves */
                for (int j = 0; j < check_move.size(); j++){
                    /* Check the capture move */
                    if (check_move.get(j).move_type == ChessRuler.move_types.capture){
                        /* Check if attacks the king */
                        if (chessboard[check_move.get(j).move_square].getPiece().getPieceType() == ChessPiece.pieces_number.king){
                            check_detected = true;
                        }
                    }
                }
            }
        }

        /* Undo the move */
        chessboard[selected_square].loadPiece(chessboard[destination_square].getPiece());
        chessboard[destination_square].loadPiece(piece_mov);

        /* Return the result */
        return check_detected;
    }
}
