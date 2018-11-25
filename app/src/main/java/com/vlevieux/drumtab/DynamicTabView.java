package com.vlevieux.drumtab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class DynamicTabView extends View {

    public int width;
    public int height;
    private int tabWidth = 1;

    public DynamicTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d("TAB_VIEW", "DynamicTabView created");
    }

    public void setTabWidth(int tabWidth){
        this.tabWidth = tabWidth;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        Log.d("TAB_VIEW", "Size changed : Width : " + String.valueOf(this.width)+" Height : "+String.valueOf(this.height));

        for(Shape shape : TabActivity.shapes){
            shape.resize(h);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = ((2*16*tabWidth * height) / 15)+((10*2*height)/15)+((16*2*height)/15);
        Log.d("TAB_VIEW", "Calculate new width : " + String.valueOf(width));
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("TAB_VIEW", "Status : On Drawing");

        Log.d("TAB_VIEW", "Status : On Drawing Background");
        drawBackground(canvas);

        Log.d("TAB_VIEW", "Status : On Drawing Shapes["+String.valueOf(TabActivity.shapes.size())+"]");
        for (Shape shape : TabActivity.shapes){
            Log.d("TAB_View", "Drawing Shape (YXWHC) : " + String.valueOf(shape.y)+" "+String.valueOf(shape.x)+" "+String.valueOf(shape.width)+" "+String.valueOf(shape.height)+" "+String.format("#%06X", 0xFFFFFF & shape.paint.getColor()));
                canvas.drawRect(shape.y, shape.x, shape.y+shape.width,shape.x+shape.height, shape.paint);
        }
    }

    protected void drawBackground(Canvas canvas){
        Paint paintBG = new Paint();
        paintBG.setColor(Color.LTGRAY);
        paintBG.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paintBG);

        paintBG.setColor(Color.BLACK);
        paintBG.setStrokeWidth(3);
        for (int i = 1; i <= 4; i++){
            canvas.drawLine(0, (i * height) / 5, getMeasuredWidth(), (i * this.height) / 5, paintBG);
        }
    }
}

class Shape {
    private int vertical_position;
    private int horizontal_position;
    float x;
    float y;
    float width;
    float height;
    Paint paint = new Paint();

    Shape(int vertical_position, int horizontal_position, int height, int width, int c){
        this.vertical_position = vertical_position;
        this.horizontal_position = horizontal_position;
        this.x = ((2*height)/15) + (vertical_position*height)/5;
        this.y = ((2*height)/15) * horizontal_position*2;
        this.width = (2 * height) / 15;
        this.height = (2 * height) / 15;
        paint.setColor(c);
        Log.d("TAB_SHAPE", "YXWHC : " + String.valueOf(this.y)+" "+String.valueOf(this.x)+" "+String.valueOf(this.width)+" "+String.valueOf(this.height)+" "+String.format("#%06X", 0xFFFFFF & paint.getColor()));
    }

    void resize(int height){
        this.x = ((2*height)/15) + (vertical_position*height)/5;
        this.y = ((2*height)/15) * horizontal_position*2;
        this.width = (2 * height) / 15;
        this.height = (2 * height) / 15;
    }
}