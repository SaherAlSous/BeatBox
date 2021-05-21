package com.bignerdranch.android.beatbox

import android.content.res.AssetManager
import android.util.Log
import java.lang.Exception

private const val TAG = "BeatBox"
private const val SOUND_FOLDER = "sample_sound"

class BeatBox(private val assets: AssetManager) {

    val sounds: List<Sound>

    init {
        sounds = loadSounds()
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
            sounds.add(sound)
        }
        return sounds
    }
}