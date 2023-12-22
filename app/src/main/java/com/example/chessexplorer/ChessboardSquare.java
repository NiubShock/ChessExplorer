package com.example.chessexplorer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class ChessboardSquare {
    private int         color;
    private ChessPiece  piece;
    private int         square_number;
    private Rect        rect_size;

    private Paint       paint;
    private Rect        rect_position;

    public ChessboardSquare(int color_init, ChessPiece piece_init, int square_number_init,
                            Rect rect_size_init){
        color           = color_init;
        piece           = piece_init;
        square_number   = square_number_init;
        rect_size       = rect_size_init;

        paint = new Paint();
    }

    public void drawSquare(Canvas canvas){
        int x,y;

        x = square_number % 8;
        y = ((square_number - x) / 8) * rect_size.width();
        x = x * rect_size.width();

        paint.setColor(color);
        rect_position = new Rect(x, y, x + rect_size.width(), y + rect_size.height());
        canvas.drawRect(rect_position, paint);

        piece.drawPiece(canvas, rect_position);
    }

}