package com.example.chessexplorer;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Pawn extends ChessPiece{

    protected boolean piece_moved   = false;

    public Pawn(Bitmap bmp, Rect rect_size, chess_colors color) {
        super(bmp, pieces_number.pawn, rect_size, color);
    }
}
