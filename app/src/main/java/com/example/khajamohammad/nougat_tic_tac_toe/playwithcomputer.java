package com.example.khajamohammad.nougat_tic_tac_toe;

import android.content.DialogInterface;
import android.graphics.Color;
import android.media.PlaybackParams;
import android.media.SoundPool;
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

public class playwithcomputer extends AppCompatActivity implements View.OnClickListener{

    private int player1points=0,player2points=0,gridcount=0,gamecount=0;
    boolean player1turn=true;
    int random_flag=0;
    private int recbut;

    TextView t1,t2;
    String s1="#FF141BE4",s2="#FFFF0000";
    SoundPool soundPool;
    private int sound1,sound2;
    Button s[][]=new Button[3][3];
    String field[][]=new String [3][3];

    @Override
    protected void onStart() {
        player1turn=true;
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playwithcomputer);

        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
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

        }


        return true;
    }

    public boolean checkForWin() {

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
               // ((Button) view).setText("O");
               // ((Button)view).setTextColor(Color.parseColor(s2));
                if(checkForcompwin()){}
                else if(checkForBlock()){}
                else if(checkForstartAttack()){}
                else if(checkForBlockAttack()){}
                player1turn = true;
                soundPool.play(sound2,0,1,0,0,1);
            }


            if (checkForWin()) {

                if (player1turn) {

                    //Toast.makeText(MainActivity.this, "Player2 won the game", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder ab1=new AlertDialog.Builder(this);
                    ab1.setTitle("Computer wins!!!");
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
                    Log.i("Player1","You won the game");
                    //Toast.makeText(MainActivity.this, "Player1 won the game", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder ab1=new AlertDialog.Builder(this);
                    ab1.setTitle("You win!!!");
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
            }
            else
                checkForDraw();

        }
        else
            return;

    }


    public void update_board() {
        t1.setText("YOU : " + player1points + "pts");
        t2.setText("Computer : " + player2points + "pts");
    }

    void reset_the_board() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                s[i][j].setText("");
            }
        }
        player1turn=(gamecount%2==0)?true:false;
        gridcount = 0;
        gamecount++;

    }

    public boolean checkForcompwin() {
        int flag = 0;
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals("") && field[i][1].equals("O") && field[i][2].equals("O")) {
                s[i][0].setText("O");
                flag = 1;
                gridcount++;
                player1turn=!player1turn;
                return true;
            } else if (field[i][1].equals("") && field[i][0].equals("O") && field[i][2].equals("O")) {
                s[i][1].setText("O");
                flag = 1;
                gridcount++;
                player1turn=!player1turn;
                return true;
            } else if (field[i][2].equals("") && field[i][1].equals("O") && field[i][0].equals("O")) {
                s[i][2].setText("O");
                flag = 1;
                gridcount++;
                player1turn=!player1turn;
                return true;
            } else if (field[0][i].equals("") && field[1][i].equals("O") && field[2][i].equals("O")) {
                s[0][i].setText("O");
                flag = 1;
                gridcount++;
                player1turn=!player1turn;
                return true;
            } else if (field[1][i].equals("") && field[2][i].equals("O") && field[0][i].equals("O")) {
                s[i][0].setText("O");
                flag = 1;
                gridcount++;
                player1turn=!player1turn;
                return true;
            } else if (field[2][i].equals("") && field[0][i].equals("O") && field[2][i].equals("O")) {
                s[i][0].setText("O");
                flag = 1;
                gridcount++;
                player1turn=!player1turn;
                return true;
            }

        }
        if (field[0][0].equals("") && field[1][1].equals("O") && field[2][2].equals("O") && flag == 0) {
            s[0][0].setText("O");
            gridcount++;
            player1turn=!player1turn;
            return true;
        } else if (field[1][1].equals("") && field[0][0].equals("O") && field[2][2].equals("O") && flag == 0) {
            s[1][1].setText("O");
            gridcount++;
            player1turn=!player1turn;
            return true;
        }
        else if (field[2][2].equals("") && field[1][1].equals("O") && field[0][0].equals("O") && flag == 0) {
            s[2][2].setText("O");
            gridcount++;
            player1turn=!player1turn;
            return true;
        }

        else if(field[0][2].equals("")&& field[1][1].equals("O") && field[2][0].equals("O") && flag==0) {
            s[0][2].setText("O");
            gridcount++;
            player1turn=!player1turn;
        }
        else if(field[1][1].equals("")&& field[0][2].equals("O") && field[2][0].equals("O") && flag==0) {
            s[1][1].setText("O");
            gridcount++;
            player1turn=!player1turn;
            return true;

        }
        else if(field[2][0].equals("")&& field[1][1].equals("O") && field[0][2].equals("O") && flag==0) {
            s[2][0].setText("O");
            gridcount++;
            player1turn=!player1turn;
            return true;
        }
        checkForDraw();
        return false;
    }
    public boolean checkForBlock()
    {

        int flag=0;
        for(int i=0;i<3;i++)
        {
            if(field[i][0].equals("")&& field[i][1].equals("X") && field[i][2].equals("X")) {
                s[i][0].setText("O");
                flag=1;
                gridcount++;
                player1turn=!player1turn;
                return true;
            }

            else if(field[i][1].equals("")&& field[i][0].equals("X") && field[i][2].equals("X")) {
                s[i][1].setText("O");
                flag=1;
                gridcount++;
                player1turn=!player1turn;
                return true;
            }

            else if(field[i][2].equals("")&& field[i][1].equals("X") && field[i][0].equals("X")) {
                s[i][2].setText("O");
                flag=1;
                gridcount++;
                player1turn=!player1turn;
                return true;
            }

            else if(field[0][i].equals("")&& field[1][i].equals("X") && field[2][i].equals("X")) {
                s[0][i].setText("O");
                flag=1;
                gridcount++;
                player1turn=!player1turn;
                return true;
            }

            else if(field[1][i].equals("")&& field[2][i].equals("X") && field[0][i].equals("X")) {
                s[i][0].setText("O");
                flag=1;
                gridcount++;
                player1turn=!player1turn;
                return true;
            }

            else if(field[2][i].equals("")&& field[0][i].equals("X") && field[2][i].equals("X")) {
                s[i][0].setText("O");
                flag=1;
                gridcount++;
                player1turn=!player1turn;
                return true;
            }

        }
        if(field[0][0].equals("")&& field[1][1].equals("X") && field[2][2].equals("X") && flag==0) {
            s[0][0].setText("O");
            gridcount++;
            player1turn=!player1turn;
            return true;
        }

        else if(field[1][1].equals("")&& field[0][0].equals("X") && field[2][2].equals("X") && flag==0) {
            s[1][1].setText("O");
            gridcount++;
            player1turn=!player1turn;
            return true;
        }
        else if(field[2][2].equals("")&& field[1][1].equals("X") && field[0][0].equals("X") && flag==0) {
            s[2][2].setText("O");
            gridcount++;
            player1turn=!player1turn;
            return true;
        }

        else if(field[0][2].equals("")&& field[1][1].equals("X") && field[2][0].equals("X") && flag==0) {
            s[0][2].setText("O");
            gridcount++;
            player1turn=!player1turn;
            return true;
        }
        else if(field[1][1].equals("")&& field[0][2].equals("X") && field[2][0].equals("X") && flag==0) {
            s[1][1].setText("O");
            gridcount++;
            player1turn=!player1turn;
            return true;
        }
        else if(field[2][0].equals("")&& field[1][1].equals("X") && field[0][2].equals("X") && flag==0) {
            s[2][0].setText("O");
            gridcount++;
            player1turn=!player1turn;
            return true;
        }
        checkForDraw();
        return false;
    }
    public boolean checkForstartAttack()
    {
        if(gridcount==0)
        {
            int ch=(int)((Math.random()*8)+1);
            switch(ch)
            {
                case 1: s[0][0].setText("O");
                        gridcount++;
                        player1turn=!player1turn;
                        return true;

                case 2: s[0][2].setText("O");
                        gridcount++;
                        player1turn=!player1turn;
                        return true;

                case 3: s[1][1].setText("O");
                        gridcount++;
                        player1turn=!player1turn;
                        return true;

                case 4: s[2][0].setText("O");
                        gridcount++;
                        player1turn=!player1turn;
                        return true;

                case 5:s[2][2].setText("O");
                        gridcount++;
                        player1turn=!player1turn;
                        return true;

                case 6:s[0][1].setText("O");
                       gridcount++;
                       player1turn=!player1turn;
                       return true;

                case 7:s[1][0].setText("O");
                    gridcount++;
                    player1turn=!player1turn;
                    return true;

                case 8:s[1][2].setText("O");
                    gridcount++;
                    player1turn=!player1turn;
                    return true;

                case 9:s[2][1].setText("O");
                    gridcount++;
                    player1turn=!player1turn;
                    return true;


            }
        }
        else if(gridcount==1)
        {
            /*int ch=(int)((Math.random()*5)+1);
            switch(ch)
            {
                case 1:s[]
            }*/
            gridcount++;
            if(s[0][0].equals("X") || s[0][2].equals("X") || s[2][2].equals("X") || s[2][0].equals("X")) {
                s[1][1].setText("O");
                return true;
            }

            else if(s[1][1].equals("X"))
            {
                int ch=(int)((Math.random()*4)+1);
                switch (ch)
                {
                    case 1:s[0][0].setText("O");
                           return true;
                    case 2:s[0][2].setText("O");
                           return true;
                    case 3:s[2][0].setText("O");
                           return true;
                    case 4:s[2][2].setText("O");
                           return true;
                }
            }
            else
            {
                while(random_flag==0) {
                    int ch1 = (int) ((Math.random() * 3));
                    int ch2 = (int) ((Math.random() * 3));
                    if (s[ch1][ch2].equals("")) {
                        s[ch1][ch2].setText("O");
                        random_flag = 1;
                        return true;
                    }
                }
            }

        }
        else if(gridcount==2) {
            gridcount++;
            if (s[1][1].equals("O") && s[2][2].equals("X")) {
                s[0][0].setText("O");
                return true;
            } else if (s[0][0].equals("X") && s[1][1].equals("O")){
                s[2][2].setText("O");
            return true;
        }
            else if(s[0][2].equals("X") && s[1][1].equals("O")) {
                s[2][0].setText("O");
                return true;
            }
            else if(s[2][0].equals("O") && s[1][1].equals("X")) {
                s[0][2].setText("O");
                return true;
            }

            else if(s[1][1].equals("X"))
            {
                if(s[0][0].equals("O")) {
                    s[2][2].setText("O");
                    return true;
                }
                else if(s[2][2].equals("O")) {
                    s[0][0].setText("O");
                    return true;
                }
                else if(s[0][2].equals("O")) {
                    s[2][0].setText("O");
                    return true;
                }
                else if(s[2][0].equals("O")) {
                    s[0][2].setText("O");
                    return true;
                }
            }
            else if(s[0][1].equals("O")) {

                if (s[0][0].equals("") && s[2][0].equals("")) {
                    s[2][0].setText("O");
                    return true;
                }
                else if (s[0][2].equals("") && s[2][2].equals("")) {
                    s[2][2].setText("O");
                    return true;
                }
            }
            else if(s[1][2].equals("O"))
            {
                if(s[0][0].equals("") && s[0][2].equals(""))
                    s[0][0].setText("O");
                else if(s[2][2].equals("") && s[2][0].equals(""))
                    s[2][0].setText("O");
            }
            else if(s[2][1].equals("O"))
            {
                if(s[2][0].equals("") && s[0][0].equals("")) {
                    s[0][0].setText("O");
                    return true;
                }
                else if(s[2][2].equals("") && s[0][2].equals("")) {
                    s[0][2].setText("O");
                    return true;
                }
            }
            else if(s[1][0].equals(""))
            {
                if(s[0][0].equals("") && s[0][2].equals("")) {
                    s[0][2].setText("O");
                    return true;
                }
                else if(s[2][0].equals("") && s[2][2].equals("")) {
                    s[2][2].setText("O");
                    return true;
                }
            }
            else
            {
                random_flag=0;
                while(random_flag==0) {
                    int ch1 = (int) ((Math.random() * 3));
                    int ch2 = (int) ((Math.random() * 3));
                    if (s[ch1][ch2].equals(""))
                    {
                        s[ch1][ch2].setText("O");
                        random_flag=1;
                        return true;
                    }
                }

            }

        }
        else if(gridcount>=4)
        {
            gridcount++;
            if(s[0][1].equals("O") && s[2][0].equals("O"))
                if(s[0][0].equals("")) {
                    s[0][0].setText("O");
                    return true;
                }

            else if(s[0][1].equals("O") && s[2][2].equals("O"))
                if(s[0][2].equals("")) {
                    s[0][2].setText("O");
                    return true;
                }

            else if(s[1][2].equals("O") && s[0][0].equals("O"))
                if(s[0][2].equals("")) {
                    s[0][2].setText("O");
                    return true;
                }

            else if(s[1][2].equals("O") && s[2][0].equals("O"))
                if(s[2][2].equals("")) {
                    s[2][2].setText("O");
                    return true;
                }

            else if(s[2][1].equals("O") && s[0][0].equals("O"))
                if(s[2][0].equals("")) {
                    s[2][0].setText("O");
                    return true;
                }

            else if(s[2][1].equals("O") && s[0][2].equals("O"))
                if(s[2][2].equals("")) {
                    s[2][2].setText("O");
                    return true;
                }

            else if(s[1][0].equals("O") && s[0][2].equals("O"))
                if(s[0][0].equals("")) {
                    s[0][0].setText("O");
                    return true;
                }

            else if(s[1][0].equals("O") && s[2][2].equals("O"))
                if(s[2][0].equals("")) {
                    s[2][0].setText("O");
                    return true;
                }




            else if(s[0][0].equals("O") && s[2][2].equals("O"))
                if(s[1][1].equals("")) {
                    s[1][1].setText("O");
                    return true;
                }
                else if(s[0][2].equals("")) {
                    s[0][2].setText("O");
                    return true;
                }
                else if(s[2][0].equals("")) {
                    s[2][0].equals("O");
                    return true;
                }

            else if(s[0][2].equals("O") && s[2][0].equals("O"))
                if(s[1][1].equals("")) {
                    s[1][1].setText("O");
                    return true;
                }
                else if(s[0][0].equals("")) {
                    s[0][0].setText("O");
                    return true;
                }
                else if(s[2][2].equals("")) {
                    s[2][2].setText("O");
                    return true;
                }


            else if(s[0][0].equals("O") && s[1][1].equals("O"))
                {
                    if(s[2][2].equals("")) {
                        s[2][2].setText("O");
                        return true;
                    }
                    else if(s[2][2].equals("X")) {
                        s[0][1].setText("O");
                        return true;
                    }
                }
            else if(s[0][2].equals("O") && s[1][1].equals("O")) {
                    if (s[2][0].equals("")) {
                        s[2][0].setText("O");
                        return true;
                    } else if (s[2][0].equals("X")) {
                        s[0][1].setText("O");
                        return true;
                    }
                }
                else if(s[1][1].equals("O") && s[2][0].equals("O"))
                {
                    if(s[0][2].equals("")) {
                        s[0][2].equals("O");
                        return true;
                    }
                    else if(s[0][2].equals("X")) {
                        s[2][1].setText("O");
                        return true;
                    }
                }
                else if(s[1][1].equals("O") && s[2][2].equals("O"))
                {
                    if(s[0][0].equals("")) {
                        s[0][0].setText("O");
                        return true;
                    }
                    else if(s[0][0].equals("X")) {
                        s[2][1].setText("O");
                        return true;
                    }
                }


        }
        return false;
    }

    public boolean checkForBlockAttack()
    {
        if(gridcount==3)
        {
             if(s[0][1].equals("X") && s[2][0].equals("X")) {
                 if (s[0][0].equals("")) {
                     s[0][0].setText("O");
                     gridcount++;
                     return true;
                 }
                 else if (s[1][1].equals("")) {
                     s[1][1].equals("O");
                     gridcount++;
                     return true;
                 }
             }
             else if(s[0][1].equals("X") && s[2][2].equals("X"))
             {

                 if(s[0][2].equals("")) {
                     s[0][2].setText("O");
                     gridcount++;
                     return true;
                 }
                 else if(s[1][1].equals("")) {
                     s[1][1].setText("O");
                     gridcount++;
                     return true;
                 }
             }
             else if(s[1][2].equals("X") && s[0][0].equals("X"))
             {
                 if(s[0][2].equals("")) {
                     s[0][2].setText("O");
                     return true;
                 }
                 else if(s[1][1].equals("")) {
                     s[1][1].setText("O");
                     return true;
                 }
             }
             else if(s[1][2].equals("X") && s[2][0].equals("X"))
             {
                 if(s[2][2].equals("")) {
                     s[2][2].setText("O");
                     return true;
                 }
                 else if(s[1][1].equals("")) {
                     s[1][1].setText("O");
                     return true;
                 }
             }
             else if(s[2][1].equals("X") && s[0][0].equals("X"))
             {
                 if(s[2][0].equals("")) {
                     s[2][0].setText("O");
                     gridcount++;
                     return true;
                 }
                 else if(s[1][1].equals("")) {
                     s[1][1].setText("O");
                     gridcount++;
                     return true;
                 }
             }
             else if(s[2][1].equals("X") && s[0][2].equals("X"))
             {
                 if(s[2][2].equals("")) {
                     s[2][2].setText("O");
                     return true;
                 }
                 else if(s[1][1].equals("")) {
                     s[1][1].setText("O");
                     return true;
                 }
             }
             else if(s[1][0].equals("X") && s[0][2].equals("X"))
             {
                 if(s[0][0].equals("")) {
                     s[0][0].setText("O");
                     return true;
                 }
                 else if(s[1][1].equals("")) {
                     s[1][1].setText("O");
                     return true;
                 }
             }
             else if(s[1][0].equals("X") && s[2][2].equals("X"))
             {
                 if(s[2][0].equals("")) {
                     s[2][0].setText("O");
                     gridcount++;
                     return true;
                 }
                 else if(s[1][1].equals("")) {
                     s[1][1].setText("O");
                     gridcount++;
                     return true;
                 }
             }


             else if(s[0][0].equals("X") && s[1][1].equals("X"))
                 if(s[0][2].equals("")) {
                     s[0][2].setText("O");
                     gridcount++;
                     return true;
                 }

             else if(s[1][1].equals("X") && s[2][2].equals("X"))
                 if(s[2][0].equals("")) {
                     s[2][0].setText("O");
                     gridcount++;
                     return true;
                 }

             else if(s[0][2].equals("X") && s[1][1].equals("X"))
                 if(s[0][0].equals("")) {
                     s[0][0].setText("O");
                     gridcount++;
                     return true;
                 }

             else if(s[1][1].equals("X") && s[2][0].equals("X"))
                 if(s[2][2].equals("")) {
                     s[2][2].setText("O");
                     gridcount++;
                     return true;
                 }


             else if(s[0][0].equals("X") && s[2][2].equals("X") && s[1][1].equals("O") || s[0][2].equals("X") && s[2][0].equals("X") && s[1][1].equals("O"))
                 {
                     int n=(int)((Math.random()*4)+1);
                     switch (n)
                     {
                         case 1:if(s[0][1].equals(""))
                                    s[0][1].setText("O");
                                     gridcount++;
                                     return true;


                         case 2:if(s[1][2].equals(""))
                                  s[1][2].setText("O");
                                  gridcount++;
                                   return true;

                         case 3:if(s[2][1].equals(""))
                                 s[2][1].equals("O");
                                 gridcount++;
                                 return true;

                         case 4:if(s[1][0].equals(""))
                                   s[1][0].setText("O");
                                   gridcount++;
                                   return true;
                     }
                 }
        }
        return false;
    }
    public  void checkForDraw()
    {
        if (gridcount == 9) {
            Toast.makeText(playwithcomputer.this, "Game Drawn", Toast.LENGTH_SHORT).show();
            update_board();
            reset_the_board();
        }
    }
}
