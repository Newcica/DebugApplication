package com.example.myfirstapplication.mvvm

class UserManager {
    // 模拟网络请求（实际开发可替换为 Retrofit/OkHttp）
    fun fetchUserInfo(userId: String, callback: (User?, String?) -> Unit) {
        Thread {
            Thread.sleep(1000) // 模拟网络延迟
            if (userId == "1001") {
                val user = User("1001", "张三", "zhangsan@example.com")
                callback(user, null)
            } else {
                callback(null, "用户不存在")
            }
        }.start()
    }
}