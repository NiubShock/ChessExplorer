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

    float[][]       square_positions;

    float           move_x_start = -1, move_y_start = -1;
    float           move_x_end = -1, move_y_end = -1;

    boolean         starting_point                  = true;
    boolean         new_move                        = false;

    Rect            rect_size;

    Paint paint = new Paint();

    ChessboardSquare[] chessboardSquare;

    public Chessboard (Context context, View v) {
        super(context);

        /* Store the coordinates of the the squares */
        square_positions = new float[64][4];
        /* Store the array of Squares */
        chessboardSquare = new ChessboardSquare[64];

        /* Determine the dimensions of the square */
        /* -------------------------------------- */
        float size_x_rect = 135; //(float) (this.getWidth()/8.0);
        float size_y_rect = 135; //(float) (this.getHeight()/8.0);
        float square_size;

        if (size_x_rect < size_y_rect)  square_size = size_x_rect;
        else                            square_size = size_y_rect;

        /* Declare the square size */
        rect_size = new Rect(0, 0, (int) size_x_rect, (int) size_y_rect);
        /* -------------------------------------------------------------------------------------- */

        /* Load the chess board */
        setChessPosition();

        /* Load the coordinates */
        loadCoordinates();

    }

    /* This function loads the coordinates of the rectangles in an array */
    private void loadCoordinates(){
        for (int i = 0; i < 64; i++){
            square_positions[i][0] = chessboardSquare[i].getRectCoordinates().left;
            square_positions[i][1] = chessboardSquare[i].getRectCoordinates().top;
            square_positions[i][2] = chessboardSquare[i].getRectCoordinates().right;
            square_positions[i][3] = chessboardSquare[i].getRectCoordinates().bottom;
        }
    }

    /* This function set the position of the chess pieces and the squares at the startup */
    private void setChessPosition(){

        int color;
        int square_position;

        Bitmap bmp_size = BitmapFactory.decodeResource(getResources(), drawable.black_pawn);

        /* Load the btm size and the square properties */
        /* ------------------------------------------- */
        for (int i = 0; i < 8; i ++){
            for (int j = 0; j < 8; j++){
                /* Prepare the color of the square */
                /* ------------------------------- */
                if ((i+j) % 2 == 0){
                    color = Color.LTGRAY;
                } else {
                    color = Color.DKGRAY;
                }
                /* ------------------------------------------------------------------------------ */

                /* Load the square position and an empty bmp */
                square_position = i * 8 + j;
                /* Load the data, not the Bitmap */
                Bitmap bmp = Bitmap.createBitmap(rect_size.width(), rect_size.height(), Bitmap.Config.ARGB_8888);
                Rect rect_src = new Rect(0, 0, bmp_size.getWidth(), bmp_size.getHeight());
                ChessPiece piece = new ChessPiece(bmp, square_position, ChessPiece.pieces_number.none, rect_src);

                chessboardSquare[square_position] = new ChessboardSquare(color, piece, square_position, rect_size);
            }
        }
        /* -------------------------------------------------------------------------------------- */

        /* Place the pieces */
        /* ---------------- */
        {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), drawable.black_pawn);
            for (int i = 0; i < 8; i++) {
                chessboardSquare[8 + i].loadNewBmp(bmp);
            }

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_rook);
            chessboardSquare[0].loadNewBmp(bmp);
            chessboardSquare[7].loadNewBmp(bmp);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_knight);
            chessboardSquare[1].loadNewBmp(bmp);
            chessboardSquare[6].loadNewBmp(bmp);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_bishop);
            chessboardSquare[2].loadNewBmp(bmp);
            chessboardSquare[5].loadNewBmp(bmp);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_queen);
            chessboardSquare[3].loadNewBmp(bmp);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_king);
            chessboardSquare[4].loadNewBmp(bmp);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_pawn);
            for (int i = 0; i < 8; i++) {
                chessboardSquare[48 + i].loadNewBmp(bmp);
            }

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_rook);
            chessboardSquare[56].loadNewBmp(bmp);
            chessboardSquare[61].loadNewBmp(bmp);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_knight);
            chessboardSquare[57].loadNewBmp(bmp);
            chessboardSquare[62].loadNewBmp(bmp);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_bishop);
            chessboardSquare[58].loadNewBmp(bmp);
            chessboardSquare[63].loadNewBmp(bmp);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_queen);
            chessboardSquare[59].loadNewBmp(bmp);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_king);
            chessboardSquare[60].loadNewBmp(bmp);
        }
        /* -------------------------------------------------------------------------------------- */
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas){
        super.onDraw(canvas);

        /* Draw all the element in the chessboard */
        /* -------------------------------------- */
        for (int i = 0; i < 8; i ++){
            for (int j = 0; j < 8; j++){
                chessboardSquare[i * 8 + j].drawSquare(canvas);
            }
        }
        /* -------------------------------------------------------------------------------------- */

        /* Highlight the selected square and show the possible moves */
        /* --------------------------------------------------------- */
        int [] highlight_square = getSquareClicked();

        if (highlight_square[0] >= 0){
            chessboardSquare[highlight_square[0]].highlightSquare(canvas);
        }

        if (highlight_square[1] >= 0 && new_move == true) {
            chessboardSquare[highlight_square[1]].highlightSquare(canvas);
        }

        if (new_move == true){
            new_move = false;
        }
        /* -------------------------------------------------------------------------------------- */
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
        }

        postInvalidate();

        return super.onTouchEvent(event);
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

        if (elemx == false){
            result[0] = -1;
        }
        if (elemy == false){
            result[1] = -1;
        }

        return result;
    }
}
