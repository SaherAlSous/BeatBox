package com.bignerdranch.android.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(private val beatBox: BeatBox): BaseObservable() {
    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it)
        }
    }


    /*page 386
    also check for LiveData and Data Binding page 391
     */


    var sound: Sound? = null
        set(sound){
            field = sound
            //notifyChange() //<-- this or the better below
            notifyPropertyChanged(BR._all)
        }

    val title: String?
    @Bindable get() = sound?.name

}