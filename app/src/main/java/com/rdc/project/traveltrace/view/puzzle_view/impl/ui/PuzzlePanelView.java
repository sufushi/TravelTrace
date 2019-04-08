package com.rdc.project.traveltrace.view.puzzle_view.impl.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.view.DrawableTextView;
import com.rdc.project.traveltrace.view.puzzle_view.impl.controller.IPuzzlePanelController;

public class PuzzlePanelView extends LinearLayout implements View.OnClickListener {

    private DrawableTextView mBtnReplace;
    private DrawableTextView mBtnRotate;
    private DrawableTextView mBtnFlipHorizontal;
    private DrawableTextView mBtnFlipVertical;
    private DrawableTextView mBtnBorder;
    private DrawableTextView mBtnFilter;
    private DrawableTextView mBtnTemple;
    private DrawableTextView mBtnSave;
    private DrawableTextView mBtnShare;

    private IPuzzlePanelController mIPuzzlePanelController;

    public PuzzlePanelView(Context context) {
        this(context, null);
    }

    public PuzzlePanelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PuzzlePanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_puzzle_panel, this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setBackgroundColor(Color.BLACK);
        setOrientation(VERTICAL);
        int padding = DensityUtil.dp2px(10, context);
        setPadding(padding, padding, padding, padding);
        initViews();
        initListener();
    }

    private void initViews() {
        mBtnReplace = findViewById(R.id.btn_replace);
        mBtnRotate = findViewById(R.id.btn_rotate);
        mBtnFlipHorizontal = findViewById(R.id.btn_flip_horizontal);
        mBtnFlipVertical = findViewById(R.id.btn_flip_vertical);
        mBtnBorder = findViewById(R.id.btn_border);
        mBtnFilter = findViewById(R.id.btn_filter);
        mBtnTemple = findViewById(R.id.btn_temple);
        mBtnSave = findViewById(R.id.btn_save);
        mBtnShare = findViewById(R.id.btn_share);
    }

    private void initListener() {
        mBtnReplace.setOnClickListener(this);
        mBtnRotate.setOnClickListener(this);
        mBtnFlipHorizontal.setOnClickListener(this);
        mBtnFlipVertical.setOnClickListener(this);
        mBtnBorder.setOnClickListener(this);
        mBtnFilter.setOnClickListener(this);
        mBtnTemple.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);
    }

    public void setIPuzzlePanelController(IPuzzlePanelController controller) {
        mIPuzzlePanelController = controller;
    }

    @Override
    public void onClick(View v) {
        if (mIPuzzlePanelController == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.btn_replace:
                mIPuzzlePanelController.replace();
                break;
            case R.id.btn_rotate:
                mIPuzzlePanelController.rotate();
                break;
            case R.id.btn_flip_horizontal:
                mIPuzzlePanelController.flipHorizontal();
                break;
            case R.id.btn_flip_vertical:
                mIPuzzlePanelController.flipVertical();
                break;
            case R.id.btn_border:
                mIPuzzlePanelController.border();
                break;
            case R.id.btn_filter:
                mIPuzzlePanelController.filter();
                break;
            case R.id.btn_temple:
                mIPuzzlePanelController.temple();
                break;
            case R.id.btn_save:
                mIPuzzlePanelController.save();
                break;
            case R.id.btn_share:
                mIPuzzlePanelController.share();
                break;
        }
    }
}
