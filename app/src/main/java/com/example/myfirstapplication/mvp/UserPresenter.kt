package com.example.myfirstapplication.mvp

import android.os.Handler
import android.os.Looper

class UserPresenter(val view: UserContract.View, val model: UserModel) : UserContract.Presenter{
    override fun getUserInfo(userId: String) {
        // 通知 View 显示加载
        view.showLoading()
        // 调用 Model 获取数据
        model.fetchUserInfo(userId) { user, error ->
            // 切换到主线程更新 UI
            Handler(Looper.getMainLooper()).post {
                // 通知 View 隐藏加载
                view.hideLoading()
                if (user != null) {
                    view.showUserInfo(user)
                } else {
                    view.showError(error ?: "未知错误")
                }
            }
        }
    }

    override fun detachView() {

    }

}