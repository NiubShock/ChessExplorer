package com.example.chessexplorer;

import static com.example.chessexplorer.R.*;

import android.content.Context;
import android.content.res.Resources;
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

    enum draw_semaphore_enum{
        DEFAULT,
        FIRST_SELECTION,
        NEW_MOVE,
        CHECK_PROMOTION,
        DRAW_PROMOTION,
        SELECT_PROMOTION,
        CHECK_CHECKS,
        NEW_TURN
    }

    float[][]               square_positions;

    float                   move_x_start = -1, move_y_start = -1;

    boolean                 starting_point                  = true;
    boolean                 possible_promo                  = false;
    boolean                 render_req                      = false;

    int                     check_detected                  = -1;

    ChessPiece.chess_colors color_moving                    = ChessPiece.chess_colors.white;
    ChessPiece.chess_colors color_to_move                   = ChessPiece.chess_colors.black;
    ChessPiece.chess_colors check_color                     = ChessPiece.chess_colors.white;

    Rect                    rect_size;

    ChessboardSquare[]      chessboardSquare;
    ArrayList<ChessMoves>   possible_moves;

    ChessboardSquare[]      chessSquarePromWhite;
    ChessboardSquare[]      chessSquarePromBlack;

    draw_semaphore_enum draw_semaphore = draw_semaphore_enum.DEFAULT;

    int selected_square;
    int new_move_square;
    int                     promotion_square                = -1;

    public Chessboard (Context context) {
        super(context);

        /* Store the coordinates of the the squares */
        square_positions = new float[64][4];
        /* Store the array of Squares */
        chessboardSquare = new ChessboardSquare[64];

        /* Determine the dimensions of the square */
        /* -------------------------------------- */
        float size_x_rect = (float) (Resources.getSystem().getDisplayMetrics().widthPixels/8.0); // 135; //(float) (this.getWidth()/8.0);
        float size_y_rect = (float) (Resources.getSystem().getDisplayMetrics().heightPixels/8.0);// 135; //(float) (this.getHeight()/8.0);
        float square_size = Math.min(size_x_rect, size_y_rect);

        /* Declare the square size */
        rect_size = new Rect(0, 0, (int) square_size, (int) square_size);
        /* -------------------------------------------------------------------------------------- */

        /* Load the chess board */
        setChessPosition();

        /* Load the coordinates */
        loadCoordinates();

        /* Initialize the vector for promotion */
        chessSquarePromWhite = new ChessboardSquare[4];
        chessSquarePromBlack = new ChessboardSquare[4];

        /* Load data for the white promotion vector */
        /* ---------------------------------------- */
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), drawable.white_knight);
        Rect rect_src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        NBQ_Piece white_knight = new NBQ_Piece(bmp, ChessPiece.pieces_number.knight, rect_src, ChessPiece.chess_colors.white);
        chessSquarePromWhite[0] = new ChessboardSquare(Color.DKGRAY, 0 , rect_size);
        chessSquarePromWhite[0].loadPiece(white_knight);

        bmp = BitmapFactory.decodeResource(getResources(), drawable.white_bishop);
        NBQ_Piece white_bishop = new NBQ_Piece(bmp, ChessPiece.pieces_number.bishop, rect_src, ChessPiece.chess_colors.white);
        chessSquarePromWhite[1] = new ChessboardSquare(Color.DKGRAY, 0 , rect_size);
        chessSquarePromWhite[1].loadPiece(white_bishop);

        bmp = BitmapFactory.decodeResource(getResources(), drawable.white_rook);
        Rook white_rook = new Rook(bmp, rect_src, ChessPiece.chess_colors.white);
        chessSquarePromWhite[2] = new ChessboardSquare(Color.DKGRAY, 0 , rect_size);
        chessSquarePromWhite[2].loadPiece(white_rook);

        bmp = BitmapFactory.decodeResource(getResources(), drawable.white_queen);
        NBQ_Piece white_queen = new NBQ_Piece(bmp, ChessPiece.pieces_number.queen, rect_src, ChessPiece.chess_colors.white);
        chessSquarePromWhite[3] = new ChessboardSquare(Color.DKGRAY, 0 , rect_size);
        chessSquarePromWhite[3].loadPiece(white_queen);
        /* -------------------------------------------------------------------------------------- */

        /* Load data for the black promotion vector */
        /* ---------------------------------------- */
        bmp = BitmapFactory.decodeResource(getResources(), drawable.black_knight);
        NBQ_Piece black_knight = new NBQ_Piece(bmp, ChessPiece.pieces_number.knight, rect_src, ChessPiece.chess_colors.black);
        chessSquarePromBlack[0] = new ChessboardSquare(Color.DKGRAY, 0 , rect_size);
        chessSquarePromBlack[0].loadPiece(black_knight);

        bmp = BitmapFactory.decodeResource(getResources(), drawable.black_bishop);
        NBQ_Piece black_bishop = new NBQ_Piece(bmp, ChessPiece.pieces_number.bishop, rect_src, ChessPiece.chess_colors.black);
        chessSquarePromBlack[1] = new ChessboardSquare(Color.DKGRAY, 0 , rect_size);
        chessSquarePromBlack[1].loadPiece(black_bishop);

        bmp = BitmapFactory.decodeResource(getResources(), drawable.black_rook);
        Rook black_rook = new Rook(bmp, rect_src, ChessPiece.chess_colors.black);
        chessSquarePromBlack[2] = new ChessboardSquare(Color.DKGRAY, 0 , rect_size);
        chessSquarePromBlack[2].loadPiece(black_rook);

        bmp = BitmapFactory.decodeResource(getResources(), drawable.black_queen);
        NBQ_Piece black_queen = new NBQ_Piece(bmp, ChessPiece.pieces_number.queen, rect_src, ChessPiece.chess_colors.black);
        chessSquarePromBlack[3] = new ChessboardSquare(Color.DKGRAY, 0 , rect_size);
        chessSquarePromBlack[3].loadPiece(black_queen);
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
        Bitmap bmp = Bitmap.createBitmap(rect_size.width(), rect_size.height(), Bitmap.Config.ARGB_8888);
        Rect rect_src = new Rect(0, 0, bmp_size.getWidth(), bmp_size.getHeight());

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
                chessboardSquare[square_position] = new ChessboardSquare(color, square_position, rect_size);
            }
        }
        /* -------------------------------------------------------------------------------------- */

        /* Place the pieces */
        /* ---------------- */
        {
            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_pawn);
            Pawn black_pawn = new Pawn(bmp, rect_src, ChessPiece.chess_colors.black);
            for (int i = 0; i < 8; i++) {
                chessboardSquare[8 + i].loadPiece(black_pawn);
            }

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_rook);
            Rook black_rook = new Rook(bmp, rect_src, ChessPiece.chess_colors.black);
            chessboardSquare[0].loadPiece(black_rook);
            chessboardSquare[7].loadPiece(black_rook);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_knight);
            NBQ_Piece black_knight = new NBQ_Piece(bmp, ChessPiece.pieces_number.knight,
                    rect_src, ChessPiece.chess_colors.black);
            chessboardSquare[1].loadPiece(black_knight);
            chessboardSquare[6].loadPiece(black_knight);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_bishop);
            NBQ_Piece black_bishop = new NBQ_Piece(bmp, ChessPiece.pieces_number.bishop,
                    rect_src, ChessPiece.chess_colors.black);
            chessboardSquare[2].loadPiece(black_bishop);
            chessboardSquare[5].loadPiece(black_bishop);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_queen);
            NBQ_Piece black_queen = new NBQ_Piece(bmp, ChessPiece.pieces_number.queen,
                    rect_src, ChessPiece.chess_colors.black);
            chessboardSquare[3].loadPiece(black_queen);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.black_king);
            King black_king = new King(bmp, rect_src, ChessPiece.chess_colors.black);
            chessboardSquare[4].loadPiece(black_king);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_pawn);
            Pawn white_pawn = new Pawn(bmp, rect_src, ChessPiece.chess_colors.white);
            for (int i = 0; i < 8; i++) {
                chessboardSquare[48 + i].loadPiece(white_pawn);
            }

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_rook);
            Rook white_rook = new Rook(bmp, rect_src, ChessPiece.chess_colors.white);
            chessboardSquare[56].loadPiece(white_rook);
            chessboardSquare[63].loadPiece(white_rook);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_knight);
            NBQ_Piece white_knight = new NBQ_Piece(bmp, ChessPiece.pieces_number.knight,
                    rect_src, ChessPiece.chess_colors.white);
            chessboardSquare[57].loadPiece(white_knight);
            chessboardSquare[62].loadPiece(white_knight);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_bishop);
            NBQ_Piece white_bishop = new NBQ_Piece(bmp, ChessPiece.pieces_number.bishop,
                    rect_src, ChessPiece.chess_colors.white);
            chessboardSquare[58].loadPiece(white_bishop);
            chessboardSquare[61].loadPiece(white_bishop);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_queen);
            NBQ_Piece white_queen = new NBQ_Piece(bmp, ChessPiece.pieces_number.queen,
                    rect_src, ChessPiece.chess_colors.white);
            chessboardSquare[59].loadPiece(white_queen);

            bmp = BitmapFactory.decodeResource(getResources(), drawable.white_king);
            King white_king = new King(bmp, rect_src, ChessPiece.chess_colors.white);
            chessboardSquare[60].loadPiece(white_king);
        }
        /* -------------------------------------------------------------------------------------- */
    }

    private void drawPromotionVector(Canvas canvas){
        /* Check the color of the piece */
        if (chessboardSquare[new_move_square].getPiece().getColor() == ChessPiece.chess_colors.white){
            /* Move the promotion vector */
            for (int j = 0; j < 4; j++){
                chessSquarePromWhite[j].moveSquarePosition(new_move_square + 8 * (j+1));
                chessSquarePromWhite[j].drawSquare(canvas);
            }
        }
        else {
            /* Move the promotion vector */
            for (int j = 0; j < 4; j++){
                chessSquarePromBlack[j].moveSquarePosition(new_move_square - 8 * (j+1));
                chessSquarePromBlack[j].drawSquare(canvas);
            }
        }
    }

    private boolean drawPromotedElement(int selected_square){
        for (int i = 0; i < 4; i++){
            if (selected_square == chessSquarePromWhite[i].getSquareNumber()){
                chessboardSquare[new_move_square].loadPiece(chessSquarePromWhite[i].getPiece());
                return true;
            }

            else if (selected_square == chessSquarePromBlack[i].getSquareNumber()){
                chessboardSquare[new_move_square].loadPiece(chessSquarePromBlack[i].getPiece());
                return true;
            }
        }
        return false;
    }

    private void drawChessboard(Canvas canvas){
        /* Draw all the element in the chessboard */
        /* -------------------------------------- */
        for (int i = 0; i < 8; i ++){
            for (int j = 0; j < 8; j++){
                chessboardSquare[i * 8 + j].drawSquare(canvas);
            }
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas){
        super.onDraw(canvas);

        /* Draw all the element in the chessboard */
        /* -------------------------------------- */
        drawChessboard(canvas);
        if (check_detected >= 0) {
            check_color = color_to_move;
            chessboardSquare[check_detected].checkHighlight(canvas);
        }
        if (possible_promo) {
            drawPromotionVector(canvas);
        }

        switch(draw_semaphore){
            case DEFAULT:
                draw_semaphore = draw_semaphore_enum.FIRST_SELECTION;
                break;

            case FIRST_SELECTION:
                /* Highlight the selected square and show the possible moves */
                /* --------------------------------------------------------- */
                selected_square = getSquareClicked();

                if (selected_square >= 0 && chessboardSquare[selected_square].getPiece() != null) {
                    /* Check if a move has been made and the correct color has been selected */
                    if (selected_square >= 0 && chessboardSquare[selected_square].getPiece().getColor() == color_moving) {

                        chessboardSquare[selected_square].highlightSquare(canvas);

                        possible_moves = chessboardSquare[selected_square].getPiece().getPossibleMoves(selected_square, chessboardSquare);

                        /* Check if the moves are going to parry or generate a check */
                        boolean check_still_here;
                        for (int i = 0; i < possible_moves.size(); i++) {
                            check_still_here = checkCheckPersistence(color_to_move, selected_square, possible_moves.get(i).move_square, chessboardSquare);
                            if (check_still_here) {
                                possible_moves.remove(i);
                                i--;
                            }
                        }

                        /* Check if the result is valid -> Return -1 if no piece selected */
                        if (!possible_moves.isEmpty()) {
                            for (int i = 0; i < possible_moves.size(); i++) {
                                chessboardSquare[possible_moves.get(i).move_square].drawOval(canvas);
                            }
                        }

                        draw_semaphore = draw_semaphore_enum.NEW_MOVE;
                    }
                }
                break;

            case NEW_MOVE:
                new_move_square = getSquareClicked();
                /* Second move received */
                /* -------------------- */
                if (new_move_square >= 0) {

                    boolean move_found = false;

                    /* Check if the move was legal */
                    for (int i = 0; i < possible_moves.size(); i++){
                        if (possible_moves.get(i).move_square == new_move_square){
                            chessboardSquare[new_move_square].highlightSquare(canvas);
                            move_found = true;
                        }
                    }

                    /* Made an illegal move, reset moves */
                    if (!move_found){
                        draw_semaphore = draw_semaphore_enum.FIRST_SELECTION;

                        /* Render again without the previous overlay */
                        freeLastMove();
                        render_req = true;
                    }

                    /* The move was legal */
                    else {
                        chessboardSquare[selected_square].getPiece().moveTo(selected_square, new_move_square, chessboardSquare, canvas);

                        resetCheckDetected();

                        draw_semaphore = draw_semaphore_enum.CHECK_PROMOTION;
                        render_req = true;
                    }
                }
                break;

            case CHECK_PROMOTION:
                possible_promo = chessboardSquare[new_move_square].getPiece().checkPawnPromotion(new_move_square, chessboardSquare);

                /* Check if promotion exists */
                if (possible_promo){
                    draw_semaphore = draw_semaphore_enum.DRAW_PROMOTION;
                }
                else{
                    draw_semaphore = draw_semaphore_enum.CHECK_CHECKS;
                }

                render_req = true;
                break;

            case DRAW_PROMOTION:
                draw_semaphore = draw_semaphore_enum.SELECT_PROMOTION;
                break;

            case SELECT_PROMOTION:
                promotion_square = getSquareClicked();

                boolean promo_done = drawPromotedElement(promotion_square);

                if (promo_done){
                    possible_promo = false;
                    draw_semaphore = draw_semaphore_enum.CHECK_CHECKS;
                    render_req = true;
                }


                break;

            case CHECK_CHECKS:

                /* Check for check and checkmate */
                check_detected = checkChecks(chessboardSquare);

                draw_semaphore = draw_semaphore_enum.NEW_TURN;
                render_req = true;
                break;

            case NEW_TURN:
                newTurn();

                draw_semaphore = draw_semaphore_enum.FIRST_SELECTION;
                render_req = true;
                break;
        }





        /* -------------------------------------------------------------------------------------- */

//        /* Check for promotion */
//        /* ------------------- */
//        if (possible_promo == false) {
//            possible_promo = chessRuler.checkPawnPromotion(highlight_square[1]);
//        }
//
//        /* Check if promotion exists */
//        if (possible_promo == true){
//
//            promotion_square = highlight_square[1];
//            /* Must wait for the next turn */
//            if (skip_turn == false){
//                skip_turn = true;
//                newTurn();
//            }
//        }
//
//        /* Remove the ovals */
//        freeLastMove();
//
//        render_req = true;
//
//        newTurn();
//
//        if (possible_promo == true && skip_turn == true) {
//
//            drawPromotionVector(canvas);
//            drawPromotedElement(highlight_square[0]);
//
//
//            possible_promo = false;
//            skip_turn = false;
//            starting_point = true;
//            chessboardSquare[highlight_square[0]].drawSquare(canvas);
//
//            newTurn();
//        }
//
//        if (highlight_square[0] >= 0){
//            /* Check for check and checkmate */
//            check_detected = chessRuler.checkChecks(color_to_move);
//        }
//
//        if (check_detected == -1 && highlight_square[1] >= 0) {
//            /* Check for check and checkmate */
//            check_detected = chessRuler.checkChecks(color_to_move);
//        }
//
//        if (check_detected >= 0) {
//            check_color = color_to_move;
//            chessboardSquare[check_detected].checkHighlight(canvas);
//        }

        if (render_req) {
            postInvalidate();
            render_req = false;
        }

        /* -------------------------------------------------------------------------------------- */
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        move_x_start = event.getX();
        move_y_start = event.getY();
        starting_point = false;

        postInvalidate();

        return super.onTouchEvent(event);
    }

    private void newTurn(){
        if (color_moving == ChessPiece.chess_colors.white){
            color_moving = ChessPiece.chess_colors.black;
            color_to_move = ChessPiece.chess_colors.white;
        }
        else {
            color_moving = ChessPiece.chess_colors.white;
            color_to_move = ChessPiece.chess_colors.black;
        }
    }

    private void freeLastMove(){
        move_x_start = -1;
        move_y_start = -1;
    }

    private int getSquareClicked(){
        int result;

        for (int i = 0; i < 64; i++){
            if (((move_x_start >= square_positions[i][0]) && (move_x_start < square_positions[i][2])) &&
                    (move_y_start >= square_positions[i][1]) && (move_y_start < square_positions[i][3])){
                result = i;
                return result;
            }
        }

        return -1;
    }

    private void resetCheckDetected(){
        check_detected = -1;
    }

    private int checkChecks(ChessboardSquare[] chessboard){
        /* Check if one piece can attack the enemy king */
        for (int i = 0; i < 64; i++) {
            /* Check the pieces of the same color */
            if (chessboard[i].getPiece() != null) {
                if (chessboard[i].getPiece().color == color_moving) {
                    ArrayList<ChessMoves> check_move = new ArrayList<>(chessboard[i].getPiece().getPossibleMoves(i, chessboardSquare));

                    /* Check all the moves */
                    for (int j = 0; j < check_move.size(); j++) {
                        /* Check the capture move */
                        if (check_move.get(j).move_type == ChessMoves.move_types.capture) {
                            /* Check if attacks the king */
                            if (chessboard[check_move.get(j).move_square].getPiece().piece_type == ChessPiece.pieces_number.king) {
                                check_detected = check_move.get(j).move_square;
                            }
                        }
                    }
                }
            }
        }

        return check_detected;
    }

    private boolean checkCheckPersistence(ChessPiece.chess_colors color, int selected_square, int destination_square, ChessboardSquare[] chessboard){
        boolean check_detected = false;

        /* Make the move */
        ChessPiece piece_mov = chessboard[destination_square].getPiece();
        chessboard[destination_square].loadPiece(chessboard[selected_square].getPiece());
        chessboard[selected_square].emptyPiece();

        /* Check if one piece can attack the enemy king */
        for (int i = 0; i < 64; i++){
            /* Check the pieces of the same color */
            if (chessboard[i].getPiece() != null) {
                if (chessboard[i].getPiece().getColor() == color) {
                    ArrayList<ChessMoves> check_move = new ArrayList<>(chessboard[i].getPiece().getPossibleMoves(i, chessboard));

                    /* Check all the moves */
                    for (int j = 0; j < check_move.size(); j++) {
                        /* Check the capture move */
                        if (check_move.get(j).move_type == ChessMoves.move_types.capture) {
                            /* Check if attacks the king */
                            if (chessboard[check_move.get(j).move_square].getPiece().piece_type == ChessPiece.pieces_number.king) {
                                check_detected = true;
                            }
                        }
                    }
                }
            }
        }

        /* Undo the move */
        chessboard[selected_square].loadPiece(chessboard[destination_square].getPiece());
        chessboard[destination_square].loadPiece(piece_mov);

        /* Return the result */
        return check_detected;
    }
}
