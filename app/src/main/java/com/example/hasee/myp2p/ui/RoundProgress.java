package com.example.hasee.myp2p.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.utils.UIUtils;

/**
 * Created by lzq on 2016/11/13.
 */
public class RoundProgress extends View {

   /* //属性
    private int roundColor = Color.GRAY;//圆环的颜色
    private int roundProgressColor = Color.RED;//圆弧的颜色
    private int textColor = Color.BLUE;//文本的颜色

    private int roundWidth = UIUtils.dp2px(10);//圆环的宽度
    private int textSize = UIUtils.dp2px(20);//字体的大小

    //提供当前的进度和最大值
    private int progress = 60;
    private int max = 100;*/


    //自定义属性的声明
    private int roundColor;//圆环的颜色
    private int roundProgressColor;//圆弧的颜色
    private int textColor;//文本的颜色
    private int roundWidth;//圆环的宽度
    private int textSize ;//字体的大小
    private int progress ;
    private int max;


    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    private Paint paint;

    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //1.获取typedArray对象

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        //2.获取布局文件中声明的自定义属性的值
        roundColor = typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.GRAY);
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.RED);
        textColor = typedArray.getColor(R.styleable.RoundProgress_textColor, Color.GREEN);
        roundWidth = (int) typedArray.getDimension(R.styleable.RoundProgress_roundWidth, UIUtils.dp2px(10));
        textSize = (int) typedArray.getDimension(R.styleable.RoundProgress_textSize, UIUtils.dp2px(20));
        progress = typedArray.getInteger(R.styleable.RoundProgress_progress,40);
        max = typedArray.getInteger(R.styleable.RoundProgress_max,100);

        //3.回收
        typedArray.recycle();

        //初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);//去除毛边

    }

    private int width;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = this.getMeasuredWidth();//获取当前视图的宽度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1.绘制圆环
        //设置圆环的中心点
        int dx = width / 2;
        int dy = width / 2;
        //设置半径
        int radius = width / 2 - roundWidth / 2;
        //设置画笔
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);//设置为圆环
        paint.setStrokeWidth(roundWidth);//设置圆环的宽度
        canvas.drawCircle(dx,dy,radius,paint);

        //2.绘制圆弧
        //理解为包裹圆环中心线的圆的矩形
        RectF rectF = new RectF(roundWidth/2,roundWidth/2,width - roundWidth/2,width - roundWidth/2);
        paint.setColor(roundProgressColor);//设置画笔颜色
        canvas.drawArc(rectF,0,progress * 360 / max,false,paint);


        //3.绘制文本

        //设置画笔(设置字体大小的操作要放在设置包裹的rect之前）
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);
        String text = progress * 100 / max + "%";
        Rect bound = new Rect();//此时包裹文本的矩形框没有宽度和高度
        paint.getTextBounds(text,0,text.length(),bound);//使其宽度和高度整好包裹文本内容
        //提供文本区域的左、下
        int left = width /2 - bound.width() / 2;
        int bottom = width / 2 + bound.height() / 2;


        canvas.drawText(text,left,bottom,paint);

    }
}
