package com.example.khajamohammad.nougat_tic_tac_toe;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button[][] s = new Button[3][3];
    boolean player1turn = true;
    static int gridcount = 0;
    int recbut;
    TextView t1, t2;
    String s1="#FF141BE4",s2="#FFFF0000";

    SoundPool soundPool;
    private int sound1,sound2;
    int player1points = 0, player2points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String res = "b" + String.valueOf(i) + String.valueOf(j);
                int resID = getResources().getIdentifier(res, "id", getPackageName());
                s[i][j] = (Button) findViewById(resID);
                s[i][j].setOnClickListener(this);
            }
        }
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            AudioAttributes audioAttributes=new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            soundPool=new SoundPool.Builder().setMaxStreams(2).setAudioAttributes(audioAttributes).build();
        }
        else
        {
            soundPool=new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        }

        sound1=soundPool.load(this,R.raw.jump_musix,1);
        sound2=soundPool.load(this,R.raw.notification_pop_sound,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.item1:
                player1points=0;
                player2points=0;
                gridcount=0;
                player1turn=true;
                update_board();
                reset_the_board();
                break;


            case R.id.item2:
                AlertDialog.Builder ab=new AlertDialog.Builder(this);
                ab.setTitle("Exit");
                ab.setMessage("Do you really want to exit?");
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                ab.show();
                ab.setCancelable(true);
                break;

            case R.id.pc:
                reset_the_board();
                break;

            case R.id.computer:
                Intent intent=new Intent(MainActivity.this,playwithcomputer.class);
                break;

        }


        return true;
    }


    public boolean checkForWin() {
        String field[][]=new String [3][3];
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                field[i][j]=s[i][j].getText().toString();
            }
        }
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        } else if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        } else {
            for (int i = 0; i < 3; i++) {
                if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                    return true;
                } else if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                    return true;
                }
            }
            return false;
        }


    }

    public void update_board() {
        t1.setText("Player1 : " + player1points + "pts");
        t2.setText("Player2 : " + player2points + "pts");
    }

    @Override
    public void onClick(View view) {

        gridcount++;
        if (((Button) view).getText().toString().equals("")) {
            if (player1turn) {
                ((Button) view).setText("X");
                ((Button)view).setTextColor(Color.parseColor(s1));
                recbut=((Button)view).getId();
                player1turn = false;
                soundPool.play(sound1,0,1,0,0,1);
            } else {
                ((Button) view).setText("O");
                ((Button)view).setTextColor(Color.parseColor(s2));
                player1turn = true;
                soundPool.play(sound2,0,1,0,0,1);
            }


            if (checkForWin()) {

                if (player1turn) {

                    //Toast.makeText(MainActivity.this, "Player2 won the game", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder ab1=new AlertDialog.Builder(this);
                    ab1.setTitle("Player2 win!!!");
                    ab1.setMessage("Do you want to replay?");
                    ab1.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reset_the_board();
                            update_board();
                            dialogInterface.dismiss();
                        }
                    });

                    ab1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    ab1.show();
                    player2points++;
                    update_board();
                    reset_the_board();
                } else {
                    Log.i("Player1","Player1 won the game");
                    //Toast.makeText(MainActivity.this, "Player1 won the game", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder ab1=new AlertDialog.Builder(this);
                    ab1.setTitle("Player1 win!!!");
                    ab1.setMessage("Do you want to replay?");
                    ab1.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reset_the_board();
                            update_board();
                            dialogInterface.dismiss();
                        }
                    });

                    ab1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    ab1.show();
                    player1points++;
                    update_board();
                    reset_the_board();
                }
            } else if (gridcount == 9) {
                Toast.makeText(MainActivity.this, "Game Drawn", Toast.LENGTH_SHORT).show();
                update_board();
                reset_the_board();
            }
        }
        else
            return;

    }


    void reset_the_board() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                s[i][j].setText("");
            }
        }
        player1turn = true;
        gridcount = 0;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("gridcount",gridcount);
        outState.putInt("player1points",player1points);
        outState.putInt("player2points",player2points);
        outState.putBoolean("player1turn",player1turn);
        outState.putString("lightgreen",s1);
        outState.putString("red",s2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        gridcount=savedInstanceState.getInt("gridcount");
        player1points=savedInstanceState.getInt("player1points");
        player2points=savedInstanceState.getInt("player2points");
        player1turn=savedInstanceState.getBoolean("player1turn");
        s1=savedInstanceState.getString("lightgreen");
        s2=savedInstanceState.getString("red");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool=null;
    }

    public void onButtonClick(View view) {

        gridcount++;
        if (((Button) view).getText().toString().equals("")) {
            if (player1turn) {
                ((Button) view).setText("X");
                ((Button)view).setTextColor(Color.parseColor(s1));
                recbut=((Button)view).getId();
                player1turn = false;
                soundPool.play(sound1,0,1,0,0,1);
            } else {
                ((Button) view).setText("O");
                ((Button)view).setTextColor(Color.parseColor(s2));
                player1turn = true;
                soundPool.play(sound2,0,1,0,0,1);
            }


            if (checkForWin()) {

                if (player1turn) {

                    //Toast.makeText(MainActivity.this, "Player2 won the game", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder ab1=new AlertDialog.Builder(this);
                    ab1.setTitle("Player2 win!!!");
                    ab1.setMessage("Do you want to replay?");
                    ab1.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reset_the_board();
                            update_board();
                            dialogInterface.dismiss();
                        }
                    });

                    ab1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    ab1.show();
                    player2points++;
                    update_board();
                    reset_the_board();
                } else {
                    Log.i("Player1","Player1 won the game");
                    //Toast.makeText(MainActivity.this, "Player1 won the game", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder ab1=new AlertDialog.Builder(this);
                    ab1.setTitle("Player1 win!!!");
                    ab1.setMessage("Do you want to replay?");
                    ab1.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reset_the_board();
                            update_board();
                            dialogInterface.dismiss();
                        }
                    });

                    ab1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    ab1.show();
                    player1points++;
                    update_board();
                    reset_the_board();
                }
            } else if (gridcount == 9) {
                Toast.makeText(MainActivity.this, "Game Drawn", Toast.LENGTH_SHORT).show();
                update_board();
                reset_the_board();
            }
        }
        else
            return;

    }
}
