package tests.dw.com.androidlivetv_example;

import android.content.Context;
import android.media.PlaybackParams;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.MediaCodecVideoRenderer;
import com.google.android.media.tv.companionlibrary.TvPlayer;


public class LiveTVPlayer implements TvPlayer {

    private static final String TAG = LiveTVPlayer.class.getSimpleName();
    private final SimpleExoPlayer mPlayer;
    private final Context context;
    private final MediaCodecVideoRenderer mediaCodecVideoRenderer;
    private Surface surface;

    LiveTVPlayer(Context context){
        Log.d(TAG,"LiveTVPlayer");
        this.context = context;
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        Uri mp4VideoUri= Uri.parse("http://tagesschau-lh.akamaihd.net/i/tagesschau_1@119231/index_3776_av-b.m3u8?sd=10&rebase=on"); //Radnom 540p indian channel
        mediaCodecVideoRenderer = new MediaCodecVideoRenderer(this.context, MediaCodecSelector.DEFAULT);

        LoadControl loadControl = new DefaultLoadControl();
        mPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "exoplayer2example"), bandwidthMeter);

        MediaSource videoSource = new HlsMediaSource(mp4VideoUri, dataSourceFactory, 3, null, null);
        final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
        mPlayer.prepare(loopingSource);
        mPlayer.setPlayWhenReady(true);
    }

    @Override
    public void seekTo(long position) {
        Log.d(TAG,"seekTo");
    }

    @Override
    public void setPlaybackParams(PlaybackParams params) {
        Log.d(TAG,"setPlaybackParams");
    }

    @Override
    public long getCurrentPosition() {
        Log.d(TAG,"getCurrentPosition");
        return 0;
    }

    @Override
    public long getDuration() {
        Log.d(TAG,"getDuration");
        return 0;
    }

    @Override
    public void setSurface(Surface surface) {
        Log.d(TAG,"setSurface "+ C.MSG_SET_SURFACE);
        this.surface = surface;
        mPlayer.setVideoSurface(surface);

    }

    @Override
    public void setVolume(float volume) {
        Log.d(TAG,"setVolume");

    }

    @Override
    public void pause() {
        Log.d(TAG,"pause");
        mPlayer.stop();
        mPlayer.release();

    }

    @Override
    public void play() {
        Log.d(TAG,"play");

    }

    @Override
    public void registerCallback(Callback callback) {
        Log.d(TAG,"registerCallback");

    }

    @Override
    public void unregisterCallback(Callback callback) {
        Log.d(TAG,"unregisterCallback");
    }


}
