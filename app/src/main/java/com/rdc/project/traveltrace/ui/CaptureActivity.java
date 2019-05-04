package com.rdc.project.traveltrace.ui;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseSwipeBackActivity;
import com.seu.magiccamera.adapter.FilterAdapter;
import com.seu.magicfilter.MagicEngine;
import com.seu.magicfilter.filter.helper.MagicFilterType;
import com.seu.magicfilter.utils.MagicParams;
import com.seu.magicfilter.widget.MagicCameraView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CaptureActivity extends BaseSwipeBackActivity implements View.OnClickListener {

    private LinearLayout mFilterLayout;
    private FilterAdapter mAdapter;
    private MagicEngine mMagicEngine;
    private boolean mIsRecording = false;

    private ObjectAnimator mAnimator;

    private final MagicFilterType[] mTypes = new MagicFilterType[]{
            MagicFilterType.NONE,
            MagicFilterType.FAIRYTALE,
            MagicFilterType.SUNRISE,
            MagicFilterType.SUNSET,
            MagicFilterType.WHITECAT,
            MagicFilterType.BLACKCAT,
            MagicFilterType.SKINWHITEN,
            MagicFilterType.HEALTHY,
            MagicFilterType.SWEETS,
            MagicFilterType.ROMANCE,
            MagicFilterType.SAKURA,
            MagicFilterType.WARM,
            MagicFilterType.ANTIQUE,
            MagicFilterType.NOSTALGIA,
            MagicFilterType.CALM,
            MagicFilterType.LATTE,
            MagicFilterType.TENDER,
            MagicFilterType.COOL,
            MagicFilterType.EMERALD,
            MagicFilterType.EVERGREEN,
            MagicFilterType.CRAYON,
            MagicFilterType.SKETCH,
            MagicFilterType.AMARO,
            MagicFilterType.BRANNAN,
            MagicFilterType.BROOKLYN,
            MagicFilterType.EARLYBIRD,
            MagicFilterType.FREUD,
            MagicFilterType.HEFE,
            MagicFilterType.HUDSON,
            MagicFilterType.INKWELL,
            MagicFilterType.KEVIN,
            MagicFilterType.LOMO,
            MagicFilterType.N1977,
            MagicFilterType.NASHVILLE,
            MagicFilterType.PIXAR,
            MagicFilterType.RISE,
            MagicFilterType.SIERRA,
            MagicFilterType.SUTRO,
            MagicFilterType.TOASTER2,
            MagicFilterType.VALENCIA,
            MagicFilterType.WALDEN,
            MagicFilterType.XPROII
    };

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_capture;
    }

    @Override
    protected boolean isEnableSwipeBack() {
        return true;
    }

    @Override
    protected void initData() {
        MagicEngine.Builder builder = new MagicEngine.Builder();
        MagicCameraView cameraView = (MagicCameraView) findViewById(R.id.magic_camera_view);
        mMagicEngine = builder.build(cameraView);

        mAdapter = new FilterAdapter(this, mTypes);
    }

    @Override
    protected void initView() {
        mFilterLayout = (LinearLayout) findViewById(R.id.layout_filter);
        RecyclerView filterListView = (RecyclerView) findViewById(R.id.filter_listView);
        ImageView btnShutter = (ImageView) findViewById(R.id.btn_camera_shutter);

        filterListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        filterListView.setAdapter(mAdapter);

        mAnimator = ObjectAnimator.ofFloat(btnShutter, "rotation", 0, 360);
        mAnimator.setDuration(500);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    protected void initListener() {
        findViewById(R.id.btn_camera_filter).setOnClickListener(this);
        findViewById(R.id.btn_camera_closefilter).setOnClickListener(this);
        findViewById(R.id.btn_camera_shutter).setOnClickListener(this);
        findViewById(R.id.btn_camera_mode).setOnClickListener(this);
        findViewById(R.id.btn_camera_beauty).setOnClickListener(this);

        mAdapter.setOnFilterChangeListener(mOnFilterChangeListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersionBar.with(this).transparentStatusBar().init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ImmersionBar.with(this).transparentStatusBar().init();
    }

    private FilterAdapter.onFilterChangeListener mOnFilterChangeListener = new FilterAdapter.onFilterChangeListener() {

        @Override
        public void onFilterChanged(MagicFilterType filterType) {
            mMagicEngine.setFilter(filterType);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length != 1 || grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takeVideo();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void takeVideo() {
        if (mIsRecording) {
            mAnimator.end();
            mMagicEngine.stopRecord();
        } else {
            mAnimator.start();
            mMagicEngine.startRecord();
        }
        mIsRecording = !mIsRecording;
    }

    private void showFilters() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mFilterLayout, "translationY", mFilterLayout.getHeight(), 0);
        animator.setDuration(200);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                findViewById(R.id.btn_camera_shutter).setClickable(false);
                mFilterLayout.setVisibility(View.VISIBLE);
            }
        });
        animator.start();
    }

    private void hideFilters() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mFilterLayout, "translationY", 0, mFilterLayout.getHeight());
        animator.setDuration(200);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFilterLayout.setVisibility(View.INVISIBLE);
                findViewById(R.id.btn_camera_shutter).setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mFilterLayout.setVisibility(View.INVISIBLE);
                findViewById(R.id.btn_camera_shutter).setClickable(true);
            }
        });
        animator.start();
    }

    public File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MagicCamera");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera_shutter:
                if (PermissionChecker.checkSelfPermission(CaptureActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(CaptureActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, v.getId());
                } else {
                    takeVideo();
                }
                break;
            case R.id.btn_camera_filter:
                showFilters();
                break;
            case R.id.btn_camera_switch:
                mMagicEngine.switchCamera();
                break;
            case R.id.btn_camera_beauty:
                new AlertDialog.Builder(CaptureActivity.this)
                        .setSingleChoiceItems(new String[]{"关闭", "1", "2", "3", "4", "5"}, MagicParams.beautyLevel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        mMagicEngine.setBeautyLevel(which);
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.btn_camera_closefilter:
                hideFilters();
                break;
        }
    }
}
