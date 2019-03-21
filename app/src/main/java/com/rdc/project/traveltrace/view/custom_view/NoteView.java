package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteView extends LinearLayout implements IView {

    private TextView mText;
    private NineGridView mNineGridView;

    public NoteView(Context context) {
        this(context, null);
    }

    public NoteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_note_view, this);
        mText = findViewById(R.id.tv_text);
        mNineGridView = findViewById(R.id.nine_grid_view);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof Note) {
            Note note = (Note) data;
            mText.setText(note.getText());
            ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
            List<String> imgUrls = note.getImgUrls();
            for (String url : imgUrls) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setThumbnailUrl(url);
                imageInfo.setBigImageUrl(url);
                imageInfoList.add(imageInfo);
            }
            mNineGridView.setAdapter(new NineGridViewClickAdapter(getContext(), imageInfoList));
        }
    }
}
