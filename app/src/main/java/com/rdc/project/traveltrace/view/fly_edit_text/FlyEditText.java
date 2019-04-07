package com.rdc.project.traveltrace.view.fly_edit_text;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class FlyEditText extends EditText {

    private ViewGroup mContentContainer;
    private int mHeight;
    private String mCacheStr = "";
    private static final int ANIMATION_DEFAULT = 0;
    private static final int ANIMATION_DROPOUT = 1;
    private static final int DEFAULT_DURATION = 600;
    private static final float DEFAULT_SCALE = 1.2f;

    private int mFlyTextColor;
    private float mFlyTextStartSize;
    private float mFlyTextScale;
    private int mFlyDuration;
    private int mFlyType;

    public FlyEditText(Context context) {
        super(context);
    }

    public FlyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setListener();
    }

    public FlyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        setListener();
    }

    private void init(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        if (null == attrs) {
            throw new IllegalArgumentException("Attributes should be provided to this view,");
        }
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlyEditText);
        mFlyTextColor = typedArray.getColor(R.styleable.FlyEditText_fly_text_color, getResources().getColor(R.color.white));
        mFlyTextStartSize = typedArray.getDimension(R.styleable.FlyEditText_fly_text_start_size, getResources().getDimension(R.dimen.dimen_text_size_middle));
        mFlyTextScale = typedArray.getFloat(R.styleable.FlyEditText_fly_text_scale, DEFAULT_SCALE);
        mFlyDuration = typedArray.getInt(R.styleable.FlyEditText_fly_duration, DEFAULT_DURATION);
        mFlyType = typedArray.getInt(R.styleable.FlyEditText_fly_type, 0);
        typedArray.recycle();

        mContentContainer = ((Activity) getContext()).findViewById(android.R.id.content);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mHeight = windowManager.getDefaultDisplay().getHeight();
    }

    private void setListener() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mCacheStr.length() < s.length()) {
                    char last = s.charAt(s.length() - 1);
                    update(last, false);
                } else if (mCacheStr.length() >= 1) {
                    char last = mCacheStr.charAt(mCacheStr.length() - 1);
                    update(last, true);
                }
                mCacheStr = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void update(char last, boolean isOpposite) {
        final TextView textView = new TextView(getContext());
        textView.setTextColor(mFlyTextColor);
        textView.setTextSize(mFlyTextStartSize);
        textView.setText(String.valueOf(last));
        textView.setGravity(Gravity.CENTER);
        mContentContainer.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.measure(0, 0);
        playAnimator(textView, isOpposite, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mContentContainer.removeView(textView);
            }
        });


    }

    private void playAnimator(TextView textView, boolean isOpposite, AnimatorListenerAdapter listenerAdapter) {

        switch (mFlyType) {
            case ANIMATION_DEFAULT:
                playFlyUp(textView, isOpposite, listenerAdapter);
                break;
            case ANIMATION_DROPOUT:
                playFlyDown(textView, isOpposite, listenerAdapter);
                break;
            default:
                break;
        }

    }

    private void playFlyDown(TextView textView, boolean isOpposite, AnimatorListenerAdapter listenerAdapter) {
        float startX;
        float startY;
        float endX;
        float endY;
        float[] coordinate = getCursorCoordinate();
        if (isOpposite) {
            endX = new Random().nextInt(mContentContainer.getWidth());
            endY = 0;
            startX = coordinate[0];
            startY = coordinate[1];
        } else {
            startX = coordinate[0];
            startY = -100;
            endX = startX;
            endY = coordinate[1];
        }
        final AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator animX = ObjectAnimator.ofFloat(textView, "translationX", startX, endX);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(textView, "translationY", startY, endY);
        translationY.setEvaluator(new BounceEaseOut(mFlyDuration));
        animSet.setDuration(mFlyDuration);
        animSet.addListener(listenerAdapter);
        animSet.playTogether(translationY, animX);
        animSet.start();
    }

    private void playFlyUp(TextView textView, boolean isOpposite, AnimatorListenerAdapter listenerAdapter) {

        float startX;
        float startY;
        float endX;
        float endY;
        float[] coordinate = getCursorCoordinate();
        if (isOpposite) {
            endX = new Random().nextInt(mContentContainer.getWidth());
            endY = mHeight / 3.0f * 2;
            startX = coordinate[0];
            startY = coordinate[1];
        } else {

            startX = coordinate[0];
            startY = mHeight / 3.0f * 2;
            endX = startX;
            endY = coordinate[1];
        }
        final AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator animX = ObjectAnimator.ofFloat(textView, "translationX", startX, endX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(textView, "translationY", startY, endY);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(textView, "scaleX", 1f, mFlyTextScale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(textView, "scaleY", 1f, mFlyTextScale);

        animY.setInterpolator(new DecelerateInterpolator());
        animSet.setDuration(mFlyDuration);
        animSet.addListener(listenerAdapter);
        animSet.playTogether(animX, animY, scaleX, scaleY);
        animSet.start();
    }

    private float[] getCursorCoordinate() {
     /*
       *以下通过反射获取光标cursor的坐标。
       * 首先观察到TextView的invalidateCursorPath()方法，它是光标闪动时重绘的方法。
       * 方法的最后有个invalidate(bounds.left + horizontalPadding, bounds.top + verticalPadding,
                   bounds.right + horizontalPadding, bounds.bottom + verticalPadding);
       *即光标重绘的区域，由此可得到光标的坐标
       * 具体的坐标在TextView.mEditor.mCursorDrawable里，获得Drawable之后用getBounds()得到Rect。
       * 之后还要获得偏移量修正，通过以下三个方法获得：
       * getVerticalOffset(),getCompoundPaddingLeft(),getExtendedPaddingTop()。
       *
      */

        int xOffset = 0;
        Class<?> clazz = EditText.class;
        clazz = clazz.getSuperclass();
        try {
            Field editor = clazz.getDeclaredField("mEditor");
            editor.setAccessible(true);
            Object mEditor = editor.get(this);
            Class<?> editorClazz = Class.forName("android.widget.Editor");
            Field drawables = editorClazz.getDeclaredField("mCursorDrawable");
            drawables.setAccessible(true);
            Drawable[] drawable = (Drawable[]) drawables.get(mEditor);

            Method getVerticalOffset = clazz.getDeclaredMethod("getVerticalOffset", boolean.class);
            Method getCompoundPaddingLeft = clazz.getDeclaredMethod("getCompoundPaddingLeft");
            Method getExtendedPaddingTop = clazz.getDeclaredMethod("getExtendedPaddingTop");
            getVerticalOffset.setAccessible(true);
            getCompoundPaddingLeft.setAccessible(true);
            getExtendedPaddingTop.setAccessible(true);
            if (drawable != null) {
                Rect bounds = drawable[0].getBounds();
                Log.d(TAG, bounds.toString());
                xOffset = (int) getCompoundPaddingLeft.invoke(this) + bounds.left;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        int[] location = new int[2];
        getLocationOnScreen(location);
        float x = getX() + xOffset;
        float y = location[1];

        return new float[]{x, y};
    }

}
