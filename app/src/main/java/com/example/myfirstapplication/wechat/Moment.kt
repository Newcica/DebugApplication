package com.example.myfirstapplication.wechat

data class Moment(
    val id: String,
    val userName: String,
    val userAvatar: String,
    val content: String,
    val time: String,
    val likes: List<String>,
    val comments: List<Comment>,
    val imageUrls: List<String>? = null // 可能没有图片
)

data class Comment(
    val id: String,
    val userName: String,
    val content: String
)