package com.code.audioandvideo.record.second

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.code.audioandvideo.common.base.BaseActivity
import com.code.audioandvideo.common.utils.Permissions
import com.code.audioandvideo.databinding.ActivityAudioRecordBinding
import com.code.audioandvideo.record.second.audiorecord.AudioRecorder
import com.code.audioandvideo.record.second.audiorecord.IAudioCallback

class AudioRecordActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, AudioRecordActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var viewBinding: ActivityAudioRecordBinding

    private var audioFileName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAudioRecordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.run {
            startAudioRecordBtn.setOnClickListener {
//            AudioRecorder.startRecord()
                audioRhythmView.startAnim()
            }

            pauseAudioRecordBtn.setOnClickListener {
//            AudioRecorder.pauseRecord()
                audioRhythmView2.setPerHeight(1f)
            }

            stopAudioRecordBtn.setOnClickListener {
                AudioRecorder.stopRecord()
            }

            playAudioRecordBtn.setOnClickListener {
                if (audioFileName == null) {
                    return@setOnClickListener
                }
                AudioRecorder.play(audioFileName!!)
            }
        }

        Permissions.checkAndRequestPermissionForAudioRecord(this)

        AudioRecorder.createDefaultAudio("rango", object : IAudioCallback {
            override fun onPlay(filePath: String) {
                audioFileName = filePath
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        AudioRecorder.release()
    }


}