package com.example.myfirstapplication

import android.util.Log

object LogManager {
    // 控制是否启用日志
    private var isLogEnabled = true
    
    // 控制各个页面的日志开关
    private val logTags = mutableMapOf(
        "FragmentDemoActivity" to true,
        "FirstFragment" to true,
        "SecondFragment" to true,
        "ThirdFragment" to true,
        "FragmentLifecycle" to true
    )
    
    /**
     * 设置全局日志开关
     */
    fun setLogEnabled(enabled: Boolean) {
        isLogEnabled = enabled
    }
    
    /**
     * 设置特定标签的日志开关
     */
    fun setTagEnabled(tag: String, enabled: Boolean) {
        logTags[tag] = enabled
    }
    
    /**
     * 检查特定标签的日志是否启用
     */
    fun isTagEnabled(tag: String): Boolean {
        return isLogEnabled && (logTags[tag] ?: true)
    }
    
    /**
     * 添加新的标签到日志管理器
     */
    fun addTag(tag: String, enabled: Boolean = true) {
        logTags[tag] = enabled
    }
    
    /**
     * DEBUG级别日志
     */
    fun d(tag: String, message: String) {
        if (isTagEnabled(tag)) {
            Log.d(tag, message)
        }
    }
    
    /**
     * INFO级别日志
     */
    fun i(tag: String, message: String) {
        if (isTagEnabled(tag)) {
            Log.i(tag, message)
        }
    }
    
    /**
     * WARN级别日志
     */
    fun w(tag: String, message: String) {
        if (isTagEnabled(tag)) {
            Log.w(tag, message)
        }
    }
    
    /**
     * ERROR级别日志
     */
    fun e(tag: String, message: String) {
        if (isTagEnabled(tag)) {
            Log.e(tag, message)
        }
    }
}