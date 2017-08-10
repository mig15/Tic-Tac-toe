package com.game.training.programmer.android.tic_tac_toe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiLogic extends AppCompatActivity implements View.OnTouchListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    private static final String HOST_IP = "192.168.221.178";
    private static final String OPPONENT_VALUE = "opponent";
    private static final String FIGURE_VALUE = "figure";

    private Socket fromServerSocket;
    private BufferedReader in;
    private PrintWriter out;

    private ConstraintLayout constraintLayout_parent;
    private TicTacToeDrawer view_gameField;

    private boolean opponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

        constraintLayout_parent = (ConstraintLayout) findViewById(R.id.constraintLayout_parent_activity_main);
        view_gameField = (TicTacToeDrawer) findViewById(R.id.view_gameField_activity_main);
        ViewTreeObserver viewTreeObserver = view_gameField.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(this);
        view_gameField.setOnTouchListener(this);

        runConnection();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        out.println();
        return true;
    }

    @Override
    public void onGlobalLayout() {
        int height = view_gameField.getWidth();
        ConstraintSet set = new ConstraintSet();
        set.clone(constraintLayout_parent);
        set.constrainHeight(R.id.view_gameField_activity_main, height);
        set.applyTo(constraintLayout_parent);
        view_gameField.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    private void runConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fromServerSocket = new Socket(HOST_IP, 9954);
                    in = new BufferedReader(new InputStreamReader(fromServerSocket.getInputStream()));
                    out = new PrintWriter(fromServerSocket.getOutputStream(), true);
                } catch (IOException e) {

                }

                String input = "";
                while (true) {
                    try {
                        // TODO
                        input = in.readLine();
                    } catch (IOException e) {

                    }

                    if (input.equalsIgnoreCase("Game Over")) {
                        break;
                    } else if (input.equals(OPPONENT_VALUE) && input.equals(FIGURE_VALUE)) {
                        Log.d("---My Log---", input);
                        break;
                    }
                }

                closeConnection();
            }
        }).start();
    }

    private void closeConnection() {
        try {
            out.println("Game Over");
            fromServerSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {

        }
    }
}
