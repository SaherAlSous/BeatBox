package com.bignerdranch.android.beatbox

import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.beatbox.databinding.ActivityMainBinding
import com.bignerdranch.android.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {

   private lateinit var  beatBoxViewModel : BeatBoxViewModel


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val factoryModel =  BeatBoxFactoryModel(assets)
        beatBoxViewModel = ViewModelProvider(this, factoryModel).get(BeatBoxViewModel::class.java)



        /*
        applying databinding in code, and recyclerview with layoutmanager
         */
//        val viewRoot = LayoutInflater.from(this).inflate(R.layout.activity_main, this,false )
//        val binding: ViewDataBinding? = DataBindingUtil.bind(viewRoot)
        val binding : ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding.setLifecycleOwner(this)
        binding.lifecycleOwner
        binding.viewModel = beatBoxViewModel


        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBoxViewModel.beatBox.sounds)
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val value = progress.toFloat()
                BeatBox.rate = value

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

    }

    private inner class SoundHolder(private val binding: ListItemSoundBinding) :
            RecyclerView.ViewHolder(binding.root){
                init {
                    binding.viewModel = SoundViewModel(beatBoxViewModel.beatBox)
                }
        fun bind (sound: Sound){
            binding.apply {
                viewModel?.sound= sound
                executePendingBindings()
                //page 384
            }
        }
            }

    private inner class SoundAdapter(private val sounds: List<Sound>):
        RecyclerView.Adapter<SoundHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
                    )
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
        }

        override fun getItemCount() = sounds.size
            }
//    override fun onDestroy() {
//        super.onDestroy()
//        beatBoxViewModel.beatBox.release()
//    }
}

