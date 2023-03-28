package com.saxena.lifestylehq;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;
public class BackgroundSoundService extends Service {
    static MediaPlayer mediaPlayer= new MediaPlayer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.sunshine);
            mediaPlayer.setLooping(true); // Set looping
            mediaPlayer.setVolume(1.0f, 1.0f);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void stopMusic(){
        mediaPlayer.pause();
    }

    public static void startMusic(){
        mediaPlayer.start();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
//        mediaPlayer.start();
//        Toast.makeText(getApplicationContext(), "Playing Music",    Toast.LENGTH_SHORT).show();
        return startId;
    }
    public void onStart(Intent intent, int startId) {
    }
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }
    @Override
    public void onLowMemory() {
    }
}