package com.wangyuelin.fruit.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


import com.wangyuelin.fruit.R;
import com.wangyuelin.fruit.utils.DisplayUtil;
import com.wangyuelin.fruit.utils.SuperOvalUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wanghao on 2017/7/15.
 */
public class ShapeImageView extends AppCompatImageView {

    // 定义Bitmap的默认配置
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLOR_DRAWABLE_DIMENSION = 1;
    private static String TAG = ShapeImageView.class.getSimpleName();

    // 图片的宽高
    private int width;
    private int height;
    private boolean isSizeChanged = false;

    private int borderColor = 0x1A000000; // 边框颜色
    private int borderWidth = 0; // 边框宽度
    private int radius = 0; // 圆角弧度
    private int shapeType = ShapeType.RECTANGLE; // 图片类型（圆形, 矩形）

    private Path ovalPath;
    private int bgColor = 0xffffffff;

    @IntDef({ShapeType.RECTANGLE, ShapeType.CIRCLE, ShapeType.SUPEROVAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShapeType {
        int RECTANGLE = 0;  //方形
        int CIRCLE = 1;     //圆形
        int SUPEROVAL = 2;  //超椭圆
    }

    public ShapeImageView(Context context) {
        this(context, null);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShapeImageViewStyle);
            borderWidth = array.getDimensionPixelOffset(R.styleable.ShapeImageViewStyle_image_border_width, borderWidth);
            borderColor = array.getColor(R.styleable.ShapeImageViewStyle_image_border_color, borderColor);
            radius = array.getDimensionPixelOffset(R.styleable.ShapeImageViewStyle_image_radius, radius);
            shapeType = array.getInteger(R.styleable.ShapeImageViewStyle_image_shape_type, shapeType);
            array.recycle();
        }
        setDrawingCacheEnabled(true);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //1：如果是正常加载的网络图片，直接使用imageview
        if (shapeType == ShapeType.RECTANGLE && borderWidth == 0 && radius == 0) {
            super.onDraw(canvas);
            return;
        }

        //2：如果是特殊处理：圆形，超椭圆，直接忽略padding，scaletype等属性，
        // 处理起来比较麻烦
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        if (drawable.getIntrinsicWidth() <= 0
                || drawable.getIntrinsicHeight() <= 0) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        drawDrawable(canvas, getBitmapFromDrawable(drawable));
        drawBorder(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        isSizeChanged = true;
    }

    /**
     * 绘制超椭圆
     *
     * @param canvas
     * @param paint
     */
    private void drawSuperOval(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        if (ovalPath == null || isSizeChanged) {
            ovalPath = SuperOvalUtil.caculateSuperEllipsePath((width - borderWidth) / 2,
                    (height - borderWidth) / 2);
            isSizeChanged = false;
        }
        if (ovalPath != null) {
            canvas.drawPath(ovalPath, paint);
        }
        canvas.restore();
    }

    // 绘制圆角
    private void drawDrawable(Canvas canvas, Bitmap bitmap) {
        Paint paint = new Paint();
        paint.setColor(bgColor);
        paint.setAntiAlias(true);

        int saveFlags = Canvas.MATRIX_SAVE_FLAG
                | Canvas.CLIP_SAVE_FLAG
                | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                | Canvas.CLIP_TO_LAYER_SAVE_FLAG;

        canvas.saveLayer(0, 0, width, height, null, saveFlags);

        if (shapeType == ShapeType.RECTANGLE) {
            RectF rectf = new RectF(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth / 2, getHeight() - borderWidth / 2);
            canvas.drawRoundRect(rectf, radius, radius, paint);
        } else if (shapeType == ShapeType.CIRCLE) {
            canvas.drawCircle(width / 2, height / 2, width / 2 - borderWidth, paint);
        } else {
            drawSuperOval(canvas, paint);
        }

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); // SRC_IN 只显示两层图像交集部分的上层图像

        //Bitmap缩放
        float scaleWidth = ((float) getWidth()) / bitmap.getWidth();
        float scaleHeight = ((float) getHeight()) / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();
    }

    // 绘制边框
    private void drawBorder(Canvas canvas) {
        if (borderWidth > 0) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(borderWidth);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setAntiAlias(true);
            if (shapeType == ShapeType.RECTANGLE) {
                RectF rectf = new RectF(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth / 2, getHeight() - borderWidth / 2);
                canvas.drawRoundRect(rectf, radius, radius, paint);
            } else if (shapeType == ShapeType.CIRCLE) {
                canvas.drawCircle(width / 2, height / 2, (width - borderWidth) / 2, paint);
            } else {
                drawSuperOval(canvas, paint);
            }
        }
    }

    // 获取Bitmap内容
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        try {
            Bitmap bitmap;
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLOR_DRAWABLE_DIMENSION, COLOR_DRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    // 设置边框颜色
    public void setBorderColor(@ColorRes int id) {
        this.borderColor = getResources().getColor(id);
        invalidate();
    }

    // 设置边框宽度
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = DisplayUtil.dip2px(getContext(), borderWidth);
        invalidate();
    }

    // 设置圆角半径
    public void setRadius(int radius) {
        this.radius = DisplayUtil.dip2px(getContext(), radius);
        invalidate();
    }

    // 设置形状类型
    public void setShapeType(@ShapeType int shapeType) {
        this.shapeType = shapeType;
        invalidate();
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        invalidate();
    }
}
