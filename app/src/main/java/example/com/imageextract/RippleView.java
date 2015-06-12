package example.com.imageextract;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by ABHISHEK on 6/11/2015.
 */
public class RippleView extends View {

    int centerX = 10;
    int centerY = 10;
    float radius = 0;
    float maxRadius = 10000;
    private Context mContext;
    private static int rippleStrokeColor = Color.GRAY;
    private static int rippleStrokeWidth = 2;
    private static int rippleVelocity = 5;
    private static boolean isLayoutCreated = false;
    private ViewGroup mParent;
    private static int originalChildCount = -1;
    private static int initialChildCount = -1;
    public void setmParent(ViewGroup mParent) {
        this.mParent = mParent;
    }


    private Paint.Style rippleType = Paint.Style.FILL;

    public Paint.Style getRippleType() {
        return rippleType;
    }

    /**
     * Called to create different type of ripples
     * @param rippleType : Paint.Style.FILL , Paint.Style.FILL, Paint.Style.FILL_AND_STROKE
     */
    public void setRippleType(Paint.Style rippleType) {
        this.rippleType = rippleType;
    }

    /**
     * Called to get ripple speed
     * @return : ripple speed
     */
    public int getRippleVelocity() {
        return rippleVelocity;
    }

    /**
     * Set ripple speed
     * @param rippleVelocity
     */
    public void setRippleVelocity(int rippleVelocity) {
        RippleView.rippleVelocity = rippleVelocity;
    }

    public int getRippleStrokeWidth() {
        return rippleStrokeWidth;
    }

    public void setRippleStrokeWidth(int rippleStrokeWidth) {
        this.rippleStrokeWidth = rippleStrokeWidth;
    }

    public int getRippleStrokeColor() {
        return rippleStrokeColor;
    }

    public void setRippleStrokeColor(int rippleStrokeColor) {
        this.rippleStrokeColor = rippleStrokeColor;
    }

    /**
     * Ripple effect duration ;default set to 2000ms
     */
    private static int RIPPLE_DURATION = 1200;

    public int getRippleDuration() {
        return RIPPLE_DURATION;
    }

    public void setRippleDuration(int rippleDuration) {
        RIPPLE_DURATION = rippleDuration;
    }

    /**
     * Array to keep account of ripple added in the parent of main layout
     */
    private static ArrayList<Integer> rippleArray = new ArrayList<>();

    /**
     * Called to get the ripple count
     * @return : Number of ripple views added in the layout
     */
    public static ArrayList<Integer> getRippleArray() {
        return rippleArray;
    }

    private static final String TAG = RippleView.class.getSimpleName();

    private RelativeLayout mGroup;

    public void setmGroup(RelativeLayout mGroup) {
        this.mGroup = mGroup;
    }

    public RippleView(Context con){
        super(con);
        mContext = con;
    }


    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "WIDTH = " + widthMeasureSpec +" HEIGHT = " + heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "ON SIZE CHANGED WIDTH = " + w +" HEIGHT = " + h);

    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStrokeWidth(rippleStrokeWidth);
        paint.setStyle(rippleType);
        paint.setColor(rippleStrokeColor);
        canvas.drawCircle(centerX, centerY, radius, paint);

        radius += rippleVelocity; // increase value to make fast ripple effect

        if(!(radius > maxRadius)){
            invalidate();
        }else{
            destroyDrawingCache();
            Log.d(TAG, "Ripple Effect end");
        }
    }

    /**
     * Call to start ripple effect
     * @param v    : Clicked view
     * @param group : Parent of clicked view
     */
    public void initRipple(View v, final ViewGroup group){

        float x = v.getX() - group.getPaddingLeft();
        float y = v.getY() - group.getPaddingTop();
        Log.d(TAG , "X = " + x);
        int width = v.getWidth();
        int height = v.getHeight();
        final RelativeLayout layout = new RelativeLayout(mContext);
       layout.setX(x);
        layout.setY(y);
        Log.d(TAG,"LAYOUT X=" +layout.getX());
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(width, height);
//        layout.setBackgroundColor(Color.RED);
        layout.setLayoutParams(param);

            originalChildCount = group.getChildCount();
            if(initialChildCount == -1 && !isLayoutCreated){
                initialChildCount = originalChildCount;
                isLayoutCreated = true;
            }

            group.addView(layout);
            Log.d(TAG, "NEW CHILD LAYOUT ADDED , TOTAL CHIL LAYOUT COUNT = " + group.getChildCount());

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                showRipple(group, layout, event.getX(), event.getY());
                return false;

            }
        });
    }

    private void showRipple(final ViewGroup parentGroup,final RelativeLayout group, float centerX, float centerY) {
        RippleView rippleView = new RippleView(mContext);
        rippleView.setCenterX((int) centerX);
        rippleView.setCenterY((int) centerY);
        rippleView.setmGroup(group);
        rippleView.setmParent(parentGroup);
        rippleView.invalidate();
        RippleView.getRippleArray().add(RippleView.getRippleArray().size() + 1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG, "Ripples Count " + RippleView.getRippleArray().size());
                Log.d(TAG, "Child Count " + group.getChildCount());

                for(int i = 0; i < group.getChildCount(); i++){
                    try {
                        group.removeViewAt(i);
                        Log.d(TAG, "LAYOUT CHILD COUNT" + group.getChildCount()) ;

                        RippleView.getRippleArray().remove((RippleView.getRippleArray().size() - 1));
                        if (group.getChildCount() == 0) {
                            Log.d(TAG, "Original Child" + initialChildCount);
                            Log.d(TAG, "Current Child " + parentGroup.getChildCount());

                            for(int j=initialChildCount; j < parentGroup.getChildCount(); j++){
                                parentGroup.removeView(group);
                                if(j == parentGroup.getChildCount() - 1) {
                                    isLayoutCreated = false;
                                }

                            }
                        }
                    }catch (Exception x){x.printStackTrace();}
                }
            }
        }, RIPPLE_DURATION);

        group.addView(rippleView);
    }
}
