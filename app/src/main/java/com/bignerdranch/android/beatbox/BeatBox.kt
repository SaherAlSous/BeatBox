package com.bignerdranch.android.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException
import java.lang.Exception

private const val TAG = "BeatBox"
private const val SOUND_FOLDER = "sample_sound"
private const val MAX_SOUNDS = 5
var rate = 1.0f

class BeatBox(private val assets: AssetManager) {



    val sounds: List<Sound>
    /* Creating a sound pool to play the wav files p. 393+ */
    val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()
    init {
        sounds = loadSounds()
    }
    /* playing sounds. P. 396*/

    fun play(sound:Sound){
        sound.soundID?.let {
            soundPool.play(it, 1.0f, 1.0f,1,0, rate)
        }
    }

    fun release(){
        soundPool.release()
    }

    private fun loadSounds() : List<Sound>{
        val soundNames : Array<String>
        try {
            soundNames = assets.list(SOUND_FOLDER)!!
        }catch (e:Exception){
            Log.e(TAG,"Could not list Assets", e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()
        soundNames.forEach { filename ->
            val assetPath = "$SOUND_FOLDER/$filename"
            val sound = Sound(assetPath)

            try {
                load(sound)
                sounds.add(sound)
            }catch (ioe: IOException){
                Log.e(TAG, "Could not load sound $filename, ioe")
            }
        }
        return sounds
    }

    /*
    loading IDs for sound files, therefore soundpool can play them. p. 394
     */
    private fun load(sound: Sound){
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd,1)
        sound.soundID=soundId
    }


}