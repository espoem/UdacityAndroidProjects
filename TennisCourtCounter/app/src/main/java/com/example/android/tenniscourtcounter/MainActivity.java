package com.example.android.tenniscourtcounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int gScoreTeamA = 0;
    private int gScoreTeamB = 0;
    private int[] setsTeamA = new int[3];
    private int[] setsTeamB = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addScoreToTeamA(View view) {
        switch (gScoreTeamA) {
            case 0:
                gScoreTeamA += 15;
                break;
            case 15:
                gScoreTeamA += 15;
                break;
            case 30:
                gScoreTeamA += 10;
                break;
            case 40:
                if (gScoreTeamB > 40) {
                    gScoreTeamB -= 10;
                    displayScoreTeamB(gScoreTeamB);
                } else if (gScoreTeamB == 40) {
                    gScoreTeamA += 10;
                } else {
                    addGameToTeamA(view);
                    gScoreTeamA = 0;
                    gScoreTeamB = 0;
                    displayScoreTeamB(gScoreTeamB);
                }
                break;
            case 50:
                addGameToTeamA(view);
                gScoreTeamA = 0;
                gScoreTeamB = 0;
                displayScoreTeamB(gScoreTeamB);
            default:
                break;
        }
        displayScoreTeamA(gScoreTeamA);
    }

    public void addScoreToTeamB(View view) {
        switch (gScoreTeamB) {
            case 0:
                gScoreTeamB += 15;
                break;
            case 15:
                gScoreTeamB += 15;
                break;
            case 30:
                gScoreTeamB += 10;
                break;
            case 40:
                if (gScoreTeamA > 40) {
                    gScoreTeamA -= 10;
                    displayScoreTeamA(gScoreTeamA);
                } else if (gScoreTeamA == 40) {
                    gScoreTeamB += 10;
                } else {
                    addGameToTeamB(view);
                    gScoreTeamA = 0;
                    gScoreTeamB = 0;
                    displayScoreTeamA(gScoreTeamA);
                }
                break;
            case 50:
                addGameToTeamB(view);
                gScoreTeamA = 0;
                gScoreTeamB = 0;
                displayScoreTeamA(gScoreTeamA);
            default:
                break;
        }
        displayScoreTeamB(gScoreTeamB);
    }

    /**
     * Add a winning game to team A.
     * @param view
     */
    public void addGameToTeamA(View view) {
        if ((setsTeamA[0] < 6 && setsTeamB[0] < 6) || (Math.abs(setsTeamA[0] - setsTeamB[0]) <
                2)) {
            setsTeamA[0]++;
        } else if ((setsTeamA[1] < 6 && setsTeamB[1] < 6) || Math.abs(setsTeamA[1] -
                setsTeamB[1]) < 2) {
            setsTeamA[1]++;
        } else if ((setsTeamA[2] < 6 && setsTeamB[2] < 6) || Math.abs(setsTeamA[2] -
                setsTeamB[2]) <
                2) {
            setsTeamA[2]++;
        }
        displayGamesTeamA(setsTeamA);
    }

    /**
     * Add a winning game to team B.
     * @param view
     */
    public void addGameToTeamB(View view) {
        if ((setsTeamB[0] < 6 && setsTeamA[0] < 6) || Math.abs(setsTeamA[0] - setsTeamB[0]) < 2) {
            setsTeamB[0]++;
        } else if ((setsTeamB[1] < 6 && setsTeamA[1] < 6) || Math.abs(setsTeamA[1] -
                setsTeamB[1]) <
                2) {
            setsTeamB[1]++;
        } else if ((setsTeamB[2] < 6 && setsTeamA[2] < 6) || Math.abs(setsTeamA[2] -
                setsTeamB[2]) < 2) {
            setsTeamB[2]++;
        }
        displayGamesTeamB(setsTeamB);
    }

    /**
     * Reset game.
     * @param view
     */
    public void newGame(View view) {
        setsTeamA[0] = 0;
        setsTeamA[1] = 0;
        setsTeamA[2] = 0;
        setsTeamB[0] = 0;
        setsTeamB[1] = 0;
        setsTeamB[2] = 0;
        gScoreTeamA = 0;
        gScoreTeamB = 0;

        displayGamesTeamA(setsTeamA);
        displayGamesTeamB(setsTeamB);
        displayScoreTeamA(gScoreTeamA);
        displayScoreTeamB(gScoreTeamB);
    }

    /**
     * Display score in game for team A.
     * @param score
     */
    public void displayScoreTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.game_score_team_a);
        if (score > 40) {
            scoreView.setText(String.valueOf("Ad"));
        } else {
            scoreView.setText(String.valueOf(score));
        }
    }

    /**
     * Display score in game for team B.
     * @param score
     */
    public void displayScoreTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.game_score_team_b);
        if (score > 40) {
            scoreView.setText(String.valueOf("Ad"));
        } else {
            scoreView.setText(String.valueOf(score));
        }
    }

    /**
     * Display games for team A.
     * @param sets
     */
    public void displayGamesTeamA(int[] sets) {
        TextView setOneView = (TextView) findViewById(R.id.set_one_team_a);
        TextView setTwoView = (TextView) findViewById(R.id.set_two_team_a);
        TextView setThreeView = (TextView) findViewById(R.id.set_three_team_a);
        setOneView.setText(String.valueOf(sets[0]));
        setTwoView.setText(String.valueOf(sets[1]));
        setThreeView.setText(String.valueOf(sets[2]));
    }

    /**
     * Display games for team B.
     * @param sets
     */
    public void displayGamesTeamB(int[] sets) {
        TextView setOneView = (TextView) findViewById(R.id.set_one_team_b);
        TextView setTwoView = (TextView) findViewById(R.id.set_two_team_b);
        TextView setThreeView = (TextView) findViewById(R.id.set_three_team_b);
        setOneView.setText(String.valueOf(sets[0]));
        setTwoView.setText(String.valueOf(sets[1]));
        setThreeView.setText(String.valueOf(sets[2]));
    }
}
