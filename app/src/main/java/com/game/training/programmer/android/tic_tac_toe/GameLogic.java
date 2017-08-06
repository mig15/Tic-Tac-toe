package com.game.training.programmer.android.tic_tac_toe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class GameLogic extends View {

    private Paint paintSeparativeLines;
    private Path path;

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

    private boolean cell1Free = true;
    private boolean cell2Free = true;
    private boolean cell3Free = true;
    private boolean cell4Free = true;
    private boolean cell5Free = true;
    private boolean cell6Free = true;
    private boolean cell7Free = true;
    private boolean cell8Free = true;
    private boolean cell9Free = true;

    private int cellNumber;

    public GameLogic(Context context) {
        super(context);
        init(context, null, 0);
    }

    public GameLogic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public GameLogic(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        path = new Path();

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
    }

    public void defineAndSetTouchCell(float x, float y) {
        if (x < firstVerticalSeparativeLineStartX && y < firstHorizontalSeparativeLineStartY) {
            cellNumber = 1;
        } else if (x > firstVerticalSeparativeLineStartX && x < secondVerticalSeparativeLineStartX
                && y < firstHorizontalSeparativeLineStartY) {
            cellNumber = 2;
        } else if (x > secondVerticalSeparativeLineStartX && y < firstHorizontalSeparativeLineStartY) {
            cellNumber = 3;
        } else if (x < firstVerticalSeparativeLineStartX && y > firstHorizontalSeparativeLineStartY
                && y < secondHorizontalSeparativeLineStartY) {
            cellNumber = 4;
        } else if (x > firstVerticalSeparativeLineStartX && x < secondVerticalSeparativeLineStartX
                && y > firstHorizontalSeparativeLineStartY && y < secondHorizontalSeparativeLineStartY) {
            cellNumber = 5;
        } else if (x > secondVerticalSeparativeLineStartX && y > firstHorizontalSeparativeLineStartY
                && y < secondHorizontalSeparativeLineStartY) {
            cellNumber = 6;
        } else if (x < firstVerticalSeparativeLineStartX && y > secondHorizontalSeparativeLineStartY) {
            cellNumber = 7;
        } else if (x > firstVerticalSeparativeLineStartX && x < secondVerticalSeparativeLineStartX
                && y > secondHorizontalSeparativeLineStartY) {
            cellNumber = 8;
        } else if (x > secondVerticalSeparativeLineStartX && y > secondHorizontalSeparativeLineStartY) {
            cellNumber = 9;
        }
    }

    private boolean isCellFree(int cellNumber) {
        switch (cellNumber) {
            case 1:
                return cell1Free;
            case 2:
                return cell2Free;
            case 3:
                return cell3Free;
            case 4:
                return cell4Free;
            case 5:
                return cell5Free;
            case 6:
                return cell6Free;
            case 7:
                return cell7Free;
            case 8:
                return cell8Free;
            case 9:
                return cell9Free;
            default:
                return false;
        }
    }

    private class Cross {

        private Paint paintCrosses;

        private Cross() {
            paintCrosses = new Paint();
            paintCrosses.setColor(Color.BLUE);
            paintCrosses.setStyle(Paint.Style.STROKE);
            paintCrosses.setStrokeWidth(50);
        }

        private void drawCrosses(Canvas canvas) {
            switch (cellNumber) {
                case 1:
                    path.moveTo(50, firstHorizontalSeparativeLineStartY - 50);
                    path.lineTo(firstVerticalSeparativeLineStartX - 50, 50);
                    path.moveTo(50, 50);
                    path.lineTo(firstVerticalSeparativeLineStartX - 50, firstHorizontalSeparativeLineStartY - 50);
                    cell1Free = false;
                    break;
                case 2:
                    path.moveTo(firstVerticalSeparativeLineStartX + 50, firstHorizontalSeparativeLineStartY - 50);
                    path.lineTo(secondVerticalSeparativeLineStartX - 50, 50);
                    path.moveTo(firstVerticalSeparativeLineStartX + 50, 50);
                    path.lineTo(secondVerticalSeparativeLineStartX - 50, firstHorizontalSeparativeLineStartY - 50);
                    cell2Free = false;
                    break;
                case 3:
                    path.moveTo(secondVerticalSeparativeLineStartX + 50, firstHorizontalSeparativeLineStartY - 50);
                    path.lineTo(maxWidth - 50, 50);
                    path.moveTo(secondVerticalSeparativeLineStartX + 50, 50);
                    path.lineTo(maxWidth - 50, firstHorizontalSeparativeLineStartY - 50);
                    cell3Free = false;
                    break;
                case 4:
                    path.moveTo(50, secondHorizontalSeparativeLineStartY - 50);
                    path.lineTo(firstVerticalSeparativeLineStartX - 50, firstHorizontalSeparativeLineStartY + 50);
                    path.moveTo(50, firstHorizontalSeparativeLineStartY + 50);
                    path.lineTo(firstVerticalSeparativeLineStartX - 50, secondHorizontalSeparativeLineStartY - 50);
                    cell4Free = false;
                    break;
                case 5:
                    path.moveTo(firstVerticalSeparativeLineStartX + 50, secondHorizontalSeparativeLineStartY - 50);
                    path.lineTo(secondVerticalSeparativeLineStartX - 50, firstHorizontalSeparativeLineStartY + 50);
                    path.moveTo(firstVerticalSeparativeLineStartX + 50, firstHorizontalSeparativeLineStartY + 50);
                    path.lineTo(secondVerticalSeparativeLineStartX - 50, secondHorizontalSeparativeLineStartY - 50);
                    cell5Free = false;
                    break;
                case 6:
                    path.moveTo(secondVerticalSeparativeLineStartX + 50, secondHorizontalSeparativeLineStartY - 50);
                    path.lineTo(maxWidth - 50, firstHorizontalSeparativeLineStartY + 50);
                    path.moveTo(secondVerticalSeparativeLineStartX + 50, firstHorizontalSeparativeLineStartY + 50);
                    path.lineTo(maxWidth - 50, secondHorizontalSeparativeLineStartY - 50);
                    cell6Free = false;
                    break;
                case 7:
                    path.moveTo(50, maxHeight - 50);
                    path.lineTo(firstVerticalSeparativeLineStartX - 50, secondHorizontalSeparativeLineStartY + 50);
                    path.moveTo(50, secondHorizontalSeparativeLineStartY + 50);
                    path.lineTo(firstVerticalSeparativeLineStartX - 50, maxHeight - 50);
                    cell7Free = false;
                    break;
                case 8:
                    path.moveTo(firstVerticalSeparativeLineStartX + 50, maxHeight - 50);
                    path.lineTo(secondVerticalSeparativeLineStartX - 50, secondHorizontalSeparativeLineStartY + 50);
                    path.moveTo(firstVerticalSeparativeLineStartX + 50, secondHorizontalSeparativeLineStartY + 50);
                    path.lineTo(secondVerticalSeparativeLineStartX - 50, maxHeight - 50);
                    cell8Free = false;
                    break;
                case 9:
                    path.moveTo(secondVerticalSeparativeLineStartX + 50, maxHeight - 50);
                    path.lineTo(maxWidth - 50, secondHorizontalSeparativeLineStartY + 50);
                    path.moveTo(secondVerticalSeparativeLineStartX + 50, secondHorizontalSeparativeLineStartY + 50);
                    path.lineTo(maxWidth - 50, maxHeight - 50);
                    cell9Free = false;
                    break;
            }
            canvas.drawPath(path, paintCrosses);
        }
    }

    private class Zero {

        private Paint paintZero;

        private float radiusZero;

        private Zero() {
            paintZero = new Paint();
            paintZero.setColor(Color.RED);
            paintZero.setStyle(Paint.Style.STROKE);
            paintZero.setStrokeWidth(50);
        }

        private void setRadiusZero(float radiusZero) {
            this.radiusZero = radiusZero;
        }

        private void drawZero(Canvas canvas) {
            switch (cellNumber) {
                case 1:
                    path.addCircle(firstVerticalSeparativeLineStartX / 2, firstHorizontalSeparativeLineStartY / 2,
                            radiusZero, Path.Direction.CCW);
                    cell1Free = false;
                    break;
                case 2:
                    path.addCircle(maxWidth / 2, firstHorizontalSeparativeLineStartY / 2,
                            radiusZero, Path.Direction.CCW);
                    cell2Free = false;
                    break;
                case 3:
                    path.addCircle(secondVerticalSeparativeLineStartX + (firstVerticalSeparativeLineStartX / 2),
                            firstHorizontalSeparativeLineStartY / 2, radiusZero, Path.Direction.CCW);
                    cell3Free = false;
                    break;
                case 4:
                    path.addCircle(firstVerticalSeparativeLineStartX / 2, maxHeight / 2,
                            radiusZero, Path.Direction.CCW);
                    cell4Free = false;
                    break;
                case 5:
                    path.addCircle(maxWidth / 2, maxHeight / 2, radiusZero, Path.Direction.CCW);
                    cell5Free = false;
                    break;
                case 6:
                    path.addCircle(secondVerticalSeparativeLineStartX + (firstVerticalSeparativeLineStartX / 2),
                            maxHeight / 2, radiusZero, Path.Direction.CCW);
                    cell6Free = false;
                    break;
                case 7:
                    path.addCircle(firstVerticalSeparativeLineStartX / 2,
                            secondHorizontalSeparativeLineStartY + (firstHorizontalSeparativeLineStartY / 2),
                            radiusZero, Path.Direction.CCW);
                    cell7Free = false;
                    break;
                case 8:
                    path.addCircle(maxWidth / 2,
                            secondHorizontalSeparativeLineStartY + (firstHorizontalSeparativeLineStartY / 2),
                            radiusZero, Path.Direction.CCW);
                    cell8Free = false;
                    break;
                case 9:
                    path.addCircle(secondVerticalSeparativeLineStartX + (firstVerticalSeparativeLineStartX / 2),
                            secondHorizontalSeparativeLineStartY + (firstHorizontalSeparativeLineStartY / 2),
                            radiusZero, Path.Direction.CCW);
                    cell9Free = false;
                    break;
            }
            canvas.drawPath(path, paintZero);
        }
    }
}


