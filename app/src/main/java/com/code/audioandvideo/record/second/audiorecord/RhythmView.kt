package com.code.audioandvideo.record.second.audiorecord

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import com.code.audioandvideo.record.second.RhythmView2
import com.code.lib.utils.Dimensions
import kotlin.math.pow
import kotlin.math.sin


class RhythmView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attributeSet, defStyle) {

    companion object {
        private const val TAG = "RhythmView"
    }

    private val paint = Paint()
    private val path = Path()

    private val ZEOR = 0
    private val ONE = 1

    private var A = ONE.toDouble()
    private var W = ONE.toDouble()
    private var Q = ZEOR.toDouble()
    private var maxA = A
    private var part = 0f
    private var halfHeight = 0f

    private lateinit var animAnimator: ValueAnimator

    init {
        paint.isAntiAlias = true
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Dimensions.dp2px(1f)

        initAnim()
    }

    private fun initAnim() {
        animAnimator = ValueAnimator.ofFloat(0f, (2 * Math.PI).toFloat())
            .apply {
                duration = 1000
                interpolator = LinearInterpolator()
                addUpdateListener {
                    Q = (it.animatedValue as Float).toDouble()
                    A = maxA * (1 - Q / (2 * Math.PI))
                    invalidate()
                }
            }
    }

    fun startAnim() {
        if (animAnimator.isRunning) {
            return
        }
        animAnimator.start()
    }

    fun stopAnim() {
        animAnimator.cancel()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        part = w / 4f
        halfHeight = h / 2f
        A = ((halfHeight * 2f) / 3f).toDouble()
        maxA = A
        W = 2 * Math.PI / (angel(part.toDouble()) * 2f)
    }

    override fun onDraw(canvas: Canvas?) {

        path.reset()
        path.moveTo(0f, halfHeight)
        path.lineTo(width.toFloat(), halfHeight)

        canvas?.let {
            paint.color = Color.BLACK
            it.drawPath(path, paint)

            it.save()
            it.translate(part * 2, halfHeight)
            setSinPath()
            paint.color = Color.RED
            it.drawPath(path, paint)
            it.restore()
        }
    }

    private fun setSinPath() {
        val start = -2 * part.toInt()
        path.reset()
        path.moveTo(start.toFloat(), getY(start.toFloat()))

        for (i in start..(part * 2).toInt()) {
            path.lineTo(i.toFloat(), getY(i.toFloat()))
        }
    }

    private fun getFactor(x: Float): Double {
        val factor = 4 / (4 + angel(x / Math.PI * 20 / 21f).pow(4.0))
        return factor.pow(2.5)
    }

    private fun getY(x: Float): Float {
        return (getFactor(x) * A * sin(W * angel(x.toDouble()) - Q)).toFloat()
    }

    private fun angel(x: Double): Double {
        return x / 180 * Math.PI
    }
}