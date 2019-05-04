package com.rdc.project.traveltrace.fragment;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.adapter.PictureFilterAdapter;
import com.rdc.project.traveltrace.base.BasePTRFragment;
import com.rdc.project.traveltrace.base.OnRefreshListener;
import com.rdc.project.traveltrace.manager.PuzzleHandlePieceManager;
import com.rdc.project.traveltrace.utils.BitmapUtil;
import com.rdc.project.traveltrace.view.EmptyViewFooter;
import com.rdc.project.traveltrace.view.EmptyViewHeader;
import com.rdc.project.traveltrace.view.SquareMagicImageView;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.seu.magiccamera.adapter.FilterAdapter;
import com.seu.magicfilter.MagicEngine;
import com.seu.magicfilter.filter.helper.MagicFilterType;
import com.seu.magicfilter.helper.SavePictureTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class PictureProcessFragment extends BasePTRFragment implements FilterAdapter.onFilterChangeListener {

    private MagicEngine mMagicEngine;

    private List<PermissionItem> mPermissionItems = new ArrayList<>();

    private PictureFilterAdapter mPictureFilterAdapter;
    private final MagicFilterType[] mMagicFilterTypes = new MagicFilterType[]{
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
        mPermissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储", R.drawable.permission_ic_storage));

        List<MagicFilterType> magicFilterTypeList = new ArrayList<>();
        magicFilterTypeList.addAll(Arrays.asList(mMagicFilterTypes));
        mPictureFilterAdapter = new PictureFilterAdapter(getActivity());
        mPictureFilterAdapter.setOnFilterChangeListener(this);
        mPictureFilterAdapter.updateData(magicFilterTypeList);
    }

    @Override
    protected void initView() {
        SquareMagicImageView magicImageView = mRootView.findViewById(R.id.magic_image_view);
        RecyclerView magicFilterView = mRootView.findViewById(R.id.magic_filter_view);
        mMagicEngine = new MagicEngine.Builder().build(magicImageView);

        magicFilterView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        magicFilterView.setItemAnimator(new DefaultItemAnimator());
        magicFilterView.setAdapter(mPictureFilterAdapter);

        Drawable drawable = PuzzleHandlePieceManager.getInstance().getPieceDrawable();
        if (drawable != null) {
            Bitmap bitmap = BitmapUtil.drawable2Bitmap(drawable);
            if (bitmap == null) {
                bitmap = BitmapUtil.drawable2Bitmap(Objects.requireNonNull(getActivity()), R.drawable.ic_picture_place_holder);
            }
            if (bitmap == null) {
                return;
            }
            magicImageView.setBitmap(bitmap);
        }
    }

    @Override
    protected void setListener() {

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
                        savePicture();
                    }

                    @Override
                    public void onDeny(String permission, int position) {

                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFilterChanged(MagicFilterType filterType) {
        mMagicEngine.setFilter(filterType);
    }

    public void onActionBtnClick() {
        if (checkPermissions()) {
            savePicture();
        } else {
            requestPermission();
        }
    }

    private void savePicture() {
        mMagicEngine.savePicture(getOutputMediaFile(), new SavePictureTask.OnPictureSaveListener() {
            @Override
            public void onSaved(String result) {
                PuzzleHandlePieceManager.getInstance().setPath(result);
                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }
}
