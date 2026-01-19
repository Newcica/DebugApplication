package com.example.myfirstapplication

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction

class LifecycleDemoActivity : AppCompatActivity() {
    
    private val tag = "LifecycleDemoActivity"
    private var demoFragment: DemoFragment? = null
    private var isFragmentAdded = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lifecycle_demo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lifecycleDemo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        title = "Fragment生命周期演示"
        
        LogManager.d(tag, "onCreate")
        
        // 设置按钮事件监听器
        setupButtonListeners()
        
        // 默认添加Fragment
        toggleFragment()
    }
    
    private fun setupButtonListeners() {
        // 添加/移除Fragment按钮
        findViewById<Button>(R.id.btnToggleFragment).setOnClickListener {
            toggleFragment()
        }
        
        // 启用所有日志
        findViewById<Button>(R.id.btnEnableAllLogs).setOnClickListener {
            LogManager.setLogEnabled(true)
            LogManager.setTagEnabled("LifecycleDemoActivity", true)
            LogManager.setTagEnabled("FragmentLifecycle", true)
            LogManager.d(tag, "已启用所有日志")
        }
        
        // 禁用所有日志
        findViewById<Button>(R.id.btnDisableAllLogs).setOnClickListener {
            LogManager.setLogEnabled(false)
            LogManager.d(tag, "已禁用所有日志")
        }
        
        // 仅启用Fragment日志
        findViewById<Button>(R.id.btnEnableFragmentLogs).setOnClickListener {
            LogManager.setLogEnabled(true)
            LogManager.setTagEnabled("LifecycleDemoActivity", false)
            LogManager.setTagEnabled("FragmentLifecycle", true)
            LogManager.d(tag, "仅启用Fragment日志")
            LogManager.d(tag,"test")
        }
        
        // 仅启用Activity日志
        findViewById<Button>(R.id.btnEnableActivityLogs).setOnClickListener {
            LogManager.setLogEnabled(true)
            LogManager.setTagEnabled("LifecycleDemoActivity", true)
            LogManager.setTagEnabled("FragmentLifecycle", false)
            LogManager.d(tag, "仅启用Activity日志")
        }
    }
    
    private fun toggleFragment() {
        if (isFragmentAdded) {
            removeFragment()
        } else {
            addFragment()
        }
        isFragmentAdded = !isFragmentAdded
    }
    
    private fun addFragment() {
        if (demoFragment == null) {
            demoFragment = DemoFragment()
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragmentContainer, demoFragment!!)
            transaction.commit()
            LogManager.d(tag, "Fragment已添加")
        }
    }
    
    private fun removeFragment() {
        demoFragment?.let { fragment ->
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.remove(fragment)
            transaction.commit()
            demoFragment = null
            LogManager.d(tag, "Fragment已移除")
        }
    }
    
    override fun onStart() {
        super.onStart()
        LogManager.d(tag, "onStart")
    }
    
    override fun onResume() {
        super.onResume()
        LogManager.d(tag, "onResume")
    }
    
    override fun onPause() {
        super.onPause()
        LogManager.d(tag, "onPause")
    }
    
    override fun onStop() {
        super.onStop()
        LogManager.d(tag, "onStop")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        LogManager.d(tag, "onDestroy")
    }
}