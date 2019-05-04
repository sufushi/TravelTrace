package com.seu.magicfilter.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.AttributeSet;
import android.util.Log;

import com.seu.magicfilter.beautify.MagicJni;
import com.seu.magicfilter.camera.CameraEngine;
import com.seu.magicfilter.filter.advanced.MagicBeautyFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageFilter;
import com.seu.magicfilter.utils.OpenGlUtils;
import com.seu.magicfilter.utils.Rotation;
import com.seu.magicfilter.utils.TextureRotationUtil;
import com.seu.magicfilter.helper.SavePictureTask;
import com.seu.magicfilter.widget.base.MagicBaseView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by why8222 on 2016/2/25.
 */
public class MagicImageView extends MagicBaseView {

    private final GPUImageFilter imageInput;

    private ByteBuffer _bitmapHandler = null;

    private Bitmap originBitmap = null;

    public MagicImageView(Context context) {
        this(context, null);
    }

    public MagicImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        imageInput = new GPUImageFilter();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        imageInput.init();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);
        adjustSize(0, false, false);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        if (textureId == OpenGlUtils.NO_TEXTURE)
            textureId = OpenGlUtils.loadTexture(getBitmap(), OpenGlUtils.NO_TEXTURE);
        if (filter == null)
            imageInput.onDrawFrame(textureId, gLCubeBuffer, gLTextureBuffer);
        else
            filter.onDrawFrame(textureId, gLCubeBuffer, gLTextureBuffer);
    }

    @Override
    public void savePicture(final SavePictureTask savePictureTask) {
        final Bitmap bitmap = getBitmap();
        queueEvent(new Runnable() {
            @Override
            public void run() {
                final Bitmap photo = drawPhoto(bitmap);
                GLES20.glViewport(0, 0, surfaceWidth, surfaceHeight);
                if (photo != null)
                    savePictureTask.execute(photo);
            }
        });
    }

    private Bitmap drawPhoto(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] mFrameBuffers = new int[1];
        int[] mFrameBufferTextures = new int[1];
//        if(beautyFilter == null)
//            beautyFilter = new MagicBeautyFilter();
//        beautyFilter.init();
//        beautyFilter.onDisplaySizeChanged(width, height);
//        beautyFilter.onInputSizeChanged(width, height);

        if (filter != null) {
            filter.onInputSizeChanged(width, height);
            filter.onDisplaySizeChanged(width, height);
        }
        GLES20.glGenFramebuffers(1, mFrameBuffers, 0);
        GLES20.glGenTextures(1, mFrameBufferTextures, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mFrameBufferTextures[0]);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0,
                GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[0]);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, mFrameBufferTextures[0], 0);

        GLES20.glViewport(0, 0, width, height);
        int textureId = OpenGlUtils.loadTexture(bitmap, OpenGlUtils.NO_TEXTURE);

        FloatBuffer gLCubeBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.CUBE.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        FloatBuffer gLTextureBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        gLCubeBuffer.put(TextureRotationUtil.CUBE).position(0);
        gLTextureBuffer.put(TextureRotationUtil.getRotation(Rotation.NORMAL, false, true)).position(0);

        if (filter != null) {
            filter.onDrawFrame(textureId, gLCubeBuffer, gLTextureBuffer);
        }
        IntBuffer ib = IntBuffer.allocate(width * height);
        GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        result.copyPixelsFromBuffer(ib);

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        GLES20.glDeleteTextures(1, new int[]{textureId}, 0);
        GLES20.glDeleteFramebuffers(mFrameBuffers.length, mFrameBuffers, 0);
        GLES20.glDeleteTextures(mFrameBufferTextures.length, mFrameBufferTextures, 0);

//        beautyFilter.destroy();
//        beautyFilter = null;
        if (filter != null) {
            filter.onDisplaySizeChanged(surfaceWidth, surfaceHeight);
            filter.onInputSizeChanged(imageWidth, imageHeight);
        }
        return result;
    }

    public void setImageBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled())
            return;
        setBitmap(bitmap);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        adjustSize(0, false, false);
        requestRender();
    }

    public void initMagicBeautify() {
//        if(_bitmapHandler == null){
//            Log.e("MagicSDK", "please storeBitmap first!!");
//            return;
//        }
//        MagicJni.jniInitMagicBeautify(_bitmapHandler);
    }

    public void setBitmap(Bitmap bitmap) {
//        if(_bitmapHandler != null)
//            freeBitmap();
//        _bitmapHandler = MagicJni.jniStoreBitmapData(bitmap);
        originBitmap = bitmap;
    }

//    public void freeBitmap(){
//        if(_bitmapHandler == null)
//            return;
//        MagicJni.jniFreeBitmapData(_bitmapHandler);
//        _bitmapHandler = null;
//    }

    public Bitmap getBitmap() {
//        if(_bitmapHandler == null)
//            return null;
//        return MagicJni.jniGetBitmapFromStoredBitmapData(_bitmapHandler);
        return originBitmap;
    }
}
