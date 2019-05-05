package com.rdc.project.traveltrace.short_video.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qiniu.pili.droid.shortvideo.PLShortVideoUploader;
import com.qiniu.pili.droid.shortvideo.PLUploadProgressListener;
import com.qiniu.pili.droid.shortvideo.PLUploadResultListener;
import com.qiniu.pili.droid.shortvideo.PLUploadSetting;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.contract.IUploadContract;
import com.rdc.project.traveltrace.contract.IUploadFileContract;
import com.rdc.project.traveltrace.entity.NoteRecord;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.manager.NoteRecordUploadManager;
import com.rdc.project.traveltrace.presenter.UploadFilePresenterImpl;
import com.rdc.project.traveltrace.presenter.UploadPresenterImpl;
import com.rdc.project.traveltrace.short_video.utils.Config;
import com.rdc.project.traveltrace.short_video.utils.ToastUtils;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.ProgressDialogUtil;
import com.rdc.project.traveltrace.view.toast.CommonToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class PlaybackActivity extends Activity implements
        PLUploadResultListener,
        PLUploadProgressListener,
        MediaController.MediaPlayerControl, IUploadContract.View, IUploadFileContract.View {

    private static final String TAG = "PlaybackActivity";
    private static final String MP4_PATH = "MP4_PATH";
    private static final String VIDEO_TEXT = "video_text";
    private static final String PREVIOUS_ORIENTATION = "PREVIOUS_ORIENTATION";

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private MediaPlayer mMediaPlayer;
    private MediaController mMediaController;

    private TextView mUploadBtn;
    private PLShortVideoUploader mVideoUploadManager;
    private ProgressBar mProgressBarDeterminate;
    private boolean mIsUpload = false;
    private String mVideoPath;
    private String mVideoText;
    private int mPreviousOrientation;
    private int mSeekingPosition = 0;

    private UploadPresenterImpl<VideoNote> mVideoNoteUploadPresenter;
    private UploadFilePresenterImpl mUploadFilePresenter;
    private NoteRecord mNoteRecord;
    private String mVideoUrl;

    public static void start(Activity activity, String mp4Path) {
        Intent intent = new Intent(activity, PlaybackActivity.class);
        intent.putExtra(MP4_PATH, mp4Path);
        activity.startActivity(intent);
    }

    public static void start(Activity activity, String mp4Path, String text, int previousOrientation) {
        Intent intent = new Intent(activity, PlaybackActivity.class);
        intent.putExtra(MP4_PATH, mp4Path);
        intent.putExtra(VIDEO_TEXT, text);
        intent.putExtra(PREVIOUS_ORIENTATION, previousOrientation);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_playback);

        PLUploadSetting uploadSetting = new PLUploadSetting();

        mVideoUploadManager = new PLShortVideoUploader(getApplicationContext(), uploadSetting);
        mVideoUploadManager.setUploadProgressListener(this);
        mVideoUploadManager.setUploadResultListener(this);

        mUploadBtn = findViewById(R.id.upload_btn);
        mUploadBtn.setOnClickListener(new UploadOnClickListener());
        mProgressBarDeterminate = findViewById(R.id.progressBar);
        mProgressBarDeterminate.setMax(100);
        mVideoPath = getIntent().getStringExtra(MP4_PATH);
        mVideoText = getIntent().getStringExtra(VIDEO_TEXT);
        mPreviousOrientation = getIntent().getIntExtra(PREVIOUS_ORIENTATION, 1);

        mMediaPlayer = new MediaPlayer();
        if (mMediaPlayer != null) {
            mMediaPlayer.setOnInfoListener(mOnInfoListener);
            mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
            mMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
            mMediaPlayer.setOnErrorListener(mOnErrorListener);
        } else {
            Log.e(TAG, "creating MediaPlayer instance failed, exit.");
            return;
        }

        mSurfaceView = findViewById(R.id.video);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mMediaPlayer.setDisplay(mSurfaceHolder);
                if (!"".equals(mVideoPath) && !mMediaPlayer.isPlaying()) {
                    try {
                        mMediaPlayer.reset();
                        mMediaPlayer.setLooping(true);
                        mMediaPlayer.setDataSource(mVideoPath);
                        mMediaPlayer.prepare();
                        mMediaPlayer.seekTo(mSeekingPosition);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                makeUpVideoPlayingSize();
                mMediaPlayer.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mMediaPlayer.isPlaying()) {
                    mSeekingPosition = mMediaPlayer.getCurrentPosition();
                    mMediaPlayer.stop();
                }
            }
        });

        mMediaController = new MediaController(this);
        mMediaController.setMediaPlayer(this);
        mMediaController.setAnchorView(mSurfaceView);

        mVideoNoteUploadPresenter = new UploadPresenterImpl<>(this);
        mUploadFilePresenter = new UploadFilePresenterImpl(this);
    }

    private void makeUpVideoPlayingSize() {
        int screenWidth, screenHeight, videoWidth, videoHeight, displayWidth, displayHeight;
        float screenAspectRatio, videoAspectRatio;
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);

        screenWidth = outMetrics.widthPixels;
        screenHeight = outMetrics.heightPixels;
        screenAspectRatio = (float)screenHeight / screenWidth;
        Log.i(TAG, "Screen size: " + screenWidth + " × " + screenHeight);
        videoWidth = mMediaPlayer.getVideoWidth();
        videoHeight = mMediaPlayer.getVideoHeight();
        videoAspectRatio = (float)videoHeight / videoWidth;
        Log.i(TAG, "Video size: " + screenWidth + " × " + screenHeight);

        if (screenAspectRatio > videoAspectRatio) {
            displayWidth = screenWidth;
            displayHeight = (int) ((float)screenWidth / videoWidth * videoHeight);
        } else {
            displayWidth = (int) ((float)screenHeight / videoHeight * videoWidth);
            displayHeight = screenHeight;
        }

        mSurfaceHolder.setFixedSize(displayWidth, displayHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mMediaController.isShowing()) {
            mMediaController.show(0);
        } else {
            mMediaController.hide();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void finish() {
        if (0 == mPreviousOrientation) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }

    @Override
    public void start() {
        mMediaPlayer.start();
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mMediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onUploadSuccess(String response) {
        ProgressDialogUtil.dismiss();
        if (mNoteRecord != null) {
            NoteRecordUploadManager.getInstance().uploadNote(mNoteRecord);
        }
    }

    @Override
    public void onUploadFailed(String response) {
        ProgressDialogUtil.dismiss();
        CommonToast.error(this, response).show();
    }

    @Override
    public void onUploadFileProgress(int curIndex, int curPercent, int total, int totalPercent) {

    }

    @Override
    public void onUploadFileSuccess(List<String> urlList, String response) {
        if (!CollectionUtil.isEmpty(urlList)) {
            String firstFramePath = urlList.get(0);
            VideoNote videoNote = new VideoNote();
            videoNote.setUser(BmobUser.getCurrentUser(User.class));
            videoNote.setVideoUrl(mVideoUrl);
            videoNote.setVideoCoverUrl(firstFramePath);
            videoNote.setText(mVideoText);
            mVideoNoteUploadPresenter.upload(videoNote);
            mNoteRecord = new NoteRecord();
            mNoteRecord.setVideoNote(videoNote);
        }
    }

    @Override
    public void onUploadFileFailed(String response) {
        ProgressDialogUtil.dismiss();
        CommonToast.error(this, response).show();
    }

    public class UploadOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!mIsUpload) {
                mVideoUploadManager.startUpload(mVideoPath, Config.TOKEN);
                mProgressBarDeterminate.setVisibility(View.VISIBLE);
                mUploadBtn.setText(R.string.cancel_upload);
                mIsUpload = true;
            } else {
                mVideoUploadManager.cancelUpload();
                mProgressBarDeterminate.setVisibility(View.INVISIBLE);
                mUploadBtn.setText(R.string.upload);
                mIsUpload = false;
            }
        }
    }

    @Override
    public void onUploadProgress(String fileName, double percent) {
        int progress = (int) (percent * 100);
        mProgressBarDeterminate.setProgress(progress);
        if (1.0 == percent) {
            mProgressBarDeterminate.setVisibility(View.INVISIBLE);
        }
    }

    public void copyToClipboard(String filePath) {
        ClipData clipData = ClipData.newPlainText("VideoFilePath", filePath);
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(clipData);
    }

    @Override
    public void onUploadVideoSuccess(JSONObject response) {
        try {
//            copyToClipboard(filePath);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    ToastUtils.l(PlaybackActivity.this, "文件上传成功，" + filePath + "已复制到粘贴板");
//                }
//            });
            mVideoUrl = "http://" + Config.DOMAIN + "/" + response.getString("key");
            mUploadBtn.setText(R.string.upload_complete);
            String firstFramePath = Config.CAPTURED_FRAME_FILE_PATH;
            List<String> list = new ArrayList<>();
            list.add(firstFramePath);
            mUploadFilePresenter.uploadFile(list);
            ProgressDialogUtil.showProgressDialog(this, "正在发表...");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUploadVideoFailed(final int statusCode, final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.l(PlaybackActivity.this, "Upload failed, statusCode = " + statusCode + " error = " + error);
            }
        });
    }

    private MediaPlayer.OnInfoListener mOnInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case MediaPlayer.MEDIA_INFO_UNKNOWN:
                    break;
                case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                    break;
                case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    Log.i(TAG, "video rendering start, ts = " + extra);
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    Log.i(TAG, "onInfo: MediaPlayer.MEDIA_INFO_BUFFERING_START");
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    Log.i(TAG, "onInfo: MEDIA_INFO_BUFFERING_END");
                    break;
                case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                    Log.i(TAG, "onInfo: MEDIA_INFO_BAD_INTERLEAVING");
                    break;
                case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                    Log.i(TAG, "onInfo: MEDIA_INFO_NOT_SEEKABLE");
                    break;
                case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                    Log.i(TAG, "onInfo: MediaPlayer.MEDIA_INFO_METADATA_UPDATE");
                    break;
                case MediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:
                    Log.i(TAG, "onInfo: MediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE");
                    break;
                case MediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:
                    Log.i(TAG, "onInfo: MediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT ");
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            Log.e(TAG, "Error happened, errorCode = " + extra);
            final String errorTip;
            switch (extra) {
                case MediaPlayer.MEDIA_ERROR_IO:
                    /**
                     * SDK will do reconnecting automatically
                     */
                    Log.e(TAG, "IO Error!");
                    return false;
                case MediaPlayer.MEDIA_ERROR_MALFORMED:
                    errorTip = "Malformed bitstream!";
                    break;
                case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                    errorTip = "Unsupported bitstream!";
                    break;
                case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                    errorTip = "Timeout!";
                    break;
                default:
                    errorTip = "unknown error !";
                    break;
            }
            if (errorTip != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.s(PlaybackActivity.this, errorTip);
                    }
                });
            }

            finish();
            return true;
        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.i(TAG, "Play Completed !");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.s(PlaybackActivity.this, "Play Completed !");
                }
            });
            finish();
        }
    };

    private MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            Log.i(TAG, "onBufferingUpdate: " + percent);
        }
    };

    private MediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            Log.i(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height);
        }
    };
}