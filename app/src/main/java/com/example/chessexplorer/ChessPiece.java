package com.example.chessexplorer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class ChessPiece {

    /* Enum to define the piece definition */
    enum pieces_number{
        none,
        pawn,
        rook,
        knight,
        bishop,
        queen,
        king
    }

    private Bitmap          bmp;
    private int             square_number;
    private pieces_number   piece_type;
    private Rect            rect_size;

    private Paint   paint;

    public ChessPiece (Bitmap bmp_init, int square_number_init, pieces_number piece_type_init,
                            Rect rect_size_init){
        bmp             = bmp_init;
        square_number   = square_number_init;
        piece_type      = piece_type_init;
        rect_size       = rect_size_init;

    }

    public void loadNewBmp(Bitmap bmp_new){
        bmp = bmp_new;
    }

    public int getSquareNumber(){
        return square_number;
    }

    public pieces_number getPieceType(){
        return piece_type;
    }

    public void drawPiece(Canvas canvas, Rect rect_dst){
        canvas.drawBitmap(bmp, rect_size, rect_dst, paint);
    }
}
