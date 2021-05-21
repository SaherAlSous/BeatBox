package com.bignerdranch.android.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(): BaseObservable() {


    //p> 386

    var sound: Sound? = null
        set(sound){
            field = sound
            //notifyChange() //<-- this or the better below
            notifyPropertyChanged(BR._all)
        }

    val title: String?
    @Bindable get() = sound?.name

}