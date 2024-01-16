package com.example.chessexplorer;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class King extends ChessPiece{

    protected boolean       piece_moved = false;
    protected boolean       under_check = false;

    public King(Bitmap bmp_init, Rect rect_size_init, chess_colors colors) {
        super(bmp_init, pieces_number.king, rect_size_init, colors);
    }
}
