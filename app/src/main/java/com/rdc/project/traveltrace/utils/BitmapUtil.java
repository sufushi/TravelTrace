package com.rdc.project.traveltrace.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class BitmapUtil {

    private static final String SAVE_PATH = "";

    /**
     * 从SD卡上获取图片。如果不存在则返回null</br>
     *
     * @param url 图片的url地址
     * @param width 期望图片的宽
     * @param height 期望图片的高
     * @return 代表图片的Bitmap对象
     */
    public static Bitmap getBitmapFromSDCard(String url, int width, int height) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(new File(getFileName(url)));
            if (inputStream.available() > 0) {
                return BitmapFactory.decodeStream(inputStream, null,
                        getScaleBitmapOptions(url, width, height));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据url获取生成文件名。如果是本地路径，则直接返回；否则将该url地址MD5后作为文件名</br>
     *
     * @param url 图片的路径
     * @return 图片的文件名
     */
    public static String getFileName(String url) {
        String filePath = url;
        if (url.startsWith("http://") || url.startsWith("https://")) {
            filePath = SAVE_PATH + MD5Util.md5(url);
        }
        return filePath;
    }

    /**
     * 根据url地址获取图片本地Stream</br>
     *
     * @param url 图片的地址
     * @return 本地图片的Stream，否则返回null
     */
    public static InputStream getBitmapStream(String url) {
        InputStream is = null;
        try {
            try {
                is = new FileInputStream(new File(getFileName(url)));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (is == null || is.available() <= 0) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("BitmapUtil", "读取图片流出错" + e.toString());
        }
        return is;
    }

    /**
     * 根据指定的宽高设置相关参数，避免出现OOM现象</br>
     *
     * @param url 图片得url地址
     * @param width 期望图片的宽
     * @param height 期望图片的高
     * @return BitmapFactory.Options对象
     */
    private static BitmapFactory.Options getScaleBitmapOptions(String url, int width, int height) {
        InputStream inputStream = getBitmapStream(url);
        if (inputStream == null || width == 0 || height == 0) {
            return null;
        }
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(inputStream, null, bmpFactoryOptions);

            int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight * 1.0f / height);
            int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth * 1.0f / width);

            /*
             * If both of the ratios are greater than 1, one of the sides of the
             * image is greater than the screen
             */
            if (heightRatio > 1 && widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    // Height ratio is larger, scale according to it
                    bmpFactoryOptions.inSampleSize = heightRatio;
                } else {
                    // Width ratio is larger, scale according to it
                    bmpFactoryOptions.inSampleSize = widthRatio;
                }
            }

            // Decode it for real
            bmpFactoryOptions.inJustDecodeBounds = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 关闭java 层的stream
        closeInputStream(inputStream);

        return bmpFactoryOptions;
    }

    /**
     * 关闭输入流</br>
     *
     * @param inputStream 输入流
     */
    private static void closeInputStream(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    public static Bitmap drawable2Bitmap(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    public static Drawable bitmap2Drawable(Context context, Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

}
