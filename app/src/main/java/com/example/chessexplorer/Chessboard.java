package com.example.chessexplorer;

import static com.example.chessexplorer.R.*;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Chessboard extends View {

    float[][]       square_positions;

    float           move_x_start = -1, move_y_start = -1;
    float           move_x_end = -1, move_y_end = -1;

    boolean         starting_point                  = true;
    boolean         new_move                        = false;
    boolean         possible_promo                  = false;
    boolean         render_req                      = false;

    int check_detected = -1;
    ChessPiece.chess_colors color_to_move = ChessPiece.chess_colors.white;

    Rect            rect_size;

    ChessboardSquare[] chessboardSquare;
    ChessRuler          chessRuler;
    ArrayList<ChessRuler.Chess_Move>  possible_moves;

    ChessboardSquare[] chessSquarePromWhite;
    ChessboardSquare[] chessSquarePromBlack;

    public Chessboard (Context context) {
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

        /* Initialize the ruler */
        chessRuler = new ChessRuler(chessboardSquare);

        /* Initialize the vector for promotion */
        chessSquarePromWhite = new ChessboardSquare[4];
        chessSquarePromBlack = new ChessboardSquare[4];

        /* Load data for the white promotion vector */
        /* ---------------------------------------- */
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), drawable.white_knight);
        Rect rect_src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        ChessPiece piece = new ChessPiece(bmp, 0, ChessPiece.pieces_number.knight, rect_src);
        chessSquarePromWhite[0] = new ChessboardSquare(Color.DKGRAY, piece, 0 , rect_size);
        chessSquarePromWhite[0].getPiece().loadColor(ChessPiece.chess_colors.white);

        bmp = BitmapFactory.decodeResource(getResources(), drawable.white_bishop);
        piece = new ChessPiece(bmp, 0, ChessPiece.pieces_number.bishop, rect_src);
        chessSquarePromWhite[1] = new ChessboardSquare(Color.DKGRAY, piece, 0 , rect_size);
        chessSquarePromWhite[1].getPiece().loadColor(ChessPiece.chess_colors.white);

        bmp = BitmapFactory.decodeResource(getResources(), drawable.white_rook);
        piece = new ChessPiece(bmp, 0, ChessPiece.pieces_number.rook, rect_src);
        chessSquarePromWhite[2] = new ChessboardSquare(Color.DKGRAY, piece, 0 , rect_size);
        chessSquarePromWhite[2].getPiece().loadColor(ChessPiece.chess_colors.white);

        bmp = BitmapFactory.decodeResource(getResources(), drawable.white_queen);
        piece = new ChessPiece(bmp, 0, ChessPiece.pieces_number.queen, rect_src);
        chessSquarePromWhite[3] = new ChessboardSquare(Color.DKGRAY, piece, 0 , rect_size);
        chessSquarePromWhite[3].getPiece().loadColor(ChessPiece.chess_colors.white);
        /* -------------------------------------------------------------------------------------- */

        /* Load data for the black promotion vector */
        /* ---------------------------------------- */
        bmp = BitmapFactory.decodeResource(getResources(), drawable.black_knight);
        piece = new ChessPiece(bmp, 0, ChessPiece.pieces_number.knight, rect_src);
        chessSquarePromBlack[0] = new ChessboardSquare(Color.DKGRAY, piece, 0 , rect_size);
        chessSquarePromBlack[0].getPiece().loadColor(ChessPiece.chess_colors.black);

        bmp = BitmapFactory.decodeResource(getResources(), drawable.black_bishop);
        piece = new ChessPiece(bmp, 0, ChessPiece.pieces_number.bishop, rect_src);
        chessSquarePromBlack[1] = new ChessboardSquare(Color.DKGRAY, piece, 0 , rect_size);
        chessSquarePromBlack[1].getPiece().loadColor(ChessPiece.chess_colors.black);

        bmp = BitmapFactory.decodeResource(getResources(), drawable.black_rook);
        piece = new ChessPiece(bmp, 0, ChessPiece.pieces_number.rook, rect_src);
        chessSquarePromBlack[2] = new ChessboardSquare(Color.DKGRAY, piece, 0 , rect_size);
        chessSquarePromBlack[2].getPiece().loadColor(ChessPiece.chess_colors.black);

        bmp = BitmapFactory.decodeResource(getResources(), drawable.black_queen);
        piece = new ChessPiece(bmp, 0, ChessPiece.pieces_number.queen, rect_src);
        chessSquarePromBlack[3] = new ChessboardSquare(Color.DKGRAY, piece, 0 , rect_size);
        chessSquarePromBlack[3].getPiece().loadColor(ChessPiece.chess_colors.black);
        /* -------------------------------------------------------------------------------------- */

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
                    color = 0xFFEEEED2;
                } else {
                    color = 0xFF769656;
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
                chessboardSquare[8 + i].loadPieceColor(ChessPiece.chess_colors.black);
                chessboardSquare[8 + i].loadPieceType(ChessPiece.pieces_number.pawn);
            }

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_rook);
            chessboardSquare[0].loadNewBmp(bmp);
            chessboardSquare[0].loadPieceColor(ChessPiece.chess_colors.black);
            chessboardSquare[0].loadPieceType(ChessPiece.pieces_number.rook);
            chessboardSquare[7].loadNewBmp(bmp);
            chessboardSquare[7].loadPieceColor(ChessPiece.chess_colors.black);
            chessboardSquare[7].loadPieceType(ChessPiece.pieces_number.rook);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_knight);
            chessboardSquare[1].loadNewBmp(bmp);
            chessboardSquare[1].loadPieceColor(ChessPiece.chess_colors.black);
            chessboardSquare[1].loadPieceType(ChessPiece.pieces_number.knight);
            chessboardSquare[6].loadNewBmp(bmp);
            chessboardSquare[6].loadPieceColor(ChessPiece.chess_colors.black);
            chessboardSquare[6].loadPieceType(ChessPiece.pieces_number.knight);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_bishop);
            chessboardSquare[2].loadNewBmp(bmp);
            chessboardSquare[2].loadPieceColor(ChessPiece.chess_colors.black);
            chessboardSquare[2].loadPieceType(ChessPiece.pieces_number.bishop);
            chessboardSquare[5].loadNewBmp(bmp);
            chessboardSquare[5].loadPieceColor(ChessPiece.chess_colors.black);
            chessboardSquare[5].loadPieceType(ChessPiece.pieces_number.bishop);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_queen);
            chessboardSquare[3].loadNewBmp(bmp);
            chessboardSquare[3].loadPieceColor(ChessPiece.chess_colors.black);
            chessboardSquare[3].loadPieceType(ChessPiece.pieces_number.queen);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_king);
            chessboardSquare[4].loadNewBmp(bmp);
            chessboardSquare[4].loadPieceColor(ChessPiece.chess_colors.black);
            chessboardSquare[4].loadPieceType(ChessPiece.pieces_number.king);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_pawn);
            for (int i = 0; i < 8; i++) {
                chessboardSquare[48 + i].loadNewBmp(bmp);
                chessboardSquare[48 + i].loadPieceColor(ChessPiece.chess_colors.white);
                chessboardSquare[48 + i].loadPieceType(ChessPiece.pieces_number.pawn);
            }

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_rook);
            chessboardSquare[56].loadNewBmp(bmp);
            chessboardSquare[56].loadPieceColor(ChessPiece.chess_colors.white);
            chessboardSquare[56].loadPieceType(ChessPiece.pieces_number.rook);
            chessboardSquare[63].loadNewBmp(bmp);
            chessboardSquare[63].loadPieceColor(ChessPiece.chess_colors.white);
            chessboardSquare[63].loadPieceType(ChessPiece.pieces_number.rook);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_knight);
            chessboardSquare[57].loadNewBmp(bmp);
            chessboardSquare[57].loadPieceColor(ChessPiece.chess_colors.white);
            chessboardSquare[57].loadPieceType(ChessPiece.pieces_number.knight);
            chessboardSquare[62].loadNewBmp(bmp);
            chessboardSquare[62].loadPieceColor(ChessPiece.chess_colors.white);
            chessboardSquare[62].loadPieceType(ChessPiece.pieces_number.knight);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_bishop);
            chessboardSquare[58].loadNewBmp(bmp);
            chessboardSquare[58].loadPieceColor(ChessPiece.chess_colors.white);
            chessboardSquare[58].loadPieceType(ChessPiece.pieces_number.bishop);
            chessboardSquare[61].loadNewBmp(bmp);
            chessboardSquare[61].loadPieceColor(ChessPiece.chess_colors.white);
            chessboardSquare[61].loadPieceType(ChessPiece.pieces_number.bishop);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_queen);
            chessboardSquare[59].loadNewBmp(bmp);
            chessboardSquare[59].loadPieceColor(ChessPiece.chess_colors.white);
            chessboardSquare[59].loadPieceType(ChessPiece.pieces_number.queen);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_king);
            chessboardSquare[60].loadNewBmp(bmp);
            chessboardSquare[60].loadPieceColor(ChessPiece.chess_colors.white);
            chessboardSquare[60].loadPieceType(ChessPiece.pieces_number.king);
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

        /* Check if a move has been made and the correct color has been selected */
        if (highlight_square[0] >= 0 &&
            chessboardSquare[highlight_square[0]].getPiece().getColor() == color_to_move){

            chessboardSquare[highlight_square[0]].highlightSquare(canvas);

            possible_moves = chessRuler.getPossibleMoves(highlight_square[0]);

            /* Check if the result is valid -> Return -1 if no piece selected */
            if (possible_moves.isEmpty() == false) {
                for (int i = 0; i < possible_moves.size(); i++) {
                    chessboardSquare[possible_moves.get(i).move_square].drawOval(canvas);

                    /* Check for possible promotion */
                    if (possible_moves.get(i).move_square == highlight_square[0]){

                        possible_promo = true;

                        /* Check the color of the piece */
                        if (chessboardSquare[highlight_square[0]].getPiece().getColor() == ChessPiece.chess_colors.white){
                            /* Move the promotion vector */
                            for (int j = 0; j < 4; j++){
                                chessSquarePromWhite[j].moveSquarePosition(highlight_square[0] + 8 * (j+1));
                                chessSquarePromWhite[j].drawSquare(canvas);
                            }
                        }
                        else {
                            /* Move the promotion vector */
                            for (int j = 0; j < 4; j++){
                                chessSquarePromBlack[j].moveSquarePosition(highlight_square[0] - 8 * (j+1));
                                chessSquarePromBlack[j].drawSquare(canvas);
                            }
                        }

                    }
                }
            }
        }
        else {
            starting_point = true;
        }
        /* -------------------------------------------------------------------------------------- */

        /* Second move received */
        /* -------------------- */
        if (highlight_square[1] >= 0 && new_move == true) {

            boolean move_found = false;

            /* Check if the move was legal */
            for (int i = 0; i < possible_moves.size(); i++){
                if (possible_moves.get(i).move_square == highlight_square[1]){
                    chessboardSquare[highlight_square[1]].highlightSquare(canvas);
                    move_found = true;
                }
            }

            /* Made an illegal move, reset moves */
            if (move_found == false){

                /* Check if selected a promotion element */
                if (possible_promo == true){
                    for (int i = 0; i < 4; i++){
                        if (highlight_square[1] == chessSquarePromWhite[i].getSquareNumber()){
                            chessboardSquare[highlight_square[0]].loadPiece(chessSquarePromWhite[i].getPiece());
                            chessboardSquare[highlight_square[0]].loadPieceColor(chessSquarePromWhite[i].getPiece().getColor());
                            chessboardSquare[highlight_square[0]].loadPieceType(chessSquarePromWhite[i].getPiece().getPieceType());
                        }

                        else if (highlight_square[1] == chessSquarePromBlack[i].getSquareNumber()){
                            chessboardSquare[highlight_square[0]].loadPiece(chessSquarePromBlack[i].getPiece());
                            chessboardSquare[highlight_square[0]].loadPieceColor(chessSquarePromBlack[i].getPiece().getColor());
                            chessboardSquare[highlight_square[0]].loadPieceType(chessSquarePromBlack[i].getPiece().getPieceType());
                        }
                    }

                    possible_promo = false;
                    chessboardSquare[highlight_square[0]].drawSquare(canvas);

                    newTurn();
                }

                starting_point = true;

                /* Render again without the previous overlay */
                freeLastMove();

                render_req = true;
            }

            /* The move was legal */
            else {
                chessboardSquare[highlight_square[1]].loadPiece(chessboardSquare[highlight_square[0]].getPiece());
                chessboardSquare[highlight_square[1]].drawSquare(canvas);

                chessboardSquare[highlight_square[0]].emptyPiece();
                chessboardSquare[highlight_square[0]].drawSquare(canvas);

                /* Remove the ovals */
                freeLastMove();

                render_req = true;

                newTurn();
            }
        }

        /* -------------------------------------------------------------------------------------- */

        if (new_move == true){
            new_move = false;
        }

        if (highlight_square[0] >= 0){
            /* Check for check and checkmate */
            check_detected = checkChecks(highlight_square[0], canvas);
        }

        if (check_detected == -1 && highlight_square[1] >= 0) {
            /* Check for check and checkmate */
            check_detected = checkChecks(highlight_square[1], canvas);
        }

        if (check_detected >= 0) {
            chessboardSquare[check_detected].checkHighlight(canvas);
        }

        if (render_req == true) {
            postInvalidate();
            render_req = false;
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

    private void newTurn(){
        if (color_to_move == ChessPiece.chess_colors.white){
            color_to_move = ChessPiece.chess_colors.black;
        }
        else {
            color_to_move = ChessPiece.chess_colors.white;
        }
    }

    private void freeLastMove(){
        move_x_start = -1;
        move_y_start = -1;
        move_y_end = -1;
        move_x_end = -1;
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

    private int checkChecks(int selected_square, Canvas canvas){
        /* Check if one piece can attack the enemy king */
        for (int i = 0; i < 64; i++){
            /* Check the pieces of the same color */
            if (chessboardSquare[i].getPiece().getColor() == chessboardSquare[selected_square].getPiece().getColor()){
                ArrayList<ChessRuler.Chess_Move> check_move = new ArrayList<>(chessRuler.getPossibleMoves(i));

                /* Check all the moves */
                for (int j = 0; j < check_move.size(); j++){
                    /* Check the capture move */
                    if (check_move.get(j).move_type == ChessRuler.move_types.capture){
                        /* Check if attacks the king */
                        if (chessboardSquare[check_move.get(j).move_square].getPiece().getPieceType() == ChessPiece.pieces_number.king){
                            return check_move.get(j).move_square;
                        }
                    }
                }
            }
        }

        return -1;
    }
}
