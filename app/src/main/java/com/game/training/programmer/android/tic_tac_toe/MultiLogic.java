package com.game.training.programmer.android.tic_tac_toe;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

    private Socket fromServerSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Handler handler;

    private ConstraintLayout constraintLayout_parent;
    private TicTacToeDrawer view_gameField;

    private boolean opponent;
    private int figureCode;


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

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String s = msg.obj.toString();
                f(s);
            }
        };
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        //out.println();
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

                String input;
                try {
                    while ((input = in.readLine()) != null)  {
                        handler.sendMessage(handler.obtainMessage(0, input));
                    }
                } catch (IOException e) {

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

    private void f(String pack) {
        Log.d("---My Log---", pack);
        switch (pack) {
            case "server:opponent:true":
                opponent = true;
                break;
            case "server:figure:zero":
                figureCode = 0;
                break;
            case "server:figure:cross":
                figureCode = 1;
                break;
        }
    }
}
