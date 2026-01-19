package com.example.myfirstapplication.wechat

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.myfirstapplication.R

class IndexBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val indexLetters = arrayOf(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"
    )

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 36f
        color = ResourcesCompat.getColor(resources, android.R.color.darker_gray, null)
    }

    private var touchListener: ((String) -> Unit)? = null
    private var currentSelectedIndex = -1

    fun setOnIndexChangeListener(listener: (String) -> Unit) {
        touchListener = listener
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val singleHeight = height / indexLetters.size

        for (i in indexLetters.indices) {
            val x = width / 2
            val y = singleHeight * i + singleHeight / 2 + (paint.textSize - paint.descent()) / 2

            // 如果是当前选中的字母，改变颜色
            if (i == currentSelectedIndex) {
                paint.color = ResourcesCompat.getColor(resources, android.R.color.holo_blue_dark, null)
                paint.textSize = 48f
            } else {
                paint.color = ResourcesCompat.getColor(resources, android.R.color.darker_gray, null)
                paint.textSize = 36f
            }

            canvas.drawText(indexLetters[i], x, y, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val y = event.y
                val index = (y / (height / indexLetters.size)).toInt()

                if (index >= 0 && index < indexLetters.size && currentSelectedIndex != index) {
                    currentSelectedIndex = index
                    invalidate()
                    touchListener?.invoke(indexLetters[index])
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                currentSelectedIndex = -1
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredWidth = 60
        val width = resolveSize(desiredWidth, widthMeasureSpec)
        setMeasuredDimension(width, heightMeasureSpec)
    }
}