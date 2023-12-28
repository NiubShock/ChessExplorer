package com.example.chessexplorer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class ChessboardSquare {
    private int         color;
    private ChessPiece  piece;
    private int         square_number;
    private Rect        rect_size;

    private Paint       paint;
    private Rect        rect_position;
    private RectF       rect_f_position;

    public ChessboardSquare(int color_init, ChessPiece piece_init, int square_number_init,
                            Rect rect_size_init){
        color           = color_init;
        piece           = piece_init;
        square_number   = square_number_init;
        rect_size       = rect_size_init;

        paint = new Paint();

        int x,y;

        x = square_number % 8;
        y = ((square_number - x) / 8) * rect_size.width();
        x = x * rect_size.width();

        rect_position = new Rect(x, y, x + rect_size.width(), y + rect_size.height());
    }

    public void drawSquare(Canvas canvas){
        paint.setColor(color);
        canvas.drawRect(rect_position, paint);

        piece.drawPiece(canvas, rect_position);
    }

    public void highlightSquare(Canvas canvas){
        paint.setColor(Color.argb(50, 255, 255, 0));
        canvas.drawRect(rect_position, paint);
    }

    public void checkHighlight(Canvas canvas){
        paint.setColor(Color.argb(50, 255, 0, 0));
        canvas.drawRect(rect_position, paint);
    }

    public void moveSquarePosition(int square_position){
        int x,y;

        square_number = square_position;

        x = square_number % 8;
        y = ((square_number - x) / 8) * rect_size.width();
        x = x * rect_size.width();

        rect_position = new Rect(x, y, x + rect_size.width(), y + rect_size.height());
    }

    public void drawOval(Canvas canvas){
        paint.setColor(Color.argb(100,0,0,0));

        rect_f_position = new RectF(rect_position);

        rect_f_position.top = rect_f_position.top + (float) rect_size.height() /4;
        rect_f_position.left = rect_f_position.left + (float) rect_size.width() /4;
        rect_f_position.bottom = rect_f_position.bottom - (float) rect_size.height() /4;
        rect_f_position.right = rect_f_position.right - (float) rect_size.height() /4;

        canvas.drawOval(rect_f_position, paint);
    }

    public void loadNewBmp(Bitmap bmp){
        piece.loadNewBmp(bmp);
    }

    public Rect getRectCoordinates(){
        return rect_position;
    }

    public ChessPiece.pieces_number getPieceType(){
        return piece.getPieceType();
    }

    public ChessPiece getPiece(){
        return piece;
    }

    public int getSquareNumber(){
        return square_number;
    }

    public void loadPiece(ChessPiece piece_new){
        piece = piece_new;
    }

    public void emptyPiece(){
        /* Load the data, not the Bitmap */
        Bitmap bmp = Bitmap.createBitmap(rect_size.width(), rect_size.height(), Bitmap.Config.ARGB_8888);

        piece = new ChessPiece(bmp, square_number, ChessPiece.pieces_number.none, rect_size);
    }

    public boolean isEmpty(){
        if (piece.getPieceType() == ChessPiece.pieces_number.none){
            return true;
        }
        else {
            return false;
        }
    }
    public void loadPieceType(ChessPiece.pieces_number piece_type_new){
        piece.loadPieceType(piece_type_new);
    }

    public void loadPieceColor(ChessPiece.chess_colors color_init){
        piece.loadColor(color_init);
    }
}
