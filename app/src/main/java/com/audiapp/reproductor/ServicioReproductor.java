package com.audiapp.reproductor;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ServicioReproductor extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private final IBinder binder = new ServicioReproductorBinder();
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private ArrayList<Uri> uris;
    private int pista;

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        uris.clear();
        return false;
    }

    public void onCreate() {
        super.onCreate();
        pista = 0;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setUris(ArrayList<String> listaMidis) {
        uris = new ArrayList<>();
        for (String midi : listaMidis) {
            uris.add(Uri.fromFile(new File(midi)));
        }
    }

    public void setUris(String midi) {
        uris = new ArrayList<>();
        uris.add(Uri.fromFile(new File(midi)));
    }

    public void start() {
        mMediaPlayer.start();
    }

    public void play() {
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(mContext, uris.get(pista));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //mp.start();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void pause() {
        mMediaPlayer.pause();
    }

    public void siguiente() {
        // Si es menor que el tope
        if (pista < uris.size()) {
            pista++;
        } else {
            // Si es la última, ir a la última
            pista = 0;
        }
        play();
    }

    public void anterior() {
        // Si es mayor de 0
        if (pista > 0) {
            pista--;
        } else {
            // Si es 0, ir a la última
            pista = uris.size() - 1;
        }
        play();
    }

    public int getPos() {
        return mMediaPlayer.getCurrentPosition();
    }

    public int getDur() {
        return mMediaPlayer.getDuration();
    }

    public void seek(int pos) {
        mMediaPlayer.seekTo(pos);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        mp.reset();
        play();
    }

    public class ServicioReproductorBinder extends Binder {
        public ServicioReproductor getService() {
            return ServicioReproductor.this;
        }
    }
}
