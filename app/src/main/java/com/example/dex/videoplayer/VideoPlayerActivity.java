package com.example.dex.videoplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class VideoPlayerActivity extends AppCompatActivity {
    private static final String TAG = "VideoPlayerActivity";

    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    private View decorView;
    private int uiImmersiveOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        init();
    }

    private void init() {

        exoPlayerView = findViewById(R.id.exo_player_view);
        fullScreenMode();

        Intent videoIntent = getIntent();
        String videoURL = videoIntent.getStringExtra("VideoUrl");
        Log.d(TAG, "init: videoUrl: " + videoURL);

        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(
                    new AdaptiveTrackSelection.Factory(bandwidthMeter));

            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            Uri videoURI = Uri.parse(videoURL);

            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                    this,
                    "ua");

            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new ExtractorMediaSource(
                    videoURI,
                    dataSourceFactory,
                    extractorsFactory,
                    null,
                    null);

            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);


        } catch (Exception e) {

            Log.e("VideoPlayer", " exoplayer error " + e.toString());

        }
    }

    private void fullScreenMode() {
        uiImmersiveOptions = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiImmersiveOptions);
    }

    @Override
    protected void onStop() {
        super.onStop();

        exoPlayer.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.release();
    }
}
