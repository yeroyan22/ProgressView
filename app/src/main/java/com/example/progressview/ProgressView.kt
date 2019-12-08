package com.example.progressview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


class ProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint().apply {
        isAntiAlias = true
        color = DEF_COLOR
        style = Paint.Style.STROKE
        strokeWidth = DEF_STROKE_WIDTH
        strokeCap = Paint.Cap.ROUND
        shader

    }

    var progress = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        context.takeIf { attrs != null }?.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.CustomProgressView,
            defStyleAttr,
            0
        )?.apply {
            try {
                progress = getFloat(R.styleable.CustomProgressView_progress, DEF_PROGRESS)
                paint.strokeWidth =
                    getDimension(R.styleable.CustomProgressView_progress_width, DEF_STROKE_WIDTH)
                paint.color = getColor(R.styleable.CustomProgressView_progress_color, DEF_COLOR)
                var color = setBackgroundColor(
                    getColor(
                        R.styleable.CustomProgressView_background_color,
                        DEF_BACK_COLOR
                    )
                )
//                var shader: Shader = LinearGradient(0f, height/2f, progress*width, height/2f,
//                    R.styleable.CustomProgressView_progress_color,
//                    R.styleable.CustomProgressView_shader_color, Shader.TileMode.MIRROR)
//                paint.shader = shader
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawLine(0f, height / 2f, progress * width, height / 2f, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                progress = event.x / width
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                progress = event.x / width
                Log.e("progress", progress.toString())
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    companion object {
        private const val DEF_STROKE_WIDTH = 20f
        private const val DEF_COLOR = Color.GREEN
        private const val DEF_BACK_COLOR = Color.GRAY
        private const val DEF_PROGRESS = 0.7f

    }
}

