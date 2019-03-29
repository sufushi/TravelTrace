package com.rdc.project.traveltrace.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.ImagePreviewActivity;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.adapter.SelectedPictureGirdAdapter;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.base.OnClickRecyclerViewListener;
import com.rdc.project.traveltrace.decorator.SpaceGridItemDecoration;
import com.rdc.project.traveltrace.entity.Picture;
import com.rdc.project.traveltrace.utils.CommonItemTouchHelper;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.CommonItemTouchCallback;
import com.rdc.project.traveltrace.utils.UriTransformUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

import static android.app.Activity.RESULT_OK;
import static com.lzy.ninegrid.preview.ImagePreviewActivity.CURRENT_ITEM;
import static com.lzy.ninegrid.preview.ImagePreviewActivity.IMAGE_INFO;

public class PublishPictureNoteFragment extends BaseFragment implements OnClickRecyclerViewListener {

    private static final int REQUEST_CODE_CHOOSE = 0x101;

    private RecyclerView mRecyclerView;
    private SelectedPictureGirdAdapter mAdapter;

    private List<Picture> mPictureList;
    private List<ImageInfo> mImageInfoList;

    private List<PermissionItem> mPermissionItems = new ArrayList<>();

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_publish_picture_note;
    }

    @Override
    protected void initData(Bundle bundle) {
        mPictureList = new ArrayList<>();
        mImageInfoList = new ArrayList<>();
        mPermissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
        mPermissionItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "存储", R.drawable.permission_ic_storage));
    }

    @Override
    protected void initView() {
//        for (int i = 0; i < 8; i++) {
//            Picture picture = new Picture();
//            picture.setImgPath("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3065338749,2306246489&fm=26&gp=0.jpg");
//            mPictureList.add(picture);
//        }
        addTakePhotoHolder();
        mRecyclerView = mRootView.findViewById(R.id.selected_picture_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.addItemDecoration(new SpaceGridItemDecoration(DensityUtil.dp2px(1, Objects.requireNonNull(getActivity()))));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SelectedPictureGirdAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.updateData(mPictureList);
        CommonItemTouchCallback callback = new CommonItemTouchCallback();
        callback.setAdapter(mAdapter);
        CommonItemTouchHelper itemTouchHelper = new CommonItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void addTakePhotoHolder() {
        Picture picture = new Picture();
        picture.setImgPath("");
        mPictureList.add(picture);
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRecyclerViewListener(this);
    }

    @Override
    public void onItemClick(int position, View view) {
        if (position == mAdapter.getItemCount() - 1) {
            if (checkPermissions()) {
                choosePicture();
            } else {
                requestPermission();
            }
        } else {
            Intent intent = new Intent(getActivity(), ImagePreviewActivity.class);
            intent.putExtra(IMAGE_INFO, (Serializable) mImageInfoList);
            intent.putExtra(CURRENT_ITEM, position);
            startActivity(intent);
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
                .filterColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getActivity().getTheme()))//图标的颜色
                .msg("为了保护世界的和平，开启这些权限吧！\n你我一起拯救世界！")
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
        Matisse.from(getActivity())
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(20)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.thunder.sample.fileprovider"))
//                                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .theme(R.style.CustomMatisseTheme)
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> selectList = Matisse.obtainResult(data);
            if (selectList != null) {
                mPictureList.remove(mPictureList.size() - 1);
                for (int i = 0; i < selectList.size(); i++) {
                    Picture picture = new Picture();
                    picture.setImgPath(UriTransformUtil.getPhotoFilePath(getActivity(), selectList.get(i)));
                    mPictureList.add(picture);
                }
                addTakePhotoHolder();
            }
            mAdapter.updateData(mPictureList);
            mImageInfoList.clear();
            for (int i = 0; i < mPictureList.size() - 1; i++) {
                Picture picture = mPictureList.get(i);
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setBigImageUrl(picture.getImgPath());
                imageInfo.setThumbnailUrl(picture.getImgPath());
                mImageInfoList.add(imageInfo);
            }
        }
    }
}
