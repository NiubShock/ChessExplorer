package com.example.chessexplorer;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;

public class King extends ChessPiece {

    protected boolean       piece_moved = false;
    protected boolean       under_check = false;

    public King(Bitmap bmp_init, Rect rect_size_init, chess_colors colors) {
        super(bmp_init, pieces_number.king, rect_size_init, colors);
    }

    @Override
    public ArrayList<ChessMoves> getPossibleMoves(int selected_square, ChessboardSquare[] chessboard) {
        ArrayList<ChessMoves> possible_moves = new ArrayList<>();

        int square_counter;
        int limit;

        limit = selected_square - selected_square % 8 + 8;

        square_counter = selected_square + 8 - 1;
        if (square_counter <= 63 && square_counter >= limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
            }
        }

        square_counter = selected_square + 8;
        if (square_counter <= 63){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
            }
        }

        limit = selected_square - selected_square % 8 + 8 + 8;
        square_counter = selected_square + 8 + 1;
        if (square_counter <= 63 && square_counter < limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
            }
        }

        limit = selected_square - selected_square % 8 + 8;
        square_counter = selected_square + 1;
        if (square_counter <= 63 && square_counter < limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
            }
        }

        limit = selected_square - selected_square % 8 - 8;
        square_counter = selected_square - 8 - 1;
        if (square_counter >= 0 && square_counter >= limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
            }
        }

        square_counter = selected_square - 8;
        if (square_counter >= 0){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
            }
        }

        limit = selected_square - selected_square % 8 - 8 + 8;
        square_counter = selected_square - 8 + 1;
        if (square_counter >= 0 && square_counter < limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
            }
        }

        limit = selected_square - selected_square % 8;
        square_counter = selected_square - 1;
        if (square_counter >= 0 && selected_square > limit){
            /* Movement */
            if (chessboard[square_counter].isEmpty()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.move));
            }
            /* Capture */
            else if (chessboard[square_counter].getPiece().getColor() != chessboard[selected_square].getPiece().getColor()){
                possible_moves.add(new ChessMoves(square_counter, ChessMoves.move_types.capture));
            }
        }

        return possible_moves;
    }

    @Override
    public void moveTo(int selected_square) {

    }

    @Override
    public boolean checkPawnPromotion(int selected_square, ChessboardSquare[] chessboard){
        return false;
    }
}
