package com.example.chessexplorer;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Rook extends ChessPiece{

    protected boolean       piece_moved = false;

    public Rook(Bitmap bmp, Rect rect_size, chess_colors color) {
        super(bmp, pieces_number.rook, rect_size, color);
    }
}
