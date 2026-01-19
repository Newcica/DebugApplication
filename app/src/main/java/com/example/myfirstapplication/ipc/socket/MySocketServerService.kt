package com.example.myfirstapplication.ipc.socket

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket
import java.security.Provider

class MySocketServerService : Service() {
    private val TAG = "MySocketServerService"
    private val SOCKET_PORT : Int = 9999
    private var mServerSocket : ServerSocket ?= null
    private var isRunning = true
    private val gson = Gson()
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
//        mServerSocket = ServerSocket(SOCKET_PORT)
        //子线程中启动SocketServer
        Thread {
            startSocketServer()
        }.start()
    }

    private fun startSocketServer() {
        try {
            // 1. 创建 ServerSocket，绑定本地端口
            mServerSocket = ServerSocket(SOCKET_PORT)
            Log.d(TAG, "服务端启动成功，监听端口")

            // 2. 循环监听客户端连接（阻塞式）
            while (isRunning) {
                Log.d(TAG, "等待客户端连接...")
                // accept() 阻塞，直到有客户端连接
                val clientSocket = mServerSocket?.accept()
                if (clientSocket != null) {
                    Log.d(TAG, "客户端连接成功：${clientSocket.inetAddress.hostAddress}")
                    // 处理单个客户端的通信（单独线程，避免阻塞监听）
                    Thread {
                        handleClientCommunication(clientSocket)
                    }.start()
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "服务端启动失败：${e.message}")
        }


    }

    private fun handleClientCommunication(clientSocket: Socket) {
        val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        val writer = OutputStreamWriter(clientSocket.getOutputStream())

        val  data = reader.readLine()
        Log.d(TAG, "data ：${data}")
        val packetData = gson.fromJson<PacketData>(data, PacketData::class.java)
        Log.d(TAG, "packetData：${packetData}")

        // 5. 构造响应数据
        val responseData = PacketData(
            id = packetData.id + 1,
            data = "response data",
            timestamp = System.currentTimeMillis(),
            isSuccess = true
        )
        val responseDataStr = gson.toJson(responseData)

        // 6. 发送响应给客户端
        writer.write(responseDataStr + "\n") // 加换行符，客户端按行读取
        writer.flush()
        Log.d(TAG, "服务端发送响应：$responseDataStr")

        // 7. 关闭流和Socket
        reader.close()
        writer.close()
        clientSocket.close()
        Log.d(TAG, "客户端通信完成，关闭连接")
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            mServerSocket?.close()

        } catch (e: IOException) {
            Log.e(TAG, "服务端关闭失败：${e.message}")
        }
        isRunning = false
    }
}