package wallace.scott.fuctionalswipes;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import static java.lang.Thread.sleep;

/**
 * Created by Scott on 2017-02-22.
 */

public class SoundThread implements Runnable {

    //private SoundPool mSoundPool;
    //private int mSoundId;
    private MediaPlayer mPlayer;
    private final String TAG = "TAG";
    private Context context;
    private int choice;

    public SoundThread(Context mContext, int sound){
        context = mContext;
        choice = sound;
    }

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void run(){
/*
        // Create a SoundPool
        SoundPool.Builder spb = new SoundPool.Builder();
        spb.setMaxStreams(1);
        spb.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build());
        mSoundPool = spb.build();

        // In Lab 7 we will be working with an older API level and will use this instead
        // of a SoundPool.Builder to create the SoundPool appropriately
        //mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        // Load a bubble popping sound.
        // See further documentation on SoundPool.play here:
        // http://developer.android.com/reference/android/media/SoundPool.html
        //AssetFileDescriptor as = new AssetFileDescriptor(R);

        // onLoadComplete will be called when the sound has finished loading
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (0 != status) {
                    Log.i(TAG, "Unable to load sound");
                    //finish();
                }
                else{
                    if(!Thread.currentThread().isInterrupted()) {
                        mSoundPool.play(mSoundId, 1, 1, 1, 0, 1);
                    }
                }
            }
        });*/

        mPlayer = new MediaPlayer();

        switch(choice) {
            case 0:
                mPlayer = MediaPlayer.create(context, R.raw.intro_mus);
                break;
            case 1:
                mPlayer = MediaPlayer.create(context, R.raw.one_way);
                break;
            case 2:
                mPlayer = MediaPlayer.create(context, R.raw.game_over1);
                break;
        }

        //mPlayer.prepareAsync();
        mPlayer.start();

        while(!Thread.currentThread().isInterrupted()){
            try {
                //sleep(100);
            } catch (Exception e){
                Log.i(TAG, "Sleep error");
            }
        }
        mPlayer.pause();
        mPlayer.stop();
        mPlayer.release();

/*
        if (null != mSoundPool) {
            mSoundPool.unload(mSoundId);
            mSoundPool.release();
            mSoundPool = null;
        }*/
    }
}
