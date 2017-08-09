package com.game.training.programmer.android.tic_tac_toe;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.Random;

public class SingleLogic extends AppCompatActivity implements View.OnTouchListener,
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

    private Random random;

    private ConstraintLayout constraintLayout_parent;
    private TicTacToeDrawer view_gameField;

    private ArrayList<Integer> arrayFreeCell;
    private boolean myStep = true;
    private boolean computerStep = false;
    private int playerFigureCode = 1;
    private int computerFigureCode = 0;
    private int countComputerStep = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

        random = new Random();

        constraintLayout_parent = (ConstraintLayout) findViewById(R.id.constraintLayout_parent_activity_main);
        constraintLayout_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doNextComputerStep();
            }
        });
        view_gameField = (TicTacToeDrawer) findViewById(R.id.view_gameField_activity_main);
        ViewTreeObserver viewTreeObserver = view_gameField.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(this);
        view_gameField.setOnTouchListener(this);

        arrayFreeCell = new ArrayList<>();
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

    private void doNextComputerStep() {
        if (computerStep) {
            computerStep = false;

            if (countComputerStep == 1) {
                doComputerStepOne();
            }

            if (countComputerStep == 2) {
                doComputerStepTwo();
            }

            if (countComputerStep >= 3) {
                doFollowingCompSteps();
            }

            countComputerStep++;
            myStep = true;
        }
    }

    private void doComputerStepOne() {
        if (computerFigureCode == 0) {
            if (isCellFree(MID_CENTER_CELL)) {
                view_gameField.setCellNumber(MID_CENTER_CELL);
                view_gameField.setFigureCode(computerFigureCode);
                view_gameField.invalidate();
            } else {
                view_gameField.setCellNumber(BOTTOM_LEFT_CELL);
                view_gameField.setFigureCode(computerFigureCode);
                view_gameField.invalidate();
            }
        }
    }

    private void doComputerStepTwo() {
        if (computerFigureCode == 0) {
            int cell = checkLinesForVictory(TicTacToeDrawer.STATE_OF_CELL_WITH_CROSS);
            if (cell != 0) {
                view_gameField.setCellNumber(cell);
                view_gameField.setFigureCode(computerFigureCode);
                view_gameField.invalidate();
            } else {
                if ((view_gameField.getCellState(TOP_LEFT_CELL) == TicTacToeDrawer.STATE_OF_CELL_WITH_CROSS
                        && view_gameField.getCellState(BOTTOM_RIGHT_CELL) == TicTacToeDrawer.STATE_OF_CELL_WITH_CROSS)
                        || (view_gameField.getCellState(TOP_RIGHT_CELL) == TicTacToeDrawer.STATE_OF_CELL_WITH_CROSS
                        && view_gameField.getCellState(BOTTOM_LEFT_CELL) == TicTacToeDrawer.STATE_OF_CELL_WITH_CROSS)) {
                    view_gameField.setCellNumber(MID_RIGHT_CELL);
                    view_gameField.setFigureCode(computerFigureCode);
                    view_gameField.invalidate();
                } else if (view_gameField.getCellState(TOP_LEFT_CELL) == TicTacToeDrawer.STATE_OF_CELL_WITH_CROSS
                        && view_gameField.getCellState(MID_RIGHT_CELL) == TicTacToeDrawer.STATE_OF_CELL_WITH_CROSS) {
                    view_gameField.setCellNumber(TOP_RIGHT_CELL);
                    view_gameField.setFigureCode(computerFigureCode);
                    view_gameField.invalidate();
                } else {
                    int var = getFreeCellForNextStep();
                    view_gameField.setCellNumber(var);
                    view_gameField.setFigureCode(computerFigureCode);
                    view_gameField.invalidate();
                }
            }
        }
    }

    private void doFollowingCompSteps() {
        if (computerFigureCode == 0) {
            int cell = checkLinesForVictory(TicTacToeDrawer.STATE_OF_CELL_WITH_ZERO);
            if (cell != 0) {
                view_gameField.setCellNumber(cell);
                view_gameField.setFigureCode(computerFigureCode);
                view_gameField.invalidate();
            } else {
                cell = checkLinesForVictory(TicTacToeDrawer.STATE_OF_CELL_WITH_CROSS);
                if (cell != 0) {
                    view_gameField.setCellNumber(cell);
                    view_gameField.setFigureCode(computerFigureCode);
                    view_gameField.invalidate();
                } else {
                    int var = getFreeCellForNextStep();
                    view_gameField.setCellNumber(var);
                    view_gameField.setFigureCode(computerFigureCode);
                    view_gameField.invalidate();
                }
            }
        }
    }

    private int checkLinesForVictory(int figureCode) {
        if (view_gameField.getCellState(TOP_LEFT_CELL) == figureCode && view_gameField.getCellState(TOP_CENTER_CELL) == figureCode) {
            if (isCellFree(TOP_RIGHT_CELL)) {
                return TOP_RIGHT_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_RIGHT_CELL) == figureCode && view_gameField.getCellState(TOP_CENTER_CELL) == figureCode) {
            if (isCellFree(TOP_LEFT_CELL)) {
                return TOP_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(MID_LEFT_CELL) == figureCode && view_gameField.getCellState(MID_CENTER_CELL) == figureCode) {
            if (isCellFree(MID_RIGHT_CELL)) {
                return MID_RIGHT_CELL;
            }
        }

        if (view_gameField.getCellState(MID_RIGHT_CELL) == figureCode && view_gameField.getCellState(MID_CENTER_CELL) == figureCode) {
            if (isCellFree(MID_LEFT_CELL)) {
                return MID_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_LEFT_CELL) == figureCode && view_gameField.getCellState(BOTTOM_CENTER_CELL) == figureCode) {
            if (isCellFree(BOTTOM_RIGHT_CELL)) {
                return BOTTOM_RIGHT_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_RIGHT_CELL) == figureCode && view_gameField.getCellState(BOTTOM_CENTER_CELL) == figureCode) {
            if (isCellFree(BOTTOM_LEFT_CELL)) {
                return BOTTOM_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_LEFT_CELL) == figureCode && view_gameField.getCellState(MID_LEFT_CELL) == figureCode) {
            if (isCellFree(BOTTOM_LEFT_CELL)) {
                return BOTTOM_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_LEFT_CELL) == figureCode && view_gameField.getCellState(MID_LEFT_CELL) == figureCode) {
            if (isCellFree(TOP_LEFT_CELL)) {
                return TOP_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_CENTER_CELL) == figureCode && view_gameField.getCellState(MID_CENTER_CELL) == figureCode) {
            if (isCellFree(BOTTOM_CENTER_CELL)) {
                return BOTTOM_CENTER_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_CENTER_CELL) == figureCode && view_gameField.getCellState(MID_CENTER_CELL) == figureCode) {
            if (isCellFree(TOP_CENTER_CELL)) {
                return TOP_CENTER_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_RIGHT_CELL) == figureCode && view_gameField.getCellState(MID_RIGHT_CELL) == figureCode) {
            if (isCellFree(BOTTOM_RIGHT_CELL)) {
                return BOTTOM_RIGHT_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_RIGHT_CELL) == figureCode && view_gameField.getCellState(MID_RIGHT_CELL) == figureCode) {
            if (isCellFree(TOP_RIGHT_CELL)) {
                return TOP_RIGHT_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_LEFT_CELL) == figureCode && view_gameField.getCellState(MID_CENTER_CELL) == figureCode) {
            if (isCellFree(BOTTOM_RIGHT_CELL)) {
                return BOTTOM_RIGHT_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_RIGHT_CELL) == figureCode && view_gameField.getCellState(MID_CENTER_CELL) == figureCode) {
            if (isCellFree(TOP_LEFT_CELL)) {
                return TOP_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_RIGHT_CELL) == figureCode && view_gameField.getCellState(MID_CENTER_CELL) == figureCode) {
            if (isCellFree(BOTTOM_LEFT_CELL)) {
                return BOTTOM_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_LEFT_CELL) == figureCode && view_gameField.getCellState(MID_CENTER_CELL) == figureCode) {
            if (isCellFree(TOP_RIGHT_CELL)) {
                return TOP_RIGHT_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_LEFT_CELL) == figureCode && view_gameField.getCellState(TOP_RIGHT_CELL) == figureCode) {
            if (isCellFree(TOP_CENTER_CELL)) {
                return TOP_CENTER_CELL;
            }
        }

        if (view_gameField.getCellState(MID_LEFT_CELL) == figureCode && view_gameField.getCellState(MID_RIGHT_CELL) == figureCode) {
            if (isCellFree(MID_CENTER_CELL)) {
                return MID_CENTER_CELL;
            }
        }

        if (view_gameField.getCellState(BOTTOM_LEFT_CELL) == figureCode && view_gameField.getCellState(BOTTOM_RIGHT_CELL) == figureCode) {
            if (isCellFree(BOTTOM_CENTER_CELL)) {
                return BOTTOM_CENTER_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_LEFT_CELL) == figureCode && view_gameField.getCellState(BOTTOM_LEFT_CELL) == figureCode) {
            if (isCellFree(MID_LEFT_CELL)) {
                return MID_LEFT_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_CENTER_CELL) == figureCode && view_gameField.getCellState(BOTTOM_CENTER_CELL) == figureCode) {
            if (isCellFree(MID_CENTER_CELL)) {
                return MID_CENTER_CELL;
            }
        }

        if (view_gameField.getCellState(TOP_RIGHT_CELL) == figureCode && view_gameField.getCellState(BOTTOM_RIGHT_CELL) == figureCode) {
            if (isCellFree(MID_RIGHT_CELL)) {
                return MID_RIGHT_CELL;
            }
        }

        return 0;
    }

    private boolean isCellFree(int cell) {
        return view_gameField.getCellState(cell) == TicTacToeDrawer.STATE_OF_FREE_CELL;
    }

    private int getFreeCellForNextStep() {
        int var; // state of cell with player figure
        if (playerFigureCode == 1) {
            var = TicTacToeDrawer.STATE_OF_CELL_WITH_CROSS;
        } else {
            var = TicTacToeDrawer.STATE_OF_CELL_WITH_ZERO;
        }

        defineRemainingFreeCells();

        if (countComputerStep >= 4) {
            return arrayFreeCell.get(random.nextInt(arrayFreeCell.size()));
        } else {
            while (true) {
                int cell = arrayFreeCell.get(random.nextInt(arrayFreeCell.size()));
                switch (cell) {
                    case TOP_CENTER_CELL:
                        if ((view_gameField.getCellState(TOP_LEFT_CELL) == var || view_gameField.getCellState(TOP_RIGHT_CELL) == var)
                                && (view_gameField.getCellState(MID_CENTER_CELL) == var || view_gameField.getCellState(BOTTOM_CENTER_CELL) == var)) {
                            continue;
                        } else {
                            return TOP_CENTER_CELL;
                        }
                    case MID_LEFT_CELL:
                        if ((view_gameField.getCellState(TOP_LEFT_CELL) == var || view_gameField.getCellState(BOTTOM_LEFT_CELL) == var)
                                && (view_gameField.getCellState(MID_RIGHT_CELL) == var || view_gameField.getCellState(MID_CENTER_CELL) == var)) {
                            continue;
                        } else {
                            return MID_LEFT_CELL;
                        }
                    case MID_RIGHT_CELL:
                        if ((view_gameField.getCellState(TOP_RIGHT_CELL) == var || view_gameField.getCellState(BOTTOM_RIGHT_CELL) == var)
                                && (view_gameField.getCellState(MID_CENTER_CELL) == var || view_gameField.getCellState(MID_LEFT_CELL) == var)) {
                            continue;
                        } else {
                            return MID_RIGHT_CELL;
                        }
                    case BOTTOM_CENTER_CELL:
                        if ((view_gameField.getCellState(BOTTOM_LEFT_CELL) == var || view_gameField.getCellState(BOTTOM_RIGHT_CELL) == var)
                                && (view_gameField.getCellState(MID_CENTER_CELL) == var || view_gameField.getCellState(TOP_CENTER_CELL) == var)) {
                            continue;
                        } else {
                            return BOTTOM_CENTER_CELL;
                        }
                    case TOP_LEFT_CELL:
                        return TOP_LEFT_CELL;
                    case TOP_RIGHT_CELL:
                        return TOP_RIGHT_CELL;
                    case BOTTOM_LEFT_CELL:
                        return BOTTOM_LEFT_CELL;
                    case BOTTOM_RIGHT_CELL:
                        return BOTTOM_RIGHT_CELL;
                    default:
                        return 0;
                }
            }
        }
    }

    private void defineRemainingFreeCells() {
        int cellAmount = 9;
        arrayFreeCell.clear();
        for (int i = 1; i <= cellAmount; i++) {
            if (isCellFree(i)) {
                arrayFreeCell.add(i);
            }
        }
    }
}
