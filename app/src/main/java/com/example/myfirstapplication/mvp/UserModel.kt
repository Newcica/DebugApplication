package com.example.myfirstapplication.mvp

class UserModel {
    // 模拟网络请求，延迟 1 秒返回数据
    fun fetchUserInfo(userId: String, callback: (User?, String?) -> Unit) {
        Thread {
            Thread.sleep(1000) // 模拟网络延迟
            if (userId == "1001") {
                // 模拟成功数据
                val user = User("1001", "张三", "zhangsan@example.com")
                callback(user, null)
            } else {
                // 模拟失败
                callback(null, "用户不存在")
            }
        }.start()
    }
}