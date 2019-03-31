package com.rdc.project.traveltrace.utils;

import android.app.Activity;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rdc.project.traveltrace.R;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;

public class GlideGalleryPickImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, Context context, String url, GalleryImageView galleryImageView, int i, int i1) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().centerCrop().placeholder(R.drawable.ic_picture_place_holder))
                .into(galleryImageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
