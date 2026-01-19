package com.example.myfirstapplication.testmvp

interface UserContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showUserInfo(user: User)
        fun showError(message: String)
    }

    interface Presenter {
        fun getUserInfo(userId: String)
        fun detachView()
    }
}