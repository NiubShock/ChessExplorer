package com.example.chessexplorer;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chessexplorer.databinding.ActivityMainBinding;

/*
[X] Pawn Promotion
[X] Check implementation
[ ] Checkmate implementation
[X] White - Black turns
[ ] Text view + reading etc
[ ] Decoding of the game
[ ] Move forward and backward button
[ ] Database ?
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    Chessboard chessboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();

        chessboard = new Chessboard(this);

        setContentView(chessboard);


    }


}