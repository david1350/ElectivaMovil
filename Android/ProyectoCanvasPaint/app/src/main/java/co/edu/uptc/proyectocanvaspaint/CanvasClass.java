package co.edu.uptc.proyectocanvaspaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class CanvasClass extends View {

    private Bitmap bitmap;
    private Canvas canvas;
    private Path path;
    private Paint paint, canvasPaint;
    private float x = 0,y=0, radius = 0, dx = 0, dy=0;
    private boolean drawCircle = false,drawRectangle=false;
    private String shape;

    public CanvasClass(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.path = new Path();
        this.initPaint();
    }

    public void initPaint(){
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setColor(Color.WHITE);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeJoin(Paint.Join.ROUND);
        this.paint.setStrokeWidth(4f);
        this.canvasPaint = new Paint(Paint.DITHER_FLAG);
        this.shape ="null";
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(this.bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, canvasPaint);
        switch (shape){
            case "pencil":
                canvas.drawPath(path, paint);
                break;
            case "oval":
                canvas.drawCircle(this.x, this.y, this.radius, this.paint);
                break;
            case "square":
                canvas.drawRect(this.x, this.y, this.dx, this.dy, this.paint);
                break;
            case "line":
                canvas.drawLine(this.x, this.y, this.dx, this.dy, this.paint);
                break;
            case "bote":
                this.canvas.drawColor(this.paint.getColor());
                break;
            case "eraser":
                paint.setStrokeWidth(12f);
              //  paint.setColor(Color.WHITE);
                canvas.drawPath(path, paint);
                paint.setStrokeWidth(4f);
                invalidate();
                break;
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX, touchY);
                this.x = touchX;
                this.y = touchY;

                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX, touchY);
                this.radius = Math.abs(touchX-this.x);
                this.dx = touchX;
                this.dy = touchY;
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(touchX, touchY);
                switch (shape){
                    case "pencil":
                         canvas.drawPath(path, paint);
                         break;
                    case "oval":
                        canvas.drawCircle(this.x, this.y, this.radius, this.paint);
                        break;
                    case "square":
                        canvas.drawRect(this.x, this.y, this.dx, this.dy, this.paint);
                        break;
                    case "line":
                        canvas.drawLine(this.x, this.y, this.dx, this.dy, this.paint);
                        return true;
                    case "eraser":
                        paint.setStrokeWidth(12f);
                        //  paint.setColor(Color.WHITE);
                        canvas.drawPath(path, paint);
                        paint.setStrokeWidth(4f);
                        invalidate();
                        break;
                }
                radius = 0;
                dx = 0;
                dy=0;
                path.reset();
                break;
        }
        invalidate();
        return true;

    }

    public  void setColor (String color){
        invalidate();
        this.paint.setColor(Color.parseColor(color));
    }


    public void setShape (String s){
        this.shape = s;
    }
}


