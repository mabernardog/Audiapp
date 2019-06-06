package com.audiapp.reproductor;

import android.widget.MediaController;


public class ControladorReproductor implements MediaController.MediaPlayerControl {
    private ServicioReproductor mServicioReproductor;

    public ControladorReproductor(ServicioReproductor servicioReproductor) {
        mServicioReproductor = servicioReproductor;
    }

    @Override
    public void start() {
        mServicioReproductor.start();
    }

    @Override
    public void pause() {
        mServicioReproductor.pause();
    }

    @Override
    public int getDuration() {
        if (mServicioReproductor != null && mServicioReproductor.isPlaying()) {
            return mServicioReproductor.getDur();
        }
        return 0;
    }

    @Override
    public int getCurrentPosition() {

        if (mServicioReproductor != null && mServicioReproductor.isPlaying()) {
            return mServicioReproductor.getPos();
        }
        return 0;
    }

    @Override
    public void seekTo(int pos) {
        mServicioReproductor.seek(pos);
    }

    @Override
    public boolean isPlaying() {
        if (mServicioReproductor != null) {
            return mServicioReproductor.isPlaying();
        }
        return false;
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
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
