package com.rdc.project.traveltrace.fragment;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BasePTRFragment;
import com.rdc.project.traveltrace.base.OnRefreshListener;
import com.rdc.project.traveltrace.utils.BitmapUtil;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.GlideGalleryPickImageLoader;
import com.rdc.project.traveltrace.utils.HandlerUtil;
import com.rdc.project.traveltrace.view.EmptyViewFooter;
import com.rdc.project.traveltrace.view.EmptyViewHeader;
import com.rdc.project.traveltrace.view.toast.CommonToast;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.seu.magicfilter.MagicEngine;
import com.seu.magicfilter.filter.helper.MagicFilterType;
import com.seu.magicfilter.helper.SavePictureTask;
import com.seu.magicfilter.widget.MagicImageView;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class PictureProcessFragment extends BasePTRFragment implements View.OnClickListener, HandlerUtil.OnReceiveMessageListener {

    private static final String TAG = "PictureProcessFragment";
    private static final String SAVE_DIR = "/Gallery/Pictures";
    private static final int MSG_CODE_ON_SAVE = 0;

    private ImageView mBtnReplacePhoto;
    private ImageView mBtnFilterPhoto;
    private ImageView mBtnSavePhoto;
    private MagicImageView mMagicImageView;
    private MagicEngine mMagicEngine;

    private List<PermissionItem> mPermissionItems = new ArrayList<>();

    private GalleryConfig mGalleryConfig;


    @Override
    protected RefreshHeader createRefreshHeader() {
        return new EmptyViewHeader(getActivity());
    }

    @Override
    protected RefreshFooter createRefreshFooter() {
        return new EmptyViewFooter(mRefreshLayout, getActivity());
    }

    @Override
    protected void configRefreshLayout() {
        mRefreshLayout.setDragRate(0.8f);
    }

    @Override
    protected OnRefreshListener createRefreshListener() {
        return null;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_picture_process;
    }

    @Override
    protected void initData(Bundle bundle) {
        mPermissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
        mPermissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储", R.drawable.permission_ic_storage));
        initGalleryConfig();
        HandlerUtil.getInstance().register(this);
    }

    private void initGalleryConfig() {
        mGalleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideGalleryPickImageLoader())
                .provider("com.rdc.project.traveltrace.fileprovider")
                .multiSelect(false)
                .crop(true)
                .isShowCamera(true)
                .filePath(SAVE_DIR)
                .iHandlerCallBack(new IHandlerCallBack() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(List<String> list) {
                        if (CollectionUtil.isEmpty(list)) {
                            return;
                        }
                        String path = list.get(0);
                        int width = mMagicImageView.getWidth();
                        int height = mMagicImageView.getHeight();
                        Bitmap bitmap = BitmapUtil.getBitmapFromSDCard(path, width, height);
                        mMagicImageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onError() {

                    }
                })
                .build();
    }

    @Override
    protected void initView() {
        mBtnReplacePhoto = mRootView.findViewById(R.id.btn_replace_photo);
        mBtnFilterPhoto = mRootView.findViewById(R.id.btn_filter_photo);
        mBtnSavePhoto = mRootView.findViewById(R.id.btn_save_photo);
        mMagicImageView = mRootView.findViewById(R.id.magic_image_view);
        mMagicEngine = new MagicEngine.Builder().build(mMagicImageView);
    }

    @Override
    protected void setListener() {
        mBtnReplacePhoto.setOnClickListener(this);
        mBtnFilterPhoto.setOnClickListener(this);
        mBtnSavePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_replace_photo:
                if (checkPermissions()) {
                    choosePicture();
                } else {
                    requestPermission();
                }
                break;
            case R.id.btn_filter_photo:
                mMagicEngine.setFilter(MagicFilterType.CRAYON);
                break;
            case R.id.btn_save_photo:
                mMagicEngine.savePicture(getOutputMediaFile(), new SavePictureTask.OnPictureSaveListener() {
                    @Override
                    public void onSaved(String result) {
                        HandlerUtil.getInstance().getHandler().sendEmptyMessage(MSG_CODE_ON_SAVE);
                        Log.i(TAG, "result:" + result);
                    }
                });
                break;
            default:
                break;
        }
    }

    private boolean checkPermissions() {
        boolean permission = false;
        for (int i = 0; i < mPermissionItems.size(); i++) {
            permission |= HiPermission.checkPermission(Objects.requireNonNull(getActivity()), mPermissionItems.get(i).Permission);
        }
        return permission;
    }

    private void requestPermission() {
        HiPermission.create(getActivity())
                .title("亲爱的上帝")
                .permissions(mPermissionItems)
                .filterColor(ResourcesCompat.getColor(getResources(), R.color.white, getActivity().getTheme()))//图标的颜色
                .msg("为了保护世界的和平，开启这些权限吧！\n你我一起拯救世界！")
                .style(R.style.PermissionDefaultBlueStyle)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {

                    }

                    @Override
                    public void onFinish() {
                        choosePicture();
                    }

                    @Override
                    public void onDeny(String permission, int position) {

                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });
    }

    private void choosePicture() {
        GalleryPick.getInstance().setGalleryConfig(mGalleryConfig).open(getActivity());
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MagicImage");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    @Override
    public void handlerMessage(Message msg) {
        switch (msg.what) {
            case MSG_CODE_ON_SAVE:
                CommonToast.info(Objects.requireNonNull(getActivity()), "保存成功").show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        HandlerUtil.getInstance().unregister(this);
        super.onDestroy();
    }
}
