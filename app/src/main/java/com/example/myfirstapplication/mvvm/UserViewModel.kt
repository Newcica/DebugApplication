package com.example.myfirstapplication.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    // 私有可变 LiveData（仅 ViewModel 内部修改）
    private val _userLiveData = MutableLiveData<User?>()
    private val _errorLiveData = MutableLiveData<String?>()
    private val _loadingLiveData = MutableLiveData<Boolean>()

    // 公开不可变 LiveData（View 仅能观察，不能修改）
    val userLiveData: LiveData<User?> = _userLiveData
    val errorLiveData: LiveData<String?> = _errorLiveData
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    // 初始化 Repository
    private val userRepository = UserManager()
    // 获取用户信息的业务逻辑
    fun getUserInfo(userId: String) {
        // 通知 View 显示加载
        _loadingLiveData.postValue(true)

        // 调用 Repository 获取数据（子线程）
        userRepository.fetchUserInfo(userId) { user, error ->
            // 切换到主线程更新 LiveData
            MainScope().launch {
                // 隐藏加载
                _loadingLiveData.value = false

                if (user != null) {
                    _userLiveData.value = user // 更新用户数据
                    _errorLiveData.value = null // 清空错误
                } else {
                    _userLiveData.value = null // 清空用户数据
                    _errorLiveData.value = error ?: "未知错误" // 更新错误信息
                }
            }
        }
    }
}