package com.example.myfirstapplication.testmvp

class UserPresenter (val view: UserContract.View, val model: UserModel) : UserContract.Presenter {
    override fun getUserInfo(userId: String) {
        view.showLoading()
        if (userId == "1001") {
            model.getUserInfo(userId) { user, error ->
                if (user != null) {
                    view.showUserInfo(user)
                } else {
                    view.showError(error ?: "Unknown error.")
                }
                view.hideLoading()

            }
        } else {
            view.showUserInfo(User("1001", "张三", "zhangsan@example.com"))
        }
    }

    override fun detachView() {

    }
}