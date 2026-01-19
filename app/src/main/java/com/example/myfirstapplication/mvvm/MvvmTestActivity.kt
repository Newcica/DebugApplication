package com.example.myfirstapplication.mvvm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myfirstapplication.R
import com.example.myfirstapplication.databinding.ActivityMvvmTestBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MvvmTestActivity : AppCompatActivity() {
    private val TAG = "MvvmTestActivity"

    // ViewBinding（自动生成，替代 findViewById）
    private lateinit var binding: ActivityMvvmTestBinding
    // ViewModel 实例
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // 初始化 ViewBinding
        binding = ActivityMvvmTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.test_mvvm)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 初始化 ViewModel（由 ViewModelProvider 管理生命周期）
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // 观察加载状态
        userViewModel.loadingLiveData.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        }

        // 观察用户数据
        userViewModel.userLiveData.observe(this) { user ->
            user?.let {
                binding.tvUserInfo.text = "用户 ID：${it.id}\n姓名：${it.name}\n邮箱：${it.email}"
            }
        }

        // 观察错误信息
        userViewModel.errorLiveData.observe(this) { error ->
            error?.let {
                binding.tvUserInfo.text = "错误：$it"
                android.widget.Toast.makeText(this, it, android.widget.Toast.LENGTH_SHORT).show()
            }
        }

        // 点击按钮触发获取用户信息
        binding.btnGetUser.setOnClickListener {
            userViewModel.getUserInfo("1001") // 仅调用 ViewModel 方法，无逻辑
        }
        // 1. 基础协程（lifecycleScope 是 Activity 生命周期绑定的作用域）
        val job = lifecycleScope.launch(Dispatchers.Main) {
            // 主线程：执行 UI 逻辑
            binding.tvUserInfo.text = "开始请求数据..."

            // 切换到 IO 线程执行挂起函数（网络/文件操作）
            val data = withContext(Dispatchers.IO) {
                fetchDataFromNetwork() // 耗时操作，挂起协程但不阻塞线程
            }

            // 切回主线程：更新 UI
            binding.tvUserInfo.text = "请求结果：$data"



            // 3. async/await 并发请求
            lifecycleScope.launch {
                // 同时启动两个协程，并发执行
                val deferred1 = async(Dispatchers.IO) { fetchData1() }
                val deferred2 = async(Dispatchers.IO) { fetchData2() }

                // 等待两个结果返回（并发，总耗时=最长的那个）
                val result1 = deferred1.await()
                val result2 = deferred2.await()

                binding.tvUserInfo.text = "并发结果：$result1, $result2"
            }
        }
        // 2. 取消协程（如页面销毁时自动取消，lifecycleScope 已内置）
        binding.btnGetUser.setOnClickListener {
            job?.let {
                if (it.isActive) {
                    it.cancel()
                    binding.tvUserInfo.text = "协程已取消"
                }
            }
        }
    }

    // 挂起函数：标记耗时操作，只能在协程中调用
    private suspend fun fetchDataFromNetwork(): String {
        // delay 是挂起函数，模拟网络请求（不阻塞线程）
        delay(2000)
        return "模拟网络数据"
    }

    private suspend fun fetchData1(): String {
        delay(1000)
        return "数据1"
    }

    private suspend fun fetchData2(): String {
        delay(1500)
        return "数据2"
    }

    private suspend fun debugGit(): String {
        delay(2000)
        return "模拟网络数据"
    }

    private suspend fun debugGit5(): String {
        delay(2000)
        return "模拟网络数据"
    }
}