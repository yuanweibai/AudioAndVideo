package com.code.audioandvideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.code.audioandvideo.databinding.ActivityMainBinding
import com.code.audioandvideo.record.first.DrawPictureActivity
import com.code.audioandvideo.record.second.AudioRecordActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.drawPictureBtn.setOnClickListener {
            DrawPictureActivity.start(this)
        }

        viewBinding.recordAudioBtn.setOnClickListener {
            AudioRecordActivity.start(this)
        }
    }
}