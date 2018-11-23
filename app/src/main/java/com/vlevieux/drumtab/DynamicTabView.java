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

    public static List<Shape> shapes = new ArrayList<>();

    public int width;
    public int height;

    public DynamicTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicTabView(Context context){
        super(context);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        Log.d("TAB_VIEW", "Width" + String.valueOf(this.width));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = 30000 + 50; // Since 3000 is bottom of last Rect to be drawn added and 50 for padding.
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("TAB_VIEW", "on drawing");
        //Log.d("TAB_VIEW", "#Shapes :"+String.valueOf(DynamicTabThread.shapes));
        drawBackground(canvas);
        for (int i=0; i<10; i++) {
            shapes.add(new Shape(1, i, this.height, this.width));
        }
        for (Shape shape : this.shapes){
            Log.d("TAB_VIEW", String.valueOf(shape.y) + " " + String.valueOf(shape.x) + " "+ String.valueOf(shape.width));
            //if (shape.y>0 & shape.y<width)
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
        Log.d("TAB_SHAPE", String.valueOf(this.y)+" "+String.valueOf(this.x)+" "+String.valueOf(this.width)+" "+String.valueOf(this.height)+" ");
    }
}