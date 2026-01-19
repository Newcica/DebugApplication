package com.example.myfirstapplication

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragmentActivity : AppCompatActivity() {
    
    private val tag = "ViewPagerFragmentActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_pager_fragment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.viewPagerFragment)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        title = "ViewPager Fragment Demo"
        
        LogManager.d(tag, "onCreate")
        
        // 初始化ViewPager2和TabLayout
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        
        // 设置适配器
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter
        
        // 连接TabLayout和ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "第一页"
                1 -> tab.text = "第二页"
                2 -> tab.text = "第三页"
            }
        }.attach()
        
        // 设置日志控制按钮
        setupLogControlButtons()
    }
    
    private fun setupLogControlButtons() {
        // 启用所有日志
        findViewById<Button>(R.id.btnEnableAllLogs).setOnClickListener {
            LogManager.setLogEnabled(true)
            LogManager.setTagEnabled("ViewPagerFragmentActivity", true)
            LogManager.setTagEnabled("FirstFragment", true)
            LogManager.setTagEnabled("SecondFragment", true)
            LogManager.setTagEnabled("ThirdFragment", true)
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
            LogManager.setTagEnabled("ViewPagerFragmentActivity", false)
            LogManager.setTagEnabled("FirstFragment", true)
            LogManager.setTagEnabled("SecondFragment", true)
            LogManager.setTagEnabled("ThirdFragment", true)
            LogManager.d(tag, "仅启用Fragment日志")
        }
        
        // 仅启用Activity日志
        findViewById<Button>(R.id.btnEnableActivityLogs).setOnClickListener {
            LogManager.setLogEnabled(true)
            LogManager.setTagEnabled("ViewPagerFragmentActivity", true)
            LogManager.setTagEnabled("FirstFragment", false)
            LogManager.setTagEnabled("SecondFragment", false)
            LogManager.setTagEnabled("ThirdFragment", false)
            LogManager.d(tag, "仅启用Activity日志")
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