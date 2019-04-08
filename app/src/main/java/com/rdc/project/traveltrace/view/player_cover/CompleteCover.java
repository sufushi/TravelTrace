package com.rdc.project.traveltrace.view.player_cover;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.receiver.BaseCover;
import com.rdc.project.traveltrace.R;

import static com.rdc.project.traveltrace.utils.player.InterData.Key.KEY_COMPLETE_SHOW;

public class CompleteCover extends BaseCover {

    public CompleteCover(Context context) {
        super(context);
    }

    @Override
    public void onReceiverBind() {
        super.onReceiverBind();
        ImageView replay = findViewById(R.id.iv_replay);
        replay.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onCoverAttachedToWindow() {
        super.onCoverAttachedToWindow();
        if(getGroupValue().getBoolean(KEY_COMPLETE_SHOW)){
            setPlayCompleteState(true);
        }
    }

    @Override
    protected void onCoverDetachedToWindow() {
        super.onCoverDetachedToWindow();
        setCoverVisibility(View.GONE);
    }

    @Override
    public void onReceiverUnBind() {
        super.onReceiverUnBind();
    }

    private View.OnClickListener mOnClickListener = v -> {
        switch (v.getId()){
            case R.id.iv_replay:
                requestReplay(null);
                break;
        }
        setPlayCompleteState(false);
    };

    private void setPlayCompleteState(boolean state){
        setCoverVisibility(state?View.VISIBLE:View.GONE);
        getGroupValue().putBoolean(KEY_COMPLETE_SHOW, state);
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode){
            case OnPlayerEventListener.PLAYER_EVENT_ON_DATA_SOURCE_SET:
            case OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START:
                setPlayCompleteState(false);
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_PLAY_COMPLETE:
                setPlayCompleteState(true);
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public View onCreateCoverView(Context context) {
        return View.inflate(context, R.layout.layout_complete_cover, null);
    }

    @Override
    public int getCoverLevel() {
        return levelMedium(20);
    }

}
