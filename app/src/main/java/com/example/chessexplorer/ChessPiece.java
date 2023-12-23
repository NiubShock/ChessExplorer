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

    enum chess_colors{
        white,
        black
    }

    private Bitmap          bmp;
    private int             square_number;
    private chess_colors    color;
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

    public void loadColor(chess_colors color_init){
        color = color_init;
    }

    public int getSquareNumber(){
        return square_number;
    }

    public pieces_number getPieceType(){
        return piece_type;
    }

    public void loadPieceType(pieces_number piece_type_new){
        piece_type = piece_type_new;
    }
    public chess_colors getColor(){
        return color;
    }

    public void drawPiece(Canvas canvas, Rect rect_dst){
        canvas.drawBitmap(bmp, rect_size, rect_dst, paint);
    }
}
