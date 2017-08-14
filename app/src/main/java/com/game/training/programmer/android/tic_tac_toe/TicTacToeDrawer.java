package com.game.training.programmer.android.tic_tac_toe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class TicTacToeDrawer extends View {

    static final int STATE_OF_FREE_CELL = 0;
    static final int STATE_OF_CELL_WITH_ZERO = 1;
    static final int STATE_OF_CELL_WITH_CROSS = 2;

    private Paint paintSeparativeLines;

    private Cross cross;
    private Zero zero;

    private int maxWidth, maxHeight;

    private float firstVerticalSeparativeLineStartX;
    private float firstVerticalSeparativeLineStartY;
    private float firstVerticalSeparativeLineEndX;
    private float firstVerticalSeparativeLineEndY;

    private float secondVerticalSeparativeLineStartX;
    private float secondVerticalSeparativeLineStartY;
    private float secondVerticalSeparativeLineEndX;
    private float secondVerticalSeparativeLineEndY;

    private float firstHorizontalSeparativeLineStartX;
    private float firstHorizontalSeparativeLineStartY;
    private float firstHorizontalSeparativeLineEndX;
    private float firstHorizontalSeparativeLineEndY;

    private float secondHorizontalSeparativeLineStartX;
    private float secondHorizontalSeparativeLineStartY;
    private float secondHorizontalSeparativeLineEndX;
    private float secondHorizontalSeparativeLineEndY;

    private int topLeftCell = 0;
    private int topCenterCell = 0;
    private int topRightCell = 0;
    private int midLeftCell = 0;
    private int midCenterCell = 0;
    private int midRightCell = 0;
    private int bottomLeftCell = 0;
    private int bottomCenterCell = 0;
    private int bottomRightCell = 0;

    private int cellNumber;

    public TicTacToeDrawer(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TicTacToeDrawer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TicTacToeDrawer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    void setCellNumber(int cellNumber) {
        this.cellNumber = cellNumber;
    }

    void setFigureCode(int figureCode) {
        if (figureCode == 0) {
            zero.addZero();
        } else if (figureCode == 1) {
            cross.addCross();
        }
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        paintSeparativeLines = new Paint();
        paintSeparativeLines.setColor(Color.GRAY);
        paintSeparativeLines.setStyle(Paint.Style.STROKE);
        paintSeparativeLines.setStrokeWidth(2);

        cross = new Cross();
        zero = new Zero();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        maxWidth = width;
        maxHeight = height;

        firstVerticalSeparativeLineStartX = width / 3;
        firstVerticalSeparativeLineStartY = 0;
        firstVerticalSeparativeLineEndX = width / 3;
        firstVerticalSeparativeLineEndY = height;

        secondVerticalSeparativeLineStartX = firstVerticalSeparativeLineStartX * 2;
        secondVerticalSeparativeLineStartY = 0;
        secondVerticalSeparativeLineEndX = firstVerticalSeparativeLineStartX * 2;
        secondVerticalSeparativeLineEndY = height;

        firstHorizontalSeparativeLineStartX = 0;
        firstHorizontalSeparativeLineStartY = height / 3;
        firstHorizontalSeparativeLineEndX = width;
        firstHorizontalSeparativeLineEndY = height / 3;

        secondHorizontalSeparativeLineStartX = 0;
        secondHorizontalSeparativeLineStartY = firstHorizontalSeparativeLineStartY * 2;
        secondHorizontalSeparativeLineEndX = width;
        secondHorizontalSeparativeLineEndY = firstHorizontalSeparativeLineStartY * 2;

        zero.setRadiusZero((secondHorizontalSeparativeLineStartY - firstHorizontalSeparativeLineStartY) / 3);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(firstVerticalSeparativeLineStartX, firstVerticalSeparativeLineStartY,
                firstVerticalSeparativeLineEndX, firstVerticalSeparativeLineEndY, paintSeparativeLines);
        canvas.drawLine(secondVerticalSeparativeLineStartX, secondVerticalSeparativeLineStartY,
                secondVerticalSeparativeLineEndX, secondVerticalSeparativeLineEndY, paintSeparativeLines);
        canvas.drawLine(firstHorizontalSeparativeLineStartX, firstHorizontalSeparativeLineStartY,
                firstHorizontalSeparativeLineEndX, firstHorizontalSeparativeLineEndY, paintSeparativeLines);
        canvas.drawLine(secondHorizontalSeparativeLineStartX, secondHorizontalSeparativeLineStartY,
                secondHorizontalSeparativeLineEndX, secondHorizontalSeparativeLineEndY, paintSeparativeLines);

        canvas.drawPath(zero.pathZero, zero.paintZero);
        canvas.drawPath(cross.pathCrosses, cross.paintCrosses);
    }

    int defineTouchCell(float x, float y) {
        if (x < firstVerticalSeparativeLineStartX && y < firstHorizontalSeparativeLineStartY) {
            return 1;
        } else if (x > firstVerticalSeparativeLineStartX && x < secondVerticalSeparativeLineStartX
                && y < firstHorizontalSeparativeLineStartY) {
            return 2;
        } else if (x > secondVerticalSeparativeLineStartX && y < firstHorizontalSeparativeLineStartY) {
            return 3;
        } else if (x < firstVerticalSeparativeLineStartX && y > firstHorizontalSeparativeLineStartY
                && y < secondHorizontalSeparativeLineStartY) {
            return 4;
        } else if (x > firstVerticalSeparativeLineStartX && x < secondVerticalSeparativeLineStartX
                && y > firstHorizontalSeparativeLineStartY && y < secondHorizontalSeparativeLineStartY) {
            return 5;
        } else if (x > secondVerticalSeparativeLineStartX && y > firstHorizontalSeparativeLineStartY
                && y < secondHorizontalSeparativeLineStartY) {
            return 6;
        } else if (x < firstVerticalSeparativeLineStartX && y > secondHorizontalSeparativeLineStartY) {
            return 7;
        } else if (x > firstVerticalSeparativeLineStartX && x < secondVerticalSeparativeLineStartX
                && y > secondHorizontalSeparativeLineStartY) {
            return 8;
        } else if (x > secondVerticalSeparativeLineStartX && y > secondHorizontalSeparativeLineStartY) {
            return 9;
        }

        return cellNumber;
    }

    int getCellState(int cellNumber) {
        switch (cellNumber) {
            case 1:
                return topLeftCell;
            case 2:
                return topCenterCell;
            case 3:
                return topRightCell;
            case 4:
                return midLeftCell;
            case 5:
                return midCenterCell;
            case 6:
                return midRightCell;
            case 7:
                return bottomLeftCell;
            case 8:
                return bottomCenterCell;
            case 9:
                return bottomRightCell;
            default:
                return 3;
        }
    }

    private class Cross {

        private Paint paintCrosses;
        private Path pathCrosses;

        private Cross() {
            pathCrosses = new Path();

            paintCrosses = new Paint();
            paintCrosses.setStyle(Paint.Style.STROKE);
            paintCrosses.setColor(Color.BLUE);
            paintCrosses.setStrokeWidth(50);
        }

        private void addCross() {
            switch (cellNumber) {
                case 1:
                    pathCrosses.moveTo(50, firstHorizontalSeparativeLineStartY - 50);
                    pathCrosses.lineTo(firstVerticalSeparativeLineStartX - 50, 50);
                    pathCrosses.moveTo(50, 50);
                    pathCrosses.lineTo(firstVerticalSeparativeLineStartX - 50, firstHorizontalSeparativeLineStartY - 50);
                    topLeftCell = 2;
                    break;
                case 2:
                    pathCrosses.moveTo(firstVerticalSeparativeLineStartX + 50, firstHorizontalSeparativeLineStartY - 50);
                    pathCrosses.lineTo(secondVerticalSeparativeLineStartX - 50, 50);
                    pathCrosses.moveTo(firstVerticalSeparativeLineStartX + 50, 50);
                    pathCrosses.lineTo(secondVerticalSeparativeLineStartX - 50, firstHorizontalSeparativeLineStartY - 50);
                    topCenterCell = 2;
                    break;
                case 3:
                    pathCrosses.moveTo(secondVerticalSeparativeLineStartX + 50, firstHorizontalSeparativeLineStartY - 50);
                    pathCrosses.lineTo(maxWidth - 50, 50);
                    pathCrosses.moveTo(secondVerticalSeparativeLineStartX + 50, 50);
                    pathCrosses.lineTo(maxWidth - 50, firstHorizontalSeparativeLineStartY - 50);
                    topRightCell = 2;
                    break;
                case 4:
                    pathCrosses.moveTo(50, secondHorizontalSeparativeLineStartY - 50);
                    pathCrosses.lineTo(firstVerticalSeparativeLineStartX - 50, firstHorizontalSeparativeLineStartY + 50);
                    pathCrosses.moveTo(50, firstHorizontalSeparativeLineStartY + 50);
                    pathCrosses.lineTo(firstVerticalSeparativeLineStartX - 50, secondHorizontalSeparativeLineStartY - 50);
                    midLeftCell = 2;
                    break;
                case 5:
                    pathCrosses.moveTo(firstVerticalSeparativeLineStartX + 50, secondHorizontalSeparativeLineStartY - 50);
                    pathCrosses.lineTo(secondVerticalSeparativeLineStartX - 50, firstHorizontalSeparativeLineStartY + 50);
                    pathCrosses.moveTo(firstVerticalSeparativeLineStartX + 50, firstHorizontalSeparativeLineStartY + 50);
                    pathCrosses.lineTo(secondVerticalSeparativeLineStartX - 50, secondHorizontalSeparativeLineStartY - 50);
                    midCenterCell = 2;
                    break;
                case 6:
                    pathCrosses.moveTo(secondVerticalSeparativeLineStartX + 50, secondHorizontalSeparativeLineStartY - 50);
                    pathCrosses.lineTo(maxWidth - 50, firstHorizontalSeparativeLineStartY + 50);
                    pathCrosses.moveTo(secondVerticalSeparativeLineStartX + 50, firstHorizontalSeparativeLineStartY + 50);
                    pathCrosses.lineTo(maxWidth - 50, secondHorizontalSeparativeLineStartY - 50);
                    midRightCell = 2;
                    break;
                case 7:
                    pathCrosses.moveTo(50, maxHeight - 50);
                    pathCrosses.lineTo(firstVerticalSeparativeLineStartX - 50, secondHorizontalSeparativeLineStartY + 50);
                    pathCrosses.moveTo(50, secondHorizontalSeparativeLineStartY + 50);
                    pathCrosses.lineTo(firstVerticalSeparativeLineStartX - 50, maxHeight - 50);
                    bottomLeftCell = 2;
                    break;
                case 8:
                    pathCrosses.moveTo(firstVerticalSeparativeLineStartX + 50, maxHeight - 50);
                    pathCrosses.lineTo(secondVerticalSeparativeLineStartX - 50, secondHorizontalSeparativeLineStartY + 50);
                    pathCrosses.moveTo(firstVerticalSeparativeLineStartX + 50, secondHorizontalSeparativeLineStartY + 50);
                    pathCrosses.lineTo(secondVerticalSeparativeLineStartX - 50, maxHeight - 50);
                    bottomCenterCell = 2;
                    break;
                case 9:
                    pathCrosses.moveTo(secondVerticalSeparativeLineStartX + 50, maxHeight - 50);
                    pathCrosses.lineTo(maxWidth - 50, secondHorizontalSeparativeLineStartY + 50);
                    pathCrosses.moveTo(secondVerticalSeparativeLineStartX + 50, secondHorizontalSeparativeLineStartY + 50);
                    pathCrosses.lineTo(maxWidth - 50, maxHeight - 50);
                    bottomRightCell = 2;
                    break;
            }
        }
    }

    private class Zero {

        private Paint paintZero;
        private Path pathZero;

        private float radiusZero;

        Zero() {
            pathZero = new Path();

            paintZero = new Paint();
            paintZero.setStyle(Paint.Style.STROKE);
            paintZero.setColor(Color.RED);
            paintZero.setStrokeWidth(50);

        }

        private void setRadiusZero(float radiusZero) {
            this.radiusZero = radiusZero;
        }

        private void addZero() {
            paintZero.setColor(Color.RED);
            paintZero.setStrokeWidth(50);

            switch (cellNumber) {
                case 1:
                    pathZero.addCircle(firstVerticalSeparativeLineStartX / 2, firstHorizontalSeparativeLineStartY / 2,
                            radiusZero, Path.Direction.CCW);
                    topLeftCell = 1;
                    break;
                case 2:
                    pathZero.addCircle(maxWidth / 2, firstHorizontalSeparativeLineStartY / 2,
                            radiusZero, Path.Direction.CCW);
                    topCenterCell = 1;
                    break;
                case 3:
                    pathZero.addCircle(secondVerticalSeparativeLineStartX + (firstVerticalSeparativeLineStartX / 2),
                            firstHorizontalSeparativeLineStartY / 2, radiusZero, Path.Direction.CCW);
                    topRightCell = 1;
                    break;
                case 4:
                    pathZero.addCircle(firstVerticalSeparativeLineStartX / 2, maxHeight / 2,
                            radiusZero, Path.Direction.CCW);
                    midLeftCell = 1;
                    break;
                case 5:
                    pathZero.addCircle(maxWidth / 2, maxHeight / 2, radiusZero, Path.Direction.CCW);
                    midCenterCell = 1;
                    break;
                case 6:
                    pathZero.addCircle(secondVerticalSeparativeLineStartX + (firstVerticalSeparativeLineStartX / 2),
                            maxHeight / 2, radiusZero, Path.Direction.CCW);
                    midRightCell = 1;
                    break;
                case 7:
                    pathZero.addCircle(firstVerticalSeparativeLineStartX / 2,
                            secondHorizontalSeparativeLineStartY + (firstHorizontalSeparativeLineStartY / 2),
                            radiusZero, Path.Direction.CCW);
                    bottomLeftCell = 1;
                    break;
                case 8:
                    pathZero.addCircle(maxWidth / 2,
                            secondHorizontalSeparativeLineStartY + (firstHorizontalSeparativeLineStartY / 2),
                            radiusZero, Path.Direction.CCW);
                    bottomCenterCell = 1;
                    break;
                case 9:
                    pathZero.addCircle(secondVerticalSeparativeLineStartX + (firstVerticalSeparativeLineStartX / 2),
                            secondHorizontalSeparativeLineStartY + (firstHorizontalSeparativeLineStartY / 2),
                            radiusZero, Path.Direction.CCW);
                    bottomRightCell = 1;
                    break;
            }
        }
    }
}


