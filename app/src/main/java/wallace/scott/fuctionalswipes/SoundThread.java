package wallace.scott.fuctionalswipes;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import static android.content.Context.AUDIO_SERVICE;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

/**
 * Author: Scott Wallace and Maxime Thibodeau
 */

public class SoundThread implements Runnable {

    private MediaPlayer mPlayer;
    private final String TAG = "TAG";
    private Context context;
    private int choice;
    private int seekTo;

    public SoundThread(Context mContext, int sound){
        context = mContext;
        choice = sound;
        //seekTo = position*5000;
    }

    public void run(){
        seekTo = 0;
        if(choice == 2) {
            seekTo = 100;
        }

        mPlayer = new MediaPlayer();

        switch(choice) {
            case 0:
                mPlayer = MediaPlayer.create(context, R.raw.level1);
                break;
            case 1:
                mPlayer = MediaPlayer.create(context, R.raw.level2);
                break;
            case 2:
               mPlayer = MediaPlayer.create(context, R.raw.game_over1);
                break;
            case 3:
                mPlayer = MediaPlayer.create(context, R.raw.level3);
                break;
        }

        mPlayer.seekTo(seekTo);
        mPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                mp.start();
            }
        });


        try {
            currentThread().sleep(100);
        } catch (Exception e){
            Log.i("Sleep Error", "Throw Sleep Error Exception");
        }

        while(!Thread.currentThread().isInterrupted() && mPlayer.isPlaying()){

        }

        if(mPlayer.isPlaying()) {
            mPlayer.stop();
        }

        mPlayer.reset();
        mPlayer.release();
    }
}