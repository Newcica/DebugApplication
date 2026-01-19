package com.example.myfirstapplication.testmvp

class UserModel() {

    fun getUserInfo(userId: String, callback: (User?, String?) -> Unit) {
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