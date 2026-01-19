package com.example.myfirstapplication.ipc.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.util.Log
import com.example.myfirstapplication.IMyAidlInterface
import com.example.myfirstapplication.MyData

class MyDataService : Service() {
    private val sTAG = "MyDataService"
    private var mCallbackList = RemoteCallbackList<IMyAidlInterface.IMyCallback>()
    private val mBinder = object : IMyAidlInterface.Stub() {
        override fun getData(requestParam : String): MyData? {
            Log.d(sTAG, "收到客户端请求参数：$requestParam")
            // 模拟构造复杂 MyData 对象
            val tags = listOf("Android", "AIDL", "跨进程", requestParam)
            return MyData(
                id = 1001,
                name = "服务端返回数据",
                isSuccess = true,
                price = 99.9f,
                tags = tags
            )
        }

        override fun registerCallback(callback: IMyAidlInterface.IMyCallback?) {
            if (callback == null) {
                return
            }
            mCallbackList.register( callback)
            try {
                callback.onDataPush(
                    MyData(
                        id = 1001,
                        name = "服务端回调返回数据",
                        isSuccess = true,
                        price = 99.9f,
                        tags = listOf("Android", "AIDL", "跨进程")
                    )
                )
            } catch (e: Exception) {
                println("callback push data error: " + e.message )
            }
        }

    }

    private fun pushData() {
        val count = mCallbackList.beginBroadcast()
        for (i in 0 until count) {
            try {
                val callback = mCallbackList.getBroadcastItem(i)
                callback.onDataPush(
                    MyData(
                        id = 1001,
                        name = "服务端回调返回数据",
                        isSuccess = true,
                        price = 99.9f,
                        tags = listOf("Android", "AIDL", "跨进程")
                    )
                )
            } catch (e: Exception) {
                println("callback push data error: " + e.message )
            }
        }
    }
    override fun onCreate() {
        super.onCreate()

    }
    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onDestroy() {
        super.onDestroy()
        mCallbackList.kill()
    }

}