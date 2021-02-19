package com.code.audioandvideo.record.first

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.os.Bundle
import android.view.SurfaceHolder
import com.code.audioandvideo.R
import com.code.audioandvideo.common.base.BaseActivity
import com.code.audioandvideo.databinding.ActivityDrawPictureBinding

class DrawPictureActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, DrawPictureActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var viewBinding: ActivityDrawPictureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDrawPictureBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initSurfaceView()
    }

    private fun initSurfaceView() {
        viewBinding.imageSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                val paint = Paint()
                paint.isAntiAlias = true
                paint.style = Paint.Style.STROKE

                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.solon)
                val canvas = holder.lockCanvas()
                canvas.drawBitmap(bitmap, 0f, 0f, paint)
                holder.unlockCanvasAndPost(canvas)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }
        })
    }


}