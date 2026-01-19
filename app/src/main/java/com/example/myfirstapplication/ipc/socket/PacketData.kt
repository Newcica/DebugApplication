package com.example.myfirstapplication.ipc.socket

import java.sql.Timestamp

data class PacketData(
    val id: Int,
    val data: String,
    val timestamp: Long,
    val isSuccess: Boolean
    ) {
}