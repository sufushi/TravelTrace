package com.rdc.project.traveltrace.view.puzzle_view.impl.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.OnClickRecyclerViewListener;
import com.rdc.project.traveltrace.fragment.dialog_fragment.PuzzleTempleListFragment;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.GlideGalleryPickImageLoader;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;
import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleLayout;
import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleView;
import com.rdc.project.traveltrace.view.puzzle_view.impl.provider.PuzzleProvider;
import com.rdc.project.traveltrace.view.puzzle_view.impl.ui.PuzzlePanelView;
import com.rdc.project.traveltrace.view.puzzle_view.util.FileUtils;
import com.rdc.project.traveltrace.view.toast.CommonToast;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_SHARE_PUZZLE_IMG;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_PUBLISH_PICTURE_NOTE;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class PuzzlePanelPanelController implements IPuzzlePanelController {

    private static final String SAVE_DIR = "/Gallery/Pictures";

    private PuzzlePanelView mPuzzlePanelView;
    private Context mContext;
    private PuzzleView mPuzzleView;

    private GalleryConfig mGalleryConfig;
    private List<PermissionItem> mPermissionItems = new ArrayList<>();

    private OnClickRecyclerViewListener mTempleListener;

    private boolean mShare = false;

    public PuzzlePanelPanelController(Context context, PuzzleView puzzleView) {
        mContext = context;
        mPuzzleView = puzzleView;
        mPermissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
        mPermissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储", R.drawable.permission_ic_storage));
        initGalleryConfig();
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
                        final Target<Bitmap> target = new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                mPuzzleView.replace(bitmap);
                            }
                        };
                        Glide.with(mContext)
                                .asBitmap()
                                .load(path)
                                .into(target);
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

    public void attachPuzzlePanelView(PuzzlePanelView puzzlePanelView) {
        mPuzzlePanelView = puzzlePanelView;
        mPuzzlePanelView.setIPuzzlePanelController(this);
    }

    public void setTempleListener(OnClickRecyclerViewListener listener) {
        mTempleListener = listener;
    }

    @Override
    public void replace() {
        if (checkPermissions()) {
            choosePicture();
        } else {
            requestPermission();
        }
    }

    @Override
    public void rotate() {
        mPuzzleView.rotate(90);
    }

    @Override
    public void flipHorizontal() {
        mPuzzleView.flipHorizontally();
    }

    @Override
    public void flipVertical() {
        mPuzzleView.flipVertically();
    }

    @Override
    public void border() {
        boolean isNeedDrawLine = mPuzzleView.isNeedDrawLine();
        mPuzzleView.setNeedDrawLine(!isNeedDrawLine);
    }

    @Override
    public void filter() {

    }

    @Override
    public void save() {
        File file = FileUtils.getNewFile(mContext, "puzzle");
        FileUtils.savePuzzle(mPuzzleView, file, 100, new FileUtils.Callback() {
            @Override
            public void onSuccess() {
                if (mShare) {
                    assert file != null;
                    Action action = new Action(ACTION_PRE + ACTION_NAME_PUBLISH_PICTURE_NOTE + "?" + ACTION_FIELD_SHARE_PUZZLE_IMG + "=" + file.getPath());
                    ActionManager.doAction(action, mContext);
                    ((Activity) mContext).finish();
                } else {
                    CommonToast.success(mContext, "保存成功").show();
                }
            }

            @Override
            public void onFailed() {
                String message;
                if (mShare) {
                    message = "分享失败";
                    mShare = false;
                } else {
                    message = "保存失败";
                }
                CommonToast.success(mContext, message).show();
            }
        });
    }

    @Override
    public void share() {
        mShare = true;
        save();
    }

    @Override
    public void temple() {
        if (mContext instanceof FragmentActivity) {
            PuzzleTempleListFragment fragment = new PuzzleTempleListFragment();
            fragment.setClickRecyclerViewListener(mTempleListener);
            fragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), PuzzleTempleListFragment.class.getSimpleName());
        }
    }

    private boolean checkPermissions() {
        boolean permission = false;
        for (int i = 0; i < mPermissionItems.size(); i++) {
            permission |= HiPermission.checkPermission(mContext, mPermissionItems.get(i).Permission);
        }
        return permission;
    }

    private void requestPermission() {
        HiPermission.create(mContext)
                .title("亲爱的上帝")
                .permissions(mPermissionItems)
                .filterColor(ResourcesCompat.getColor(mContext.getResources(), R.color.white, mContext.getTheme()))//图标的颜色
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
        GalleryPick.getInstance().setGalleryConfig(mGalleryConfig).open((Activity) mContext);
    }
}
