package com.example.myfirstapplication.mvvm.myview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.myfirstapplication.R

class CircleProgressView constructor(context: Context, attrs : AttributeSet, defStyleAttr : Int)
    : View(context, attrs, defStyleAttr) {

    // 自定义属性默认值
    private var progressBgColor: Int = Color.GRAY
    private var progressColor: Int = Color.BLUE
    private var progressWidth: Float = 10f
    private var maxProgress: Int = 100
    private var currentProgress: Int = 0

    // 绘制相关工具类
    private lateinit var paint: Paint
    private lateinit var rectF: RectF

    init {
        // 1. 解析自定义属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView, defStyleAttr, 0)
        progressBgColor = typedArray.getColor(R.styleable.CircleProgressView_progressBgColor, progressBgColor)
        progressColor = typedArray.getColor(R.styleable.CircleProgressView_progressColor, progressColor)
        progressWidth = typedArray.getDimension(R.styleable.CircleProgressView_progressWidth, progressWidth)
        maxProgress = typedArray.getInt(R.styleable.CircleProgressView_maxProgress, maxProgress)
        typedArray.recycle() // 必须回收，避免内存泄漏

        // 2. 初始化画笔
        initPaint()
    }

    private fun initPaint() {
        paint = Paint().apply {
            isAntiAlias = true // 抗锯齿
            style = Paint.Style.STROKE // 描边模式（绘制圆环）
            strokeWidth = progressWidth // 画笔宽度
            strokeCap = Paint.Cap.ROUND // 圆角端点（可选，美观）
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}