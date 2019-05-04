package com.rdc.project.traveltrace.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceFragment;
import com.rdc.project.traveltrace.base.BaseSwipeAwayDialogFragment;
import com.rdc.project.traveltrace.contract.IUpdateContract;
import com.rdc.project.traveltrace.contract.IUploadFileContract;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.presenter.UpdatePresenterImpl;
import com.rdc.project.traveltrace.presenter.UploadFilePresenterImpl;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.GlideGalleryPickImageLoader;
import com.rdc.project.traveltrace.utils.SharePreferenceUtil;
import com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory.DialogFactory;
import com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory.InputTextDialog;
import com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory.SelectSexDialog;
import com.rdc.project.traveltrace.view.toast.CommonToast;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class PersonDetailFragment extends BaseBounceFragment implements View.OnClickListener, IUploadFileContract.View, IUpdateContract.View {

    private static final String SAVE_DIR = "/Gallery/Pictures";

    private ImageView mIvUserIcon;
    private TextView mTvUserPhoneNumber;
    private TextView mTvUserNikName;
    private TextView mTvUserSex;

    private RelativeLayout mRlUserIcon;
    private RelativeLayout mRlUserPhoneNumber;
    private RelativeLayout mRlUserNikName;
    private RelativeLayout mRlUserPassword;
    private RelativeLayout mRlUserSex;

    private String mUserIcon;
    private String mUserPhoneNumber;
    private String mUserNikName;
    private boolean mUserSex;

    private List<PermissionItem> mPermissionItems = new ArrayList<>();

    private GalleryConfig mGalleryConfig;

    private UploadFilePresenterImpl mUploadFilePresenter;
    private UpdatePresenterImpl<User> mUserUpdatePresenter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_person_detail;
    }

    @Override
    protected void initData(Bundle bundle) {
        mPermissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
        mPermissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储", R.drawable.permission_ic_storage));
        initGalleryConfig();
        if (BmobUser.isLogin()) {
            User user = BmobUser.getCurrentUser(User.class);
            mUserIcon = user.getUserIcon();
            mUserPhoneNumber = user.getMobilePhoneNumber();
            mUserNikName = user.getUserNikName();
            mUserSex = user.isSex();
        }
        mUploadFilePresenter = new UploadFilePresenterImpl(this);
        mUserUpdatePresenter = new UpdatePresenterImpl<>(this);
    }

    @Override
    protected void initView() {

        mIvUserIcon = mRootView.findViewById(R.id.iv_user_icon);
        mTvUserPhoneNumber = mRootView.findViewById(R.id.tv_user_phone_number);
        mTvUserNikName = mRootView.findViewById(R.id.tv_user_nik_name);
        mTvUserSex = mRootView.findViewById(R.id.tv_user_sex);

        mRlUserIcon = mRootView.findViewById(R.id.rl_user_icon);
        mRlUserPhoneNumber = mRootView.findViewById(R.id.rl_user_phone_number);
        mRlUserPassword = mRootView.findViewById(R.id.rl_user_password);
        mRlUserNikName = mRootView.findViewById(R.id.rl_user_nik_name);
        mRlUserSex = mRootView.findViewById(R.id.rl_user_sex);

        Glide.with(Objects.requireNonNull(getActivity())).load(mUserIcon).into(mIvUserIcon);
        mTvUserPhoneNumber.setText(mUserPhoneNumber);
        mTvUserNikName.setText(mUserNikName);
        mTvUserSex.setText(mUserSex ? getString(R.string.string_male) : getString(R.string.string_female));
    }

    @Override
    protected void setListener() {
        mRlUserIcon.setOnClickListener(this);
        mRlUserPhoneNumber.setOnClickListener(this);
        mRlUserNikName.setOnClickListener(this);
        mRlUserPassword.setOnClickListener(this);
        mRlUserSex.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_user_icon:
                selectPicture();
                break;
            case R.id.rl_user_nik_name:
                changeNikName();
                break;
            case R.id.rl_user_phone_number:
                changePhoneNumber();
                break;
            case R.id.rl_user_sex:
                changeSex();
                break;
            case R.id.rl_user_password:
                changePassword();
                break;
        }
    }

    private void changeSex() {
        BaseSwipeAwayDialogFragment
                .newInstance(DialogFactory.createSelectSexDialog(mUserSex, new SelectSexDialog.OnSelectListener() {
                    @Override
                    public void onSelect(boolean isMale) {
                        mUserSex = isMale;
                        mTvUserSex.setText(isMale ? getString(R.string.string_male) : getString(R.string.string_female));
                    }
                }), null)
                .show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "sex");
    }

    private void changePassword() {
        BaseSwipeAwayDialogFragment
                .newInstance(DialogFactory.createChangePasswordDialog("", new InputTextDialog.OnInputTextListener() {
                    @Override
                    public void onInputText(String text) {
                        final String key = "password" + "[" + BmobUser.getCurrentUser(User.class).getObjectId() + "]";
                        String oldPassword = (String) SharePreferenceUtil.get(getActivity(), key, "123456");
                        final String newPassword = text;
                        BmobUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    SharePreferenceUtil.put(getActivity(), key, newPassword);
                                    CommonToast.success(Objects.requireNonNull(getActivity()), "修改密码成功").show();
                                } else {
                                    CommonToast.error(Objects.requireNonNull(getActivity()), "修改密码失败").show();
                                }
                            }
                        });
                    }
                }), null)
                .show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "password");
    }

    private void changePhoneNumber() {
        BaseSwipeAwayDialogFragment
                .newInstance(DialogFactory.createChangePhoneDialog(mUserPhoneNumber, new InputTextDialog.OnInputTextListener() {
                    @Override
                    public void onInputText(String text) {
                        mUserPhoneNumber = text;
                        mTvUserPhoneNumber.setText(text);
                    }
                }), null)
                .show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "phone");
    }

    private void changeNikName() {
        BaseSwipeAwayDialogFragment
                .newInstance(DialogFactory.createChangeNikNameDialog(mUserNikName, new InputTextDialog.OnInputTextListener() {
                    @Override
                    public void onInputText(String text) {
                        mUserNikName = text;
                        mTvUserNikName.setText(text);
                    }
                }), null)
                .show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "nikName");
    }

    private void selectPicture() {
        if (checkPermissions()) {
            choosePicture();
        } else {
            requestPermission();
        }
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
                        mUserIcon = path;
                        Glide.with(Objects.requireNonNull(getActivity())).load(path).into(mIvUserIcon);
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

    public void onActionBtnClick() {
        if (mUserIcon.startsWith("http")) {
            updateUser("");
        } else {
            List<String> pathList = new ArrayList<>();
            pathList.add(mUserIcon);
            mUploadFilePresenter.uploadFile(pathList);
        }
    }

    private void updateUser(String url) {
        User user = BmobUser.getCurrentUser(User.class);
        user.setUserNikName(mUserNikName);
        user.setMobilePhoneNumber(mUserPhoneNumber);
        user.setSex(mUserSex);
        if (!TextUtils.isEmpty(url)) {
            user.setUserIcon(url);
        }
        mUserUpdatePresenter.update(user);
    }

    @Override
    public void onUploadFileProgress(int curIndex, int curPercent, int total, int totalPercent) {

    }

    @Override
    public void onUploadFileSuccess(List<String> urlList, String response) {
        if (!CollectionUtil.isEmpty(urlList)) {
            String url = urlList.get(0);
            updateUser(url);
        }
    }

    @Override
    public void onUploadFileFailed(String response) {
        CommonToast.error(Objects.requireNonNull(getActivity()), response).show();
    }

    @Override
    public void onUpdateSuccess(String response) {
        CommonToast.success(Objects.requireNonNull(getActivity()), response).show();
        getActivity().finish();
    }

    @Override
    public void onUpdateFailed(String response) {
        CommonToast.error(Objects.requireNonNull(getActivity()), response).show();
    }
}
