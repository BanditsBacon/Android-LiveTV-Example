package tests.dw.com.androidlivetv_example;


import android.content.Context;
import android.media.tv.TvInputManager;
import android.media.tv.TvInputService;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.media.tv.companionlibrary.BaseTvInputService;
import com.google.android.media.tv.companionlibrary.TvPlayer;
import com.google.android.media.tv.companionlibrary.model.Channel;
import com.google.android.media.tv.companionlibrary.model.Program;
import com.google.android.media.tv.companionlibrary.model.RecordedProgram;

public class DWLiveTVInputService extends BaseTvInputService {

    private static final String TAG = "DWLiveTVInputService";

    //TODO Maybe delete it l8
    private static final boolean DEBUG = false;
    private static final long EPG_SYNC_DELAYED_PERIOD_MS = 1000 * 2; // 2 Seconds
    private RichTvInputSessionImpl session;


    /**
     * Gets the track id of the track type and track index.
     *
     * @param trackType  the type of the track e.g. TvTrackInfo.TYPE_AUDIO
     * @param trackIndex the index of that track within the media. e.g. 0, 1, 2...
     * @return the track id for the type & index combination.
     */
    private static String getTrackId(int trackType, int trackIndex) {
        return trackType + "-" + trackIndex;
    }

    /**
     * Gets the index of the track for a given track id.
     *
     * @param trackId the track id.
     * @return the track index for the given id, as an integer.
     */
    private static int getIndexFromTrackId(String trackId) {
        return Integer.parseInt(trackId.split("-")[1]);
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate 1");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        this.session.releasePlayer();
    }

    @Nullable
    @Override
    public TvInputService.Session onCreateSession(String inputId) {
        Log.d(TAG,"onCreateSession with id" + inputId);
        this.session = new RichTvInputSessionImpl(this, inputId);
//          RichTvInputSessionImpl session = new RichTvInputSessionImpl(this, inputId);

//        session.setOverlayViewEnabled(true);
        return super.sessionCreated(session);
    }

    class RichTvInputSessionImpl extends BaseTvInputService.Session {

        private static final float CAPTION_LINE_HEIGHT_RATIO = 0.0533f;
        private static final int TEXT_UNIT_PIXELS = 0;
        private static final String UNKNOWN_LANGUAGE = "und";

        private int mSelectedSubtitleTrackIndex;
//        private SubtitleLayout mSubtitleView;
        private TvPlayer mPlayer;
        private boolean mCaptionEnabled;
        private String mInputId;
        private Context mContext;


        RichTvInputSessionImpl(Context context, String inputId) {
            super(context, inputId);
//            mCaptionEnabled = mCaptioningManager.isEnabled();
            mContext = context;
            mInputId = inputId;
        }


        @Override
        public void onSetCaptionEnabled(boolean enabled) {
            Log.d(TAG,"onSetCaptionEnabled");
        }
//
//        @Override
//        public void onTimelineChanged(Timeline timeline, Object manifest) {
//            Log.d(TAG,"onTimelineChanged");
//        }
//
//        @Override
//        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//            Log.d(TAG,"onTracksChanged");
//        }
//
//        @Override
//        public void onLoadingChanged(boolean isLoading) {
//            Log.d(TAG,"onLoadingChanged");
//        }
//
//        @Override
//        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//            Log.d(TAG,"onPlayerStateChanged");
//        }
//
//        @Override
//        public void onPlayerError(ExoPlaybackException error) {
//            Log.d(TAG,"onPlayerError");
//        }
//
//        @Override
//        public void onPositionDiscontinuity() {
//            Log.d(TAG,"onPositionDiscontinuity");
//        }

        @Override
        public TvPlayer getTvPlayer() {
            Log.d(TAG,"getTvPlayer");
//            if(mPlayer != null) mPlayer.play();

            return mPlayer;
        }

        @Override
        public void onPlayChannel(Channel channel) {

            super.onPlayChannel(channel);
        }

        @Override
        public boolean onPlayProgram(Program program, long startPosMs) {
            Log.d(TAG,"onPlayProgram with program " + program.getTitle() + " and " + startPosMs);
            if (program == null) {
                Log.d(TAG,"is null");
//                requestEpgSync(getCurrentChannelUri());
                notifyVideoUnavailable(TvInputManager.VIDEO_UNAVAILABLE_REASON_TUNING);
                return false;
            }
            createPlayer(program.getInternalProviderData().getVideoType(),
                    Uri.parse(program.getInternalProviderData().getVideoUrl()));
            if (startPosMs > 0) {
                mPlayer.seekTo(startPosMs);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                notifyTimeShiftStatusChanged(TvInputManager.TIME_SHIFT_STATUS_AVAILABLE);
            }
//            mPlayer.setPlayWhenReady(true);
            return true;
        }

        private void createPlayer(int videoType, Uri videoUrl) {
            Log.d(TAG,"createPlayer");
            releasePlayer();
            mPlayer = new LiveTVPlayer(getBaseContext());
        }

        private void releasePlayer() {
            Log.d(TAG,"releasePlayer");
            if (mPlayer != null) {
//                mPlayer.removeListener(this);
//                mPlayer.setSurface(null);
                mPlayer.pause();
//                mPlayer.release();
                mPlayer = null;
            }
        }

        @Override
        public void onRelease() {
            super.onRelease();
            releasePlayer();
        }

        @Override
        public boolean onPlayRecordedProgram(RecordedProgram recordedProgram) {
            Log.d(TAG,"onPlayRecordedProgram");
            return false;
        }
    }
}
