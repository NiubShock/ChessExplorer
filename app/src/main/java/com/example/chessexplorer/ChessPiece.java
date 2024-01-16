package com.example.chessexplorer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public abstract class ChessPiece {

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
