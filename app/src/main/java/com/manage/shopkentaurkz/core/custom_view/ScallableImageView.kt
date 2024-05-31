package com.manage.shopkentaurkz.core.custom_view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Matrix
import android.graphics.RectF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView

//customView для просмотра и масштабирования изображений
class ScalableImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    private var scaleGestureDetector: ScaleGestureDetector
    private var matrix: Matrix
    private var scaleFactor = 1.0f
    private val MIN_SCALE_FACTOR = 0.5f
    private val MAX_SCALE_FACTOR = 2.0f

    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var currentTouchX = 0f
    private var currentTouchY = 0f
    private var isMoving = false

    private var viewRect: RectF? = null
    private var imageRect: RectF? = null

    private var gestureDetector: GestureDetector

    init {
        scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
        matrix = Matrix()
        viewRect = RectF()
        imageRect = RectF()

        gestureDetector = GestureDetector(context, GestureListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = x
                lastTouchY = y
                currentTouchX = x
                currentTouchY = y
                isMoving = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (!scaleGestureDetector.isInProgress) {
                    val dx = x - lastTouchX
                    val dy = y - lastTouchY
                    matrix.postTranslate(dx, dy)
                    lastTouchX = x
                    lastTouchY = y
                    isMoving = true
                    checkBounds()
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (!isMoving) {
                    performClick()
                }
                isMoving = false
            }
        }

        imageMatrix = matrix
        gestureDetector.onTouchEvent(event)
        scaleGestureDetector.onTouchEvent(event)
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()

        return true
    }

    private fun checkBounds() {
        imageRect?.set(0f, 0f, drawable.intrinsicWidth.toFloat(), drawable.intrinsicHeight.toFloat())
        matrix.mapRect(imageRect)

        viewRect?.set(0f, 0f, width.toFloat(), height.toFloat())

        val dx = when {
            imageRect!!.left > viewRect!!.left -> viewRect!!.left - imageRect!!.left
            imageRect!!.right < viewRect!!.right -> viewRect!!.right - imageRect!!.right
            else -> 0f
        }
        val dy = when {
            imageRect!!.top > viewRect!!.top -> viewRect!!.top - imageRect!!.top
            imageRect!!.bottom < viewRect!!.bottom -> viewRect!!.bottom - imageRect!!.bottom
            else -> 0f
        }
        matrix.postTranslate(dx, dy)
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val newScaleFactor = scaleFactor * detector.scaleFactor
            scaleFactor = newScaleFactor.coerceIn(MIN_SCALE_FACTOR, MAX_SCALE_FACTOR)


            if (scaleFactor < 1.0f) {
                scaleFactor = 1.0f
            }

            val focusX = detector.focusX
            val focusY = detector.focusY
            matrix.setScale(scaleFactor, scaleFactor, focusX, focusY)
            checkBounds()
            imageMatrix = matrix
            return true
        }
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            val targetScale = if (scaleFactor > 1.0f) 1.0f else MAX_SCALE_FACTOR
            animateScaleTo(targetScale, e.x, e.y)
            return true
        }
    }

    private fun animateScaleTo(targetScale: Float, focusX: Float, focusY: Float) {
        if (targetScale < 1.0f) {

            return
        }

        val scaleAnimator = ValueAnimator.ofFloat(scaleFactor, targetScale)
        scaleAnimator.addUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            val scaleFactorDiff = scale / scaleFactor
            scaleFactor = scale
            matrix.postScale(scaleFactorDiff, scaleFactorDiff, focusX, focusY)
            checkBounds()
            imageMatrix = matrix
        }
        scaleAnimator.duration = 300
        scaleAnimator.start()
    }
}