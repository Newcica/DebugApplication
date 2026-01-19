package com.example.myfirstapplication.mvp

interface UserContract {
    interface View {
        fun showLoading() // 显示加载框
        fun hideLoading() // 隐藏加载框
        fun showUserInfo(user: User) // 展示用户信息
        fun showError(message: String) // 展示错误信息
    }
    interface Presenter {
        fun getUserInfo(userId: String) // 获取用户信息
        fun detachView() // 解绑 View，防止内存泄漏
    }
}