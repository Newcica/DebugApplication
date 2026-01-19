package com.example.myfirstapplication.wechat

data class Contact(
    val id: String,
    val name: String,
    val avatar: String? = null, // 头像URL或资源ID
    val firstLetter: String,    // 首字母
    val phoneNumber: String? = null,
    val signature: String? = null
)