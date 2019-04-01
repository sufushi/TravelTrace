package com.rdc.project.traveltrace.view.float_background;

import android.content.Context;

import com.rdc.project.traveltrace.R;

import java.util.ArrayList;
import java.util.List;

public class FloatViewFactory {

    public static List<FloatObject> createFloatViewList(Context context) {
        List<FloatObject> list = new ArrayList<>();
        list.add(new FloatRect(0.1f, 0.1f, 170, 30));
        list.add(new FloatBitmap( context, 0.2f, 0.3f, R.drawable.ic_action_add_photo));
        list.add(new FloatBitmap( context, 0.8f, 0.3f, R.drawable.ic_action_record));
        list.add(new FloatCircle( 0.8f, 0.8f));
//        list.add(new FloatBitmap( context, 0.1f, 0.6f, R.drawable.ic_action_like_normal));
        list.add(new FloatText( 0.3f, 0.6f, "E"));
        list.add(new FloatText( 0.5f, 0.6f, "T"));
        list.add(new FloatRing( 0.4f, 0.8f, 10 ,40));
        list.add(new FloatRing( 0.6f, 0.2f, 15 ,20));
        list.add(new FloatBitmap( context, 0.3f, 0.7f, R.drawable.ic_action_add_photo));
//        list.add(new FloatBitmap( context, 0.8f, 0.2f, R.drawable.ic_action_comment));
        list.add(new FloatCircle( 0.2f, 0.9f));
        return list;
    }

}
