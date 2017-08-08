package com.game.training.programmer.android.tic_tac_toe;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

public class SingleTicTacToe extends AppCompatActivity implements View.OnTouchListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    private static final int TOP_LEFT_CELL = 1;
    private static final int TOP_CENTER_CELL = 2;
    private static final int TOP_RIGHT_CELL = 3;
    private static final int MID_LEFT_CELL = 4;
    private static final int MID_CENTER_CELL = 5;
    private static final int MID_RIGHT_CELL = 6;
    private static final int BOTTOM_LEFT_CELL = 7;
    private static final int BOTTOM_CENTER_CELL = 8;
    private static final int BOTTOM_RIGHT_CELL = 9;

    private ConstraintLayout constraintLayout_parent;
    private TicTacToeDrawer view_gameField;

    private boolean myStep = true;
    private boolean computerStep = false;
    private int playerFigureCode = 1;
    private int computerFigureCode = 0;
    private int countComputerStep = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

        constraintLayout_parent = (ConstraintLayout) findViewById(R.id.constraintLayout_parent_activity_main);
        constraintLayout_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doComputerStep();
            }
        });
        view_gameField = (TicTacToeDrawer) findViewById(R.id.view_gameField_activity_main);
        ViewTreeObserver viewTreeObserver = view_gameField.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(this);
        view_gameField.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        doMyStep(x, y);

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

    private void doMyStep(float x, float y) {
        if (myStep) {
            myStep = false;
            int cell = view_gameField.defineTouchCell(x, y);
            view_gameField.setCellNumber(cell);

            if (isCellFree(cell)) {
                view_gameField.setFigureCode(playerFigureCode);
                view_gameField.invalidate();
                computerStep = true;
            }
        }
    }

    private void doComputerStep() {
        int cell;
        if (computerStep) {
            computerStep = false;
            if (countComputerStep == 1) {
                if (isCellFree(MID_CENTER_CELL)) {
                    view_gameField.setCellNumber(MID_CENTER_CELL);
                    view_gameField.setFigureCode(computerFigureCode);
                    view_gameField.invalidate();
                } else {
                    view_gameField.setCellNumber(BOTTOM_LEFT_CELL);
                    view_gameField.setFigureCode(computerFigureCode);
                    view_gameField.invalidate();
                }
                countComputerStep++;
            } else if (countComputerStep > 1) {
                cell = checkLinesOnVictory();
                view_gameField.setCellNumber(cell);
                view_gameField.setFigureCode(computerFigureCode);
                view_gameField.invalidate();
            }

            myStep = true;
        }
    }

    private boolean isCellFree(int cell) {
        return view_gameField.getCellState(cell) == 0;
    }

    private int checkLinesOnVictory() {
        int free = 0; // free cell state
        int var; // state of cell with player figure
        if (playerFigureCode == 1) {
            var = 2; // cross code
        } else {
            var = 1; // zero code
        }

        if (view_gameField.getCellState(TOP_LEFT_CELL) == var && view_gameField.getCellState(TOP_CENTER_CELL) == var) {
            if (view_gameField.getCellState(TOP_RIGHT_CELL) == free) {
                return TOP_RIGHT_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_RIGHT_CELL) == var && view_gameField.getCellState(TOP_CENTER_CELL) == var) {
            if (view_gameField.getCellState(TOP_LEFT_CELL) == free) {
                return TOP_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(MID_LEFT_CELL) == var && view_gameField.getCellState(MID_CENTER_CELL) == var) {
            if (view_gameField.getCellState(MID_RIGHT_CELL) == free) {
                return MID_RIGHT_CELL;
            }
        }

        if (view_gameField.getCellState(MID_RIGHT_CELL) == var && view_gameField.getCellState(MID_CENTER_CELL) == var) {
            if (view_gameField.getCellState(MID_LEFT_CELL) == free) {
                return MID_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_LEFT_CELL) == var && view_gameField.getCellState(BOTTOM_CENTER_CELL) == var) {
            if (view_gameField.getCellState(BOTTOM_RIGHT_CELL) == free) {
                return BOTTOM_RIGHT_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_RIGHT_CELL) == var && view_gameField.getCellState(BOTTOM_CENTER_CELL) == var) {
            if (view_gameField.getCellState(BOTTOM_LEFT_CELL) == free) {
                return BOTTOM_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_LEFT_CELL) == var && view_gameField.getCellState(MID_LEFT_CELL) == var) {
            if (view_gameField.getCellState(BOTTOM_LEFT_CELL) == free) {
                return BOTTOM_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_LEFT_CELL) == var && view_gameField.getCellState(MID_LEFT_CELL) == var) {
            if (view_gameField.getCellState(TOP_LEFT_CELL) == free) {
                return TOP_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_CENTER_CELL) == var && view_gameField.getCellState(MID_CENTER_CELL) == var) {
            if (view_gameField.getCellState(BOTTOM_CENTER_CELL) == free) {
                return BOTTOM_CENTER_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_CENTER_CELL) == var && view_gameField.getCellState(MID_CENTER_CELL) == var) {
            if (view_gameField.getCellState(TOP_CENTER_CELL) == free) {
                return TOP_CENTER_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_RIGHT_CELL) == var && view_gameField.getCellState(MID_RIGHT_CELL) == var) {
            if (view_gameField.getCellState(BOTTOM_RIGHT_CELL) == free) {
                return BOTTOM_RIGHT_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_RIGHT_CELL) == var && view_gameField.getCellState(MID_RIGHT_CELL) == var) {
            if (view_gameField.getCellState(TOP_RIGHT_CELL) == free) {
                return TOP_RIGHT_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_LEFT_CELL) == var && view_gameField.getCellState(MID_CENTER_CELL) == var) {
            if (view_gameField.getCellState(BOTTOM_RIGHT_CELL) == free) {
                return BOTTOM_RIGHT_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_RIGHT_CELL) == var && view_gameField.getCellState(MID_CENTER_CELL) == var) {
            if (view_gameField.getCellState(TOP_LEFT_CELL) == free) {
                return TOP_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_RIGHT_CELL) == var && view_gameField.getCellState(MID_CENTER_CELL) == var) {
            if (view_gameField.getCellState(BOTTOM_LEFT_CELL) == free) {
                return BOTTOM_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_LEFT_CELL) == var && view_gameField.getCellState(MID_CENTER_CELL) == var) {
            if (view_gameField.getCellState(TOP_RIGHT_CELL) == free) {
                return TOP_RIGHT_CELL;
            }
        }

        return 0;
    }
}
