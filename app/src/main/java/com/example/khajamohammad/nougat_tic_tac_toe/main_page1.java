package com.example.khajamohammad.nougat_tic_tac_toe;

        import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class main_page1 extends AppCompatActivity {

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_page1);

        b1=(Button)findViewById(R.id.go);
    }

    public void go_play_game(View view) {

        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
