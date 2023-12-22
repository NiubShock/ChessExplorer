package com.example.chessexplorer;

import static com.example.chessexplorer.R.*;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.chessexplorer.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Chessboard extends View {

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

    float[][] square_positions;

    float move_x_start;
    float move_y_start;
    float move_x_end;
    float move_y_end;
    boolean starting_point  = true;
    boolean new_move        = false;

    Paint paint = new Paint();

    ChessboardSquare[] chessboardSquare;

    public Chessboard (Context context, View v) {
        super(context);

        /* Store the square positions to change color later */
        square_positions = new float[64][4];

        /* Determine the dimensions of the square */
        float size_x_rect = (float) (this.getWidth()/8.0);
        float size_y_rect = (float) (this.getHeight()/8.0);
        float square_size;

        if (size_x_rect < size_y_rect)  square_size = size_x_rect;
        else                            square_size = size_y_rect;

        chessboardSquare = new ChessboardSquare[64];

    }

    @Override
    protected void onDraw(@NonNull Canvas canvas){
        super.onDraw(canvas);

        /* Determine the dimensions of the square */
        float size_x_rect = (float) (this.getWidth()/8.0);
        float size_y_rect = (float) (this.getHeight()/8.0);
        float square_size;

        if (size_x_rect < size_y_rect)  square_size = size_x_rect;
        else                            square_size = size_y_rect;

        Rect rect_size = new Rect(0,0,(int) square_size, (int) square_size);

        for (int i = 0; i < 8; i ++){
            for (int j = 0; j < 8; j++){
                if ((i+j) % 2 == 0){
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), drawable.black_bishop);
                    Rect rect_src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
                    Rect rect_dst = new Rect((int)square_positions[i * 8 + j][0], (int)square_positions[i * 8 + j][1],
                            (int)square_positions[i * 8 + j][2], (int)square_positions[i * 8 + j][3]);
                    ChessPiece piece = new ChessPiece(bmp, i * 8 + j, 1, rect_src);

                    chessboardSquare[i * 8 + j] = new ChessboardSquare(Color.LTGRAY, piece, i * 8 + j, rect_size);
                    chessboardSquare[i * 8 + j].drawSquare(canvas);
                }
                else {
                    paint.setColor(Color.DKGRAY);
                    drawRectangle(canvas, paint, i * square_size , j * square_size,
                            square_size, i * 8 + j);
                }
            }
        }

        if (new_move == true){
            int [] highlight_square = getSquareClicked();

            if (highlight_square[0] >= 0 && highlight_square[1] >= 0){
                paint.setColor(Color.argb(50, 255,0,0));
                drawRectangle(canvas, paint, square_positions[highlight_square[0]][0] ,
                        square_positions[highlight_square[0]][1],
                        square_size, highlight_square[0]);

                paint.setColor(Color.argb(50, 255,0,0));
                drawRectangle(canvas, paint, square_positions[highlight_square[1]][0] ,
                        square_positions[highlight_square[1]][1],
                        square_size, highlight_square[1]);
            }

            new_move = false;
        }

//        for (int i = 0; i < 8; i ++){
//            for (int j = 0; j < 8; j++){
//                if ((i+j) % 2 == 0){
//                    paint.setColor(Color.LTGRAY);
//                    drawRectangle(canvas, paint, i * square_size , j * square_size,
//                            square_size, i * 8 + j);
//                }
//                else {
//                    paint.setColor(Color.DKGRAY);
//                    drawRectangle(canvas, paint, i * square_size , j * square_size,
//                            square_size, i * 8 + j);
//                }
//            }
//        }
//
//        if (new_move == true){
//            int [] highlight_square = getSquareClicked();
//
//            if (highlight_square[0] >= 0 && highlight_square[1] >= 0){
//                paint.setColor(Color.argb(50, 255,0,0));
//                drawRectangle(canvas, paint, square_positions[highlight_square[0]][0] ,
//                        square_positions[highlight_square[0]][1],
//                        square_size, highlight_square[0]);
//
//                paint.setColor(Color.argb(50, 255,0,0));
//                drawRectangle(canvas, paint, square_positions[highlight_square[1]][0] ,
//                        square_positions[highlight_square[1]][1],
//                        square_size, highlight_square[1]);
//            }
//
//            new_move = false;
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (starting_point == true){
            move_x_start = event.getX();
            move_y_start = event.getY();
            starting_point = false;
        }
        else {
            move_x_end = event.getX();
            move_y_end = event.getY();
            starting_point = true;
            new_move = true;

            postInvalidate();
        }
        return super.onTouchEvent(event);
    }

    private void drawRectangle(Canvas canvas, Paint paint, float origin_x, float origin_y,
                               float size, int square_ref){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), drawable.black_bishop);
        square_positions[square_ref][0] = origin_x;
        square_positions[square_ref][1] = origin_y;
        square_positions[square_ref][2] = origin_x + size;
        square_positions[square_ref][3] = origin_y + size;
        canvas.drawRect(square_positions[square_ref][0], square_positions[square_ref][1],
                square_positions[square_ref][2], square_positions[square_ref][3], paint);

        Rect rect_src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        Rect rect_dst = new Rect((int)square_positions[square_ref][0], (int)square_positions[square_ref][1],
                (int)square_positions[square_ref][2], (int)square_positions[square_ref][3]);
//        canvas.drawBitmap(bmp, rect_src, rect_dst, paint);

        ChessPiece piece = new ChessPiece(bmp, square_ref, 1, rect_src);
        piece.drawPiece(canvas, rect_dst);
    }

    private int[] getSquareClicked(){
        boolean elemx = false, elemy = false;
        int [] result = new int[2];

        for (int i = 0; i < 64; i++){
            if (((move_x_start >= square_positions[i][0]) && (move_x_start < square_positions[i][2])) &&
                    (move_y_start >= square_positions[i][1]) && (move_y_start < square_positions[i][3])){
                result[0] = i;
                elemx = true;
            }

            if (((move_x_end >= square_positions[i][0]) && (move_x_end < square_positions[i][2])) &&
                    (move_y_end >= square_positions[i][1]) && (move_y_end < square_positions[i][3])){
                result[1] = i;
                elemy = true;
            }
        }

        if (elemx == true && elemy == true){
            return result;
        }else{
            result[0] = -1;
            result[1] = -1;
            return result;
        }
    }
}
