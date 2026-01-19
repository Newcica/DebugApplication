package com.example.myfirstapplication.fragment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myfirstapplication.R
import com.example.myfirstapplication.databinding.ActivityFragmentTestBinding

class FragmentTestActivity : AppCompatActivity() {
    val TAG = "FragmentTestActivity"
    private  var binding: ActivityFragmentTestBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFragmentTestBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // 设置窗口边衬监听器，用于处理系统栏(状态栏和导航栏)的布局空间
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // 获取系统栏(状态栏和导航栏)的边衬尺寸
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // 为视图设置边衬，确保内容不会与系统栏重叠
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            // 返回 insets 以继续传递窗口边衬信息
            insets
        }
    }
}