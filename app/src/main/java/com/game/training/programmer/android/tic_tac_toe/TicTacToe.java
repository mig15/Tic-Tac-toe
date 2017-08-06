package com.game.training.programmer.android.tic_tac_toe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class TicTacToe extends View {

    private Handler handler;
    private Paint paint;

    private Cross cross;
    private Zero zero;

    private boolean firstStart = true;

    public TicTacToe(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TicTacToe(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TicTacToe(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        cross = new Cross();
        zero = new Zero();

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                int x = msg.arg1;
                int y = msg.arg2;

                if (msg.what == 1) {
                    cross.setFirstLineEndX(x);
                    cross.setFirstLineEndY(y);
                } else if (msg.what == 2) {
                    cross.setSecondLineEndX(x);
                    cross.setSecondLineEndY(y);
                } else if (msg.what == 0) {
                    zero.setEndAngle(msg.arg1);
                }
                invalidate();
            }
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        cross.setFirstLineStartX(0);
        cross.setFirstLineStartY(height);
        cross.setFirstLineEndX(width);
        cross.setFirstLineEndY(0);
        cross.setSecondLineStartX(0);
        cross.setSecondLineStartY(0);
        cross.setSecondLineEndX(0);
        cross.setSecondLineEndY(0);
        cross.setX1(width);
        cross.setY1(height);

        zero.setTopLeftX((width / 2) / 2);
        zero.setTopLeftY((height / 2) / 2);
        zero.setBottomRightX(zero.getTopLeftX() * 3);
        zero.setBottomRightY(zero.getTopLeftY() * 3);
        zero.setRectF(zero.getTopLeftX(), zero.getTopLeftY(), zero.getBottomRightX(), zero.getBottomRightY());
        zero.setEndAngle(180);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!firstStart) {
            //cross.drawCross(canvas);
            zero.drawZero(canvas);
        }
    }

    public boolean isFirstStart() {
        return firstStart;
    }

    public void startCross() {
        firstStart = false;
        new Thread(new Runnable() {
            @Override
            public void run() {

                int x = (int) cross.getFirstLineStartX();
                int y = (int) cross.getFirstLineStartY();
                int endX = (int) cross.getFirstLineEndX();
                int endY = (int) cross.getFirstLineEndY();

                while (x <= endX && y >= endY) {
                    x += 2;
                    y -= 2;
                    handler.sendMessage(handler.obtainMessage(1 ,x, y));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {

                    }
                }

                x = (int) cross.getSecondLineStartX();
                y = (int) cross.getSecondLineStartY();
                endX = (int) cross.getX1();
                endY = (int) cross.getY1();

                while (x <= endX && y <= endY) {
                    x += 2;
                    y += 2;
                    handler.sendMessage(handler.obtainMessage(2 ,x, y));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }).start();
    }

    public void startZero() {
        firstStart = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                float end = zero.getEndAngle();

                while (end <= 360) {
                    end += 10;
                    handler.sendMessage(handler.obtainMessage(0, (int) end, 0));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }).start();
    }

    private class Cross {

        private float firstLineStartX, secondLineStartX;
        private float firstLineEndX, secondLineEndX;
        private float firstLineStartY, secondLineStartY;
        private float firstLineEndY, secondLineEndY;
        private float x1, y1;

        private void setFirstLineStartX(float firstLineStartX) {
            this.firstLineStartX = firstLineStartX;
        }

        private void setFirstLineEndX(float firstLineEndX) {
            this.firstLineEndX = firstLineEndX;
        }

        private void setFirstLineStartY(float firstLineStartY) {
            this.firstLineStartY = firstLineStartY;
        }

        private void setFirstLineEndY(float firstLineEndY) {
            this.firstLineEndY = firstLineEndY;
        }

        private void setSecondLineStartX(float secondLineStartX) {
            this.secondLineStartX = secondLineStartX;
        }

        private void setSecondLineStartY(float secondLineStartY) {
            this.secondLineStartY = secondLineStartY;
        }

        private void setSecondLineEndX(float secondLineEndX) {
            this.secondLineEndX = secondLineEndX;
        }

        private void setSecondLineEndY(float secondLineEndY) {
            this.secondLineEndY = secondLineEndY;
        }

        private void setX1(float x1) {
            this.x1 = x1;
        }

        private void setY1(float y1) {
            this.y1 = y1;
        }

        private float getFirstLineStartX() {
            return firstLineStartX;
        }

        private float getFirstLineStartY() {
            return firstLineStartY;
        }

        private float getFirstLineEndX() {
            return firstLineEndX;
        }

        private float getFirstLineEndY() {
            return firstLineEndY;
        }

        private float getSecondLineStartX() {
            return secondLineStartX;
        }

        private float getSecondLineStartY() {
            return secondLineStartY;
        }

        private float getX1() {
            return x1;
        }

        private float getY1() {
            return y1;
        }

        private void drawCross(Canvas canvas) {
            canvas.drawLine(firstLineStartX, firstLineStartY, firstLineEndX, firstLineEndY, paint);
            canvas.drawLine(secondLineStartX, secondLineStartY, secondLineEndX, secondLineEndY, paint);
        }
    }

    private class Zero {

        private RectF rectF;

        private float topLeftX, topLeftY;
        private float bottomRightX, bottomRightY;
        private float endAngle;

        private Zero() {
            rectF = new RectF();
        }

        private void setRectF(float x, float y, float x1, float y1) {
            rectF.set(x, y, x1, y1);
        }

        private void setTopLeftX(float topLeftX) {
            this.topLeftX = topLeftX;
        }

        private void setTopLeftY(float topLeftY) {
            this.topLeftY = topLeftY;
        }

        private void setBottomRightX(float bottomRightX) {
            this.bottomRightX = bottomRightX;
        }

        private void setBottomRightY(float bottomRightY) {
            this.bottomRightY = bottomRightY;
        }

        private void setEndAngle(float endAngle) {
            this.endAngle = endAngle;
        }

        private float getTopLeftX() {
            return topLeftX;
        }

        private float getTopLeftY() {
            return topLeftY;
        }

        private float getBottomRightX() {
            return bottomRightX;
        }

        private float getBottomRightY() {
            return bottomRightY;
        }

        public float getEndAngle() {
            return endAngle;
        }

        private void drawZero(Canvas canvas) {
            canvas.drawArc(rectF, 180, endAngle, false, paint);
        }
    }
}
