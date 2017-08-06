package com.game.training.programmer.android.tic_tac_toe;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

public class TicTacToe2 extends AppCompatActivity implements View.OnTouchListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    private ConstraintLayout constraintLayout_parent;
    private GameLogic view_gameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

        constraintLayout_parent = (ConstraintLayout) findViewById(R.id.constraintLayout_parent_activity_main);
        view_gameField = (GameLogic) findViewById(R.id.view_gameField_activity_main);
        ViewTreeObserver viewTreeObserver = view_gameField.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(this);
        view_gameField.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        view_gameField.defineAndSetTouchCell(x, y);
        view_gameField.invalidate();
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
}
