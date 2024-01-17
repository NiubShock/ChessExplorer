package com.example.chessexplorer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

interface PieceOption {

    ArrayList<ChessMoves> getPossibleMoves(int selected_square, ChessboardSquare[] chessboard);
    void moveTo(int selected_square, int new_move_square, ChessboardSquare[] chessboard, Canvas canvas);

    boolean checkPawnPromotion(int selected_square, ChessboardSquare[] chessboardSquares);

    default ArrayList<ChessMoves> checkUP(int selected_square, ChessboardSquare[] chessboard){
        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

        boolean stop_search = false;
        int     square_counter = selected_square;

        /* Must check on the vertical direction as long as there's no pieces, both direction */
        do{
            square_counter = square_counter + 8;

            /* Check if out of range */
            if (square_counter < 64) {
                /* Collision with another piece */
                if (!chessboard[square_counter].isEmpty()) {
                    stop_search = true;

                    /* Check if it is a capture */
                    if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                        possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
                    }
                }
                /* Only movement available */
                else {
                    possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
                }
            }
            else {
                stop_search = true;
            }
        }while(!stop_search);

        return possible_moves;
    }

    default ArrayList<ChessMoves> checkDOWN(int selected_square, ChessboardSquare[] chessboard){

        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

        boolean stop_search = false;
        int     square_counter = selected_square;

        /* Must check on the vertical direction as long as there's no pieces, both direction */
        do{
            square_counter = square_counter - 8;

            /* Check if out range */
            if (square_counter > 0) {
                /* Collision with another piece */
                if (!chessboard[square_counter].isEmpty()) {
                    stop_search = true;

                    /* Check if it is a capture */
                    if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                        possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
                    }
                }
                /* Movement available */
                else {
                    possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
                }
            }
            else{
                stop_search = true;
            }
        }while(!stop_search);

        return possible_moves;
    }

    default ArrayList<ChessMoves> checkLEFT(int selected_square, ChessboardSquare[] chessboard){

        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

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
                if (!chessboard[square_counter].isEmpty()) {
                    stop_search = true;

                    /* Check if it is a capture */
                    if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                        possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
                    }
                }
                /* Movement available */
                else {
                    possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
                }
            }
            else{
                stop_search = true;
            }
        }while(!stop_search);

        return possible_moves;
    }

    default ArrayList<ChessMoves> checkRIGHT(int selected_square, ChessboardSquare[] chessboard){

        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

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
                    if (!chessboard[square_counter].isEmpty()) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
                        }
                    }
                    /* Movement available */
                    else {
                        possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
                    }
                }
                else{
                    stop_search = true;
                }
            }while(!stop_search);
        }

        return possible_moves;
    }

    default ArrayList<ChessMoves> checkSE(int selected_square, ChessboardSquare[] chessboard){
        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

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
                    if (!chessboard[square_counter].isEmpty()) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
                        }
                    }
                    /* Movement available */
                    else {
                        possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
                    }

                    if (square_counter == limit){
                        stop_search = true;
                    }
                }
                else{
                    stop_search = true;
                }
            }while(!stop_search);
        }

        return possible_moves;
    }

    default ArrayList<ChessMoves> checkSO(int selected_square, ChessboardSquare[] chessboard){
        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

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
                    if (!chessboard[square_counter].isEmpty()) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
                        }
                    }
                    /* Movement available */
                    else {
                        possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
                    }

                    if (square_counter == limit){
                        stop_search = true;
                    }
                }
                else{
                    stop_search = true;
                }
            }while(!stop_search);
        }

        return possible_moves;
    }

    default ArrayList<ChessMoves> checkNE(int selected_square, ChessboardSquare[] chessboard){
        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

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
                    if (!chessboard[square_counter].isEmpty()) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
                        }
                    }
                    /* Movement available */
                    else {
                        possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
                    }

                    if (square_counter == limit) {
                        stop_search = true;
                    }
                } else {
                    stop_search = true;
                }
            } while (!stop_search);
        }
        return possible_moves;
    }

    default ArrayList<ChessMoves> checkNO(int selected_square, ChessboardSquare[] chessboard){
        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

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
                    if (!chessboard[square_counter].isEmpty()) {
                        stop_search = true;

                        /* Check if it is a capture */
                        if (chessboard[selected_square].getPiece().getColor() != chessboard[square_counter].getPiece().getColor()) {
                            possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
                        }
                    }
                    /* Movement available */
                    else {
                        possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
                    }

                    if (square_counter == limit){
                        stop_search = true;
                    }
                }
                else{
                    stop_search = true;
                }
            }while(!stop_search);
        }

        return possible_moves;
    }
}

public abstract class ChessPiece implements PieceOption {

    /* Enum to define the piece definition */
    enum pieces_number{
        pawn,
        rook,
        knight,
        bishop,
        queen,
        king
    }

    enum chess_colors{
        white,
        black
    }

    protected Bitmap        bmp;
    protected chess_colors  color;
    protected pieces_number piece_type;
    protected Rect          rect_size;

    public ChessPiece (Bitmap bmp, pieces_number piece_type, Rect rect_size, chess_colors color){
        this.bmp            = bmp;
        this.piece_type     = piece_type;
        this.rect_size      = rect_size;
        this.color          = color;
    }

    public void drawPiece(Canvas canvas, Rect rect_dst){
        Paint paint = new Paint();
        canvas.drawBitmap(bmp, rect_size, rect_dst, paint);
    }

    public chess_colors getColor(){
        return color;
    }
}
