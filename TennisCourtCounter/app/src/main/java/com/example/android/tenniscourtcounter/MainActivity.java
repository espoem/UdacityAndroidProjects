package com.example.android.tenniscourtcounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private int gScoreTeamA = 0;
    private int gScoreTeamB = 0;
    private int[] setsTeamA = new int[3];
    private int[] setsTeamB = new int[3];
    private int setsWonTeamA = 0;
    private int setsWonTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("scoreTeamA", gScoreTeamA);
        outState.putInt("scoreTeamB", gScoreTeamB);
        outState.putIntArray("setsTeamA", setsTeamA);
        outState.putIntArray("setsTeamB", setsTeamB);
        outState.putInt("setsWonTeamA", setsWonTeamA);
        outState.putInt("setsWonTeamB", setsWonTeamB);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        gScoreTeamA = savedInstanceState.getInt("scoreTeamA");
        gScoreTeamB = savedInstanceState.getInt("scoreTeamB");
        setsTeamA = savedInstanceState.getIntArray("setsTeamA");
        setsTeamB = savedInstanceState.getIntArray("setsTeamB");
        setsWonTeamA = savedInstanceState.getInt("setsWonTeamA");
        setsWonTeamB = savedInstanceState.getInt("setsWonTeamB");

        displayScoreTeamA(gScoreTeamA);
        displayScoreTeamB(gScoreTeamB);
        displayGamesTeamA(setsTeamA);
        displayGamesTeamB(setsTeamB);
    }

    private void addGameScoreToTeamA(View view) {
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

    private void addGameScoreToTeamB(View view) {
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

    private void addTiebreakScoreToTeamA(View view) {
        if (gScoreTeamA >= 6 && gScoreTeamA - gScoreTeamB > 0) {
            addGameToTeamA(view);
            gScoreTeamA = 0;
            gScoreTeamB = 0;
            displayScoreTeamB(gScoreTeamB);
        } else {
            gScoreTeamA++;
        }
        displayScoreTeamA(gScoreTeamA);
    }

    private void addTiebreakScoreToTeamB(View view) {
        if (gScoreTeamB >= 6 && gScoreTeamB - gScoreTeamA > 0) {
            addGameToTeamB(view);
            gScoreTeamA = 0;
            gScoreTeamB = 0;
            displayScoreTeamA(gScoreTeamA);
        } else {
            gScoreTeamB++;
        }
        displayScoreTeamB(gScoreTeamB);
    }

    public void addScoreToTeamA(View view) {
        int currentSet = setsWonTeamA + setsWonTeamB;
        if (setsWonTeamA < 2 && setsWonTeamB < 2) {
            if (setsTeamA[currentSet] == 6 && setsTeamB[currentSet] == 6) {
                addTiebreakScoreToTeamA(view);
            } else {
                addGameScoreToTeamA(view);
            }
        }
        if (setsWonTeamA == 2) {
            Toast.makeText(this, "Team A won", Toast.LENGTH_SHORT).show();
        }
    }

    public void addScoreToTeamB(View view) {
        int currentSet = setsWonTeamA + setsWonTeamB;
        if (setsWonTeamA < 2 && setsWonTeamB < 2) {
            if (setsTeamA[currentSet] == 6 && setsTeamB[currentSet] == 6) {
                addTiebreakScoreToTeamB(view);
            } else {
                addGameScoreToTeamB(view);
            }
        }
        if (setsWonTeamB == 2) {
            Toast.makeText(this, "Team B won", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Add a winning game to team A.
     *
     * @param view
     */
    public void addGameToTeamA(View view) {
        int currentSet = setsWonTeamA + setsWonTeamB;
        if (canAddGameToTeam(currentSet)) {
            setsTeamA[currentSet]++;
            if (teamAWonSet(currentSet)) {
                setsWonTeamA++;
            }
        }
        displayGamesTeamA(setsTeamA);
    }

    private boolean canAddGameToTeam(int currentSet) {
        return (setsTeamA[currentSet] < 6 && setsTeamB[currentSet] < 6) ||
                (Math.abs(setsTeamA[currentSet] - setsTeamB[currentSet]) < 2);
    }

    private boolean teamAWonSet(int currentSet) {
        return (setsTeamA[currentSet] == 7) || (setsTeamA[currentSet] == 6 &&
                Math.abs(setsTeamA[currentSet] - setsTeamB[currentSet]) > 1);
    }

    private boolean teamBWonSet(int currentSet) {
        return (setsTeamB[currentSet] == 7) || (setsTeamB[currentSet] == 6 &&
                Math.abs(setsTeamA[currentSet] - setsTeamB[currentSet]) > 1);
    }

    /**
     * Add a winning game to team B.
     *
     * @param view
     */
    public void addGameToTeamB(View view) {
        int currentSet = setsWonTeamA + setsWonTeamB;
        if (canAddGameToTeam(currentSet)) {
            setsTeamB[currentSet]++;
            if (teamBWonSet(currentSet)) {
                setsWonTeamB++;
            }
        }
        displayGamesTeamB(setsTeamB);
    }

    /**
     * Reset game.
     *
     * @param view
     */
    public void newGame(View view) {
        Arrays.fill(setsTeamA, 0);
        Arrays.fill(setsTeamB, 0);
        gScoreTeamA = 0;
        gScoreTeamB = 0;
        setsWonTeamA = 0;
        setsWonTeamB = 0;

        displayGamesTeamA(setsTeamA);
        displayGamesTeamB(setsTeamB);
        displayScoreTeamA(gScoreTeamA);
        displayScoreTeamB(gScoreTeamB);

        Toast.makeText(this, "The game has been reset!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display score in game for team A.
     *
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
     *
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
     *
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
     *
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
