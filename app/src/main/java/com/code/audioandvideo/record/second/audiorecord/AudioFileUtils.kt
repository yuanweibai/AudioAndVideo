package com.code.audioandvideo.record.second.audiorecord


import android.os.Environment
import android.text.TextUtils

import java.io.File

/**
 * 管理录音文件的类
 */
object AudioFileUtils {

    private const val rootPath = "audio"

    //原始文件(不能播放)
    private const val AUDIO_PCM_BASE_PATH = "/$rootPath/pcm/"

    //可播放的高质量音频文件
    private const val AUDIO_WAV_BASE_PATH = "/$rootPath/wav/"

    /**
     * 判断是否有外部存储设备sdcard
     *
     * @return true | false
     */
    private val isSdcardExit: Boolean
        get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    fun getPcmFileAbsolutePath(fileName: String): String {
        var pcmFileName = fileName
        if (TextUtils.isEmpty(pcmFileName)) {
            throw NullPointerException("fileName isEmpty")
        }
        if (!isSdcardExit) {
            throw IllegalStateException("sd card no found")
        }

        if (!pcmFileName.endsWith(".pcm")) {
            pcmFileName = "$pcmFileName.pcm"
        }
        val fileBasePath =
            Environment.getExternalStorageDirectory().absolutePath + AUDIO_PCM_BASE_PATH
        val file = File(fileBasePath)
        //创建目录
        if (!file.exists()) {
            file.mkdirs()
        }
        return fileBasePath + pcmFileName
    }

    fun getWavFileAbsolutePath(fileName: String): String {
        var wavFileName = fileName
        if (!isSdcardExit) {
            throw IllegalStateException("sd card no found")
        }

        if (!wavFileName.endsWith(".wav")) {
            wavFileName = "$wavFileName.wav"
        }
        val fileBasePath =
            Environment.getExternalStorageDirectory().absolutePath + AUDIO_WAV_BASE_PATH
        val file = File(fileBasePath)
        //创建目录
        if (!file.exists()) {
            file.mkdirs()
        }
        return fileBasePath + wavFileName
    }

    /**
     * 清除文件
     *
     * @param filePathList
     */
    fun clearFiles(filePathList: List<String>) {
        for (i in filePathList.indices) {
            val file = File(filePathList[i])
            if (file.exists()) {
                file.delete()
            }
        }
    }

    fun deleteFile(file: File) {
        if (file.isDirectory) {
            val files = file.listFiles()
            for (i in files.indices) {
                val f = files[i]
                deleteFile(f)
            }
            file.delete()//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete()
        }
    }
}