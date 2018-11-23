package com.vlevieux.drumtab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DynamicTabView extends View {

    public List<Shape> shapes = new ArrayList<>();

    public int width;
    public int height;

    public DynamicTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d("TAB_VIEW", "DynamicTabView created");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        Log.d("TAB_VIEW", "DynamicTabView size changed : Width : " + String.valueOf(this.width)+" Height : "+String.valueOf(this.height));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);

        // TODO: Calculate Width according to the tab
        int width = 30000 + 50;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("TAB_VIEW", "DynamicTabView Status : On Drawing");

        Log.d("TAB_VIEW", "DynamicTabView Status : On Drawing Background");
        drawBackground(canvas);

        // TODO: Move the creation of shape
        for (int i=0; i<10; i++) {
            shapes.add(new Shape(1, i, this.height, this.width));
        }

        Log.d("TAB_VIEW", "DynamicTabView Status : On Drawing Shapes["+String.valueOf(this.shapes.size())+"]");
        for (Shape shape : this.shapes){
            Log.d("TAB_VIEW", String.valueOf(shape.y) + " " + String.valueOf(shape.x) + " "+ String.valueOf(shape.width));
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
    float x;
    float y;
    float width;
    float height;
    Paint paint = new Paint();

    Shape(int vertical_position, int horizontal_position, int height, int width){
        this.x = ((2*height)/15) + (vertical_position*height)/5;
        this.y = ((2*height)/15) * horizontal_position*2;
        this.width = (2 * height) / 15;
        this.height = (2 * height) / 15;
        paint.setColor(Color.RED);
        Log.d("TAB_SHAPE", "YXWHC :" + String.valueOf(this.y)+" "+String.valueOf(this.x)+" "+String.valueOf(this.width)+" "+String.valueOf(this.height)+String.valueOf(Color.RED));
    }
}