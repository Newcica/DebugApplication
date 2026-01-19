package com.example.myfirstapplication.mvp

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myfirstapplication.R

class UserActivity : AppCompatActivity() , UserContract.View{
    private val TAG = "UserActivity"

    private lateinit var presenter: UserContract.Presenter
    private lateinit var tvUserInfo: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 初始化 UI
        tvUserInfo = findViewById(R.id.tv_user_info)
        progressBar = findViewById(R.id.progress_bar)

        // 初始化 Presenter
        val userModel = UserModel()
        presenter = UserPresenter(this, userModel)

        // 模拟点击按钮获取用户信息
        findViewById<Button>(R.id.btn_get_user).setOnClickListener {
            // 传入用户 ID 1001
            presenter.getUserInfo("1001")
        }
    }

    override fun showLoading() {
        progressBar.visibility = ProgressBar.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = ProgressBar.GONE
    }

    override fun showUserInfo(user: User) {
        tvUserInfo.text = "用户名：${user.name}\n邮箱：${user.email}"
    }

    override fun showError(message: String) {
        tvUserInfo.text = "获取用户信息失败：$message"
    }
}