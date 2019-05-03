package com.rdc.project.traveltrace.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lzy.ninegrid.ImageInfo;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.adapter.SelectedPictureGirdAdapter;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.base.OnClickRecyclerViewListener;
import com.rdc.project.traveltrace.contract.IUploadContract;
import com.rdc.project.traveltrace.contract.IUploadFileContract;
import com.rdc.project.traveltrace.decorator.SpaceGridItemDecoration;
import com.rdc.project.traveltrace.entity.Note;
import com.rdc.project.traveltrace.entity.NoteRecord;
import com.rdc.project.traveltrace.entity.Picture;
import com.rdc.project.traveltrace.entity.PictureNote;
import com.rdc.project.traveltrace.entity.PlainNote;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.manager.NoteRecordUploadManager;
import com.rdc.project.traveltrace.presenter.UploadFilePresenterImpl;
import com.rdc.project.traveltrace.presenter.UploadPresenterImpl;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.CommonItemTouchHelper;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.CommonItemTouchCallback;
import com.rdc.project.traveltrace.utils.GlideGalleryPickImageLoader;
import com.rdc.project.traveltrace.utils.PageSwitchUtil;
import com.rdc.project.traveltrace.utils.PictureUtil;
import com.rdc.project.traveltrace.utils.ProgressDialogUtil;
import com.rdc.project.traveltrace.view.toast.CommonToast;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import cn.bmob.v3.BmobUser;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_SHARE_PUZZLE_IMG;

public class PublishPictureNoteFragment extends BaseFragment implements OnClickRecyclerViewListener, CommonItemTouchCallback.OnItemTouchListener, IUploadFileContract.View, IUploadContract.View {

    private static final String SAVE_DIR = "/Gallery/Pictures";
    private static final String CAPTURE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + SAVE_DIR;

    private EditText mPublishTextView;
    private RecyclerView mRecyclerView;
    private SelectedPictureGirdAdapter mAdapter;

    private List<Picture> mPictureList;
    private List<ImageInfo> mImageInfoList;
    private List<String> mSelectedList;
    private Set<String> mCaptureSet;

    private List<PermissionItem> mPermissionItems = new ArrayList<>();

    private GalleryConfig mGalleryConfig;

    private UploadFilePresenterImpl mUploadFilePresenter;
    private UploadPresenterImpl<Note> mNoteUploadPresenter;

    private NoteRecord mNoteRecord;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_publish_picture_note;
    }

    @Override
    protected void initData(Bundle bundle) {
        mPictureList = new ArrayList<>();
        mImageInfoList = new ArrayList<>();
        mSelectedList = new ArrayList<>();
        mCaptureSet = new HashSet<>();
        mPermissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
        mPermissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储", R.drawable.permission_ic_storage));
        String sharePuzzleImg = bundle.getString(ACTION_FIELD_SHARE_PUZZLE_IMG);
        if (!TextUtils.isEmpty(sharePuzzleImg)) {
            mSelectedList.add(sharePuzzleImg);
            Picture picture = new Picture();
            picture.setImgPath(sharePuzzleImg);
            mPictureList.add(picture);
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setBigImageUrl(picture.getImgPath());
            imageInfo.setThumbnailUrl(picture.getImgPath());
            mImageInfoList.add(imageInfo);
        }
        initGalleryConfig();
        mUploadFilePresenter = new UploadFilePresenterImpl(this);
        mNoteUploadPresenter = new UploadPresenterImpl<>(this);
    }

    private void initGalleryConfig() {
        mGalleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideGalleryPickImageLoader())
                .provider("com.rdc.project.traveltrace.fileprovider")
                .pathList(mSelectedList)
                .multiSelect(true, 15)
                .crop(true)
                .isShowCamera(true)
                .filePath(SAVE_DIR)
                .iHandlerCallBack(new IHandlerCallBack() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(List<String> list) {
                        mSelectedList = list;
                        if (mSelectedList != null) {
                            mPictureList.clear();
                            for (int i = 0; i < mSelectedList.size(); i++) {
                                String path = mSelectedList.get(i);
                                if (!TextUtils.isEmpty(path)) {
                                    Picture picture = new Picture();
                                    picture.setImgPath(path);
                                    mPictureList.add(picture);
                                    if (path.contains(CAPTURE_DIR) && !mCaptureSet.contains(path)) {
                                        Activity activity = getActivity();
                                        if (activity != null) {
                                            activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(path))));
                                            mCaptureSet.add(path);
                                        }
                                    }
                                }
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
        addTakePhotoHolder();
        mPublishTextView = mRootView.findViewById(R.id.et_publish_text);
        mRecyclerView = mRootView.findViewById(R.id.selected_picture_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.addItemDecoration(new SpaceGridItemDecoration(DensityUtil.dp2px(1, Objects.requireNonNull(getActivity()))));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SelectedPictureGirdAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.updateData(mPictureList);
        CommonItemTouchCallback callback = new CommonItemTouchCallback();
        callback.setAdapter(mAdapter);
        callback.setOnItemTouchListener(this);
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
            previewPicture(position);
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

    private void previewPicture(int position) {
        PageSwitchUtil.goPreviewPictureActivity(getActivity(), mRecyclerView, mImageInfoList, position);
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    @Override
    public void onMovePosition(int fromPos, int targetPos) {
        CollectionUtil.swap(mSelectedList, fromPos, targetPos);
        CollectionUtil.swap(mImageInfoList, fromPos, targetPos);
    }

    @Override
    public void onRemovePosition(int position) {
        if (CollectionUtil.inRange(mSelectedList, position)) {
            mSelectedList.remove(position);
        }
        if (CollectionUtil.inRange(mImageInfoList, position)) {
            mImageInfoList.remove(position);
        }
    }

    public void onActionBtnClick() {
        String publishText = String.valueOf(mPublishTextView.getText());
        if (TextUtils.isEmpty(publishText) && mSelectedList.isEmpty()) {
            CommonToast.error(Objects.requireNonNull(getActivity()), "发表内容不能为空").show();
            return;
        }
        if (mSelectedList.isEmpty()) {
            PlainNote plainNote = new PlainNote();
            plainNote.setText(publishText);
            User user = new User();
            user.setObjectId(BmobUser.getCurrentUser(BmobUser.class).getObjectId());
            plainNote.setUser(user);
            mNoteUploadPresenter.upload(plainNote);
            mNoteRecord = new NoteRecord();
            mNoteRecord.setPlainNote(plainNote);
            ProgressDialogUtil.showProgressDialog(getActivity(), "正在发表...");
        } else {
            for (int i = 0; i < mSelectedList.size(); i++) {
                PictureUtil.compressImage(mSelectedList.get(i), 600, 800, 800);
            }
            mUploadFilePresenter.uploadFile(mSelectedList);
            ProgressDialogUtil.showProgressDialog(getActivity(), "上传图片");
        }
    }

    @Override
    public void onUploadFileProgress(int curIndex, int curPercent, int total, int totalPercent) {
        String str = "总上传图片数:" + total + "\n第 " + curIndex + " 张图片正在上传";
        if (ProgressDialogUtil.isShow()) {
            ProgressDialogUtil.setMsg(str);
        }
    }

    @Override
    public void onUploadFileSuccess(List<String> urlList, String response) {
        String publishText = String.valueOf(mPublishTextView.getText());
        PictureNote pictureNote = new PictureNote();
        pictureNote.setImgUrls(urlList);
        pictureNote.setText(publishText);
        User user = new User();
        user.setObjectId(BmobUser.getCurrentUser(BmobUser.class).getObjectId());
        pictureNote.setUser(user);
        mNoteUploadPresenter.upload(pictureNote);
        mNoteRecord = new NoteRecord();
        mNoteRecord.setPictureNote(pictureNote);
    }

    @Override
    public void onUploadFileFailed(String response) {
        ProgressDialogUtil.dismiss();
        CommonToast.error(Objects.requireNonNull(getActivity()), response).show();
    }

    @Override
    public void onUploadSuccess(String response) {
        ProgressDialogUtil.dismiss();
        CommonToast.success(Objects.requireNonNull(getActivity()), "发表成功").show();
        NoteRecordUploadManager.getInstance().uploadNote(mNoteRecord);
        getActivity().finish();
    }

    @Override
    public void onUploadFailed(String response) {
        ProgressDialogUtil.dismiss();
        CommonToast.error(Objects.requireNonNull(getActivity()), response).show();
    }
}
